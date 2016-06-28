import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class EpGUI extends JMenuBar implements ActionListener {
	
	private EpFrame epf;
	private JMenuItem about, help, info, exit, pass, other;
	private JMenu sett, file, aid;
	
	// constructor: sets up the menu bar with the correct item and layout
	public EpGUI(EpFrame e) {
		epf = e;
		sett = new JMenu("Settings");
		file = new JMenu("File");
		aid = new JMenu("Help");

		info = new JMenuItem("Information");
		help = new JMenuItem("Tutorial");
		exit = new JMenuItem("Exit");
		pass = new JMenuItem("Set Password");
		other = new JMenuItem("Advanced");
		about = new JMenuItem("About EpBlock");

		info.addActionListener(this);
		help.addActionListener(this);
		exit.addActionListener(this);
		pass.addActionListener(this);
		other.addActionListener(this);
		about.addActionListener(this);

		this.add(file);
		file.add(about);
		file.add(exit);

		this.add(sett);
		sett.add(pass);
		sett.add(other);

		this.add(aid);
		aid.add(help);
		aid.add(info);

	}


	public void actionPerformed(ActionEvent e) {
		JMenuItem src = (JMenuItem)e.getSource(); //listens for an action and casts it to a JMenuItem action
		
		if(src == exit)
	    { 
			System.exit(1); //exits the program
	    }
		if(src == other)
	    { 
			epf.init().getBlock().setVisible(true);
			epf.dispose();
	    }
		if(src == pass)
	    { 
			EpBlocker epb = epf.init();
			epb.setPassword(epb.getBk().showPass("Set Password", "Enter a password:"));
			
			if (!epb.getPass().equals(" "))
			{
				epb.changePassState();
				epb.getBk().getPass().setVisible(false);
			}
			epf.dispose();
	    }
		if(src == about)
	    { 
			JOptionPane.showMessageDialog(null, "EpBlocker is a program designed to assist \n"
					+ "epileptic computer users by blocking out "
					+ "\ndangerous visual stimuli. EpBlocker samples "
					+ "\nthe computer's visual data at a high rate and "
					+ "\nopens a window to protect you from flashing "
					+ "\nlights and other strobe effects. The EpBlock "
					+ "\nteam hopes that EpBlock will allow you to"
					+ "\nbrowse the web without fear of an epileptic"
					+ "\nfit. If you have questions, comments, or concerns,"
					+ "\nplease contact the team at EpBlocker@gmail.com.","About EpBlock", 1);
			
	    }
		if (src == help)
		{
			EpTutorial ept = new EpTutorial();
			ept.resetScroll();
		}
		if(src == info)
	    { 
			JOptionPane.showMessageDialog(null, "While EpBlocker can help you stop dangerous stimuli coming from many different"
					+ "\nsources like videos and websites, there are many computer usage habits that"
					+ "\ncan help reduce risk of an epileptic fit. Many of these measures are very easy!"
					+ "\n\nDon't use your computer in the dark- high levels of contrast between your screen "
					+ "\nand the environment increases risk of a seizure. "
					+ "\n\nDon't sit too close to your screen- for video games, try to sit at least 2 feet "
					+ "\naway from your screen.\n\nWear polaroid glasses- this will reduce eye strain. These "
					+ "\nare often sold as \"non-glare\" glasses.\n\nRest your eyes- don't spend a lot of "
					+ "\ntime on the screen if you're tired, and always remember to take frequent breaks."
					+ "\n\nAdapted from the Epilepsy Foundation \"Tips to Consider\" for individuals with"
					+ "\nphotosensitive epilepsy.","How to Avoid Epileptic Fits!", 1);
			
	    }
		
		
	}

}
