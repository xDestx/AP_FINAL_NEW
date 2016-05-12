package xdest.game.location;

import java.awt.Point;
import java.io.Serializable;

public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327001493795116942L;
	private double x,y;
	
	/**
	 * Create a default location
	 */
	public Location() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Create a specific location
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get x position
	 * @return x position
	 */
	public double getX()
	{
		return this.x;
	}

	/**
	 * Get y position
	 * @return y position
	 */
	public double getY()
	{
		return this.y;
	}
	
	/**
	 * Set x position
	 * @param x new x position
	 */
	public void setX(double x)
	{
		this.x=x;
	}
	
	/**
	 * Set y position
	 * @param y new y position
	 */
	public void setY(double y)
	{
		this.y=y;
	}
	
	/**
	 * Add to x position
	 * @param x added x value;
	 */
	public void addX(double x)
	{
		this.x+=x;
	}
	
	/**
	 * Add to y position
	 * @param Y added y value;
	 */
	public void addY(double y)
	{
		this.y+=y;
	}
	
	public Location copy()
	{
		return new Location(x,y);
	}
	
	public Point toPoint()
	{
		return new Point((int)x,(int)y);
	}
	
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}

}
