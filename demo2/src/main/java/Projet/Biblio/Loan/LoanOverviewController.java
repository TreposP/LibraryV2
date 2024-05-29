package Projet.Biblio.Loan;

import Projet.Biblio.SQLNEW;
import Projet.Biblio.MainAppFX;
import Projet.Biblio.Screen.ScreensController;
import Projet.Biblio.Screen.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanOverviewController implements Initializable, ControlledScreen {

    @FXML private TableView<Loan> loanTable;
    @FXML private TableColumn<Loan, Integer> idUserColumn;
    @FXML private TableColumn<Loan, String> titleColumn;
    @FXML private TableColumn<Loan, String> autorColumn;
    @FXML private TableColumn<Loan, Object> stateColumn;

    @FXML private Label idUserLabel;
    @FXML private Label titleLabel;
    @FXML private Label autorLabel;
    @FXML private Label dateLoanLabel;
    @FXML private Label dateReturnLoanLabel;
    @FXML private Label realDateReturnLoanLabel;

    /** Reference to the main application. */
    private MainAppFX mainAppFX;

    /** Reference to the screens controller. */
    ScreensController myController;

    /** Reference to the SQLBase */
    private SQLNEW sqlBase;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LoanOverviewController() {
        sqlBase = new SQLNEW();
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the loan table with the columns.
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Clear loan details.
        showLoanDetails(null);

        // Listen for selection changes and show the loan details when changed.
        loanTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showLoanDetails(newValue));

        // Load loan data from database
        loanTable.setItems(sqlBase.getLoans());
    }

    /**
     * Sets the main application reference.
     *
     * @param mainAppFX the main application instance.
     */
    public void setMainApp(MainAppFX mainAppFX) {
        this.mainAppFX = mainAppFX;
    }

    /**
     * Fills all text fields to show details about the loan.
     * If the specified loan is null, all text fields are cleared.
     *
     * @param loan the loan or null.
     */
    private void showLoanDetails(Loan loan) {
        if (loan != null) {
            // Fill the labels with info from the loan object.
            idUserLabel.setText(Integer.toString(loan.getIdUser()));
            titleLabel.setText(loan.getTitle());
            autorLabel.setText(loan.getAutor());
            dateLoanLabel.setText(loan.getDateLoan().toString());
            dateReturnLoanLabel.setText(loan.getDateReturnLoan().toString());
            realDateReturnLoanLabel.setText(loan.getRealDateReturnLoan() != null ? loan.getRealDateReturnLoan().toString() : "");
        } else {
            // Loan is null, clear all text.
            idUserLabel.setText("");
            titleLabel.setText("");
            autorLabel.setText("");
            dateLoanLabel.setText("");
            dateReturnLoanLabel.setText("");
            realDateReturnLoanLabel.setText("");
        }
    }

    /**
     * Called when the user clicks the delete button.
     * Removes the selected loan from the table.
     */
    @FXML
    private void handleDeleteLoan() {
        int selectedIndex = loanTable.getSelectionModel().getSelectedIndex();
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            loanTable.getItems().remove(selectedIndex);
            sqlBase.removeLoan(selectedLoan.getIdLoan());
        } else {
            // No selection made.
            showAlert("No Selection", "No Loan Selected", "Please select a loan in the table.");
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to create a new loan.
     */
    @FXML
    private void handleNewLoan() {
        Loan tempLoan = new Loan();
        boolean okClicked = mainAppFX.showLoanEditDialog(tempLoan);
        if (okClicked) {
            sqlBase.insertLoan(tempLoan);
            loanTable.setItems(sqlBase.getLoans());
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit the selected loan.
     */
    @FXML
    private void handleEditLoan() {
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();
        if (selectedLoan != null) {
            boolean okClicked = mainAppFX.showLoanEditDialog(selectedLoan);
            if (okClicked) {
                sqlBase.updateLoan(selectedLoan);
                showLoanDetails(selectedLoan);
                loanTable.refresh();
            }
        } else {
            // No selection made.
            showAlert("No Selection", "No Loan Selected", "Please select a loan in the table.");
        }
    }

    /**
     * Sets the parent screen controller.
     *
     * @param screenParent the parent screen controller.
     */
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    /**
     * Navigates to the start page screen.
     *
     * @param event the action event.
     */
    @FXML
    private void goToScreenStartPageID(ActionEvent event) {
        myController.setScreen(MainAppFX.screenStartPageID);
    }

    /**
     * Shows an alert with the specified title, header, and content text.
     *
     * @param title the title of the alert.
     * @param header the header text of the alert.
     * @param content the content text of the alert.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainAppFX.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
