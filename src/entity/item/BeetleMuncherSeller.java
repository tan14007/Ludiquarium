package entity.item;

import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

import entity.Tank;
import entity.fish.BeetleMuncher;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;

public class BeetleMuncherSeller extends SellingItem {

	public BeetleMuncherSeller(Tank tank) {
		super(tank);
		super.price = 2000;
		try {
			sprite = new AnimatedObject("Gekko.gif", 10, 7);
			sprite.playAnimation(Animation.INDEFINITE, 0, 10, 0, 10);
			sprite.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					clickAction(event);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clickAction(MouseEvent event) {
		// TODO Auto-generated method stub

		if (tank.getMoney() >= price) {
			BeetleMuncher beetleMuncher = new BeetleMuncher(tank);
			beetleMuncher.startLogic();
			tank.getEntity().add(beetleMuncher);
			tank.addMoney(-price);
			try {
				MediaPlayer sound = new AudioLoader().load("SPLASH3.mp3");
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
	}
}
