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
import javafx.scene.paint.Color;
import java.util.*;
import javafx.scene.image.Image;

public class Player{
	private double x, y;
	public static List<Bullets> bullets = new ArrayList<>();
	private static final double WIDTH = 100;
	private boolean shooting = false, damage = false;
	private int hp = 100;
        private static Image playerImage;
	
	public Player(double x, double y){
		this.x = x;
		this.y = y;
                
                if (playerImage == null) {
            playerImage = new Image("simpleshooter/player.png");
        }
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public int getHp(){
		return this.hp;
	}
	
	public void takeDamage(int dmg){
		 if (damage || this.hp <= 0) return;
                 this.hp -= dmg;
                 damage = true;
                 SimpleShooter.shedule(150, () -> damage = false);
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(playerImage, this.x, this.y, WIDTH, WIDTH);
		for (int i = 0; i < this.bullets.size(); i++){
			this.bullets.get(i).render(gc);
		}
	}
	
	public void move(double x, double y){
		this.x += x;
		this.y += y;
	}
	
	public void shoot(double x, double y){
		if (shooting) return;
		shooting = true;
		SimpleShooter.shedule(150, () -> this.shooting = false);
		double angle = Math.atan2(y-this.y, x-this.x); // Radians
		Bullets b = new Bullets(angle, this.x+WIDTH/2, this.y+WIDTH/2);
		this.bullets.add(b);
	}
        
       
}