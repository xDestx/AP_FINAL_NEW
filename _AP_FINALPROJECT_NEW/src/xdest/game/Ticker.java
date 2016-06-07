package xdest.game;

import java.awt.Graphics;

import java.util.Collection;
import java.util.LinkedList;

import xdest.game.GameObject;
import xdest.game.entity.Entity;
import xdest.game.util.Renderable;
import xdest.game.vis.Animation;

public class Ticker {

	private LinkedList<GameObject> o;
	private LinkedList<Animation> a;
	
	/**
	 * Handles updating all game objects
	 */
	public Ticker() {
		o = new LinkedList<GameObject>();
		a = new LinkedList<Animation>();
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
		for (int i = 0; i < a.size(); i++)
		{
			a.get(i).update(g);
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
		for (int i = 0; i < a.size(); i++)
		{
			a.get(i).render(g);
			if (a.get(i).expired())
			{
				a.remove(i);
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
		
		for (int i = 0; i < a.size(); i++)
		{
			if (a.get(i) instanceof Renderable)
			{
				x.add((Renderable) a.get(i));
			}
		}
		return x;
	}
	
	public Collection<Animation> getAnimations()
	{
		return a;
	}
	
	public void tickAnimations(Game g)
	{
		for (int i = 0; i < a.size(); i++)
		{
			a.get(i).update(g);
			if(a.get(i).expired())
			{
				a.remove(i);
			}
		}
	}
	
	public Collection<Renderable> getAnimationsAsRenderable()
	{
		LinkedList<Renderable> x = new LinkedList<Renderable>();
		for (Animation an : a)
		{
			x.add((Renderable)an);
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
	
	public void log()
	{
		String s = "Object count: " + o.size() + "\nObjects:\n";
		for (GameObject ob : o)
		{
			s+= ob.toString() + "\n";
		}
		Game.log(s);
	}
	
	/**
	 * Add ticked game object
	 * @param g - Game object
	 */
	public void addObject(GameObject g)
	{
		o.add(g);
	}
	
	//Add an overlaying animation
	public void addOverAnimation(Animation an)
	{
		this.a.add(an);
	}
	
	public void removeObject(GameObject g)
	{
		if(o.contains(g))
			o.remove(g);
	}

}
