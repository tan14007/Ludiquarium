package entity.item;

import entity.Clickable;
import entity.Craftable;
import entity.Entity;
import entity.Movable;
import entity.Tank;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class Pearl extends TankItem implements Clickable, Movable {

	private final int pearlValue = 350;
	private Entity fish = null;

	public Pearl(int x, int y, Tank tank, Entity fish) {
		super(x, y, tank);
		this.fish = fish;
		sprite = new AnimatedObject("pearl.gif", 1, 1);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 0);
		startLogic();
	}

	public Pearl(Tank tank, Entity fish) {
		super(tank);
		this.fish = fish;
		sprite = new AnimatedObject("pearl.gif", 1, 1);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 0);
		startLogic();
	}

	@Override
	public void clickAction(MouseEvent event) {
		tank.addMoney(pearlValue);
		tank.getItem().remove(this);
		try {
			MediaPlayer sound = new AudioLoader().load("PEARL.mp3");
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
	
	@Override
	public void onGrounded() {
		if (isGrounded()) {
			tank.getItem().remove(this);
			((Craftable)this.fish).resetCoin();
		}
	}

	public Entity getParent() {
		return this.fish;
	}
	
}