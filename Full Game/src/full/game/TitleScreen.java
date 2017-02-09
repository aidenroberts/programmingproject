package full.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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


public class TitleScreen extends Pane{
    public static double width = 1000, height = 1000;
    public boolean created = false, loaded = false, gameplayLoaded = false;
    public static File song = new File("Aiden's song.mp3");
    public static Media media = new Media(song.toURI().toString());
    public static MediaPlayer mp = new MediaPlayer(media);
    
    public TitleScreen() {
        createScreen();
    }
    
    public void createScreen() {
        File graphic = new File("Main Menu.gif");
        Image image = new Image(graphic.toURI().toString());
        ImageView titleScreen = new ImageView(image);
        titleScreen.setFitHeight(height);
        titleScreen.setFitWidth(width);
        
        mp.setCycleCount(Timeline.INDEFINITE);
        mp.play();
        
        Text title = new Text(width * .425, height * .11, "E-Lemon-Ators");
        title.setFont(Font.font("Times", FontWeight.BOLD, 12));
        title.setScaleX(width / 100);
        title.setScaleY(height / 100);
        title.setFill(Color.YELLOW);
        
        Button newGame = new Button("New Game");
        newGame.setFont(Font.font("Times", 12));
        newGame.setLayoutX(width * .45);
        newGame.setLayoutY(height * .55);
        newGame.setScaleX(width * .005);
        newGame.setScaleY(height * .005);
        
        newGame.setOnAction(e -> {
            getChildren().clear();
            Creation creator = new Creation();
            getChildren().add(creator);
            
            creator.backToTitle.setOnAction(f -> {
                creator.getChildren().clear();
                createScreen();
            });
        });
        
        Button loadGame = new Button("Load Game");
        loadGame.setFont(Font.font("Times", 12));
        loadGame.setLayoutX(width * .45);
        loadGame.setLayoutY(height * .75);
        loadGame.setScaleX(width * .005);
        loadGame.setScaleY(height * .005);
        
        loadGame.setOnAction(e -> {
            loaded = true;
            getChildren().clear();
            Loading loadPane = new Loading();
            getChildren().add(loadPane);
            
            loadPane.backToTitle.setOnAction(f -> {
                loadPane.getChildren().clear();
                createScreen();
            });
            
            loadPane.loadGame.setOnAction(g -> {
                gameplayLoaded = true;
            });
        });
        getChildren().addAll(titleScreen, title, newGame, loadGame);
    }
    
    static class Creation extends Pane{
        String name = "";
        int classNum, health, speed, damage;
        boolean titleClear = false;
        Button backToTitle = new Button("Back To Title Screen"), backToSelection = new Button("Back To Class Selection");
        
        public Creation() {
            createScene();
        }
        
        public void createScene() {
            Rectangle background = new Rectangle(0, 0, width, height);
            background.setFill(Color.GREY);
            getChildren().add(background);

            Text prompt = new Text(width * .45, height * .11, "Select a class: ");
            prompt.setFont(Font.font("Times", FontWeight.BOLD, 12));
            prompt.setScaleX(width * .006);
            prompt.setScaleY(height * .006);
            prompt.setFill(Color.YELLOW);
            getChildren().add(prompt);

            Button warrior = new Button("Warrior");
            warrior.setFont(Font.font("Times", 12));
            warrior.setLayoutX(width * .8);
            warrior.setLayoutY(height * .275);
            warrior.setScaleX(width * .006);
            warrior.setScaleY(height * .006);
            warrior.setOnAction(e -> {
                getChildren().clear();
                classNum = 1;
                health = 75;
                speed = 75;
                damage = 75;
                makeName();
            });

            File warriorPicLoc = new File("characters\\Warrior Character.png");
            Image warriorPic = new Image(warriorPicLoc.toURI().toString());
            ImageView ivWarrior = new ImageView(warriorPic);
            ivWarrior.setFitHeight(height * .15);
            ivWarrior.setFitWidth(width * .15);
            ivWarrior.setLayoutX(0);
            ivWarrior.setLayoutY(height * .225);

            Text warriorInfo = new Text(width * .15, height * .3, "Balanced Stats");
            warriorInfo.setFont(Font.font("Times", 32));
            
            
            Button tank = new Button("Tank");
            tank.setFont(Font.font("Times", 12));
            tank.setLayoutX(width * .85);
            tank.setLayoutY(height * .45);
            tank.setScaleX(width * .006);
            tank.setScaleY(height * .006);
            tank.setOnAction(e -> {
                getChildren().clear();
                classNum = 2;
                health = 125;
                speed = 50;
                damage = 75;
                makeName();
            });

            File tankPicLoc = new File("characters\\Tank Character.png");
            Image tankPic = new Image(tankPicLoc.toURI().toString());
            ImageView ivTank = new ImageView(tankPic);
            ivTank.setFitHeight(height * .15);
            ivTank.setFitWidth(width * .15);
            ivTank.setLayoutX(0);
            ivTank.setLayoutY(height * .4);

            Text tankInfo = new Text(width * .15, height * .475, "High health, low speed");
            tankInfo.setFont(Font.font("Times", 32));
            
            Button speedster = new Button("Speedster");
            speedster.setFont(Font.font("Times", 12));
            speedster.setLayoutX(width * .75);
            speedster.setLayoutY(height * .635);
            speedster.setScaleX(width * .006);
            speedster.setScaleY(height * .006);
            speedster.setOnAction(e -> {
                getChildren().clear();
                classNum = 3;
                health = 75;
                speed = 125;
                damage = 50;
                makeName();
            });

            File speedsterPicLoc = new File("characters\\Speedster Character.png");
            Image speedsterPic = new Image(speedsterPicLoc.toURI().toString());
            ImageView ivSpeedster = new ImageView(speedsterPic);
            ivSpeedster.setFitHeight(width * .15);
            ivSpeedster.setFitWidth(height * .15);
            ivSpeedster.setLayoutX(0);
            ivSpeedster.setLayoutY(height * .6);

            Text speedsterInfo = new Text(width * .15, height * .675, "High speed, low damage");
            speedsterInfo.setFont(Font.font("Times", 32));
            
            Button glassCanon = new Button("Glass Cannon");
            glassCanon.setFont(Font.font("Times", 10));
            glassCanon.setLayoutX(width * .735);
            glassCanon.setLayoutY(height * .85);
            glassCanon.setScaleX(width * .006);
            glassCanon.setScaleY(height * .006);
            glassCanon.setOnAction(e -> {
                getChildren().clear();
                classNum = 4;
                health = 50;
                speed = 75;
                damage = 125;
                makeName();
            });

            File glassCanonPicLoc = new File("characters\\Glass Cannon Character.png");
            Image glassCanonPic = new Image(glassCanonPicLoc.toURI().toString());
            ImageView ivGlassCanon = new ImageView(glassCanonPic);
            ivGlassCanon.setFitHeight(height * .15);
            ivGlassCanon.setFitWidth(width * .15);
            ivGlassCanon.setLayoutX(0);
            ivGlassCanon.setLayoutY(height * .8);
            
            Text glassCannonInfo = new Text(width * .15, height * .875, "High damage, low health");
            glassCannonInfo.setFont(Font.font("Times", 32));
            
            backToTitle.setLayoutX(width / 3.5);
            backToTitle.setLayoutY(0);
            backToTitle.setFont(Font.font("Times", 32));
            
            getChildren().addAll(warrior, ivWarrior, warriorInfo, tank, ivTank, tankInfo, speedster, ivSpeedster, speedsterInfo, glassCanon, ivGlassCanon, glassCannonInfo, backToTitle);
        }
        
        public void makeName() {
            Rectangle background = new Rectangle(0, 0, width, height);
            background.setFill(Color.GREY);

            Text prompt = new Text(width  * .425, height / 9, "Enter your character's\n\t\tname: ");
            prompt.setFont(Font.font("Times",FontWeight.BOLD, 12));
            prompt.setScaleX(width * .0075);
            prompt.setScaleY(height * .0075);
            prompt.setFill(Color.YELLOW);

            TextArea stats = new TextArea();
            
            String info = "";
            switch (classNum) {
                case 1:
                    info += "Class: Warrior";
                    break;
                case 2:
                    info += "Class: Tank";
                    break;
                case 3:
                    info += "Class: Speedster";
                    break;
                default:
                    info += "Class: Glass Cannon";
                    break;
            }
            info += "\nHealth: " + health;
            info += "\nSpeed: " + speed;
            info += "\nDamage: " + damage;
            
            stats.setText(info);
            stats.setLayoutX(width / 4);
            stats.setLayoutY(height / 3);
            stats.setPrefSize(width / 2, height / 3.5);
            stats.setFont(Font.font("Times", 24));
            stats.setEditable(false);
            
            TextField nameEntry = new TextField();
            nameEntry.setLayoutX(width / 7);
            nameEntry.setLayoutY(height / 4);
            nameEntry.setFont(Font.font("Times", 32));
            nameEntry.setPrefWidth(width * .75);
            nameEntry.setOnAction(e -> {
                name = nameEntry.getText();
                getChildren().clear();
                mp.stop();
                Gameplay pane = new Gameplay(name, classNum, health, speed, damage);
                getChildren().add(pane);
            });
            
            backToTitle.setLayoutX(width / 4);
            backToTitle.setLayoutY(height / 1.5);
            backToTitle.setFont(Font.font("Times", 48));
            
            backToSelection.setLayoutX(width / 4);
            backToSelection.setLayoutY(height / 1.25);
            backToSelection.setFont(Font.font("Times", 48));
            
            backToSelection.setOnAction(e -> {
               getChildren().clear();
               createScene(); 
            });
            
            getChildren().addAll(background, nameEntry, prompt, stats, backToTitle, backToSelection);
        }
    }
    
    static class Loading extends Pane {
        public int classNum, health, speed, damage, xp, level, cLevel;
        String name;
        Button backToTitle = new Button("Back To Title Screen"), loadGame = new Button("Load Selected File");
        ComboBox saveList = new ComboBox();
        ArrayList<String> names;
        ObservableList<String> nameList;
        
        public Loading() {
            loadGames();
            
            Rectangle background = new Rectangle(0, 0, width, height);
            background.setFill(Color.GREY);
            
            Text prompt = new Text(width / 2.25, height / 10, "Select a save file: ");
            prompt.setFill(Color.YELLOW);
            prompt.setFont(Font.font("Times", 12));
            prompt.setScaleX(width * .006);
            prompt.setScaleY(height * .006);
            
            saveList.setLayoutX(width / 4.25);
            saveList.setLayoutY(height / 4);
            saveList.setPrefSize(width / 1.9, height / 20);
            
            TextArea stats = new TextArea();
            stats.setLayoutX(width / 4);
            stats.setLayoutY(height / 3);
            stats.setPrefSize(width / 2, height / 3.5);
            stats.setFont(Font.font("Times", 24));
            stats.setEditable(false);
            
            backToTitle.setFont(Font.font("Times", 48));
            backToTitle.setLayoutX(width / 3.875);
            backToTitle.setLayoutY(height / 1.5);
            
            loadGame.setFont(Font.font("Times", 48));
            loadGame.setLayoutX(width / 3.6125);
            loadGame.setLayoutY(height / 1.25);
           
            getChildren().addAll(background, prompt, saveList, backToTitle, loadGame, stats);
            
            saveList.setOnAction(e -> {
                loadFile();
                String info = "";
                info += "Name: " + name;
                switch (classNum) {
                    case 1:
                        info += "\nClass: Warrior";
                        break;
                    case 2:
                        info += "\nClass: Tank";
                        break;
                    case 3:
                        info += "\nClass: Speedster";
                        break;
                    default:
                        info += "\nClass: Glass Cannon";
                        break;
                }
                info += "\nLevel: " + cLevel;
                info += "\nHealth: " + health;
                info += "\nSpeed: " + speed;
                info += "\nDamage: " + damage;
                info += "\nArea #: " + level;
                info += "\nXP: " + xp;
                
                stats.setText(info);
            });
            
            loadGame.setOnAction(e -> {
               getChildren().clear();
               mp.stop();
               Gameplay pane = new Gameplay(name, classNum, health, speed, damage, level, xp, cLevel);
               getChildren().add(pane);
            });
        }
        
        public void loadGames() {
            try {
                FileInputStream list = new FileInputStream("saves\\SaveList.sav");
                ObjectInputStream save = new ObjectInputStream(list);
                
                names = (ArrayList<String>)save.readObject();
                
                nameList = (ObservableList)names;
                
                saveList.setItems(nameList);
                
                save.close();
            } catch(Exception ex) {
                Text nothing = new Text(width / 8, height / 2, "No files exist.");
                nothing.setFont(Font.font("Times", 128));
                nothing.setFill(Color.YELLOW);
                
                getChildren().add(nothing);
            }
            
            
            
        }
        
        public void loadFile() {
            name = names.get(nameList.indexOf(saveList.getValue()));
            String fileName = "saves\\" + name + ".sav";
            
            try {
                FileInputStream saveFile = new FileInputStream(fileName);
                ObjectInputStream save = new ObjectInputStream(saveFile);
                
                this.classNum = (Integer)save.readObject();
                this.health = (Integer)save.readObject();
                this.speed = (Integer)save.readObject();
                this.damage = (Integer)save.readObject();
                this.xp = (Integer)save.readObject();
                this.level = (Integer)save.readObject();
                this.cLevel = (Integer)save.readObject();
                
                save.close();
            } catch (Exception ex) {
            }
        }
    }
}