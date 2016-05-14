package xdest.game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import xdest.game.Game;
import xdest.game.util.Renderable;

public class Menu extends MouseAdapter implements Renderable {

	private String name;
	private UIButton[] buttons;
	private BufferedImage bg;
	
	public Menu(String name, UIButton[] buttons, BufferedImage i)
	{
		this.name = name;
		this.buttons = buttons;
		this.bg = i;
	}
	
	public void render(Graphics g)
	{
		drawBg(g);
		drawTitle(g);
		drawOptions(g);
	}
	
	public void addFloatingButton(UIButton b)
	{
		UIButton[] addedButton = new UIButton[buttons.length+1];
		for (int i = 0; i < buttons.length; i++)
		{
			addedButton[i] = buttons[i];
		}
		addedButton[addedButton.length-1] = b;
	}
	
	private void drawBg(Graphics g)
	{
		g.setColor(Color.PINK);
		if (bg != null)
			g.drawImage(bg, 0, 0, null);
		else
			g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
	}

	private void drawOptions(Graphics g)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].render(g);
		}
	}
	
	private void drawTitle(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial",0,30));
		TextLayout tl = new TextLayout(name, new Font("Arial",0,30), ((Graphics2D)g).getFontRenderContext());
		g2.drawRect((Screen.WIDTH / 12), 2, 10 * Screen.WIDTH / 12, 30);
		g2.drawString(name, (int)((Screen.WIDTH / 2) - (tl.getBounds().getWidth() / 2)), 3 + 24);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		for (UIButton b : buttons)
		{
			if (b.getBounds().contains(e.getPoint()))
			{
				b.clicked(Game.getCurrentGame());
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		for (UIButton b : buttons)
		{
			if (b.getBounds().contains(e.getPoint()))
			{
				b.setHover(true);
			} else if (b.isHovered())
			{
				b.setHover(false);
			}
		}
	}
	
	@Override
	public boolean expired() {
		return false;
	}
	
}
