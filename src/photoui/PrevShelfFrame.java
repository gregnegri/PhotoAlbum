package photoui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.Vector;

import album.*;
import albumui.AlbumPreviewPane;
import dialogui.AddImgUrlDialog;
import dialogui.SlideShowDialog;
import popup.PrevShelfPopup;
import statics.Constants;
import statics.StaticMethods;
import tools.FlexibleGridPane;

/**
 * Mostra le anteprime delle foto contenute nell'album
 * 
 * @author grego
 */

@SuppressWarnings("serial")
public class PrevShelfFrame extends JFrame implements ActionListener {

	/**
	 * Attributo per la creazione del frame accedendo ad un album (o una sottoclasse)
	 */
	private Album album = null;
	
	/**
	 * Vettori per la creazione del frame
	 */
	private Vector<File> contentpics_files;
	private Vector<URL> contentpics_urls;
	private Vector<BufferedImage> contentpics = new Vector<BufferedImage>();
	private Vector<String> pics_names = new Vector<String>();
	
	/**
	 * Vettore che conterra i pannelli di tutte le anteprime
	 */
	private Vector<IconPrevPane> ico_panes = new Vector<IconPrevPane>();
	
	/**
	 * Attributi per creazione ui
	 */
	private FlexibleGridPane<IconPrevPane> base_panel;
	private JScrollPane scroll_pane;
	
	/**
	 * Componenti di JMenuBar
	 */
	private JMenuBar menuBar;
	
	private JMenu menuFile;
	private JMenuItem exit_item;
	
	/**
	 * menuAdd e sottomenu di menuFile
	 */
	private JMenu menuAdd;
	private JMenuItem addpic_file_it;
	private JMenuItem addpic_url_it;
	
	/**
	 * menuAct e menu parallelo a menuFile
	 */
	private JMenu menuAct;
	private JMenuItem start_slideshow_it;
	
	/**
	 * file_chooser implementa la ui per la navigazione del file system 
	 * per selezionare le immagini da inserire nell'album
	 */
	private JFileChooser file_chooser = new JFileChooser();
	
	private AlbumPreviewPane caller;
	
	
	public PrevShelfFrame (Album alb, AlbumPreviewPane caller)
	{
		super("Album: "+alb.GetAlbName());
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent w)
			{
				CloseOp();
			}
		});
		
		this.album = alb;
		this.caller = caller;
		this.contentpics_files = album.GetPicsFiles();
		this.contentpics_urls = album.GetPicsUrls();
		this.MakeFrame();
	}
	
	/**
	 * Crea e dispone i pannelli necessari
	 * per mostrare le anteprime delle foto contenute nell'album (IconShelfPane)
	 */
	public void MakeFrame()
	{
		/**
		 * Genero un nuovo pannello 'FlexibleGrid_pane' esclusivamente 
		 * per i pannelli di anteprima delle immagini
		 */
		base_panel = new FlexibleGridPane<IconPrevPane>();
		
		/**
		 * Estraggono le foto da file e url, poi le inseriscono nei pannelli e successivamente nel frame
		 */
		this.Files_extr(contentpics_files);
		this.Urls_extr(contentpics_urls);
		
		/**
		 * Crea tutti i componenti del MenuBar
		 */
		this.MakeMenuBar();
		
		this.setJMenuBar(menuBar);
	
		scroll_pane = new JScrollPane(base_panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.getContentPane().add(scroll_pane);
		this.scroll_pane.setComponentPopupMenu(new PrevShelfPopup(this));
		
		this.setSize(Constants.screenWidth, Constants.screenHeight);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setUndecorated(true);
		
		this.setVisible(true);		
	}

	/**
	 * Crea e dispone i componenti del 'mebubar' nel frame
	 */
	private void MakeMenuBar()
	{
		menuBar = new JMenuBar();
		menuFile = new JMenu("File");
		exit_item = new JMenuItem("Close Album");
		exit_item.addActionListener(this);
		
		menuAdd = new JMenu("Add");
		addpic_file_it = new JMenuItem("Add new picture from File");
		addpic_file_it.addActionListener(this);
		
		menuAct = new JMenu("Actions");
		start_slideshow_it = new JMenuItem("Start SlideShow");
		start_slideshow_it.addActionListener(this);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter( "Image Files", ImageIO.getReaderFileSuffixes());
		file_chooser.setFileFilter(filter);
		file_chooser.setMultiSelectionEnabled(true);
		
		addpic_url_it = new JMenuItem("Add new picture from URL");
		addpic_url_it.addActionListener(this);
		
		menuAdd.add(addpic_file_it);
		menuAdd.add(addpic_url_it);
		
		menuFile.add(menuAdd);
		menuFile.addSeparator();
		menuFile.add(exit_item);
		
		menuAct.add(start_slideshow_it);
	
		menuBar.add(menuFile);
		menuBar.add(menuAct);
	}


	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==exit_item)
		{
			this.CloseOp();
		}
		
		/**
		 * Se l'action event arriva dal menu item 'addpic_file_it'
		 * utilizzo un 'JFileChooser' per selezionare i file da aggiungere 
		 * (compatibili con i formati ammessi da 'ImageIO')
		 */
		else if(e.getSource() == addpic_file_it)
		{
			this.GetNewFiles();
		}
		
		/**
		 * Se l'action event arriva dal menu item 'addpic_url_it'
		 * genera un dialog che permette di aggiungere una o piu immagini da url (AddImgUrlDialog)
		 */
		else if(e.getSource() == addpic_url_it)
		{
			new AddImgUrlDialog(this);
		}
		
		/**
		 * Se l'action event arriva dal menu item 'start_slideshow_it'
		 * genera un dialog da cui selezionare il delay tra la visualizzazione di un immagine e la successiva 
		 * e poi avviera una slideshow in un nuovo frame con il delay indicato
		 */
		else if(e.getSource() == start_slideshow_it && ico_panes.size()>1)
		{
			new SlideShowDialog(ico_panes);
		}
	}
	
	/**
	 * Apre l'interfaccia del fileChooser e controlla il suo output
	 */
	public void GetNewFiles()
	{
		int result;
		result = file_chooser.showOpenDialog(PrevShelfFrame.this);
		if(result == JFileChooser.APPROVE_OPTION)
		{
			this.GetFilesFromChooser();
		}
		else if(result == JFileChooser.ERROR_OPTION)
			System.out.println("ERROR: error occurred while loading selected files");
	}
	
	/**
	 * Estrae e poi inserisce nel pannello le foto ottenute attraverso URL o File (public per essere richiamato da dialog)
	 * 
	 * @param img_urls
	 */
	public void AddFromUrl(Vector<URL> img_urls)
	{
		this.Urls_extr(img_urls);
	}
	public void AddFromFile(Vector<File> img_files)
	{
		this.Files_extr(img_files);
	}
	
	/**
	 * Prende dal file chooser i file selezionati e poi 
	 * li inserisce nell'album e successivamente nel frame delle anteprime 
	 * (elimina eventuali cloni presenti negli altri album)
	 */
	private void GetFilesFromChooser()
	{
		Vector<File> selected_files = new Vector<File>();
		File chosen_files[] = file_chooser.getSelectedFiles();
		for(File f: chosen_files)
		{
			boolean clone = false;
			
			/**
			 * Controlla che il file che voglio aggiungere non sia presente in questo album
			 */
			for(File f2: contentpics_files)
			{
				if(f.equals(f2))
					clone = true;
			}
			if(clone == false)
				selected_files.add(f);
		}
		StaticMethods.RemoveClonesFile(selected_files);
		this.Files_extr(selected_files);
	}
	
	/**
	 * Estrae da una lista di file le rispettive foto e effettua azioni diverse in base a di che lista si tratta:
	 * 1.se e la lista dell'album non ritorna nulla; 
	 * 2.se sono file aggiunti dall'utente e non ancora registrati nella lista dell'album allora
	 * ritorna una lista con le nuove foto e poi aggiunge i file alla lista dei file-immagine dell'album
	 * 
	 * @param img_files_list
	 */
	private void Files_extr(Vector<File> img_files_list)
	{
		if(!img_files_list.isEmpty())
		{
			for(File image_f : img_files_list)
			{
				BufferedImage image_i = null;
				String name = image_f.getName();
				
				try {
					image_i = ImageIO.read(image_f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						image_i = ImageIO.read(Constants.emptyImgFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				this.GenerateIcoPane(null, image_f, image_i, name);
				
				contentpics.add(image_i);
				pics_names.add(name);
				if(img_files_list != contentpics_files)
					contentpics_files.add(image_f);
			}
		}
	}
	
	/**
	 * Stesso funzionamento del precedente ('Files_extr') ma opera sugli url invece che sui file
	 * 
	 * @param img_urls_list
	 */
	private void Urls_extr(Vector<URL> img_urls_list)
	{	
		if(!img_urls_list.isEmpty())
		{
			for(URL image_u : img_urls_list)
			{
				BufferedImage image_i = null;
				String path = image_u.getPath();
				String name = "[W] "+path.substring(path.lastIndexOf('/')+1, path.length());
				
				try {
					image_i = ImageIO.read(image_u);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						image_i = ImageIO.read(Constants.emptyImgFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				this.GenerateIcoPane(image_u, null, image_i, name);
				
				contentpics.add(image_i);
				pics_names.add(name);
				if(img_urls_list != contentpics_urls)
					contentpics_urls.add(image_u);
			}
		}
	}
	
	/**
	 * Dati un file-immagine, una buffered image del suo contenuto e il nome del file
	 * crea un nuovo "IconPrevPane" e lo inserisce all'interno del relativo pannello
	 * 
	 * @param image_f
	 * @param image_i
	 * @param name
	 */
	private void GenerateIcoPane(URL image_u, File image_f, BufferedImage image_i, String name)
	{
		Image res_image = null;
		ImageIcon icon = null;
		IconPrevPane newIco_pane = null;
		
		res_image = image_i.getScaledInstance(Constants.icons_dim, Constants.icons_dim, BufferedImage.SCALE_SMOOTH);
		icon = new ImageIcon(res_image);
		
		newIco_pane = new IconPrevPane(icon, image_i, name, ico_panes, image_u, image_f);
		newIco_pane.SetShelfToPopupMenu(this);
		
		ico_panes.add(newIco_pane);
		base_panel.add(newIco_pane);
		base_panel.repaint();
		base_panel.validate();
	}
	
	/**
	 * Dato un pannello di anteprima di una foto dell'album
	 * rimuove dall'album il riferimento al file/url connessa all'anteprima 
	 * e poi rimuove il pannello di anteprima dal frame
	 * 
	 * @param ico_pane
	 */
	public void RemoveImageFromAlbum(IconPrevPane ico_pane)
	{
		File img_file = ico_pane.GetImgFile();
		URL img_url = ico_pane.GetImgUrl();
		
		if(img_file != null)
		{
			contentpics_files.remove(img_file);
		}
		else if(img_url != null)
		{
			contentpics_urls.remove(img_url);
		}
		else
		{
			System.out.println("ERROR WHILE REMOVING IMAGE: COULD NOT FIND ANY FILE OR URL REFERENCE");
		}
		
		ico_panes.remove(ico_pane);
		base_panel.remove(ico_pane);
		
		base_panel.validate();
		base_panel.repaint();
		scroll_pane.validate();
		scroll_pane.repaint();
	}
	
	public Vector<URL> GetAlbumUrls()
	{
		return contentpics_urls;
	}
	
	public Album GetAlbum()
	{
		return album;
	}
	
	/**
	 * Operazioni svolte alla chiusura del frame
	 */
	private void CloseOp()
	{
		caller.RefreshPrev();
		this.dispose();
	}
	
}
