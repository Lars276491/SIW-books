package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AuthorController {

    @Autowired AuthorService authorService;
    @Autowired BookService bookService;


    @GetMapping("/author")
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Optional<Author> optionalAuthor = authorService.findByIdWithBooks(id);
        if (!optionalAuthor.isPresent()) {
            return "redirect:/author"; // o una pagina 404
        }
        Author author = optionalAuthor.get();
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "formNewAuthor";
    }

    /*@PostMapping("/author")
    public String newAuthor(
                            @Valid @ModelAttribute Author author,
                            BindingResult bindingResult,
                            @RequestParam(value = "bookIds", required = false) List<Long> bookIds,
                            Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewAuthor";
        } else {
            if (bookIds != null) {
                 List<Book> books = bookService.findAllById(bookIds);
                author.setBooks(books);
                // Aggiorna la relazione bidirezionale
                for (Book book : books) {
                    book.getAuthors().add(author);
                    bookService.save(book); // salva il libro aggiornato
                }   
            } else {
                author.setBooks(null);
            }
            this.authorService.save(author);
            model.addAttribute("author", author);
            return "redirect:/author" + author.getId();
        }
    }*/
    


}
