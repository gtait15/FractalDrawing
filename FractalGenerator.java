import java.awt.*;
import java.util.ArrayList;

/**
 * This class is the generator for the fractal. It uses recursion to generate branches for
 * the fractal. Acts as a subject and notifies observers when changes are available.
 * @author Gwen Tait
 * @version 12-3-24
 */
public class FractalGenerator implements FractalSubject{
    /**
     * the number of recursions to make in the fractal
     */
    private int recDepth;

    /**
     * the ratio of child branch length and width to its parent's
     */
    private double ratio;

    /**
     * the angle for the left child branch to be generated relative to its parent
     */
    private double lChAngle;

    /**
     * the angle for the right child branch to be generated relative to its parent
     */
    private double rChAngle;

    /**
     * the initial length for the starting branch
     */
    private int trunkLength;

    /**
     * the initial width for the starting branch
     */
    private int trunkWidth;

    /**
     * the starting color for the first branch
     */
    private Color trunkColor;

    /**
     * the ending color for the final branches
     */
    private Color leafColor;

    /**
     * the increment of red values between parent and child branches
     */
    private int redIncrement;

    /**
     * the increment of green values between parent and child branches
     */
    private int greenIncrement;

    /**
     * the increment of blue values between parent and child branches
     */
    private int blueIncrement;

    /**
     * the list of observers of the subject
     */
    private ArrayList<FractalObserver> observers;

    /**
     * the list of generated fractal elements which can be passed to the
     * observers upon request
     */
    ArrayList<FractalElement> elements;

    /**
     * the x-coordinate for starting point of the first branch
     */
    private final int X_ORIGIN = 500;

    /**
     * the y-coordinate for the starting point of the first branch
     */
    private final int Y_ORIGIN = 800;

    /**
     * the starting angle for the first branch
     */
    private final double STARTING_ANGLE = Math.PI/2;

    /**
     * constructs a new FractalGenerator object with an empty list of observers
     */
    public FractalGenerator(){
       observers = new ArrayList<>();
    }

    /**
     * notifies observers that an update is available
     */
    public void notifyObservers(){
         for (FractalObserver observer : observers) {
             observer.update();
         }
    }

    /**
     * adds a new observer to the list of observers
     * @param obs  the observer to register
     */
    public void registerObservers(FractalObserver obs){
       observers.add(obs);
    }

    /**
     * removes an observer from the list of observers
     * @param obs  the observer to remove
     */
    public void unregisterObservers(FractalObserver obs){
        observers.remove(obs);
    }

    /**
     * builds an ArrayList of fractal elements using the current settings.
     * @return  an ArrayList of fractal elements
     */
    public ArrayList<FractalElement> getFractalElements(){
        //calculate rgb increments to calculate colors
        redIncrement =  (leafColor.getRed() - trunkColor.getRed()) / recDepth;
        greenIncrement = (leafColor.getGreen() - trunkColor.getGreen()) / recDepth;
        blueIncrement =  (leafColor.getBlue() - trunkColor.getBlue()) / recDepth;

        elements = new ArrayList<>();
        generateBranches(recDepth, trunkWidth, trunkLength, STARTING_ANGLE, trunkColor,
                X_ORIGIN, Y_ORIGIN, X_ORIGIN, Y_ORIGIN - trunkLength);
        return elements;
    }

    /**
     * recursive method for getFractalElements(). Adds a branch with the passed data, then calculates
     * the info for the next branches and recursively calls itself with that info.
     * @param depthCount        the number of recursions left to make
     * @param currTrunkWidth    the current width of the branches
     * @param currTrunkLength   the current length of the branches
     * @param currAngle         the current angle the branch is at
     * @param color             the current color of the branch
     * @param x1                the x coordinate of the starting point of the branch
     * @param y1                the y coordinate of the starting point of the branch
     * @param x2                the x coordinate of the endpoint of the branch
     * @param y2                the y coordinate of the endpoint of the branch
     */
    private void generateBranches(int depthCount, double currTrunkWidth, double currTrunkLength,
                                  double currAngle, Color color, int x1, int y1, int x2, int y2){
        if (depthCount != 0) {
            //first add branch with the passed data
            elements.add(new Branch(x1, y1, x2, y2, (int) currTrunkWidth, color));

            //calculate some info for next branches
            currTrunkWidth *= ratio;
            currTrunkLength *= ratio;
            color = new Color(redIncrement + color.getRed(), greenIncrement + color.getGreen(),
                     blueIncrement + color.getBlue());
            depthCount--;

            //recursively call generateBranches. Performance was worse when I added local variables
            //for angle and branch math, so it takes place in the function calls instead (regrettably).
            generateBranches(depthCount, currTrunkWidth, currTrunkLength,
                    currAngle - rChAngle, color, x2, y2,
                    x2 + (int) (currTrunkLength * Math.cos(currAngle - rChAngle)),
                    y2 - (int) (currTrunkLength * Math.sin(currAngle - rChAngle)));
            generateBranches(depthCount,  currTrunkWidth, currTrunkLength,
                    currAngle + lChAngle, color, x2, y2,
                    x2 + (int) (currTrunkLength * Math.cos(currAngle + lChAngle)),
                    y2 - (int) (currTrunkLength * Math.sin(currAngle + lChAngle)));
        }
    }

    /**
     * receives settings from the GUI and updates its variables accordingly. Angle measures
     * are converted to radians and ratio is converted into a decimal. Then notifies observers
     * that an update is available
     * @param recDepth      the new recursion depth
     * @param ratio         the new child-to-parent ratio, as a whole number
     * @param lChAngle      the new left child angle, in degrees
     * @param rChAngle      the new right child angle, in degrees
     * @param trunkLength   the new trunk length
     * @param trunkWidth    the new trunk width
     * @param trunkColor    the new trunk color
     * @param leafColor     the new lead color
     */
    public void setOptions(int recDepth, int ratio, int lChAngle, int rChAngle,
                           int trunkLength, int trunkWidth, Color trunkColor, Color leafColor){
        this.recDepth = recDepth;
        this.ratio = ratio * .01;
        this.lChAngle = Math.toRadians(lChAngle);
        this.rChAngle = Math.toRadians(rChAngle);
        this.trunkLength = trunkLength;
        this.trunkWidth = trunkWidth;
        this.trunkColor = trunkColor;
        this.leafColor = leafColor;
        notifyObservers();
    }
}
