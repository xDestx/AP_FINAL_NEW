package xdest.game.vis;

import java.awt.Graphics;

import xdest.game.Game;

public interface Animation {
	//They do their name
	void update(Game g);
	void render(Graphics g);
	boolean expired();
}
