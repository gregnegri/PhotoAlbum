package dialogui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import mainfolder.MainMenuFrame;

@SuppressWarnings("serial")
public class EditGalleryNameDialog extends JDialog implements ActionListener,KeyListener {

	private JPanel content_pane;
	private JLabel name_label = new JLabel("Name: ");
	private JTextField name_field;
	private JButton cancel_butt = new JButton("Cancel");
	private JButton edit_butt = new JButton("Edit");
	
	private MainMenuFrame main_frame;
	
	
	public EditGalleryNameDialog(MainMenuFrame main_frame)
	{
		this.setTitle("Edit gallery's name");
		this.setModal(true);
		
		this.main_frame = main_frame;
		
		this.name_field = new JTextField(15);
		this.name_field.setText(main_frame.GetName());
		this.name_field.addKeyListener(this);
		
		this.cancel_butt.addActionListener(this);
		this.edit_butt.addActionListener(this);
		
		GridBagLayout gridBag_l = new GridBagLayout();
		this.content_pane = new JPanel(gridBag_l);
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		csr.gridx = 0;
		csr.gridy = 0;
		csr.anchor = GridBagConstraints.LINE_START;
		gridBag_l.setConstraints(name_label, csr);
		this.content_pane.add(name_label);
		
		csr.gridx = 1;
		csr.weightx = 2;
		gridBag_l.setConstraints(name_field, csr);
		this.content_pane.add(name_field);
		
		csr.weightx = 1;
		csr.insets = new Insets(10,0,0,0);
		csr.gridx = 0;
		csr.gridy = 1;
		gridBag_l.setConstraints(cancel_butt, csr);
		this.content_pane.add(cancel_butt);
		
		csr.gridx = 1;
		csr.insets = new Insets(10,100,0,0);
		csr.anchor = GridBagConstraints.LINE_END;
		gridBag_l.setConstraints(edit_butt, csr);
		this.content_pane.add(edit_butt);
		
		this.content_pane.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));
		this.add(content_pane);
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == cancel_butt)
		{
			this.dispose();
		}
		else if(e.getSource() == edit_butt)
		{
			/**
			 * Riceve in ingresso un eventuale modifica dell'attuale nome della galleria
			 * e ne richiede il cambiamento al main frame
			 */
			main_frame.ChangeTitle(name_field.getText());
			this.dispose();
		}
	}


	public void keyPressed(KeyEvent k) {
		
		int key;
		key = k.getKeyCode();
		if(key == KeyEvent.VK_ENTER)
		{
			if(k.getSource()==name_field)
			{
				main_frame.ChangeTitle(name_field.getText());
				this.dispose();
			}
		}
		
	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
