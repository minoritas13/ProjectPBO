
package testgame;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.W;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;

public class Paddle {
    private Rectangle rectangle;
    private double velocityY = 0;
    private double speed = 10;

    public Paddle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void move1(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                velocityY = -speed; // Move up
                break;
            case S:
                velocityY = speed; // Move down
                break;
        }
    }
    public void move2(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                velocityY = -speed; // Move up (for second paddle)
                break;
            case DOWN:
                velocityY = speed; // Move down (for second paddle)
                break;
        }
    }

    public void update(double damping, double height) {
        double newY = rectangle.getLayoutY() + velocityY;
        velocityY *= damping; // Apply damping
        if (newY < 0) {
            newY = 0;
        } else if (newY + rectangle.getHeight() > height) {
            newY = height - rectangle.getHeight();
        }
        rectangle.setLayoutY(newY);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
