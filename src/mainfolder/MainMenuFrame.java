package mainfolder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Vector;

import javax.swing.*;

import album.*;
import albumui.AlbumShelfPane;
import dialogui.EditGalleryNameDialog;
import dialogui.NewAlbumDialog;
import popup.MainMenuPopup;
import statics.Constants;
import statics.StaticMethods;

@SuppressWarnings("serial")
public class MainMenuFrame extends JFrame implements ActionListener {

private String gallery_name;
	
	private String gallery_date;
	
	private String title;
	
	private static Vector<Album> album_list = new Vector<Album>();
	
	private static AlbumShelfPane album_shelf;
	
	private JMenuBar menu_bar;
	
	private JMenu file_menu;
	
	private JMenuItem editGalleryName_it;
	private JMenuItem addNewAlb_it;
	private JMenuItem exit_it;
	
	public MainMenuFrame(String gallery_name, String gallery_date, Vector<Album> album_list)
	{
		super();
		this.title = new String(" Gallery: "+gallery_name+"     Creation date: "+gallery_date);
		this.setTitle(title);
		this.gallery_name = gallery_name;
		this.gallery_date = gallery_date;
		this.setBackground(Color.DARK_GRAY);

		if(album_list != null)
			MainMenuFrame.album_list = album_list;
		
		this.MakeMenuBar();
		this.setJMenuBar(menu_bar);
		
		
		MainMenuFrame.album_shelf = new AlbumShelfPane(album_list);
		this.add(album_shelf);
		
		this.setSize(Constants.screenWidth, Constants.screenHeight);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainMenuFrame.album_shelf.setComponentPopupMenu(new MainMenuPopup());
		
		//this.setUndecorated(true);
		this.setVisible(true);
	}
	
	/**
	 * Crea il JMenuBar del frame
	 */
	private void MakeMenuBar()
	{
		menu_bar = new JMenuBar();
		
		file_menu = new JMenu("File");
		
		editGalleryName_it = new JMenuItem("Edit Gallery's name");
		editGalleryName_it.addActionListener(this);
		
		addNewAlb_it = new JMenuItem("Add new Album");
		addNewAlb_it.addActionListener(this);
		
		exit_it = new JMenuItem("Exit");
		exit_it.addActionListener(this);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent w)
			{
				CloseOp();
			}
		});
		
		file_menu.add(editGalleryName_it);
		file_menu.add(addNewAlb_it);
		file_menu.addSeparator();
		file_menu.add(exit_it);
		
		menu_bar.add(file_menu);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addNewAlb_it)
		{
			new NewAlbumDialog();
		}
		else if(e.getSource() == editGalleryName_it)
		{
			new EditGalleryNameDialog(this);
		}
		else if(e.getSource() == exit_it)
		{
			this.CloseOp();
		}
	}
	
	/**
	 * Getter del nome della galleria (si trova sulla cornice)
	 * 
	 * @return
	 */
	public String GetName()
	{
		return this.gallery_name;
	}
	
	/**
	 * Setter del nome della galleria
	 * 
	 * @param name
	 */
	public void SetName(String name)
	{
		if(name == null || name == "")
			name = "Unnamed";
		gallery_name = name;
	}
	
	/**
	 * Aggiorna il nome della galleria (sulla cornice del frame)
	 * 
	 * @param name
	 */
	public void ChangeTitle(String name)
	{
		if(name.equals("") || name == null)
		{
			name = "NoName";
		}
		this.SetName(name);
		this.title = new String(" Gallery: "+gallery_name+"     Creation date: "+gallery_date);
		this.setTitle(title);
	}
	
	/**
	 * Getter del vettore degli album rappresentati dal programma
	 * 
	 * @return
	 */
	public static Vector<Album> GetAlbumList()
	{
		return MainMenuFrame.album_list;
	}
	
	/**
	 * Aggiunge un nuovo album dell'apposito pannello "espositivo" direttamente dal 'MainMenuFrame'
	 * 
	 * @param newAlbum
	 */
	public static void addAlbumToShelf(Album newAlbum)
	{
		album_shelf.AddNewAlbum(newAlbum);
	}
	
	/**
	 * Salva lo stato su file prima di terminare l'esecuzione del programma
	 */
	private void CloseOp()
	{
		if(!StaticMethods.Save(gallery_name, gallery_date))
			System.out.println("ERROR WHILE SAVING");
		System.exit(1);
	}
	
}
