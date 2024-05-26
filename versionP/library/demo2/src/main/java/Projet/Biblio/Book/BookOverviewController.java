package Projet.Biblio.Book;
import Projet.Biblio.MainAppFX;
import Projet.Biblio.Screen.ScreensController;
import Projet.Biblio.Screen.ControlledScreen;
import Projet.Biblio.Util.DateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class BookOverviewController implements Initializable, ControlledScreen{
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

    // test
    ScreensController myController;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BookOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundl) {
        // Initialize the Book table with the two columns.
        TitleColumn.setCellValueFactory(
                cellData -> cellData.getValue().TitleProperty());
        AutorColumn.setCellValueFactory(
                cellData -> cellData.getValue().AutorProperty());
        /** ChiffreColumn.setCellValueFactory(cellData -> cellData.getValue().ChiffreProperty().asObject());
         * quand il s'agit d'un integer ou d'un double, on rajoute  .asObject()
         */
        // Clear Book details.
        showBookDetails(null);

        // Listen for selection changes and show the Book details when changed.
        BookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
    }




    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainAppFX
     */
    public void setMainApp(MainAppFX mainAppFX) {
        this.mainAppFX = mainAppFX;

        // Add observable list data to the table
        BookTable.setItems(mainAppFX.getBookData());
    }

    /**
     * Fills all text fields to show details about the Book.
     * If the specified Book is null, all text fields are cleared.
     *
     * @param Book the Book or null
     */
    private void showBookDetails(Book Book) {
        if (Book != null) {
            // Fill the labels with info from the Book object.
            TitleLabel.setText(Book.getTitle());
            AutorLabel.setText(Book.getAutor());
            AvailableLabel.setText(Book.getAvailable());
            ISBNLabel.setText(Integer.toString(Book.getISBN()));
            DateLabel.setText(DateUtil.format(Book.getDate()));

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
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreenStartPageID(ActionEvent event) {
        System.out.println("C1");
        myController.setScreen(MainAppFX.screenStartPageID);
    }
}