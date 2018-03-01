package entity.pet;

import java.util.ArrayList;

import entity.Craftable;
import entity.Entity;
import entity.Tank;
import entity.alien.Alien;
import entity.item.Coin;
import entity.item.NikoPearl;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.media.MediaPlayer;
import main.Main;

public class Niko extends Pet implements Craftable {

	public Niko(Tank tank) {
		super(tank);
		this.tank = tank;
		this.setSprite("Niko.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public Niko(int x, int y, Tank tank) {
		super(x, y, tank);
		this.tank = tank;
		this.setSprite("Niko.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	@Override
	public Entity craft() {
		// TODO Auto-generated method stub
		boolean crafted = false;
		for (Entity i : tank.getItem()) {
			if (i instanceof NikoPearl) {
				if (((NikoPearl) i).getParent() == this) {
					crafted = true;
					break;
				}
			}
		}
		if (!crafted) {
			this.setSprite("Niko.gif", 10, 3);
			sprite.playAnimation(9, 1, 0, 1, 10);
			NikoPearl pearl = new NikoPearl(this.x+5, this.y+5, tank, this);
			tank.getItem().add(pearl);
			try {
				MediaPlayer sound = new AudioLoader().load("nikoopen.mp3");
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
			return pearl;
		}
		return null;
	}

	@Override
	public void moveTo(int x, int y) {
	}

	@Override
	public void moveTo(double xx, double yy, double time) {

	}

	@Override
	public void translate(int x, int y) {

	}

	@Override
	public void translateBy(double xx, double yy, double time) {

	}

	public void close() {
		this.setSprite("Niko1.gif", 10, 3);
		sprite.playAnimation(1, 3, 0, 3, 10);
		this.setSprite("Niko.gif", 10, 3);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	public void startLogic() {
		new AnimationTimer() {
			long last;
			long duration = 0;
			long durationSec;
			long lastDurationSec;
			boolean action = false;
			int tick = 0;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (tick == 20) {
					craft();
					tick = 0;
				} else if (lastDurationSec != durationSec) {
					lastDurationSec = durationSec;
					tick++;
				}

				if (now - last > (1e9) / Main.FPS) {
					duration += (now - last);
					durationSec = (long) (duration / 1e9);
					last = now;
				}
			}
		}.start();
	}

	@Override
	public void resetCoin() {
		// TODO Auto-generated method stub

	}

}
