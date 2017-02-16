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
    boolean allowMove = true;
    
    public Gameplay(String name, int classNum, int health, int maxHealth, int speed, int damage, int level, int xp, int cLevel, int timesSaved) {
        this.name = name;
        this.classNum = classNum;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.xp = xp;
        this.cLevel = cLevel;
        this.timesSaved = timesSaved;
        
        Rectangle background = new Rectangle(0, 0, 1000, 1000);
        background.setFill(Color.GREY);
        movement = new Exploration();
        getChildren().addAll(background, movement);
    }
    
    public Gameplay(String name, int classNum, int health, int speed, int damage) {
        this.name = name;
        this.classNum = classNum;
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.damage = damage;
        level = 1;
        xp = 0;
        cLevel = 1;
        this.timesSaved = 0;
        
        Rectangle background = new Rectangle(0, 0, 1000, 1000);
        background.setFill(Color.GREY);
        movement = new Exploration();
        ButtonToStart pane = new ButtonToStart();
        getChildren().addAll(background, movement, pane);
    }
    
    public void backToMap() {
        getChildren().remove(getChildren().size() - 1);
        allowMove = true;
        createScene();
    }
    
    public void createScene() {
        
        Menu menu = new Menu();
        menu.setLayoutX(750);
        menu.setLayoutY(0);
        
        
        this.setOnKeyPressed(e -> {
            if (e.getCode() == e.getCode().X && allowMove) {
                getChildren().add(menu);
                final int Y1 = 50, Y2 = 150;

                this.setOnKeyPressed(f  -> {
                    if (f.getCode() == f.getCode().UP || f.getCode() == f.getCode().DOWN) {
                        if (menu.arrow.getY() == Y1) {
                            menu.arrow.setY(Y2);
                        } else {
                            menu.arrow.setY(Y1);
                        }
                    }

                    if (f.getCode() == f.getCode().ENTER) {
                        if (menu.arrow.getY() == Y1) save();
                        else System.exit(0);
                    } else if (f.getCode() == f.getCode().ESCAPE) backToMap();
                });

                this.requestFocus();
            }
            
            switch (e.getCode()) {
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
    
    public void setXP(int xp) {
        this.xp = xp;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void encounter() {
        Battle battle = new Battle(health, maxHealth, speed, damage, xp, level);
        getChildren().add(battle);
    }
    
    public void save() {
        Loading pane = new Loading(0);
        
        ArrayList<String> names = pane.names;
        
        if (timesSaved == 0) {
            names.add(0, name);
        } else {
            names.add(0, name + "(" + timesSaved + ")");
        }
        
        timesSaved++;
        
        try{
                FileOutputStream saveFile = new FileOutputStream("E:/Full Game/saves/saves.sav");
                ObjectOutputStream save = new ObjectOutputStream(saveFile);

                save.writeObject(names);
                
                save.close();
        } catch(Exception e) {
            System.out.println("error");
        }
        
            
        try{
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
            System.out.println("error");
        }
    }
    
    public void setStats() {
        switch (classNum) {
            case 1:
                health = 75 + (int)(75 * (level - 1) * .25);
                maxHealth = 75 + (int)(75 * (level - 1) * .25);
                speed = 75 + (int)(75 * (level - 1) * .25);
                damage = 75 + (int)(75 * (level - 1) * .25);
                break;
            case 2:
                health = 125 + (int)(125 * (level - 1) * .25);
                maxHealth = 125 + (int)(125 * (level - 1) * .25);
                speed = 50 + (int)(50 * (level - 1) * .25);
                damage = 75 + (int)(75 * (level - 1) * .25);
                break;
            case 3:
                health = 75 + (int)(75 * (level - 1) * .25);
                maxHealth = 75 + (int)(75 * (level - 1) * .25);
                speed = 125 + (int)(125 * (level - 1) * .25);
                damage = 50 + (int)(50 * (level - 1) * .25);
                break;
            default:
                health = 50 + (int)(50 * (level - 1) * .25);
                maxHealth = 50 + (int)(50 * (level - 1) * .25);
                speed = 75 + (int)(75 * (level - 1) * .25);
                damage = 125 + (int)(125 * (level - 1) * .25);
                break;
        }
    }
    
    class Battle extends Pane {
        String nameOfEnemy = "", combatFeed = "";
        int health, maxHealth, speed, damage, xp, level, enemyHealth, enemySpeed, enemyDamage;
        Button attack = new Button("Attack"), guard = new Button("Guard"), pass = new Button("Pass");
        TextArea battleText = new TextArea();
        boolean isBoss = false;
        
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
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
            this.health = health;
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
            this.health = health;
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
            
            File enemy = new File("enemies\\Lemon King.png");
            Image enemyPic = new Image(enemy.toURI().toString());
            ImageView ivEnemy = new ImageView(enemyPic);
            ivEnemy.setFitHeight(300);
            ivEnemy.setFitWidth(300);
            ivEnemy.setLayoutX(350);
            ivEnemy.setLayoutY(20);
            getChildren().add(ivEnemy);
            
            createBattleScene();
            startBattle();
        }
        
        public void generateEnemy() {
            int enemyNum = (int)(Math.random() * 4);
            
            if (enemyNum != 1) {
                File enemy = new File("enemies\\New Lemon.png");
                Image enemyPic = new Image(enemy.toURI().toString());
                ImageView ivEnemy = new ImageView(enemyPic);
                ivEnemy.setFitHeight(225);
                ivEnemy.setFitWidth(225);
                ivEnemy.setLayoutX(625);
                getChildren().add(ivEnemy);
            } else {
                File enemy = new File("enemies\\New Lemon Armor.png");
                Image enemyPic = new Image(enemy.toURI().toString());
                ImageView ivEnemy = new ImageView(enemyPic);
                ivEnemy.setFitHeight(225);
                ivEnemy.setFitWidth(225);
                ivEnemy.setLayoutX(625);
                getChildren().add(ivEnemy);
            }
            
            switch (enemyNum) {
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
            
            Text text = new Text(75, 300, nameOfEnemy);
            text.setFont(Font.font("Times", 32));
            getChildren().add(text);
            
            currentEnemyHealth = new Text(280, 300, Integer.toString(enemyHealth));
            Text enemyTotalHealth = new Text(350, 300, "/" + Integer.toString(enemyHealth));
            
            currentEnemyHealth.setFont(Font.font("Times", 32));
            enemyTotalHealth.setFont(Font.font("Times", 32));
            
            getChildren().addAll(enemyTotalHealth, currentEnemyHealth);
        }
        
        public void createBattleScene() {
            
            File battle = new File("BattleGround.png");
            Image battleField = new Image(battle.toURI().toString());
            ImageView background = new ImageView(battleField);
            
            background.setFitHeight(1000);
            background.setFitWidth(1000);
            
            battleText.setLayoutX(20);
            battleText.setLayoutY(775);
            battleText.setPrefSize(960, 200);
            battleText.setFont(Font.font("Times", 24));
            battleText.setEditable(false);
            
            
            attack.setLayoutX(100);
            attack.setLayoutY(825);
            attack.setPrefWidth(200);
            attack.setFont(Font.font("Times", 32));
            attack.setVisible(false);
            
            guard.setLayoutX(400);
            guard.setLayoutY(825);
            guard.setPrefWidth(200);
            guard.setFont(Font.font("Times", 32));
            guard.setVisible(false);
            
            pass.setLayoutX(700);
            pass.setLayoutY(825);
            pass.setPrefWidth(200);
            pass.setFont(Font.font("Times", 32));
            pass.setVisible(false);
            
            File character;
            Image sprite;
            ImageView seeCharacter;
            
            switch (classNum) {
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
            
            seeCharacter.setLayoutX(150);
            seeCharacter.setLayoutY(475);
            seeCharacter.setFitHeight(225);
            seeCharacter.setFitWidth(225);
            
            Text cName = new Text(580, 590, name);
            cName.setFont(Font.font("Times", 32));
            
            cCurrentHealth = new Text(780, 590, Integer.toString(health));
            cCurrentHealth.setFont(Font.font("Times", 32));
            
            Text cMaxHealth = new Text(850, 590, "/" + Integer.toString(maxHealth));
            cMaxHealth.setFont(Font.font("Times", 32));
            
            getChildren().addAll(background, battleText, attack, guard, pass, seeCharacter, cName, cCurrentHealth, cMaxHealth);
        }
        
        public void startBattle() {
            battleText.setText(nameOfEnemy + " wishes to fight!");
            
            animation.play();
            
            attack.setOnAction(e -> {
                battleText.setText("");
               if (speed > enemySpeed || speed == enemySpeed) {
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
            });
            
            guard.setOnAction(e -> {
                battleText.setText("");
                animation2.play();
                battleText.setText("You guarded.\n");
                enemyAttack((int)(enemyDamage * .9));
            });
            
            pass.setOnAction(e -> {
                battleText.setText("");
                animation2.play();
                battleText.setText("You passed for a turn.\n");
                enemyAttack(enemyDamage);
            });
        }
        
        public void playerAttack() {
            enemyHealth -= damage;
            combatFeed += "You deal " + damage + " damage to your opponent.\n";
            battleText.setText(combatFeed);
            
            if (enemyHealth < 0) enemyHealth = 0;
            
            currentEnemyHealth.setText(Integer.toString(enemyHealth));
            
            checkForWin();
        }
        
        public void enemyAttack(int enemyDamage) {
            health -= enemyDamage;
            combatFeed += "Your opponent dealt " + enemyDamage + " damage to you.\n";
            battleText.setText(combatFeed);
            
            if(health < 0) health = 0;
            
            cCurrentHealth.setText(Integer.toString(health));
            
            checkForLoss();
        }
        
        public void checkForWin() {
            if (enemyHealth == 0) {
                youWin();
            } else {
                animation.setDelay(Duration.millis(1000));
                animation.play();
            }
        }
        
        public void checkForLoss() {
            if (health == 0) {
                youLose();
            } else {
                animation.setDelay(Duration.millis(1000));
                animation.play();
            }
        }
        
        public void youLose() {
            combatFeed += "You lost the fight.\nGame Over";
            battleText.setText(combatFeed);
            
            getChildren().removeAll(attack, guard, pass);
            
            Button exit = new Button("Exit Game");
            exit.setFont(Font.font("Times", 32));
            exit.setPrefWidth(200);
            exit.setLayoutX(400);
            exit.setLayoutY(825);
            exit.setVisible(false);
            
            getChildren().add(exit);
            
            Timeline ani = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
                getChildren().remove(battleText);
                exit.setVisible(true);
            }));
            
            ani.play();
            
            exit.setOnAction(e -> System.exit(0));
        }
        
        public void youWin() {
            combatFeed += "You won the fight!\n";
            battleText.setText(combatFeed);
            
            if (!isBoss) {
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
            
            Button backToMap = new Button("Back to the Map");
            backToMap.setFont(Font.font("Times", 32));
            backToMap.setPrefWidth(400);
            backToMap.setLayoutX(300);
            backToMap.setLayoutY(825);
            backToMap.setVisible(false);
            
            getChildren().add(backToMap);
            
            Timeline ani = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
                battleText.setVisible(false);
                backToMap.setVisible(true);
            }));
            
            ani.play();
            
            backToMap.setOnAction(e -> {
                backToMap();
            });
        }
        
        public void checkForLevelUp() {
            while (xp >= 100 + (int)(100 * (level - 1) * .25)) {
                xp -= 100 + (int)(100 * (level - 1) * .25);
                level++;
                setStats();
                
                combatFeed += "You leveled up!\nYou are now level " + level + ".";
                battleText.setText(combatFeed);
            }
        }
    }
    
    class Menu extends Pane {
        Text arrow = new Text(10, 50, ">");
        
        public Menu() {
            createMenu();
        }
        
        public void createMenu() {
            Rectangle background1 = new Rectangle(0, 0, 250, 400);
            background1.setFill(Color.BLACK);
            
            Rectangle background2 = new Rectangle(10, 10, 230, 380);
            background2.setFill(Color.DARKGREY);
            
            
            arrow.setFont(Font.font("Times", 48));
            arrow.setFill(Color.WHITE);
            
            Text save = new Text(125, 50, "Save");
            save.setFont(Font.font("Times", 48));
            save.setFill(Color.WHITE);
            
            Text quit = new Text(125, 150, "Quit");
            quit.setFont(Font.font("Times", 48));
            quit.setFill(Color.WHITE);
            
            getChildren().addAll(background1, background2, arrow, save, quit);
            
            
        }
    }
    
    public class Exploration extends Pane {
        ImageView ivModel;
        int upN = 1, downN = 1, leftN = 1, rightN = 1;
        public Exploration() {
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
            
            ivModel.setFitHeight(100);
            ivModel.setFitWidth(100);
            ivModel.setX(450);
            ivModel.setY(450);
            
            getChildren().add(ivModel);
        }
        

        
        public void moveRight() {
            switch(leftN) {
                case 1:
                    ivModel.setX(ivModel.getX() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    upN = 1;
                    downN = 1;
                    leftN = 1;
                    rightN = 2;
                    break;
                case 2:
                    ivModel.setX(ivModel.getX() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    rightN = 3;
                    break;
                case 3:
                    ivModel.setX(ivModel.getX() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side.png").toURI().toString()));
                    rightN = 1;
                    break;
            }
            if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
        
        public void moveLeft() {
            ivModel.setX(ivModel.getX() - 5);
            ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
            switch(leftN) {
                case 1:
                    ivModel.setX(ivModel.getX() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    upN = 1;
                    downN = 1;
                    leftN = 2;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setX(ivModel.getX() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    leftN = 3;
                    break;
                case 3:
                    ivModel.setX(ivModel.getX() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character side clone.png").toURI().toString()));
                    leftN = 1;
                    break;
            }
            if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
        
        public void moveUp() {
            switch (upN) {
                case 1:
                    ivModel.setY(ivModel.getY() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 2.png").toURI().toString()));
                    upN = 2;
                    downN = 1;
                    leftN = 1;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setY(ivModel.getY() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 3.png").toURI().toString()));
                    upN = 3;
                    break;
                case 3:
                    ivModel.setY(ivModel.getY() - 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Back 1.png").toURI().toString()));
                    upN = 1;
                    break;
            }
            if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
        
        public void moveDown() {
            switch (upN) {
                case 1:
                    ivModel.setY(ivModel.getY() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    upN = 1;
                    downN = 2;
                    leftN = 1;
                    rightN = 1;
                    break;
                case 2:
                    ivModel.setY(ivModel.getY() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    downN = 3;
                    break;
                case 3:
                    ivModel.setY(ivModel.getY() + 5);
                    ivModel.setImage(new Image(new File("characters\\Glass Cannon Character.png").toURI().toString()));
                    downN = 1;
                    break;
            }
            if ((int)(Math.random() * 25) == 1) {
                allowMove = false;
                encounter();
            }
        }
    }
    
    public class ButtonToStart extends Pane {
        public ButtonToStart() {
            Button start = new Button("Click Here");
            start.setPrefWidth(200);
            start.setPrefHeight(200);
            start.setLayoutX(400);
            start.setLayoutY(400);
            start.setFont(Font.font("Times", 32));
            
            getChildren().add(start);
            
            start.setOnAction(e -> backToMap());
        }
    }
}