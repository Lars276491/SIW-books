package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Review;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long>{

   List<Review> findByBook(Book book);
    
}
