package Projet.Biblio.Book;

import Projet.Biblio.BookServiceNew;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

/**
 * Controller class for the book overview screen.
 */
public class BookOverviewController implements ControlledScreen{

    @FXML
    private TextField titleField;
    @FXML
    private TextField autorField;
    @FXML
    private TextField dateField;

    @FXML
    private TableView<Book> BookTable;
    @FXML
    private TableColumn<Book, String> TitleColumn;
    @FXML
    private TableColumn<Book, String> AutorColumn;
    @FXML
    private TableColumn<Book, String> DateColumn;

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

    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    // Reference to the main application.
    private MainAppFX mainAppFX;

    // Screen controller
    ScreensController myController;

    @FXML
    private void initialize() {
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        AutorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        BookTable.setItems(bookData);
    }

    @FXML
    private void handleSearch() {
        String title = titleField.getText().trim();
        String author = autorField.getText().trim();
        String date = dateField.getText().trim();

        if (title.isEmpty() && author.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Pour faire une recherche il faut au moins un titre ou un nom d'auteur");
            alert.showAndWait();
            return;
        }

        // Effectuer la recherche avec l'API
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("PREFIX rdarelationships: <http://rdvocab.info/RDARelationshipsWEMI/>\n");
            queryBuilder.append("PREFIX dcterms: <http://purl.org/dc/terms/>\n");
            queryBuilder.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n");
            queryBuilder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n");
            queryBuilder.append("PREFIX bnf-onto: <http://data.bnf.fr/ontology/bnf-onto/>\n");
            queryBuilder.append("SELECT DISTINCT ?titre ?auteur ?date\n");
            queryBuilder.append("WHERE {\n");

            if (!title.isEmpty()) {
                queryBuilder.append("  FILTER regex(?titre, \"" + title + "\", \"i\") \n");
            }
            if (!author.isEmpty()) {
                queryBuilder.append("  ?work dcterms:creator ?creator .\n");
                queryBuilder.append("  ?creator foaf:name ?auteur .\n");
                queryBuilder.append("  FILTER regex(?auteur, \"" + author + "\", \"i\") \n");
            }
            if (!date.isEmpty()) {
                queryBuilder.append("  ?work dcterms:date ?date .\n");
                queryBuilder.append("  FILTER regex(?date, \"" + date + "\", \"i\") \n");
            }

            queryBuilder.append("  ?work rdfs:label ?titre ;\n");
            queryBuilder.append("        dcterms:creator ?creator;\n");
            queryBuilder.append("        dcterms:date ?date.\n");
            queryBuilder.append("  ?creator foaf:name ?auteur .\n");
            queryBuilder.append("}\n");
            queryBuilder.append("LIMIT 50\n");

            String encodedQuery = URLEncoder.encode(queryBuilder.toString(), "UTF-8");
            String sparqlEndpoint = "https://data.bnf.fr/sparql?query=" + encodedQuery;
            HttpGet httpGet = new HttpGet(sparqlEndpoint);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null && response.getStatusLine().getStatusCode() == 200) {
                String xmlData = EntityUtils.toString(entity);
                parseAndDisplayBooks(xmlData);
            } else {
                showErrorMessage("La requête SPARQL a retourné une réponse non valide: " + response.getStatusLine());
            }
        } catch (Exception e) {
            showErrorMessage("Erreur lors de la recherche : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void parseAndDisplayBooks(String xmlData) {
        List<Book> books = BookServiceNew.getBooksFromXML(xmlData);
        bookData.clear();
        bookData.addAll(books);
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
