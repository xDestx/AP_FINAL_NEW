package xdest.game.world;

import java.awt.Graphics;
import java.io.Serializable;

import xdest.game.Game;
import xdest.game.GameObject;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public abstract class WorldObject implements GameObject, Serializable, Renderable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2015740315327157176L;
	private final Location l;
	
	public WorldObject(Location l) {
		this.l = l;
	}
	
	public Location getLocation()
	{
		return this.l;
	}

	@Override
	public void update(Game g) {
	}
	
	public abstract void render(Graphics g);

}
