package photoui;

import java.awt.event.ActionEvent;

import java.util.TimerTask;
import java.util.Vector;
import java.util.Timer;

@SuppressWarnings("serial")
public class SlideShowFrame extends PhotoViewerFrame {

	private int delay_time = 1;	//delay temporale tra la visualizzazione di un immagine e un'altra
	private Timer timer = new Timer();
	
	public SlideShowFrame(Vector<IconPrevPane> icopane_list, int index, int delay) {
		super(icopane_list, index);
		if(delay>1 && delay<600)
		{
			this.delay_time = delay;
		}
		this.setVisible(true);
		this.Start();
	}
	
	/**
	 * Inizia lo slideshow scorrendo il vettore di foto, passando da una all'altra con il delay temporale indicato dall'utente
	 */
	private void Start()
	{
		final int size = this.icopane_list.size();
		
		timer.schedule(new TimerTask(){
		@Override
		public void run(){
			int index = icopane_list.indexOf(prev_pane);
			if(index < (size-1))
			{
				index++;
				prev_pane = icopane_list.elementAt(index);
			}
			else if(index == (size-1))
			{
				index = 0;
				prev_pane = icopane_list.elementAt(index);
			}
			ChangeShownPic();
		}}, 0,(delay_time*1000));
	}
	
	/**
	 * Se viene premuto il pulsante 'Exit' interrompe lo slide show e torna inietro
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.exit_butt)
		{
			timer.cancel();
			timer.purge();
			this.dispose();
		}
	}
	
}
