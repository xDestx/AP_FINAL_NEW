package xdest.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import xdest.game.Game;
import xdest.game.GameObject;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public abstract class Entity implements GameObject, Renderable {

	
	private Location l;
	private String name;
	private BufferedImage b;
	private Rectangle bounds;
	
	public Entity(String name, Location l, BufferedImage b, Rectangle r)
	{
		this.l = l;
		this.name = name;
		this.b = b;
		this.bounds = r;
	}
	
	public Rectangle getBounds()
	{
		return this.bounds;
	}
	
	/**
	 * Get image for entity
	 * @return the image
	 */
	public BufferedImage getImage()
	{
		return b;
	}
	
	/**
	 * Get the name of this entity
	 * @return the entity name
	 */
	public final String getName()
	{
		return this.name;
	}
	
	/**
	 * Set the name of this entity
	 * @param name the name of the entity
	 */
	public final void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Get the location of this entity
	 * @return the entity location
	 */
	public final Location getLocation()
	{
		return this.l;
	}
	
	/**
	 * Set the location of this entity
	 * @param l location
	 */
	public final void setLocation(Location l)
	{
		this.l = l;
	}
	
	/**
	 * Set the location of this entity
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public final void setLocation(int x, int y)
	{
		l.setX(x);
		l.setY(y);
	}
	
	/**
	 * Update entity
	 */
	public abstract void update(Game g);
	/**
	 * Render entity
	 */
	public void render(Graphics g)
	{
		g.drawImage(b, (int)this.getLocation().getX(), (int)this.getLocation().getY(), null);
	}

}
