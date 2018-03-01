package entity.pet;

import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.Movable;
import entity.Tank;
import entity.alien.Alien;
import entity.alien.Gus;
import entity.fish.Guppy;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import main.Main;

public class Itchy extends Pet implements Movable {
	public Itchy(Tank tank) {
		super(tank);
		this.tank = tank;
		this.setSprite("itchy.gif", 10, 4);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public Itchy(int x, int y, Tank tank) {
		super(x, y, tank);
		this.tank = tank;
		this.setSprite("itchy.gif", 10, 4);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	@Override
	public void startLogic() {
		logic = new AnimationTimer() {
			Random random = new Random();
			long last;
			long duration = 0;
			long durationSec;
			long lastDurationSec;
			int tick = 0;
			boolean hasOverride = false;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (tick == 2) {
					tick = 0;
					ArrayList<Entity> entities = tank.getItemByRadius(getX(), getY(), 1600);
					Entity alien = null;
					for (Entity e : entities) {
						if (e instanceof Alien) {
							if (alien == null || getDistance(alien) > getDistance(e)) {
								alien = e;
							}
						}
					}
					if (alien != null) {
						((Alien) alien).decreaseHP(73);
					}
				} else if (lastDurationSec != durationSec) {
					lastDurationSec = durationSec;
					tick = (tick + 1 >= 3) ? 0 : tick + 1;
				}
				// System.out.println(lastDurationSec + " " + durationSec);

				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long) (duration / 1e9);
					last = now;

					// Hungry Movement
					ArrayList<Entity> entities = tank.getItemByRadius(getX(), getY(), 1000000);
					Entity foodToFeed = null;
					for (Entity e : entities) {
						if (e instanceof Alien) {
							if (foodToFeed == null || getDistance(foodToFeed) > getDistance(e)) {
								foodToFeed = e;
							}
						}
					}
					if (foodToFeed != null
							&& (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)) {
						double diffX = foodToFeed.getX() - getX();
						double diffY = foodToFeed.getY() - getY();
						translateBy(Math.ceil(diffX * 0.3), Math.ceil(diffY * 0.3), 0.1);
					} else if (foodToFeed != null && !hasOverride) {
						hasOverride = true;
						double diffX = foodToFeed.getX() - getX();
						double diffY = foodToFeed.getY() - getY();
						translateBy(Math.ceil(diffX * 0.3), Math.ceil(diffY * 0.3), 0.1);
					} else if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
						hasOverride = false;
						Random random = new Random();
						int diffX = random.nextInt(600) - 300 - random.nextInt(getX() > 0 ? getX() : 1) / 2;
						int diffY = random.nextInt(600) - 300 - random.nextInt(getY() > 0 ? getY() : 1) / 2;
						translateBy(diffX, diffY, (random.nextInt(5) + 3));
					}
				}
			}
		};

		logic.start();
	}
}
