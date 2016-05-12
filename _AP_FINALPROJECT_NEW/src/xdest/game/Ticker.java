package xdest.game;

import java.awt.Graphics;
import java.util.Collection;
import java.util.LinkedList;

import xdest.game.GameObject;
import xdest.game.entity.Entity;
import xdest.game.util.Renderable;

public class Ticker {

	private LinkedList<GameObject> o;
	
	/**
	 * Handles updating all game objects
	 */
	public Ticker() {
		o = new LinkedList<GameObject>();
	}
	
	/**
	 * Execute 1 tick
	 */
	public void tick(Game g)
	{
		for (int i = 0; i < o.size(); i++)
		{
			o.get(i).update(g);
		}
	}
	
	/**
	 * Render all entities
	 * @param g Graphics
	 */
	public void render(Graphics g)
	{
		for (int i = 0; i < o.size(); i++)
		{
			if (o.get(i) instanceof Renderable)
			{
				((Renderable) o.get(i)).render(g);
				if (((Renderable)o.get(i)).expired())
				{
					o.remove(i);
				}
			}
		}
	}
	
	public Collection<Renderable> getRenderables()
	{
		Collection<Renderable> x = new LinkedList<Renderable>();
		for (int i = 0; i < o.size(); i++)
		{
			if (o.get(i) instanceof Renderable)
			{
				x.add((Renderable) o.get(i));
			}
		}
		return x;
	}
	
	public Collection<Entity> getEntities()
	{
		Collection<Entity> x = new LinkedList<Entity>();
		for (int i = 0; i < o.size(); i++)
		{
			if (o.get(i) instanceof Entity)
			{
				x.add((Entity) o.get(i));
			}
		}
		return x;
	}
	
	/**
	 * Add ticked game object
	 * @param g - Game object
	 */
	public void addObject(GameObject g)
	{
		o.add(g);
	}

}
