package helper;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class AudioLoader {
	public MediaPlayer load(String fileName) throws NullAudioException {
		MediaPlayer mediaPlayer = null;
		try {
			mediaPlayer = Main.sprite.getMedia(fileName);
			System.out.println(fileName + "Loaded");
		} catch (Exception e) {
			if (e instanceof NullPointerException)
				throw new NullAudioException(fileName);
			else
				throw e;
		}
		return mediaPlayer;
	}
}
