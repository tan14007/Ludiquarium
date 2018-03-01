package entity;

import java.util.Random;

import helper.AnimatedObject;
import javafx.animation.AnimationTimer;
import main.Main;

public abstract class Entity implements Movable{

	protected int x;
	protected int y;
	protected Tank tank;
	protected AnimatedObject sprite;
	protected AnimationTimer logic = null;
	protected Entity self; 
	protected Thread moveThread;


	public Entity(Tank tank) {
		Random random = new Random();
		this.x = random.nextInt(Main.SCREEN_WIDTH);
		this.y = random.nextInt(Main.SCREEN_HEIGHT);
		this.tank = tank;
		this.self = this;
	}

	public Entity(int x, int y, Tank tank) {
		this.x = x;
		this.y = y;
		this.tank = tank;
		this.self = this;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		if (x < 0)
			this.x = 0;
		else if (x > Main.SCREEN_WIDTH-100)
			this.x = Main.SCREEN_WIDTH-100;
		else
			this.x = x;
	}

	public void setY(int y) {
		if (y < 100)
			this.y = 100;
		else if (y > Main.SCREEN_HEIGHT-100)
			this.y = Main.SCREEN_HEIGHT-100;
		else
			this.y = y;
	}

	public AnimatedObject getSprite() {
		return this.sprite;
	}

	public void setSprite(String fileName, int column, int row) {
		try {
			if(sprite != null) {
				sprite.pauseAnimation();
				sprite = null;
			}
			sprite = new AnimatedObject(fileName, column, row);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Return Euclidean Distance**2 (use **2 for performance)
	 * 
	 * @return int
	 */
	public int getDistance(Entity other) {
		return (other.getX() - getX()) * (other.getX() - getX())
				+ (other.getY() - getY()) * (other.getY() - getY());
	}
	
	public void startLogic() {
		logic = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		logic.start();
	}
	
	public void stopLogic() {
		if(logic != null) {
			logic.stop();
			logic = null;
		}
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
}