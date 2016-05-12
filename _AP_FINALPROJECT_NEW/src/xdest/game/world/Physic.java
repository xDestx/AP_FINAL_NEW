package xdest.game.world;

import java.awt.Rectangle;

public class Physic {

	public static boolean colliding(Collidable c, Rectangle r)
	{
		return c.colliding(r);
	}

}
