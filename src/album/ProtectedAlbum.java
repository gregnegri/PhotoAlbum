package album;

import java.io.File;
import java.io.Serializable;

import java.net.URL;

//import java.util.Random;
import java.util.Vector;

import albumui.AlbumPreviewPane;
import albumui.AlbumShelfPane;
import dialogui.EditAlbumDialog;
import dialogui.ControllerDialog;
import mainfolder.MainMenuFrame;
import photoui.PrevShelfFrame;
import statics.Constants;
import tools.Controller;

/**
 * L'album protetto eredita da 'album' e aggiunge come attributo quello della password
 * 
 */


@SuppressWarnings("serial")
public class ProtectedAlbum extends Album implements Serializable {

	private String password;
	
	/**
	 * Crea un album con vettori di files e urls di immagini gia esistenti
	 * 
	 * @param name
	 * @param date
	 * @param descr
	 * @param password
	 */
	public ProtectedAlbum(String name, String date, String descr, String password) 
	{
		this(name, new Vector<File>(), new Vector<URL>(), date, descr, password);
	}
	/**
	 * Crea di default un album protetto senza immagini
	 * 
	 * @param name
	 * @param pics_f
	 * @param pics_u
	 * @param date
	 * @param descr
	 * @param password
	 */
	public ProtectedAlbum(String name, Vector<File> pics_f, Vector<URL> pics_u, String date, String descr, String password)
	{
		super(name, pics_f, pics_u, date, descr);
		this.password = password;
			this.ChoosePrev();
	}
	
	/**
	 * Dato un oggetto di classe "Validator" crea un istanza
	 * della classe "Validation_dial" per di verificare la password dell'album
	 * 
	 * @param valid
	 */
	public void Validation(Controller valid)
	{
		new ControllerDialog (valid, password, this.alb_name);
	}
	/**
	 * Date due password (String) in ingresso, se la prima corrisponde
	 * alla password dell'album, cambia la password con la seconda password data in ingresso
	 * 
	 * @param oldPassw
	 * @param newPassw
	 * @return
	 */
	public boolean SetPassword(String oldPassw, String newPassw)
	{
		if(this.password.equals(oldPassw))
		{
			this.password = newPassw;
			return true;
		}
		else
			return false;
			
	}

	@Override
	
	/**
	 * Sovrascrive i metodi di modifica dei dati, apertura e rimozione dell'album in modo da 
	 * verificare ogni volta che la password sia corretta prima che siano eseguiti
	 */
	
	public boolean RemoveAlbum(AlbumShelfPane album_shelf, AlbumPreviewPane album_pane)
	{
		Controller valid = new Controller();
		
		this.Validation(valid);
		
		
		if(valid.GetValue() == true)
		{
			album_shelf.RemoveFromContentPane(album_pane);
			album_shelf.GetAlbPanesList().remove(album_pane);
			album_shelf.validate();
			album_shelf.repaint();
			MainMenuFrame.GetAlbumList().remove(this);
		}
		return valid.GetValue();
	}
	
	public void EditAlbum()
	{
		Controller valid = new Controller();
		
		this.Validation(valid);
		
		if(valid.GetValue() == true)
		{
			new EditAlbumDialog(this, caller);
		}
	}
	
	public void Open()
	{
		Controller valid = new Controller();
		
		this.Validation(valid);
		
		
		if(valid.GetValue() == true)
		{
			if(content_shelf != null)
				content_shelf.dispose();
			this.content_shelf = new PrevShelfFrame(this, caller);
		}
	}
		
	/**
	 * Sovrascrive il metodo di scelta della anteprima
	 * impostandola ad un'anteprima mi default per gli album protetti
	 */
	public void ChoosePrev()
	{
		this.alb_prev = Constants.lockedImgFile;
	}
	
	/**
	 * Sovrascrive il metodo "toString" in modo da restituire il nome dell'album
	 * seguito da "-Protected" in modo da riconoscere gli album protected anche dal nome
	 */
	public String toString()
	{
		return new String(alb_name+" -Protected");
	}
	
}
