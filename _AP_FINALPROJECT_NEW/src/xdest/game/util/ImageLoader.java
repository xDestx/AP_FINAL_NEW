package xdest.game.util;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageLoader {

	//Loads an image
	public static BufferedImage loadImage(String path)
	{
		try
		{
			BufferedImage b;
			b = ImageIO.read(ImageLoader.class.getResource(path));
			return b;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
