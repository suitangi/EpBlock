import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EpTutorial extends JFrame implements ActionListener{

	private JPanel MainPane, panel;
	private JScrollPane scrollPanel;
	public JButton close, top;
	
	public EpTutorial()
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);			
		this.setTitle("EpBlock Tutorial");
		this.setSize(new Dimension(450,500));
		this.setBackground(new Color(0, 0, 0));
		
		
		MainPane = new JPanel();
		panel = new JPanel();
		
        
        panel.setBackground(Color.black);
        
        Box box = Box.createVerticalBox();
        panel.add(box);
        box.add(new JLabel(" "));
        this.add(MainPane);
        
        JTextArea first = new JTextArea("Most of the time, as you use EpBlocker, you will "
        		+ "not notice it is open because it is just open in the background. To start"
        		+ " the program, click the \"Start\" button on the title screen. ");
        first.setLineWrap(true);
        first.setWrapStyleWord(true);
        first.setBackground(Color.black);
        first.setForeground(Color.white);
        box.add(first);
        
        box.add(new JLabel(" "));
        ImageIcon second = new ImageIcon("second.png");
        JLabel sec = new JLabel();
        sec.setIcon(second);
        sec.setAlignmentX(CENTER_ALIGNMENT);
        box.add(sec);
        box.add(new JLabel(" "));
        
        JTextArea third = new JTextArea("Before this, it's a good idea to make sure all the"
        		+ " settings are configured properly before you start. To do this before the"
        		+ " program has started, click on the \"Settings\" button in the top menu bar."
        		+ " To set a password, click the \"Password\" option. ");
        third.setLineWrap(true);
        third.setWrapStyleWord(true);
        third.setBackground(Color.black);
        third.setForeground(Color.white);
        box.add(third);
        
        box.add(new JLabel(" "));
        ImageIcon forth = new ImageIcon("forth.png");
        JLabel fort = new JLabel();
        fort.setIcon(forth);
        fort.setAlignmentX(CENTER_ALIGNMENT);
        box.add(fort);
        box.add(new JLabel(" "));
        
        JTextArea fifth = new JTextArea("After clicking the \"Password\" option, the password set"
        		+ "screen will appear. Type in the password you wish to use and click the \"OK\""
        		+ " button. This password is intended to stop an individual, such as a young child, "
        		+ "from closing the program accidentally.");
        fifth.setLineWrap(true);
        fifth.setWrapStyleWord(true);
        fifth.setBackground(Color.black);
        fifth.setForeground(Color.white);
        box.add(fifth);
        
        box.add(new JLabel(" "));
        ImageIcon sixth = new ImageIcon("sixth.png");
        JLabel six = new JLabel();
        six.setIcon(sixth);
        six.setAlignmentX(CENTER_ALIGNMENT);
        box.add(six);
        box.add(new JLabel(" "));
        
        JTextArea seventh = new JTextArea("If you want to see the full suite of settings, click the "
        		+ "\"Advanced\" option. You can also access this menu during normal program operation "
        		+ "by pressing \"Alt+Shift+S\". In addition to letting you set a password and allowing"
        		+ " you to toggle the video pause settings, this menu will allow you to control the opacity"
        		+ " of the block screen and the sensitivity of the program. However, we suggest using the "
        		+ "default ");
        seventh.setLineWrap(true);
        seventh.setWrapStyleWord(true);
        seventh.setBackground(Color.black);
        seventh.setForeground(Color.white);
        box.add(seventh);
        
        box.add(new JLabel(" "));
        ImageIcon eigth = new ImageIcon("eigth.png");
        JLabel eig = new JLabel();
        eig.setIcon(eigth);
        eig.setAlignmentX(CENTER_ALIGNMENT);
        box.add(eig);
        box.add(new JLabel(" "));
        
        JTextArea ninth = new JTextArea("The \"Close\" button closes the EpBlock window, but the "
        		+ "\"Exit Application\" button closes the application. The \"Close\" button will "
        		+ "allow EpBlock to continue running in the background, so you should choose this option."
        		+ "\n\nYou are now ready to use EpBlock!");
        ninth.setLineWrap(true);
        ninth.setWrapStyleWord(true);
        ninth.setBackground(Color.black);
        ninth.setForeground(Color.white);
        box.add(ninth);
        box.add(new JLabel(" "));
        
        MainPane.setLayout(new BorderLayout());
	    scrollPanel = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MainPane.add(scrollPanel, BorderLayout.CENTER);
        scrollPanel.getViewport().setViewSize(new Dimension(500, 550));
        scrollPanel.getVerticalScrollBar().setUnitIncrement(10);
		
        close = new JButton("Close");
        top = new JButton("Return to Top");
        close.addActionListener(this);
        top.addActionListener(this);
        
        close.setForeground(Color.WHITE);
		close.setBackground(Color.black);
		top.setForeground(Color.WHITE);
		top.setBackground(Color.black);
        
        Box box2 = Box.createHorizontalBox();;
        box.add(box2);
        box2.add(close);
        box2.add(new JLabel(" "));
        box2.add(top);
        box.add(new JLabel(" "));
        
        resetScroll();
        
	}
	public void resetScroll()
	{
		scrollPanel.getVerticalScrollBar().setValue(scrollPanel.getVerticalScrollBar().getMinimum());
	}

	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		if(src == close)
	    {
			this.dispose(); //exits the window
	    }if(src ==top)
	    {
	    	resetScroll();
	    }
		
	}
}