package core.bookie.service.serviceImpl;


import core.bookie.entity.BorrowingRecord;
import core.bookie.repository.InventoryRepository;
import core.bookie.repository.PatronRepository;
import core.bookie.request.BookRequest;
import core.bookie.entity.Book;
import core.bookie.repository.BooksRepository;
import core.bookie.security.TokenService;
import core.bookie.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import core.bookie.utils.utils;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final utils utils;

    @Autowired
    private final BooksRepository booksRepository;

    @Autowired
    private final PatronRepository patronsRepository;

    @Autowired
    private final InventoryRepository inventoryRepository;

    private final TokenService jwtutils;


    @Override
    public Object addBook(BookRequest request) {

        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setISBN( (String) generateISBN());
        book.setPublicationDate(request.getPublicationDate());
        book.setEdition(request.getEdition());
        book.setQuantity(request.getQuantity());


       return booksRepository.saveAndFlush(book);
    }


    @Cacheable(value = "books")
    @Override
    public Object getAllBooks(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }



    @Cacheable(value = "books", key = "#bookID")
    @Override
    public Object getBook(Long bookID){
        return booksRepository.findById(bookID);
    }


    @Cacheable(value = "books", key = "#bookId")
    @Override
    public void deleteBook(Long bookId) {

        var books = booksRepository.findById(bookId);
        if (books.isEmpty()) {
            throw new IllegalStateException("Book with id " + bookId + " does not exist!");
        }

        booksRepository.deleteById(bookId);

    }

    @Override
    public Object updateBook(Long bookID, Map<Object, Object> fields){

        var patronOptional = booksRepository.findById(bookID);

        if(patronOptional.isEmpty()){
            throw new IllegalStateException("Book with id " + bookID + " does not exist!");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Book book = patronOptional.get();

        fields.forEach((k, v) -> {
            if (v != null) {
                switch (k.toString()) {
                    case "quantity":
                        book.setQuantity((long) v);
                        break;
                    case "edition":
                        book.setEdition((Integer) v);
                        break;
                    case "isbn":
                        book.setISBN((String) v);
                        break;
                    case "publicationDate":
                        try {
                            book.setPublicationDate(dateFormat.parse( v.toString()));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case "author":
                        book.setAuthor((String) v);
                        break;
                    case "title":
                        book.setTitle((String) v);
                        break;
                }
            }
        });

        return booksRepository.saveAndFlush(book);
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

    @Cacheable(value = "inventory")
    @Override
    public Object queryInventory(Pageable pageable, HttpServletRequest request){
        final var header = request.getHeader("Authorization");


        if (header == null){

            return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Authorization parameter in header");

        }

        var token = header.substring(7);

        var email = jwtutils.getUsername(token);

        var user = patronsRepository.findByEmail(email);

        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Does not Exist" );
        }

        var auth = user.get().getAuthorities();

        if(auth.contains(new SimpleGrantedAuthority("ADMIN"))){

            return ResponseEntity.status(HttpStatus.OK).body(inventoryRepository.findAll(pageable));

        } else {


            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    private Object generateISBN(){
        return new BigInteger(24, new SecureRandom()).toString(10);
    }
}
