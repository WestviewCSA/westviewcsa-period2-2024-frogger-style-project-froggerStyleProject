import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background {
	private Image forward, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 2.7;		//change to scale image
	double scaleHeight = 2.7; 		//change to scale image

	public Background() {
		
		//load the main image (front or forward view) 
		
		
		forward 	= getImage("/imgs/"+"grassfield2.png"); //load the image for Tree
		
		//alter these
		width = 600;
		height = 800;
		
		//used for placement on the JFrame
		x = 0; 
		y = 0;
		
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//2nd constructor - allow setting x and y during consutrction
	public Background(int x, int y) {
		
		//call the deafult constructor for all the normal stuff
		this(); //invokes default constructor 
		
		//do the specific task for THIS constructor 
		this.x = x;
		this.y = y; 
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x	+=	vx;
		y	+=	vy;	
		
	
		init(x,y);

			g2.drawImage(forward, tx, null);
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
