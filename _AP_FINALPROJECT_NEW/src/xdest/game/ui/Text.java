package xdest.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import xdest.game.Game;
import xdest.game.GameObject;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public class Text implements GameObject, Renderable {

	private String text;
	private Location l;
	private Font f;
	private Color c;
	private BufferedImage bg;
	private int ticks;
	
	public Text(String text, Location l, Color c) {
		this.text = text;
		this.l = l;
		bg = null;
		this.c = c;
		f = new Font("Arial", 0, 32);
		ticks = -1;
	}
	

	public Text(String text, Location l, Color c, int t) {
		this.text = text;
		this.l = l;
		bg = null;
		this.c = c;
		f = new Font("Arial", 0, 32);
		ticks = t;
	}
	
	public Text(String t, Location l, Font f, Color c, int ti, BufferedImage bg)
	{
		this.text = t;
		this.l = l;
		this.f = f;
		this.c = c;
		this.bg = bg;
		this.ticks = ti;
	}

	@Override
	public void render(Graphics g) {
		Color x = g.getColor();
		if (!(bg == null))
		{
			g.drawImage(bg,(int)l.getX(), (int)l.getY(), null);
		} 
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(f);
		g2.setColor(c);
		g2.drawString(text, (int)l.getX(), (int)l.getY());
		g.setColor(x);
	}

	@Override
	public void update(Game g) {
		if (ticks == 0)
			return;
		if (ticks <= -1)
			return;
		ticks--;
	}
	
	@Override
	public boolean expired()
	{
		if (ticks <= -1)
			return false;
		if (ticks == 0)
		{
			return true;
		}
		return false;
	}

}
