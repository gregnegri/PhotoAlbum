package photoui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.Vector;

import statics.Constants;

@SuppressWarnings("serial")
public class PhotoViewerFrame extends JFrame implements ActionListener, KeyListener {

	/**
	 * Elementi dalla ui del pannello
	 */
	private JButton next_butt = new JButton("Next");
	private JButton back_butt= new JButton("Back");
	
	/**
	 * Ereditabile dal frame 'Slideshow' (quindi protected)
	 */
	protected JButton exit_butt = new JButton("Exit");
	
	private JLabel image_label;
	private JPanel base_pane;
	private JScrollPane base_scroll;
	private JPanel butt_northpane;
	private JPanel butt_southpane;
	
	/**
	 * Attributi da ricevere che saranno parametri del costruttore, per lo scorrimento delle immagini nel frame
	 */
	protected IconPrevPane prev_pane;
	protected Vector<IconPrevPane> icopane_list;
	
	/**
	 * Costruttore che ha come input:
	 * 1.il nome della foto(diventa titolo del frame),
	 * 2.il vettore dei pannelli delle anteprime dell'album, 
	 * 3.l'indice del pannello attuale all'interno del vettore di 2.
	 * 
	 * @param name
	 * @param icopane_list
	 * @param prev_pane
	 */
	public PhotoViewerFrame(String name, Vector<IconPrevPane> icopane_list, IconPrevPane prev_pane)
	{
		super(name);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		this.icopane_list = icopane_list;
		this.prev_pane = prev_pane;
		
		this.setSize(Constants.screenWidth,Constants.screenHeight);
		
		exit_butt.addActionListener(this);
		exit_butt.setFocusable(false);
		next_butt.addActionListener(this);
		next_butt.setFocusable(false);
		back_butt.addActionListener(this);
		back_butt.setFocusable(false);
		
		base_pane = new JPanel(new BorderLayout());
		base_pane.setBackground(Color.BLACK);
		
		butt_northpane = new JPanel(new GridLayout(0,10));
		butt_northpane.setBackground(Color.DARK_GRAY);
		butt_northpane.add(exit_butt);
		
		base_pane.add(butt_northpane, BorderLayout.NORTH);
		
		image_label = new JLabel(new ImageIcon(prev_pane.GetPic()));
		image_label.setBackground(Color.WHITE);
		
		base_scroll = new JScrollPane(image_label);
		base_scroll.getViewport().setBackground(Color.BLACK);
		
		base_pane.add(base_scroll, BorderLayout.CENTER);
		
		butt_southpane= new JPanel(new GridLayout(0,2));
		butt_southpane.add(back_butt);
		butt_southpane.add(next_butt);
		
		base_pane.add(butt_southpane, BorderLayout.SOUTH);
		
		this.add(base_pane);
		
		this.setBackground(Color.BLACK);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		this.setVisible(true);
	}
	
	/**
	 * Costruttore alternativo senza nome
	 * Genera il frame usando il vettore dei pannelli delle anteprime delle foto e 
	 * l'indice del pannello di cui si vuole rappresentare la foto
	 * 
	 * @param icopane_list
	 * @param index
	 */
	public PhotoViewerFrame(Vector<IconPrevPane> icopane_list, int index)
	{
		super(icopane_list.elementAt(index).GetName());
		this.icopane_list = icopane_list;
		
		this.setSize(Constants.screenWidth,Constants.screenHeight);
		
		base_pane = new JPanel(new BorderLayout());
		base_pane.setBackground(Color.BLACK);
		
		butt_northpane = new JPanel(new GridLayout(0,10));
		butt_northpane.setBackground(Color.DARK_GRAY);
		butt_northpane.add(exit_butt);
		
		exit_butt.addActionListener(this);
		
		base_pane.add(butt_northpane, BorderLayout.NORTH);
		
		image_label = new JLabel(new ImageIcon(icopane_list.elementAt(index).GetPic()));
		image_label.setBackground(Color.WHITE);
		
		base_scroll = new JScrollPane(image_label);
		base_scroll.getViewport().setBackground(Color.BLACK);
		
		base_pane.add(base_scroll, BorderLayout.CENTER);
		
		this.add(base_pane);
		
		this.setBackground(Color.BLACK);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		
		this.setVisible(true);
		
	}
	
	/**
	 * Scorre le foto a destra o a sinistra in base al pulsante cliccato
	 * (rispettivamente freccia destra e freccia sinistra sulla tastiera)
	 */
	public void keyPressed(KeyEvent k) {
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_RIGHT)
		{
			this.Next();
		}
		else if(key == KeyEvent.VK_LEFT)
		{
			this.Prev();
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()== next_butt)
		{
			this.Next();
		}
		else if(e.getSource()==back_butt)
		{
			this.Prev();
		}
		else if(e.getSource()==exit_butt)
		{
				this.dispose();
		}
	}
	
	/**
	 * Sostituisce la foto attualmente visualizzata e il suo titolo con
	 * quelli della foto all'indice dato in input nel vettore dei pannelli delle anteprime
	 */
	public void ChangeShownPic()
	{
		String new_title;
		BufferedImage new_image;
		
		new_title = prev_pane.GetName();
		new_image = prev_pane.GetPic();
		
		ImageIcon icon = new ImageIcon(new_image);
		this.setTitle(new_title);
		image_label.setIcon(icon);
		this.validate();
		this.repaint();
	}
	
	/**
	 * Fa avanzare (aumenta l'indice di 1) l'indice sul vettore delle anteprime delle foto usando il vettore come un array circolare
	 */
	protected void Next()
	{
		int size = icopane_list.size();
		int index = icopane_list.indexOf(prev_pane);
		
		if(index<(size-1))
		{
			index++;
			prev_pane = icopane_list.elementAt(index);
		}
		else if (index == size-1)
		{
			index=0;
			prev_pane = icopane_list.elementAt(index);
		}
	ChangeShownPic();
	}
	
	/**
	 * Analogo al precedente ma decresce l'indice (di 1) 
	 */
	protected void Prev()
	{
		int size = icopane_list.size();
		int index = icopane_list.indexOf(prev_pane);
		
		if(index>0)
		{
			index--;
			this.prev_pane = icopane_list.elementAt(index);
		}
		else if (index == 0)
		{
			index = size-1;
			this.prev_pane = icopane_list.elementAt(index);
		}
		ChangeShownPic();
	}
	
}
