package xdest.game.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import xdest.game.Game;
import xdest.game.location.Location;
import xdest.game.sound.SoundMaster;
import xdest.game.util.key.KeySelectFrame;

public enum GameAction {

	QUIT,START,HELP,SELECT_LEVEL,MUTE,KEYC;

	
	public static void doAction(Game g,GameAction i)
	{
		if (i == HELP)
		{
			JOptionPane.showMessageDialog(null, "Player 1 Moves with ASDW, Player 2 moves with JKLI\nPlayer 1 uses C to attack, Player 2 uses SPACE to attack\nYou attack the direction you are facing (The red box).");
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
			if(s.length() == 0)
			{
				s = "1";
			}
			if(s1.length() == 0)
			{
				s1 = "2";
			}
			g.setName(1, s);
			g.setName(2, s1);
			g.setState(0);
			SoundMaster.stopSound("main_screen.wav");
			SoundMaster.playSound("fight_music.wav");
		}
		if (i == MUTE)
			SoundMaster.mutePressed();
		if(i == KEYC)
		{
			KeySelectFrame ksf = new KeySelectFrame(Game.getCurrentGame().getKeyConfig());
			ksf.setVisible(true);
		}
	}
}
