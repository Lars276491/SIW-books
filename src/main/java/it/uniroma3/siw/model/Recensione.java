package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recensione {
    @Id
    @GeneratedValue
    private Long id;

     private String contenuto;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
