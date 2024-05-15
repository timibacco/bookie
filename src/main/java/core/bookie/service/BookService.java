package core.bookie.service;


import core.bookie.request.BookRequest;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Object addBook(BookRequest request);

    Object getBook(Long bookID);

    Object getAllBooks(Pageable pageable);

    void deleteBook(Long bookId);

    void updateBook(Long bookId, String title, String author, String ISBN);


    void returnBook(Long bookId, Long patronId);

    void borrowBook(Long bookId, Long patronId);

    Object queryInventory(Pageable pageable);



}
