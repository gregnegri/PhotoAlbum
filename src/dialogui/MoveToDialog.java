package dialogui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;

import java.net.URL;

import javax.swing.*;

import java.util.Vector;

import album.Album;
import album.ProtectedAlbum;
import photoui.IconPrevPane;
import photoui.PrevShelfFrame;
import tools.Controller;
import mainfolder.MainMenuFrame;

@SuppressWarnings("serial")
public class MoveToDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel select_label;
	private JComboBox<Album> select_list;
	private JButton cancel_butt = new JButton("Cancel");
	private JButton move_butt = new JButton("Move");
	
	private JPanel content_pane = new JPanel();
	
	private PrevShelfFrame shelf_frame;
	private IconPrevPane ico_pane;
	private Album source_album;
	private Vector<Album> album_list;
	
	private Album destination_album;
	
	public MoveToDialog(PrevShelfFrame shelf_frame, IconPrevPane ico_pane)
	{
		this.setTitle("Choose destination");
		this.setModal(true);
		this.addKeyListener(this);
		
		this.shelf_frame = shelf_frame;
		this.ico_pane = ico_pane;
		this.source_album = shelf_frame.GetAlbum();
		this.album_list = MainMenuFrame.GetAlbumList();
		
		this.select_label = new JLabel("Move to: ");

		if(album_list.size()<=1)
			this.dispose();
		else
		{
			DefaultComboBoxModel<Album> list_model = new DefaultComboBoxModel<Album>();
			this.select_list = new JComboBox<Album>(list_model);
			this.select_list.addKeyListener(this);
			for(Album album: album_list)
			{
				if(!source_album.equals(album))
				{
					list_model.addElement(album);
				}
			}
			this.select_list.setSelectedIndex(0);
			
			this.cancel_butt.addActionListener(this);
			this.move_butt.addActionListener(this);
		
			GridBagLayout gridbag_l = new GridBagLayout();
			
			this.content_pane.setLayout(gridbag_l);
		
			GridBagConstraints csr = new GridBagConstraints();
			csr.fill = GridBagConstraints.HORIZONTAL;
		
			csr.anchor = GridBagConstraints.LINE_START;
			csr.gridx = 0;
			csr.gridy = 0;
			csr.insets = new Insets(30,30,0,0);
			gridbag_l.setConstraints(select_label, csr);
			this.content_pane.add(select_label);
		
			csr.gridx = 1;
			csr.insets = new Insets(30,0,0,30);
			gridbag_l.setConstraints(select_list, csr);
			this.content_pane.add(select_list);
		
			csr.gridy = 1;
			csr.gridx = 0;
			csr.insets = new Insets(30,30,30,0);
			gridbag_l.setConstraints(cancel_butt, csr);
			this.content_pane.add(cancel_butt);
		
			csr.gridx = 1;
			csr.anchor = GridBagConstraints.LINE_END;
			csr.insets = new Insets(30,30,30,30);
			gridbag_l.setConstraints(move_butt, csr);
			this.content_pane.add(move_butt);
			
			this.add(content_pane);
		
			this.pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);	
		}
	}
	
	public void keyPressed(KeyEvent k) {
		
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			this.Move();				
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
			this.dispose();
		
		/**
		 * Se il pulsante "Move" e premuto, chiama 'Move' sull'album selezionato
		 */
		else if(e.getSource() == move_butt)
		{
			this.Move();
		}
	}
		
	/**
	 * Rimuove l'immagine dall'album selezionato e la trasferisce nell'album destinatario
	 * (se l'album destinatario e protetto chiedo la sua password per avere conferma) 
	 */
	public void Move()
	{
		destination_album = (Album) select_list.getSelectedItem();
		File img_f = ico_pane.GetImgFile();
		URL img_u = ico_pane.GetImgUrl();
		Controller valid = new Controller();
		
		if(destination_album instanceof ProtectedAlbum)
			((ProtectedAlbum)destination_album).Validation(valid);
		if(!(destination_album instanceof ProtectedAlbum) || valid.GetValue() == true)
		{
			if(img_f != null)
			{
				destination_album.add(img_f);
				shelf_frame.RemoveImageFromAlbum(ico_pane);
			}
			else if(img_u != null)
			{
				destination_album.add(img_u);
				shelf_frame.RemoveImageFromAlbum(ico_pane);
			}
			this.dispose();
		}
	}
	
}
