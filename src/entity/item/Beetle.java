package entity.item;

import entity.Clickable;
import entity.Craftable;
import entity.Entity;
import entity.Movable;
import entity.Tank;
import entity.fish.GuppyCruncher;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class Beetle extends TankItem implements Clickable, Movable {

	private final int beetleValue = 150;
	private Entity fish;

	public Beetle(int x, int y, Tank tank, Entity fish) {
		super(x, y, tank);
		this.fish = fish;
		sprite = new AnimatedObject("money.gif", 10, 6);
		sprite.playAnimation(Animation.INDEFINITE, 5, 0, 5, 10);
		startLogic();
	}

	public Beetle(Tank tank, Entity fish) {
		super(tank);
		this.fish = fish;
		sprite = new AnimatedObject("money.gif", 10, 6);
		sprite.playAnimation(Animation.INDEFINITE, 5, 0, 5, 10);
		startLogic();
	}

	@Override
	public void clickAction(MouseEvent event) {

		tank.addMoney(beetleValue);
		tank.getItem().remove(this);
		((Craftable) this.fish).resetCoin();
		try {
			MediaPlayer sound = new AudioLoader().load("POINTS2.mp3");
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

	public Entity getParent() {
		// TODO Auto-generated method stub
		return this.fish;
	}

	@Override
	public boolean isGrounded() {
		return getY() < 100;
	}

	@Override
	public void startLogic() {
		logic = new AnimationTimer() {
			long last;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (now - last > (1e9) / Main.FPS) {
					if (moveThread != null)
						System.out.println(moveThread.getState());
					last = now;
					if (moveThread == null || moveThread.getState() == Thread.State.TERMINATED)
						translateBy(0, -100, 1 * getFallFactor());
					if (isGrounded()) {
						onGrounded();
						stop();
					}
				}
			}
		};
		logic.start();
	}
}