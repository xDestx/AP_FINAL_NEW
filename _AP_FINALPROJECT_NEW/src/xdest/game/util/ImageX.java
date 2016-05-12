package xdest.game.util;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ImageX implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9202862969808969069L;
	private int w,h;
	private int[] rgb;
	
	public ImageX(BufferedImage i) {
		w = i.getWidth();
		h = i.getHeight();
		rgb = i.getRGB(0, 0, w, h, null, 0, w);
	}
	
	
	public BufferedImage getImage()
	{
		BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, w, h, rgb, 0, w);
		return bi;
	}

}
