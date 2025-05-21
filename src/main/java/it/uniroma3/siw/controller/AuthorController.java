package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class AuthorController {

    @Autowired AuthorService authorService;

    @GetMapping("/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("athor", new Author());
        return "formNewAuthor.html";
    }

    @GetMapping("/author")
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors.html";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("author", this.authorService.getAuthorById(id));
        return "author.html";
    }



}
