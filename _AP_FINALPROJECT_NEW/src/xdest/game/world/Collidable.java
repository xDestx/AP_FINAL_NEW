package xdest.game.world;

import java.awt.Rectangle;


public interface Collidable {

	public boolean colliding(Rectangle r);
	public Rectangle getBounds();
	
}
