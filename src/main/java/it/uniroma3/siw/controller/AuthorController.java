package it.uniroma3.siw.controller;

import java.util.Optional;

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
        model.addAttribute("authors", authorService.findAll());
        return "authors";
    }

    @GetMapping("/author/{id}")
    public String getAuthor(@PathVariable Long id, Model model) {
        Optional<Author> optionalAuthor = authorService.findById(id);
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

    @PostMapping("/author")
    public String newAuthor(@Valid @ModelAttribute Author author, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("messaggioErroreTitolo", "Campo obbligatorio");
            return "formNewAuthor";
        }else{
            this.authorService.save(author);
            model.addAttribute("author", author);
            return "redirect:/author" + author.getId();
        }
    }
    


}
