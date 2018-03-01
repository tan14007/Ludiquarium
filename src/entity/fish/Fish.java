package entity.fish;

import entity.Craftable;
import entity.Entity;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.AnimationTimer;
import javafx.scene.media.MediaPlayer;
import main.Main;

public abstract class Fish extends Entity implements Movable, Killable, Craftable {

	protected int fullness;
	protected final int MAX_FULLNESS = 100;
	protected Thread moveThread;
	protected Entity coin = null;

	public Fish(int x, int y, Tank tank) {
		super(x, y, tank);
		this.fullness = 70;
	}

	public Fish(Tank tank) {
		super(tank);
		this.fullness = 70;
	}

	public boolean isStarving() {
		return fullness <= 0;
	}

	public boolean isHungry() {
		return fullness <= MAX_FULLNESS * 50 / 100;
	}

	public boolean isFuckingHungry() {
		return fullness <= MAX_FULLNESS * 25 / 100;
	}

	public int getMaxFullness() {
		return MAX_FULLNESS;
	}
	
	// Avoid Fish from being too low
	@Override
	public void setY(int y) {
		if (y < 100)
			this.y = 100;
		else if (y > Main.SCREEN_HEIGHT-170) 
			this.y = Main.SCREEN_HEIGHT-170;
		else
			this.y = y;
	}

	@Override
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void moveTo(double xx, double yy, double time) {
		double velocityX = (xx - getX()) / time;
		double velocityY = (yy - getY()) / time;
		double movePerFrameX = velocityX / (Main.FPS);
		double movePerFrameY = velocityY / (Main.FPS);System.out.println(xx + " " + getX());
		// TODO Fill Thread with function to move progressively 60 frame per second
		moveThread = new Thread(new Runnable() {
			@Override
			public void run() {
				new AnimationTimer() {
					long duration = 0;
					long last = -1;

					@Override
					public void handle(long now) {
						if (last == -1)
							last = now;
						duration += now - last;
						last = now;
						if (duration >= (1e9) * time) {
							moveThread = null;
							stop();
						}
						setX((int) Math.ceil(getX() + movePerFrameX));
						setY((int) Math.ceil(getY() + movePerFrameY));
					}
				}.start();
			}

		});

		moveThread.run();
	}

	@Override
	public void translate(int x, int y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public void translateBy(double xx, double yy, double time) {
		double velocityX = (xx) / time;
		double velocityY = (yy) / time;
		double movePerFrameX = velocityX / (Main.FPS);
		double movePerFrameY = velocityY / (Main.FPS);
		// TODO Fill Thread with function to move progressively 60 frame per second
		moveThread = new Thread(new Runnable() {
			@Override
			public void run() {
				new AnimationTimer() {
					long duration = 0;
					long last = -1;

					@Override
					public void handle(long now) {
						if (last == -1)
							last = now;
						duration += now - last;
						last = now;
						if (duration >= (1e9) * time) {
							moveThread = null;
							stop();
						}
						setX((int) Math.ceil(getX() + movePerFrameX));
						setY((int) Math.ceil(getY() + movePerFrameY));
					}
				}.start();
			}

		});

		moveThread.run();
	}

	@Override
	public void died() {
		tank.getEntity().remove(this);	
		stopLogic();
		try {
			MediaPlayer sound = new AudioLoader().load("HEAL.mp3");
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

	public Thread getMoveThread() {
		return this.moveThread;
	}
	
	public void reduceFullness() {
		if(this.fullness > 0) {
			this.fullness -= 3;
		}
	}
	
	public int getFullness() {
		return this.fullness;
	}
	
	public void resetCoin() {
		this.coin = null;
	}
}
