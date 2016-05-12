package xdest.game.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

import xdest.game.location.Location;
import xdest.game.util.ImageX;

public class World extends WorldObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005305835428372857L;
	private LinkedList<WorldObject> objs;
	//Need to add bounds for
	//0,0 ---> 0,600
	//0,600 ---> 600,600
	
	private transient BufferedImage bgx;
	private boolean completed;
	private String name;
	private ImageX bg;
	private Color bgc;
	
	public World(String name, ImageX bg, Color bgc) {
		super(new Location());
		this.name = name;
		objs = new LinkedList<WorldObject>();
		completed = false;
		this.bg = bg;
		this.bgc = bgc;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setComplete(boolean b)
	{
		this.completed = b;
	}
	
	public void add(WorldObject o)
	{
		if(completed)
			return;
		objs.add(o);
	}
	
	public Collection<WorldObject> getAll()
	{
		return this.objs;
	}
	

	@Override
	public void render(Graphics g) {
		if (!completed)
			return;
		if (bg == null)
		{
			Color x = g.getColor();
			g.setColor(bgc);
			g.fillRect(0, 0, 600, 600);
			g.setColor(x);
		} else
		{
			if (bgx == null)
				bgx = bg.getImage();
			g.drawImage(bgx, 0, 0, null);
		}
		for (WorldObject o : objs)
		{
			o.render(g);
		}
	}
	
	@Override
	public boolean expired()
	{
		return false;
	}

}
