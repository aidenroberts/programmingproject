package full.game;

import full.game.TitleScreen.Loading;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Gameplay extends Pane{
    int classNum, health, speed, damage, level, xp, cLevel, timesSaved, maxHealth;
    String name;
    Exploration movement;
    boolean allowMove = true, bossDefeated = false;
    
    public Gameplay(String name, int classNum, int health, int maxHealth, int speed, int damage, int level, int xp, int cLevel, int timesSaved) {
        this.name = name; // Sets the stats of a character if the game was loaded
        this.classNum = classNum;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.xp = xp;
        this.cLevel = cLevel;
        this.timesSaved = timesSaved;
        
        Rectangle background = new Rectangle(0, 0, 500, 500); // Creates the scene of the game
        background.setFill(Color.GREY);
        movement = new Exploration();
        ButtonToStart pane = new ButtonToStart();
        getChildren().addAll(background, movement, pane);
    }
    
    public Gameplay(String name, int classNum, int health, int speed, int damage) {
        this.name = name; // Sets the character's stats if the user created a new character
        this.classNum = classNum;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.damage = damage;
        level = 1;
        xp = 0;
        cLevel = 1;
        this.timesSaved = 0;
        
        Rectangle background = new Rectangle(0, 0, 500, 500); // Creates the scene of the game
        background.setFill(Color.GREY);
        movement = new Exploration();
        ButtonToStart pane = new ButtonToStart();
        getChildren().addAll(background, movement, pane);
    }
    
    public void backToMap() { // Sends the user back to the exploration screen
        getChildren().remove(getChildren().size() - 1);
        allowMove = true;
        createScene();
    }
    
    public void createScene() { // Recreates the map selection screen
        Menu menu = new Menu(); // Creates a menu for saving and quitting a game
        menu.setLayoutX(375);
        menu.setLayoutY(0);
        
        
        this.setOnKeyPressed(e -> {
            if (e.getCode() == e.getCode().X && allowMove) { // Opens the menu if the user presses X
                getChildren().add(menu);
                final int Y1 = 25, Y2 = 75;

                this.setOnKeyPressed(f  -> { 
                    if (f.getCode() == f.getCode().UP || f.getCode() == f.getCode().DOWN) { // Changes the location of the arrow for selecting an option if the user presses up or down if the menu is open
                        if (menu.arrow.getY() == Y1) {
                            menu.arrow.setY(Y2);
                        } else {
                            menu.arrow.setY(Y1);
                        }
                    }

                    if (f.getCode() == f.getCode().ENTER) { // Selects the option the arrow hovers over if the user presses ENTER
                        if (menu.arrow.getY() == Y1) save();
                        else System.exit(0);
                    } else if (f.getCode() == f.getCode().ESCAPE) backToMap(); // Closes the menu if the user presses ESCAPE
                });

                this.requestFocus();
            }
            
            switch (e.getCode()) { // Moves the character based on the arrow key the user presses as long as the menu is not open
                case UP:
                    if (allowMove)
                    movement.moveUp();
                    break;
                case DOWN:
                    if (allowMove)
                    movement.moveDown();
                    break;
                case LEFT:
                    if (allowMove)
                    movement.moveLeft();
                    break;
                case RIGHT:
                    if (allowMove)
                    movement.moveRight();
                    break;
            }
            
        });
        
        this.requestFocus();
    }
    
    public void setXP(int xp) { // Sets the character's xp
        this.xp = xp;
    }
    
    public void setHealth(int health) { // Sets the character's health
        this.health = health;
    }
    
    public void setLevel(int level) { // Sets the character's level
        cLevel = level;
    }
    
    public void encounter() { // Starts a battle if the user encounters a random enemy
        Battle battle = new Battle(health, maxHealth, speed, damage, xp, cLevel);
        getChildren().add(battle);
    }
    
    public void bossBattle() { // Starts a battle if the user runs into the boss
        Battle battle = new Battle(health, maxHealth, speed, damage, xp, cLevel, "The Lemon King", 150, 50, 25);
        getChildren().add(battle);
    }
    
    public void deleteBoss() { // Deletes the boss on the exploration scree n if the user defeats the boss
        bossDefeated = true;
    }
    
    public void save() { // Saves the game
        Loading pane = new Loading(0);
        
        ArrayList<String> names = pane.names;
        
        if (timesSaved == 0) {
            names.add(0, name);
        } else {
            names.add(0, name + "(" + timesSaved + ")");
        }
        
        timesSaved++;
        
        try { // Creates/Edits the list of saves in the user's memory
            FileOutputStream saveFile = new FileOutputStream("saves/saves.sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(names);

            save.close();
        } catch(Exception e) {
            System.out.println("error");
        }
        
            
        try { // Saves the character's information into a text file
            PrintWriter save = new PrintWriter("saves/" + names.get(0) + ".txt");

            save.println(name);
            save.println(classNum);
            save.println(health);
            save.println(maxHealth);
            save.println(speed);
            save.println(damage);
            save.println(xp);
            save.println(level);
            save.println(cLevel);
            save.println(timesSaved);
            
            save.close();
        } catch(Exception e){
        }
    }
    
    public void setStats() { // Sets the stats of the character once the character levels up
        switch (classNum) {
            case 1:
                health = 75 + (int)(75 * (cLevel - 1) * .25);
                maxHealth = 75 + (int)(75 * (cLevel - 1) * .25);
                speed = 75 + (int)(75 * (cLevel - 1) * .25);
                damage = 75 + (int)(75 * (cLevel - 1) * .25);
                break;
            case 2:
                health = 125 + (int)(125 * (cLevel - 1) * .25);
                maxHealth = 125 + (int)(125 * (cLevel - 1) * .25);
                speed = 50 + (int)(50 * (cLevel - 1) * .25);
                damage = 75 + (int)(75 * (cLevel - 1) * .25);
                break;
            case 3:
                health = 75 + (int)(75 * (cLevel - 1) * .25);
                maxHealth = 75 + (int)(75 * (cLevel - 1) * .25);
                speed = 125 + (int)(125 * (cLevel - 1) * .25);
                damage = 50 + (int)(50 * (cLevel - 1) * .25);
                break;
            default:
                health = 50 + (int)(50 * (cLevel - 1) * .25);
                maxHealth = 50 + (int)(50 * (cLevel - 1) * .25);
                speed = 75 + (int)(75 * (cLevel - 1) * .25);
                damage = 125 + (int)(125 * (cLevel - 1) * .25);
                break;
        }
    }
    
    class Battle extends Pane {
        String nameOfEnemy = "", combatFeed = "";
        int health, maxHealth, speed, damage, xp, level, enemyHealth, enemySpeed, enemyDamage;
        Button attack = new Button("Attack"), guard = new Button("Guard"), pass = new Button("Pass");
        TextArea battleText = new TextArea();
        boolean isBoss = false;
        
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> { // Creates a transition between displaying a combat feed and the character's options for attacking
            battleText.setVisible(false);
            attack.setVisible(true);
            guard.setVisible(true);
            pass.setVisible(true);
        })), animation2 = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            battleText.setVisible(true);
            attack.setVisible(false);
            guard.setVisible(false);
            pass.setVisible(false);
        }));
        
        Text cCurrentHealth, currentEnemyHealth;
        
        public Battle(int health, int maxHealth, int speed, int damage, int xp, int level) {
            this.health = health; // Creates the battle if the user encountered a random enemy
            this.maxHealth = maxHealth;
            this.speed = speed;
            this.damage = damage;
            this.xp = xp;
            this.level = level;
            
            createBattleScene();
            generateEnemy();
            startBattle();
        }
        
        public Battle(int health, int maxHealth, int speed, int damage, int xp, int level, String bossName, int bossHealth, int bossSpeed, int bossDamage) {
            this.health = health; // Creates the battle if the user encountered a boss
            this.maxHealth = maxHealth;
            this.speed = speed;
            this.damage = damage;
            this.xp = xp;
            this.level = level;
            
            nameOfEnemy = bossName;
            enemyHealth = bossHealth;
            enemySpeed = bossSpeed;
            enemyDamage = bossDamage;
            isBoss = true;
            
            createBattleScene();
            generateBoss();
            startBattle();
        }
        
        public void generateBoss() { // If the user encountered a boss, the game genereates the boss's sprite
            File enemy = new File("enemies\\Lemon King.png");
            Image enemyPic = new Image(enemy.toURI().toString());
            ImageView ivEnemy = new ImageView(enemyPic);
            ivEnemy.setFitHeight(112.5);
            ivEnemy.setFitWidth(112.5);
            ivEnemy.setLayoutX(312.5);
            getChildren().add(ivEnemy);
            
            Text text = new Text(37.5, 150, nameOfEnemy); // Displays the name of the boss
            text.setFont(Font.font("Times", 12));
            getChildren().add(text);
            
            currentEnemyHealth = new Text(140, 150, Integer.toString(enemyHealth)); // Creates a text that sisplays the name of the enemy's current health
            Text enemyTotalHealth = new Text(175, 150, "/" + Integer.toString(enemyHealth));
            
            currentEnemyHealth.setFont(Font.font("Times", 12)); // Creates a text that displays the highest health the enemy can have
            enemyTotalHealth.setFont(Font.font("Times", 12));
            
            getChildren().addAll(enemyTotalHealth, currentEnemyHealth);
        }
        
        public void generateEnemy() {
            int enemyNum = (int)(Math.random() * 4); // Randomly generates an enemy
            
            if (enemyNum != 1) { // Sets the enemy's sprite if the enemy is not the Strong Lemon
                File enemy = new File("enemies\\New Lemon.png");
                Image enemyPic = new Image(enemy.toURI().toString());
                ImageView ivEnemy = new ImageView(enemyPic);
                ivEnemy.setFitHeight(112.5);
                ivEnemy.setFitWidth(112.5);
                ivEnemy.setLayoutX(312.5);
                getChildren().add(ivEnemy);
            } else {
                File enemy = new File("enemies\\New Lemon Armor.png"); // Sets the enemy's sprite if the enemy is the Strong Lemon
                Image enemyPic = new Image(enemy.toURI().toString());
                ImageView ivEnemy = new ImageView(enemyPic);
                ivEnemy.setFitHeight(112.5);
                ivEnemy.setFitWidth(112.5);
                ivEnemy.setLayoutX(312.5);
                getChildren().add(ivEnemy);
            }
            
            switch (enemyNum) {// Sets the enemy's info
                case 0:
                    nameOfEnemy = "Grunt Lemon";
                    enemyHealth = 50 + (int)(50 * (level - 1) * .25);
                    enemySpeed = 50 + (int)(50 * (level - 1) * .25);
                    enemyDamage = 50 + (int)(50 * (level - 1) * .25);
                    break;
                case 1:
                    nameOfEnemy = "Strong Lemon";
                    enemyHealth = 100 + (int)(100 * (level - 1) * .25);
                    enemySpeed = 50 + (int)(50 * (level - 1) * .25);
                    enemyDamage = 75 + (int)(75 * (level - 1) * .25);
                    break;
                case 2:
                    nameOfEnemy = "Fast Lemon";
                    enemyHealth = 50 + (int)(50 * (level - 1) * .25);
                    enemySpeed = 100 + (int)(100 * (level - 1) * .25);
                    enemyDamage = 75 + (int)(75 * (level - 1) * .25);
                    break;
                case 3:
                    nameOfEnemy = "Potent Lemon";
                    enemyHealth = 50 + (int)(50 * (level - 1) * .25);
                    enemySpeed = 75 + (int)(75 * (level - 1) * .25);
                    enemyDamage = 100 + (int)(100 * (level - 1) * .25);
                    break;
            }
            
            Text text = new Text(37.5, 150, nameOfEnemy); // Creates a text that displays the name of the enemy
            text.setFont(Font.font("Times", 12));
            getChildren().add(text);
            
            currentEnemyHealth = new Text(140, 150, Integer.toString(enemyHealth)); // Creates a text that displays the enemy's current health
            Text enemyTotalHealth = new Text(175, 150, "/" + Integer.toString(enemyHealth)); // Creates a text that displays the highest amount of health the enemy can reach
            
            currentEnemyHealth.setFont(Font.font("Times", 12));
            enemyTotalHealth.setFont(Font.font("Times", 12));
            
            getChildren().addAll(enemyTotalHealth, currentEnemyHealth);
        }
        
        public void createBattleScene() {
            
            File battle = new File("BattleGround.png"); // Creates the back of the Battle Screen
            Image battleField = new Image(battle.toURI().toString());
            ImageView background = new ImageView(battleField);
            
            background.setFitHeight(500);
            background.setFitWidth(500);
            
            battleText.setLayoutX(10); // Creates a TextArea that acts as the combat feed
            battleText.setLayoutY(387.5);
            battleText.setPrefSize(480, 100);
            battleText.setFont(Font.font("Times", 12));
            battleText.setEditable(false);
            
            
            attack.setLayoutX(50); // Creates a button that allows the user to attack the enemy
            attack.setLayoutY(412.5);
            attack.setPrefWidth(100);
            attack.setFont(Font.font("Times", 16));
            attack.setVisible(false);
            
            guard.setLayoutX(200); // Creates a button that allows the user to reduce the amount of damage the enemy deals by 10% and pass a turn
            guard.setLayoutY(412.5);
            guard.setPrefWidth(100);
            guard.setFont(Font.font("Times", 16));
            guard.setVisible(false);
            
            pass.setLayoutX(350); // Creates a button that allows the user to pass a turn
            pass.setLayoutY(412.5);
            pass.setPrefWidth(100);
            pass.setFont(Font.font("Times", 16));
            pass.setVisible(false);
            
            File character;
            Image sprite;
            ImageView seeCharacter;
            
            switch (classNum) { // Sets the sprite of the character
                case 1:
                    character = new File("characters//Warrior Character.png");
                    sprite = new Image(character.toURI().toString());
                    seeCharacter = new ImageView(sprite);
                    break;
                case 2:
                    character = new File("characters//Tank Character.png");
                    sprite = new Image(character.toURI().toString());
                    seeCharacter = new ImageView(sprite);
                    break;
                case 3:
                    character = new File("characters//Speedster Character.png");
                    sprite = new Image(character.toURI().toString());
                    seeCharacter = new ImageView(sprite);
                    break;
                default:
                    character = new File("characters//Glass Cannon Back 1.png");
                    sprite = new Image(character.toURI().toString());
                    seeCharacter = new ImageView(sprite);
                    break;
            }
            
            seeCharacter.setLayoutX(75);
            seeCharacter.setLayoutY(237.5);
            seeCharacter.setFitHeight(112.5);
            seeCharacter.setFitWidth(112.5);
            
            Text cName = new Text(290, 295, name); // Creates a text that displays the character's name
            cName.setFont(Font.font("Times", 12));
            
            cCurrentHealth = new Text(390, 295, Integer.toString(health)); // Creates a text that displays the character's current health
            cCurrentHealth.setFont(Font.font("Times", 12));
            
            Text cMaxHealth = new Text(425, 295, "/" + Integer.toString(maxHealth)); // Creates a text that displays the highest amount of health that the user can have
            cMaxHealth.setFont(Font.font("Times", 12));
            
            getChildren().addAll(background, battleText, attack, guard, pass, seeCharacter, cName, cCurrentHealth, cMaxHealth);
        }
        
        public void startBattle() { // Starts the battle
            battleText.setText(nameOfEnemy + " wishes to fight!"); //Sets the text of the combat feed
            
            animation.play();
            
            attack.setOnAction(e -> { // Creates the function of the button that attacks the enemy when pressed
                battleText.setText("");
               if (speed > enemySpeed || speed == enemySpeed) { // If the character's speed is greater than or equal to the enemy's speed, the character attacks first. If not, the enemy atack first.
                   animation2.play();
                   playerAttack();
                   
                   if (enemyHealth != 0)
                   enemyAttack(enemyDamage);
               } else {
                   animation2.play();
                   enemyAttack(enemyDamage);
                   
                   if (health != 0)
                   playerAttack();
               }
               combatFeed = "";
            });
            
            guard.setOnAction(e -> { // Creates the function of the button that guards the user from an attack
                battleText.setText("");
                animation2.play();
                battleText.setText("You guarded.\n");
                enemyAttack((int)(enemyDamage * .9));
                combatFeed = "";
            });
            
            pass.setOnAction(e -> { // Creates the function of the button that passes a turn
                battleText.setText("");
                animation2.play();
                battleText.setText("You passed for a turn.\n");
                enemyAttack(enemyDamage);
                combatFeed = "";
            });
        }
        
        public void playerAttack() { // Deals damage to the enemy based on the character's damage
            enemyHealth -= damage;
            combatFeed += "You deal " + damage + " damage to your opponent.\n";
            battleText.setText(combatFeed);
            
            if (enemyHealth < 0) enemyHealth = 0; // If the enemy's health is less than 0, the enemy's health is set to 0
            
            currentEnemyHealth.setText(Integer.toString(enemyHealth));
            
            checkForWin(); // Checks to see if the enemy died
        }
        
        public void enemyAttack(int enemyDamage) { // The character takes damage from the enemy based on the enemy's damage
            health -= enemyDamage;
            combatFeed += "Your opponent dealt " + enemyDamage + " damage to you.\n";
            battleText.setText(combatFeed);
            
            if(health < 0) health = 0; // If the character's health is less than 0, the character's health is set to 0
            
            cCurrentHealth.setText(Integer.toString(health));
            
            checkForLoss(); // Checks to see if the character died
        }
        
        public void checkForWin() { // Sees if the enemy's health is equal to 0
            if (enemyHealth == 0) { // If the enemy died, the character wins and is prompted to return to the map
                youWin();
            } else {
                animation.setDelay(Duration.millis(1000));
                animation.play();
            }
        }
        
        public void checkForLoss() { // Sees if the chracter's health is equal to 0
            if (health == 0) { // If the character died, the user loses and is prompted to exit the game
                youLose();
            } else {
                animation.setDelay(Duration.millis(1000));
                animation.play();
            }
        }
        
        public void youLose() {
            combatFeed += "You lost the fight.\nGame Over"; // Tells the user that they lost
            battleText.setText(combatFeed);
            
            getChildren().removeAll(attack, guard, pass);
            
            Button exit = new Button("Exit Game"); // Creates a button that exits the game
            exit.setFont(Font.font("Times", 16));
            exit.setPrefWidth(100);
            exit.setLayoutX(200);
            exit.setLayoutY(412.5);
            exit.setVisible(false);
            
            getChildren().add(exit);
            
            Timeline ani = new Timeline(new KeyFrame(Duration.millis(3000), e -> { // Removes the combat feed and adds the Exit Game button
                getChildren().remove(battleText);
                exit.setVisible(true);
            }));
            
            ani.play();
            
            exit.setOnAction(e -> System.exit(0)); // Creates the function of the Exit Game button
        }
        
        public void youWin() {
            combatFeed += "You won the fight!\n"; // Tells the user they won the fight
            battleText.setText(combatFeed);
            
            if (!isBoss) { // Gives a certain amount of xp based on if the enemy was a boss or not
                int xpGain = 25 + (int)(25 * (level - 1) * .25);
                
                combatFeed += "You gained " + xpGain + " xp.\n";
                battleText.setText(combatFeed);
                
                xp += xpGain;
                
                setHealth(health);
                
                checkForLevelUp();
                
                cLevel = level;
                setXP(xp);
            } else {
                int xpGain = 100 + (int)(100 * (level - 1) * .25);
                
                combatFeed += "You gained " + xpGain + " xp.\n";
                battleText.setText(combatFeed);
                
                xp += xpGain;
                
                checkForLevelUp();
                
                cLevel = level;
                setXP(xp);
            }
            
            
            getChildren().removeAll(attack, guard, pass);
            
            Button backToMap = new Button("Back to the Map"); // Creates a button that sends the user back to the map
            backToMap.setFont(Font.font("Times", 16));
            backToMap.setPrefWidth(200);
            backToMap.setLayoutX(150);
            backToMap.setLayoutY(412.5);
            backToMap.setVisible(false);
            
            getChildren().add(backToMap);
            
            Timeline ani = new Timeline(new KeyFrame(Duration.millis(3000), e -> { // Removes the combat feed and adds the button that returns the user to the map screen
                battleText.setVisible(false);
                backToMap.setVisible(true);
            }));
            
            ani.play();
            
            backToMap.setOnAction(e -> { // Creates the function of the button that returns the user to the map screen
                backToMap();
            });
        }
        
        public void checkForLevelUp() {
            while (xp >= 100 + (int)(100 * (level - 1) * .25)) { // If the user's xp surpasses the required amount, the user levels up and increases their stats
                xp -= 100 + (int)(100 * (level - 1) * .25);
                level++;
                
                setLevel(level);
                setStats();
                
                combatFeed += "You leveled up!\nYou are now level " + level + ".";
                battleText.setText(combatFeed);
            }
        }
    }
    
    class Menu extends Pane {
        Text arrow = new Text(5, 25, ">");
        
        public Menu() {
            createMenu();
        }
        
        public void createMenu() { // Creates the menu that is access on the map
            Rectangle background1 = new Rectangle(0, 0, 125, 200);
            background1.setFill(Color.BLACK);
            
            Rectangle background2 = new Rectangle(5, 5, 115, 190);
            background2.setFill(Color.DARKGREY);
            
            
            arrow.setFont(Font.font("Times", 24));
            arrow.setFill(Color.WHITE);
            
            Text save = new Text(62.5, 25, "Save");
            save.setFont(Font.font("Times", 24));
            save.setFill(Color.WHITE);
            
            Text quit = new Text(62.5, 75, "Quit");
            quit.setFont(Font.font("Times", 24));
            quit.setFill(Color.WHITE);
            
            getChildren().addAll(background1, background2, arrow, save, quit);
            
            
        }
    }
    
    public class Exploration extends Pane {
        ImageView ivModel;
        ImageView ivBoss;
        int upN = 1, downN = 1, leftN = 1, rightN = 1;
        public Exploration() { // Creates the user's sprite that moves and the sprite of the boss that, if run into, triggers a boss fight
            File cha;
            Image model;
            
            
            switch (classNum) {
                case 1:
                    cha = new File("characters\\Warrior Character.png");
                    model = new Image(cha.toURI().toString());
                    ivModel = new ImageView(model);
                    break;
                case 2:
                    cha = new File("characters\\Tank Character.png");
                    model = new Image(cha.toURI().toString());
                    ivModel = new ImageView(model);
                    break;
                case 3:
                    cha = new File("characters\\Speedster Character.png");
                    model = new Image(cha.toURI().toString());
                    ivModel = new ImageView(model);
                    break;
                default:
                    cha = new File("characters\\Glass Cannon Character.png");
                    model = new Image(cha.toURI().toString());
                    ivModel = new ImageView(model);
                    break;
            }
            
            ivModel.setFitHeight(50);
            ivModel.setFitWidth(50);
            ivModel.setX(225);
            ivModel.setY(225);
            
            File bossFile = new File("enemies\\Lemon King.png");
            Image boss = new Image(bossFile.toURI().toString());
            ivBoss = new ImageView(boss);
            
            ivBoss.setFitHeight(100);
            ivBoss.setFitWidth(100);
            ivBoss.setLayoutX(200);
            ivBoss.setLayoutY(0);
            
            getChildren().addAll(ivModel, ivBoss);
        }
        
        /*
            The next 4 methods move the character in 4 directions based on key input (up, down, left, right)
        */
        
        public void moveRight() {
            if (bossDefeated) getChildren().remove(movement.ivBoss);
            switch(leftN) {
                case 1:
                    ivModel.setX(ivModel.getX() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    upN = 1;
                    downN = 1;
                    leftN = 1;
                    rightN = 2;
                    break;
                case 2:
                    ivModel.setX(ivModel.getX() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    rightN = 3;
                    break;
                case 3:
                    ivModel.setX(ivModel.getX() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    rightN = 1;
                    break;
            }
            if (!bossDefeated && (ivModel.getX() >= ivBoss.getX() && ivModel.getX() <= ivBoss.getX() + 400) && (ivModel.getY() >= ivBoss.getY() && ivModel.getY() <= ivBoss.getY() + 100)) {
                deleteBoss();
                bossBattle();
            } else if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
        
        public void moveLeft() {
            if (bossDefeated) getChildren().remove(movement.ivBoss);
            switch(leftN) {
                case 1:
                    ivModel.setX(ivModel.getX() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    upN = 1;
                    downN = 1;
                    leftN = 2;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setX(ivModel.getX() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    leftN = 3;
                    break;
                case 3:
                    ivModel.setX(ivModel.getX() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    leftN = 1;
                    break;
            }
            if (!bossDefeated && (ivModel.getX() >= ivBoss.getX() && ivModel.getX() <= ivBoss.getX() + 400) && (ivModel.getY() >= ivBoss.getY() && ivModel.getY() <= ivBoss.getY() + 100)) {
                deleteBoss();
                bossBattle();
            } else if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
        
        public void moveUp() {
            if (bossDefeated) getChildren().remove(movement.ivBoss);
            switch (upN) {
                case 1:
                    ivModel.setY(ivModel.getY() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 2.png").toURI().toString()));
                    upN = 2;
                    downN = 1;
                    leftN = 1;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setY(ivModel.getY() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 3.png").toURI().toString()));
                    upN = 3;
                    break;
                case 3:
                    ivModel.setY(ivModel.getY() - 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 1.png").toURI().toString()));
                    upN = 1;
                    break;
            }
            if (!bossDefeated && (ivModel.getX() >= ivBoss.getX() && ivModel.getX() <= ivBoss.getX() + 400) && (ivModel.getY() >= ivBoss.getY() && ivModel.getY() <= ivBoss.getY() + 100)) {
                deleteBoss();
                bossBattle();
            }
        }
        
        public void moveDown() {
            if (bossDefeated) getChildren().remove(movement.ivBoss);
            switch (upN) {
                case 1:
                    ivModel.setY(ivModel.getY() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    upN = 1;
                    downN = 2;
                    leftN = 1;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setY(ivModel.getY() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    downN = 3;
                    break;
                case 3:
                    ivModel.setY(ivModel.getY() + 2.5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    downN = 1;
                    break;
            }
            
            if (!bossDefeated && (ivModel.getX() >= ivBoss.getX() && ivModel.getX() <= ivBoss.getX() + 400) && (ivModel.getY() >= ivBoss.getY() && ivModel.getY() <= ivBoss.getY() + 100)) {
                deleteBoss();
                bossBattle();
            } else if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
    }
    
    public class ButtonToStart extends Pane {
        public ButtonToStart() {
            Button start = new Button("Click Here");
            start.setPrefWidth(100);
            start.setPrefHeight(100);
            start.setLayoutX(200);
            start.setLayoutY(200);
            start.setFont(Font.font("Times", 16));
            
            getChildren().add(start);
            
            start.setOnAction(e -> backToMap());
        }
    }
}