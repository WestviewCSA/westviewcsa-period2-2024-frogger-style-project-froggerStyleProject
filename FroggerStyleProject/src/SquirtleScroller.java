import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class SquirtleScroller{
		private Image forward, backward, left, right; 	
		private AffineTransform tx;
		
		int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
		int width, height;
		int x, y;						//position of the object
		int vx, vy;						//movement variables
		double scaleWidth = .08;		//change to scale image
		double scaleHeight = .08; 		//change to scale image

		public SquirtleScroller() {
			
			//load the main image (front or forward view) 
			
			
			forward 	= getImage("/imgs/"+"squirtle.png"); //load the image for Tree
			
			//alter these
			width = 70;
			height = 80;
			
			//used for placement on the JFrame
			x = -width; //offscreen for now 
			y = 50;
			
			vx = 5;
			vy = 0;
			
			tx = AffineTransform.getTranslateInstance(0, 0);
			
			init(x, y); 				//initialize the location of the image
										//use your variables
			
		}
		
		/*
		 * Collision detection with main character class
		 */
		public boolean collided(Charmander character) {
			
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
		public SquirtleScroller(int x, int y) {
			
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
			
			//for infinite scrolling - teleport to the other side 
			//once it leaves the other side!
			if(x > 650) {
				x = -300; 
			}
			 
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
				URL imageURL = SquirtleScroller.class.getResource(path);
				tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tempImage;
		}

	}

