/**
 * 
 */
package mainfolder;

import dialogui.NewGalleryDialog;
import statics.StaticMethods;


/**
 * @author grego
 *
 */
public class Main {

	public static void main(String[] args) {
		
		if(!StaticMethods.Load())
			new NewGalleryDialog();
		
	}

}
