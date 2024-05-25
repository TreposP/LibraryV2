package com.example;

public class Book {

    private String isbn;

    // Constructeur par défaut
    public Book() {
        // Constructeur vide
    }

    // Constructeur avec tous les champs
    public Book(String isbn) {
        this.isbn = isbn;
    }

    // Getters and setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{ isbn='" + isbn + '\'' +
                '}';
    }
}
