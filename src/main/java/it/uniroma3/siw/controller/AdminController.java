package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/indexAdmin")
    public String adminHome() {
        return "admin/indexAdmin";  // Thymeleaf template: src/main/resources/templates/admin/indexAdmin.html
    }

    //metodi per book
    /********************/
     @GetMapping("/admin/formNewBook")
    public String getFormNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "admin/formNewBook";
    }

    @GetMapping("/admin/book")
    public String showAdminBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "admin/adminBooks";
    }

    @GetMapping("/admin/aggiornaBook")
    public String homeAggiornaBook(Model model) {
        model.addAttribute("book", this.bookService.findAll());
        return "admin/aggiornaBook";
    }

    @GetMapping("/admin/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        this.bookService.deleteById(id);
        return "redirect:/admin/book";
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
    
    @GetMapping("/admin/AutorePerLibro/{authorId}/{bookId}")
    public String aggiungiAutorePerLibro(@PathVariable("authorId") Long authorId,@PathVariable Long bookId, Model model) {
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

   
    @PostMapping("/admin/book/{id}")
    public String updateBook(@PathVariable Long id,
                            @ModelAttribute("book") Book book,
                            @RequestParam("bookImages") MultipartFile[] files,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "admin/modificaBook";
        }

        // Opzionale: setta l'ID per sicurezza
        book.setId(id);

        // gestione immagini qui...

        this.bookService.save(book);
        return "redirect:/admin/book";
    }
    @PostMapping("/admin/book")
    public String addBook(@ModelAttribute("book") Book book,
                        @RequestParam("bookImages") MultipartFile[] files,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            return "admin/formNewBook";
        }

        // gestione immagini (se necessario) ...

        this.bookService.save(book);
        return "redirect:/admin/book";
    }

    @GetMapping("/admin/book/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", this.bookService.findById(id));
        return "admin/adminBook";
    }


     //metodi per author
     /********************/
    @GetMapping("/admin/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor";
    }

    @GetMapping("/admin/author")
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "admin/adminAuthors";
    }

    @GetMapping("/admin/author/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", this.authorService.findById(id));
        return "admin/adminAuthor";
    }

    @PostMapping("/admin/author")
    public String addAuthor(@ModelAttribute("author") Author author,
                        @RequestParam("authorImages") MultipartFile[] files,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            return "admin/formNewAuthor";
        }

        // gestione immagini (se necessario) ...

        this.authorService.save(author);
        return "redirect:/admin/author";
    }

    @PostMapping("/admin/author/{id}")
    public String updateAuthor(@PathVariable Long id,
                            @ModelAttribute("author") Author author,
                            @RequestParam("authorImages") MultipartFile[] files,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "admin/modificaAuthor";
        }

        // Opzionale: setta l'ID per sicurezza
        author.setId(id);

        // gestione immagini qui...

        this.authorService.save(author);
        return "redirect:/admin/author";
    }

    @GetMapping("/admin/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable Long id, Model model) {
        this.authorService.deleteById(id);
        return "redirect:/admin/author";
    }

    @GetMapping(value="/admin/modificaAuthor/{id}")
    public String modificaAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", this.authorService.findById(id));
        return "admin/modificaAuthor";
    }



}
