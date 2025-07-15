package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    public boolean existsByNameAndSurname(String name, String surname);	

	@Query(value="select * "
			+ "from author a "
			+ "where a.id not in "
			+ "(select athors_id "
			+ "from book_authors "
			+ "where book_authors.starred_movies_id = :bookId)", nativeQuery=true)

    Iterable<Author> findAuthorsNotInBook(@Param("bookId")Long Id);
    

}
