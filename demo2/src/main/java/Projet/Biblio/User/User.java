package Projet.Biblio.User;

import java.time.LocalDate;

import javafx.beans.property.*;

/**
 * Model class for a Person.
 * This class represents a person with properties such as ID, first name, last name, address, phone number,
 * number of loans, and unreturned book status.
 */
import javafx.beans.property.*;

import java.time.LocalDate;

public class User {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty address;
    private StringProperty phone;
    private IntegerProperty nbremprunt;
    private StringProperty livrenonrendu;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.nbremprunt = new SimpleIntegerProperty();
        this.livrenonrendu = new SimpleStringProperty();
    }

    /**
     * Constructor with some initial data.
     *
     * @param id the ID of the user.
     * @param firstName the first name of the user.
     * @param lastName the last name of the user.
     */
    public User(int id, String firstName, String lastName) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty("some address");
        this.phone = new SimpleStringProperty("some phone");
        this.nbremprunt = new SimpleIntegerProperty(0);
        this.livrenonrendu = new SimpleStringProperty("none");
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public int getNbremprunt() {
        return nbremprunt.get();
    }

    public String getLivrenonrendu() {
        return livrenonrendu.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setNbremprunt(int nbremprunt) {
        this.nbremprunt.set(nbremprunt);
    }

    public void setLivrenonrendu(String livrenonrendu) {
        this.livrenonrendu.set(livrenonrendu);
    }

    // Property methods
    public IntegerProperty idProperty() {return id;}

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public IntegerProperty nbrempruntProperty() {
        return nbremprunt;
    }

    public StringProperty livrenonrenduProperty() {
        return livrenonrendu;
    }
}
