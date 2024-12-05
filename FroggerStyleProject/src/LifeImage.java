import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class LifeImage{
	private Image forward, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1.0;		//change to scale image
	double scaleHeight = 1.0; 		//change to scale image

	public LifeImage() {
		
		//load the main image (front or forward view) 
		
		
		forward 	= getImage("/imgs/"+"charmander-pixilart (1).png"); //load the image for Tree
		
		//alter these
		width = 60;
		height = 60;
		
		//used for placement on the JFrame
		x = Frame.width/2;
		y = Frame.height - height*2;
		
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	public boolean collided(LifeImage character) {
		
		//represent each object as a rectangle
		//then check if they are intersecting 
		Rectangle main = new Rectangle(
				character.getX(),
				character.getY(),
				character.getWidth(),
				character.getHeight()
				);
		Rectangle thisObject = new Rectangle(x, y, width, height);			
		
		
		//user built-in method to check intersection (COLLISION)
		return main.intersects(thisObject);
	}
	
	//2nd constructor - allow setting x and y during consutrction
	public LifeImage(int x, int y) {
		
		//call the deafult constructor for all the normal stuff
		this(); //invokes default constructor 
		
		//do the specific task for THIS constructor 
		this.x = x;
		this.y = y; 
	}
	
	public void resetPosition(){
		this.x = 300;
		this.y = 690; 
	}
	
	
	public void move(int dir) {
		
		switch(dir) {
	
		case 0: //hop up 
			y-= height*1.5; //move up a body-length
						
			
			break;
		case 1: //hop down
			y += height*1.5; //move down by a body length
			
			break;
			
		case 2: // hopleft
			x-= width*1.5;
			
			
			break; 
			
		case 3:// hop right
			x += width*1.5;
		
			break;
		
		
		
		
		
		}
	
		
	}

	
	
	/*
	 * Getters!
	 */
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height; 
	}
	
	public void setVx(int Vx) {
		vx = Vx;
	}
	
	public int getVx() {
		
		return vx/2;
	}


	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);

			g2.drawImage(forward, tx, null);
		//draw hit box based on x, y, width, height
		//for collision detection 
			if(Frame.debugging) {
				// draw hitbox only if debugging
				g.setColor(Color.green);
				g.drawRect(x, y, width, height);
			}
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = LifeImage.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
