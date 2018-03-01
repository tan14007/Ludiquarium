package entity.item;

import entity.Clickable;
import entity.Entity;
import entity.Movable;
import entity.Tank;
import entity.pet.Seymour;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import main.Main;

public abstract class TankItem extends Entity implements Movable, Clickable {

	private static double fallFactor = 1;

	public TankItem(int x, int y, Tank tank) {
		super(x, y, tank);
		for (Entity e : tank.getEntity()) {
			if (e instanceof Seymour) {
				fallFactor = 2;
				break;
			}
		}
	}

	public TankItem(Tank tank) {
		super(tank);
		for (Entity e : tank.getEntity()) {
			if (e instanceof Seymour) {
				fallFactor = 2;
				break;
			}
		}
	}

	public boolean isGrounded() {
		return getY() >= Main.SCREEN_HEIGHT - 100;
	}

	public void onGrounded() {
		if (isGrounded()) {
			tank.getItem().remove(this);
			logic.stop();
		}
	}

	public double getFallFactor() {
		return fallFactor;
	}

	public void setFallFactor(double fallFactor) {
		TankItem.fallFactor = fallFactor;
	}
	
	@Override
	public void startLogic() {
		logic = new AnimationTimer() {
			long last;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (now - last > (1e9) / Main.FPS) {
					last = now;
					if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)
						translateBy(0, 10, 1 * fallFactor);
					if (isGrounded()) {
						onGrounded();
						stop();
					}
				}
			}
		};
		logic.start();
}
	
	public void clickAction(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
