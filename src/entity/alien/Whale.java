package entity.alien;

import java.util.ArrayList;

import entity.Clickable;
import entity.Craftable;
import entity.Entity;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.fish.Fish;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class Whale extends BasicAlien implements Craftable, Killable, Movable, Clickable {

	private ArrayList<LittleAlien> alienList = new ArrayList<LittleAlien>();

	public Whale(Tank tank) {
		super(tank);
		super.maxFullness = 70;
		super.hp = 2500;
		this.setSprite("boss.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}

	@Override
	public void feed(Entity food) {
		if (alienList.size() + 1 <= 10) {
			LittleAlien alien = new LittleAlien(food.getX(), food.getY(), this.tank, this);
			tank.getEntity().add(alien);
			alienList.add(alien);
		}
		super.feed(food);
		try {
			MediaPlayer sound = new AudioLoader().load("cached_chomp2.mp3");
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
	public Entity craft() {
		// TODO Auto-generated method stub
		LittleAlien alien = null;
		if (alienList.size() + 1 <= 10) {
			alien = new LittleAlien(this.tank, this);
			tank.getEntity().add(alien);
			alienList.add(alien);
		}
		return alien;
	}

	@Override
	public void resetCoin() {
		// TODO Auto-generated method stub
	}

	public void removeAlien(LittleAlien alien) {
		alienList.remove(alien);
	}

}
