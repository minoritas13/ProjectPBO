package testgame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Ball {
    private Circle ball;
    private double velocityX = 1;
    private double velocityY = 1;
    private double speed = 1;
    private Label skor1;
    private Label skor2;
    
    private static int player1Points = 0;
    private static int player2Points = 0;

    public Ball(Circle ball) {
        this.ball = ball;
    }

    public void update(AnchorPane ruang, Paddle paddle1, Paddle paddle2,Label skor1,Label skor2) {
        double nextX = ball.getLayoutX() + velocityX * speed;
        double nextY = ball.getLayoutY() + velocityY * speed;
        double ruangHeight = ruang.getHeight();
        double ruangWidth = ruang.getWidth();
        
        ball.setLayoutX(nextX);
        ball.setLayoutY(nextY);

        if (nextY < 0) {
            velocityY = 2;
            ball.setLayoutY(nextY);
        }
        
        else if (nextY > ruangHeight) {
            velocityY = -2;
            ball.setLayoutY(nextY);
        }
       
        if (paddle1.getRectangle().getBoundsInParent().intersects(ball.getBoundsInParent())) {
           velocityX = 2;
           ball.setLayoutX(nextX);
        }

        if (paddle2.getRectangle().getBoundsInParent().intersects(ball.getBoundsInParent())) {
           velocityX = -2;
           ball.setLayoutX(nextX);
        }
        
        if (nextX < 0) {
            player1Points++;
            String str = Integer.toString(player1Points);
            skor2.setText(str);
            resetBall();
        }
        
        if (nextX > ruang.getWidth()) {
            player2Points++;
            String str = Integer.toString(player2Points);
            skor1.setText(str);
            resetBall();
        }
    }

    private void resetBall() {
        ball.setLayoutX(400);
        ball.setLayoutY(300);
        velocityX = 2;
        velocityY = 2;
    }
    
}
