package Projet.Biblio.Screen;

/**
 * Interface for controlling screens.
 * This interface defines a method to set the parent screen controller.
 */
public interface ControlledScreen {

    /**
     * Sets the parent screen controller.
     *
     * @param screenPage the parent screen controller to be set
     */
    void setScreenParent(ScreensController screenPage);
}
