package xdest.game.entity.item;

import java.awt.Rectangle;

import xdest.game.Game;
import xdest.game.entity.player.Player;
import xdest.game.location.Location;
import xdest.game.util.ImageLoader;

public class HealItem extends Item {

	private int heal;
	
	public HealItem(Rectangle r) {
		super("Heal", new Location(r.getX(), r.getY()), ImageLoader.loadImage("/images/items/heal.png"), r);
		heal = (int)(Math.random() * 100) + 1;
		setName(getName() + " - " + heal);
	}
	
	@Override
	public void update(Game g)
	{
		if (expired())
			g.removeObject(this);
		if (g.getPlayer1().colliding(this.getBounds()) && !expired())
		{
			this.picked(g.getPlayer1());
		} else if (g.getPlayer2().colliding(this.getBounds()) && !expired())
		{
			this.picked(g.getPlayer2());
		}
	}

	@Override
	protected void grabbed(Player p) {
		Game.log("Heal grabbed! (" + heal + ")");
		p.effectHeal(heal);
	}

}
