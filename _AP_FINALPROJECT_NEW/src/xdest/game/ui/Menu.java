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
	private double x;

	public Menu(String name, UIButton[] buttons, BufferedImage i) {
		this.name = name;
		this.buttons = buttons;
		this.bg = i;
		x = 0;
	}

	public void render(Graphics g) {
		drawBg(g);
		drawTitle(g);
		drawWave(g);
		drawOptions(g);
	}
	
	public void tick()
	{
		x+=1.35;
	}

	//Draw the wave based on ticks passed
	private void drawWave(Graphics g) {
		Color last = g.getColor();
		g.setColor(Color.cyan);
		int rangeX = 600;
		int rangeY = 600;
		int startX = 0;
		int startY = 0;
		for (UIButton b : buttons) {
			if (b.isHovered()) {
				rangeX = (int) b.getBounds().getWidth() + 50;
				rangeY = (int) b.getBounds().getHeight() + 50;
				startX = (int) b.getBounds().getX() - 25;
				startY = (int) b.getBounds().getY() - 25;
				for (int i = 0; i < rangeX; i++) {
					g.setColor(Color.blue);
					g.fillRect(0 + startX + i, startY + (int) (Math.cos(Math.toRadians(x + i*2)) * 5), 1, 1);

					g.setColor(Color.red);
					g.fillRect(599 - i - startX, startY + rangeY + (int) (Math.cos(Math.toRadians(x + i*2)) * 5), 1, 1);
				}
				for (int i = 0; i < rangeY; i++) {
					g.setColor(Color.blue);
					g.fillRect(600 - startX + (int) (Math.sin(Math.toRadians(x + i*2)) * 5), startY + i, 1, 1);
					g.setColor(Color.red);
					g.fillRect(startX + (int) (Math.sin(Math.toRadians(x + i*2)) * 5), startY + (rangeY - i), 1, 1);
				}
				return;
			}
		}
		for (int i = 0; i < rangeX; i++) {
			// filled - g.drawLine(i, 300, i, 300 +
			// (int)(Math.cos(Math.toRadians(x + (i*2))) * 20));
			// unfilled
			g.setColor(Color.blue);
			g.fillRect(0 + i, 100 + (int) (Math.cos(Math.toRadians(x + i)) * 20), 1, 1);
			g.fillRect(500+(int)(Math.cos(Math.toRadians(x + i)) * 20), i, 1, 1);
			g.setColor(Color.red);
			g.fillRect(599 - i, 500 + (int) (Math.cos(Math.toRadians(x + i)) * 20), 1, 1);
			g.fillRect(100+(int)(Math.cos(Math.toRadians(x + i)) * 20), 599-i, 1, 1);
		}
		
		g.setColor(last);
	}
	
	//Add a button anywhere
	public void addFloatingButton(UIButton b) {
		UIButton[] addedButton = new UIButton[buttons.length + 1];
		for (int i = 0; i < buttons.length; i++) {
			addedButton[i] = buttons[i];
		}
		addedButton[addedButton.length - 1] = b;
		buttons = addedButton;
	}

	private void drawBg(Graphics g) {
		g.setColor(Color.PINK);
		if (bg != null)
			g.drawImage(bg, 0, 0, null);
		else
			g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
	}

	private void drawOptions(Graphics g) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].render(g);
		}
	}

	private void drawTitle(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", 0, 30));
		TextLayout tl = new TextLayout(name, new Font("Arial", 0, 30), ((Graphics2D) g).getFontRenderContext());
		g2.drawRect((Screen.WIDTH / 12), 2, 10 * Screen.WIDTH / 12, 30);
		g2.drawString(name, (int) ((Screen.WIDTH / 2) - (tl.getBounds().getWidth() / 2)), 3 + 24);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (UIButton b : buttons) {
			if (b.getBounds().contains(e.getPoint())) {
				b.clicked(Game.getCurrentGame());
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (UIButton b : buttons) {
			if (b.getBounds().contains(e.getPoint())) {
				b.setHover(true);
			} else if (b.isHovered()) {
				b.setHover(false);
			}
		}
	}

	@Override
	public boolean expired() {
		return false;
	}

}
