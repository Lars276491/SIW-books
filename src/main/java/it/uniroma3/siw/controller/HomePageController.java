package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;



@Controller
public class HomePageController {

    @Autowired BookService bookService;
    @Autowired AuthorService authorService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", this.bookService.getAllBooks());
        model.addAttribute("authors", this.authorService.getAllAuthors());
        return "index.html";
    }
    
}   
