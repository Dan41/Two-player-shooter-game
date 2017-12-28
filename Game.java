package shooterGame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
	
	Pane root = new Pane();
	
	private Player player1 = new Player();
	private Player player2 = new Player();
	
	private HealthBar healthBar1 = new HealthBar(Color.LIME);
	private HealthBar healthBar2 = new HealthBar(Color.LIME);
	
	private int healthDec = 6;
	
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
	
	ImageView pause;
	
	private boolean winStatus = false;
	
	private boolean playable = true;
	
	public Parent createContent() {
		root.setPrefSize(800, 600);
		
		Rectangle background = new Rectangle(800, 600);
	
		player1.setTranslateY(267.5);
		player1.setTranslateX(0);
		player1.plane.setRotate(90);
		
		player2.setTranslateY(267.5);
		player2.setTranslateX(735);
		player2.plane.setRotate(270);
		
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
		healthText1.setX(30);
		healthText1.setY(43);
		healthText2.setX(430);
		healthText2.setY(43);
		
		Line line = new Line(400, 0, 400, 600);
		line.setStrokeWidth(5);
		line.setStroke(Color.WHITE);
		
		Rectangle blueTeam = new Rectangle(400, 600);
		blueTeam.setFill(Color.BLUE);
		blueTeam.setTranslateX(0);
		blueTeam.setTranslateY(0);
		
		Rectangle redTeam = new Rectangle(400, 600);
		redTeam.setFill(Color.RED);
		redTeam.setTranslateX(400);
		redTeam.setTranslateY(0);
		
		root.getChildren().addAll(
				background, blueTeam, redTeam, healthBar1, healthBar2, healthText1, healthText2, player1, player2, line);
		
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
				if (player1.getTranslateY() >= 540) {
					down.stop();
				}
				else {
					player1.setTranslateY(player1.getTranslateY() + translate);
				}
			}
		};
		
		right = new AnimationTimer() {
			public void handle(long now) {
				if (player1.getTranslateX() >= 335) {
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
				if (player2.getTranslateY() >= 540) {
					down2.stop();
				}
				else {
					player2.setTranslateY(player2.getTranslateY() + translate);
				}			
			}
		};
		
		right2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateX() >= 735) {
					right2.stop();
				}
				else {
					player2.setTranslateX(player2.getTranslateX() + translate);
				}			
			}
		};
		
		left2 = new AnimationTimer() {
			public void handle(long now) {
				if (player2.getTranslateX() <= 400) {
					left2.stop();
				}
				else {
					player2.setTranslateX(player2.getTranslateX() - translate);
				}
			}
		};
		
		pause = new ImageView(new Image("file:///Users/Danpark/Downloads/pausebutton.png"));
		pause.setFitHeight(200);
		pause.setFitWidth(200);
		pause.setTranslateX(300);
		pause.setTranslateY(200);
		
		return root;
	}
	
	public void bullet() {
		Rectangle bul = new Rectangle(10, 5);
		bul.setTranslateX(player1.getTranslateX() + 55);
		bul.setTranslateY(player1.getTranslateY() + 27.5);
		bul.setFill(Color.WHITE);
		
		root.getChildren().add(bul);

		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				if (playable) {
					bul.setTranslateX(bul.getTranslateX() + bulletSpeed);
				}
				if (bul.getTranslateX() > player2.getTranslateX() + 13
					&& bul.getTranslateX() < player2.getTranslateX() + 40
					&& bul.getTranslateY() > player2.getTranslateY() - 10
					&& bul.getTranslateY() < player2.getTranslateY() + 65
					&& playable) {
					healthBar2.health.setWidth(healthBar2.health.getWidth() - healthDec);
					healthBar2.health.setTranslateX(healthBar2.health.getTranslateX() - healthDec / 2);
					root.getChildren().remove(bul);
					this.stop();
				}
				
				if (bul.getTranslateX() > 800) {
					root.getChildren().remove(bul);
				}
				
			}
		};
		
		bulletMotion.start();
		
		if (healthBar2.health.getWidth() < 1) {
			WinningText blueWin = new WinningText("Blue", Color.BLUE);
			root.getChildren().add(blueWin);
			playable = false;
			winStatus = true;
		}
	}
	
	public void bullet2() {
		Rectangle bul = new Rectangle(10, 5);
		bul.setTranslateX(player2.getTranslateX());
		bul.setTranslateY(player2.getTranslateY() + 28);
		bul.setFill(Color.WHITE);
		
		root.getChildren().add(bul);
		
		bulletMotion = new AnimationTimer() {
			public void handle(long now) {
				if (playable) {
					bul.setTranslateX(bul.getTranslateX() - bulletSpeed);
				}
				if (bul.getTranslateX() < player1.getTranslateX() + 40
					&& bul.getTranslateX() > player1.getTranslateX()
					&& bul.getTranslateY() > player1.getTranslateY() - 10
					&& bul.getTranslateY() < player1.getTranslateY() + 65
					&& playable) {
					healthBar1.health.setWidth(healthBar1.health.getWidth() - healthDec);
					healthBar1.health.setTranslateX(healthBar1.health.getTranslateX() - healthDec / 2);
					root.getChildren().remove(bul);
					this.stop();
				}
				
				if (bul.getTranslateX() < 0) {
					root.getChildren().remove(bul);
				}
				
			}
		};
		
		bulletMotion.start();
		
		if (healthBar1.health.getWidth() < 1) {
			WinningText redWin = new WinningText("Red", Color.RED);
			root.getChildren().add(redWin);
			playable = false;
			winStatus = true;
		}
	}
	
	private class Player extends StackPane {
		ImageView plane = new ImageView(new Image("file:///Users/Danpark/Downloads/Aircraft.gif"));
		public Player() {
			plane.setFitWidth(65);
			plane.setFitHeight(60);
			
			getChildren().add(plane);
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
	
	private class WinningText extends StackPane {
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
			if (event.getCode().equals(KeyCode.W) && playable) {
				up.start();
			}
			if (event.getCode().equals(KeyCode.S) && playable) {
				down.start();
			}
			if (event.getCode().equals(KeyCode.D) && playable) {
				right.start();
			}
			if (event.getCode().equals(KeyCode.A) && playable) {
				left.start();
			}
			
			//Player 2
			if (event.getCode().equals(KeyCode.UP) && playable) {
				up2.start();
			}
			if (event.getCode().equals(KeyCode.DOWN) && playable) {
				down2.start();
			}
			if (event.getCode().equals(KeyCode.RIGHT) && playable) {
				right2.start();
			}
			if (event.getCode().equals(KeyCode.LEFT) && playable) {
				left2.start();
			}
			
			//Bullet for player1
			if (event.getCode().equals(KeyCode.V) && playable) {
				bullet();
			}
			
			//Bullet for player22	
			if (event.getCode().equals(KeyCode.ENTER) && playable) {
				bullet2();
			}
			
			if (event.getCode().equals(KeyCode.SPACE)) {
				if (!winStatus) {
				playable = !playable;
					if (!playable) {
						try {
							root.getChildren().add(pause);
							up.stop(); up2.stop();
							right.stop(); right2.stop();
							down.stop(); down2.stop();
							left.stop(); left2.stop();
						}
						catch(Exception e){}
					}
					else {
						root.getChildren().remove(pause);
					}
				}
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
