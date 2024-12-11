/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpleshooter;

/**
 *
 * @author lutfi
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;

public class Enemy extends Character {
    private double x, y;
    private Player player;
    public static final double WIDTH = 80;
    private static final double SPEED = 2;
    private static Image enemyImage;
    private double directionX;
    private double directionY;
    private Random random;
    private long lastDirectionChangeTime = System.currentTimeMillis();
    private static final long DIRECTION_CHANGE_INTERVAL = 2000;
    private double screenWidth;
    private double screenHeight;

    public Enemy(Player p, double screenWidth, double screenHeight) {
        // Set random initial position
        super(0, 0); // The initial position will be set randomly below
        this.player = p;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        random = new Random();

        // Generate random position within the screen bounds
        this.x = random.nextDouble() * (screenWidth - WIDTH);
        this.y = random.nextDouble() * (screenHeight - WIDTH);

        randomizeDirection(); // Initialize random direction

        if (enemyImage == null) {
            enemyImage = new Image("simpleshooter/enemy.png");
        }
    }

    private void randomizeDirection() {
        double angle = random.nextDouble() * 2 * Math.PI; // Random angle between 0 and 360 degrees
        directionX = Math.cos(angle);
        directionY = Math.sin(angle);
    }

    private boolean checkCollision() {
        for (int i = 0; i < SimpleShooter.enemies.size(); i++) {
            Enemy e = SimpleShooter.enemies.get(i);
            if (e != this) {
                if (e.collided(this.x, this.y, WIDTH, WIDTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collided(double x, double y, double w1, double w2) {
        return Math.sqrt(Math.pow(this.x + w1 / 2 - x - w2 / 2, 2) 
                       + Math.pow(this.y + w1 / 2 - y - w2 / 2, 2)) <= w1 / 2 + w2 / 2;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(enemyImage, this.x, this.y, WIDTH, WIDTH);
        double distance = Math.sqrt(Math.pow(this.x - this.player.getX(), 2) 
                                  + Math.pow(this.y - this.player.getY(), 2));
        if (distance <= 60) {
            this.player.takeDamage(5);
        } else {
            double angle = Math.atan2(this.player.getY() - this.y, this.player.getX() - this.x);
            this.x += Math.cos(angle) * SPEED;
            if (checkCollision()) {
                this.x -= Math.cos(angle) * SPEED;
            }
            this.y += Math.sin(angle) * SPEED;
            if (checkCollision()) {
                this.y -= Math.sin(angle) * SPEED;
            }
        }
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }
}
