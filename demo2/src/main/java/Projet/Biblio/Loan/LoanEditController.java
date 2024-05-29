package Projet.Biblio.Loan;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Dialog to edit details of a loan.
 * This class provides the interaction logic for the loan edit dialog.
 *
 * @version 1.0
 */
public class LoanEditController {

    @FXML
    private TextField idUserField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isLateField;
    @FXML
    private TextField dateLoanField;
    @FXML
    private TextField dateReturnLoanField;
    @FXML
    private TextField realDateReturnLoanField;

    /** The dialog stage. */
    private Stage loanStage;

    /** The loan being edited. */
    private Loan loan;

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
     * @param loanStage the dialog stage
     */
    public void setLoanStage(Stage loanStage) {
        this.loanStage = loanStage;
    }

    /**
     * Sets the loan to be edited in the dialog.
     *
     * @param loan the loan to be edited
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
        idUserField.setText(Integer.toString(loan.getIdUser()));
        titleField.setText(loan.getTitle());
        authorField.setText(loan.getAutor());
        isLateField.setText(loan.getIsLate().toString());
        dateLoanField.setText(loan.getDateLoan().toString());
        dateReturnLoanField.setText(loan.getDateReturnLoan().toString());
        realDateReturnLoanField.setText(loan.getRealDateReturnLoan() != null ? loan.getRealDateReturnLoan().toString() : "");
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
     * Validates the input and, if valid, updates the loan object and closes the dialog.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            loan.setIdUser(Integer.parseInt(idUserField.getText()));
            loan.setTitle(titleField.getText());
            loan.setAutor(authorField.getText());
            loan.setIsLate(isLateField.getText());
            loan.setDateLoan(LocalDate.parse(dateLoanField.getText()));
            loan.setDateReturnLoan(LocalDate.parse(dateReturnLoanField.getText()));
            loan.setRealDateReturnLoan(realDateReturnLoanField.getText().isEmpty() ? null : LocalDate.parse(realDateReturnLoanField.getText()));
            okClicked = true;
            loanStage.close();
        }
    }

    /**
     * Called when the user clicks Cancel.
     * Closes the dialog without saving changes.
     */
    @FXML
    private void handleCancel() {
        loanStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid, false otherwise
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (idUserField.getText() == null || idUserField.getText().isEmpty()) {
            errorMessage += "No valid user ID!\n";
        } else {
            // Try to parse the user ID into an integer.
            try {
                Integer.parseInt(idUserField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid user ID (must be an integer)!\n";
            }
        }

        if (titleField.getText() == null || titleField.getText().isEmpty()) {
            errorMessage += "No valid title!\n";
        }
        if (authorField.getText() == null || authorField.getText().isEmpty()) {
            errorMessage += "No valid author!\n";
        }
        if (isLateField.getText() == null || isLateField.getText().isEmpty()) {
            errorMessage += "No valid state!\n";
        }
        if (dateLoanField.getText() == null || dateLoanField.getText().isEmpty()) {
            errorMessage += "No valid loan date!\n";
        } else {
            // Try to parse the loan date.
            try {
                LocalDate.parse(dateLoanField.getText());
            } catch (Exception e) {
                errorMessage += "No valid loan date (must be in format YYYY-MM-DD)!\n";
            }
        }
        if (dateReturnLoanField.getText() == null || dateReturnLoanField.getText().isEmpty()) {
            errorMessage += "No valid return date!\n";
        } else {
            // Try to parse the return date.
            try {
                LocalDate.parse(dateReturnLoanField.getText());
            } catch (Exception e) {
                errorMessage += "No valid return date (must be in format YYYY-MM-DD)!\n";
            }
        }
        if (!realDateReturnLoanField.getText().isEmpty()) {
            // Try to parse the real return date if provided.
            try {
                LocalDate.parse(realDateReturnLoanField.getText());
            } catch (Exception e) {
                errorMessage += "No valid real return date (must be in format YYYY-MM-DD)!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(loanStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
