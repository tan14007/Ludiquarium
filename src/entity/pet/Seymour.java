package entity.pet;

import java.util.Random;

import entity.Tank;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import main.Main;

public class Seymour extends Pet {

	public Seymour(Tank tank) {
		super(tank);
		this.setSprite("Seymour.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public Seymour(int x, int y, Tank tank) {
		super(x, y, tank);
		this.setSprite("Seymour.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public void startAnimation() {
		new AnimationTimer() {
			Random random = new Random();
			long last;
			long duration = 0;
			long durationSec;
			long lastDurationSec;
			boolean action = false;

			@Override
			public void handle(long now) {
				// Normal Movement
				if (random.nextInt(100) > 60) {
					if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
						Random random = new Random();
						int diffX = random.nextInt(600) - 300;
						int diffY = random.nextInt(600) - 300;
						translateBy(diffX, diffY, (random.nextInt(5) + 3));
					}
				}

			}
		}.start();
	}

	public void startLogic() {
		logic = new AnimationTimer() {
			Random random = new Random();

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				// Normal Movement
				if (random.nextInt(100) > 60) {
					if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
						Random random = new Random();
						int diffX = random.nextInt(600) - 300;
						int diffY = random.nextInt(600) - 300;
						translateBy(diffX, diffY, (random.nextInt(5) + 3));
					}
				}
			}

		};

		logic.start();
	}
}
