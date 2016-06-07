package xdest.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import xdest.game.Game;
import xdest.game.entity.Entity;
import xdest.game.location.Location;

public class DamageNumber extends Entity {

	private final double damage;
	private final Color c;
	private final int fSize;
	private int toRemove;
	
	//Honestly I should have made this an animation
	public DamageNumber(double d, int x, int y) {
		super("Damage Numer: " + d, new Location(x,y),null, null);
		toRemove = 300;
		this.damage = d;
		if (d < 10)
		{
			c = new Color(215,88,88);
			fSize = 12;
		} else if (d < 50)
		{
			c = new Color(222,46,46);
			fSize = 18;
		} else if (d < 75)
		{
			c = new Color(255,0,0);
			fSize = 24;
		} else
		{
			c = new Color(213,177,33);
			fSize = 36;
		}
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setColor(c);
		g.setFont(new Font("Impact",0,fSize));
		double x = damage * 100;
		g.drawString((double) Math.round(x) / 100+"", (int)getLocation().getX(), (int)getLocation().getY());
		getLocation().addX(Math.random() * .1);
		getLocation().addY(Math.random() * -.04);
	}
	
	public boolean expired()
	{
		return toRemove <= 0;
	}

	public double getDamage()
	{
		return damage;
	}
	
	@Override
	public void update(Game g) {
		toRemove--;
	}

}
