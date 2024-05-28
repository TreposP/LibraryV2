package Projet.Biblio;

import Projet.Biblio.Book.Book;
import Projet.Biblio.Book.BookEditEmpruntController;
import Projet.Biblio.User.UserEditDialogController;
import Projet.Biblio.User.User;
import Projet.Biblio.Screen.ScreensController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAppFX extends Application {

    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Book> bookData = FXCollections.observableArrayList();
    private Stage primaryStage;
    private BorderPane rootLayout;

    public static MainAppFX Lemain;

    // Screen IDs and file paths
    public static final String screenStartPageID = "StartPage";
    public static final String screenStartPageFile = "/Projet/Biblio/StartPage/StartPage.fxml";
    public static final String screenPersonPageID = "PersonOverview";
    public static final String screenPersonPageFile = "/Projet/Biblio/User/PersonOverview.fxml";
    public static final String screenBookPageID = "BookOverview";
    public static final String screenBookPageFile = "/Projet/Biblio/Book/BookOverview.fxml";

    public MainAppFX() {
        // Add some sample data
        userData.add(new User(1, "Hans", "Muster"));
        userData.add(new User(2, "Ruth", "Mueller"));
        userData.add(new User(3, "Heinz", "Kurz"));
        userData.add(new User(4, "Cornelia", "Meier"));
        userData.add(new User(5, "Werner", "Meyer"));
        userData.add(new User(6, "Lydia", "Kunz"));
        userData.add(new User(7, "Anna", "Best"));
        userData.add(new User(8, "Stefan", "Meier"));
        userData.add(new User(9, "Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Users.
     *
     * @return
     */
    public ObservableList<User> getUserData() {
        return userData;
    }

    public ObservableList<Book> getBookData() {
        return bookData;
    }

    @Override
    public void start(Stage primaryStage) {
        Lemain = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cy-Books");

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainAppFX.screenStartPageID, MainAppFX.screenStartPageFile);
        mainContainer.loadScreen(MainAppFX.screenPersonPageID, MainAppFX.screenPersonPageFile);
        mainContainer.loadScreen(MainAppFX.screenBookPageID, MainAppFX.screenBookPageFile);

        mainContainer.setScreen(MainAppFX.screenStartPageID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Opens a dialog to edit details for the specified user. If the user
     * clicks OK, the changes are saved into the provided user object and true
     * is returned.
     *
     * @param user the user object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showUserEditDialog(User user) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/User/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit User");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the user into the controller.
            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);

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
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
