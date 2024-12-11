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

public abstract class Character {
    protected double x, y;
    protected static final double WIDTH = 100;
    
    public Character(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(GraphicsContext gc);
    
    public abstract void move(double x, double y);
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
}
