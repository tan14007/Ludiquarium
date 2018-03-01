package entity.item;

import entity.Clickable;
import entity.Craftable;
import entity.Entity;
import entity.Movable;
import entity.Tank;
import entity.fish.Guppy;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Coin extends TankItem implements Clickable, Movable {

	private int level;
	private Entity fish;
	private static final int[] coinValue = { 15, 35 };

	public Coin(int x, int y, Tank tank, Entity fish, int level) {
		super(x, y, tank);
		this.level = level;
		this.fish = fish;
		sprite = new AnimatedObject("money.gif", 10, 6);
		if (level == 0) {
			sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		}
		if (level == 1) {
			sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
		}
		startLogic();
	}

	public Coin(Tank tank, Entity fish, int level) {
		super(tank);
		this.level = level;
		this.fish = fish;
		sprite = new AnimatedObject("money.gif", 10, 6);
		if (level == 0) {
			sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
		}
		if (level == 1) {
			sprite.playAnimation(Animation.INDEFINITE, 1, 0, 1, 10);
		}
		startLogic();
	}

	@Override
	public void clickAction(MouseEvent event) {
		tank.addMoney(coinValue[this.level]);
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

	@Override
	public void onGrounded() {
		if (isGrounded()) {
			super.onGrounded();
			((Craftable)this.fish).resetCoin();
			logic.stop();
		}
	}

	public int getLevel() {
		return level;
	}
	
	public Entity getParent() {
		return this.fish;
	}

}
