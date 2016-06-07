package xdest.game.util.key;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import xdest.game.Game;
import xdest.game.util.Loader;

public class KeyConfig implements Serializable {

	/**
	 * 
	 */
	//key configuration
	private static final long serialVersionUID = -11940601235974528L;
	private int leftKeyP1, rightKeyP1, upKeyP1, downKeyP1, hitKeyP1;
	private int leftKeyP2, rightKeyP2, upKeyP2, downKeyP2, hitKeyP2;

	public KeyConfig() {
		// PLayer 1
		leftKeyP1 = KeyEvent.VK_A;
		rightKeyP1 = KeyEvent.VK_D;
		upKeyP1 = KeyEvent.VK_W;
		downKeyP1 = KeyEvent.VK_S;
		hitKeyP1 = KeyEvent.VK_SPACE;

		leftKeyP2 = KeyEvent.VK_LEFT;
		rightKeyP2 = KeyEvent.VK_RIGHT;
		upKeyP2 = KeyEvent.VK_UP;
		downKeyP2 = KeyEvent.VK_DOWN;
		hitKeyP2 = KeyEvent.VK_SHIFT;

	}
	
	public int getP2Left()
	{
		return leftKeyP2;
	}

	public int getP2Right()
	{
		return rightKeyP2;
	}
	
	public int getP2Up()
	{
		return upKeyP2;
	}
	
	public int getP2Down()
	{
		return downKeyP2;
	}
	
	public int getP2Hit()
	{
		return hitKeyP2;
	}
	
	public int getP1Left()
	{
		return leftKeyP1;
	}

	public int getP1Right()
	{
		return rightKeyP1;
	}
	
	public int getP1Up()
	{
		return upKeyP1;
	}
	
	public int getP1Down()
	{
		return downKeyP1;
	}
	
	public int getP1Hit()
	{
		return hitKeyP1;
	}
	
	public void setP1Hit(int key)
	{
		this.hitKeyP1 = key;
	}
	
	public void setP1Left(int key)
	{
		this.leftKeyP1 = key;
	}
	
	public void setP1Right(int key)
	{
		this.rightKeyP1 = key;
	}
	
	public void setP1Up(int key)
	{
		this.upKeyP1 = key;
	}
	
	public void setP1Down(int key)
	{
		this.downKeyP1 = key;
	}
	
	public void setP2Hit(int key)
	{
		this.hitKeyP2 = key;
	}
	
	public void setP2Left(int key)
	{
		this.leftKeyP2 = key;
	}
	
	public void setP2Right(int key)
	{
		this.rightKeyP2 = key;
	}
	
	public void setP2Up(int key)
	{
		this.upKeyP2 = key;
	}
	
	public void setP2Down(int key)
	{
		this.downKeyP2 = key;
	}
	
	

	// Export to Desktop
	public void export() {
		try {
			File f = new File(Loader.defaultPath + "/Config/");
			if(!f.exists())
			{
				f.mkdirs();
			}
			f = new File(Loader.defaultPath + "/Config/KeyConfig.scfg");
			if(!f.exists())
			{
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
