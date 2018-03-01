package entity.fish;

import java.util.ArrayList;
import java.util.Random;

import entity.Craftable;
import entity.Clickable;
import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.alien.Alien;
import entity.item.Coin;
import entity.item.Food;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class Guppy extends Fish implements Feedable, Killable, Craftable, Movable {
	private int exp;
	private int level;

	public Guppy(Tank tank) {
		super(tank);
		this.exp = 0;
		this.level = 0;
		this.setSprite("Smallswim.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	public Guppy(int x, int y, Tank tank) {
		super(x, y, tank);
		this.exp = 90;
		this.level = 0;
		this.setSprite("Smallswim.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	@Override
	public Entity craft() {
		boolean crafted = false;
		for(Entity i: tank.getItem()) {
			if(i instanceof Coin) {
				if(((Coin)i).getParent() == this) {
					crafted = true;
					break;
				}
			}
		}
		if (!crafted) {
			if (getLevel() == 1) {
				coin = new Coin(getX(), getY(), this.tank, this, 0);
			}
			if (getLevel() >= 2) {
				coin = new Coin(getX(), getY(), this.tank, this, 1);
			}
			if (coin != null) {
				coin.getSprite().setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						((Clickable)coin).clickAction(event);
					}
				});
				tank.getItem().add(coin);System.out.println(tank.getItem().indexOf(coin) + "Crafted");
			}
		}
		return coin;
	}

	@Override
	public void feed(Entity food) {
		if (food instanceof Food) {
			fullness = MAX_FULLNESS;
			isFuckingHungry();
			if (((Food) food).getFoodLevel() == 0)
				this.exp += 20;
			else if (((Food) food).getFoodLevel() == 1)
				this.exp += 35;
			else if (((Food) food).getFoodLevel() == 2)
				this.exp += 50;

			if (this.exp >= 100 && this.exp < 300) {
				if (this.level != 1) {
					sprite.pauseAnimation();
					sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
				}
				this.level = 1;
			} else if (this.exp >= 300) {
				if (this.level != 2) {
					sprite.pauseAnimation();
					sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
				}
				this.level = 2;
			}
			if (tank.getEntity().indexOf(food) != -1)
				tank.decreaseCurrentFood();
			tank.getEntity().remove(food);

			try {
				MediaPlayer sound = new AudioLoader().load("cached_slurp.mp3");
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

	public int getLevel() {
		if (this.exp >= 100 && this.exp < 300) {
			this.level = 1;
		}
		if (this.exp >= 300) {
			this.level = 2;
		}
		return this.level;
	}

	@Override
	public boolean isFuckingHungry() {
		if (fullness <= MAX_FULLNESS * 25 / 100) {
			if (this.getSprite().getFileName() != "hungryswim.gif") {
				this.setSprite("hungryswim.gif", 10, 5);
				if (this.level == 0) {
					sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
				} else if (this.level == 1) {
					sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
				} else if (this.level == 2) {
					sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
				}
			}
			return true;
		} else {
			if (this.getSprite().getFileName() != "Smallswim.gif") {
				this.setSprite("Smallswim.gif", 10, 5);
				if (this.level == 0) {
					sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
				} else if (this.level == 1) {
					sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
				} else if (this.level == 2) {
					sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
				}
			}
			return false;
		}
	}

	@Override
	public void startLogic() {
		logic = new AnimationTimer() {
			Random random = new Random();
			long last;
			long duration = 0;
			long durationSec;
			long lastDurationSec;
			boolean action = false;
			boolean hasOverride = false;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (!action) {
					action = true;
					reduceFullness();
					if(tank.getEntity().indexOf(self) == -1) {
						died();
						stop();
					}
					craft();
					
					// Guppy's Attack!!!
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
						((Alien) alien).decreaseHP(1);
					}
					
				} else if (lastDurationSec != durationSec) {
					lastDurationSec = durationSec;
					action = false;
				}

				if (now - last > (1e9) / Main.FPS) {
					durationSec = (long) (duration / 1e9);
					duration += (now - last);
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
							if (e instanceof Food) {
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
