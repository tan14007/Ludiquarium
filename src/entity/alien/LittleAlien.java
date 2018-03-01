package entity.alien;

import entity.Clickable;
import entity.Entity;
import entity.Killable;
import entity.Movable;
import entity.Tank;
import entity.fish.Fish;
import javafx.animation.Animation;

public class LittleAlien extends BasicAlien implements Clickable, Killable, Movable {

	private Whale whale = null;
	
	public LittleAlien(Tank tank, Whale whale) {
		super(tank);
		super.maxFullness = 30;
		this.whale = whale;
		super.hp = 250;
		this.setSprite("smallsylv.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}
	
	public LittleAlien(int x, int y, Tank tank, Whale whale) {
		super(tank);
		setX(x);
		setY(y);
		super.maxFullness = 30;
		this.whale = whale;
		super.hp = 250;
		this.setSprite("smallsylv.gif", 10, 2);
		sprite.playAnimation(Animation.INDEFINITE, 0, 0, 0, 10);
	}
	
	@Override
	public void died() {
		tank.getEntity().remove(this);
		whale.removeAlien(this);
		logic.stop();
	}
}
