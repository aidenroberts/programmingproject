package full.game;

import java.io.File; // Imports all necessary files for the game
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
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
    public static double width = 500, height = 500;
    
    public static File song = new File("Aiden's song.mp3"); // Plays the Title Screen song
    public static Media media = new Media(song.toURI().toString());
    public static MediaPlayer mp = new MediaPlayer(media);
    
    public TitleScreen() {
        createScreen();
    }
    
    public void createScreen() {
        File graphic = new File("Main Menu.gif"); // Creates the Title Screen Background
        Image image = new Image(graphic.toURI().toString());
        ImageView titleScreen = new ImageView(image);
        titleScreen.setFitHeight(height);
        titleScreen.setFitWidth(width);
        
        mp.setCycleCount(Timeline.INDEFINITE); // Plays the Title Screen song
        mp.play();
        
        Text title = new Text(width * .425, height * .11, "E-Lemon-Ators"); // Creates the Title Text
        title.setFont(Font.font("Times", FontWeight.BOLD, 12));
        title.setScaleX(width / 100);
        title.setScaleY(height / 100);
        title.setFill(Color.YELLOW);
        
        Button newGame = new Button("New Game"); // Creates the New Game button for the Title Screen
        newGame.setFont(Font.font("Times", 12));
        newGame.setLayoutX(width * .45);
        newGame.setLayoutY(height * .55);
        newGame.setScaleX(width * .005);
        newGame.setScaleY(height * .005);
        
        newGame.setOnAction(e -> { // Creates the function of the New Game button
            getChildren().clear();
            Creation creator = new Creation();
            getChildren().add(creator);
            
            creator.backToTitle.setOnAction(f -> {
                creator.getChildren().clear();
                createScreen();
            });
        });
        
        Button loadGame = new Button("Load Game"); // Create the Load Game button for the Title Screen
        loadGame.setFont(Font.font("Times", 12));
        loadGame.setLayoutX(width * .45);
        loadGame.setLayoutY(height * .75);
        loadGame.setScaleX(width * .005);
        loadGame.setScaleY(height * .005);
        
        loadGame.setOnAction(e -> { // Creates the function for the Load Game button
            getChildren().clear();
            Loading loadPane = new Loading();
            getChildren().add(loadPane);
            
            loadPane.backToTitle.setOnAction(f -> { // Creates the function of the Back To Title button on the Load Game screen
                loadPane.getChildren().clear();
                createScreen();
            });
        });
        getChildren().addAll(titleScreen, title, newGame, loadGame); // Adds the background, title, and buttons to the Pane
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
            Rectangle background = new Rectangle(0, 0, width, height); // Creates the grey background on the Create a Character Screens
            background.setFill(Color.GREY);
            getChildren().add(background);

            Text prompt = new Text(width * .45, height * .125, "Select a class: "); // Creates the prompt on the Select a Class Screen
            prompt.setFont(Font.font("Times", FontWeight.BOLD, 12));
            prompt.setScaleX(width * .006);
            prompt.setScaleY(height * .006);
            prompt.setFill(Color.YELLOW);
            getChildren().add(prompt);

            Button warrior = new Button("Warrior"); // Creates the button that selects the Warrior class
            warrior.setFont(Font.font("Times", 12));
            warrior.setLayoutX(width * .8);
            warrior.setLayoutY(height * .275);
            warrior.setScaleX(width * .0045);
            warrior.setScaleY(height * .0045);
            
            warrior.setOnAction(e -> { // Creates the function of the Warrior class selection button
                getChildren().clear();
                classNum = 1;
                health = 75;
                speed = 75;
                damage = 75;
                makeName();
            });

            File warriorPicLoc = new File("characters\\Warrior Character.png"); // Creates an image that displays the Warrior character's sprite
            Image warriorPic = new Image(warriorPicLoc.toURI().toString());
            ImageView ivWarrior = new ImageView(warriorPic);
            ivWarrior.setFitHeight(height * .15);
            ivWarrior.setFitWidth(width * .15);
            ivWarrior.setLayoutX(0);
            ivWarrior.setLayoutY(height * .225);

            Text warriorInfo = new Text(width * .15, height * .3, "Balanced Stats"); // Creates a text that basically describes the Warrior's stats
            warriorInfo.setFont(Font.font("Times", 20));
            
            
            Button tank = new Button("Tank"); // Creates a button that selects the Tank class
            tank.setFont(Font.font("Times", 12));
            tank.setLayoutX(width * .85);
            tank.setLayoutY(height * .45);
            tank.setScaleX(width * .0045);
            tank.setScaleY(height * .0045);
            
            tank.setOnAction(e -> { // Creates the function of the Button that selects the Tank class
                getChildren().clear();
                classNum = 2;
                health = 125;
                speed = 50;
                damage = 75;
                makeName();
            });

            File tankPicLoc = new File("characters\\Tank Character.png"); // Creates an image that displays the Tank character's sprite
            Image tankPic = new Image(tankPicLoc.toURI().toString());
            ImageView ivTank = new ImageView(tankPic);
            ivTank.setFitHeight(height * .15);
            ivTank.setFitWidth(width * .15);
            ivTank.setLayoutX(0);
            ivTank.setLayoutY(height * .4);

            Text tankInfo = new Text(width * .15, height * .475, "High health, low speed"); // Creates a text that basically desribes the Tank's stats
            tankInfo.setFont(Font.font("Times", 20));
            
            Button speedster = new Button("Speedster"); // Creates a button that selects the Speedster class
            speedster.setFont(Font.font("Times", 12));
            speedster.setLayoutX(width * .75);
            speedster.setLayoutY(height * .635);
            speedster.setScaleX(width * .0045);
            speedster.setScaleY(height * .0045);
            
            speedster.setOnAction(e -> { // Creates the function of the button that selects the Speedster class
                getChildren().clear();
                classNum = 3;
                health = 75;
                speed = 125;
                damage = 50;
                makeName();
            });

            File speedsterPicLoc = new File("characters\\Speedster Character.png"); // Creates an image that displays the Speedster character's sprite
            Image speedsterPic = new Image(speedsterPicLoc.toURI().toString());
            ImageView ivSpeedster = new ImageView(speedsterPic);
            ivSpeedster.setFitHeight(width * .15);
            ivSpeedster.setFitWidth(height * .15);
            ivSpeedster.setLayoutX(0);
            ivSpeedster.setLayoutY(height * .6);

            Text speedsterInfo = new Text(width * .15, height * .675, "High speed, low damage"); // Creates the text that basically describes the Speedster character's stats
            speedsterInfo.setFont(Font.font("Times", 20));
            
            Button glassCanon = new Button("Glass Cannon"); // Creates a button that selects the Glass Cannon class
            glassCanon.setFont(Font.font("Times", 10));
            glassCanon.setLayoutX(width * .735);
            glassCanon.setLayoutY(height * .85);
            glassCanon.setScaleX(width * .0045);
            glassCanon.setScaleY(height * .0045);
            
            glassCanon.setOnAction(e -> { // Creates the function of the button that selects the Glass Cannon class
                getChildren().clear();
                classNum = 4;
                health = 50;
                speed = 75;
                damage = 125;
                makeName();
            });

            File glassCanonPicLoc = new File("characters\\Glass Cannon Character.png"); // Creates an image that displays the Glass Cannon character's sprite
            Image glassCanonPic = new Image(glassCanonPicLoc.toURI().toString());
            ImageView ivGlassCanon = new ImageView(glassCanonPic);
            ivGlassCanon.setFitHeight(height * .15);
            ivGlassCanon.setFitWidth(width * .15);
            ivGlassCanon.setLayoutX(0);
            ivGlassCanon.setLayoutY(height * .8);
            
            Text glassCannonInfo = new Text(width * .15, height * .875, "High damage, low health"); // Creates a text that basically describes the Glass Cannon character's stats
            glassCannonInfo.setFont(Font.font("Times", 20));
            
            backToTitle.setLayoutX(width / 2.5); // Creates a button that sends the user back to the Title Screen
            backToTitle.setLayoutY(10);
            backToTitle.setScaleX(width * .003);
            backToTitle.setScaleY(height * .003);
            backToTitle.setFont(Font.font("Times", 10));
            
            getChildren().addAll(warrior, ivWarrior, warriorInfo, tank, ivTank, tankInfo, speedster, ivSpeedster, speedsterInfo, glassCanon, ivGlassCanon, glassCannonInfo, backToTitle);
        }
        
        public void makeName() {
            Rectangle background = new Rectangle(0, 0, width, height); // Creates the background of the Make a Name screen
            background.setFill(Color.GREY);

            Text prompt = new Text(width  * .35, height / 9, "Enter your character's\n\t\tname: "); // Creates a text that prompts the user to enter the name of their character
            prompt.setFont(Font.font("Times",FontWeight.BOLD, 12));
            prompt.setScaleX(width * .0075);
            prompt.setScaleY(height * .0075);
            prompt.setFill(Color.YELLOW);

            TextArea stats = new TextArea(); // Creates a TextArea that displays the base stats of the class selected
            
            String info = ""; // Adds the stats into a string that is added to the TextArea
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
            stats.setLayoutX(width / 4); // Adjusts the TextArea
            stats.setLayoutY(height / 3);
            stats.setPrefSize(width / 2, height / 3.5);
            stats.setFont(Font.font("Times", 16));
            stats.setEditable(false);
            
            TextField nameEntry = new TextField(); // Creates a TextField that accepts user input and saves the input to be the character's name
            nameEntry.setLayoutX(width / 7);
            nameEntry.setLayoutY(height / 4);
            nameEntry.setFont(Font.font("Times", 16));
            nameEntry.setPrefWidth(width * .75);
            
            backToTitle.setLayoutX(width / 2.8); // Creates a button that sends the user back to the Title Screen
            backToTitle.setLayoutY(height / 1.5);
            backToTitle.setFont(Font.font("Times", 16));
            
            backToSelection.setLayoutX(width / 3); // Creates a button that sends the user back to the Class Selection screen
            backToSelection.setLayoutY(height / 1.3);
            backToSelection.setFont(Font.font("Times", 16));
            
            Button startGame = new Button("Start Playing"); // Creates the button that starts the game once the user has entered a name
            startGame.setLayoutX(width / 2.5);
            startGame.setLayoutY(height / 1.15);
            startGame.setFont(Font.font("Times", 16));
            
            backToSelection.setOnAction(e -> { // Creates the function of the button that sends the user back to the Class Selection screen
               getChildren().clear();
               createScene(); 
            });
            
            getChildren().addAll(background, nameEntry, prompt, stats, backToTitle, backToSelection, startGame);
            
            startGame.setOnAction(e -> { // Creates the function of the button that starts the game
                name = nameEntry.getText();
                if (name != null) {
                    getChildren().clear();
                    mp.stop();
                    Gameplay pane = new Gameplay(name, classNum, health, speed, damage);
                    getChildren().add(pane);
                    pane.createScene();
                }
            });
        }
    }
    
    static class Loading extends Pane {
        public int classNum, health, maxHealth, speed, damage, xp, level, cLevel, timesSaved;
        String name;
        double x, y;
        boolean bossDefeated;
        Button backToTitle = new Button("Back To Title Screen"), loadGame = new Button("Load Selected File");
        ComboBox saveList = new ComboBox();
        ArrayList<String> names = new ArrayList<>();
        ObservableList<String> nameList;
        
        public Loading() {
            loadGames();
            createScene();
        }
        
        public Loading(int cond) {
            loadGames();
        }
        
        public void createScene() {
            if (!names.isEmpty()) {
                Rectangle background = new Rectangle(0, 0, width, height); // Creates the grey background of the Load Game screen
                background.setFill(Color.GREY);

                Text prompt = new Text(width / 2.25, height / 10, "Select a save file: "); // Creates a Text that prompts the user to select a save file
                prompt.setFill(Color.YELLOW);
                prompt.setFont(Font.font("Times", 12));
                prompt.setScaleX(width * .006);
                prompt.setScaleY(height * .006);

                saveList.setLayoutX(width / 4.25); // Creates a ComboBox that contains the save files
                saveList.setLayoutY(height / 4);
                saveList.setPrefSize(width / 1.9, height / 20);

                TextArea stats = new TextArea(); // Creates a TextArea that shows the loaded Character's game information
                stats.setLayoutX(width / 4);
                stats.setLayoutY(height / 3);
                stats.setPrefSize(width / 2, height / 3.5);
                stats.setFont(Font.font("Times", 16));
                stats.setEditable(false);

                backToTitle.setFont(Font.font("Times", 24)); // Creates a button that sends the user back to the Title Screen
                backToTitle.setLayoutX(width / 3.875);
                backToTitle.setLayoutY(height / 1.5);

                loadGame.setFont(Font.font("Times", 24)); // Creates a button that starts the game once the user selects a save file
                loadGame.setLayoutX(width / 3.6125);
                loadGame.setLayoutY(height / 1.25);
                loadGame.setDisable(true);

                getChildren().addAll(background, prompt, saveList, backToTitle, loadGame, stats);

                saveList.setOnAction(e -> { // Creates the function of the ComboBox that loads the save file that is clicked on
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
                    info += "\nTimes Saved: " + timesSaved;

                    stats.setText(info);

                    loadGame.setDisable(false); // Enables the Load Game button

                    loadGame.setOnAction(f -> { // Creates the function of the button that continues the game
                       getChildren().clear();
                       mp.stop();
                       Gameplay pane = new Gameplay(name, classNum, health, maxHealth, speed, damage, level, xp, cLevel, timesSaved, bossDefeated, x, y);
                       getChildren().add(pane);
                       pane.createScene();
                    });
                });
            }
        }
        public void loadGames() {
            try { // Loads the list of save files in the user's game memory
                FileInputStream list = new FileInputStream("saves\\saves.sav");
                ObjectInputStream save = new ObjectInputStream(list);
                
                names = (ArrayList<String>)save.readObject();
                
                nameList = FXCollections.observableArrayList(names);
                
                saveList.setItems(nameList);
                
                save.close();
            } catch(Exception ex) { // If no saves are present, an error screen appears
                getChildren().clear();
                
                ErrorScreen pane = new ErrorScreen("No save files detected.");
                
                getChildren().add(pane);
                
                pane.backToTitle.setOnAction(e -> {
                   getChildren().clear();
                   TitleScreen title = new TitleScreen();
                   getChildren().add(title);
                });
            }
        }
        
        public void loadFile() {
            String fileName = names.get(nameList.indexOf(saveList.getValue()));
            
            try { // Loads the save file selected and saves the loaded information in separate variables
                File file = new File("saves/" + fileName + ".txt");
                Scanner input = new Scanner(file);
                
                this.name = input.nextLine();
                this.classNum = Integer.parseInt(input.nextLine());
                this.health = Integer.parseInt(input.nextLine());
                this.maxHealth = Integer.parseInt(input.nextLine());
                this.speed = Integer.parseInt(input.nextLine());
                this.damage = Integer.parseInt(input.nextLine());
                this.xp = Integer.parseInt(input.nextLine());
                this.level = Integer.parseInt(input.nextLine());
                this.cLevel = Integer.parseInt(input.nextLine());
                this.timesSaved = Integer.parseInt(input.nextLine());
                this.bossDefeated = Boolean.parseBoolean(input.nextLine());
                this.x = Double.parseDouble(input.nextLine());
                this.y = Double.parseDouble(input.nextLine());
                
                input.close();
            } catch (Exception ex) {
            }
        }
    }
}