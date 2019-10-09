package dialogui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import java.util.Vector;

import photoui.PrevShelfFrame;
import statics.StaticMethods;

@SuppressWarnings ("serial")
public class AddImgUrlDialog extends JDialog implements ActionListener {

	private JLabel url_label;
	private JTextField url_field;
	private JButton cancel_butt;
	private JButton add_butt;
	private JButton apply_butt;
	
	private JPanel content_pane;
	
	private Vector<URL> img_urls = new Vector<URL>();
	/**
	 * 'caller_Shelf' e il frame delle anteprime a cui verranno aggiunte le immagini
	 */
	private PrevShelfFrame caller_Shelf;
	
	/**
	 * Il Costruttore si occupa della ui
	 */
	
	public AddImgUrlDialog(PrevShelfFrame caller)
	{
		super();
		
		this.setTitle("Add from URL");
		this.caller_Shelf = caller;
		this.setModal(true);
		
		this.content_pane = new JPanel(new GridBagLayout());
		GridBagConstraints csr = new GridBagConstraints();
		csr.fill = GridBagConstraints.HORIZONTAL;
		
		this.url_label = new JLabel("URL:");
		this.url_field = new JTextField();
		this.cancel_butt = new JButton("Cancel");
		this.add_butt = new JButton("Add++");
		this.apply_butt = new JButton("Apply");
		
		this.cancel_butt.addActionListener(this);
		this.add_butt.addActionListener(this);
		this.apply_butt.addActionListener(this);
		
		csr.weighty = 1;
		csr.gridx = 0;
		csr.gridy = 0;
		csr.insets = new Insets(20,20,0,0);
		csr.anchor = GridBagConstraints.LINE_END;
		this.content_pane.add(url_label, csr);
		
		csr.anchor = GridBagConstraints.LINE_START;
		csr.gridx = 1;
		csr.gridy = 0;
		csr.gridwidth = 8;
		csr.insets = new Insets(20,0,0,20);
		this.content_pane.add(url_field, csr);
		this.url_field.setText("");
		
		csr.gridwidth = 1;
		csr.anchor = GridBagConstraints.LINE_END;
		csr.insets = new Insets(5,20,20,30);
		csr.gridx = 0;
		csr.gridy = 1;
		csr.weighty = 1;
		this.content_pane.add(cancel_butt, csr);
		
		csr.gridx = 4;
		csr.gridy = 1;
		csr.anchor = GridBagConstraints.LINE_START;
		new Insets(5,30,20,0);
		this.content_pane.add(add_butt, csr);
		
		csr.gridx = 5;
		csr.gridy = 1;
		csr.insets = new Insets(5,0,20,20);
		this.content_pane.add(apply_butt, csr);
		
		this.add(content_pane);
		this.pack();
		
		this.url_field.requestFocus();
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == cancel_butt)
		{
			this.dispose();
		}
		
		/**
		 * Quando viene premuto "Add" l'url viene aggiunto ad un vettore,
		 * cosi e possibile aggiungere di piu urls contemporaneamente
		 */
		else if(e.getSource() == add_butt)
		{
			this.AddToVector();
			this.url_field.requestFocus();
		}
		
		/**
		 * Quando viene premuto "Apply" l'url viene aggiunto al vettore contenente 
		 * gli urls inseriti precedentemente (attraverso "Add") e va ad
		 * eliminare possibili copie di questi ultimi dagli album (tranne per
		 * gli album protetti), poi aggiunge le immagini all'album ed al 
		 * pannello(frame) espositivo chiamante (PreviewShelfFrame)
		 */
		else if(e.getSource() == apply_butt)
		{
			this.AddToVector();
			
			if(!img_urls.isEmpty())
			{
				StaticMethods.RemoveClonesUrl(img_urls);
				caller_Shelf.AddFromUrl(img_urls);
			}
			this.dispose();
		}
	}
	
	/**
	 * Inserisce l'URL all'interno del vettore
	 * (dopo aver controllato che non ci sia gia)
	 */
	private void AddToVector()
	{
		String text = null;
		text = url_field.getText();
		url_field.setText("");
		URL new_url = null;
		boolean clone = false;
		
		
		if(text != null && text != "")
		{	
			try {
				new_url = new URL(text);
			} catch (MalformedURLException e1){
				new_url = null;
				e1.printStackTrace();
			}
		}
		if(new_url != null)
		{
			for(URL url: img_urls)
			{
				if(url.getPath().equals(new_url.getPath()))
					clone = true;
			}
			if(clone != true)
			{
				for(URL url: caller_Shelf.GetAlbumUrls())
				{
					if(url.getPath().equals(new_url.getPath()))
						clone = true;
				}
			}
			if(clone != true)
				img_urls.add(new_url);
		}
	}
	
}
