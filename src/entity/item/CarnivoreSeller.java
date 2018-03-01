package entity.item;

import java.util.Random;

import entity.Tank;
import entity.fish.Carnivore;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class CarnivoreSeller extends SellingItem {

	public CarnivoreSeller(Tank tank) {
		super(tank);
		super.price = 1000;
		try {
			sprite = new AnimatedObject("Smallswim.gif", 10, 5);
			sprite.playAnimation(Animation.INDEFINITE, 4, 10, 4, 10);
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
			Carnivore carnivore = new Carnivore(tank);
			carnivore.startLogic();
			tank.getEntity().add(carnivore);
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
