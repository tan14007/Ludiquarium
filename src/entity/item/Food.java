package entity.item;

import entity.Movable;
import entity.Tank;
import helper.AnimatedObject;
import javafx.animation.Animation;
import javafx.scene.input.MouseEvent;

public class Food extends TankItem implements Movable{

	private int foodLevel;

	public Food(int x, int y, Tank tank) {
		super(x, y, tank);
		if(tank.getCurrentFood() >= tank.getMaxFood())return;
		foodLevel = tank.getFoodLevel();
		tank.increaseCurrentFood();
		sprite = new AnimatedObject("food.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, foodLevel, 0, foodLevel, 10);
		startLogic();
	}

	public Food(Tank tank) {
		super(tank);
		if(tank.getCurrentFood() >= tank.getMaxFood())return;
		foodLevel = tank.getFoodLevel();
		tank.increaseCurrentFood();
		sprite = new AnimatedObject("food.gif", 10, 5);
		sprite.playAnimation(Animation.INDEFINITE, foodLevel, 0, foodLevel, 10);
		startLogic();
	}

	public int getFoodLevel() {
		return this.foodLevel;
	}
	
	@Override
	public void onGrounded() {
		if (isGrounded()) {
			tank.getEntity().remove(this);
			tank.decreaseCurrentFood();
		}
	}

}
