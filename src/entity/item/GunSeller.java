package entity.item;

import entity.Tank;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class GunSeller extends SellingItem {

	public GunSeller(Tank tank) {
		super(tank);
		// TODO Auto-generated constructor stub
		this.price = 1500;
		sprite = new AnimatedObject("A_Gun.png", 1, 1);
		sprite.playAnimation(1, 0, 0, 0, 0);
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
		if (tank.getMoney() >= price && tank.getGunLevel() < 9) {
			tank.increaseGunLevel();
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
