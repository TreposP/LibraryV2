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



public class StartPageController implements Initializable, ControlledScreen {
/**
    @FXML
    private Button ResearchUser;

    @FXML
    private Button ResearchBook;
*/
    Projet.Biblio.Screen.ScreensController myController;
    /**
     * Initializes the controller class.
     */

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }


    @FXML
    private void goToScreenPersonOverview(ActionEvent event){
        System.out.println("C1");
        myController.setScreen(MainAppFX.screenPersonPageID);
    }

    @FXML
    private void goToscreenBookPageID(ActionEvent event){
        myController.setScreen(MainAppFX.screenBookPageID);
    }
    private Stage dialogStage;

    private boolean ResearchUserClicked = false;
    private boolean ResearchBookClicked = false;
    private MainAppFX mainAppFX;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isResearchUserClicked() {
        return ResearchUserClicked;
    }

    public boolean isResearchBookClicked() {
        return ResearchBookClicked;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void handleBook() {
        boolean okClicked = mainApp.showStartPage();
        if (okClicked) {
            mainApp.showStartPage();
        }
    }
    */
}



