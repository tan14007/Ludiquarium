package entity.alien;

import entity.Clickable;
import entity.Entity;
import entity.Feedable;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import main.Main;

public abstract class Alien extends Entity implements Movable, Killable, Clickable, Feedable {

	protected int fullness;
	protected int maxFullness;
	protected int hp;

	public Alien(Tank tank) {
		super(tank);
		maxFullness = 30;
	}

	public Alien(int x, int y, Tank tank) {
		super(x, y, tank);
		maxFullness = 30;
	}

	public boolean isHungry() {
		return this.fullness <= 0;
	}

	public void onHungry(Entity Food) {

	}

	public void decreaseHP(int amount) {
		this.hp -= amount;
	}

	public int getHP() {
		return this.hp > 0 ? this.hp : 0;
	}

	public void setHP(int hp) {
		this.hp = hp;
	}

	public void reduceFullness() {
		if (this.fullness > 0) {
			this.fullness -= 3;
		}
	}

	public int getMaxFullness() {
		return maxFullness;
	}
	
	public int getFullness() {
		return this.fullness;
	}

	@Override
	public void died() {
		tank.getEntity().remove(this);
		try {
			MediaPlayer sound = new AudioLoader().load("EXPLOSION1.mp3");
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
		logic.stop();
	}
	
	public void clickAction(MouseEvent event) {
		
	}
}
