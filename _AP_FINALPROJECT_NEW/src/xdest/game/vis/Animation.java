package xdest.game.vis;

import java.awt.Graphics;

import xdest.game.Game;

public interface Animation {

	void update(Game g);
	void render(Graphics g);
	boolean expired();
}
