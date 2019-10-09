package tools;

/**
 * Trasmette il risultato della verifica della password esternamente a 'ControllerDialog', 
 * comunica il valore del boolean
 * 
 * @author grego
 */

public class Controller {

	boolean validator = false;
	
	public Controller()
	{
	}
	
	/**
	 * Restituisce il valore dell'attributo boolean ('validator')
	 * @return
	 */
	public boolean GetValue()
	{
		return validator;
	}
	
	/**
	 * Imposta il valore contenuto a false
	 */
	public void False()
	{
		validator = false;
	}
	
	/**
	 * Imposta il valore contenuto a true
	 */
	public void True()
	{
		validator = true;
	}	
	
}
