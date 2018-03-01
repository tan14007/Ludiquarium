package gui;

import java.util.ArrayList;
import java.util.Optional;

import entity.Clickable;
import entity.Entity;
import entity.Tank;
import entity.alien.Alien;
import entity.alien.BasicAlien;
import entity.alien.Gus;
import entity.alien.LittleAlien;
import entity.alien.Whale;
import entity.fish.BeetleMuncher;
import entity.fish.Carnivore;
import entity.fish.Guppy;
import entity.fish.GuppyCruncher;
import entity.fish.SpecialFish;
import entity.item.BeetleMuncherSeller;
import entity.item.CarnivoreSeller;
import entity.item.Coin;
import entity.item.Food;
import entity.item.FoodLevelSeller;
import entity.item.GunSeller;
import entity.item.GuppyCruncherSeller;
import entity.item.GuppySeller;
import entity.item.MaxFoodSeller;
import entity.item.SellingItem;
import entity.item.SpecialFishSeller;
import entity.pet.Itchy;
import entity.pet.Niko;
import entity.pet.Pet;
import entity.pet.Seymour;
import entity.pet.Vert;
import entity.pet.Wadsworth;
import helper.AudioLoader;
import helper.ImageLoader;
import helper.NullAudioException;
import helper.NullPictureException;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Main;

public class TankView extends View {

	private static final int numCols = 9;
	private static final int numRows = 1;

	private Tank tank;
	private StackPane topMenu = new StackPane();
	private StackPane tankZone = new StackPane();
	private Text mainMenuText = new Text("Leave Tank");
	private VBox menu = new VBox();
	private Text moneyText;
	private AnimationTimer logic = null;
	private StackPane foodSeller;
	private StackPane maxFoodSeller;
	private StackPane gunSeller;

	public TankView(Tank newTank) {
		this.tank = newTank;

		GridPane table = new GridPane();
		foodSeller = getGrid(new FoodLevelSeller(tank));
		maxFoodSeller = getGrid(new MaxFoodSeller(tank));
		gunSeller = getGrid(new GunSeller(tank));
		moneyText = new Text(String.valueOf(tank.getMoney()));
		try {
			tankZone.getChildren().add(tank.getBG());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < numCols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPercentWidth(100.0 / numCols);
			table.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < numRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / numRows);
			table.getRowConstraints().add(rowConst);
		}

		tankZone.setMaxHeight(Main.SCREEN_HEIGHT);
		tankZone.setMaxWidth(Main.SCREEN_WIDTH);

		table.add(getGrid(new GuppySeller(tank)), 0, 0);
		table.add(maxFoodSeller, 1, 0);
		table.add(foodSeller, 2, 0);
		table.add(gunSeller, 7, 0);
		
		mainMenuText.setFont(Font.font ("Tahoma", 16));
		mainMenuText.setFill(Color.BLACK);
		menu.getChildren().add(mainMenuText);
		table.add(menu, 8, 0);
		mainMenuText.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Are you sure?");
				alert.setContentText("Do you really want to leave this tank now? All the process will be lost.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
				
				logic.stop();
				MainMenu.setBGM("main.mp3");
				for (Entity e : tank.getEntity()) {
					e.stopLogic();
				}
				for (Entity e : tank.getItem()) {
					e.stopLogic();
				}
				Main.setScene(MainMenu.getTankSelect().getScene());
				}
			}
		});

		// Level Specification
		if (this.tank.getId() == 1) {
			table.add(getGrid(new SpecialFishSeller(tank, 1)), 6, 0);

		} else if (this.tank.getId() == 2) {
			table.add(getGrid(new CarnivoreSeller(tank)), 3, 0);
			table.add(getGrid(new GuppyCruncherSeller(tank)), 4, 0);
			table.add(getGrid(new SpecialFishSeller(tank, 2)), 6, 0);
		} else if (this.tank.getId() == 3) {
			table.add(getGrid(new CarnivoreSeller(tank)), 3, 0);
			table.add(getGrid(new GuppyCruncherSeller(tank)), 4, 0);
			table.add(getGrid(new BeetleMuncherSeller(tank)), 5, 0);
			table.add(getGrid(new SpecialFishSeller(tank, 3)), 6, 0);
		} else {
			table.add(getGrid(new GuppyCruncherSeller(tank)), 4, 0);
			table.add(getGrid(new BeetleMuncherSeller(tank)), 5, 0);
			table.add(getGrid(new SpecialFishSeller(tank, 4)), 6, 0);
		}

		// Render Entities
		logic = new AnimationTimer() {
			long last;

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (tank.getId() == 1) {
					if (tick == 90) {
						BasicAlien basic = new BasicAlien(tank);
						tank.addEntity(basic);
						try {
							MediaPlayer sound = new AudioLoader().load("ROAR3.mp3");
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
						tick = 0;

					}

					else if (lastDurationSec != durationSec) {
						lastDurationSec = durationSec;
						tick++;
						System.out.println(tick);
					}

				}
				if (tank.getId() == 2) {
					if (tick == 90) {
						Gus gus = new Gus(tank);
						tank.addEntity(gus);
						try {
							MediaPlayer sound = new AudioLoader().load("cached_guffaw.mp3");
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
						tick = 0;

					}

					else if (lastDurationSec != durationSec) {
						lastDurationSec = durationSec;
						tick++;
						System.out.println(tick);

					}

				}
				if (tank.getId() == 3) {
					boolean rock = true;
					if (tick == 100) {
						if (!rock) {
							Gus gus = new Gus(tank);
							tank.addEntity(gus);
							try {
								MediaPlayer sound = new AudioLoader().load("cached_guffaw.mp3");
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
							tick = 0;
							rock = false;
						} else {
							Gus gus = new Gus(tank);
							BasicAlien basic = new BasicAlien(tank);
							tank.addEntity(basic);
							tank.addEntity(gus);
							try {
								MediaPlayer sound = new AudioLoader().load("ROAR3.mp3");
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
							try {
								MediaPlayer sound = new AudioLoader().load("cached_guffaw.mp3");
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
							tick = 0;
							rock = false;
						}
					}

					else if (lastDurationSec != durationSec) {
						lastDurationSec = durationSec;
						tick ++;
						System.out.println(tick);

					}

				}
				if (tank.getId() == 4) {
					boolean rock = true;
					if (tick == 100) {
						if (!rock) {
							Gus gus = new Gus(tank);
							tank.addEntity(gus);
							try {
								MediaPlayer sound = new AudioLoader().load("cached_guffaw.mp3");
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
							tick = 0;
							rock = false;
						} else {
							BasicAlien basic1 = new BasicAlien(tank);
							BasicAlien basic2 = new BasicAlien(tank);
							try {
								MediaPlayer sound = new AudioLoader().load("ROAR3.mp3");
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
							tank.addEntity(basic1);
							tank.addEntity(basic2);
							tick = 0;
							rock = false;
						}

					} else if (lastDurationSec != durationSec) {
						lastDurationSec = durationSec;
						tick++;
						boss++;
						System.out.println(tick);


					}
					if (boss == 270) {
						Whale whale = new Whale(tank);
						tank.addEntity(whale);
						try {
							MediaPlayer sound = new AudioLoader().load("EVILLAFF.mp3");
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
						boss = 0;
					}

				}
				if (now - last > (1e9) / Main.FPS) {
					// Refresh Sprites
					last = now;
					if (tankZone.getChildren().size() > 2)
						tankZone.getChildren().remove(2);
					if (tankZone.getChildren().size() > 1)
						tankZone.getChildren().remove(1);
					menu.getChildren().remove(moneyText);
					moneyText = new Text("$ "+String.valueOf(tank.getMoney()));
					moneyText.setFont(Font.font ("Tahoma", 16));
					moneyText.setFill(Color.BROWN);
					menu.getChildren().add(moneyText);
					StackPane entityLayer = new StackPane();
					if (tank.getFoodLevel() == 2) {
						table.getChildren().remove(foodSeller);
					}
					if (tank.getMaxFood() == 9) {
						table.getChildren().remove(maxFoodSeller);
					}
					if (tank.getGunLevel() == 9) {
						table.getChildren().remove(gunSeller);
					}

					// Render Entity
					for (Entity e : tank.getEntity()) {
						StackPane spritePane = new StackPane();
						spritePane.setPadding(new Insets(e.getY(), 0, 0, e.getX()));
						spritePane.getChildren().add(e.getSprite());
						entityLayer.getChildren().add(spritePane);
						if (e instanceof Clickable) {
							e.getSprite().setOnMouseEntered(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									// TODO Auto-generated method stub
									e.getSprite().setEffect(new Glow(1.0));
								}
							});
							e.getSprite().setOnMouseExited(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									// TODO Auto-generated method stub
									e.getSprite().setEffect(new Glow(0.0));
								}
							});
						}
						StackPane.setAlignment(e.getSprite(), Pos.CENTER);
						StackPane.setAlignment(entityLayer, Pos.CENTER);
					}

					// Render Clickable Items

					for (Entity e : tank.getItem()) {
						StackPane spritePane = new StackPane();
						spritePane.setPadding(new Insets(e.getY(), 0, 0, e.getX()));
						spritePane.getChildren().add(new Pane(e.getSprite()));
						entityLayer.getChildren().add(spritePane);
						if (e instanceof Clickable) {
							e.getSprite().setOnMouseEntered(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									// TODO Auto-generated method stub
									e.getSprite().setEffect(new Glow(1.0));
								}
							});
							e.getSprite().setOnMouseExited(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									// TODO Auto-generated method stub
									e.getSprite().setEffect(new Glow(0.0));
								}
							});
							e.getSprite().setOnMouseClicked(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									// TODO Auto-generated method stub
									((Clickable) e).clickAction(event);
								}
							});
						}
						StackPane.setAlignment(e.getSprite(), Pos.CENTER);
						StackPane.setAlignment(entityLayer, Pos.CENTER);
					}

					entityLayer.setPadding(new Insets(0));
					entityLayer.setEffect(new DropShadow(2, Color.BLACK));
					tankZone.getChildren().add(entityLayer);
					System.gc();
				}
			}
		};

		logic.start();

		topMenu.getChildren().add(table);

		Pane root = new Pane();
		topMenu.setPadding(new Insets(0));
		topMenu.setMaxHeight(75);
		topMenu.setEffect(new DropShadow(2, Color.BLACK));
		root.getChildren().add(tankZone);
		root.getChildren().add(topMenu);

		// Give Food to the tank & Check ItemClick & Check Alien Click
		tankZone.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				ArrayList<Entity> toProcess = new ArrayList<Entity>();
				for (Entity e : tank.getItem()) {
					if (event.getSceneX() <= e.getX() + 80 && event.getSceneY() <= e.getY() + 80
							&& event.getSceneX() >= e.getX() && event.getSceneY() >= e.getY() && e instanceof Clickable)
						toProcess.add(e);
				}
				for (Entity e : tank.getEntity()) {
					if (event.getSceneX() <= e.getX() + 80 && event.getSceneY() <= e.getY() + 80
							&& event.getSceneX() >= e.getX() && event.getSceneY() >= e.getY() && e instanceof Clickable
							&& !(e instanceof Gus))
						toProcess.add(e);
				}
				for (Entity e : toProcess) {
					((Clickable) e).clickAction(event);
				}
				if (toProcess.size() == 0 && tank.getCurrentFood() < tank.getMaxFood() && event.getSceneY() >= 100) {
					tank.addMoney(-5);
					tank.addEntity(new Food((int) event.getSceneX(), (int) event.getSceneY(), tank));
					try {
						MediaPlayer sound = new AudioLoader().load("DROPFOOD.mp3");
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

		});

		// Initial some variable
		while (tank.getCurrentFood() > 0)
			tank.decreaseCurrentFood();
		tank.addMoney(200 - tank.getMoney());
		scene = new Scene(root);
		for (Entity e : tank.getEntity()) {
			if (e instanceof Pet) {
				e.startLogic();
			}
		}
		MainMenu.setBGM("tank" + tank.getId() + ".mp3");
	}

	public StackPane getGrid(SellingItem s) {
		StackPane ret = new StackPane();
		Canvas canvas = new Canvas(80, 80);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		try {
			gc.drawImage(new ImageLoader().load("SellingBG.png").getImage(), 0, 0);
		} catch (NullPictureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ret.getChildren().add(canvas);
		ret.getChildren().add(s.getSprite());
		DropShadow ds = new DropShadow();

		Text text = new Text(s.getPrice() == 0 ? "" : "$ " + String.valueOf(s.getPrice()));
		ds.setColor(Color.WHEAT);
		text.setFont(Font.font(null, FontWeight.BOLD, 18));
		text.setEffect(ds);
		text.setFill(Color.RED);
		ret.getChildren().add(text);
		ret.setAlignment(Pos.BOTTOM_CENTER);
		return ret;
	}
}
