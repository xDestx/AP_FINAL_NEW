package xdest.game.vis;

import java.awt.Color;
import java.awt.Graphics;

import xdest.game.Game;
import xdest.game.util.Renderable;

public class EndAnimation implements Animation, Renderable {

	private int ticker;
	private final int maxT = 20;
	private boolean inc;
	private int trueTick;
	
	public EndAnimation() {
		ticker=0;
		trueTick = 0;
		inc = true;
	}

	@Override
	public void update(Game g) {
		if (inc)
		{
			ticker++;
			inc = !(ticker >= maxT/2);
		} else
		{
			ticker--;
		}
		trueTick++;
	}

	@Override
	public void render(Graphics g) {
		Color lastColor = g.getColor();
		if(ticker < 0)
			ticker=0;
		g.setColor(new Color(255,255,255, (int)(((double)ticker / ((double)maxT / 2.0) * 255))));
		g.fillRect(0, 0, 600, 600);
		g.setColor(lastColor);
	}

	@Override
	public boolean expired() {
		return trueTick >= maxT;
	}

}
