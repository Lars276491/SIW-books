package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.controller.validator.BookValidator;
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
    @Autowired BookValidator bookValidator;

    @GetMapping(value="/admin/formNewBook")
    public String getFormNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "admin/formNewBook";
    }

    @GetMapping("/book")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", this.bookService.findById(id));
        return "book";
    }

    @PostMapping("/book")
    public String newBook(@Valid @ModelAttribute Book book,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewBook";
        }
        else{
            this.bookService.save(book);
            model.addAttribute("book", book);
            return "redirect:/book" + book.getId();
        }
    }
    @PostMapping("/admin/indexBook")
    public String indexMovie(){
        return "admin/indexBook";
    }

    @GetMapping("/admin/aggiornaBook")
    public String homeAggiornaBook(Model model) {
        model.addAttribute("book", this.bookService.findAll());
        return "admin/aggiornaBook";
    }

    @GetMapping("/admin/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        this.bookService.deleteById(id);
        return "redirect:/aggiornaBook";
    }
    
    @GetMapping(value="/admin/modificaBook/{id}")
    public String modificaBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", this.bookService.findById(id));
        return "admin/modificaBook";
    }

    @GetMapping("/admin/modificaAutori/{id}")
    public String modificaAutori(@PathVariable Long id, Model model) {
        List<Author> authors = this.authorsToAdd(id);
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("autoriLibro", authors);
        return "admin/autorePerLibro";
    }
    

    @GetMapping("/admin/AutorePerLibro/{authorId}/{bookid}")
    public String aggiungiAutorePerLibro(@PathVariable("actorId") Long authorId,@PathVariable Long bookId, Model model) {
        Book book = this.bookService.findById(bookId);
        Author author = this.authorService.findById(authorId);
        List<Author> authors = book.getAuthors();
        authors.add(author);
        this.bookService.save(book);

        List<Author> authorsToAdd = authorsToAdd(bookId);
        model.addAttribute("book", book);
        model.addAttribute("autoriLibro", authorsToAdd);
        return "admin/autorePerLibro";
    }

    private List<Author> authorsToAdd(Long bookId) {
        List<Author> authors = new ArrayList<>();

        for (Author author : this.authorService.findAuthorsNotInBook(bookId)) {
            authors.add(author);
        }
        return authors;
    }

    @GetMapping("/admin/deleteAutorePerLibro/{authorId}/{bookId}")
    public String deleteAutorePerLibro(@PathVariable Long authorId, @PathVariable Long bookId, Model model) {
        Book book = this.bookService.findById(bookId);
        Author author = this.authorService.findById(authorId);
        List<Author> authors = book.getAuthors();
        authors.remove(author);
        this.bookService.save(book);

        List<Author> authorsToAdd = authorsToAdd(bookId);
        model.addAttribute("book", book);
        model.addAttribute("autoriLibro", authorsToAdd);
        return "admin/autorePerLibro";
    }

    
    
}
