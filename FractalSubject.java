import java.awt.*;
import java.util.ArrayList;

/**
 * Interface for fractal subjects for the fractal drawing program
 * @author Gwen Tait
 * @version 12-3-24
 */
public interface FractalSubject {
    /**
     * Notifies observers that an update is available
     */
    void notifyObservers();

    /**
     * adds a new observer on the list of observers
     * @param FractalObserver  the new observer
     */
    void registerObservers(FractalObserver FractalObserver);

    /**
     * removes an observer from the list of observers
     * @param FractalObserver    the observer to remove
     */
    void unregisterObservers(FractalObserver FractalObserver);

    /**
     * gets the list of elements that have been generated
      * @return     An ArrayList of fractal elements
     */
    ArrayList<FractalElement> getFractalElements();

    /**
     * Sets the options of the fractal generator with the passed values
     * as the settings
     * @param recDepth      the number of recursions to make
     * @param ratio         the ratio of a child's branch width and length to its parent
     * @param lChAngle      the angle to put the left branch at
     * @param rChAngle      the angle to put the right branch at
     * @param trunkLength   the length of the starting branch
     * @param trunkWidth    the width of the starting branch
     * @param trunkColor    the starting color of the first branch
     * @param leafColor     the ending color of the final branches
     */
    void setOptions(int recDepth, int ratio, int lChAngle, int rChAngle,
                           int trunkLength, int trunkWidth, Color trunkColor, Color leafColor);
}
