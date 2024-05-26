package Projet.Biblio.Book;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Book {

    private final StringProperty Title;
    private final StringProperty Autor;
    private final ObjectProperty<LocalDate> Date;
    private final IntegerProperty ISBN;
    private final StringProperty Available;


    /**
     * Default constructor.
     */
    public Book() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param Title
     * @param Autor
     */
    public Book(String Title, String Autor) {
        this.Title = new SimpleStringProperty(Title);
        this.Autor = new SimpleStringProperty(Autor);

        // Some initial dummy data, just for convenient testing.
        this.Available = new SimpleStringProperty("some Available");
        this.ISBN = new SimpleIntegerProperty(1234);
        this.Date = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

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

    public String getAvailable() {
        return Available.get();
    }

    public void setAvailable(String Available) {
        this.Available.set(Available);
    }

    public StringProperty AvailableProperty() {
        return Available;
    }

    public int getISBN() {
        return ISBN.get();
    }

    public void setISBN(int ISBN) {
        this.ISBN.set(ISBN);
    }

    public IntegerProperty ISBNProperty() {
        return ISBN;
    }

    public LocalDate getDate() {
        return Date.get();
    }

    public void setDate(LocalDate Date) {
        this.Date.set(Date);
    }

    public ObjectProperty<LocalDate> DateProperty() {
        return Date;
    }
}