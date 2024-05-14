package core.bookie.controller;


import brave.Response;
import core.bookie.request.BookRequest;
import core.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;


    @GetMapping("/{bookID}")
    public ResponseEntity<?> getBook(@PathVariable Long bookID) {


        var response = bookService.getBook(bookID);

        return ResponseEntity.ok(response);

    }



    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(@RequestBody BookRequest request) {

        var response = bookService.addBook(request);

        return ResponseEntity.ok(response);

    }


    /**
     * Update a book
     * with bookId
     * PatchMapping is used as opposed to PutMapping in the assessment
     * This is because PutMapping is used to update the entire resource,
     * thus setting the resource to null if the request body is empty
     *
     */
    @PatchMapping("/update/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookRequest request) {

//        bookService.updateBook(bookId, request.getTitle(), request.getAuthor(), request.getISBN());

        return ResponseEntity.ok("Book updated successfully!");
    }


    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.ok("Book deleted successfully!");

    }



    @GetMapping("/all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable) {

        var response = bookService.getAllBooks();

        return ResponseEntity.ok(response);

    }




    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {

        bookService.borrowBook(bookId, patronId);

        return ResponseEntity.ok("Book borrowed successfully!");

    }
}
