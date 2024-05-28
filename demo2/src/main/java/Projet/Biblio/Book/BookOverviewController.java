package Projet.Biblio.Book;

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

/**
 * Controller class for the book overview screen.
 */
public class BookOverviewController implements Initializable, ControlledScreen {
    @FXML
    private TableView<Book> BookTable;
    @FXML
    private TableColumn<Book, String> TitleColumn;
    @FXML
    private TableColumn<Book, String> AutorColumn;

    @FXML
    private Label TitleLabel;
    @FXML
    private Label AutorLabel;
    @FXML
    private Label AvailableLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label DateLabel;

    // Reference to the main application.
    private MainAppFX mainAppFX;

    // Screen controller
    ScreensController myController;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the Book table with the two columns.
        TitleColumn.setCellValueFactory(
                cellData -> cellData.getValue().TitleProperty());
        AutorColumn.setCellValueFactory(
                cellData -> cellData.getValue().AutorProperty());
        // Clear Book details.
        showBookDetails(null);

        // Listen for selection changes and show the Book details when changed.
        BookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     */
    public void setMainApp(MainAppFX mainAppFX) {
        this.mainAppFX = mainAppFX;

        // Add observable list data to the table
        BookTable.setItems(mainAppFX.getBookData());
    }

    /**
     * Fills all text fields to show details about the Book.
     * If the specified Book is null, all text fields are cleared.
     */
    private void showBookDetails(Book book) {
        if (book != null) {
            // Fill the labels with info from the Book object.
            TitleLabel.setText(book.getTitle());
            AutorLabel.setText(book.getAutor());
        } else {
            // Book is null, remove all the text.
            TitleLabel.setText("");
            AutorLabel.setText("");
            AvailableLabel.setText("");
            ISBNLabel.setText("");
            DateLabel.setText("");
        }
    }

    /**
     * Called when the user clicks the edit button. Opens Emprunt to edit
     * details for the selected Book.
     */
    @FXML
    private void handleEditBook() {
        Book selectedBook = BookTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            boolean okClicked = MainAppFX.Lemain.showBookEditEmprunt(selectedBook);
            if (okClicked) {
                showBookDetails(selectedBook);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainAppFX.Lemain.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Book Selected");
            alert.setContentText("Please select a Book in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Sets the screen parent for this controller.
     */
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    /**
     * Handles the action to go to the start page.
     */
    @FXML
    private void goToScreenStartPageID(ActionEvent event) {
        myController.setScreen(MainAppFX.screenStartPageID);
    }
}
