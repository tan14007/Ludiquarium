package gui;

import helper.ImageLoader;
import helper.NullPictureException;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.Main;

public class EndingScene {
	private static boolean isLose ;
	private static Scene scene = null;
	
	
	public static Scene getScene(int n) {
		isLose = (n==1);
		BorderPane root = new BorderPane();
		//Text text = new Text("Congratuation on " + id + " tank!!!");
		//root.setCenter(text);
		StackPane pic = null;
		if(!isLose) {
		try {
			pic = new StackPane(new ImageLoader().load("ENDING.gif"));
		} catch (NullPictureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pic.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainMenu.setBGM("main.mp3");
				Main.setScene(MainMenu.getTankSelect().getScene());
			}
		});}
		else {
			try {
				pic = new StackPane(new ImageLoader().load("Lose.gif"));
			} catch (NullPictureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pic.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					MainMenu.setBGM("main.mp3");
					Main.setScene(MainMenu.getTankSelect().getScene());
				}
			});}
		root.setCenter(pic);
		scene = new Scene(root);
		return scene;
	}
}
