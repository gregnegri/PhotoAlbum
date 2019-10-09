package albumui;

import album.*;

import java.util.Vector;

import javax.swing.*;

import tools.FlexibleGridPane;

@SuppressWarnings("serial")
public class AlbumShelfPane extends JScrollPane{

private Vector<Album> album_list = new Vector<Album>();
	
	private Vector<AlbumPreviewPane> albumPrev_panes = new Vector<AlbumPreviewPane>();
	
	/**
	 * Uso la classe 'FlexibleGridPane' per il pannello che conterra i pannelli delle icone degli album
	 * e faccio in modo che sia compatibile soltanto con questi ultimi
	 */
	private FlexibleGridPane<AlbumPreviewPane> content_pane = new FlexibleGridPane<AlbumPreviewPane>();
	
	public AlbumShelfPane(Vector<Album> album_list)
	{
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.album_list = album_list;
		
		this.MakePrevPanes(album_list);
		this.AddPrevPanes(albumPrev_panes);
		this.getViewport().add(content_pane);
	}
	
	/**
	 * Datauna lista di album crea un pannello per ogni album e li inserisce
	 * nella lista apposita
	 * 
	 * @param albums
	 */
	private void MakePrevPanes(Vector<Album> albums)
	{
		if(albums != null)
		{
			for(Album alb: albums)
			{
				AlbumPreviewPane prev_pane = new AlbumPreviewPane(alb, album_list);
				prev_pane.SetShelfToPopupMenu(this);
				albumPrev_panes.add(prev_pane);
			}
		}
	}
	
	/**
	 * Data una lista di pannelli (AlbumPreviewPane) la inserisce pannelo per pannello all'interno del GridLayout
	 * 
	 * @param albumPrev_panes
	 */
	private void AddPrevPanes(Vector<AlbumPreviewPane> albumPrev_panes)
	{
		for(AlbumPreviewPane prevPane: albumPrev_panes)
		{
			content_pane.add(prevPane);
		}
	}
	
	/**
	 * Crea un nuovo album a runtime e lo espone sul pannello (finche il pannello e visibile)
	 * 
	 * @param newAlbum
	 */
	public void AddNewAlbum(Album newAlbum)	
	{
		AlbumPreviewPane newPane = new AlbumPreviewPane(newAlbum, album_list);
		newPane.SetShelfToPopupMenu(this);
		albumPrev_panes.add(newPane);
		content_pane.add(newPane);
		content_pane.validate();
		this.validate();
	}

	/**
	 * Restituisce il vettore contenente riferimenti ai pannelli di anteprima degli album (AlbumPreviewPane)
	 * 
	 * @return
	 */
	public Vector<AlbumPreviewPane> GetAlbPanesList()
	{
		return this.albumPrev_panes;
	}
	
	/**
	 * Rimuove dal pannello contenitore un 'AlbumPreviewPane'
	 * 
	 * @param album_pane
	 */
	public void RemoveFromContentPane(AlbumPreviewPane album_pane)
	{
		this.content_pane.remove(album_pane);
	}
	
}
