package core.bookie.service;


import core.bookie.request.BookRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BookService {

    Object addBook(BookRequest request);

    Object getBook(Long bookID);

    Object getAllBooks(Pageable pageable);

    void deleteBook(Long bookId);

    Object updateBook(Long bookID, Map<Object, Object> fields);


    void returnBook(Long bookId, Long patronId);

    void borrowBook(Long bookId, Long patronId);

    Object queryInventory(Pageable pageable, HttpServletRequest request);



}
