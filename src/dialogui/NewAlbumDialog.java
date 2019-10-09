package dialogui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Vector;

import mainfolder.MainMenuFrame;
import album.Album;
import album.ProtectedAlbum;

@SuppressWarnings("serial")
public class NewAlbumDialog extends JDialog implements ActionListener, KeyListener {

private Album album;
	
	private JLabel name_label = new JLabel("Name: ");
	private JTextField name_field;
	
	private JLabel descr_label = new JLabel("Description: ");
	private JTextArea descr_field;
	
	private JCheckBox protected_box = new JCheckBox("Protected");
	
	private JLabel passw_label = new JLabel("Password: ");
	private JPasswordField passw_field;
	
	private JLabel confirmP_label = new JLabel("Confirm Password: ");
	private JPasswordField confirmP_field;
	
	private JButton cancel_butt = new JButton("Cancel");
	private JButton apply_butt = new JButton("Apply");
	
	private JPanel content_pane = new JPanel();
	
	public NewAlbumDialog()
	{
		super();
		this.setTitle("New Album");
		this.setModal(true);
		
		this.name_field = new JTextField(15);
		this.name_field.addKeyListener(this);
		this.name_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		this.descr_field = new JTextArea(3,15);
		this.descr_field.addKeyListener(this);
		this.descr_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.descr_field.setWrapStyleWord(true);
		this.descr_field.setLineWrap(true);

		this.protected_box.addActionListener(this);
		this.protected_box.addKeyListener(this);
		
		this.passw_field = new JPasswordField(15);
		this.passw_field.addKeyListener(this);
		this.passw_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.passw_field.setEditable(false);
		this.passw_field.setBackground(Color.LIGHT_GRAY);
		
		this.confirmP_field = new JPasswordField(15);
		this.confirmP_field.addKeyListener(this);
		this.confirmP_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.confirmP_field.setEditable(false);
		this.confirmP_field.setBackground(Color.LIGHT_GRAY);
		
		this.cancel_butt.addActionListener(this);
		this.apply_butt.addActionListener(this);
		
		GridBagLayout gridBag_l = new GridBagLayout();
		this.content_pane.setLayout(gridBag_l);
		
		
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * Settings prima colonna
		 */
		csr.insets = new Insets(10,15,0,0);
		
		csr.anchor = GridBagConstraints.LINE_START;
		csr.gridx = 0;
		csr.gridy = 0;
		gridBag_l.setConstraints(name_label, csr);
		this.content_pane.add(name_label);
		
		csr.insets = new Insets(20,15,0,0);
		
		csr.gridy = 1;
		csr.anchor = GridBagConstraints.NORTHWEST;
		gridBag_l.setConstraints(descr_label, csr);
		this.content_pane.add(descr_label);
		
		csr.gridy = 3;
		csr.anchor = GridBagConstraints.LINE_START;
		gridBag_l.setConstraints(passw_label, csr);
		this.content_pane.add(passw_label);
		
		csr.gridy = 4;
		gridBag_l.setConstraints(confirmP_label, csr);
		this.content_pane.add(confirmP_label);
		
		csr.insets = new Insets(20,10,10,40);
		
		csr.gridy= 5;
		gridBag_l.setConstraints(cancel_butt, csr);
		this.content_pane.add(cancel_butt);
		
		/**
		 * Settings seconda colonna
		 */
		csr.insets = new Insets(10,0,0,15);
		
		csr.gridx = 1;
		csr.gridy = 0;
		csr.gridwidth = 2;
		gridBag_l.setConstraints(name_field, csr);
		this.content_pane.add(name_field);
		
		csr.insets = new Insets(20,0,0,15);
		
		csr.gridy = 1;
		gridBag_l.setConstraints(descr_field, csr);
		this.content_pane.add(descr_field);
		
		csr.gridy = 2;
		csr.gridwidth = 1;
		csr.anchor = GridBagConstraints.LINE_END;
		gridBag_l.setConstraints(protected_box, csr);
		this.content_pane.add(protected_box);
		
		csr.gridy = 3;
		csr.gridwidth = 2;
		gridBag_l.setConstraints(passw_field, csr);
		this.content_pane.add(passw_field);
		
		csr.gridy = 4;
		gridBag_l.setConstraints(confirmP_field, csr);
		this.content_pane.add(confirmP_field);
		
		csr.insets = new Insets(20,20,10,15);
		
		csr.gridy = 5;
		csr.gridx = 2;
		csr.gridwidth = 1;
		csr.anchor = GridBagConstraints.EAST;
		gridBag_l.setConstraints(apply_butt, csr);
		this.content_pane.add(apply_butt);
		
		this.name_field.requestFocus();
		
		this.add(content_pane);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Se viene premuto 'invio' si sposta sul prossimo campo da compilare,
	 * nel caso sia in fondo ai campi da compilare procede con la creazione di un album
	 * 
	 * @param k
	 */
	public void keyPressed(KeyEvent k) {
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			if(k.getSource()==name_field)
			{
				descr_field.requestFocus();
			}
			else if(k.getSource()==descr_field && !protected_box.isSelected())
			{
				this.MakeAlbumAndClose();
			}
			else if(k.getSource()==descr_field && protected_box.isSelected())
			{
				passw_field.requestFocus();
			}
			else if(k.getSource()==protected_box && protected_box.isSelected())
			{
				passw_field.requestFocus();
			}
			else if(k.getSource()==passw_field && !protected_box.isSelected())
			{
				this.MakeAlbumAndClose();
			}
			else if(k.getSource()==passw_field && protected_box.isSelected())
			{
				confirmP_field.requestFocus();
			}
			else if(k.getSource()==confirmP_field)
			{
				this.MakeAlbumAndClose();
			}
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == protected_box)
		{
			/**
			 * Nel caso la checkbox venga spuntata i campi di testo delle password diventano editabili
			 * e li faccio diventare bianchi in modo da renderli visibili come editabili
			 */
			if(protected_box.isSelected())
			{
				passw_field.setEditable(true);
				passw_field.setBackground(Color.WHITE);
				confirmP_field.setEditable(true);
				confirmP_field.setBackground(Color.WHITE);
			}
			/**
			 * Se la checkbox viene deselezionata l'operazione sopra viene svolta in senso opposto
			 * rendendo le password non editabili e impostando le caselle di testo grigie
			 */
			else
			{
				passw_field.setEditable(false);
				passw_field.setBackground(Color.LIGHT_GRAY);
				confirmP_field.setEditable(false);
				confirmP_field.setBackground(Color.LIGHT_GRAY);
			}
		}
		else if(e.getSource() == cancel_butt)
		{
			this.dispose();
		}
		else if(e.getSource() == apply_butt)
		{
			this.MakeAlbumAndClose();
		}
	}
	
	
	private void MakeAlbumAndClose()
	{
		Vector<Album> album_list = MainMenuFrame.GetAlbumList();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		/**
		 * Se viene premuto il pulsante "Apply" e la checkbox e deselezionata
		 * crea e aggiunge un nuovo album normale
		 */
		if(!protected_box.isSelected())
		{
			album = new Album(name_field.getText(), dtf.format(now), descr_field.getText());
			album_list.add(album);
			MainMenuFrame.addAlbumToShelf(album);
			this.dispose();
		}
		else if(protected_box.isSelected())
		{
			String password = new String(passw_field.getPassword());
			String conf_pass = new String(confirmP_field.getPassword());
			/**
			 * Se la checkbox e selezionata e le password nei due campi combaciano
			 * crea un album protetto con la password indicata
			 */
			if(password.equals(conf_pass))
			{
				album = new ProtectedAlbum(name_field.getText(), dtf.format(now), descr_field.getText(), new String(passw_field.getPassword()));
				album_list.add(album);
				MainMenuFrame.addAlbumToShelf(album);
				this.dispose();
			}		
			/**
			 * Se le password nei due campi non combaciano
			 * cancella le password nei due campi e i campi saranno circondati da un bordo rosso per evidenziare errore
			 */
			else if(!password.equals(conf_pass))
			{
				passw_field.setText("");
				confirmP_field.setText("");
			
				passw_field.setBorder(BorderFactory.createLineBorder(Color.RED));
				confirmP_field.setBorder(BorderFactory.createLineBorder(Color.RED));
			
				passw_field.requestFocus();
			}
		}
	}
	
}
