package shooterGame;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
	
	Pane root = new Pane();
	
	private Player player1 = new Player("Dan");
	private Player player2 = new Player("Irene");
	
	private HealthBar healthBar1 = new HealthBar(Color.LIME);
	private HealthBar healthBar2 = new HealthBar(Color.LIME);
	
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
	
	AnimationTimer winAnimation;
	
	private boolean playable = true;
	
	public Parent createContent() {
		root.setPrefSize(800, 600);
		
		Rectangle background = new Rectangle(800, 600);
	
		player1.player.setFill(Color.DODGERBLUE);
		player1.setTranslateY(300 - player1.getHeight() / 2);
		player1.setTranslateX(50);
		player1.blaster.setTranslateX(player1.player.getTranslateX() + 27);
		
		player2.player.setFill(Color.RED);
		player2.setTranslateY(300 - player2.getHeight() / 2);
		player2.setTranslateX(710);
		player2.blaster.setTranslateX(player2.player.getTranslateX() - 28);
		
		healthBar1.setTranslateX(100);
		healthBar1.setTranslateY(25);
		
		healthBar2.setTranslateX(500);
		healthBar2.setTranslateY(25);
		
		Text healthText1 = new Text("Health:");
		Text healthText2 = new Text("Health:");
		healthText1.setFill(Color.WHITE);
		healthText2.setFill(Color.WHITE);
		healthText1.setFont(Font.font(18));
		healthText2.setFont(Font.font(18));
		healthText2.setFill(Color.WHITE);
		healthText1.setX(30);
		healthText1.setY(43);
		healthText2.setX(430);
		healthText2.setY(43);
		
		Line line = new Line();
		line.setStartX(400);
		line.setStartY(0);
		line.setEndX(400);
		line.setEndY(600);
		line.setStrokeWidth(5);
		line.setStroke(Color.WHITE);
		
		root.getChildren().addAll(background, healthBar1, healthBar2, healthText1, healthText2, player1, player2, line);
		
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
				if (player1.getTranslateX() >= 342) {
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
				if (player2.getTranslateX() <= 418) {
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
		circle.setTranslateX(player1.getTranslateX() + 50);
		circle.setTranslateY(player1.getTranslateY() + 20);
		circle.setFill(Color.DODGERBLUE);
		
		root.getChildren().add(circle);

		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				circle.setTranslateX(circle.getTranslateX() + bulletSpeed);
				if (circle.getTranslateX() > player2.getTranslateX()
					&& circle.getTranslateX() < player2.getTranslateX() + 40
					&& circle.getTranslateY() > player2.getTranslateY() - 10
					&& circle.getTranslateY() < player2.getTranslateY() + 50
					&& playable) {
					healthBar2.health.setWidth(healthBar2.health.getWidth() - 8);
					healthBar2.health.setTranslateX(healthBar2.health.getTranslateX() - 4);
					root.getChildren().remove(circle);
					this.stop();
				}
				
				if (circle.getTranslateX() > 800) {
					root.getChildren().remove(circle);
				}
				
			}
		};
		
		bulletMotion.start();
		
		if (healthBar2.health.getWidth() < 1) {
			WinningText blueWin = new WinningText("Blue", Color.BLUE);
			root.getChildren().add(blueWin);
			playable = false;
		}
	}
	
	public void bullet2() {
		Circle circle = new Circle();
		circle.setRadius(5);
		circle.setTranslateX(player2.getTranslateX() - 10);
		circle.setTranslateY(player2.getTranslateY() + 20);
		circle.setFill(Color.RED);
		
		root.getChildren().add(circle);
		
		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				circle.setTranslateX(circle.getTranslateX() - bulletSpeed);
				if (circle.getTranslateX() < player1.getTranslateX() + 40
					&& circle.getTranslateX() > player1.getTranslateX()
					&& circle.getTranslateY() > player1.getTranslateY() - 10
					&& circle.getTranslateY() < player1.getTranslateY() + 50
					&& playable) {
					healthBar1.health.setWidth(healthBar1.health.getWidth() - 8);
					healthBar1.health.setTranslateX(healthBar1.health.getTranslateX() - 4);
					root.getChildren().remove(circle);
					this.stop();
				}
				
				if (circle.getTranslateX() < 0) {
					root.getChildren().remove(circle);
				}
				
			}
		};
		
		bulletMotion.start();
		
		if (healthBar1.health.getWidth() < 1) {
			WinningText redWin = new WinningText("Red", Color.RED);
			root.getChildren().add(redWin);
			playable = false;
		}
	}
	
	private class Player extends StackPane {
		Rectangle player = new Rectangle(40, 40);
		Rectangle blaster = new Rectangle(15, 20);
		public Player(String name) {
			Text text = new Text(name);
			text.setFill(Color.WHITE);
			
			blaster.setFill(Color.GREY);
			
			getChildren().addAll(player, text, blaster);
		}
	}
	
	private class HealthBar extends StackPane {
		Rectangle back = new Rectangle(200, 25);
		Rectangle health = new Rectangle(198, 23);
		public HealthBar(Color color) {
			back.setFill(Color.GREY);
			back.setStroke(Color.WHITE);
			back.setStrokeWidth(2);
			health.setFill(color);
			
			getChildren().addAll(back, health);
		}
	}
	
	private class WinningText extends StackPane{
		private double fontSize = 1;
		public WinningText(String winner, Color color) {
			setPrefSize(800, 600);
			Text text = new Text(winner + " Wins!");
			text.setFill(color);
			text.setStroke(Color.WHITE);
			text.setStrokeWidth(2);
			
			getChildren().add(text);
			
			winAnimation = new AnimationTimer() {
				public void handle(long now) {
					text.setFont(Font.font("NextGames", fontSize));
					fontSize += 1.5;
					if (fontSize > 75) {
						winAnimation.stop();
					}
				}
			};
			winAnimation.start();
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
			if (event.getCode().equals(KeyCode.SPACE) && playable) {
				bullet();
			}
			
			//Bullet for player2
			if (event.getCode().equals(KeyCode.ENTER) && playable) {
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
