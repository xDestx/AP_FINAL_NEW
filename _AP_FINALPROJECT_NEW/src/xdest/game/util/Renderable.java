package xdest.game.util;

import java.awt.Graphics;

public interface Renderable {

	//Can something be rendered?
	public void render(Graphics g);
	public boolean expired();
	
}
