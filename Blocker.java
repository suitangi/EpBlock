import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Blocker extends JFrame implements ActionListener, ChangeListener{

	private float alpha = 0.95f;
	
	private JButton close, quit;
	private JSlider slider;
	
	public Blocker()
	{
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
		panel.setLayout(new GridLayout(5,1));
		panel.setSize(this.getSize());
		
		slider = new JSlider(JSlider.HORIZONTAL, 0,100, 95);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setForeground(Color.WHITE);
		slider.addChangeListener(this);
		
		close = new JButton("Close");		
		close.addActionListener(this);
		
		quit = new JButton("Exit Application");		
		quit.addActionListener(this);
		
		panel.add(slider);
		panel.add(close);
		panel.add(quit);
//		panel.add(new JButton("ttt"), BorderLayout.CENTER);
		this.setOpacity(alpha);
		
	}
	public void setAlpha(float a)
	{
		alpha = a;
		this.setOpacity(alpha);
	}

	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton)e.getSource();
		if (src == close)
		{
			this.setVisible(false);
		}
		if (src == quit)
		{
			System.exit(0);;
		}
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (source == slider)
		{
			this.setAlpha(slider.getValue()*0.01f);
		}
		
	}
	


}
