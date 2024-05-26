package Projet.Biblio.User;

import java.time.LocalDate;

import javafx.beans.property.*;

/**
 * Model class for a Person.
 * This class represents a person with properties such as ID, first name, last name, address, phone number, birthday,
 * number of loans, and unreturned book status.
 */
public class Person {

    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty address;
    private StringProperty phone;
    private ObjectProperty<LocalDate> birthday;
    private IntegerProperty nbremprunt;
    private StringProperty livrenonrendu;

    private final static String[] FIELD_NAMES = { "id", "firstName", "lastName", "nbremprunt", "livrenonrendu", "adresse", "mail", "phone" };

    /**
     * Constructs a Person with the specified details.
     *
     * @param id the ID of the person
     * @param lastName the last name of the person
     * @param firstName the first name of the person
     * @param address the address of the person
     * @param phone the phone number of the person
     * @param birthday the birthday of the person
     * @param nbremprunt the number of loans of the person
     */
    public Person(int id, String lastName, String firstName, String address, String phone, Object birthday, int nbremprunt) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.birthday = new SimpleObjectProperty<LocalDate>((LocalDate) birthday);
        this.nbremprunt = new SimpleIntegerProperty(nbremprunt);
    }

    /**
     * Constructs a Person with the specified first and last names.
     * Initializes the number of loans to 0, the unreturned book status to "non", and the birthday to a default date.
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial data, just for convenient testing.
        this.nbremprunt = new SimpleIntegerProperty(0);
        this.livrenonrendu = new SimpleStringProperty("non");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(2024, 5, 26));
    }

    /**
     * Default constructor.
     * Initializes the person with null values for first name and last name.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Gets the ID of the person.
     * @return the ID of the person
     */
    public int getId() {
        return id.get();
    }

    /**
     * Sets the ID of the person.
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Gets the ID property.
     * @return the ID property
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Gets the first name of the person.
     * @return the first name of the person
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets the first name of the person.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets the first name property.
     * @return the first name property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets the last name of the person.
     * @return the last name of the person
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets the last name of the person.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Gets the last name property.
     * @return the last name property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Gets the address of the person.
     * @return the address of the person
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Sets the address of the person.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Gets the address property.
     * @return the address property
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * Gets the phone number of the person.
     * @return the phone number of the person
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Sets the phone number of the person.
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * Gets the phone number property.
     * @return the phone number property
     */
    public StringProperty phoneProperty() {
        return phone;
    }

    /**
     * Gets the birthday of the person.
     * @return the birthday of the person
     */
    public LocalDate getBirthday() {
        return birthday.get();
    }

    /**
     * Sets the birthday of the person.
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    /**
     * Gets the birthday property.
     * @return the birthday property
     */
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    /**
     * Gets the number of loans of the person.
     * @return the number of loans of the person
     */
    public int getNbremprunt() {
        return nbremprunt.get();
    }

    /**
     * Sets the number of loans of the person.
     * @param nbremprunt the number of loans to set
     */
    public void setNbremprunt(int nbremprunt) {
        this.nbremprunt.set(nbremprunt);
    }

    /**
     * Gets the number of loans property.
     * @return the number of loans property
     */
    public IntegerProperty nbrempruntProperty() {
        return nbremprunt;
    }

    /**
     * Gets the unreturned book status of the person
     * @return the unreturned book status of the person
     */
    public String getLivrenonrendu() {
        return livrenonrendu.get();
    }

    /**
     * Sets the unreturned book status of the person.
     * @param livrenonrendu the unreturned book status to set
     */
    public void setLivrenonrendu(String livrenonrendu) {
        this.livrenonrendu.set(livrenonrendu);
    }

    /**
     * Gets the unreturned book status property.
     * @return the unreturned book status property
     */
    public StringProperty livrenonrenduProperty() {
        return livrenonrendu;
    }
}