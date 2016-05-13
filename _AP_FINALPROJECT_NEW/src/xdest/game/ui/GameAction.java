package xdest.game.ui;

import java.awt.Color;

import javax.swing.JOptionPane;

import xdest.game.Game;
import xdest.game.location.Location;
import xdest.game.sound.SoundMaster;

public enum GameAction {

	QUIT,START,HELP,SELECT_LEVEL;

	
	public static void doAction(Game g,GameAction i)
	{
		if (i == HELP)
		{
			JOptionPane.showMessageDialog(null, "Player 1 Moves with ASDW, Player 2 moves with JKLI\nPlayer 1 uses E to attack, Player 2 uses SPACE to attack\nYou attack the direction you are facing.");
		}
		if (i == QUIT)
		{
			g.createObject(new Text("Quitting", new Location((int)Screen.WIDTH /2, (int)Screen.HEIGHT / 2), Color.CYAN));
			g.quit();
		}
		if (i == START)
		{
			String s = JOptionPane.showInputDialog("Enter Player 1's name");
			String s1 = JOptionPane.showInputDialog("Enter Player 2's name");
			g.setName(1, s);
			g.setName(2, s1);
			g.setState(0);
			SoundMaster.stopSound("main_screen.wav");
			SoundMaster.playSound("fight_music.wav");
		}
	}
}
