package Projet.Biblio.User;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a user.
 * This class provides the interaction logic for the user edit dialog.
 *
 * @version 1.0
 */
public class UserEditDialogController {

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
    private TextField nbrempruntField;
    @FXML
    private TextField livrenonrenduField;

    /** The dialog stage. */
    private Stage dialogStage;

    /** The user being edited. */
    private User user;

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
     * Sets the user to be edited in the dialog.
     *
     * @param user the user to be edited
     */
    public void setUser(User user) {
        this.user = user;
        IdField.setText(Integer.toString(user.getId()));
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        nbrempruntField.setText(Integer.toString(user.getNbremprunt()));
        livrenonrenduField.setText(user.getLivrenonrendu());
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
     * Validates the input and, if valid, updates the user object and closes the dialog.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setAddress(addressField.getText());
            user.setPhone(phoneField.getText());
            user.setNbremprunt(Integer.parseInt(nbrempruntField.getText()));
            user.setLivrenonrendu(livrenonrenduField.getText());
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
