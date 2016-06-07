package xdest.game.util.key;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import xdest.game.Game;

public class CWAdapter extends WindowAdapter {

	private KeySelectFrame ksf;
	
	//on window close save keys
	public CWAdapter(KeySelectFrame ksf) {
		this.ksf = ksf;
	}
	
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		ksf.getKeyConfig().export();
		Game.log("Exported new Keys!");
	}

}
