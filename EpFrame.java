import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class EpFrame extends JFrame implements ActionListener{
	private JPanel picture;
	private JButton start,sett, quit;
	private Box box;
	
	//Frame of the title page
	public EpFrame()
	{
		//basic set up of the JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);			
		this.setTitle("EpBlock");
		this.setSize(new Dimension(500,600));
		this.setBackground(new Color(0, 0, 0));
		this.setJMenuBar(new EpGUI(this));
		
		//setting up the main logo
		picture = new JPanel();
		ImageIcon icon = new ImageIcon("Logo.png");
		
		JLabel pic = new JLabel();
		pic.setIcon(icon);
		picture.add(pic); //the logo
		picture.setBackground(new Color(0, 0, 0));
		this.setIconImage(icon.getImage());
		
		start = new JButton("Start"); //the start button
		start.addActionListener(this);
		sett = new JButton("Setting"); //the settings button
		sett.addActionListener(this);
		quit = new JButton("Exit"); //the exit button
		quit.addActionListener(this);
		
		Box box = Box.createVerticalBox(); //creating a box layout
		box.setBackground(new Color(0, 0, 0));
		Box box2 = Box.createHorizontalBox();// creating a second layout
		box2.setBackground(new Color(0, 0, 0));

		//adding and setting up the box2 layout
		box2.setOpaque(true);
		box.setOpaque(true);
		box.add(picture);
		box.add(box2);
		box.add(new JLabel(" "));
		box2.add(start);
		this.add(box);
		this.setVisible(true);
				
	}
	
	//creates and initializes an EpBlocker object
	public EpBlocker init()
	{
		EpBlocker bk = new EpBlocker();
		//This stuff here is from the github in order to initialize the listener
		try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
//        	for debugging purposes
//            System.err.println("There was a problem registering the native hook.");
//            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(bk);
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
        return bk;
	}
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource(); //listens for an action and casts it to a button action

		if(src ==start)
	    {
			init();
			this.dispose(); //exits the window
	    }
		
	}

}
	
	

