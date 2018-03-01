package gui;

import java.util.Optional;

import helper.AudioLoader;
import helper.ImageLoader;
import helper.NullAudioException;
import helper.NullPictureException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import main.Main;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class MainMenu extends View {

	private static final int numCols = 5;
	private static final int numRows = 5;

	private static ImageView background;
	private static ImageView startButton;
	private static ImageView exitButton;
	private static TankSelect tankSelect;

	private static MediaPlayer bgm;

	public MainMenu(){
		try {
			new Thread() {
				@Override
				public void run() {
					try {
						background = new ImageLoader().load("mainMenuBG.gif");
						startButton = new ImageLoader().load("play.png");
						exitButton = new ImageLoader().load("exit.png");
					} catch (Exception e) {
						if (e instanceof NullPictureException)
							System.out.println(e.getMessage());
						else
							e.printStackTrace();
					}
				};
			}.run();

			setBGM("main.mp3");

			// Set Background setting
			background.setPreserveRatio(true);
			background.setSmooth(true);
			background.setCache(true);

			// Set Play button setting
			startButton.setFitWidth(100);
			startButton.setPreserveRatio(true);
			startButton.setSmooth(true);
			startButton.setCache(true);
			startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					if (event.getButton() == MouseButton.PRIMARY) {
						tankSelect = new TankSelect();
						Main.setScene(tankSelect.getScene());
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

			// Set Exit button setting
			exitButton.setFitWidth(100);
			exitButton.setPreserveRatio(true);
			exitButton.setSmooth(true);
			exitButton.setCache(true);
			exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					try {
						MediaPlayer sound = new AudioLoader().load("baby.mp3");
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
					if (event.getButton() == MouseButton.PRIMARY) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Exit");
						alert.setContentText("Do you really want to exit now?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							System.exit(0);
						}
					}

				}

			});
			StackPane root = new StackPane();
			GridPane menu = new GridPane();

			for (int i = 0; i < numCols; i++) {
				ColumnConstraints colConst = new ColumnConstraints();
				colConst.setPercentWidth(100.0 / numCols);
				menu.getColumnConstraints().add(colConst);
			}
			for (int i = 0; i < numRows; i++) {
				RowConstraints rowConst = new RowConstraints();
				rowConst.setPercentHeight(100.0 / numRows);
				menu.getRowConstraints().add(rowConst);
			}

			menu.setPadding(new Insets(10));
			menu.add(startButton, 4, 2);
			menu.add(exitButton, 4, 3);

			root.getChildren().add(background);
			root.getChildren().add(menu);
			scene = new Scene(root);

			Main.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setBGM(String fileName) {
		if (bgm != null)
			bgm.stop();
		try {
			bgm = new AudioLoader().load(fileName);
			bgm.setCycleCount(MediaPlayer.INDEFINITE);
			bgm.play();
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	public static TankSelect getTankSelect() {
		return tankSelect;
	}
}
