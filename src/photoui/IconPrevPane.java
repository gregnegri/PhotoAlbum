package photoui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import java.io.File;

import java.net.URL;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.Vector;

import popup.PictureIconPopup;
import tools.*;

@SuppressWarnings("serial")
public class IconPrevPane extends IconPane {

	/**
	 * Contenuto del pannello
	 */
	private ImageIcon prev;
	private BufferedImage image;
	private String name;
	private File image_file;
	private URL image_url;
	
	/**
	 * Lista delle anteprime delle foto dell'album
	 */
	private Vector<IconPrevPane> icopane_list;
	
	/**
	 * Componenti della ui
	 */
	private JLabel prev_label;
	private JTextField name_field;
	private PictureIconPopup popup_menu = new PictureIconPopup(this);
	
	
	public IconPrevPane(ImageIcon icon, BufferedImage image, String name, Vector<IconPrevPane> icopane_list, URL image_url, File image_file)
	{
		super();
		this.icopane_list = icopane_list;
		this.prev = icon;
		this.image = image;
		this.name = name;
		this.image_file = image_file;
		this.image_url = image_url;
		
		/**
		 * per gestire le posizioni del field contenente il nome e la label con l'icona
		 * ho sfruttato il layout manager GridBagLayout
		 */
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;

		csr.gridx = 0;
		csr.gridy = 3;
		csr.gridwidth = 1;

		prev_label = new JLabel(prev);
		
		/**
		 * Aggiunge alla label dell'icona dell'anteprima un menu popup che mostra le azioni eseguibili sull'immagine
		 * (sposta ed elimina)
		 */
		prev_label.setComponentPopupMenu(popup_menu);
		AddClickListen(prev_label);
		
		/**
		 * Cambia il bordo quando il cursore si trova sull'anteprima della foto
		 */
		prev_label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		prev_label.setOpaque(true);
		prev_label.setBackground(Color.WHITE);
		add(prev_label, csr);
		

		csr.gridy = 1;
		name_field = new JTextField(name, 5);
		name_field.setCaretPosition(0);
		name_field.setEditable(false);
		name_field.setBackground(Color.DARK_GRAY);
		name_field.setForeground(Color.WHITE);
		name_field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		add(name_field, csr);
	}
	
	/**
	 * ClickListener della label dell'anteprima della foto
	 */
	private void AddClickListen(JLabel lab)
	{
		
		lab.addMouseListener(new MouseAdapter(){
			@Override
			/**
			 * azioni conseguenti agli eventi individuati del listener
			 * 1.click sinistro; 2.puntatore che si sovrappone al componente;
			 * 3.puntatore che, al contrario del precedente, esce dall'area relativa dal componente (opposto di 2.)
			 */
			public void mouseClicked(MouseEvent m)
			{
				if(m.getButton() == MouseEvent.BUTTON1)
					MakePicFrame();
			}
			public void mouseEntered(MouseEvent m)
			{
					prev_label.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
					name_field.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
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
		 * Genera un nuovo frame per visualizzare la foto a schermo intero
		 */
		private void MakePicFrame()
		{
			new PhotoViewerFrame(name, icopane_list, this);
		}
		
		/**
		 * Metodo (permette di) imposta il ShelfFrame di riferimento per il popupmenu
		 * 
		 * @param shelf_frame
		 */
		public void SetShelfToPopupMenu(PrevShelfFrame shelf_frame)
		{
			popup_menu.SetShelf(shelf_frame);
		}
		
		/**
		 * Ritorna la foto dell'anteprima a fullsize
		 * 
		 * @return
		 */
		public BufferedImage GetPic()
		{
			return image;
		}
		
		/**
		 * Ritorna il nome della foto dell'anteprima
		 * 
		 * @return
		 */
		public String GetName()
		{
			return name;
		}
		
		/**
		 * Getter della foto rappresentata (FILE)
		 * 
		 * @return
		 */
		public File GetImgFile()
		{
			return image_file;
		}
		
		/**
		 * Getter della foto rappresentata (URL)
		 * 
		 * @return
		 */
		public URL GetImgUrl()
		{
			return image_url;
		}
	
}
