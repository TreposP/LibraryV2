package Projet.Biblio;

import java.io.IOException;

import Projet.Biblio.Book.Book;
import Projet.Biblio.Book.BookOverviewController;
import Projet.Biblio.Book.BookEditEmpruntController;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import Projet.Biblio.User.Person;
import Projet.Biblio.User.PersonEditDialogController;
import Projet.Biblio.User.PersonOverviewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class MainAppFX extends Application {
    // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ObservableList<Book> BookData = FXCollections.observableArrayList();

    //
    public static String screenStartPageID = "StartPage";
    public static String screenStartPageFile = "/Projet/Biblio/StartPage/StartPage.fxml";
    public static String screenPersonPageID = "PersonOverview";
    public static String screenPersonPageFile = "/Projet/Biblio/User/PersonOverview.fxml";
    public static String screenBookPageID = "BookOverview";
    public static String screenBookPageFile = "/Projet/Biblio/Book/BookOverview.fxml";
    public static MainAppFX Lemain;
    //

    public MainAppFX() {
        personData.add(new Person("Nathan", "Signoud"));
        personData.add(new Person("Nathan", "Battais"));
        personData.add(new Person("Alize", "Fortunel"));
        personData.add(new Person("François", "Bretagne"));
        personData.add(new Person("Efe", "tatar"));

        BookData.add(new Book("Le Livre", "De la jungle"));
        BookData.add(new Book("Le Livre", "De la jungle"));
        BookData.add(new Book("Le Livre", "De la jungle"));
        BookData.add(new Book("Le Livre", "De la jungle"));
        BookData.add(new Book("Le Livre", "De la jungle"));

    }
    /**
     * Returns the data as an observable list of Persons.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    public ObservableList<Book> getBookData() {
        return BookData;
    }

    // ... THE REST OF THE CLASS ...

    public Stage primaryStage;
    public AnchorPane Startpage;
    public BorderPane rootLayout;

    /** 2 main car on a pas encore associé le back end et l'interface graphique ensemble*/

     @Override
    public void start(Stage primaryStage) {


        Lemain = this;

        Projet.Biblio.Screen.ScreensController mainContainer = new Projet.Biblio.Screen.ScreensController();
        mainContainer.loadScreen(MainAppFX.screenStartPageID, MainAppFX.screenStartPageFile);
        mainContainer.loadScreen(MainAppFX.screenPersonPageID, MainAppFX.screenPersonPageFile);
        mainContainer.loadScreen(MainAppFX.screenBookPageID, MainAppFX.screenBookPageFile);

        mainContainer.setScreen(MainAppFX.screenStartPageID);

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cy-Books");
        //initRootLayout();

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        //showPersonOverview();
    }

    /**
     * Initializes the root layout.
     *Don't work for the moment
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/User/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     * Don't work for the moment
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/User/PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set person overview into the center of root layout.
            //Startpage.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController Pcontroller = loader.getController();
            Pcontroller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the book overview inside the root layout.
     * Don't work for the moment
     */
    public void showBookOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/Book/BookOverview.fxml"));
            AnchorPane BookOverview = loader.load();

            // Set person overview into the center of root layout.
            //rootLayout.setCenter(BookOverview);

            // Give the controller access to the main app.
            BookOverviewController Bcontroller = loader.getController();
            Bcontroller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }



    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/User/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showBookEditEmprunt(Book book) {
        try {
            // Load the fxml file and create a new stage for the popup Emprunt.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/Book/BookEditEmprunt.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the Emprunt Stage.
            Stage EmpruntStage = new Stage();
            EmpruntStage.setTitle("Edit Book");
            EmpruntStage.initModality(Modality.WINDOW_MODAL);
            EmpruntStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            EmpruntStage.setScene(scene);

            // Set the person into the controller.
            BookEditEmpruntController controller = loader.getController();
            controller.setEmpruntStage(EmpruntStage);
            controller.setBook(book);

            // Show the Emprunt and wait until the user closes it
            EmpruntStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Ne marche pas en mettant showStartPage() dans le main
    public boolean showStartPage() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/StartPage/StartPage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("page d'accueil");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}