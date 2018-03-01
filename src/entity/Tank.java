package entity;

import java.util.ArrayList;

import org.hamcrest.core.IsNull;

import entity.fish.Guppy;
import helper.ImageLoader;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Tank {

	public static final int DIST_THRESH = 30 * 30;

	protected int id;
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	protected ArrayList<Entity> items = new ArrayList<Entity>();
	protected int money;
	protected ImageView bg;
	protected int currentFood;
	protected int foodLevel;
	protected int gunLevel;
	protected int maxFood;

	public Tank(int id) {
		this.money = 9999999;
		this.id = id;
		setBG("tank" + id + "BG.png");
		this.foodLevel = 0;
		this.currentFood = 0;
		this.maxFood = 1;
		addEntity(new Guppy(this));
		addEntity(new Guppy(this));
	}

	public void addEntity(Entity entity) {
		entities.add(entity);

		if (entity instanceof Clickable) {
			entity.getSprite().setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					((Clickable) entity).clickAction(event);
				}
			});
		}
	}

	public int getId() {
		return this.id;
	}

	public ArrayList<Entity> getItemByRadius(int x, int y, int r) {
		ArrayList<Entity> gottenItem = new ArrayList<Entity>();
		for (Entity i : entities) {
			if (((i.getX() - x) * (i.getX() - x) + (i.getY() - y) * (i.getY() - y)) <= r) {
				gottenItem.add(i);
			}
		}
		return gottenItem;
	}

	public int getGunLevel() {
		return this.gunLevel;
	}

	public void setBG(String fileName) {
		try {
			bg = new ImageLoader().load(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImageView getBG() {
		return this.bg;
	}

	public ArrayList<Entity> getEntity() {
		return this.entities;
	}

	public int getCurrentFood() {
		return this.currentFood;
	}

	public int getMaxFood() {
		return this.maxFood;
	}

	public void increaseCurrentFood() {
		this.currentFood += 1;
	}

	public void decreaseCurrentFood() {
		this.currentFood -= 1;
		if (this.currentFood < 0)
			this.currentFood = 0;
	}

	public ArrayList<Entity> getItem() {
		return this.items;
	}

	public void addMoney(int amount) {
		money += amount;
		if (money < 0)
			money = 0;
	}

	public int getMoney() {
		return this.money;
	}

	public void increaseFoodLevel() {
		if (this.foodLevel < 2)
			this.foodLevel += 1;
	}

	public int getFoodLevel() {
		return this.foodLevel;
	}

	public void increaseGunLevel() {
		if (this.gunLevel < 9)
			this.gunLevel += 1;
	}

	public void increaseMaxFood() {
		if (maxFood + 1 <= 9)
			this.maxFood += 1;
	}

}
