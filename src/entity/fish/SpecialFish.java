package entity.fish;

import java.util.ArrayList;
import java.util.Random;

import entity.Clickable;
import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.item.Food;
import gui.EndingScene;
import gui.MainMenu;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class SpecialFish extends Fish implements Feedable, Killable, Movable {

	private int exp;
	private int level;

	public SpecialFish(Tank tank) {
		super(tank);
		this.level = 0;
		this.exp = 399;
		this.setSprite("special.png", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	public SpecialFish(int x, int y, Tank tank) {
		super(x, y, tank);
		this.level = 0;
		this.setSprite("special.png", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		startLogic();
	}

	public int getLevel() {
		return this.level;
	}

	@Override
	public void feed(Entity food) {
		// TODO Auto-generated method stub
		if (food instanceof Food) {
			super.fullness = super.MAX_FULLNESS;
			if (this.level < 4) {
				if (((Food) food).getFoodLevel() == 0)
					this.exp += 20;
				else if (((Food) food).getFoodLevel() == 1)
					this.exp += 35;
				else if (((Food) food).getFoodLevel() == 2)
					this.exp += 50;
				tank.getEntity().remove(food);
				if (this.exp >= 100 && this.exp < 200) {
					this.level = 1;
				}
				if (this.exp >= 200 && this.exp < 300) {
					this.level = 2;
				}
				if (this.exp >= 300 && this.exp < 400) {
					this.level = 3;
				}
				if (this.exp >= 400) {
					this.level = 4;
				}
			}
			if (this.level == 0) {
				this.setSprite("special.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
			} else if (this.level == 1) {
				this.setSprite("special.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
			} else if (this.level == 2) {
				this.setSprite("special.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
			} else if (this.level == 3) {
				this.setSprite("special.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
			} else if (this.level == 4) {
				this.setSprite("special.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 3, 0, 3, 10);
			}
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

	@Override
	public boolean isFuckingHungry() {
		if (fullness <= MAX_FULLNESS * 25 / 100) {
			if(sprite.getFileName() != "hungryspecial.png")
			if (this.level == 0) {
				this.setSprite("hungryspecial.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
			} else if (this.level == 1) {
				this.setSprite("hungryspecial.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
			} else if (this.level == 2) {
				this.setSprite("hungryspecial.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
			} else if (this.level == 3) {
				this.setSprite("hungryspecial.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 2, 0, 2, 10);
			} else if (this.level == 4) {
				this.setSprite("hungryspecial.png", 10, 5);
				sprite.playAnimation(Animation.INDEFINITE, 3, 0, 3, 10);
			}
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
			boolean action = false;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(!action) {
					action = true;
					reduceFullness();
					lastDurationSec = durationSec;
				}else if(lastDurationSec != durationSec) {
					action = false;
					if(level == 4) {
						try {
							MediaPlayer sound = new AudioLoader().load("crowned1.mp3");
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
						level = 0;
						exp = 0;
						for(Entity e: tank.getEntity()) {
							e.stopLogic();
						}
						for(Entity e: tank.getItem()) {
							e.stopLogic();
						}
						Main.setScene(EndingScene.getScene(0));
					}
				}
					
				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long)(duration/1e9);
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
							if (foodToFeed != null) {
								int diffX = foodToFeed.getX() - getX();
								int diffY = foodToFeed.getY() - getY();
								translateBy((int) Math.ceil(diffX / 5), (int) Math.ceil(diffY / 5), 0.05);
							} else if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
								Random random = new Random();
								int diffX = random.nextInt(600) - 300;
								int diffY = random.nextInt(600) - 300;
								translateBy(diffX, diffY, (random.nextInt(5) + 3));
							}
						}
					}

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
			}
		};

		logic.start();
	}

	@Override
	public Entity craft() {
		// TODO Auto-generated method stub
		return null;
	}
}
