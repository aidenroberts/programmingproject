package full.game;

import full.game.TitleScreen.Loading;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Gameplay extends Pane{
    int classNum, health, speed, damage, level, xp, cLevel;
    String name;
    
    public Gameplay(String name, int classNum, int health, int speed, int damage, int level, int xp, int cLevel) {
        this.name = name;
        this.classNum = classNum;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.xp = xp;
        this.cLevel = cLevel;
    }
    
    public Gameplay(String name, int classNum, int health, int speed, int damage) {
        this.name = name;
        this.classNum = classNum;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        level = 1;
        xp = 0;
        cLevel = 1;
        
        Battle battle = new Battle(this.health, this.speed, this.damage, xp, cLevel);
        getChildren().add(battle);
    }
    
    public void backToMap() {
        getChildren().clear();
        createScene();
    }
    
    public void createScene() {
        Rectangle background = new Rectangle(0, 0, 1000, 1000);
        background.setFill(Color.GREY);
        getChildren().add(background);
        
        Text save = new Text(400, 500, "Save? (Y/N)");
        save.setFont(Font.font("Times", 32));
        save.setFill(Color.YELLOW);
        save.setVisible(false);
        
        getChildren().add(save);
                
        save.setOnKeyPressed(e -> {
            if (e.getCode() == e.getCode().X) {
                save.setVisible(true);
                
                this.setOnKeyPressed(f -> {
                    if (e.getCode() == e.getCode().Y) {
                        save();
                        System.out.println("Saved.");
                    } else if (e.getCode() == e.getCode().N) {
                        save.setVisible(false);
                    }
                });
            }
        });
    }
    
    public void setXP(int xp) {
        this.xp = xp;
    }
    
    public void save() {
        Loading pane = new Loading();
        
        ArrayList<String> names = pane.names;
        
        names.add(0, name);
        
        try{
                FileOutputStream saveFile=new FileOutputStream("saves\\SaveList.sav");
                ObjectOutputStream save = new ObjectOutputStream(saveFile);

                save.writeObject(names);
                
                save.close();
        } catch(Exception e) {
        }
        
            
        try{
            FileOutputStream saveFile=new FileOutputStream("saves\\" + name + ".sav");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(classNum);
            save.writeObject(health);
            save.writeObject(speed);
            save.writeObject(damage);
            save.writeObject(xp);
            save.writeObject(level);
            save.writeObject(cLevel);

            save.close();
        } catch(Exception e){
        }
    }
    class Battle extends Pane {
        String nameOfEnemy = "", combatFeed = "";
        int health, speed, damage, xp, level, enemyHealth, enemySpeed, enemyDamage, xpGained;
        Button attack = new Button("Attack"), guard = new Button("Guard"), pass = new Button("Pass");
        TextArea battleText = new TextArea();
        Rectangle background = new Rectangle(0, 0, 1000, 1000);
        boolean isBoss = false;
        
        public Battle(int health, int speed, int damage, int xp, int level) {
            this.health = health;
            this.speed = speed;
            this.damage = damage;
            this.xp = xp;
            this.level = level;
            
            createBattleScene();
            generateEnemy();
            startBattle();
        }
        
        public Battle(int health, int speed, int damage, int xp, int level, String bossName, int bossHealth, int bossSpeed, int bossDamage, int xpGained) {
            this.health = health;
            this.speed = speed;
            this.damage = damage;
            this.xp = xp;
            this.level = level;
            
            nameOfEnemy = bossName;
            enemyHealth = bossHealth;
            enemySpeed = bossSpeed;
            enemyDamage = bossDamage;
            this.xpGained = xpGained;
            isBoss = true;
            
            createBattleScene();
            startBattle();
        }
        
        public void generateEnemy() {
            int enemyNum = (int)(Math.random() * 4);
            
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
        }
        
        public void createBattleScene() {
            
            background.setFill(Color.GREY);
            
            
            battleText.setLayoutX(250);
            battleText.setLayoutY(400);
            battleText.setPrefSize(500, 300);
            battleText.setFont(Font.font("Times", 24));
            battleText.setEditable(false);
            
            
            attack.setLayoutX(100);
            attack.setLayoutY(800);
            attack.setPrefWidth(200);
            attack.setFont(Font.font("Times", 32));
            
            guard.setLayoutX(400);
            guard.setLayoutY(800);
            guard.setPrefWidth(200);
            guard.setFont(Font.font("Times", 32));
            
            pass.setLayoutX(700);
            pass.setLayoutY(800);
            pass.setPrefWidth(200);
            pass.setFont(Font.font("Times", 32));
            
            getChildren().addAll(background, battleText, attack, guard, pass);
        }
        
        public void startBattle() {
            combatFeed += nameOfEnemy + " wishes to fight!";
            battleText.setText(combatFeed);
            
            attack.setOnAction(e -> {
               if (speed > enemySpeed) {
                   playerAttack();
                   
                   if (enemyHealth != 0)
                   enemyAttack(enemyDamage);
               } else {
                   enemyAttack(enemyDamage);
                   
                   if (health != 0)
                   playerAttack();
               }
            });
            
            guard.setOnAction(e -> {
                combatFeed += "\nYou guarded.";
                battleText.setText(combatFeed);
                enemyAttack((int)(enemyDamage * .9));
            });
            
            pass.setOnAction(e -> {
                combatFeed += "You passed for a turn.";
                battleText.setText(combatFeed);
                enemyAttack(enemyDamage);
            });
        }
        
        public void playerAttack() {
            enemyHealth -= damage;
            combatFeed += "\nYou deal " + damage + " damage to your opponent.";
            battleText.setText(combatFeed);
            
            if (enemyHealth < 0) enemyHealth = 0;
            
            checkForWin();
        }
        
        public void enemyAttack(int enemyDamage) {
            health -= enemyDamage;
            combatFeed += "\nYour opponent dealt " + enemyDamage + " damage to you.";
            battleText.setText(combatFeed);
            
            if(health < 0) health = 0;
            
            checkForLoss();
        }
        
        public void checkForWin() {
            if (enemyHealth == 0) {
                youWin();
            }
        }
        
        public void checkForLoss() {
            if (health == 0) {
                youLose();
            }
        }
        
        public void youLose() {
            combatFeed += "\nYou lost the fight.\nGame Over";
            battleText.setText(combatFeed);
            
            getChildren().removeAll(attack, guard, pass);
            
            Button exit = new Button("Exit Game");
            exit.setFont(Font.font("Times", 32));
            exit.setPrefWidth(200);
            exit.setLayoutX(400);
            exit.setLayoutY(800);
            
            getChildren().add(exit);
            
            exit.setOnAction(e -> System.exit(0));
        }
        
        public void youWin() {
            combatFeed += "\nYou won the fight!";
            battleText.setText(combatFeed);
            
            if (!isBoss) {
                int xpGain = 25 + (int)(25 * (level - 1) * .25);
                
                combatFeed += "\nYou gained " + xpGain + " xp.";
                battleText.setText(combatFeed);
                
                xp += xpGain;
                
                checkForLevelUp();
                
                cLevel = level;
                setXP(xp);
            } else {
                int xpGain = 100 + (int)(100 * (level - 1) * .25);
                
                combatFeed += "\nYou gained " + xpGain + " xp.";
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
            backToMap.setLayoutY(800);
            
            getChildren().add(backToMap);
            
            backToMap.setOnAction(e -> backToMap());
        }
        
        public void checkForLevelUp() {
            while (xp >= 100 + (int)(100 * (level - 1) * .25)) {
                level++;
                
                combatFeed += "You leveled up!\nYou are now level " + level + ".";
                battleText.setText(combatFeed);
                
                xp -= 100 + (int)(100 * (level - 1) * .25);
            }
        }
    }
}