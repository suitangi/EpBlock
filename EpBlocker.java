//	import net.codejava.graphics;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.imageio.ImageIO;


//these are the imports for the global key listener and debugging output stuff
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.GlobalScreen;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EpBlocker implements ActionListener, NativeKeyListener{

	private int time;
	private String format = "jpg";
	private int scnw = Toolkit.getDefaultToolkit().getScreenSize().width/5;
	private int scnh = Toolkit.getDefaultToolkit().getScreenSize().height/5;
	private Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	private long beforeT, diffT;
	private double fps;
	private BufferedImage lastImg, thisImg;
	private int sens;
	private int pixelInterval = 3;
	private int xChunks = 4;
	private int yChunks = 8;
	private int temp = 0;			//holds the number of drastic changes for the current chunk
	private int flashyTiles = 0;
	private int width, height, clrA, redA, greenA, blueA, clrB, redB, greenB, blueB, sum = 0, frm;
	private Blocker bk;
	private int[] frms;
	private boolean passSet;
	private String password;
	
	private double sensitivity = 70.0;
	private boolean attemptStop = false;
	
	//these are to tell which keys are pressed
	private Boolean altPressed = false;
	private Boolean sPressed = false;
	private Boolean shiftPressed = false;
	
	//this field works with the timer from Blocker to make it not trigger itself
	private Boolean canTrigger = true;

	
	private Robot robot;
	public EpBlocker()
	{
		passSet = false;
		password = "";
		time = 0;
		Timer clock = new Timer(2, this); //sets timer to at least 2 millisecond, will be slower due to processing
		
		beforeT = System.currentTimeMillis();
		try
		{
			robot = new Robot();
		}
		catch(AWTException ex)
		{
			System.err.println(ex);
		}
		lastImg = robot.createScreenCapture(screen);
		frm = 50;
		bk = new Blocker(this);
		bk.setVisible(false);
		frms = new int[frm];
		clock.start();
	}
	
	//returns sensitivity
	public double getSensitivity()
	{
		return sensitivity;
	}
	
	//sets sensitivity to desired value
	public void setSensitivity(double x)
	{
		sensitivity = x;
	}
	public int isFlash(BufferedImage pre, BufferedImage post)
	//the algorithm for determining whether an image if "flashing"
	{	
		flashyTiles = 0;
		temp = 0;
		width = pre.getWidth();
		height = pre.getHeight();
		for(int i = 0; i < xChunks; i++)
		{
			for(int j = 0; j < yChunks; j++)	//Looks through each chunk of both images
			{
				//Checks the current chunk of the first image
				for (int x = 0; x < (width/xChunks); x += pixelInterval)
				{
				    for (int y = 0; y < (height/yChunks); y += pixelInterval)
				    {
				    	
				    	//gets the RGB value of the pixel
				    	 clrA=  pre.getRGB(x + i*(width/xChunks), y + j*(height/yChunks)); 
				    	 redA   = (clrA & 0x00ff0000) >> 16;
				    	 greenA = (clrA & 0x0000ff00) >> 8;
				    	 blueA  =  clrA & 0x000000ff;
				    	 clrB=  post.getRGB(x + i*(width/xChunks), y + j*(height/yChunks));  
				    	 redB   = (clrB & 0x00ff0000) >> 16;
				    	 greenB = (clrB & 0x0000ff00) >> 8;
				    	 blueB  =  clrB & 0x000000ff;
				    	 
				    	//finds the differences of rgb in pixel
				    	if(Math.abs(redA - redB) > 100
				    			|| Math.abs(greenA - greenB) > 125
				    			|| Math.abs(blueA - blueB) > 125)

				    	{
				    		//counts the amount of flashing pixels
				    		temp++;
				    	}
				    }
				}
				//if the number of drastic pixel changes found is larger than a-third of the pixels tested in the tiles
				if(temp > ((((width/xChunks)/pixelInterval) * (height/yChunks)/pixelInterval)) / 3)
				{
					flashyTiles++;
				}
				
				temp = 0;
			}
		}
		return flashyTiles;
		//returns the number of flashing tiles
	}

	//the main steps that run the algorithm
	public void actionPerformed(ActionEvent e) {
		time++;

			thisImg = robot.createScreenCapture(screen);
//			 for fps testing purposes:
//            diffT = System.currentTimeMillis() - beforeT;
//            if (diffT>0)
//            	fps = (fps*time+(1000/diffT))/(time+1);
//            System.out.println(fps); 
			
            frms[time%frm] = isFlash(thisImg, lastImg); //stores the 
            for(int i: frms)
            {
            	sum += i;
            }
//            System.out.println(sum); //for testing purposes
            if (sum > sensitivity && !bk.isShowing() && canTrigger)
            { //the isShowing() makes sure that it doesn't repeatedly trigger itself
            	if(attemptStop)//attempts to stop the video by hitting space
            	{
            		//presses space to pause the video
            		//presses escape to leave fullscreen mode
            		robot.keyPress(KeyEvent.VK_SPACE);
            		robot.keyPress(KeyEvent.VK_ESCAPE);
            		robot.keyRelease(KeyEvent.VK_SPACE);
            		robot.keyRelease(KeyEvent.VK_ESCAPE);
            	}
            	bk.setVisible(true);
            }

//		beforeT = System.currentTimeMillis(); //for fps testing purposes
		if (time%200 == 0)
			time = 0;
		lastImg = thisImg;
		sum = 0;
	}
	
	//this is called from the Blocker checkbox to change the attemptStop field
	public void setAttempt(boolean a)
	{
		attemptStop = a;
	}
	
	//method that's used by Blocker to change the trigger field
	public void setTrigger(boolean b)
	{
		canTrigger = b;
	}
	
	//method for finding the blockscreen instance
	public Blocker getBlock()
	{
		return bk;
	}
	//returns the string of the password
	public String getPass()
	{
		return password;
	}
	//sets the password string
	public void setPassword(String pass)
	{
		password = pass;
	}
	//gets the password state
	public boolean getPassState()
	{
		return passSet;
	}
	//changes the password state
	public void changePassState()
	{
		if (passSet)
			passSet = false;
		else
			passSet = true;
	}
	
	//this event triggers on key presses and sets each of the fields based on the keycode
	//this also makes the settings visible if all of the variables are true
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == 42)
		{
			shiftPressed = true;
		}
		else if(e.getKeyCode() == 56)
		{
			altPressed = true;
		}
		else if(e.getKeyCode() == 31)
		{
			sPressed = true;
		}
		if(shiftPressed && altPressed && sPressed)
		{
			bk.setVisible(true);
		}
       
    }

	//this does the same thing as nativeKeyPressed except for checks on release
    public void nativeKeyReleased(NativeKeyEvent e) {
    	if(e.getKeyCode() == 42)
		{
			shiftPressed = false;
		}
		else if(e.getKeyCode() == 56)
		{
			altPressed = false;
		}
		else if(e.getKeyCode() == 31)
		{
			sPressed = false;
		}
    }
    
    //returns the Blocker frame that is active and running
    public Blocker getBk()
    {
    	return bk;
    }

    //Necessary in order to implement NativeKeyListener
    public void nativeKeyTyped(NativeKeyEvent e) 
    {
    }

	//main mehtod
	public static void main(String[] args) {
		EpFrame main = new EpFrame();

		
	}


}
