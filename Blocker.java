import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Blocker extends JFrame implements ActionListener, ChangeListener{

	private float alpha = 0.95f;
	
	private JButton close, quit, pass;
	private JLabel opacity, sense;
	private JSlider slider, sliderSense;
	private JCheckBox autoStop;
	private EpBlocker epb;
	
	//this is a Timer of the .util type 
	private Timer triggerTimer;
	
	//setting up the window with the correct buttons in the correct positions
	public Blocker(EpBlocker e)
	{
		triggerTimer = new Timer();//initialization
		
		epb = e;
		this.setVisible(false);
		this.setAlwaysOnTop(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		
		this.getContentPane().setBackground(Color.BLACK);
		this.repaint();
		
		JPanel panel = new JPanel();
		this.add(panel);
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setSize(this.getSize());
		
		//setting up the sliders
		slider = new JSlider(JSlider.HORIZONTAL, 20,100, 95);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setAlignmentX(Component.CENTER_ALIGNMENT);
		slider.setForeground(Color.WHITE);
		slider.setBackground(Color.black);
		slider.addChangeListener(this);
		
		opacity = new JLabel("Opacity");
		opacity.setBackground(Color.BLACK);
		opacity.setForeground(Color.WHITE);
		opacity.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		sliderSense = new JSlider(JSlider.HORIZONTAL, 0, 100, 80);
		sliderSense.setMajorTickSpacing(10);
		sliderSense.setMinorTickSpacing(1);
		sliderSense.setPaintLabels(true);
		sliderSense.setAlignmentX(Component.CENTER_ALIGNMENT);
		sliderSense.setForeground(Color.WHITE);
		sliderSense.setBackground(Color.black);
		sliderSense.addChangeListener(this);
		
		//initialization and aesthetics of the buttons
		sense = new JLabel("Sensitivity");
		sense.setBackground(Color.BLACK);
		sense.setForeground(Color.WHITE);
		sense.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		close = new JButton("Close");		
		close.addActionListener(this);
		close.setAlignmentX(Component.CENTER_ALIGNMENT);
		close.setForeground(Color.WHITE);
		close.setBackground(Color.black);
		
		quit = new JButton("Exit Application");		
		quit.addActionListener(this);
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.setForeground(Color.WHITE);
		quit.setBackground(Color.black);
		
		pass = new JButton("Set Password");		
		pass.addActionListener(this);
		pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		pass.setForeground(Color.WHITE);
		pass.setBackground(Color.black);
		
		autoStop = new JCheckBox("Attempt to auto-stop video");
		autoStop.addActionListener(this);
		autoStop.setAlignmentX(Component.CENTER_ALIGNMENT);
		autoStop.setForeground(Color.WHITE);
		autoStop.setBackground(Color.BLACK);
		
		//setting up the panel by adding the elements in vertical fashion
		panel.add(Box.createVerticalGlue());
		panel.add(slider);
		panel.add(opacity);
		panel.add(Box.createVerticalGlue());
		panel.add(sliderSense);
		panel.add(sense);
		panel.add(Box.createVerticalGlue());
		panel.add(autoStop);
		panel.add(Box.createVerticalGlue());
		panel.add(close);
		panel.add(Box.createVerticalGlue());
		panel.add(quit);
		panel.add(Box.createVerticalGlue());
		panel.add(pass);
		panel.add(Box.createVerticalGlue());
		this.setOpacity(alpha);
		
	}
	
	//sets the alpha of the screenblocker
	public void setAlpha(float a)
	{
		alpha = a;
		this.setOpacity(alpha);
	}
	
	//show password entering dialogue
	public String showPass(String show, String message)
	{
		JPanel panel = new JPanel();
		JLabel label = new JLabel(message);
		JPasswordField passw = new JPasswordField(10);
		panel.add(label);
		panel.add(passw);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, show,
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[0]);
		if(option == 0) // pressing OK button
		{
		    char[] password = passw.getPassword();
		    return new String(password);
		}
		else
			return " ";
	}
	
	//returns the password button
	public JButton getPass()
	{
		return pass;
	}
	
	//method to check for pressing of the buttons
	public void actionPerformed(ActionEvent e) {
		//AbstractButton is parent to both JButton and JCheckBox
		AbstractButton src = (AbstractButton)e.getSource();
		if (src == close)
		{
			//this is sort of confusing, it's from StackOverflow, but it makes a timer for 1 second
			epb.setTrigger(false);
			triggerTimer.schedule(new TimerTask() {
				public void run() {
					epb.setTrigger(true);
				}
			}, 2000);
			this.setVisible(false);
		}
		if (src == quit)
		{
			if(epb.getPassState())
			{
				triggerTimer.schedule(new TimerTask() {
					public void run() {
						epb.setTrigger(true);
					}
				}, 2000);
				this.setVisible(false);
				if (showPass("Password Check", "Please enter the correct password:").equals(epb.getPass()))
				{
					System.exit(0);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The entered password is incorrect!", "Incorrect Password", JOptionPane.ERROR_MESSAGE);
					this.setVisible(true);
				}
			}
			else
				System.exit(0);
		}
		if (src == pass)
		{
			this.setVisible(false);
			epb.setPassword(showPass("Set password", "Enter a Password:"));
			
			if (!epb.getPass().equals(" "))
			{
				epb.changePassState();
				triggerTimer.schedule(new TimerTask() {
					public void run() {
						epb.setTrigger(true);
					}
				}, 2000);
				pass.setVisible(false);
			}
			
			this.setVisible(true);
			
		}
		if(src == autoStop)
		{
			//properly sets the epb value
			epb.setAttempt(autoStop.isSelected());
		}
	}

	
	//code for the slider
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (source == slider)
		{
			this.setAlpha(slider.getValue()*0.01f);
		}
		
		//this sets the sensitivity to a value between 40 to 190 based on the input from the slider
		else if(source == sliderSense)
		{
			epb.setSensitivity((190 - sliderSense.getValue() * 1.5));
		}
		
	}
	


}
