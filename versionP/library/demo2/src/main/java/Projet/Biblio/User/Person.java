package Projet.Biblio.User;

import java.time.LocalDate;

import javafx.beans.property.*;

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


    public Person(int id, String lastName, String firstName, String address, String phone, Object birthday,int nbremprunt) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.birthday = new SimpleObjectProperty<LocalDate>((LocalDate) birthday);
        this.nbremprunt =new SimpleIntegerProperty(nbremprunt);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial  data, just for convenient testing.
        this.nbremprunt = new SimpleIntegerProperty(0);
        this.livrenonrendu = new SimpleStringProperty("non");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(2024, 5, 26));

    }

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null);
    }

    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public IntegerProperty IdProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getAddress() {
        return address.get();
    }
    public void setAddress(String address) {
        this.address.set(address);
    }
    public StringProperty firstAddress() {
        return address;
    }

    public String getPhone() {
        return phone.get();
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public StringProperty phoneProperty() {
        return phone;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public int getnbremprunt() {
        return nbremprunt.get();
    }
    public void setnbremprunt(int nbremprunt) {
        this.nbremprunt.set(nbremprunt);
    }
    public IntegerProperty nbrempruntProperty() {
        return nbremprunt;
    }

    public String getlivrenonrendu() {
        return livrenonrendu.get();
    }
    public void setlivrenonrendu(String livrenonrendu) {
        this.livrenonrendu.set(livrenonrendu);
    }
    public StringProperty livrenonrenduProperty() {
        return livrenonrendu;
    }

}