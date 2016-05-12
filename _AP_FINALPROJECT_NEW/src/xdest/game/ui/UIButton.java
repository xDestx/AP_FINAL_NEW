package xdest.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import xdest.game.Game;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public class UIButton implements Renderable{

	private String text;
	private Color c;
	private BufferedImage bg;
	private Color bgc;
	private GameAction i;
	private Location l;
	private int w,h;
	private boolean hover;
	
	
	public UIButton(String text, Rectangle r, Color c, GameAction i) {
		this.text = text;
		this.c = c;
		this.bg = null;
		this.i = i;
		this.l = new Location(r.getX(), r.getY());
		this.w = (int) r.getWidth();
		this.h = (int) r.getHeight();
		hover = false;
	}
	
	public UIButton(String text, Rectangle r, Color c, BufferedImage bg, GameAction i) {
		this.text = text;
		this.c = c;
		this.bg = bg;
		this.i = i;
		this.l = new Location(r.getX(), r.getY());
		this.w = (int) r.getWidth();
		this.h = (int) r.getHeight();
		hover = false;
	}
	
	public UIButton(String text, Rectangle r, Color c, Color bgc, GameAction i)
	{
		this.text = text;
		this.c = c;
		this.bgc = bgc;
		this.i = i;
		this.l = new Location(r.getX(), r.getY());
		this.w = (int) r.getWidth();
		this.h = (int) r.getHeight();
		hover = false;
	}
	
	public UIButton(String text, Rectangle r, Color c, BufferedImage bg, Color bgc, GameAction i)
	{
		this.text = text;
		this.c = c;
		this.bg = bg;
		this.i = i;
		this.bgc = bgc;
		this.l = new Location(r.getX(), r.getY());
		this.w = (int) r.getWidth();
		this.h = (int) r.getHeight();
		hover = false;
	}
	
	public void render(Graphics g)
	{
		Color x = g.getColor();
		renderBg(g);
		//System.out.println("x" + l.getX() + " y" + l.getY() + " w" + w + " h" + h);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(c);
		TextLayout tl = new TextLayout(text, new Font("Arial",0,30), g2.getFontRenderContext());
		g2.drawRect((int)l.getX(),(int)l.getY(),this.w,this.h);
		g2.drawString(text,(int)(l.getX() + (1.5 * tl.getBounds().getWidth())), (int)l.getY() + h - (h/3));
		g.setColor(x);
	}
	
	public boolean isHovered()
	{
		return this.hover;
	}
	
	private void renderBg(Graphics g)
	{
		Color x = g.getColor();
		if(hover == false)
		{
			if (bg == null && bgc != null)
			{
				g.setColor(bgc);
				g.fillRect((int)l.getX(), (int)l.getY(), w,h);
			} else if (bg != null && bgc == null)
			{
				g.drawImage(bg, (int)l.getX(), (int)l.getY(), w,h,null);
			} else if (bg != null && bgc != null)
			{
				g.setColor(bgc);
				g.fillRect((int)l.getX(), (int)l.getY(), w,h);
				g.drawImage(bg, (int)l.getX(), (int)l.getY(), w,h,null);
			} else
			{
				g.setColor(Color.WHITE);
				g.fill3DRect((int)l.getX(), (int)l.getY(), w,h,true);
			}
		} else
		{
			g.setColor(Color.black);
			g.fillRect((int)l.getX(), (int)l.getY(), w,h);
		}
		g.setColor(x);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)l.getX(),(int)l.getY(),w,h);
	}
	
	public void clicked(Game g)
	{
		GameAction.doAction(g,i);
	}
	
	public void setHover(boolean b)
	{
		this.hover = b;
	}
	
	public boolean expired()
	{
		return false;
	}

}
