package helper;

import javafx.animation.Transition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedImage extends ImageView {

	private Rectangle2D[] clips;
	private double width, height;
	private ImageView image;
	private Transition trans;
	private int columns;
	private int rows;

	public AnimatedImage(String fileName, int columns, int rows) throws NullPictureException {

		image = new ImageLoader().load(fileName);
		width = image.getImage().getWidth() / columns;
		height = image.getImage().getHeight() / rows;
		this.columns = columns;
		this.rows = rows;

		clips = new Rectangle2D[rows * columns];
		int count = 0;
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++, count++) {
				clips[count] = new Rectangle2D(width * column, height * row, width, height);
			}
		}

		setImage(image.getImage());
		setViewport(clips[0]);
	}

	public void pauseAnimation() {
		trans.stop();
	}

	public void playAnimation(int cycleCount, int fromRow, int fromCol, int toRow, int toCol) {
		IntegerProperty count = new SimpleIntegerProperty(fromRow * columns);
		trans = new Transition() {
			{
				setCycleDuration(Duration.millis(1000 / 20.0));
			}

			@Override
			public void interpolate(double frac) {
				if (frac != 1)
					return;
				// End of one cycle.
				if (count.getValue() + 1 < toRow * columns + toCol) 
					count.setValue(count.getValue() + 1);
				else
					count.setValue(fromRow * columns);
				setViewport(clips[count.getValue()]);
			}

		};
		trans.setCycleCount(cycleCount);
		trans.playFromStart();
	}
}