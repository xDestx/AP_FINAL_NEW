package xdest.game.vis;

import java.awt.Color;
import java.awt.Graphics;

import xdest.game.Game;
import xdest.game.util.Renderable;

public class BlackToWhite implements Renderable, Animation {

	private int ticker;
	private int maxT;
	
	/**
	 * 
	 * @param dur duration in SECONDS
	 */
	public BlackToWhite(int dur) {
		maxT = dur*100;
	}

	@Override
	public void update(Game g) {
		ticker++;
	}

	@Override
	public void render(Graphics g) {
		Color last = g.getColor();
		int v = (int)(((double)ticker/(double)maxT) * 255);
		g.setColor(new Color(v,v,v));
		g.fillRect(0, 0, 600, 600);
		g.setColor(last);
	}

	@Override
	public boolean expired() {
		return ticker>=maxT;
	}

}
