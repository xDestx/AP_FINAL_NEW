package xdest.game.entity.item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import xdest.game.Game;
import xdest.game.entity.Entity;
import xdest.game.entity.player.Player;
import xdest.game.location.Location;

public abstract class Item extends Entity {

	private boolean pickedUp;

	public Item(String name, Location l, BufferedImage b, Rectangle r) {
		super(name, l, b, r);
		pickedUp = false;
	}

	public void picked(Player p) {
		pickedUp = true;
		grabbed(p);
	}
	
	protected abstract void grabbed(Player p);

	@Override
	public boolean expired() {
		return pickedUp;
	}

	@Override
	public abstract void update(Game g);

	@Override
	public void render(Graphics g) {
		Rectangle r = getBounds();
		super.render(g);
		g.setFont(new Font("Arial",Font.BOLD,18));
		g.setColor(setBoxColor());
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
		g.drawString(getName(), (int) r.getX(), (int) r.getY() - 20);
	}
	
	protected abstract Color setBoxColor();
	
	public static Item create(String name, Location l)
	{
		Rectangle bound = new Rectangle((int)l.getX(), (int)l.getY(), 50, 50);
		HashMap<String,Item> toReturn = new HashMap<String,Item>();
		toReturn.put("heal", new HealItem(bound));
		toReturn.put("regen", new RegenItem(bound));
		for(String s : toReturn.keySet())
		{
			if(s.equals(name))
			{
				return toReturn.get(s);
			}
		}
		return null;
	}

}
