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

public class SoundMaster {

	private static boolean init = false;
	private static HashMap<String,Clip> sounds;

	public static void init() {
		if (init)
			return;
		init = true;
		sounds = new HashMap<String,Clip>();
		String[] sound = { "/sounds/hitsound_real.wav", "/sounds/main_screen.wav", "/sounds/click_sound.wav"};
		for (String name : sound) {
			try {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(SoundMaster.class.getResourceAsStream(name));
				Clip c = AudioSystem.getClip();
				c.open(audioInputStream);
				sounds.put(name.substring(8),c);
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("memes");
	}

	public static void playSound(String path) {
		if (!init) {
			SoundMaster.init();
		}
		try {System.out.println("TOTAL ANNIHILTION");
			Clip c = sounds.get(path);
			if (c.getFramePosition() > 0)
				c.setFramePosition(0);
			c.start();
			System.out.println("WAAAHHHH");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void stopSound(String path) {
		if (!init) {
			SoundMaster.init();
		}
		try {System.out.println("TOTAL ANNIHILTION");
			Clip c = sounds.get(path);
			c.stop();
			if (c.getFramePosition() > 0)
				c.setFramePosition(0);
			System.out.println("WAAAHHHH");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
