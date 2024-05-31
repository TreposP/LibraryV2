package Projet.Biblio;

import Projet.Biblio.User.User;
import Projet.Biblio.Loan.Loan;
import Projet.Biblio.Book.Book;

import Projet.Biblio.Book.BookEditEmpruntController;
import Projet.Biblio.User.UserEditDialogController;
import Projet.Biblio.Loan.LoanEditController;

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

import static Projet.Biblio.APIXSQL.CreateDB.createNewDatabase;

public class MainAppFX extends Application {

    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Book> bookData = FXCollections.observableArrayList();
    private ObservableList<Loan> loanData = FXCollections.observableArrayList();

    private Stage primaryStage;

    public static MainAppFX Lemain;

    // Screen IDs and file paths
    public static final String screenStartPageID = "StartPage";
    public static final String screenStartPageFile = "/Projet/Biblio/StartPage/StartPage.fxml";
    public static final String screenPersonPageID = "PersonOverview";
    public static final String screenPersonPageFile = "/Projet/Biblio/User/PersonOverview.fxml";
    public static final String screenBookPageID = "BookOverview";
    public static final String screenBookPageFile = "/Projet/Biblio/Book/BookOverview.fxml";
    public static final String screenLoanPageID = "LoanOverview";
    public static final String screenLoanPageFile = "/Projet/Biblio/Loan/LoanOverview.fxml";

    public MainAppFX() {
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

    public ObservableList<Loan> getLoanData() {
        return loanData;
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        //createNewDatabase();

        Lemain = this;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cy-Books");

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainAppFX.screenStartPageID, MainAppFX.screenStartPageFile);
        mainContainer.loadScreen(MainAppFX.screenPersonPageID, MainAppFX.screenPersonPageFile);
        mainContainer.loadScreen(MainAppFX.screenBookPageID, MainAppFX.screenBookPageFile);
        mainContainer.loadScreen(MainAppFX.screenLoanPageID, MainAppFX.screenLoanPageFile);

        mainContainer.setScreen(MainAppFX.screenStartPageID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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

    public boolean showLoanEditDialog(Loan loan) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAppFX.class.getResource("/Projet/Biblio/Loan/LoanEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage loanStage = new Stage();
            loanStage.setTitle("Edit Loan");
            loanStage.initModality(Modality.WINDOW_MODAL);
            loanStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            loanStage.setScene(scene);

            // Set the user into the controller.
            LoanEditController controller = loader.getController();
            controller.setLoanStage(loanStage);
            controller.setLoan(loan);

            // Show the dialog and wait until the user closes it
            loanStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
