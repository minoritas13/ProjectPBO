/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package simpleshooter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext; 
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

/**
 *
 * @author lutfi
 */
public class SimpleShooter extends Application{
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	private static final double SPEED = 3;
	private Player player;
	private Map<KeyCode, Boolean> keys = new HashMap<>();
	public static List<Enemy> enemies = new ArrayList<>();
        private boolean gameOver = false;
	private int score = 0;
        private Image backgroundImage;
        private Stage stage;
        
        
	public static void main(String[] args){
		launch(args);
	}
	
	public static void shedule(long time, Runnable r){
		new Thread(() -> {
			try {
				Thread.sleep(time);
				r.run();
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
		}).start();
	}
	
	@Override
        public void start(Stage stage) {
        this.stage = stage;

        showMenu();
    }
        private void showMenu() {
        VBox menuPane = new VBox(20);
        menuPane.setPrefSize(WIDTH, HEIGHT);
        
        Image menuBackgroundImage = new Image("simpleshooter/bgMenu.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(menuBackgroundImage,
            javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
            javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
            javafx.scene.layout.BackgroundPosition.CENTER,
            new javafx.scene.layout.BackgroundSize(100, 100, true, true, true, true));
        menuPane.setBackground(new javafx.scene.layout.Background(backgroundImage));

        Text title = new Text("SIMPLE SHOOTER");
        title.setFont(Font.font("ROBOT", 40));
        title.setFill(Color.RED);

        Button startButton = new Button("Start Game");
        startButton.setFont(Font.font("Arial", 20));
        startButton.setOnAction(e -> startGame());

        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font("Arial", 20));
        exitButton.setOnAction(e -> System.exit(0)); 

        menuPane.setAlignment(javafx.geometry.Pos.CENTER);
        menuPane.getChildren().addAll(title, startButton, exitButton);

        Scene menuScene = new Scene(menuPane, WIDTH, HEIGHT);
        stage.setTitle("Simple Shooter Menu");
        stage.setScene(menuScene);
        stage.show();
    }
	public void startGame(){
		StackPane pane = new StackPane();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		canvas.setFocusTraversable(true);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		pane.getChildren().add(canvas);
                
                backgroundImage = new Image("simpleshooter/background.png");
		
		this.player = new Player(50, 50);
		
		Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/40), e -> update(gc)));
		loop.setCycleCount(Animation.INDEFINITE);
		loop.play();
		
		spawnEnemies();
		
		canvas.setOnKeyPressed(e -> this.keys.put(e.getCode(), true));
		canvas.setOnKeyReleased(e -> this.keys.put(e.getCode(), false));
		canvas.setOnMousePressed(e -> this.player.shoot(e.getX(), e.getY()));
		canvas.setOnMouseDragged(e -> this.player.shoot(e.getX(), e.getY()));
                canvas.setOnKeyPressed(e -> {this.keys.put(e.getCode(), true);
                if (e.getCode() == KeyCode.R && gameOver) {
                    restartGame();
                }
            });
		
		Scene scene = new Scene(pane, WIDTH, HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
	
	private void spawnEnemies(){
		Thread spawner = new Thread(() -> {
			try {
				Random random = new Random();
				while (true){
					int spawnSide = random.nextInt(4); 

                double x = 0, y = 0;

                switch (spawnSide) {
                    case 0:
                        x = -Enemy.WIDTH;
                        y = random.nextDouble() * HEIGHT;
                        break;
                    case 1:
                        x = WIDTH;
                        y = random.nextDouble() * HEIGHT;
                        break;
                    case 2:
                        x = random.nextDouble() * WIDTH;
                        y = -Enemy.WIDTH;
                        break;
                    case 3:
                        x = random.nextDouble() * WIDTH;
                        y = HEIGHT;
                        break;
                }this.enemies.add(new Enemy(this.player, x, y));
                Thread.sleep(2000);
            }
				
			} catch (InterruptedException ex){
				ex.printStackTrace();
			}
		});
		spawner.setDaemon(true);
		spawner.start();
	}
	
	private void update(GraphicsContext gc){
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            gc.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT);
            if (gameOver) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillText("GAME OVER", WIDTH / 2 - 60, HEIGHT / 2);
            gc.fillText("Press 'R' to Restart", WIDTH / 2 - 80, HEIGHT / 2 + 30);
            return; 
    }

                
                if (this.player.getHp() <= 0) {
                gameOver = true;
                return;
                }
        
		for (int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.render(gc);
			for (int j = 0; j < Player.bullets.size(); j++){
				if (e.collided(Player.bullets.get(j).getX(), Player.bullets.get(j).getY(), Enemy.WIDTH, Bullets.WIDTH)){
					Player.bullets.remove(j);
					enemies.remove(i);
					i++;
                                        score += 10; 
					break;
				}
			}
		}
		this.player.render(gc);

		if (this.keys.getOrDefault(KeyCode.W, false)){
			this.player.move(0, -SPEED);
		}
		if (this.keys.getOrDefault(KeyCode.A, false)){
			this.player.move(-SPEED, 0);
		}
		if (this.keys.getOrDefault(KeyCode.S, false)){
			this.player.move(0, SPEED);
		}
		if (this.keys.getOrDefault(KeyCode.D, false)){
			this.player.move(SPEED, 0);
		}
		
		gc.setFill(Color.GREEN);
		gc.fillRect(50, HEIGHT-80, 100*(this.player.getHp()/100.0), 30);
		gc.setStroke(Color.BLACK);
		gc.strokeRect(50, HEIGHT-80, 100, 30);
                gc.setFill(Color.LIGHTGREEN);
                gc.fillText("Score: " + score, 50, 60);
                
	}
        
        private void restartGame() {
            this.player = new Player(50, 50);
            this.enemies.clear();
            this.score = 0;
            this.gameOver = false;
            this.keys.clear();
            spawnEnemies(); 
    
    }   
}