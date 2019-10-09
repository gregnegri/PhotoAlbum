package tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

import statics.Constants;

/**
 * JPanel gestito con GridLayout che accetta solo classi discendenti dalla classe astratta 'IconPane'(GENERICS)
 * la cui funzione e determinare il numero di colonne in base alla dimensione del monitor dello user 
 * e la dimensione delle icone generate dal programma, le righe vengono aumentate dinamicamente 
 * in base alla quantita di elementi inseriti durante l'esecuzione del programma (a run time)
 * 
 * @author grego
 *
 * @param <Icon>
 */

@SuppressWarnings("serial")
public class FlexibleGridPane <Icon extends IconPane> extends JPanel{

	public FlexibleGridPane()
	{
		 super();
		 this.setLayout(new GridLayout(0, Constants.screenWidth/Constants.icons_column_width, 0, 50));
		 this.setBackground(Color.LIGHT_GRAY);
		 this.setBackground(Color.LIGHT_GRAY);
	}
	
	/**
	 * Aggiunge al pannello solamente oggetti appartenenti alle classi discendenti dalla classe astratta IconPane
	 * 
	 * @param icon_pane
	 */
	public void add(Icon icon_pane)
	{
		super.add(icon_pane);
		this.revalidate();
	}
	
	/**
	 * Sovrascrive il metodo della classe "padre" di aggiunta di un componente qualsiasi per renderlo inutilizzabile 
	 * dato che usero il precedente metodo per gestire le aggiunte
	 * 
	 * @param comp
	 * @return
	 */
	@Override
	public Component add(Component comp)
	{
		return null;
	}
	
}
