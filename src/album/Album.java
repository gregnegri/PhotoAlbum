package album;

import java.io.File;
import java.io.Serializable;

import java.net.URL;

import java.util.Random;
import java.util.Vector;

import albumui.AlbumPreviewPane;
import albumui.AlbumShelfPane;
import dialogui.EditAlbumDialog;
import mainfolder.MainMenuFrame;
import photoui.PrevShelfFrame;
import statics.Constants;

@SuppressWarnings("serial")
public class Album implements Serializable {
	
	protected String alb_name;
	protected Vector<File> albpics_files;
	protected Vector<URL> albpics_urls;
	protected File alb_prev = Constants.emptyImgFile;
	protected String date;
	protected String descr;
	
	/**
	 * 'caller' e il pannello che aprira l'interfaccia dell'album
	 */
	protected transient AlbumPreviewPane caller = null;
	
	protected transient PrevShelfFrame content_shelf;
	
	/**
	 * Crea un album con vettori d'immagini (URL e FILE) gia esistenti
	 * 
	 * @param name
	 * @param albpics_files
	 * @param albpics_urls
	 * @param date
	 * @param descr
	 */
	public Album(String name, Vector<File> albpics_files, Vector<URL> albpics_urls, String date, String descr)
	{
			this.SetName(name);
			this.albpics_files = albpics_files;
			this.albpics_urls = albpics_urls;
			this.SetDescr(descr);
			this.date = date;
			this.ChoosePreview();
	}
	/**
	 * Crea un album vuoto di default
	 * 
	 * @param name
	 * @param date
	 * @param descr
	 */
	public Album(String name, String date, String descr)
	{
		this(name, new Vector<File>(), new Vector<URL>(), date, descr);
	}
	
	/**
	 * Seleziona un'immagine di anteprima in modo randomico dal vettore di file delle immagini
	 */
	public void ChoosePreview()
	{
		if(!albpics_files.isEmpty())
		{
			int r_num;
			Random random = new Random();
			r_num = random.nextInt(albpics_files.size());
			alb_prev = albpics_files.elementAt(r_num);
		}
		else
		{
			alb_prev = Constants.emptyImgFile;
		}
	}
	
	/**
	 * Chiede all'album di creare un frame(PrevShelfFrame) in cui esporre il contenuto
	 */
	public void Open()
	{
		if(content_shelf != null)
			content_shelf.dispose();
		content_shelf = new PrevShelfFrame(this, caller);
	}
	
	/**
	 * Aggiunge un'immagine da un URL
	 * 
	 * @param img_url
	 */
	public void add(URL img_url)
	{
		if(content_shelf != null && content_shelf.isVisible())
		{
			Vector<URL> imgUrl_wrap = new Vector<URL>();
			imgUrl_wrap.add(img_url);
			content_shelf.AddFromUrl(imgUrl_wrap);
		}
		else
			albpics_urls.add(img_url);
	}
	
	/**
	 * Aggiunge un'immagine da un File
	 * 
	 * @param img_file
	 */
	public void add(File img_file)
	{
		if(content_shelf != null && content_shelf.isVisible())
		{
			Vector<File> imgFile_wrap = new Vector<File>();
			imgFile_wrap.add(img_file);
			content_shelf.AddFromFile(imgFile_wrap);
		}
		else
			albpics_files.add(img_file);
		this.ChoosePreview();
		this.caller.RefreshPrev();
	}
	
	/**
	 * Imposta il nome dell'album
	 * 
	 * @param name
	 */
	public void SetName(String name)
	{
		if(name.equals("") || name == null)
			this.alb_name = "Unnamed";
		else
			this.alb_name = name;
	}
	
	/**
	 * Imposta la descrizione dell'album
	 * @param descr
	 */
	public void SetDescr(String descr)
	{
		if(descr != null)
			this.descr = descr;
		else
			descr = "";
	}
	
	/**
	 * Ritorna il vettore contenente i file delle immagini dell'album
	 * 
	 * @return
	 */
	public Vector<File> GetPicsFiles()
	{
		return albpics_files;
	}
	
	/**
	 * Ritorna il vettore contenente gli URL delle immagini dell'album
	 * @return
	 */
	public Vector<URL> GetPicsUrls()
	{
		return albpics_urls;
	}
	
	/**
	 * Ritorna il nome dell'album
	 * 
	 * @return
	 */
	public String GetAlbName()
	{
		return alb_name;
	}
	
	/**
	 * Ritorna l'anteprima dell'album
	 * 
	 * @return
	 */
	public File GetPrev()
	{
		return alb_prev;
	}
	
	/**
	 * Ritorna la descrizione dell'albumt
	 * @return
	 */
	public String GetDescr()
	{
		return descr;
	}
	
	/**
	 * Ritorna la data di creazione dell'album
	 * @return
	 */
	public String GetDate()
	{
		return date;
	}
	
	/**
	 * Imposta il pannello che chiama l'apertura dell'interfaccia dell'album
	 * 
	 * @param caller
	 */
	public void SetCaller(AlbumPreviewPane caller)
	{
		this.caller = caller;
	}
	
	/**
	 * Dato un File cerca all'interno del vettore dei file un riferimento ad esso e lo rimuove (se presente)
	 * 
	 * @param img_f
	 */
	public void RemoveFile(File img_f)
	{
		for(File file : albpics_files)
		{
			if(file==img_f)
			{
				albpics_files.remove(file);
				caller.RefreshPrev();
			}
		}
	}
	
	/**
	 * Dato un URL cerca all'interno del vettore dei file un riferimento ad esso e lo rimuove (se presente)
	 * 
	 * @param img_u
	 */
	public void RemoveUrl(URL img_u)
	{
		for(URL url: albpics_urls)
		{
			if(url == img_u)
			{
				albpics_urls.remove(url);
			}
		}
	}
	
	/**
	 * Se e diverso da null chiama sul frame (PrevShelfFrame) mostrando il contenuto dell'album 'dispose()'
	 */
	public void DisposeContentShelf()
	{
		if(content_shelf != null)
			content_shelf.dispose();
	}
	
	/**
	 * Ritorna il pannello di anteprima dell'album
	 * 
	 * @return
	 */
	public AlbumPreviewPane getCaller()
	{
		return caller;
	}
	
	/**
	 * Dato il pannello di anteprima dell'album (AlbumPreviewPane) e il pannello che lo contiene (AlbumShelfPane)
	 * elimina l'album dalla lista globale degli album e chiede all'(AlbumShelfPane) di rimuovere
	 * il pannello di anteprima da esso
	 * 
	 * @param album_shelf
	 * @param album_pane
	 */
	public boolean RemoveAlbum(AlbumShelfPane album_shelf, AlbumPreviewPane album_pane)
	{
		album_shelf.RemoveFromContentPane(album_pane);
		album_shelf.GetAlbPanesList().remove(album_pane);
		album_shelf.validate();
		album_shelf.repaint();
		if(content_shelf != null)
			content_shelf.dispose();
		MainMenuFrame.GetAlbumList().remove(this);
		
		return true;
	}
	
	/**
	 * Genera un'istanza di dialog per l'eventuale modifica dei dati dell'album
	 */
	public void EditAlbum()
	{
		new EditAlbumDialog(this, caller);
	}
	
	@Override
	
	/**
	 * Metodo 'toString' sovrascritto in modo che restituisca solo il nome dell'album
	 */
	public String toString()
	{
		return alb_name;
	}

}
