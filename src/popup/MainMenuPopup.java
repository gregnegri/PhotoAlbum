package popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import dialogui.NewAlbumDialog;

@SuppressWarnings("serial")
public class MainMenuPopup extends JPopupMenu implements ActionListener {

private JMenuItem newAlbum_it = new JMenuItem("New Album");
	
	public MainMenuPopup()
	{
		this.newAlbum_it.addActionListener(this);
		
		this.add(newAlbum_it);
	}
	
	/**
	 * Shortcut per la creazione di un nuovo album direttamente premendo il tasto destro del mouse sulla galleria
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == newAlbum_it)
		{
			new NewAlbumDialog();
		}
		
	}
	
}
