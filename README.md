# 📚 SIW-Books

**SIW-Books** è un'applicazione web realizzata come progetto accademico per il corso di **Sviluppo di Applicazioni Web (SIW)** presso l'Università degli Studi di Roma Tre.

L'applicazione permette agli utenti di:

- 🧐 Consultare un catalogo di libri e autori
- 📝 Lasciare una singola recensione per ciascun libro
- 🔐 Accedere tramite autenticazione utente
- 👤 Visualizzare i dettagli di ciascun autore o libro

---

## 🚀 Tecnologie utilizzate

- **Java 17**
- **Spring Boot**
- **Spring MVC + Thymeleaf**
- **Spring Data JPA + Hibernate**
- **Spring Security**
- **PostgreSQL**
- **HTML + CSS (Thymeleaf templating)**

---

## ⚙️ Funzionalità principali

### Utente anonimo:
- ✅ Visualizza l'elenco di tutti i libri disponibili
- ✅ Consulta le informazioni dettagliate di ciascun libro e autore

### Utente autenticato:
- 📝 Aggiunge una recensione a un libro (massimo **una** per libro)
- 👁️ Visualizza le proprie recensioni
- 📚 Naviga tra autori e i relativi libri scritti

---

## 🔐 Autenticazione

L'autenticazione è gestita con **Spring Security**. Ogni utente può registrarsi, accedere e lasciare una recensione. Le credenziali sono collegate a un'entità `User`.

---

## 🧩 Modello di Dominio
```mermaid
classDiagram
    class SIW-Books{

    }

    class Portale{

    }

    class UtenteOccasionale{

    }

    class User{
        id
        nome
        cognome
        email
    }

    class UtenteRegistrato{

    }
    class Amministratore{

    }

    class Credentials{
        id
        username
        password
        role
    }

    class Libro{
        id
        titolo 
        annoPubblicazione
        descrizione
    }

    class Autore{
        id
        nome
        cognome
        nazionalità
        dataNascita
        dataMorte
    }

    class Recensione{
        id
        titolo
        testo
        voto
    }

    class Immagine
    
    SIW-Books "1" -- "*" Portale : offre
    User "1" -- "1" Credentials
    UtenteOccasionale "0..1" -- "0..1" Portale : staUsando
    Portale "0..1" -- "0..1" User : staUsando
    UtenteRegistrato "1" -- "*" Recensione : ha scritto
    Libro "*" -- "1" Autore 
    Libro "1" -- "*" Recensione
    Amministratore "*" -- "*" Libro : haAggiunto
    Amministratore "*" -- "*" Libro : haModificato
    Amministratore "*" -- "*" Autore : haAggiunto
    Amministratore "*" -- "*" Autore : haModificato
    Immagine "*" -- "1" Libro
    Immagine "1" -- "1" Autore
    Amministratore --|> User
    UtenteRegistrato --|> User
```