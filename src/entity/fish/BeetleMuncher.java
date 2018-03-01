package entity.fish;

import java.util.ArrayList;
import java.util.Random;

import entity.Craftable;
import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.alien.Alien;
import entity.item.Beetle;
import entity.item.Diamond;
import entity.item.Pearl;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class BeetleMuncher extends Fish implements Feedable, Craftable, Killable, Movable {
	private boolean isAlreadyHungry = false;

	public BeetleMuncher(int x, int y, Tank tank) {
		super(x, y, tank);

		sprite = new AnimatedObject("Gekko.gif", 10, 7);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	public BeetleMuncher(Tank tank) {
		super(tank);
		sprite = new AnimatedObject("Gekko.gif", 10, 7);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	@Override
	public Entity craft() {

		boolean crafted = false;
		for (Entity i : tank.getItem()) {
			if (i instanceof Pearl) {
				if (((Pearl) i).getParent() == this) {
					crafted = true;
					break;
				}
			}
		}
		if (!crafted) {
			coin = new Pearl(getX(), getY(), tank, this);
			tank.getItem().add(coin);
		}
		return coin;
	}

	@Override
	public void feed(Entity food) {
		// TODO Auto-generated method stub
		if (food instanceof Beetle) {
			super.fullness = super.MAX_FULLNESS;
			tank.getItem().remove(food);
			sprite = new AnimatedObject("Gekko.gif", 10, 7);
			sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
			try {
				MediaPlayer sound = new AudioLoader().load("cached_chomp2.mp3");
				sound.setCycleCount(1);
				sound.stop();
				sound.play();
			} catch (Exception e) {
				if (e instanceof NullAudioException)
					System.out.println(e.getMessage());
				else {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean isFuckingHungry() {
		if (fullness <= MAX_FULLNESS * 25 / 100) {
			if (!isAlreadyHungry)
				sprite = new AnimatedObject("Gekko.gif", 10, 7);
				sprite.playAnimation(Animation.INDEFINITE, 3, 0, 3, 10);
			isAlreadyHungry = true;
			return true;
		}
		return false;
	}

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
				if (tick == 15) {
					if (tank.getEntity().indexOf(self) == -1) {
						died();
						stop();
					}
					craft();
					tick = 0;
					// BeetleMuncher Attack is so strong!!!
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
						((Alien) alien).decreaseHP(1000);
					}
				} else if (lastDurationSec != durationSec) {
					lastDurationSec = durationSec;
					reduceFullness();
					tick = (tick + 1 > 15) ? 0 : tick + 1;
				}
				// System.out.println(lastDurationSec + " " + durationSec);

				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long) (duration / 1e9);
					last = now;

					// Frame action
					isFuckingHungry();
					if (isStarving()) {
						died();
						stop();
					}

					// Hungry Movement
					if (isHungry()) {
						Entity foodToFeed = null;
						for (Entity e : tank.getItem()) {
							if (e instanceof Beetle) {
								if (foodToFeed == null || getDistance(foodToFeed) > getDistance(e)) {
									foodToFeed = e;
								}
							}
						}
						if (foodToFeed != null && getDistance(foodToFeed) <= Tank.DIST_THRESH) {
							feed(foodToFeed);
						} else {
							if (foodToFeed != null
									&& (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)) {
								double diffX = foodToFeed.getX() - getX();
								double diffY = foodToFeed.getY() - getY();
								translateBy(Math.ceil(diffX * 0.2), Math.ceil(diffY * 0.2), 0.1);
							} else if (foodToFeed != null && !hasOverride) {
								hasOverride = true;
								double diffX = foodToFeed.getX() - getX();
								double diffY = foodToFeed.getY() - getY();
								translateBy(Math.ceil(diffX * 0.2), Math.ceil(diffY * 0.2), 0.1);
							} else if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
								hasOverride = false;
								Random random = new Random();
								int diffX = random.nextInt(600) - 300 - random.nextInt(getX() > 0 ? getX() : 1) / 2;
								int diffY = random.nextInt(600) - 300 - random.nextInt(getY() > 0 ? getY() : 1) / 2;
								translateBy(diffX, diffY, (random.nextInt(5) + 3));
							}
						}
					}

					// Normal Movement
					if (random.nextInt(100) > 60 && !isHungry()) {
						if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
							hasOverride = false;
							Random random = new Random();
							int diffX = random.nextInt(600) - 300 - random.nextInt(getX() > 0 ? getX() : 1) / 2;
							int diffY = random.nextInt(600) - 300 - random.nextInt(getY() > 0 ? getY() : 1) / 2;
							translateBy(diffX, diffY, (random.nextInt(5) + 3));
						}
					}
				}
			}
		};
		
		logic.start();
	}

}
