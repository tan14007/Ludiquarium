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

public class Diamond extends TankItem implements Clickable, Movable {

	private final int diamondValue = 200;
	private Entity fish; 

	public Diamond(int x, int y, Tank tank, Entity fish) {
		super(x, y, tank);
		this.sprite = new AnimatedObject("money.gif", 10, 6);
		this.fish = fish;
		sprite.playAnimation(Animation.INDEFINITE, 3, 1, 3, 10);
		startLogic();
	}

	public Diamond(Tank tank, Entity fish) {
		super(tank);
		this.fish = fish;
		this.sprite = new AnimatedObject("money.gif", 10, 6);
		sprite.playAnimation(Animation.INDEFINITE, 3, 1, 3, 10);
	}

	@Override
	public void clickAction(MouseEvent event) {
		if (tank.getItem().indexOf(this) != -1)
			tank.addMoney(diamondValue);
		tank.getItem().remove(this);
		((Craftable)this.fish).resetCoin();
		try {
			MediaPlayer sound = new AudioLoader().load("bonuscollect.mp3");
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
