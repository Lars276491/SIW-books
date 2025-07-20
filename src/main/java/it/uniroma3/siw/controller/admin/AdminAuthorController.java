package it.uniroma3.siw.controller.admin;

import java.io.IOException;
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

import it.uniroma3.siw.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminAuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/author")
    public String showAdminAuthors(Model model) {
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

    @GetMapping("/formNewAuthor")
    public String getFormNewAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin/formNewAuthor";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable Long id, Model model) {
        Optional<Author> authorOpt = authorService.findById(id);
        if (!authorOpt.isPresent()) {
            model.addAttribute("errorMessage", "Autore non trovato.");
            return "redirect:/admin/author"; // o pagina 404
        }
        Author author = authorOpt.get();
        
        if(author.getImage() != null) {
            String imagePath = author.getImage().getPath();
            java.io.File imageFile = new java.io.File("." + imagePath); // Assicurati che il path sia corretto
            if (imageFile.exists()) {
                imageFile.delete(); // Cancella il file immagine fisico
            }
        }
        // Cancella l'autore dal database
        this.authorService.deleteById(id);

        return "redirect:/admin/author";
    }


    @PostMapping("/author")
    public String addAuthor(@Valid@ModelAttribute("author") Author author,
                            BindingResult result,
                            @RequestParam("authorImages") MultipartFile file,
                            Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/formNewAuthor";
        }

        // Usa il servizio per salvare autore e immagine
        this.authorService.saveWithImage(author, file);

        return "redirect:/admin/author";
    }


    @PostMapping("/author/{id}")
    public String updateAuthor(@PathVariable Long id,
                            @Valid@ModelAttribute("author") Author author,
                            BindingResult result,
                            @RequestParam(value = "authorImages", required = false) MultipartFile file,
                            Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/modificaAuthor";
        }

        // Opzionale: setta l'ID per sicurezza
        author.setId(id);
        author.setImage(null); // evita errori di binding su image

        // Usa il metodo del servizio per gestire autore e immagine
        this.authorService.saveWithImage(author, file);
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
