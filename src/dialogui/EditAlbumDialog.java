package dialogui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import album.Album;
import album.ProtectedAlbum;
import albumui.AlbumPreviewPane;

@SuppressWarnings("serial")
public class EditAlbumDialog extends JDialog implements ActionListener, KeyListener {

	private Album album;
	
	private AlbumPreviewPane album_pane;
	
	private JLabel name_label = new JLabel("Name: ");
	private JTextField name_field;
	
	private JLabel descr_label = new JLabel("Description: ");
	private JTextArea descr_field;
	
	private JCheckBox changePsw_box = new JCheckBox("Change Password");
	
	private JLabel oldPassw_label = new JLabel("Old Password: ");
	private JPasswordField oldPassw_field;
	
	private JLabel newPassw_label = new JLabel("New Password: ");
	private JPasswordField newPassw_field;
	
	private JButton cancel_butt = new JButton("Cancel");
	private JButton apply_butt = new JButton("Apply");
	
	private JPanel content_pane = new JPanel();
	
	public EditAlbumDialog(Album album, AlbumPreviewPane album_pane)
	{
		super();
		this.album = album;
		this.album_pane = album_pane;
		this.setTitle("Edit album: "+album.GetAlbName());
		this.setModal(true);
		
		this.name_field = new JTextField(15);
		this.name_field.addKeyListener(this);
		this.name_field.setText(album.GetAlbName());
		this.name_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		this.descr_field = new JTextArea(3,15);
		this.descr_field.addKeyListener(this);
		this.descr_field.setWrapStyleWord(true);
		this.descr_field.setLineWrap(true);
		this.descr_field.setText(album.GetDescr());
		this.descr_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		this.changePsw_box.addActionListener(this);
		this.changePsw_box.addKeyListener(this);
		if(!(album instanceof ProtectedAlbum))
			changePsw_box.setEnabled(false);
		
		this.oldPassw_field = new JPasswordField(15);
		this.oldPassw_field.addKeyListener(this);
		this.oldPassw_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.oldPassw_field.setEditable(false);
		this.oldPassw_field.setBackground(Color.LIGHT_GRAY);
		
		this.newPassw_field = new JPasswordField(15);
		this.newPassw_field.addKeyListener(this);
		this.newPassw_field.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.newPassw_field.setEditable(false);
		this.newPassw_field.setBackground(Color.LIGHT_GRAY);
		
		this.cancel_butt.addActionListener(this);
		this.apply_butt.addActionListener(this);
		
		GridBagLayout gridBag_l = new GridBagLayout();
		this.content_pane.setLayout(gridBag_l);
		
		
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * Imposta la prima colonna
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
		gridBag_l.setConstraints(oldPassw_label, csr);
		this.content_pane.add(oldPassw_label);
		
		csr.gridy = 4;
		gridBag_l.setConstraints(newPassw_label, csr);
		this.content_pane.add(newPassw_label);
		
		csr.insets = new Insets(20,10,10,40);
		
		csr.gridy= 5;
		gridBag_l.setConstraints(cancel_butt, csr);
		this.content_pane.add(cancel_butt);
		
		/**
		 * Imposta la seconda colonna
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
		gridBag_l.setConstraints(changePsw_box, csr);
		this.content_pane.add(changePsw_box);
		
		csr.gridy = 3;
		csr.gridwidth = 2;
		gridBag_l.setConstraints(oldPassw_field, csr);
		this.content_pane.add(oldPassw_field);
		
		csr.gridy = 4;
		gridBag_l.setConstraints(newPassw_field, csr);
		this.content_pane.add(newPassw_field);
		
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
			else if(k.getSource()==descr_field && !changePsw_box.isSelected())
			{
				this.Edit();
			}
			else if(k.getSource()==descr_field && changePsw_box.isSelected())
			{
				oldPassw_field.requestFocus();
			}
			else if(k.getSource()==changePsw_box && changePsw_box.isSelected())
			{
				oldPassw_field.requestFocus();
			}
			else if(k.getSource()==oldPassw_field && !changePsw_box.isSelected())
			{
				this.Edit();
			}
			else if(k.getSource()==oldPassw_field && changePsw_box.isSelected())
			{
				newPassw_field.requestFocus();
			}
			else if(k.getSource()==newPassw_field)
			{
				this.Edit();
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
		
		if(e.getSource() == changePsw_box)
		{
			/**
			 * Nel caso la checkbox venga spuntata i campi di testo delle password diventano editabili
			 * e li faccio diventare bianchi in modo da renderli visibili come editabili
			 */
			if(changePsw_box.isSelected())
			{
				oldPassw_field.setEditable(true);
				oldPassw_field.setBackground(Color.WHITE);
				newPassw_field.setEditable(true);
				newPassw_field.setBackground(Color.WHITE);
			}
			
			/**
			 * Se la checkbox viene deselezionata l'operazione sopra viene svolta in senso opposto
			 * rendendo le password non editabili e impostando le caselle di testo grigie
			 */
			else
			{
				oldPassw_field.setEditable(false);
				oldPassw_field.setBackground(Color.LIGHT_GRAY);
				newPassw_field.setEditable(false);
				newPassw_field.setBackground(Color.LIGHT_GRAY);
			}
		}
		else if(e.getSource() == cancel_butt)
		{
			this.dispose();
		}
		else if(e.getSource() == apply_butt)
		{
			this.Edit();
		}
	}
	
	/**
	 * Modifica le proprieta dell'album in base ai dati forniti dall'utente
	 * (agisce in modo diverso in base al tipo di album e le scelte riguardo i campi da modificare)
	 */
	private void Edit()
	{
		/**
		 * Se l'album e protetto e si vuole modificarne la password
		 * viene effettuato un controllo sull'album stesso e solo se viene passato
		 * la modifica della password va a buon fine
		 */
		if(album instanceof ProtectedAlbum && changePsw_box.isSelected())
		{
			ProtectedAlbum prot_album = (ProtectedAlbum)album;
			if(prot_album.SetPassword(new String(oldPassw_field.getPassword()), new String(newPassw_field.getPassword())))
			{
				prot_album.SetName(name_field.getText());
				prot_album.SetDescr(descr_field.getText());
				album_pane.RefreshName();
				this.dispose();
			}
			else
			{
				oldPassw_field.setText("");
				newPassw_field.setText("");
				oldPassw_field.setBorder(BorderFactory.createLineBorder(Color.RED));
				newPassw_field.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
		
		/**
		 * Se la password non viene modificata l'album protetto si comporta come un album normale
		 */
		else
		{
			album.SetName(name_field.getText());
			album.SetDescr(descr_field.getText());
			album_pane.RefreshName();
			this.dispose();
		}
	}
	
}
