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
			} catch (UnsupportedAudioFileException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			} catch (IOException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			} catch (LineUnavailableException er) {
				// TODO Auto-generated catch block
				er.printStackTrace();
			}

		}

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
