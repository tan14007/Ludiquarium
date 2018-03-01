package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gui.MainMenu;
import helper.SpriteLoader;

public class Main extends Application {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	public static final int FPS = 60;
	private static Stage primaryStage;
	public static SpriteLoader sprite = new SpriteLoader();
	private MainMenu menu;

	@Override
	public void start(Stage primaryStage){
		try {
			Main.primaryStage = primaryStage;
			menu = new MainMenu();
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setFullScreen(false);
			Main.primaryStage.setHeight(SCREEN_HEIGHT);
			Main.primaryStage.setWidth(SCREEN_WIDTH);
			Main.primaryStage.setTitle("Ludiquarium");
			Main.primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() {
		System.exit(0);
	}
	
	public static void setScene(Scene scene) {
		primaryStage.setScene(scene);
	}
}
