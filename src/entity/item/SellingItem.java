package entity.item;

import entity.Clickable;
import entity.Tank;
import helper.AnimatedObject;

public abstract class SellingItem implements Clickable {

	protected int price;
	protected Tank tank;
	protected AnimatedObject sprite;

	public SellingItem(Tank tank) {
		this.tank = tank;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
		
	public AnimatedObject getSprite() {
		return this.sprite;
	}


}
