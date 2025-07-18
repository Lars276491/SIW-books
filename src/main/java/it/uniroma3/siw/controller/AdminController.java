package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ReviewService reviewService;

    /***********************************************************************/
    /**********************METODI PER BOOK**********************************/
    /***********************************************************************/
     @GetMapping("/formNewBook")
    public String getFormNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "admin/formNewBook";
    }

    @GetMapping("/book")
    public String showAdminBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "admin/adminBooks";
    }

    @GetMapping("/aggiornaBook")
    public String homeAggiornaBook(Model model) {
        model.addAttribute("book", this.bookService.findAll());
        return "admin/aggiornaBook";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        Optional<Book> optionalBook = this.bookService.findById(id);
        if (!optionalBook.isPresent()) {
            model.addAttribute("errorMessage", "Libro non trovato.");
            return "redirect:/admin/book";
        }
        Book book = optionalBook.get();

        // Cancella i file delle immagini fisiche
        if (book.getImages() != null) {
            for (Image img : book.getImages()) {
                String path = img.getPath();  // o come si chiama la proprietà
                java.io.File file = new java.io.File("C:/Users/laura/Documents/Coding/SIW/SIW-books/uploads" + path); //DA RIVEDERE
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        // Cancella il libro dal database
        this.bookService.deleteById(id);

        return "redirect:/admin/book";
    }

    @GetMapping("/modificaBook/{id}")
    public String modificaBook(@PathVariable Long id, Model model) {
        Optional<Book> optionalBook = this.bookService.findById(id);
        if (!optionalBook.isPresent()) {
            model.addAttribute("errorMessage", "Libro non trovato.");
            return "redirect:/admin/book";
        }
        model.addAttribute("book", optionalBook.get());
        return "admin/modificaBook";
       // model.addAttribute("book", this.bookService.findById(id));
       // return "admin/modificaBook";
    }

    @GetMapping("/modificaAutori/{id}")
    public String modificaAutori(@PathVariable Long id, Model model) {
        List<Author> authors = this.authorsToAdd(id);
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("autoriLibro", authors);
        return "admin/autorePerLibro";
    }
    
    /*@GetMapping("/AutorePerLibro/{authorId}/{bookId}")
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
    }*/

    private List<Author> authorsToAdd(Long bookId) {
        List<Author> authors = new ArrayList<>();

        for (Author author : this.authorService.findAuthorsNotInBook(bookId)) {
            authors.add(author);
        }
        return authors;
    }

   /* @GetMapping("/deleteAutorePerLibro/{authorId}/{bookId}")
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
    }*/

   
    @PostMapping("/book/{id}")
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
    @PostMapping("/book")
    public String addBook(@ModelAttribute("book") Book book,
                        @RequestParam("bookImages") List<MultipartFile> images,
                        BindingResult result,
                        Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/formNewBook";
        }

        // gestione immagini (se necessario) ...

        this.bookService.saveWithImages(book, images);
        return "redirect:/admin/book";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        Optional<Book> optionalBook = bookService.findById(id);
        if (!optionalBook.isPresent()) {
            model.addAttribute("errorMessage", "Libro non trovato.");
            return "redirect:/admin/book"; // o pagina 404
        }
        Book book = optionalBook.get();
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewService.findByBook(book));
        return "admin/adminBook";
    }

    @GetMapping("/deleteReview/{id}")
    public String deleteReview(@PathVariable Long id, Model model) {
        Optional<Review> optionalReview = reviewService.findById(id);

        if (!optionalReview.isPresent()) {
            model.addAttribute("errorMessage", "Recensione non trovata.");
            return "redirect:/admin/book";
        }

        Review review = optionalReview.get();
        Long bookId = review.getBook().getId(); // utile per redirect alla pagina del libro

        reviewService.deleteById(id);
        return "redirect:/admin/book/" + bookId;
    }

    /***********************************************************************/
    /**********************METODI PER AUTHOR********************************/
    /***********************************************************************/
    @GetMapping("/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor";
    }

    @GetMapping("/author")
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "admin/adminAuthors";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Optional<Author> optionalAuthor = authorService.findById(id);
        if (!optionalAuthor.isPresent()) {
            model.addAttribute("errorMessage", "Autore non trovato.");
            return "redirect:/admin/author"; // o pagina 404
        }
        Author author = optionalAuthor.get();
        model.addAttribute("author", author);
        return "admin/adminAuthor";
    }

    @PostMapping("/author")
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

    @PostMapping("/author/{id}")
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

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable Long id, Model model) {
        this.authorService.deleteById(id);
        return "redirect:/admin/author";
    }

    @GetMapping("/modificaAuthor/{id}")
    public String modificaAuthor(@PathVariable Long id, Model model) {
        Optional<Author> optionalAuthor = this.authorService.findById(id);
        if (!optionalAuthor.isPresent()) {
            model.addAttribute("errorMessage", "Autore non trovato.");
            return "redirect:/admin/author";
        }
        model.addAttribute("author", optionalAuthor.get());
        return "admin/modificaAuthor";
    }



}
