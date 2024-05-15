package core.bookie.service.serviceImpl;


import core.bookie.entity.BorrowingRecord;
import core.bookie.repository.InventoryRepository;
import core.bookie.repository.PatronRepository;
import core.bookie.request.BookRequest;
import core.bookie.entity.Book;
import core.bookie.repository.BooksRepository;
import core.bookie.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BooksRepository booksRepository;

    @Autowired
    private final PatronRepository patronsRepository;

    @Autowired
    private final InventoryRepository inventoryRepository;


    @Override
    public Object addBook(BookRequest request) {

        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setISBN( (String) generateISBN());
        book.setPublicationDate(request.getPublicationDate());
        book.setEdition(request.getEdition());


       return booksRepository.saveAndFlush(book);
    }

    @Override
    public Object getAllBooks(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }


    @Override
    public Object getBook(Long bookID){
        return booksRepository.findById(bookID);
    }

    @Override
    public void deleteBook(Long bookId) {

        var books = booksRepository.findById(bookId);
        if (books.isEmpty()) {
            throw new IllegalStateException("Book with id " + bookId + " does not exist!");
        }

        booksRepository.deleteById(bookId);

    }

    @Override
    public void updateBook(Long bookId, String title, String author, String ISBN) {

    }



    @Override
    public void returnBook(Long bookId, Long patronId) {

        var patron = patronsRepository.findById(patronId);
        if(patron.isEmpty()){
            throw new IllegalStateException("Patron with id " + patronId + " does not exist!");
        }

        var book =  booksRepository.findById(bookId);


        if(book.isEmpty()) {
            throw new IllegalStateException("Book with id " + bookId + " does not exist!");
        }

                    inventoryRepository.findByBook(book.get()).ifPresent(


                            inventory -> {


                                if(inventory.isReturned() ){
                                    throw new IllegalStateException("Book is already returned!");
                                }

                                if(!Objects.equals(inventory.getPatron().getPatronId(), patronId)){
                                    throw new IllegalStateException("Book is not borrowed by this patron!");
                                }

                                if(inventory.isOverdue()) {
                                    inventory.setToBeFined(true);
                                }
                                book.get().setQuantity(book.get().getQuantity() + 1);
                                inventory.setReturned(true);
                                inventory.setReturnDate(new Date());
                                inventoryRepository.saveAndFlush(inventory);
                            }
                    );

                }

    



    @Override
    public void borrowBook(Long bookId, Long patronId) {

        var patron = patronsRepository.findById(patronId);

        if (patron.isEmpty()) {
            throw new IllegalStateException("Patron with id " + patronId + " does not exist!");
        }

         booksRepository.findById(bookId).ifPresent(
                 bk -> {
                    if (!bk.isAvailable()) {
                        throw new IllegalStateException("Book is not available at the moment!");
                    }



                    // record this action in the inventory.
                     BorrowingRecord borrowingRecord = new BorrowingRecord();
                        borrowingRecord.setBook(bk);
                        borrowingRecord.setPatron(patron.get());
                        borrowingRecord.setBorrowDate(new Date());


                        // we want all borrowed books to be returned in 7 (SEVEN) days. Thus , (604800s).
                        borrowingRecord.setReturnDate(Date.from(Instant.now().plusSeconds(604800)));
                        borrowingRecord.setReturned(false);


                     //decrement the quantity of book by 1.
                     bk.setQuantity(bk.getQuantity() - 1);


                        
                        booksRepository.saveAndFlush(bk);
                }
        );


    }

    @Override
    public Object queryInventory(Pageable pageable){
        return inventoryRepository.findAll(pageable);
    }

    private Object generateISBN(){
        return new BigInteger(12, new SecureRandom()).toString(10);
    }
}