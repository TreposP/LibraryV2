package Projet.Biblio.User;

import Projet.Biblio.Util.DateUtil;
import Projet.Biblio.MainAppFX;
import Projet.Biblio.Screen.ScreensController;
import Projet.Biblio.Screen.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonOverviewController implements Initializable, ControlledScreen {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label nbrempruntLabel;
    @FXML
    private Label livrenonrenduLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private MainAppFX mainAppFX;

    // test
    ScreensController myController;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController()  {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());
        /** ChiffreColumn.setCellValueFactory(cellData -> cellData.getValue().ChiffreProperty().asObject());
         * quand il s'agit d'un integer ou d'un double, on rajoute  .asObject()
         */
        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainAppFX
     */
    public void setMainApp(MainAppFX mainAppFX) {
        this.mainAppFX = mainAppFX;

        // Add observable list data to the table
        personTable.setItems(mainAppFX.getPersonData());
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            nbrempruntLabel.setText(Integer.toString(person.getnbremprunt()));
            livrenonrenduLabel.setText(person.getlivrenonrendu());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));

        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            nbrempruntLabel.setText("");
            livrenonrenduLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainAppFX.Lemain.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = MainAppFX.Lemain.showPersonEditDialog(tempPerson);
        if (okClicked) {
            MainAppFX.Lemain.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = MainAppFX.Lemain.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainAppFX.Lemain.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreenStartPageID(ActionEvent event){
        System.out.println("C1");
        myController.setScreen(MainAppFX.screenStartPageID);
    }

}