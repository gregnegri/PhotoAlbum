package dialogui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import java.util.Vector;

import photoui.IconPrevPane;
import photoui.SlideShowFrame;

@SuppressWarnings("serial")
public class SlideShowDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel time_label = new JLabel("Delay time [s]:");
	private JSlider slider;
	private JButton start_butt = new JButton("Start");
	
	private Vector<IconPrevPane> icopane_list;
	
	
	public SlideShowDialog(Vector<IconPrevPane> icopanes)
	{
		super();
		
		this.setTitle("SlideShow delay");
		this.addKeyListener(this);
		
		this.icopane_list = icopanes;
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 60, 5);
		this.slider.setMinorTickSpacing(1);
		this.slider.setMajorTickSpacing(10);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);
		this.slider.addKeyListener(this);
		
		start_butt.addActionListener(this);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		csr.gridx = 0;
		csr.gridy = 0;
		csr.weighty = 1;
		csr.insets =  new Insets(20, 0, 0, 0);
		csr.anchor =  GridBagConstraints.LINE_END;
		this.add(time_label, csr);
		
		csr.gridx = 1;
		csr.anchor = GridBagConstraints.LINE_START;
		csr.gridwidth = 2;
		csr.insets = new Insets(0,0,0,0);
		this.add(slider, csr);
		
		csr.gridx = 3;
		csr.gridwidth = 1;
		csr.insets = new Insets(0,0,0,20);
		this.add(start_butt, csr);
		
		this.setModal(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	public void keyPressed(KeyEvent k) {
		
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			this.Start();				
		}
	}
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void Start()
	{
		int delay = 5;
		
		delay = slider.getValue();
		if(delay<1)
			delay = 1;
		this.dispose();
		new SlideShowFrame(icopane_list, 0, delay);
	}

	/**
	 * Genera un frame che svolge lo slideshow delle immagini dell'album con il 'delay' selezionato
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == start_butt)
		{
			this.Start();
		}
		
	}
	
}
