package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired BookService bookService;

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
    public String getBook(@RequestParam("id") Long id, Model model) {
        model.addAttribute("book", this.bookService.getBookById(id));
        return "book.html";
    }
    
    
}
