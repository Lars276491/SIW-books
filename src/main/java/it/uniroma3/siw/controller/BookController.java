package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.controller.validator.BookValidator;
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

    
    

    
    
}
