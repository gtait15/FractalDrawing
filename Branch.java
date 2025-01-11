import java.awt.*;

import static java.awt.BasicStroke.*;

/**
 * record for a Branch object which stores info to draw a line as a branch
 * in the fractal
 * @author Gwen Tait
 * @version 12-3-24
 *
 *
 * @param x1        the x-coordinate of the start of the line
 * @param y1        the y-coordinate of the start of the line
 * @param x2        the x-coordinate of the endpoint of the line
 * @param y2        the y-coordinate of the endpoint of the line
 * @param width     the width of the line
 * @param color     the color of the line
 */
public record Branch(int x1, int y1, int x2, int y2,
                     int width, Color color) implements FractalElement{
    /**
     * draws the branch using the stored variables
     * @param g  the passed system graphics reference
     */
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(width, CAP_ROUND, JOIN_ROUND));
        g2d.setColor(color);
        g2d.drawLine(x1,y1, x2, y2);
    }
}
