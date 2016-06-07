package xdest.game.world;

import java.awt.Rectangle;

public class Physic {

	//Check if colliding
	public static boolean colliding(Collidable c, Rectangle r)
	{
		return c.colliding(r);
	}

}
