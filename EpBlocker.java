//	import net.codejava.graphics;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

import javax.imageio.ImageIO;

public class EpBlocker implements ActionListener{

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

	
	private Robot robot;
	public EpBlocker()
	{
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
		bk = new Blocker();
		bk.setVisible(false);
		frms = new int[frm];
		clock.start();
	}
	public int isFlash(BufferedImage pre, BufferedImage post)
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
				    	
				    	 clrA=  pre.getRGB(x + i*(width/xChunks), y + j*(height/yChunks)); 
				    	 redA   = (clrA & 0x00ff0000) >> 16;
				    	 greenA = (clrA & 0x0000ff00) >> 8;
				    	 blueA  =  clrA & 0x000000ff;
				    	 clrB=  post.getRGB(x + i*(width/xChunks), y + j*(height/yChunks));  
				    	 redB   = (clrB & 0x00ff0000) >> 16;
				    	 greenB = (clrB & 0x0000ff00) >> 8;
				    	 blueB  =  clrB & 0x000000ff;
				    	if(Math.abs(redA - redB) > 100
				    			|| Math.abs(greenA - greenB) > 125
				    			|| Math.abs(blueA - blueB) > 125)

				    	{
				    		temp++;
				    	}
				    }
				}
				//if the number of drastic pixel changes found is larger than half of the pixels tested in the tiles
				if(temp > ((((width/xChunks)/pixelInterval) * (height/yChunks)/pixelInterval)) / 3)
				{
					flashyTiles++;
				}
				
				temp = 0;
			}
		}
		return flashyTiles;
	}

	public void actionPerformed(ActionEvent e) {
		time++;
//		try
//		{	
//            ImageIO.write(robot.createScreenCapture(screen), format, new File(time + "." +format));
//            if(time%24==0)
//            	System.out.println("timeeeeeeeeeeeeeeeeeeeeeeee");
			thisImg = robot.createScreenCapture(screen);
			System.out.println();
            diffT = System.currentTimeMillis() - beforeT;
            if (diffT>0)
            	fps = (fps*time+(1000/diffT))/(time+1);
            frms[time%frm] = isFlash(thisImg, lastImg);
            for(int i: frms)
            {
            	sum += i;
            }
//            System.out.println(sum);
            if (sum > 70)
            {
            	bk.setVisible(true);
            }
//            else
//            {
//            	bk.setVisible(false);
//            }
//            System.out.println(fps);
//		}
//		catch (IOException ex)
//		{
//            System.err.println(ex);
//        }
//		
		beforeT = System.currentTimeMillis();
		if (time%200 == 0)
			time = 0;
		lastImg = thisImg;
		sum = 0;
	}

	public static void main(String[] args) {
		EpBlocker main = new EpBlocker();
//        Blocker b = new Blocker();
//        b.setVisible(true);
//        b.repaint();
	}

}