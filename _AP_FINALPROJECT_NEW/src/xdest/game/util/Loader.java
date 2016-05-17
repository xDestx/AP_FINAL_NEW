package xdest.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import xdest.game.util.key.KeyConfig;

public class Loader {

	public static final String defaultPath = System.getProperty("user.home") + "/Desktop/SJFAXVII";

	public static KeyConfig loadKeyConfig() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(new File(defaultPath + "/Config/KeyConfig.scfg")));
			KeyConfig kc = (KeyConfig) ois.readObject();
			ois.close();
			return kc;
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}

		try {
			ObjectInputStream ois = new ObjectInputStream(Loader.class.getResourceAsStream("/config/KeyConfig.scfg"));
			KeyConfig kc = (KeyConfig) ois.readObject();
			ois.close();
			return kc;
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return new KeyConfig();
		
	}
	

}
