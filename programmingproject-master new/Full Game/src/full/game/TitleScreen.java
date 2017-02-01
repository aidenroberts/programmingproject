package full.game;

import java.io.File;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author programming
 */
public class TitleScreen extends Pane{
    public int width = 500, height = 500;
    
    public TitleScreen() {
        File graphic = new File("Main Menu.gif");
        Image image = new Image(graphic.toURI().toString());
        ImageView titleScreen = new ImageView(image);
        titleScreen.setFitHeight(500);
        titleScreen.setFitWidth(500);
        
        
        File song = new File("Aiden's song.mp3");
        Media media = new Media(song.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setCycleCount(Timeline.INDEFINITE);
        mp.play();
        
        Text title = new Text(25, 70, "E-Lemon-Ators");
        title.setFont(Font.font("Times", FontWeight.BOLD, 64));
        title.setFill(Color.YELLOW);
        
        Button newGame = new Button("New Game");
        newGame.setFont(Font.font("Times", 32));
        newGame.setLayoutX(150);
        newGame.setLayoutY(275);
        
        newGame.setOnAction(e -> {
            getChildren().clear();
            Creation creator = new Creation();
            getChildren().add(creator);
        });
        
        Button loadGame = new Button("Load Game");
        loadGame.setFont(Font.font("Times", 32));
        loadGame.setLayoutX(150);
        loadGame.setLayoutY(375);
        
        getChildren().addAll(titleScreen, title, newGame, loadGame);
    }
    
    static class Creation extends Pane{
        String name = "";
        int classNum;
        public Creation() {
            Rectangle background = new Rectangle(0, 0, 500, 500);
            background.setFill(Color.GREY);
            getChildren().add(background);

            Text prompt = new Text(50, 60, "Select a class: ");
            prompt.setFont(Font.font("Times", FontWeight.BOLD, 64));
            prompt.setFill(Color.YELLOW);
            getChildren().add(prompt);

            Button warrior = new Button("Warrior");
            warrior.setFont(Font.font("Times", 32));
            warrior.setLayoutX(315);
            warrior.setLayoutY(100);
            warrior.setOnAction(e -> {
                getChildren().clear();
                setClassNum(1);
                makeName();
                });

            File warriorPicLoc = new File("characters\\Warrior Character.png");
            Image warriorPic = new Image(warriorPicLoc.toURI().toString());
            ImageView ivWarrior = new ImageView(warriorPic);
            ivWarrior.setFitHeight(75);
            ivWarrior.setFitWidth(75);
            ivWarrior.setLayoutX(50);
            ivWarrior.setLayoutY(warrior.getLayoutY());

            Button tank = new Button("Tank");
            tank.setFont(Font.font("Times", 32));
            tank.setLayoutX(350);
            tank.setLayoutY(200);
            tank.setOnAction(e -> {
                getChildren().clear();
                setClassNum(2);
                makeName();
                });

            File tankPicLoc = new File("characters\\Tank Character.png");
            Image tankPic = new Image(tankPicLoc.toURI().toString());
            ImageView ivTank = new ImageView(tankPic);
            ivTank.setFitHeight(75);
            ivTank.setFitWidth(75);
            ivTank.setLayoutX(50);
            ivTank.setLayoutY(tank.getLayoutY());

            Button speedster = new Button("Speedster");
            speedster.setFont(Font.font("Times", 32));
            speedster.setLayoutX(280);
            speedster.setLayoutY(300);
            speedster.setOnAction(e -> {
                getChildren().clear();
                setClassNum(3);
                makeName();
                });

            File speedsterPicLoc = new File("characters\\Speedster Character.png");
            Image speedsterPic = new Image(speedsterPicLoc.toURI().toString());
            ImageView ivSpeedster = new ImageView(speedsterPic);
            ivSpeedster.setFitHeight(75);
            ivSpeedster.setFitWidth(75);
            ivSpeedster.setLayoutX(50);
            ivSpeedster.setLayoutY(speedster.getLayoutY());

            Button glassCannon = new Button("Glass Cannon");
            glassCannon.setFont(Font.font("Times", 32));
            glassCannon.setLayoutX(250);
            glassCannon.setLayoutY(400);
            glassCannon.setOnAction(e -> {
                getChildren().clear();
                setClassNum(4);
                makeName();
                });

            File glassCannonPicLoc = new File("characters\\Glass Cannon Character.png");
            Image glassCannonPic = new Image(glassCannonPicLoc.toURI().toString());
            ImageView ivGlassCannon = new ImageView(glassCannonPic);
            ivGlassCannon.setFitHeight(75);
            ivGlassCannon.setFitWidth(75);
            ivGlassCannon.setLayoutX(50);
            ivGlassCannon.setLayoutY(glassCannon.getLayoutY());

            getChildren().addAll(warrior, ivWarrior, tank, ivTank, speedster, ivSpeedster, glassCannon, ivGlassCannon);
        }
    
        public void makeName() {
            Rectangle background = new Rectangle(0, 0, 500, 500);
            background.setFill(Color.GREY);

            Text prompt = new Text(5, 50, "Enter your character's\n\t\tname: ");
            prompt.setFont(Font.font("Times",FontWeight.BOLD, 48));
            prompt.setFill(Color.YELLOW);

            TextField nameEntry = new TextField();
            nameEntry.setLayoutX(100);
            nameEntry.setLayoutY(250);
            nameEntry.setFont(Font.font("Times", 32));
            nameEntry.setPrefWidth(300);
            nameEntry.setOnAction(e -> {
                setName(nameEntry.getText());
                });

            getChildren().addAll(background, nameEntry, prompt);
        }
        public void setClassNum(int classNum) {
            this.classNum = classNum;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
