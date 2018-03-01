package entity.item;

import entity.Tank;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class FoodLevelSeller extends SellingItem {

	public FoodLevelSeller(Tank tank) {
		super(tank);
		// TODO Auto-generated constructor stub
		this.price = 300;
		sprite = new AnimatedObject("foodquality.png", 4, 1);
		sprite.playAnimation(1, 0, 0, 0, 1);
		sprite.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				clickAction(event);
			}
		});
	}

	@Override
	public void clickAction(MouseEvent event) {
		// TODO Auto-generated method stub
		if (tank.getMoney() >= price && tank.getFoodLevel() < 2) {
			tank.increaseFoodLevel();
			tank.addMoney(-price);
			if (tank.getFoodLevel() == 1) {
				sprite.pauseAnimation();
				sprite.playAnimation(1, 0, 1, 0, 2);
			} else {
				sprite.pauseAnimation();
				sprite.playAnimation(1, 0, 3, 0, 3);
				this.price = 0;
			}
			try {
				MediaPlayer sound = new AudioLoader().load("cached_buy.mp3");
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
