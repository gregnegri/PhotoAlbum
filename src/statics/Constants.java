package statics;

import java.awt.Dimension;

import java.awt.Toolkit;
import java.io.File;

/**
 * Classe che contiene tutte le costanti globali del programma
 * 
 * @author grego
 * 
 */

public class Constants {
	
	/**
	 * Con 'screenSize' prendo l'altezza e la larghezza dello schermo in pixel e 
	 * da esse genero due dimensioni aggiustate con un offset arbitrario
	 */
	private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public final static int screenHeight = screenSize.height;
	public final static int screenWidth = screenSize.width;
	
	public final static int icons_dim = 200;
	public final static int icons_column_width = 250;
	
	public final static String abspath = new File(".").getAbsolutePath();
	public final static File emptyImgFile = new File(abspath+"empty.png");
	public final static File lockedImgFile = new File(abspath+"locked.jpg");
	public final static File saveFile = new File(abspath+"save.dat");

}
