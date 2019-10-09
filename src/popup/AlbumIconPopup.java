package popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dialogui.MergeWSelectedAlbumDialog;
import album.Album;
import albumui.AlbumPreviewPane;
import albumui.AlbumShelfPane;

@SuppressWarnings("serial")
public class AlbumIconPopup extends JPopupMenu implements ActionListener {

	private JMenuItem delete_item = new JMenuItem("Delete");
	private JMenuItem merge_item = new JMenuItem("Merge");
	private JMenuItem edit_item = new JMenuItem("Edit");
	
	private AlbumPreviewPane album_pane;
	private AlbumShelfPane album_shelf;
	
	private Album album;
	
	public AlbumIconPopup(AlbumPreviewPane alb_pane)
	{
		this.album_pane = alb_pane;
		this.album = album_pane.GetAlbum();
		
		this.delete_item.addActionListener(this);
		this.merge_item.addActionListener(this);
		this.edit_item.addActionListener(this);
		
		this.add(delete_item);
		this.add(merge_item);
		this.add(edit_item);
	}
	
	/**
	 * Gli items del menu consentono l'eliminazione e la modifica dei dati dell'album selezionato 
	 * e la chiamata del dialog che effettua il merge tra due album
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == delete_item)
		{
			album.RemoveAlbum(album_shelf, album_pane);
		}
		
		else if(e.getSource() == merge_item)
		{
			new MergeWSelectedAlbumDialog(album_shelf, album_pane);
		}
		
		else if(e.getSource() == edit_item)
		{
			album.EditAlbum();
		}
	}
	
	/**
	 * Setta il pannello contenente le anteprime degli album nel menu 
	 * per poi poterlo sfruttare nelle interazioni
	 * 
	 * @param album_shelf
	 */
	public void SetShelf(AlbumShelfPane album_shelf)
	{
		this.album_shelf = album_shelf;
	}
	
}
