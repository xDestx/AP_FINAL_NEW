package xdest.game.sound;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import xdest.game.Game;

public class SoundMaster {

	private static boolean init = false;
	private static HashMap<String, Clip> sounds;

	public static void init() {
		if (init)
			return;
		Game.log("SoundMaster init...");
		init = true;
		sounds = new HashMap<String, Clip>();
		String[] sound = { "/sound/hitsound_real.wav", "/sound/main_screen.wav", "/sound/click_sound.wav",
				"/sound/fight_music.wav", "/sound/jump_sound.wav"};

		JFrame f = new JFrame("Meme hack");
		JPanel p = new JPanel();
		JLabel l = new JLabel("Downloading memes...");
		JProgressBar jpb = new JProgressBar(0,sound.length);
		p.add(l);
		p.add(jpb);
		f.add(p);
		f.setSize(400, 400);
		f.setResizable(false);
		f.setVisible(true);
		jpb.setString(""+((double)jpb.getValue()/(double)sound.length));
		jpb.setStringPainted(true);
		int currentLoaded = 0;
		for (int i = 0; i < sound.length; i++) {
			String x = sound[i];
			try {
				Game.log("Trying to load " + x + " ...");
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(SoundMaster.class.getResourceAsStream(x));
				Clip c = AudioSystem.getClip();
				c.open(audioInputStream);
				sounds.put(x.substring(7), c);
				Game.log("Loaded " + x);
				currentLoaded++;
				jpb.setValue(currentLoaded);
				jpb.setString(""+((double)currentLoaded/(double)sound.length));
			} catch (UnsupportedAudioFileException er) {
				er.printStackTrace();
			} catch (IOException er) {
				er.printStackTrace();
			} catch (LineUnavailableException er) {
				er.printStackTrace();
			}

		}
		f.setVisible(false);

	}
	
	public static boolean isPlaying(String path)
	{
		if (!init) {
			SoundMaster.init();
		}
		if (sounds.containsKey(path))
			return sounds.get(path).isActive();
		else
			return false;
	}

	public static void playSound(String path) {
		if (!init) {
			SoundMaster.init();
		}
		if (SoundMaster.isPlaying(path))
		{
			sounds.get(path).setFramePosition(0);
		}
		try {
			Clip c = sounds.get(path);
			if (c.getFramePosition() > 0)
				c.setFramePosition(0);
			c.start();
			Game.log("Playing sound " + path);
		} catch (Exception e) {
			Game.log("Failed to play " + path);
			e.printStackTrace();
		}
	}

	public static void stopSound(String path) {
		if (!init) {
			SoundMaster.init();
		}
		try {
			Clip c = sounds.get(path);
			c.stop();
			if (c.getFramePosition() > 0)
				c.setFramePosition(0);
			Game.log("Stopping and resetting sound " + path);
		} catch (Exception e) {
			Game.log("Failed to stop " + path);
			e.printStackTrace();
		}
	}

}
