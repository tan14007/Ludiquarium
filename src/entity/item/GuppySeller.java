package entity.item;

import entity.Tank;
import entity.fish.Guppy;
import helper.AnimatedObject;
import helper.AudioLoader;
import helper.NullAudioException;
import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class GuppySeller extends SellingItem {

	public GuppySeller(Tank tank) {
		super(tank);
		super.price = 100;
		try {
			sprite = new AnimatedObject("Smallswim.gif", 10, 5);
			sprite.playAnimation(Animation.INDEFINITE, 0, 0, 1, 0);
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
			Guppy guppy = new Guppy(tank);
			tank.addEntity(guppy);
			guppy.startLogic();
			tank.addMoney(-100);
			System.out.println(tank.getMoney());
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
