package Projet.Biblio.User;

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

public class UserOverviewController implements Initializable, ControlledScreen {

    @FXML private TableView<User> personTable;
    @FXML private TableColumn<User, String> firstNameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;
    @FXML private Label IdLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label addressLabel;
    @FXML private Label phoneLabel;
    @FXML private Label nbrempruntLabel;
    @FXML private Label livrenonrenduLabel;

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
    public UserOverviewController() {
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
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

        // Load user data from database
        personTable.setItems(sqlBase.getUsers());
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
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null.
     */
    private void showPersonDetails(User person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            IdLabel.setText(Integer.toString(person.getId()));
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            addressLabel.setText(person.getAddress());
            phoneLabel.setText(person.getPhone());
            nbrempruntLabel.setText(Integer.toString(person.getNbremprunt()));
            livrenonrenduLabel.setText(person.getLivrenonrendu());
        } else {
            // Person is null, clear all text.
            IdLabel.setText("");
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            addressLabel.setText("");
            phoneLabel.setText("");
            nbrempruntLabel.setText("");
            livrenonrenduLabel.setText("");
        }
    }

    /**
     * Called when the user clicks the delete button.
     * Removes the selected person from the table.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        User selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
            sqlBase.removeUser(selectedPerson.getId());
        } else {
            // No selection made.
            showAlert("No Selection", "No Person Selected", "Please select a person in the table.");
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to create a new person.
     */
    @FXML
    private void handleNewPerson() {
        User tempPerson = new User();
        boolean okClicked = MainAppFX.Lemain.showUserEditDialog(tempPerson);
        if (okClicked) {
            sqlBase.insertUser(tempPerson);
            personTable.setItems(sqlBase.getUsers());
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit the selected person.
     */
    @FXML
    private void handleEditPerson() {
        User selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = MainAppFX.Lemain.showUserEditDialog(selectedPerson);
            if (okClicked) {
                sqlBase.updateUser(selectedPerson);
                showPersonDetails(selectedPerson);
                personTable.refresh();
            }
        } else {
            // No selection made.
            showAlert("No Selection", "No Person Selected", "Please select a person in the table.");
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
        alert.initOwner(MainAppFX.Lemain.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
