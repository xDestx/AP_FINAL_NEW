package xdest.game.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import xdest.game.ui.Screen;

public class MouseWatcher extends MouseAdapter {

	private Screen s;
	
	//Used to check for mouse click
	public MouseWatcher(Screen s) {
		this.s = s;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		s.mouseClicked(e);
	}
	
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		s.mouseMoved(e);
	}

}
