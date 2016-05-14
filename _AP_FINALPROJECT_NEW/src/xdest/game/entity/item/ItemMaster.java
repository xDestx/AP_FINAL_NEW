package xdest.game.entity.item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import xdest.game.Game;
import xdest.game.GameObject;
import xdest.game.location.Location;
import xdest.game.util.Renderable;

public class ItemMaster implements GameObject, Renderable {

	private ArrayList<String> itemNames;
	private int counter;
	public ItemMaster() {
		//To be created with game
		init();
	}
	
	private void init()
	{
		counter = 0;
		//Create all items
		itemNames = new ArrayList<String>();
		itemNames.add("heal");
		itemNames.add("regen");
	}

	@Override
	public void update(Game g) {
		if(g.getState() == Game.FIGHT)
		{
			counter++;
			if(counter >= (30*100))
			{
				if ((Math.random() * 100) <= 0.25)
				{
					int x = (int)(Math.random() * itemNames.size());
					Item i = Item.create(itemNames.get(x), new Location((int)(Math.random() * 500),(int)(Math.random() * 500)));
					if (i == null)
					{
						counter = 0;
						Game.log("Created a null item ?? with " + itemNames.get(x));
						return;
					}
					g.createObject(i);
					counter = 0;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (Game.getCurrentGame().getState() == Game.FIGHT)
		{
			String x = (int)(counter/100) + "/30";
			int l = x.length();
			int space = 100;
			g.setFont(new Font("Arial", 0, 14));
			g.setColor(Color.white);
			g.fillRect(250, 54, space, 20);
			g.setColor(Color.black);
			g.drawRect(250, 54, space, 20);
			for (int i = 0; i < l; i++)
			{
				g.drawString(""+x.charAt(i), 250 + ((space / (l + 1)) * (i + 1)), 68);
			}
		}
	}

	@Override
	public boolean expired() {
		// TODO Auto-generated method stub
		return false;
	}

}
