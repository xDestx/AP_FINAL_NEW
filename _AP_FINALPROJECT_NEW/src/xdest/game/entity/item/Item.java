package xdest.game.entity.item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
		g.setColor(Color.yellow);
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
		g.drawString(getName(), (int) r.getX(), (int) r.getY() - 20);
	}
	
	public static Item create(String name, Location l)
	{
		Rectangle bound = new Rectangle((int)l.getX(), (int)l.getY(), 50, 50);
		if (name.equalsIgnoreCase("heal"))
			return new HealItem(bound);
		if (name.equalsIgnoreCase("regen"))
			return new RegenItem(bound);
		return null;
	}

}
