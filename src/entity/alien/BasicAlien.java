package entity.alien;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

import java.util.ArrayList;
import java.util.Random;

import entity.Clickable;
import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.fish.Fish;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;

public class BasicAlien extends Alien implements Feedable, Clickable, Killable, Movable {

	public BasicAlien(Tank tank) {
		super(tank);
		maxFullness = 30;
		fullness = maxFullness;
		hp = 150;
		sprite = new AnimatedObject("sylv.gif", 10, 2);
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
		// TODO Auto-generated method stub
		if (food instanceof Fish) {
			((Fish) food).died();
			fullness = maxFullness;
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
							if (e instanceof Fish) {
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
						}
					}
				}
			}
		};

		logic.start();
	}

	@Override
	public void clickAction(MouseEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Alien: " + getHP() + " " + event.getSceneX() + " " + (getX() + sprite.getWidth() / 2) );
		if(moveThread != null) {
			moveThread.interrupt();
			moveThread = null;
		}
		translateBy(75 * (event.getSceneX() < getX() + 80 ? -1 : 1),
				75 * (event.getSceneY() < getY() + 80 ? -1 : 1), 3);
		decreaseHP((tank.getGunLevel() + 1) * (tank.getGunLevel() + 1) * 3);
		try {
			MediaPlayer sound = new AudioLoader().load("HIT.mp3");
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
