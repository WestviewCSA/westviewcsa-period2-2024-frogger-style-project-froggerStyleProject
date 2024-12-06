import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Bottle {
    private Image seahImage;
    private AffineTransform tx;

    int width, height;
    int x, y; // Position of the object
    int vx, vy; // Velocity variables
    double scaleWidth = 1; // Scale image width
    double scaleHeight = 1; // Scale image height

    public Bottle() {
        seahImage = getImage("/imgs/seah.png"); // Load the shark image

        // Initialize size, position, and velocity
        width = (int) (30 * scaleWidth); // Set width from image dimensions
        height = (int) (20 * scaleHeight); // Set height from image dimensions

        x = 100; // Starting horizontal position
        y = 100; // Starting vertical position

        vx = 3; // Horizontal movement speed
        vy = 2; // Vertical movement speed

        tx = AffineTransform.getTranslateInstance(0, 0);
        init(x, y); // Initialize the location of the image
    }

    public Bottle(int x, int y, int width, int vx, int vy) {
        this();
        this.x = x;
        this.y = y;
        this.width = width;
        this.vx = vx;
        this.vy = vy;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public void paint(Graphics g) {
        // Cast graphics to Graphics2D for transformation
        Graphics2D g2 = (Graphics2D) g;

        // Update position based on velocity
        x += vx;
        y += vy;

        // Check for collisions with screen edges and reverse direction if necessary
        if (x <= 0 || x + width >= Frame.width) {
            vx = -vx; // Reverse horizontal direction
        }
        if (y <= 0 || y + height >= Frame.height) {
            vy = -vy; // Reverse vertical direction
        }

        // Update the translation matrix for rendering
        init(x, y);

        // Draw the Shark image
        g2.drawImage(seahImage, tx, null);

        // Optional: Draw the hitbox if debugging
        if (Frame.debugging) {
            g.setColor(Color.red);
           // g.drawRect(x, y, width, height);
        }
    }

    private void init(double a, double b) {
        // Initialize the position and scaling
        tx.setToTranslation(a, b);
        tx.scale(scaleWidth, scaleHeight);
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Bottle.class.getResource(path);
            if (imageURL != null) {
                tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    // Accessors and Mutators
    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVx() {
        return this.vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVy() {
        return this.vy;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}