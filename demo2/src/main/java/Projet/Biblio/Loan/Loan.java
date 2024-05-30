package Projet.Biblio.Loan;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a loan entity.
 * This class encapsulates the properties and behavior of a loan.
 */
public class Loan {

    private final IntegerProperty idLoan;
    private final IntegerProperty idUser;
    private final StringProperty title;
    private final StringProperty autor;
    private final StringProperty isLate;
    private final ObjectProperty<LocalDate> dateLoan;
    private final ObjectProperty<LocalDate> dateReturnLoan;
    private final ObjectProperty<LocalDate> realDateReturnLoan;


    /**
     * Default constructor.
     * Initializes the loan with null values.
     */
    public Loan() { this(0, 0, null, null, null, null, null,null);}

    /**
     * Constructor with initial data.
     * Initializes the loan with the provided data.
     * @param idLoan The ID of the loan
     * @param idUser The ID of the user who borrowed the book
     * @param title The title of the book
     * @param auteur The author of the book
     * @param dateLoan The date when the book was borrowed
     * @param dateReturnLoan The expected return date of the book
     * @param realDateReturnLoan The actual return date of the book
     */
    public Loan(int idLoan, int idUser, String title, String auteur, LocalDate dateLoan, LocalDate dateReturnLoan, LocalDate realDateReturnLoan, String isLate) {
        this.idLoan = new SimpleIntegerProperty(idLoan);
        this.idUser = new SimpleIntegerProperty(idUser);
        this.title = new SimpleStringProperty(title);
        this.autor = new SimpleStringProperty(auteur);
        this.dateLoan = new SimpleObjectProperty<>(dateLoan);
        this.dateReturnLoan = new SimpleObjectProperty<>(dateReturnLoan);
        this.realDateReturnLoan = new SimpleObjectProperty<>(realDateReturnLoan);
        this.isLate = new SimpleStringProperty(isLate);
    }

    public int getIdLoan() {
        return idLoan.get();
    }
    public void setIdLoan(int idLoan) {
        this.idLoan.set(idLoan);
    }
    public IntegerProperty idLoanProperty() {
        return idLoan;
    }

    public int getIdUser() {
        return idUser.get();
    }
    public void setIdUser(int idUser) {
        this.idUser.set(idUser);
    }
    public IntegerProperty idUserProperty() {
        return idUser;
    }

    public String getTitle() {return title.get();}
    public void setTitle(String title) {this.title.set(title);}
    public StringProperty titleProperty() {return title;}

    public String getAutor() {return autor.get();}
    public void setAutor(String auteur) {
        this.autor.set(auteur);
    }
    public StringProperty auteurProperty() {
        return autor;
    }

    public LocalDate getDateLoan() {
        return dateLoan.get();
    }
    public void setDateLoan(LocalDate dateLoan) {
        this.dateLoan.set(dateLoan);
    }
    public ObjectProperty<LocalDate> dateLoanProperty() {
        return dateLoan;
    }

    public LocalDate getDateReturnLoan() {
        return dateReturnLoan.get();
    }
    public void setDateReturnLoan(LocalDate dateReturnLoan) {
        this.dateReturnLoan.set(dateReturnLoan);
    }
    public ObjectProperty<LocalDate> dateReturnLoanProperty() {
        return dateReturnLoan;
    }

    public LocalDate getRealDateReturnLoan() {
        return realDateReturnLoan.get();
    }
    public void setRealDateReturnLoan(LocalDate realDateReturnLoan) {
        this.realDateReturnLoan.set(realDateReturnLoan);
    }
    public ObjectProperty<LocalDate> realDateReturnLoanProperty() {
        return realDateReturnLoan;
    }

    public String getIsLate() {return isLate.get();}
    public void setIsLate(String title) {this.isLate.set(title);}
    public StringProperty isLateProperty() {return isLate;}

    @Override
    public String toString() {
        return "Loan{" +
                "idLoan=" + idLoan.get() +
                ", idUser=" + idUser.get() +
                ", title='" + title.get() + '\'' +
                ", auteur='" + autor.get() + '\'' +
                ", dateLoan=" + dateLoan.get() +
                ", dateReturnLoan=" + dateReturnLoan.get() +
                ", realDateReturnLoan=" + realDateReturnLoan.get() +
                ", isLate='" + isLate.get() + '\'' +
                '}';
    }
}
