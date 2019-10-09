package dialogui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.File;

import java.net.URL;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Vector;

import album.ProtectedAlbum;

import tools.Controller;
import mainfolder.MainMenuFrame;
import album.Album;
import albumui.AlbumPreviewPane;
import albumui.AlbumShelfPane;

@SuppressWarnings("serial")
public class MergeWSelectedAlbumDialog extends JDialog implements ActionListener, KeyListener {

	private JLabel select_label;
	private JComboBox<Album> select_list;
	private JButton cancel_butt = new JButton("Cancel");
	private JButton merge_butt = new JButton("Merge");
	
	private JPanel content_pane = new JPanel();
	
	private AlbumPreviewPane album_pane;
	private AlbumShelfPane album_shelf;
	private Album source_album;
	private Vector<Album> album_list;
	
	private Album destination_album;
	
	
	public MergeWSelectedAlbumDialog (AlbumShelfPane album_shelf, AlbumPreviewPane album_pane)
	{
		this.setTitle("Merge albums");
		this.setModal(true);
		this.addKeyListener(this);
		
		this.album_shelf = album_shelf;
		this.album_pane = album_pane;
		this.source_album = this.album_pane.GetAlbum();
		
		this.album_list = MainMenuFrame.GetAlbumList();
		
		if(album_list.size()<=1)
			this.dispose();
		else{
			this.select_label = new JLabel("Merge with: ");
		
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
			this.merge_butt.addActionListener(this);
			
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
			gridbag_l.setConstraints(merge_butt, csr);
			this.content_pane.add(merge_butt);
		
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
			this.Merge();				
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
		 * Se il pulsante "Merge" e premuto, chiama 'Merge' sull'album selezionato
		 */
		else if(e.getSource() == merge_butt)
		{
			this.Merge();
		}
	}
	
	/**
	 * Estrae il riferimento all'album migratore(se protetto viene richiesta conferma tramite la password),
	 * poi estrae i vettori di immagini(URL e FILE) del migratore e
	 * li aggiunge all'album destinatario. Alla fine cancella l'album migratore
	 */
	public void Merge()
	{
		destination_album = (Album) select_list.getSelectedItem();
		Controller valid = new Controller(); 
		
		if(source_album instanceof ProtectedAlbum)
		{
			((ProtectedAlbum) source_album).Validation(valid);
		}
		if(!(source_album instanceof ProtectedAlbum) || valid.GetValue())
		{
			Vector<File> filesToAdd_list = destination_album.GetPicsFiles();
			Vector<URL> UrlsToAdd_list = destination_album.GetPicsUrls();
		
			if(destination_album.RemoveAlbum(album_shelf, destination_album.getCaller()))
			{
				source_album.GetPicsFiles().addAll(filesToAdd_list);
				source_album.GetPicsUrls().addAll(UrlsToAdd_list);
				source_album.DisposeContentShelf();
				album_pane.RefreshPrev();
				this.dispose();
			}
		}
	}
	
}
