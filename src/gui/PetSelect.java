package gui;

import helper.AudioLoader;
import helper.ImageLoader;
import helper.NullAudioException;
import helper.NullPictureException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import main.Main;
import java.util.ArrayList;

import entity.Tank;
import entity.pet.Itchy;
import entity.pet.Niko;
import entity.pet.Pet;
import entity.pet.Seymour;
import entity.pet.Vert;
import entity.pet.Wadsworth;

public class PetSelect extends View {

	private String bgFile;
	private TilePane tilePane;
	private BorderPane root;
	private Canvas background;
	private Canvas go;
	private StackPane goPane;
	private Tank tank;
	private ArrayList<Pet> selectedPets;
	private FlowPane bottomPane;

	public PetSelect(Tank newTank) throws NullPictureException {
		root = new BorderPane();
		tank = newTank;
		root.setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		tilePane = new TilePane();
		tilePane.setPrefColumns(3);
		tilePane.setPrefRows(2);
		selectedPets = new ArrayList<Pet>();
		bottomPane = new FlowPane();
		bottomPane.setPrefSize(800, 150);

		tilePane.setPrefHeight(300);
		tilePane.setPrefWidth(450);
		tilePane.setMinHeight(300);
		tilePane.setMinWidth(450);
		tilePane.setMaxSize(450, 300);
		tilePane.setAlignment(Pos.CENTER);
		bgFile = "petSelectBG.png";

		Canvas niko = new Canvas(150, 150);
		GraphicsContext nikoGC = niko.getGraphicsContext2D();
		nikoGC.drawImage(new ImageLoader().load("NikoSelect.png").getImage(), 0, 0);
		Niko newNiko = new Niko(70, 450, tank);
		niko.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!selectedPets.contains(newNiko)) {
					if (selectedPets.size() < 3) {
						try {
							nikoGC.clearRect(0, 0, 150, 150);
							nikoGC.drawImage(new ImageLoader().load("NikoSelected.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						selectedPets.add(newNiko);
					}
				} else {
					nikoGC.clearRect(0, 0, 150, 150);
					try {
						nikoGC.drawImage(new ImageLoader().load("NikoSelect.png").getImage(), 0, 0);
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectedPets.remove(newNiko);
				}
				try {
					MediaPlayer sound = new AudioLoader().load("cached_punch.mp3");
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
		});

		tilePane.getChildren().add(niko);

		Canvas vert = new Canvas(150, 150);
		GraphicsContext vertGC = vert.getGraphicsContext2D();
		vertGC.drawImage(new ImageLoader().load("VertSelect.png").getImage(), 0, 0);
		Vert newVert = new Vert(tank);
		vert.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!selectedPets.contains(newVert)) {
					if (selectedPets.size() < 3) {
						try {
							vertGC.clearRect(0, 0, 150, 150);
							vertGC.drawImage(new ImageLoader().load("VertSelected.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						selectedPets.add(newVert);
					}
				} else {
					vertGC.clearRect(0, 0, 150, 150);
					try {
						vertGC.drawImage(new ImageLoader().load("VertSelect.png").getImage(), 0, 0);
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectedPets.remove(newVert);
				}
				try {
					MediaPlayer sound = new AudioLoader().load("cached_punch.mp3");
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
		});

		tilePane.getChildren().add(vert);

		Canvas itchy = new Canvas(150, 150);
		GraphicsContext itchyGC = itchy.getGraphicsContext2D();
		itchyGC.drawImage(new ImageLoader().load("ItchySelect.png").getImage(), 0, 0);
		Itchy newItchy = new Itchy(tank);
		itchy.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!selectedPets.contains(newItchy)) {
					if (selectedPets.size() < 3) {
						try {
							itchyGC.clearRect(0, 0, 150, 150);
							itchyGC.drawImage(new ImageLoader().load("ItchySelected.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						selectedPets.add(newItchy);
					}
				} else {
					itchyGC.clearRect(0, 0, 150, 150);
					try {
						itchyGC.drawImage(new ImageLoader().load("ItchySelect.png").getImage(), 0, 0);
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectedPets.remove(newItchy);
				}
				try {
					MediaPlayer sound = new AudioLoader().load("cached_punch.mp3");
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
		});

		tilePane.getChildren().add(itchy);

		Canvas seymour = new Canvas(150, 150);
		GraphicsContext seymourGC = seymour.getGraphicsContext2D();
		seymourGC.drawImage(new ImageLoader().load("SeymourSelect.png").getImage(), 0, 0);
		Seymour newSeymour = new Seymour(tank);
		seymour.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!selectedPets.contains(newSeymour)) {
					if (selectedPets.size() < 3) {
						try {
							seymourGC.clearRect(0, 0, 150, 150);
							seymourGC.drawImage(new ImageLoader().load("SeymourSelected.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						selectedPets.add(newSeymour);
					}
				} else {
					seymourGC.clearRect(0, 0, 150, 150);
					try {
						seymourGC.drawImage(new ImageLoader().load("SeymourSelect.png").getImage(), 0, 0);
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectedPets.remove(newSeymour);
				}
				try {
					MediaPlayer sound = new AudioLoader().load("cached_punch.mp3");
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
		});

		tilePane.getChildren().add(seymour);

		Canvas wadsworth = new Canvas(150, 150);
		GraphicsContext wadsworthGC = wadsworth.getGraphicsContext2D();
		wadsworthGC.drawImage(new ImageLoader().load("WadsworthSelect.png").getImage(), 0, 0);
		Wadsworth newWadsworth = new Wadsworth(tank);
		wadsworth.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (!selectedPets.contains(newWadsworth)) {
					if (selectedPets.size() < 3) {
						try {
							wadsworthGC.clearRect(0, 0, 150, 150);
							wadsworthGC.drawImage(new ImageLoader().load("WadsworthSelected.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						selectedPets.add(newWadsworth);
					}
				} else {
					wadsworthGC.clearRect(0, 0, 150, 150);
					try {
						wadsworthGC.drawImage(new ImageLoader().load("WadsworthSelect.png").getImage(), 0, 0);
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					selectedPets.remove(newWadsworth);
				}
				try {
					MediaPlayer sound = new AudioLoader().load("cached_punch.mp3");
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
		});

		tilePane.getChildren().add(wadsworth);

		background = new Canvas(800, 600);
		GraphicsContext backGroundGC = background.getGraphicsContext2D();
		backGroundGC.drawImage(new ImageLoader().load(bgFile).getImage(), 0, 0);

		go = new Canvas(100, 50);
		GraphicsContext goGC = go.getGraphicsContext2D();
		goGC.drawImage(new ImageLoader().load("go.png").getImage(), 0, 0);
		go.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					goGC.clearRect(0, 0, 100, 50);
					goGC.drawImage(new ImageLoader().load("goEx.png").getImage(), 0, 0);
				} catch (NullPictureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		go.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					goGC.clearRect(0, 0, 100, 50);
					goGC.drawImage(new ImageLoader().load("go.png").getImage(), 0, 0);
				} catch (NullPictureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		go.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for (Pet pet : selectedPets) {
					tank.addEntity(pet);
					pet.startLogic();
				}
				TankView tankView = new TankView(tank);
				Main.setScene(tankView.getScene());
				try {
					MediaPlayer sound = new AudioLoader().load("TREASURE.mp3");
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

		});

		goPane = new StackPane();
		goPane.setPrefSize(100, 50);
		goPane.setMinSize(100, 50);
		goPane.setMaxSize(100, 50);
		goPane.getChildren().add(go);
		//goPane.setTranslateX(Main.SCREEN_WIDTH - goPane.getWidth() - 140);
		//goPane.setTranslateY(-30);
		
		StackPane backPane = new StackPane();
		backPane.setPrefSize(150, 50);
		Canvas back = new Canvas(150,50);
		GraphicsContext gc = back.getGraphicsContext2D();
		gc.drawImage(new ImageLoader().load("Back.png").getImage(), 0, 0);
		back.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

						try {
							gc.clearRect(0, 0, 150, 50);
							gc.drawImage(new ImageLoader().load("BackEx.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


			}});
		
		back.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

						try {
							gc.clearRect(0, 0, 150, 50);
							gc.drawImage(new ImageLoader().load("Back.png").getImage(), 0, 0);
						} catch (NullPictureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


			}});
		
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				try {
					MediaPlayer sound = new AudioLoader().load("TREASURE.mp3");
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
				Main.setScene(new TankSelect().getScene());


			}});
		
		backPane.getChildren().add(back);
		//backPane.setAlignment(Pos.CENTER);
		
		//backPane.setTranslateX(-200);
		//goPane.setTranslateX(-300);
		//backPane.setAlignment(Pos.BOTTOM_LEFT);
		//goPane.setAlignment(Pos.CENTER_RIGHT);
		goPane.setTranslateX(490);
		backPane.setTranslateX(50);
		
		bottomPane.getChildren().add(backPane);
		bottomPane.getChildren().add(goPane);
		bottomPane.setTranslateY(80);

		tilePane.setTranslateY(80);
		//goPane.setAlignment(Pos.CENTER);
		root.getChildren().add(background);
		root.setCenter(tilePane);
		//root.setLeft(backPane);
		//root.setRight(goPane);
		root.setBottom(bottomPane);

		scene = new Scene(root);
	}
}
