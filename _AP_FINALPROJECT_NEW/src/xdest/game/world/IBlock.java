package xdest.game.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import xdest.game.util.ImageX;

public class IBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3496377786621940268L;
	private ImageX i;
	private transient BufferedImage bg;
	
	public IBlock(int x, int y, int w, int h, ImageX i) {
		super(x,y,w,h);
		this.i = i;
	}
	
	@Override
	public void render(Graphics g)
	{
		super.render(g);
		Rectangle x = getBounds();
		if (i != null)
		{
			if (bg == null)
				bg = i.getImage();
			g.drawImage(bg, (int)x.getX(), (int)x.getY(),(int) x.getWidth(), (int)x.getHeight(), null);
		}
	}

}
