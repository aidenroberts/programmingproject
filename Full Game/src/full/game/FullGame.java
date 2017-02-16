package full.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FullGame extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        TitleScreen title = new TitleScreen();
        root.setTop(title);
        
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("E-Lemon-Ators");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setResizable(false);
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
