package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class AuthorController {

    @Autowired AuthorService authorService;


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

    @GetMapping("/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("athor", new Author());
        return "formNewAuthor.html";
    }

    @PostMapping("/author")
    public String newAuthor(@Valid @ModelAttribute("author") Author author, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewAuthor.html";
        }else{
            this.authorService.save(author);
            model.addAttribute("author", author);
            return "redirect:/author" + author.getId();
        }
    }
    


}
