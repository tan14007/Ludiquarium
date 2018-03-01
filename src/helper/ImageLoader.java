package helper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;

public class ImageLoader {
	public ImageView load(String fileName) throws NullPictureException {
		ImageView iv = null;
		try {
			iv = Main.sprite.getImage(fileName);
			System.out.println(fileName + " has been Loaded.");
			if(iv == null) {
				throw new NullPictureException(fileName);
			}
			return iv;
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException || e instanceof NullPointerException) {

				throw new NullPictureException(fileName);
			} else {
				throw e;
			}
		}
	}
}
