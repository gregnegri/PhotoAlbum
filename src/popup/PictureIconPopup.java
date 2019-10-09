package popup;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dialogui.MoveToDialog;
import photoui.IconPrevPane;
import photoui.PrevShelfFrame;

@SuppressWarnings("serial")
public class PictureIconPopup extends JPopupMenu implements ActionListener {
	
	private JMenuItem delete_item = new JMenuItem("Delete");
	private JMenuItem move_item = new JMenuItem("Move");
	
	private IconPrevPane ico_pane;
	private PrevShelfFrame shelf_frame;
	
	public PictureIconPopup(IconPrevPane ico_pane)
	{
		super();
		this.ico_pane = ico_pane;
		
		delete_item.addActionListener(this);
		move_item.addActionListener(this);
	
		this.add(delete_item);
		this.add(move_item);
	}
	
	/**
	 * Setta il frame nel quale sono rappresentate le anteprime delle immagini 
	 * con cui successivamente interagire
	 * 
	 * @param shelf_frame
	 */
	public void SetShelf(PrevShelfFrame shelf_frame)
	{
		this.shelf_frame = shelf_frame;
	}

	/**
	 * Gli items del popup permettono di eliminare e spostare tra album la foto selezionata
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == delete_item)
		{
			shelf_frame.RemoveImageFromAlbum(ico_pane);
		}
		else if(e.getSource() == move_item)
		{
			new MoveToDialog (shelf_frame, ico_pane);
		}
	}

}
