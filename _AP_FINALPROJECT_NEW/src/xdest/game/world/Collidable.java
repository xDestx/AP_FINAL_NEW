package xdest.game.world;

import java.awt.Rectangle;


public interface Collidable {

	//Does this collide?
	public boolean colliding(Rectangle r);
	public Rectangle getBounds();
	
}
