/**
 * Main method for the fractal program. Creates a drawing of a fractal which can be manipulated
 * by user input through a GUI
 *
 * @author Gwen Tait
 * @version 12-3-24
 */
public class Main {
    /**
     *  main method for the fractal program. instantiates a generator, gui, and drawing to display
     *  the fractal
     * @param args any command line arguments
     */
    public static void main(String[] args){
        FractalSubject subj = new FractalGenerator();
        new FractalGui(subj);
        new FractalDrawing(subj);
    }
}
