package xdest.game.vis;

import java.awt.Color;
import java.awt.Graphics;

import xdest.game.Game;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public class HitAnimation implements Renderable, Animation {

	private Location origin;
	private int ticker;
	private final int maxT = 20;
	
	public HitAnimation(Location l)
	{
		this.origin = l;
		ticker = 0;
	}
	
	@Override
	public void update(Game g) {
		ticker++;
	}

	@Override
	public void render(Graphics g) {
		Color x = g.getColor();
		g.setColor(Color.red);
		for(int i = 0; i < 4; i++)
		{
			//Horiz
			g.drawLine((int)(origin.getX()) + 25 - ((int)((50) * ((double)ticker/(double)maxT))), 
					(int)origin.getY()+22 + i, 
					(int)(origin.getX()) + 25 + (int)(((double)ticker/(double)maxT) * (50)),
					(int)origin.getY() + 22 + i);
			//Diagonal L>R
			g.drawLine((int)(origin.getX()) + 25 - ((int)((50) * ((double)ticker/(double)maxT))), 
					(int)origin.getY()+22 + i - (int)(35 * ((double)ticker/(double)maxT)), 
					(int)(origin.getX()) + 25 + (int)(((double)ticker/(double)maxT) * (50)),
					(int)origin.getY() + 27 + i + (int)(35 * ((double)ticker/(double)maxT)));
			//Diagonal R>L
			g.drawLine((int)(origin.getX()) + 25 - ((int)((50) * ((double)ticker/(double)maxT))), 
					(int)origin.getY()+27 + i + (int)(35 * ((double)ticker/(double)maxT)), 
					(int)(origin.getX()) + 25 + (int)(((double)ticker/(double)maxT) * (50)),
					(int)origin.getY() + 22 + i - (int)(35 * ((double)ticker/(double)maxT)));
			//Vert
			g.drawLine((int)(origin.getX()) + 25 + i, 
					(int)origin.getY()+22 + ((int)((50) * ((double)ticker/(double)maxT))), 
					(int)(origin.getX()) + 25 + i,
					(int)origin.getY() + 22 - ((int)((50) * ((double)ticker/(double)maxT))));
		}
		
		g.setColor(x);
	}

	@Override
	public boolean expired() {
		return ticker > maxT;
	}

}
