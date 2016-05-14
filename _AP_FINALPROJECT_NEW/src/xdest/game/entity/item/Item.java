package xdest.game.entity.item;

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
	public void update(Game g) {
		// Do nothing! Sike!
	}

	@Override
	public void render(Graphics g) {
		Rectangle r = getBounds();
		super.render(g);
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
		g.drawString(getName(), (int) r.getX(), (int) r.getY() - 20);
	}

}
