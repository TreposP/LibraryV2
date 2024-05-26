package Projet.Biblio.Book;

import Projet.Biblio.Util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Emprunt to edit details of a Book.
 *
 * @author Marco Jakob
 */
public class BookEditEmpruntController {

    @FXML
    private TextField TitleField;
    @FXML
    private TextField AutorField;
    @FXML
    private TextField AvailableField;
    @FXML
    private TextField ISBNField;

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
     *
     * @param EmpruntStage
     */
    public void setEmpruntStage(Stage EmpruntStage) {
        this.EmpruntStage = EmpruntStage;
    }

    /**
     * Sets the Book to be edited in the Emprunt.
     *
     * @param Book
     */
    public void setBook(Book Book) {
        this.Book = Book;

        TitleField.setText(Book.getTitle());
        AutorField.setText(Book.getAutor());
        AvailableField.setText(Book.getAvailable());
        ISBNField.setText(Integer.toString(Book.getISBN()));
        DateField.setText(DateUtil.format(Book.getDate()));
        DateField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
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
            Book.setAvailable(AvailableField.getText());
            Book.setISBN(Integer.parseInt(ISBNField.getText()));
            Book.setDate(DateUtil.parse(DateField.getText()));

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
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (TitleField.getText() == null || TitleField.getText().length() == 0) {
            errorMessage += "No valid Title!\n";
        }
        if (AutorField.getText() == null || AutorField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (AvailableField.getText() == null || AvailableField.getText().length() == 0) {
            errorMessage += "No valid Available!\n";
        }

        if (ISBNField.getText() == null || ISBNField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(ISBNField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (DateField.getText() == null || DateField.getText().length() == 0) {
            errorMessage += "No valid Date!\n";
        } else {
            if (!DateUtil.validDate(DateField.getText())) {
                errorMessage += "No valid Date. Use the format dd.mm.yyyy!\n";
            }
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