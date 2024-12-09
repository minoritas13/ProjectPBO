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
import javafx.scene.image.Image;

public class Bullets{
	private double angle, x, y;
	private static final double SPEED = 10;
	public static final double WIDTH = 50;
        public static Image bulletsImage;
	
	public Bullets(double angle, double x, double y){
		this.x = x;
		this.y = y;
		this.angle = angle;
                
                if (bulletsImage == null) {
            bulletsImage = new Image("simpleshooter/bullets.png");
        }
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void render(GraphicsContext gc){
		gc.drawImage(bulletsImage, this.x, this.y, WIDTH, WIDTH);
		
		this.x += Math.cos(this.angle)*SPEED;
		this.y += Math.sin(this.angle)*SPEED;
	}
}