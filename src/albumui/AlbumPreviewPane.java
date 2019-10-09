package albumui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import java.util.Vector;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import popup.AlbumIconPopup;
import dialogui.OpenAlbumDialog;
import album.*;
import statics.Constants;
import tools.*;

@SuppressWarnings("serial")
public class AlbumPreviewPane extends IconPane {
	
	/**
	 * Album rappresentato dal pannello e la lista dei pannelli
	 */
	private Album album;
	
	/**
	 * Attributi de il file contenente l'immagine e l'icona dell'immagine(anteprima)
	 */
	private File image_f = Constants.emptyImgFile;
	private ImageIcon prev = null;
	
	/**
	 * Nome e data di creazione dell'album
	 */
	private String name;
	private String date;
	
	/**
	 * Componenti base dell'interfaccia del pannello
	 */
	private JLabel prev_label;
	private JLabel date_label;
	private JTextField name_field;
	
	/**
	 * Componenti opzionali per gli album protetti
	 */
	private boolean protected_alb = false;
	
	/**
	 * Menu popup per selezionare le future operazioni svolgibili sull'album considerato
	 */
	private AlbumIconPopup popup_menu;
	
	
	
	public AlbumPreviewPane(Album album, Vector<Album> album_list) 
	{
		super();
		
		this.album = album;
		this.popup_menu = new AlbumIconPopup(this);
		
		this.album.SetCaller(this);
		
		this.name = album.GetAlbName();
		this.date = album.GetDate();
		
		this.prev_label = new JLabel();
		
		if(album instanceof ProtectedAlbum)
			protected_alb = true;
		
		this.SetPrev();
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;

		csr.gridx = 0;
		csr.gridy = 3;
		csr.gridwidth = 1;

		prev_label = new JLabel(prev);
		this.prev_label.setComponentPopupMenu(popup_menu);
		AddClickListen(prev_label);
		/**
		 * Imposto il bordo che cambiera dinamicamente quando il cursore del mouse
		 * si sovrapporra all'anteprima dell'album
		 */
		prev_label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		prev_label.setOpaque(true);
		prev_label.setBackground(Color.WHITE);
		add(prev_label, csr);
		
		Font font = new Font("myfont", Font.ITALIC, 15);
		
		csr.gridy = 1;
		csr.anchor = GridBagConstraints.CENTER;
		name_field = new JTextField(name, 5);
		name_field.setCaretPosition(0);
		name_field.setHorizontalAlignment(JTextField.CENTER);
		name_field.setFont(font);
		name_field.setEditable(false);
		name_field.setBackground(Color.DARK_GRAY);
		name_field.setForeground(Color.WHITE);
		name_field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		add(name_field, csr);
		
		csr.insets = new Insets(5,0,0,0);
		csr.anchor = GridBagConstraints.PAGE_START;
		csr.gridy = 5;
		csr.gridx = 0;
		this.date_label = new JLabel("Creation date: "+this.date);
		this.date_label.setForeground(Color.LIGHT_GRAY);
		this.add(date_label, csr);
		}


	private void AddClickListen(JLabel lab)
	{
		
		lab.addMouseListener(new MouseAdapter(){
			@Override
			/**
			 * azioni conseguenti agli eventi individuati del listener
			 * 1.click sinistro; 
			 * 2.puntatore che si sovrappone al componente; 
			 * 3.puntatore che, al contrario del precedente, esce dall'area relativa dal componente (opposto di 2.)
			 */
			public void mouseClicked(MouseEvent m)
			{
				if(m.getButton() == MouseEvent.BUTTON1)
				{
					new OpenAlbumDialog(album);
				}
			}
			public void mouseEntered(MouseEvent m)
			{	
				if(protected_alb)
				{
					prev_label.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
					name_field.setBorder(BorderFactory.createLineBorder(Color.ORANGE,1));
				}
				else
				{
					prev_label.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
					name_field.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
				}
				prev_label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent m)
			{
					prev_label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
					name_field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					prev_label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		);
		}
	
	/**
	 * Imposta l'anteprima dell'album in base all'anteprima dell'oggetto "Album" a cui si collega
	 */
	private void SetPrev()
	{
		BufferedImage image = null;
		Image res_image;
		File new_prev_f;
		new_prev_f = album.GetPrev();
		if(image_f != new_prev_f || prev == null)
		{
			this.image_f = new_prev_f;
		
			try {
				image = ImageIO.read(image_f);
			} catch (IOException e) {
				try {
					image = ImageIO.read(Constants.emptyImgFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			if(image != null)
			{
				res_image = image.getScaledInstance(Constants.icons_dim, Constants.icons_dim, Image.SCALE_SMOOTH);
				this.prev = new ImageIcon(res_image);
			}
			this.prev_label.setIcon(prev);
		}
	}
	
	/**
	 * Restiuisce l'album rappresentato dal pannello
	 * 
	 * @return
	 */
	public Album GetAlbum()
	{
		return album;
	}
	
	/**
	 * Da al menu popup un riferimento al pannello contenente tutte le anteprime degli album 
	 * (in questo modo il menu popup potra interagirvi)
	 * 
	 * @param album_shelf
	 */
	public void SetShelfToPopupMenu(AlbumShelfPane album_shelf)
	{
		this.popup_menu.SetShelf(album_shelf);
	}
	
	/**
	 * Seleziona una nuova anteprima per l'album e la sostituisce a quella gia presente nel pannello
	 */
	public void RefreshPrev()
	{
		this.album.ChoosePreview();
		this.SetPrev();
	}
	
	/**
	 * Nel caso il nome dell'album venga cambiato aggiorno il nome esposto della sua label
	 */
	public void RefreshName()
	{
		this.name_field.setText(album.GetAlbName());
	}
	

}
