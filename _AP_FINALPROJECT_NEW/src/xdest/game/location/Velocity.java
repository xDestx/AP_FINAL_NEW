package xdest.game.location;

public class Velocity {

	private double x,y;
	
	/**
	 * Create a default velocity 
	 */
	public Velocity() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Create a specific velocity
	 * @param x x velocity
	 * @param y y velocity
	 */
	public Velocity(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get x velocity
	 * @return x velocity
	 */
	public double getX()
	{
		return this.x;
	}
	
	
	/**
	 * Get y velocity
	 * @return y velocity
	 */
	public double getY()
	{
		return this.y;
	}
	
	/**
	 * Set x velocity
	 * @param x new x velocity
	 */
	public void setX(double x)
	{
		this.x = x;
	}

	/**
	 * Set y velocity
	 * @param y new y velocity
	 */
	public void setY(double y)
	{
		this.y = y;
	}

	/**
	 * Add to x velocity
	 * @param x added velocity (x)
	 */
	public void addX(double x)
	{
		this.x+=x;
	}
	
	/**
	 * Add to y velocity
	 * @param y added velocity (y)
	 */
	public void addY(double y)
	{
		this.y += y;
	}
	
	public String toString()
	{
		return "X: " + x + " Y: " + y;
	}
	
}
