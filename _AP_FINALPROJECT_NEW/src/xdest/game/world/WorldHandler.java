package xdest.game.world;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import xdest.game.util.ImageLoader;
import xdest.game.util.ImageX;

public class WorldHandler {

	public static void exportWorld(World w)
	{
		try
		{
			File f = new File(System.getProperty("user.home") + "/Desktop/" + w.getName() + ".wrd");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(w);
			oos.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static World getWorld(String name)
	{
		try
		{
			ObjectInputStream oos = new ObjectInputStream((WorldHandler.class.getResourceAsStream("/worlds/" + name + ".wrd")));
			World x = (World)oos.readObject();
			oos.close();
			return x;
		} catch (Exception e)
		{
			e.printStackTrace();
			return makeDefaultWorld();
		}
	}
	
	private static World makeDefaultWorld()
	{
		World w = new World("default", null, Color.WHITE);
		w.add(new Block(0,578,600,10));
		w.add(new Block(-10,0,10,600));
		w.add(new Block(600,0,10,600));
		w.add(new Block(0,-10,600,10));
		w.add(new Block(150,400,300,30));
		w.add(new Block(150,200,300,30));
		w.setComplete(true);
		return w;
	}
	///*
	public static void main(String[] args)
	{
		exportWorld(worldCreator());
	}
	//*/
	
	
	private static World worldCreator()
	{
		World w = new World("Level1", new ImageX(ImageLoader.loadImage("/images/Level1.png")) ,new Color(134,200,224));
		w.add(new Block(0,578,600,10));
		w.add(new Block(-10,0,10,600));
		w.add(new Block(600,0,10,600));
		w.add(new Block(0,-10,600,10));

		
		w.add(new IBlock(150,200,100,10, new ImageX(ImageLoader.loadImage("/images/Level1block.png"))));
		w.add(new IBlock(300,200,100,10, new ImageX(ImageLoader.loadImage("/images/Level1block.png"))));
		w.add(new IBlock(275,50,10,100, new ImageX(ImageLoader.loadImage("/images/Level1block.png"))));
		
		//w.add(new Block(200,200,100,10));
		//w.add(new Block(400,200,100,10));
		//w.add(new Block(350,100,10,100));
		
		
		
		w.setComplete(true);
		return w;
	}

}
