import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Marlin {
private static Image nemoImage;
private AffineTransform tx;

int dir = 0; // 0-forward, 1-backward, 2-left, 3-right
int width, height;
int x;
int y; // Position of the object
int vx, vy; // Movement variables
double scaleWidth = .5; // Scale image width
double scaleHeight = .5; // Scale image height
int spacing = 50;
//public int originalX;
//public int originalY;


public Marlin() {
	nemoImage = getImage("/imgs/Marlin.png");//Load the image for the

// Initialize size, position, and velocity
width = (int) (30*scaleWidth); // Set width from image dimensions
height =(int) (35*scaleHeight); // Set height from image dimensions

//originalX=width;
//originalY=500;

//x=originalX;
//y=originalY;
x = 500; // Start centered horizontally
y = 600; // Start at the bottom of the frame
//x = width;
//y = 500;
//originalX=x;
//originalY=y;
vx = 0; // Set velocity to 2 pixels per frame (speed of movement)
vy = 0; // No vertical movement

tx = AffineTransform.getTranslateInstance(0, 0);
init(x, y); // Initialize the location of the image



}
public Marlin(int x, int y, int spacing) {
	this();
	this.x=x;
	this.y=y;
	this.spacing=spacing;
		
}

public Rectangle getHitbox() {
	int scaleW = (int) (Marlin.nemoImage.getWidth(null)*scaleWidth);
	int scaleH = (int) (Marlin.nemoImage.getHeight(null)*scaleHeight);
	return new Rectangle(x,y,scaleW, scaleH);
}

public void paint(Graphics g) {
	//these are the 2 lines of code needed draw an image on the screen
	Graphics2D g2 = (Graphics2D) g;
	
	x+=vx;
	y+=vy;	
	
	init(x,y);

	g2.drawImage(nemoImage, tx, null);
	if(Frame.debugging) {
		g.setColor(Color.green );
		//g.drawRect(x,y,width, height);
		System.out.println("mar position"+x+ " "+y);
	}
	

}


/*public void move(int dir) {
	switch(dir) {
	case 0: //up
	vy=-8;
	break;
	case 1: 
	vy=8;
	break;
	case 2://left
	vx=-6;
	break;
	case 3: //right
		vx=6;
		break;
	case 4: //rest
		resetPosition();
		break;
	//x=700/2 - width/2;
	//y=770;
	//break;
	case 5: //log
	break;
	case 6://stop
	vx=0;
	vy=0;
	break;
	case 7: //log2
		vx=-5;
		break;
	
	
	}
}*/
private void init(double a, double b) {
// Initialize the position and scaling
tx.setToTranslation(a, b);
tx.scale(scaleWidth, scaleHeight);
}

private Image getImage(String path) {
Image tempImage = null;
try {
URL imageURL = Pufferfish.class.getResource(path);
if(imageURL!=null) {
tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
}else {
	System.err.println("image not found"+ path);
}
} catch (Exception e) {
e.printStackTrace();
}
return tempImage;
}
public void setVx(int i) {
	this.vx=i;
	
}
public void setVy(int i) {
	this.vy=i;
	
}
public int getY() {
	
	return this.y;
}
public void setY(int b) {
	this.y=b;
}
public void setX(int c) {
	this.x=c;
}
public int getX() {
	return this.x;
}
public int getWidth() {
	
	return this.width;
}
public int getHeight() {
	
	return this.height;
}
public void resetPosition() {
    this.x = 300; // Center horizontally
    this.y = 600; // Place at the bottom, with a small margin
    this.vx = 0; // Stop horizontal movement
    this.vy = 0; // Stop vertical movement
    System.out.println("Reset Position: x=" + this.x + ", y=" + this.y);
}
/*public void resetPosition() {
	this.x=getWidth();
	this.y=getHeight();
	this.vx=0;
	this.vy=0;
	System.out.println("reset block");
}*/

}