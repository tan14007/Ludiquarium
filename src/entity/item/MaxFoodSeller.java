package entity.item;

import entity.Tank;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class MaxFoodSeller extends SellingItem {

	public MaxFoodSeller(Tank tank) {
		super(tank);
		// TODO Auto-generated constructor stub
		this.price = 300;
		sprite = new AnimatedObject("maxfood.png", 1, 1);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 1);
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
		if (tank.getMoney() >= price && tank.getMaxFood() < 9) {
			tank.increaseMaxFood();
			tank.addMoney(-price);
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
