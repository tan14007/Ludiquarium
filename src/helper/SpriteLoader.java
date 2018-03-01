package helper;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SpriteLoader {
	private static Map<String, ImageView> iv = new HashMap<String, ImageView>();
	private static Map<String, MediaPlayer> mp = new HashMap<String, MediaPlayer>();
	
	public ImageView getImage(String fileName) {
		ImageView ret = null;
		try {
			if(iv.get(fileName) == null) {
				iv.put(fileName, new ImageView(new Image(this.getClass().getResource("/images/" + fileName).toExternalForm())));
			}
			ret = iv.get(fileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public MediaPlayer getMedia(String fileName) {
		MediaPlayer ret = null;
		try {
			if(mp.get(fileName) == null) {
				mp.put(fileName, new MediaPlayer(new Media(this.getClass().getResource("/audio/" + fileName).toExternalForm())));
			}
			ret = mp.get(fileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
