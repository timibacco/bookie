package core.repository;


import core.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface booksRepo extends JpaRepository<Book, Long>{


    Object findByTitle(String title);

    Object findByAuthor(String author);

    Object findByISBN(String ISBN);
}
