package core.bookie.controller;

import io.swagger.v3.oas.annotations.Operation;
import core.bookie.request.BookRequest;
import core.bookie.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @Operation(summary = "Get a book", description = "Get a book with bookId as the path variable",
            tags = {"books"},
            parameters = @Parameter(name = "bookID", description = "The valid ID of the book to retrieve", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{bookID}")
    public ResponseEntity<?> getBook(@PathVariable Long bookID) {


        var response = bookService.getBook(bookID);

        return ResponseEntity.ok(response);

    }



    @Operation(summary = "Add a new book to the library", description = "Create a new book instance",
            tags = {"books"},

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
                    (content = @Content(mediaType = "application/json",schema = @Schema(implementation= BookRequest.class))),


            responses = {
                    @ApiResponse(responseCode = "200", description = "Book retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })

    @PostMapping("/add")
    public ResponseEntity<?> addNewBook(@RequestBody BookRequest request) {

        var response = bookService.addBook(request);

        return ResponseEntity.ok(response);

    }


@Operation(summary = "Update a book",description = "Update a book with a valid bookId",
        tags = {"books"},
        parameters =
                    @Parameter(name = "bookId", description = "The valid ID of the book to update", required = true),


        responses = {
                @ApiResponse(responseCode = "200", description = "Book updated successfully"),
                @ApiResponse(responseCode = "404", description = "Book not found"),
                @ApiResponse(responseCode = "400", description = "Bad or Invalid request"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody
                (content = @Content(mediaType = "application/json",schema = @Schema(implementation= BookRequest.class)))
)
    /*
     * Update a book
     * with bookId
     * Patch Mapping is used as opposed to Put Mapping in the assessment
     * This is because Put Mapping is used to update the entire resource,
     *
     * thus setting the resource to null if the request body is empty.
     *
     * Not sure if that's the intent.
     *
     */
    @PatchMapping("/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookRequest request) {

//        bookService.updateBook(bookId, request.getTitle(), request.getAuthor(), request.getISBN());

        return ResponseEntity.ok("Book updated successfully!");
    }





    @Operation(summary = "Delete a book", description = "Delete a book with a valid bookId",
            tags = {"books"},
            parameters = @Parameter(name = "bookId", description = "The valid ID of the book to delete", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Book not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.ok("Book deleted successfully!");

    }



    @Operation(summary = "Get all books", description = "Get all books in the library. It's a pageable request/response.",
            tags = {"books"},


            responses = {
                    @ApiResponse(responseCode = "200", description = "Books retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Books not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable) {

        var response = bookService.getAllBooks();

        return ResponseEntity.ok(response);

    }




@Operation(summary = "Borrow a book", description = "Borrow a book with a valid bookId and patronId",
        tags = {"books"},
        parameters = {
                @Parameter(name = "bookId", description = "The valid ID of the book to borrow", required = true),
                @Parameter(name = "patronId", description = "The valid ID of the patron borrowing the book", required = true)
        },
        responses = {
                @ApiResponse(responseCode = "200", description = "Book checked out successfully"),
                @ApiResponse(responseCode = "404", description = "Book not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {

        bookService.borrowBook(bookId, patronId);

        return ResponseEntity.ok("Book borrowed successfully!");

    }






    @Operation(summary = "Endpoint to return a borrowed book", description = "Return a borrowed book with its valid ID and the " +
            "patron's valid ID",

    responses = {
            @ApiResponse(responseCode = "200", description = "Book returned successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid or Bad Request")

    })

    @PatchMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {

        bookService.returnBook(bookId, patronId);

        return ResponseEntity.ok("Book returned successfully!");

    }
}
