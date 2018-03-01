package entity.item;

import java.util.Random;

import entity.Tank;
import entity.fish.SpecialFish;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class SpecialFishSeller extends SellingItem {

	protected final int[] levelPrice = {5000,10000,15000,20000};

	public SpecialFishSeller(Tank tank,int level) {
		super(tank);
		super.price = levelPrice[level - 1];
		try {
			sprite = new AnimatedObject("special.png", 10, 5);
			sprite.playAnimation(Animation.INDEFINITE, 0, 10, 0, 10);
			sprite.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					clickAction(event);
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clickAction(MouseEvent event) {
		// TODO Auto-generated method stub
		if (tank.getMoney() >= price) {
			SpecialFish specialFish = new SpecialFish(tank);
			tank.getEntity().add(specialFish);
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
