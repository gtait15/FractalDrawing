import java.awt.*;

/**
 * Interface for fractal elements, such as branches, leaves, etc
 * @author Gwen Tait
 * @version 12-3-24
 */
public interface FractalElement {
    /**
     * Makes the element draw itself
     * @param g     the system graphics reference
     */
    void draw(Graphics g);
}
