package core.bookie.service;


import core.bookie.request.BookRequest;

public interface BookService {

    Object addBook(BookRequest request);

    Object getBook(Long bookID);

    Object getAllBooks();  // todo: make pageable

    void deleteBook(Long bookId);

    void updateBook(Long bookId, String title, String author, String ISBN);

    void checkoutBook(Long bookId, Long patronId);

    void returnBook(Long bookId, Long patronId);

    void borrowBook(Long bookId, Long patronId);



}
