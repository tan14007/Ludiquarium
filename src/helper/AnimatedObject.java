package helper;

import javafx.scene.layout.Pane;

public class AnimatedObject extends Pane {

	AnimatedImage animatedImage;
	String fileName;
	
	public AnimatedObject(AnimatedObject other){
		this.animatedImage = other.animatedImage;
	}

	public AnimatedObject(String fileName, int columns, int rows) {
		try {
			animatedImage = new AnimatedImage(fileName, columns, rows);
			this.fileName = fileName;
			getChildren().setAll(animatedImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseAnimation() {
		try {
			getChildren().setAll(animatedImage);
			animatedImage.pauseAnimation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playAnimation(int cycleCount, int fromRow, int fromCol, int toRow, int toCol) {
		try {
			getChildren().setAll(animatedImage);
			animatedImage.playAnimation(cycleCount, fromRow, fromCol, toRow, toCol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getFileName() {
		return this.fileName;
	}

}
