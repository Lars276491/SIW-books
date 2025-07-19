package it.uniroma3.siw.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService {

    @Autowired  
    private BookRepository bookRepository;
    @Autowired
    private ImageStorageService imageStorageService;

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

    @Transactional
    public Book saveWithImages(Book book, List<MultipartFile> images) throws IOException {
        book = this.bookRepository.save(book); // salva inizialmente per avere l'ID

        // Inizializza lista immagini se null
        List<Image> imgs = book.getImages();
        if (imgs == null)
            imgs = new ArrayList<>();

        // Se arrivano immagini
        if (images != null && !images.isEmpty()) {
            for (MultipartFile f : images) {
                if (!f.isEmpty()) {
                    String path = this.imageStorageService.store(f, "book/" + book.getId());

                    Image image = new Image();
                    image.setPath(path);
                    image.setBook(book);
                    imgs.add(image);
                }
            }
        }

        book.setImages(imgs); // aggiorna lista (anche se vuota)
        return this.bookRepository.save(book); // aggiorna book + immagini (se in cascade)
    }




    
}
