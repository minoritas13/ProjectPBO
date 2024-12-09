package testgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane ruang;
    @FXML
    private Rectangle kotak;
    @FXML
    private Rectangle kotak1;
    @FXML
    private Circle bola;
    @FXML
    private Label skor2;
    @FXML
    private Label skor1;

    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;

    private Timeline gameLoop;
    private double damping = 0.5;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruang.setOnKeyPressed(this::handleKeyPress);
        
        ruang.requestFocus();
        ruang.setOnMouseClicked(event -> ruang.requestFocus());
        
        // Initialize paddles and ball
        paddle1 = new Paddle(kotak);
        paddle2 = new Paddle(kotak1);
        ball = new Ball(bola);
        

// Gunakan `customBall` dalam permainan

        // Start game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> updateGame()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void updateGame() {
        double ruangHeight = ruang.getHeight();

        // Update paddles and ball
        paddle1.update(damping, ruangHeight);
        paddle2.update(damping, ruangHeight);
        ball.update(ruang, paddle1, paddle2,skor1,skor2);
    }
    
    
    private void handleKeyPress(KeyEvent event) {
        // Handle key events for both paddles
            paddle1.move1(event);
            paddle2.move2(event);        
    }

    
}
