import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * This class creates a GUI with sliders and color pickers to set user
 * options to send to the fractal subject
 * @author Gwen Tait
 * @version 12-3-24
 */
public class FractalGui extends JFrame{
    /**
     * the subject to send the options to
     */
    FractalSubject subject;

    /**
     * the desired number of recursions to make
     */
    private int recursionDepth;

    /**
     * the desired ratio for child branch length and width compared to its parent
     */
    private int childToParRatio;

    /**
     * the desired angle of the left branch away from the parent
     */
    private int leftChAngle;

    /**
     * the desired angle of the right branch away from the parent
     */
    private int rightChAngle;

    /**
     * the desired starting pixel length for the branches
     */
    private int trunkLength;

    /**
     * the desired starting pixel width for the branches
     */
    private int trunkWidth;

    /**
     * the desired starting color for the first branch
     */
    private Color trunkColor;

    /**
     * the desired ending color for the last branches
     */
    private Color leafColor;


    /**
     * constructs a new GUI with the appropriate sliders and buttons to
     * change the settings for the fractal program
     * @param subject   something to send the settings to. Must implement FractalSubject and
     *                  may not be null
     */
    public FractalGui(FractalSubject subject){
        if (subject == null){
            throw new IllegalArgumentException("Subject may not be null");
        }
        this.subject = subject;

        //set some defaults
        recursionDepth = 12;
        childToParRatio = 60;
        leftChAngle = 45;
        rightChAngle = 45;
        trunkLength = 250;
        trunkWidth = 25;
        trunkColor = Color.BLUE;
        leafColor = Color.GREEN;
        updateSettings();

        //create the window with some basic settings
        setTitle("Fractal Settings");
        setSize(300,750);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create JPanel to add widgets on
        JPanel mainPanel = new JPanel(null);

        //add recursion depth slider
        mainPanel.add(addNewLabel(100, 20, "Recursion Depth"));
        JSlider recDepthSlider = addNewSlider(4, 20, 50, 2, 1);
        mainPanel.add(recDepthSlider);
        recDepthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!recDepthSlider.getValueIsAdjusting()){
                    recursionDepth = recDepthSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add child to parent ratio slider
        mainPanel.add(addNewLabel(90, 95, "Child to Parent Ratio"));
        JSlider childToParRatioSlider = addNewSlider(40, 80, 125, 10, 5);
        mainPanel.add(childToParRatioSlider);
        childToParRatioSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!childToParRatioSlider.getValueIsAdjusting()){
                    childToParRatio = childToParRatioSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add left child angle slider
        mainPanel.add(addNewLabel(100, 170, "Left Child Angle"));
        JSlider leftChAngleSlider = addNewSlider(0, 90, 200, 10, 5);
        mainPanel.add(leftChAngleSlider);
        leftChAngleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!leftChAngleSlider.getValueIsAdjusting()){
                    leftChAngle = leftChAngleSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add right child angle slider
        mainPanel.add(addNewLabel(98, 245, "Right Child Angle"));
        JSlider rightChAngleSlider = addNewSlider(0, 90, 275, 10, 5);
        mainPanel.add(rightChAngleSlider);
        rightChAngleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!rightChAngleSlider.getValueIsAdjusting()){
                    rightChAngle = rightChAngleSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add trunk length slider
        mainPanel.add(addNewLabel(110, 320,  "Trunk Length"));
        JSlider trunkLengthSlider = addNewSlider(100, 400, 350, 100, 25);
        mainPanel.add(trunkLengthSlider);
        trunkLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!trunkLengthSlider.getValueIsAdjusting()){
                    trunkLength = trunkLengthSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add trunk width slider
        mainPanel.add(addNewLabel(120, 395,  "Trunk Width"));
        JSlider trunkWidthSlider = addNewSlider(0, 50, 425, 10, 5);
        mainPanel.add(trunkWidthSlider);
        trunkWidthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!trunkWidthSlider.getValueIsAdjusting()){
                    trunkWidth = trunkWidthSlider.getValue();
                    updateSettings();
                }
            }
        });

        //add trunk color button that pulls up a color picker
        JButton trunkColorButton = new JButton("Trunk Color");
        trunkColorButton.setBounds(100, 500, 100, 25);
        mainPanel.add(trunkColorButton);
        trunkColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color temp = trunkColor; //saves color if user changes their mind
                trunkColor = JColorChooser.showDialog(
                        trunkColorButton, "Choose Trunk Color", Color.WHITE); //returns null if user cancels
                if (trunkColor == null){
                    trunkColor = temp;
                }
                updateSettings();
            }
        });

        //add leaf color button that pulls up a color picker
        JButton leafColorButton = new JButton("Leaf Color");
        leafColorButton.setBounds(100, 550, 100, 25);
        mainPanel.add(leafColorButton);
        leafColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Color temp = leafColor; //saves color if user changes their mind
                leafColor = JColorChooser.showDialog(
                        leafColorButton, "Choose Leaf Color", Color.WHITE); //returns null if user cancels
                if (leafColor == null){
                    leafColor = temp;
                }
                updateSettings();
            }
        });

        //add random button
        JButton randomButton = new JButton("Randomize");
        randomButton.setBounds(100, 600, 100, 25);
        mainPanel.add(randomButton);
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomize(recDepthSlider, childToParRatioSlider, leftChAngleSlider,
                        rightChAngleSlider, trunkLengthSlider, trunkWidthSlider);
            }
        });

        //add panel to the window and make the whole thing visible
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    /**
     * creates a slider for the GUI with the passed features on the same axis as the others
     * @param min           the desired minimum value pf the slider
     * @param max           the desired maximum value of the slider
     * @param y             the height at which to put the new slider
     * @param majorTick     the distance between major ticks on the slider
     * @param minorTick     the distance between minor ticks on the slider
     * @return              a JSlider with the appropriate values
     */
    private JSlider addNewSlider(int min, int max, int y, int majorTick, int minorTick){
        JSlider slider = new JSlider(min, max);
        slider.setBounds(25, y, 250, 40);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(majorTick);
        slider.setMinorTickSpacing(minorTick);
        slider.setPaintLabels(true);
        return slider;
    }

    /**
     * creates a new JLabel with the passed features
     * @param x         the desired x coordinate to place the label
     * @param y         the desired y coordinate to place the label
     * @param name      the name to display on the label
     * @return          a JLabel with the appropriate specifications
     */
    private JLabel addNewLabel(int x, int y, String name){
        JLabel label = new JLabel(name);
        label.setBounds(x, y, 200, 40);
        return label;
    }

    /**
     * sends all of the current settings to the subject
     */
    private void updateSettings(){
        subject.setOptions(recursionDepth, childToParRatio, leftChAngle, rightChAngle,
                trunkLength, trunkWidth, trunkColor, leafColor);
    }

    /**
     * randomizes the values on all the sliders and randomizes the leaf and
     * trunk colors
     * @param depth     the slider for recursion depth
     * @param ratio     the slider for child-to-parent ratio
     * @param lAngle    the slider for the left child angle
     * @param rAngle    the slider for the right child angle
     * @param length    the slider for trunk length
     * @param width     the slider for trunk width
     */
    private void randomize(JSlider depth, JSlider ratio, JSlider lAngle, JSlider rAngle,
        JSlider length, JSlider width){
        Random random = new Random();

        trunkColor = new Color(random.nextInt(0, 255),random.nextInt(0, 255),
                random.nextInt(0, 255));
        leafColor = new Color(random.nextInt(0, 255),random.nextInt(0, 255),
                random.nextInt(0, 255));

        depth.setValue(random.nextInt(4, 20));
        ratio.setValue(random.nextInt(40, 80));
        lAngle.setValue(random.nextInt(0,90));
        rAngle.setValue(random.nextInt(0,90));
        length.setValue(random.nextInt(25,350));
        width.setValue(random.nextInt(0, 50));
    }
}
