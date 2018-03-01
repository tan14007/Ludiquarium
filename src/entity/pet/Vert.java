package entity.pet;

import java.util.Random;

import entity.Craftable;
import entity.Entity;
import entity.Tank;
import entity.item.Coin;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import main.Main;

public class Vert extends Pet implements Craftable {
	private Coin coin = null;

	public Vert(Tank tank) {
		super(tank);
		this.setSprite("Vert.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public Vert(int x, int y, Tank tank) {
		super(x, y, tank);
		this.setSprite("Vert.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
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
			coin = new Coin(this.x, this.y, this.tank, this, 1);
			tank.getItem().add(coin);
		}
		return coin;
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
					craft();
					lastDurationSec = durationSec;
				} else if (lastDurationSec != durationSec) {
					action = false;
				}

				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long) (duration / 1e9);
					last = now;

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

	public void resetCoin() {
		this.coin = null;
	}
}
