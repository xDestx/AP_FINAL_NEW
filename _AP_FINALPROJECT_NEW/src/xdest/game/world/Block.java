package xdest.game.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import xdest.game.location.Location;

public class Block extends WorldObject implements Collidable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9018214900662471479L;
	private Rectangle bound;
	
	public Block() {
		super(new Location(0,0));
		bound = new Rectangle(0,0,0,0);
	}
	
	public Block(int x, int y, int w, int h)
	{
		super(new Location(x,y));
		bound = new Rectangle(x,y,w,h);
	}
	
	
	public void render(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(bound);
	}
	
	public Rectangle getBounds()
	{
		return this.bound;
	}

	@Override
	public boolean colliding(Rectangle r) {
		if (bound.intersects(r))
			return true;
		return false;
	}
	
	@Override
	public boolean expired()
	{
		return false;
	}

}
