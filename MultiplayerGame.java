package javaFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MultiplayerGame extends Application {
	
	Pane root = new Pane();
	
	private Player player1 = new Player("Dan");
	private Player player2 = new Player("Irene");
	
	private AnimationTimer up;
	private AnimationTimer down;
	private AnimationTimer right;
	private AnimationTimer left;
	
	private AnimationTimer up2;
	private AnimationTimer down2;
	private AnimationTimer right2;
	private AnimationTimer left2;
	
	private int translate = 4;
	
	//Bullet
	private AnimationTimer bulletMotion;
	private int bulletSpeed = 9;
	
	public Parent createContent() {
		root.setPrefSize(800, 600);
		
		Rectangle background = new Rectangle(800, 600);
	
		player1.player.setFill(Color.DODGERBLUE);
		player1.setTranslateY(300 - player1.getHeight() / 2);
		player1.setTranslateX(50);
		player1.blaster.setTranslateX(player1.player.getTranslateX() + 32);
		
		player2.player.setFill(Color.RED);
		player2.setTranslateY(300 - player2.getHeight() / 2);
		player2.setTranslateX(710);
		player2.blaster.setTranslateX(player2.player.getTranslateX() - 33);
		
		Line line = new Line();
		line.setStartX(400);
		line.setStartY(0);
		line.setEndX(400);
		line.setEndY(600);
		line.setStrokeWidth(5);
		line.setStroke(Color.WHITE);
		
		root.getChildren().addAll(background, player1, player2, line);
		
		up = new AnimationTimer() {
			public void handle(long now) {
				if (player1.getTranslateY() <= 0) {
					up.stop();
				}
				else {
					player1.setTranslateY(player1.getTranslateY() - translate);
				}
			}
		};
		
		down = new AnimationTimer() {
			public void handle(long now) {
				if (player1.getTranslateY() >= 560) {
					down.stop();
				}
				else {
					player1.setTranslateY(player1.getTranslateY() + translate);
				}
			}
		};
		
		right = new AnimationTimer() {
			public void handle(long now) {
				if (player1.getTranslateX() >= 332) {
					right.stop();
				}
				else {
					player1.setTranslateX(player1.getTranslateX() + translate);
				}
			}
		};
		
		left = new AnimationTimer() {
			public void handle(long now) {
				if (player1.getTranslateX() <= 0) {
					left.stop();
				}
				else {
					player1.setTranslateX(player1.getTranslateX() - translate);
				}
			}
		};
		
		up2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateY() <= 0) {
					up2.stop();
				}
				else {
					player2.setTranslateY(player2.getTranslateY() - translate);
				}
			}
		};
		
		down2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateY() >= 560) {
					down2.stop();
				}
				else {
					player2.setTranslateY(player2.getTranslateY() + translate);
				}			
			}
		};
		
		right2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateX() >= 760) {
					right2.stop();
				}
				else {
					player2.setTranslateX(player2.getTranslateX() + translate);
				}			
			}
		};
		
		left2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateX() <= 427) {
					left2.stop();
				}
				else {
					player2.setTranslateX(player2.getTranslateX() - translate);
				}
			}
		};
		
		return root;
	}
	
	public void bullet() {
		Circle circle = new Circle();
		circle.setRadius(5);
		circle.setCenterX(player1.getTranslateX() + 65);
		circle.setCenterY(player1.getTranslateY() + 20);
		circle.setFill(Color.DODGERBLUE);
		
		root.getChildren().add(circle);
		
		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				circle.setTranslateX(circle.getTranslateX() + bulletSpeed);
			}
		};
		
		bulletMotion.start();
		
		if (circle.getTranslateX() > 800) {
			root.getChildren().remove(circle);
		}
	}
	
	public void bullet2() {
		Circle circle = new Circle();
		circle.setRadius(5);
		circle.setCenterX(player2.getTranslateX() - 25);
		circle.setCenterY(player2.getTranslateY() + 20);
		circle.setFill(Color.RED);
		
		root.getChildren().add(circle);
		
		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				circle.setTranslateX(circle.getTranslateX() - bulletSpeed);
			}
		};
		
		bulletMotion.start();
		
		if (circle.getTranslateX() > 800) {
			bulletMotion.stop();
		}
	}
	
	private class Player extends StackPane {
		Rectangle player = new Rectangle(40, 40);
		Rectangle blaster = new Rectangle(25, 20);
		public Player(String name) {
			Text text = new Text(name);
			text.setFill(Color.WHITE);
			
			blaster.setFill(Color.GREY);
			blaster.setTranslateY(player.getLayoutY());
			
			getChildren().addAll(player, text, blaster);
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.setTitle("Multiplayer game");
		
		primaryStage.getScene().setOnKeyPressed(event -> {
			//Player 1
			if (event.getCode().equals(KeyCode.W)) {
				up.start();
			}
			if (event.getCode().equals(KeyCode.S)) {
				down.start();
			}
			if (event.getCode().equals(KeyCode.D)) {
				right.start();
			}
			if (event.getCode().equals(KeyCode.A)) {
				left.start();
			}
			
			//Player 2
			if (event.getCode().equals(KeyCode.UP)) {
				up2.start();
			}
			if (event.getCode().equals(KeyCode.DOWN)) {
				down2.start();
			}
			if (event.getCode().equals(KeyCode.RIGHT)) {
				right2.start();
			}
			if (event.getCode().equals(KeyCode.LEFT)) {
				left2.start();
			}
			
			//Bullet for player1
			if (event.getCode().equals(KeyCode.SPACE)) {
				bullet();
			}
			
			//Bullet for player2
			if (event.getCode().equals(KeyCode.ENTER)) {
				bullet2();
			}
		});
		
		primaryStage.getScene().setOnKeyReleased(event -> {
			//Player 1
			if (event.getCode().equals(KeyCode.W)) {
				up.stop();
			}
			if (event.getCode().equals(KeyCode.S)) {
				down.stop();
			}
			if (event.getCode().equals(KeyCode.D)) {
				right.stop();
			}
			if (event.getCode().equals(KeyCode.A)) {
				left.stop();
			}
			
			//Player 2
			if (event.getCode().equals(KeyCode.UP)) {
				up2.stop();
			}
			if (event.getCode().equals(KeyCode.DOWN)) {
				down2.stop();
			}
			if (event.getCode().equals(KeyCode.RIGHT)) {
				right2.stop();
			}
			if (event.getCode().equals(KeyCode.LEFT)) {
				left2.stop();
			}
		});
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
