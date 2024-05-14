package core.service.serviceImpl;


import core.bookie.request.BookRequest;
import core.entity.Book;
import core.repository.BooksRepository;
import core.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;


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
    public Object getAllBooks() {
        return booksRepository.findAll();
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
    public void checkoutBook(Long bookId, Long patronId) {

    }

    @Override
    public void returnBook(Long bookId, Long patronId) {

    }

    @Override
    public void borrowBook(Long bookId, Long patronId) {


    }

    private Object generateISBN(){
        return new BigInteger(12, new SecureRandom()).toString(10);
    }
}
