import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener  {
	public static boolean debugging=true;
	public static int width = 600;
	public static int height =800;
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int round = 0;
	boolean canLock = false;
	boolean locked = false;
	boolean riding =false;
	
	int score = 0;
	
	int lives = 3;
	 
	 private boolean gameOverMusicPlayed = false;
	Font myFont = new Font("Courier", Font.BOLD, 40);
	 
	public SimpleAudioPlayer backgroundMusic;
	public SimpleAudioPlayer    backgroundMusicLose ;
//SimpleAudioPlayer  soundHaha = new SimpleAudioPlayer ("haha.wav", false);
    	
	Marlin mar =new Marlin();
	Pufferfish[] row1 = new Pufferfish[8];
	Pufferfish[] row2 = new Pufferfish[8];
	Dory[] row3 = new Dory[4];
	Shark[] row4 = new Shark[9];
	Turtle[] row5 = new Turtle[7];
	Bottle[] bottles = new Bottle[3];
	 
	public Image backgroundImage;  // Declare an image for the background
	 public Image win; 
	 public Image lose;
	 	 
	public Frame() {
		JFrame f = new JFrame("Underwater Hunt");
		
		System.out.println(System.getProperty("user.dir"));
		backgroundImage = new ImageIcon("src/imgs/Background.png").getImage();
		 System.out.println("Background image dimensions: " + backgroundImage.getWidth(null) + "x" + backgroundImage.getHeight(null));
		 if (backgroundImage == null) {
		     System.out.println("Image not found or failed to load.");
		 } else {
		     System.out.println("Image loaded successfully.");
		 }
		 
		 
		 
		 win = new ImageIcon("src/imgs/win.png").getImage();
		 lose = new ImageIcon("src/imgs/lose2.jpg").getImage();
		 
		 backgroundMusic = new SimpleAudioPlayer("src/audio/bgc.wav", true); // Looping
	        backgroundMusicLose = new SimpleAudioPlayer("src/audio/gOver.wav", false); // One-time
	        backgroundMusic.play();
		 
		f.setSize(new Dimension(width, height));
		//f.setBackground(Color.BLUE);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
		f.setFocusable(true);
		f.addWindowFocusListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowGainedFocus(java.awt.event.WindowEvent e) {
		        f.requestFocusInWindow();
		    }
		});
		
		
		

		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		
		for(int i=0;i<row1.length;i++) {
			row1[i]=new Pufferfish((int)(i*110),100,110, 3);
		}
		for(int i=0;i<row2.length;i++) {
			row2[i]=new Pufferfish((int)(i*100),300,100, -2);
		}
		for(int i=0;i<row3.length;i++) {
			row3[i]=new Dory((int)(i*500),220,300, 7);
			// row3[i] = new Dory(row3); 
		}
		for(int i=0;i<row4.length;i++) {
			row4[i]=new Shark((int)(i*200),20,200, 5);
		}
		for(int i=0;i<row5.length;i++) {
			row5[i]=new Turtle((int)(i*120),450,110, 5);
		}
		
		for (int i = 0; i < bottles.length; i++) {
		    bottles[i] = new Bottle(100 * i, 100, 50, (int) (Math.random() * 5) + 2, (int) (Math.random() * 5) + 2);
		}
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, e->{repaint();});
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public void restartGame() {
		System.out.println("restarting the game");
		score = 0;
        lives = 3;

        gameOverMusicPlayed = false;  // Reset flag for music playing
        
        // Stop the lose music and restart the background music
        backgroundMusicLose.stop();  
        backgroundMusic.stop();  // Stop any background music
        backgroundMusic = new SimpleAudioPlayer("src/audio/bgc.wav", true); 
        backgroundMusic.play();  // Restart the background music

        mar.resetPosition();

        // Reinitialize positions
        for (int i = 0; i < row1.length; i++) row1[i] = new Pufferfish(i * 110, 100, 110, 2);
        for (int i = 0; i < row2.length; i++) row2[i] = new Pufferfish(i * 100, 300, 100, -2);
        for (int i = 0; i < row3.length; i++) row3[i] = new Dory(i * 500, 220, 300, 7);
        for (int i = 0; i < row4.length; i++) row4[i] = new Shark(i * 200, 20, 200, 5);
        for (int i = 0; i < row5.length; i++) row5[i] = new Turtle(i * 120, 450, 110, 5);
        for (int i = 0; i < bottles.length; i++) {
            bottles[i] = new Bottle(100 * i, 100, 50, (int) (Math.random() * 5) + 2, (int) (Math.random() * 5) + 2);
        }

        repaint();
    }
@Override
public void paint(Graphics g) {

	super.paintComponent(g);
	
	if (backgroundImage != null) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    } else {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight()); // Default background if image not loaded
    }
	
	 // Draw background image (stretch to fit the entire window)
   
	
	riding =false;
	for(Dory d :row3) {
		d.paint(g);   
		
		if(mar.getHitbox().intersects(d.getHitbox())){
			mar.setVx(d.getVx());
			riding = true; 
			System.out.println(riding +"at"+mar.getX()+" "+mar.getY());
			break;
		}
		
	}	
	
	if(!riding) {
		
		if(mar.getY()>800) {
			mar.resetPosition();
			lives--;
			System.out.println("end of the frame touched Y");
			mar.paint(g);
		
		}
		else if(mar.getX()>600) {
			mar.resetPosition();
			lives--;
			System.out.println("end of the frame touched X");
			mar.paint(g);
	}
		
	}
	
	
	for(Pufferfish p: row1) {
		p.paint(g);
		if(mar.getHitbox().intersects(p.getHitbox())){
			mar.resetPosition();
			lives--;
		
		}
	}
	for(Pufferfish p: row2) {
		p.paint(g);
		
		if(mar.getHitbox().intersects(p.getHitbox())){
			mar.resetPosition();
			lives--;
		}
	}
	
	for(Shark s: row4) {
		s.paint(g);
		if(mar.getHitbox().intersects(s.getHitbox())){
				mar.resetPosition();
				lives--;
		}
	}
	
	for(Turtle t: row5) {
		t.paint(g);
		if(mar.getHitbox().intersects(t.getHitbox())){
				mar.resetPosition();
				lives--;
		}
	}
	
	for(Bottle b: bottles) {
		b.paint(g);
		if(mar.getHitbox().intersects(b.getHitbox())){
				mar.resetPosition();
				
		}
	}
	
	if (mar.getY() <= 0) {
        score ++; // Increase score by 10
        System.out.println("Score increased! Current score: " + score);
        mar.resetPosition(); // Reset Marlin's position
    }
	   
	
	if(score >= 3) {
		g.drawImage(win, 0, 0, getWidth(), getHeight(), this);
	}
	
	if(lives <= 0) {
		g.drawImage(lose, 0, 0, getWidth(), getHeight(), this);
		g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("Game Over!", 200, 300);
        g.drawString("Click to Restart", 150, 400);
        if (!gameOverMusicPlayed) {
            backgroundMusic.stop();
            backgroundMusicLose = new SimpleAudioPlayer("src/audio/gOver.wav", false); 
            backgroundMusicLose.play();
            gameOverMusicPlayed = true;
        }
        
	}
	mar.paint(g);
	if(debugging) {
		g.setColor(Color.RED);
		//g.drawRect(mar.getX(),mar.getY(),mar.getWidth(),mar.getHeight());
	}
	
	g.setFont(myFont);
    g.setColor(Color.BLUE);
    g.drawString("Score: " + score, 20, 700);
	g.drawString("Lives: " + lives, 400, 700);
}
public static void main(String[] arg) {
	new Frame();
	
}

@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		if (lives <= 0) { // Restart game when clicked after losing
			System.out.println("restarted game");
			restartGame();
        }
	
		
	}

	

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("keypress block");
	    int keyCode = arg0.getKeyCode();

	    if (keyCode == KeyEvent.VK_W) {
	        mar.setVy(-5); // Move up
	    } else if (keyCode == KeyEvent.VK_S) {
	        mar.setVy(5); // Move down
	    } else if (keyCode == KeyEvent.VK_A) {
	        mar.setVx(-5); // Move left
	    } else if (keyCode == KeyEvent.VK_D) {
	        mar.setVx(5); // Move right
	    }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	    int keyCode = arg0.getKeyCode();

	    if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
	        mar.setVy(0); // Stop vertical movement
	    } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
	        mar.setVx(0); // Stop horizontal movement
	    }
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	    public void actionPerformed(ActionEvent e) {
	        repaint();
	    }
	}


