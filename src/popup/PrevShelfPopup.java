package popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import dialogui.AddImgUrlDialog;
import photoui.PrevShelfFrame;

@SuppressWarnings("serial")
public class PrevShelfPopup extends JPopupMenu implements ActionListener {

	private JMenu add_menu = new JMenu("Add");
	private JMenuItem add_file_it = new JMenuItem("Add from File");
	private JMenuItem add_url_it = new JMenuItem("Add from URL");
	private PrevShelfFrame prev_shelf;
	
	public PrevShelfPopup(PrevShelfFrame prev_shelf )
	{
		this.prev_shelf = prev_shelf;
		
		this.add_file_it.addActionListener(this);
		this.add_url_it.addActionListener(this);
		
		this.add_menu.add(add_file_it);
		this.add_menu.add(add_url_it);
		this.add(add_menu);
	}

	/**
	 * Shortcut delle operazioni di aggiunta di nuove immagini a file o URL 
	 * con un click del tasto destro sullo sfondo dell 'PrevShelfFrame'
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == add_file_it)
		{
			prev_shelf.GetNewFiles();
		}
		else if(e.getSource() == add_url_it)
		{
			new AddImgUrlDialog(prev_shelf);
		}
		
	}
	
}
