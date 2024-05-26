package Projet.Biblio.Screen;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Controller class for managing different screens in the application.
 * This class extends StackPane and provides methods to add, load, set, and unload screens.
 */
public class ScreensController extends StackPane {
    // Holds the screens to be displayed
    private HashMap<String, Node> screens = new HashMap<>();

    /**
     * Constructor for ScreensController.
     */
    public ScreensController() {
        super();
    }

    /**
     * Adds a screen to the collection.
     *
     * @param name   the name of the screen
     * @param screen the node representing the screen
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * Retrieves the Node with the specified name.
     *
     * @param name the name of the screen
     * @return the Node representing the screen
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Loads an FXML file representing a screen and adds it to the screens collection.
     *
     * @param name     the name of the screen
     * @param resource the resource path of the FXML file
     * @return true if the screen is successfully loaded, false otherwise
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenControler = myLoader.getController();
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Sets the specified screen to be displayed.
     *
     * @param name the name of the screen to be displayed
     * @return true if the screen is successfully set, false otherwise
     */
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {   // screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    // if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);                    // remove the displayed screen
                                getChildren().add(0, screens.get(name));     // add the new screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       // no other screens displayed, show the new screen
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!!! \n");
            return false;
        }
    }

    /**
     * Removes the screen with the specified name from the screens collection.
     *
     * @param name the name of the screen to be unloaded
     * @return true if the screen is successfully unloaded, false otherwise
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen doesn't exist");
            return false;
        } else {
            return true;
        }
    }
}
