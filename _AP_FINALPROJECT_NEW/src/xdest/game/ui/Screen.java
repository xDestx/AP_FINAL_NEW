package xdest.game.ui;


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Collection;

import xdest.game.Game;
import xdest.game.util.Renderable;


public class Screen extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192905738915824072L;
	private BufferedImage im;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private int m;
	private Game g;
	
	//Game screen (everything drawn here)
	public Screen(Game g) {
		this.setSize(Screen.WIDTH,Screen.HEIGHT);
		this.setMinimumSize(this.getSize());
		this.setMaximumSize(this.getSize());
		im = new BufferedImage(Screen.WIDTH,Screen.HEIGHT,BufferedImage.TYPE_INT_RGB);
		m = 0;
		this.g = g;
	}

	public void render(Collection<Renderable> e)
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(im, 0, 0, null);
		
		for (Renderable en : e)
		{
			en.render(g);
		}
		
		g.dispose();
		bs.show();
		m = 0;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		if (m == 1)
		{
			g.getMenu().mouseMoved(e);
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		if (m == 1)
		{
			g.getMenu().mouseClicked(e);
		}
	}
	
	public void render(Menu m)
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(im, 0, 0, null);
		
		m.render(g);
	//	System.out.println("AHAA");
		g.dispose();
		bs.show();
		this.m = 1;
	}
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paint(g);
	}
}
