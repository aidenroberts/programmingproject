package full.game;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author programming
 */
public class Gameplay extends Pane{
    int classNum, health, speed, damage, level, xp, cLevel;
    String name;
    
    public Gameplay(String name, int classNum, int health, int speed, int damage, int level, int xp) {
        this.name = name;
        this.classNum = classNum;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.xp = xp;
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
    }
    
}
