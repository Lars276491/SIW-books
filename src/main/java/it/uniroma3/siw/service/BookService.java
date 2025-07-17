package it.uniroma3.siw.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService {

    @Autowired  
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }    

    
    public void addAuthorToBook(Long bookId, Long authorId) {
        bookRepository.addAuthorToBook(bookId, authorId);
    }
    public boolean existsByTitleAndYear(String title, Integer year) {
        return bookRepository.existsByTitleAndYear(title, year);
    }
    public Iterable<Book> findByYear(int year) {
        return bookRepository.findByYear(year);
    }
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
