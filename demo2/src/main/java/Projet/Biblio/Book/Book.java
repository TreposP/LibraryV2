package Projet.Biblio.Book;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Represents a book entity.
 * This class encapsulates the properties and behavior of a book.
 */
public class Book {

    // Properties of the book
    private final StringProperty Title;
    private final StringProperty Autor;
    private final StringProperty Date;

    /**
     * Constructor with initial data.
     * Initializes the book with the provided title and author.*/

    public Book(String Title, String Autor, String Date) {
        // Initialize properties with provided data or dummy values for testing
        this.Title = new SimpleStringProperty(Title);
        this.Autor = new SimpleStringProperty(Autor);
        this.Date = new SimpleStringProperty(Date);
    }

    // Getters and setters for each property

    public String getTitle() {
        return Title.get();
    }
    public void setTitle(String Title) {
        this.Title.set(Title);
    }
    public StringProperty TitleProperty() {
        return Title;
    }

    public String getAutor() {
        return Autor.get();
    }
    public void setAutor(String Autor) {
        this.Autor.set(Autor);
    }
    public StringProperty AutorProperty() {
        return Autor;
    }

    public String getDate() {return Date.get();}
    public void setDate(String Date) {
        this.Date.set(Date);
    }
    public StringProperty DateProperty() {
        return Date;
    }
}
