import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  observer class which draws the fractal elements received from the subject
 * @author Gwen Tait
 * @version 12-3-24
 */
public class FractalDrawing extends JFrame implements FractalObserver  {
    /**
     * the subject to receive updates from
     */
    FractalSubject subject;

    /**
     * the elements to draw
     */
    ArrayList<FractalElement> elements;

    /**
     * constructs a new fractal drawing observer to get info from the passed subject
     * @param subject   the subject
     */
    FractalDrawing(FractalSubject subject){
        //register object as an observer
        this.subject = subject;
        subject.registerObservers(this);
        elements = subject.getFractalElements();//get starting picture

        //create window for the drawing
        setTitle("Fractal Drawing");
        setSize(1000, 800);
        setResizable(false);

        //create panel to draw on
        JPanel panel = new DrawArea();
        getContentPane().add(panel);
        setVisible(true);
    }

    /**
     * updates the list of elements by pulling from the subject, then repaints
     */
    public void update(){
        elements = subject.getFractalElements();
        repaint();
    }

    /**
     * private helper class for FractalDrawing, created to override
     * the paintComponent() function in JPanel
     */
    private class DrawArea extends JPanel {
        /**
         * override of JPanel's paintComponent. Sets background to black and
         * draws each of the objects in the ArrayList of elements
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            setBackground(Color.BLACK);
            for (FractalElement element : elements) {
                element.draw(g);
            }
        }
    }
}


