package dialogui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import album.*;

@SuppressWarnings("serial")
public class OpenAlbumDialog extends JDialog implements ActionListener, KeyListener {

private Album album;
	
	private JLabel name_label = new JLabel("Name: ");
	private JTextField name_field;
	
	private JLabel descr_label = new JLabel("Description: ");
	private JTextArea descr_field;
	
	private JLabel date_label = new JLabel("Creation date: ");
	private JTextField date_field;
	
	private JButton open_butt = new JButton("Open");
	private JButton cancel_butt = new JButton("Cancel");
	
	private JPanel content_pane = new JPanel();
	
	public OpenAlbumDialog (Album album)
	{
		super();
		this.album = album;
		
		this.setFocusable(true);
		this.addKeyListener(this);
		
		String name = this.album.GetAlbName();
		this.setTitle("Album: "+name);
		//content_pane.setBackground(Color.WHITE);
		
		this.name_field = new JTextField(name, 15);
		this.name_field.setEditable(false);
		this.name_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.name_field.setBackground(Color.LIGHT_GRAY);
		
		this.descr_field = new JTextArea(album.GetDescr(), 3, 15);
		this.descr_field.setEditable(false);
		this.descr_field.setWrapStyleWord(true);
		this.descr_field.setLineWrap(true);
		this.descr_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.descr_field.setBackground(Color.LIGHT_GRAY);
		
		this.date_field = new JTextField(album.GetDate(), 15);
		this.date_field.setEditable(false);
		this.date_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.date_field.setBackground(Color.LIGHT_GRAY);
		
		this.cancel_butt.addActionListener(this);
		this.open_butt.addActionListener(this);
		this.open_butt.addKeyListener(this);
		
		GridBagLayout gridBag_l = new GridBagLayout();
		
		this.content_pane.setLayout(gridBag_l);
		
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		

		/**
		 * Settings prima colonna
		 */
		csr.insets = new Insets(10,15,0,0);
				
		csr.gridx = 0;
		csr.gridy = 0;
		gridBag_l.setConstraints(name_label, csr);
		this.content_pane.add(name_label);
		
		csr.insets = new Insets(20,15,0,0);
		
		csr.gridy = 1;
		csr.anchor = GridBagConstraints.NORTHWEST;
		gridBag_l.setConstraints(descr_label, csr);
		this.content_pane.add(descr_label);
		
		csr.gridy = 2;
		csr.anchor = GridBagConstraints.LINE_START;
		gridBag_l.setConstraints(date_label, csr);
		this.content_pane.add(date_label);
		
		csr.insets = new Insets(20,10,10,10);
		
		csr.gridy= 3;
		gridBag_l.setConstraints(cancel_butt, csr);
		this.content_pane.add(cancel_butt);
		
		
		/**
		 * Settings seconda colonna
		 */
		csr.insets = new Insets(10,0,0,15);
		
		csr.gridx = 1;
		csr.gridy = 0;
		csr.gridwidth = 3;
		gridBag_l.setConstraints(name_field, csr);
		this.content_pane.add(name_field);
		
		csr.insets = new Insets(20,0,0,15);
		
		csr.gridy = 1;
		gridBag_l.setConstraints(descr_field, csr);
		this.content_pane.add(descr_field);
		
		
		csr.gridy = 2;
		gridBag_l.setConstraints(date_field, csr);
		this.content_pane.add(date_field);
		
		csr.insets = new Insets(20,100,10,15);
		
		csr.gridy = 3;
		csr.gridx = 3;
		csr.gridwidth = 1;
		csr.anchor = GridBagConstraints.EAST;
		gridBag_l.setConstraints(open_butt, csr);
		this.content_pane.add(open_butt);
	
		this.setModal(true);
		this.add(content_pane);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Se viene premuto "invio" 
	 * apre il frame con il contenuto dell'album
	 * 
	 * @param k
	 */
	public void keyPressed(KeyEvent k) {
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			this.dispose();
			album.Open();
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == cancel_butt)
		{
			this.dispose();
		}
		/**
		 * Se viene premuto il pulsante "Open" 
		 * apre l'album (genera il suo "PreviewShelfFrame")
		 */
		else if(e.getSource() == open_butt)
		{
			this.dispose();
			album.Open();
		}	
	}
	
}
