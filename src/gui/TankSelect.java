package gui;

import helper.AudioLoader;
import helper.ImageLoader;
import helper.NullAudioException;
import helper.NullPictureException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import main.Main;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

import entity.Tank;

public class TankSelect extends View {
	private static final int numCols = 4;
	private static final int numRows = 4;
	private static final int THUMB_INDEX = 0;
	private static final int TEXT_INDEX = 1;

	private static ArrayList<StackPane> levelButton = new ArrayList<StackPane>();
	private static ImageView clicked;
	private static ImageView bg;
	public TankSelect() {

		// TODO Construct TankScreen for each level (1 to 4)
		try {
			// Load Required Resources
			if(levelButton.size() == 0) {
			levelButton.add(new StackPane(new ImageLoader().load("level1.png")));
			levelButton.add(new StackPane(new ImageLoader().load("level2.png")));
			levelButton.add(new StackPane(new ImageLoader().load("level3.png")));
			levelButton.add(new StackPane(new ImageLoader().load("level4.png")));

			bg = new ImageLoader().load("tankSelectBG.png");

			for (int i = 0; i < levelButton.size(); i++) {
				StackPane sp = levelButton.get(i);

				sp.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						sp.getChildren().get(THUMB_INDEX).setEffect(new Glow(0.2));
					}
				});

				sp.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						if (clicked != sp.getChildren().get(THUMB_INDEX))
							sp.getChildren().get(THUMB_INDEX).setEffect(new Glow(0.0));
					}
				});

				sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						if (event.getButton() == MouseButton.PRIMARY) {
							if (clicked != sp.getChildren().get(THUMB_INDEX)) {
								if (clicked != null)
									clicked.setEffect(new Glow(0.0));
								clicked = (ImageView) sp.getChildren().get(THUMB_INDEX);
								sp.getChildren().get(THUMB_INDEX).setEffect(new Glow(0.8));
							} else {
								Tank tank = new Tank(levelButton.indexOf(sp) + 1);
								PetSelect pet;
								try {
									pet = new PetSelect(tank);
									Main.setScene(pet.getScene());
								} catch (NullPictureException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
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
			}
			
			/*VBox backPane = new VBox();
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
					try {
						Main.setScene(new MainMenu().getScene());
					} catch (NullPictureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}});
			
			backPane.getChildren().add(back);
			backPane.setTranslateX(20);
			backPane.setTranslateY(500);
*/
			StackPane root = new StackPane();
			GridPane table = new GridPane();

			table.setPadding(new Insets(10, 30, 30, 30));
			table.setPrefWidth(Main.SCREEN_WIDTH);
			table.setPrefHeight(Main.SCREEN_HEIGHT);
			table.setAlignment(Pos.BASELINE_CENTER);
			table.setVgap(5);
			table.setHgap(5);

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

			for (int i = 0; i < levelButton.size(); i++) {
				table.add(levelButton.get(i), i % 4, 1);
			}

			root.getChildren().add(bg);
			root.getChildren().add(table);

			scene = new Scene(root);
		}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
