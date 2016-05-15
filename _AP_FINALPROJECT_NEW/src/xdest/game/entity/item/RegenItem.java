package xdest.game.entity.item;

import java.awt.Color;
import java.awt.Rectangle;

import xdest.game.Game;
import xdest.game.effect.Regeneration;
import xdest.game.entity.player.Player;
import xdest.game.location.Location;
import xdest.game.util.ImageLoader;

public class RegenItem extends Item {

	private int totalHealed;
	
	public RegenItem(Rectangle r) {
		super("Regen", new Location(r.getX(), r.getY()), ImageLoader.loadImage("/images/items/heal.png"), r);
		totalHealed = (int)(Math.random() * 200) + 1;
		setName(getName() + " - " + totalHealed);
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


	protected Color setBoxColor()
	{
		return Color.cyan;
	}
	
	@Override
	protected void grabbed(Player p) {
		Game.log("Regen grabbed! (" + totalHealed + ")");
		p.addEffect(new Regeneration(p, 25 * 100, totalHealed, 25));
	}

}
