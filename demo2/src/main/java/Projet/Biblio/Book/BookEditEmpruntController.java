package Projet.Biblio.Book;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Emprunt to edit details of a Book.
 */
public class BookEditEmpruntController {

    @FXML
    private TextField TitleField;
    @FXML
    private TextField AutorField;
    @FXML
    private TextField DateField;

    private Stage EmpruntStage;
    private Book Book;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this Emprunt.
     */
    public void setEmpruntStage(Stage EmpruntStage) {
        this.EmpruntStage = EmpruntStage;
    }

    /**
     * Sets the Book to be edited in the Emprunt.
     */
    public void setBook(Book Book) {
        this.Book = Book;

        TitleField.setText(Book.getTitle());
        AutorField.setText(Book.getAutor());
        DateField.setText(Book.getDate());
        DateField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Book.setTitle(TitleField.getText());
            Book.setAutor(AutorField.getText());
            Book.setDate(DateField.getText());

            okClicked = true;
            EmpruntStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        EmpruntStage.close();
    }

    /**
     * Validates the user input in the text fields.
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (TitleField.getText() == null || TitleField.getText().length() == 0) {
            errorMessage += "No valid Title!\n";
        }
        if (AutorField.getText() == null || AutorField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }

        if (DateField.getText() == null || DateField.getText().length() == 0) {
            errorMessage += "No valid Date!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(EmpruntStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}