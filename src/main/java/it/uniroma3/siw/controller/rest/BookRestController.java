package it.uniroma3.siw.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

@RestController
public class BookRestController {

	@Autowired 
	private BookService bookService;
	
	@Autowired 
	private AuthorService authorService;
	
	@PutMapping(value="/admin/rest/books/{bookId}/authors/{AuthorId}/")
	public Book setAuthorToBook(@PathVariable("bookId") Long bookId,
										@PathVariable("authorId") Long authorId) {
		
		Author author = this.authorService.findById(authorId);
		Book book = this.bookService.findById(bookId);
        List<Author> authors = book.getAuthors();
		authors.add(author);
		this.bookService.save(book);
		return book;
	}

	@PostMapping("/admin/rest/books")
	public Book newBook(@Valid @RequestBody Book book) {
		this.bookService.save(book); 
		return book;
	}

	@GetMapping("/rest/books/{id}")
	public Book getBook(@PathVariable("id") Long id) {
		return this.bookService.findById(id);
	}

	@GetMapping("/rest/books")
	public List<Book> getBooks() {	
		List<Book> books = new ArrayList<>();
		for(Book m : this.bookService.findAll())
			books.add(m);
		return books;
	}
	
	@DeleteMapping(value="/admin/rest/books/{bookId}/authors/{authorId}")
	public Book removeAuthorFromBook(@PathVariable("bookId") Long bookId, @PathVariable("authorId") Long authorId) {
		Book book = this.bookService.findById(bookId);
		Author author = this.authorService.findById(authorId);
		List<Author> authors = book.getAuthors();
		authors.remove(author);
		this.bookService.save(book);
		return book;
	}
}
