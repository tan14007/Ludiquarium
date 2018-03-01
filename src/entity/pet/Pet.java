package entity.pet;

import entity.Entity;
import entity.Movable;
import entity.Tank;
import javafx.animation.AnimationTimer;
import main.Main;

public class Pet extends Entity implements Movable {

	public Pet(Tank tank) {
		super(tank);
		this.tank = tank;
	}

	public Pet(int x, int y, Tank tank) {
		super(x, y, tank);
		this.tank = tank;
	}

}
