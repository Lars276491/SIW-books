package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.AuthorService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BookController {

    @Autowired BookService bookService;
    @Autowired AuthorService authorService;

    @GetMapping("/formNewBook")
    public String getFormNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "formNewBook.html";
    }

    @GetMapping("/book")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books.html";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));
        return "book.html";
    }

    @PostMapping("/book")
    public String newBook(@Valid @ModelAttribute("book") Book book,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewBook.html";
        }
        else{
            this.bookService.save(book);
            model.addAttribute("book", book);
            return "redirect:/book" + book.getId();
        }
    }

    @GetMapping("/aggiornaBook")
    public String homeAggiornaBook(Model model) {
        model.addAttribute("book", this.bookService.getAllBooks());
        return "aggiornaBook.html";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        this.bookService.deleteById(id);
        return "redirect:/aggiornaBook";
    }
    
    @GetMapping("/modificaBook/{id}")
    public String modificaBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));
        return "modificaBook.html";
    }

    @GetMapping("/AutorePerLibro/{id}")
    public String aggiungiAutorePerLibro(@PathVariable("id") Long id, Model model) {
        Book book = this.bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("autoriLibro", book.getAuthors());
        List<Author> liberi = (List<Author>) this.authorService.getAllAuthors();
        liberi.removeAll(book.getAuthors());
        model.addAttribute("autoriLiberi", liberi);
        return "autorePerLibro.html";
    }

    @GetMapping("/aggiungiAutore/{bookId}/{authorId}")
    public String aggiungiAutore(@PathVariable("bookId") Long bookId, @PathVariable("authorId") Long authorId, Model model) {
        this.bookService.addAuthorToBook(bookId, authorId);
        Book book = this.bookService.getBookById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("autoriLibro", book.getAuthors());
        List<Author> liberi = (List<Author>) this.authorService.getAllAuthors();
        liberi.removeAll(book.getAuthors());
        model.addAttribute("autoriLiberi", liberi);
        return "autorePerLibro.html";
    }
    
    
    
}
