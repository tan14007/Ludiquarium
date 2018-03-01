package entity.alien;

import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.fish.Fish;
import entity.item.Coin;
import entity.item.Diamond;
import entity.item.Food;
import entity.item.Pearl;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class Gus extends Alien implements Feedable, Killable, Movable {

	public Gus(Tank tank) {
		super(tank);
		this.maxFullness = 15;
		this.fullness = maxFullness;
		this.hp = 2000;
		sprite = new AnimatedObject("gus.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		sprite.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				clickAction(event);
			}
		});
		startLogic();
	}

	@Override
	public void feed(Entity food) {
		if (food instanceof Fish) {
			super.hp -= 50;
			tank.getEntity().remove(food);
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
		if (food instanceof Food) {
			super.hp -= 50;
			if (tank.getEntity().indexOf(food) != -1)
				tank.decreaseCurrentFood();
			tank.getEntity().remove(food);
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
		if (food instanceof Coin) {
			tank.getItem().remove(food);
			((Fish) ((Coin) food).getParent()).resetCoin();
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
		if (food instanceof Diamond) {
			tank.getItem().remove(food);
			((Fish) ((Diamond) food).getParent()).resetCoin();
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
		if (food instanceof Pearl) {
			tank.getItem().remove(food);
			((Fish) ((Pearl) food).getParent()).resetCoin();
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
		System.out.println(getHP());
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

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (!action) {
					action = true;
					reduceFullness();
					lastDurationSec = durationSec;
				} else if (lastDurationSec != durationSec) {
					action = false;
				}

				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long) (duration / 1e9);
					last = now;

					// Frame action
					if (getHP() <= 0) {
						died();
					}

					// Hungry Movement
					if (isHungry()) {
						ArrayList<Entity> entities = tank.getItemByRadius(getX(), getY(), 1000000);
						Entity foodToFeed = null;
						for (Entity e : entities) {
							if (e instanceof Fish || e instanceof Food) {
								if (foodToFeed == null || getDistance(foodToFeed) > getDistance(e)) {
									foodToFeed = e;
								}
							}
						}
						for (Entity e : tank.getItem()) {
							if (e instanceof Coin || e instanceof Diamond || e instanceof Pearl) {
								if (foodToFeed == null || getDistance(foodToFeed) > getDistance(e)) {
									foodToFeed = e;
								}
							}
						}
						if (foodToFeed != null && getDistance(foodToFeed) <= Tank.DIST_THRESH * 2
								&& (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)) {
							feed(foodToFeed);
						} else {
							if (foodToFeed != null
									&& (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)) {
								double diffX = foodToFeed.getX() - getX();
								double diffY = foodToFeed.getY() - getY();
								translateBy(Math.ceil(diffX * 0.2), Math.ceil(diffY * 0.2), 0.1);
							}
						}
					}

					// Normal Movement
					if (random.nextInt(100) > 60 && !isHungry()) {
						if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED) {
							Random random = new Random();
							int diffX = random.nextInt(600) - 300 - random.nextInt(getX() > 0 ? getX() : 1) / 2;
							int diffY = random.nextInt(600) - 300 - random.nextInt(getY() > 0 ? getY() : 1) / 2;
							translateBy(diffX, diffY, (random.nextInt(5) + 3));
							System.out.println(diffX + " " + diffY);
						}
					}
				}
			}
		};

		logic.start();
	}
}
