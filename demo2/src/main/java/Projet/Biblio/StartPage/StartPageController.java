package Projet.Biblio.StartPage;

import Projet.Biblio.Screen.ScreensController;
import Projet.Biblio.Screen.ControlledScreen;
import Projet.Biblio.MainAppFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Controller class for the start page of the application.
 * This class handles the initialization of the start page and navigation to other screens.
 */
public class StartPageController implements Initializable, ControlledScreen {

    Projet.Biblio.Screen.ScreensController myController;

    /**
     * Sets the screen parent for this controller.
     *
     * @param screenParent the screen parent
     */
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    /**
     * Navigates to the person overview screen.
     *
     * @param event the action event
     */
    @FXML
    private void goToScreenPersonOverview(ActionEvent event){
        System.out.println("C1");
        myController.setScreen(MainAppFX.screenPersonPageID);
    }

    @FXML
    private void goToScreenLoanOverview(ActionEvent event){
        System.out.println("C2");
        myController.setScreen(MainAppFX.screenLoanPageID);
    }

    /**
     * Navigates to the book page screen.
     *
     * @param event the action event
     */
    @FXML
    private void goToscreenBookPageID(ActionEvent event){
        System.out.println("C3");
        myController.setScreen(MainAppFX.screenBookPageID);
    }

    private Stage dialogStage;

    private boolean ResearchUserClicked = false;
    private boolean ResearchBookClicked = false;
    private MainAppFX mainAppFX;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic goes here if needed
    }
}
