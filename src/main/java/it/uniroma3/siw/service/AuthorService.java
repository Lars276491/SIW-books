package it.uniroma3.siw.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
    
    public Iterable<Author> findAuthorsNotInBook(Long bookId) {
        return authorRepository.findAuthorsNotInBook(bookId);
    }
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public String saveAuthorImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null; // or handle the error as needed
        }
        File uploadDir = new File("uploads/author/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create the directory if it doesn't exist
        }
        String fileName = file.getOriginalFilename();

        File dest = new File(uploadDir, fileName);
        file.transferTo(dest); // Save the file to the destination
        return  "uploads/author/" + fileName; // Return the path or filename as needed
    }
    
}
