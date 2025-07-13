package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Book;
import jakarta.transaction.Transactional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO book_author VALUES (:bookId, :authorId)", nativeQuery = true)
    public void addAuthorToBook(Long bookId, Long authorId);

}
