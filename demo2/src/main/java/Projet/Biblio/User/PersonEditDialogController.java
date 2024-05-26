package Projet.Biblio.User;

import Projet.Biblio.Util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a person.
 * This class provides the interaction logic for the person edit dialog.
 *
 * @version 1.0
 */
public class PersonEditDialogController {

    @FXML
    private TextField IdField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField nbrempruntField;
    @FXML
    private TextField livrenonrenduField;

    /** The dialog stage. */
    private Stage dialogStage;

    /** The person being edited. */
    private Person person;

    /** Indicates if the user clicked OK. */
    private boolean okClicked = false;

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // No initialization required
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person the person to be edited
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        nbrempruntField.setText(Integer.toString(person.getNbremprunt()));
        livrenonrenduField.setText(person.getLivrenonrendu());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return true if the user clicked OK, false otherwise
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks OK.
     * Validates the input and, if valid, updates the person object and closes the dialog.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setNbremprunt(Integer.parseInt(nbrempruntField.getText()));
            person.setLivrenonrendu(livrenonrenduField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks Cancel.
     * Closes the dialog without saving changes.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid, false otherwise
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().isEmpty()) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().isEmpty()) {
            errorMessage += "No valid last name!\n";
        }
        if (nbrempruntField.getText() == null || nbrempruntField.getText().isEmpty()) {
            errorMessage += "No valid loan number!\n";
        } else {
            // Try to parse the loan number into an integer.
            try {
                Integer.parseInt(nbrempruntField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid loan number (must be an integer)!\n";
            }
        }

        if (livrenonrenduField.getText() == null || livrenonrenduField.getText().isEmpty()) {
            errorMessage += "No valid book return status!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().isEmpty()) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
