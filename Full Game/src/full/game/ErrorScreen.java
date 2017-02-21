package full.game;

import java.io.File; // Imports all necessary files for the game
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 *
 * @author guine
 */
public class ErrorScreen extends Pane{
    private TextField error;
    Button backToTitle = new Button("Back To Title");
    
    public ErrorScreen(String error) {
        this.error = new TextField(error);
        createScreen();
    }
    
    public void createScreen() { // Creates the Error Screen
        File file = new File("Error Sign.png"); // Creates the background of the Error Screen
        Image sign = new Image(file.toURI().toString());
        ImageView ivSign = new ImageView(sign);
        
        ivSign.setFitWidth(500);
        ivSign.setFitHeight(500);
        
        error.setFont(Font.font("Times", 16)); // Creates a TextField that contains an error message
        error.setPrefWidth(300);
        error.setLayoutX(100);
        error.setLayoutY(350);
        error.setEditable(false);
        
        backToTitle.setLayoutX(150); // Creates a button that returns the user to the Title Screen
        backToTitle.setPrefWidth(200);
        backToTitle.setLayoutY(400);
        backToTitle.setFont(Font.font("Times", 16));
        
        getChildren().addAll(ivSign, error, backToTitle);
    }
}
