package dialogui;

import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.swing.*;

import mainfolder.MainMenuFrame;

@SuppressWarnings("serial")
public class NewGalleryDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel name_label = new JLabel("Name: ");
	private JTextField name_field;
	private JButton apply_butt = new JButton("Apply");
	
	private JPanel content_pane;
	
	public NewGalleryDialog()
	{
	this.setTitle("Make new Gallery");
	this.setModal(true);
	
	this.name_field = new JTextField(15);
	this.name_field.addKeyListener(this);
	
	this.apply_butt.addActionListener(this);
	
	GridBagLayout gridbag_l = new GridBagLayout();
	content_pane = new JPanel(gridbag_l);
	GridBagConstraints csr = new GridBagConstraints();
	csr.fill = GridBagConstraints.HORIZONTAL;
	
	csr.anchor = GridBagConstraints.LINE_START;
	csr.gridx = 0;
	csr.gridy = 0;
	gridbag_l.setConstraints(name_label, csr);
	this.content_pane.add(name_label);
	
	csr.gridx = 1;
	csr.weightx = 2;
	gridbag_l.setConstraints(name_field, csr);
	this.content_pane.add(name_field);
	
	csr.insets = new Insets(10,20,0,20);
	csr.weightx = 1;
	csr.gridx = 1;
	csr.gridy = 1;
	csr.anchor = GridBagConstraints.CENTER;
	gridbag_l.setConstraints(apply_butt, csr);
	this.content_pane.add(apply_butt);
	
	this.content_pane.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
	this.add(content_pane);
	
	this.pack();
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == apply_butt)
			this.MakeGallery();
		
	}

	public void keyPressed(KeyEvent k) {
		
	
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			if(k.getSource()==name_field)
			{
				this.MakeGallery();
			}
				
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Genera la galleria, con il nome dato dall'utente e la data attuale
	 */
	private void MakeGallery()
	{
		String name = "";
		String date = "";
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		
		name = name_field.getText();
		date = dtf.format(now);
		
		this.dispose();
		new MainMenuFrame(name,date,null);
	}
	
}
