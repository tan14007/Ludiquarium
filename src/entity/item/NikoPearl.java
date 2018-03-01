package entity.item;

import entity.Clickable;
import entity.Entity;
import entity.Tank;
import entity.pet.Niko;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class NikoPearl extends TankItem implements Clickable {

	private final int pearlValue = 350;
	private Niko niko;

	public NikoPearl(int x, int y, Tank tank, Niko niko) {
		super(x, y, tank);
		this.niko = niko;
		sprite = new AnimatedObject("pearl.gif", 1, 1);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 0);
	}

	public NikoPearl(Tank tank, Niko niko) {
		super(tank);
		this.niko = niko;
		sprite = new AnimatedObject("pearl.gif", 1, 1);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 0);
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
		niko.close();
	}
	
	public Entity getParent() {
		return this.niko;
	}
}