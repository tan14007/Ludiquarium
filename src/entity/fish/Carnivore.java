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
import entity.item.Coin;
import entity.item.Diamond;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class Carnivore extends Fish implements Feedable, Craftable, Killable, Movable {

	public Carnivore(Tank tank) {
		super(tank);
		this.setSprite("Smallswim.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 4, 0, 4, 10);
		startLogic();
	}

	public Carnivore(int x, int y, Tank tank) {
		super(x, y, tank);
		this.setSprite("Smallswim.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 4, 0, 4, 10);
		startLogic();
	}

	@Override
	public Entity craft() {
		boolean crafted = false;
		for (Entity i : tank.getItem()) {
			if (i instanceof Diamond) {
				if (((Diamond) i).getParent() == this) {
					crafted = true;
					break;
				}
			}
		}
		if (!crafted) {
			coin = new Diamond(getX(), getY(), tank, this);
			tank.getItem().add(coin);
		}
		return coin;
	}

	@Override
	public void feed(Entity food) {
		if (food instanceof Guppy) {
			((Guppy) food).died();
			tank.getEntity().remove(food);
			setSprite("Smallswim.gif", 10, 5);
			sprite.playAnimation(Animation.INDEFINITE, 4, 0, 4, 10);
			fullness = MAX_FULLNESS;
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
			if (sprite.getFileName() != "hungryswim.gif") {
				setSprite("hungryswim.gif", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 4, 0, 4, 10);
			}
			return true;
		}
		return false;
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
				if (tick == 5) {
					if (tank.getEntity().indexOf(self) == -1) {
						died();
						stop();
					}
					craft();
					tick = 0;
					// Carnivore Attack is much fiercer
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
						((Alien) alien).decreaseHP(75);
					}
				} else if (lastDurationSec != durationSec) {
					lastDurationSec = durationSec;
					reduceFullness();
					tick = (tick + 1 > 5) ? 0 : tick + 1;
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
						ArrayList<Entity> entities = tank.getItemByRadius(getX(), getY(), 1000000);
						Entity foodToFeed = null;
						for (Entity e : entities) {
							if (e instanceof Guppy && ((Guppy) e).getLevel() == 0) {
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
