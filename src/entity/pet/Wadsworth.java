package entity.pet;

import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.Tank;
import entity.alien.Alien;
import entity.fish.Guppy;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import main.Main;

public class Wadsworth extends Pet {

	ArrayList<Guppy> guppies = new ArrayList<Guppy>();

	public Wadsworth(Tank tank) {
		super(tank);
		this.setSprite("Wadsworth.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public Wadsworth(int x, int y, Tank tank) {
		super(x, y, tank);
		this.setSprite("Wadsworth.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public void saveGuppy() {
		for (Entity entity : tank.getEntity()) {
			if (entity instanceof Guppy) {
				if (((Guppy) entity).getLevel() < 1) {
					guppies.add((Guppy) entity);
					((Guppy) entity).stopLogic();
					tank.getEntity().remove(entity);
				}
			}

		}
	}

	public void releaseGuppy() {
		for (Guppy guppy : guppies) {
			tank.getEntity().add(guppy);
			guppy.startLogic();
			guppies.remove(guppy);
		}
	}

	public void startLogic() {
		logic = new AnimationTimer() {
			Random random = new Random();

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub

				// When aliens come
				ArrayList<Entity> entities = tank.getItemByRadius(getX(), getY(), 1000000);
				Entity foodToFeed = null;
				for (Entity e : entities) {
					if (e instanceof Alien) {
						if (foodToFeed == null || getDistance(foodToFeed) > getDistance(e)) {
							foodToFeed = e;
						}
					}
				}
				if (foodToFeed != null) {
					saveGuppy();
				} else {
					if(guppies.size() != 0)releaseGuppy();
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

		};

		logic.start();
	}
}
