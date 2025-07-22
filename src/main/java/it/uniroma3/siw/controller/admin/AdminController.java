package it.uniroma3.siw.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.model.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import it.uniroma3.siw.service.*;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CredentialsService credentialsService;
    
    @GetMapping("/account")
    public String getAccount(Model model) {
        Credentials credentials = credentialsService.getCurrentCredentials();

        if (credentials == null) {
            return "admin/indexAdmin"; // oppure una pagina di errore più adatta
        }

        model.addAttribute("credentials", credentials);

        if(Credentials.ADMIN_ROLE.equals(credentials.getRole())) {
            List<Credentials> allCredentials = (List<Credentials>) credentialsService.findAll();
            model.addAttribute("allCredentials", allCredentials);
        }

        return "admin/adminAccount";
    }
    
    @GetMapping("/modificaAccount")
    public String getModificaAccount(Model model) {
        Credentials credentials = credentialsService.getCurrentCredentials();
        model.addAttribute("credentials", credentials);
        return "admin/adminModificaAccount";
    }
    

    @PostMapping("/account")
    public String updateAccount(@ModelAttribute("credentials") Credentials updatedCredentials, Model model) {
        Credentials current = credentialsService.getCurrentCredentials();

        if (current == null || !updatedCredentials.getId().equals(current.getId())) {
            model.addAttribute("error", "Accesso non autorizzato");
            return "redirect:/admin/account";
        }
        
        credentialsService.updateCredentials(updatedCredentials);
        // 🔄 Riautentica l'utente con le nuove credenziali
        credentialsService.autoLogin(updatedCredentials.getUsername(), updatedCredentials.getPassword());
        return "redirect:/admin/account";
    }
 
    
    //ottiene l'utente registrato 
    @GetMapping("/account/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        Credentials credentials = credentialsService.getCredentials(id);
        if (credentials != null) {
            model.addAttribute("credentials", credentials);
            return "admin/user";
        }
        return "redirect: /admin/account";
    }
    
    @PostMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable("id") Long id, Model model) {
        Credentials credentials = credentialsService.getCredentials(id);
        if (credentials != null) {
            credentialsService.deleteCredentials(id);
            return "redirect:/admin/account";
        }
        model.addAttribute("error", "Utente non trovato");
        return "error";
    }

}
