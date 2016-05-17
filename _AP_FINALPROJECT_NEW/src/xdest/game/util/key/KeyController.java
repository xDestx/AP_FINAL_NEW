package xdest.game.util.key;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import xdest.game.Game;

public class KeyController extends KeyAdapter {

	private Game g;
	
	public KeyController(Game g)
	{
		this.g = g;
	}
	
	public void keyPressed(KeyEvent e)
	{
		g.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e)
	{
		g.keyReleased(e);
	}

}
