package rpg.demo;

public class RPGDemo{

    
    
    public static void main(String[] args) {
        CharacterInfoAndLeveling charInfo = new CharacterInfoAndLeveling(0, 0, 0);
        Battle battle = new Battle(0, 0, 0);
        charInfo.mainMenu();
        
        for (int i = 1; i < 5; i++) {
            battle.battle();
            charInfo.CheckForGameOver();
            charInfo.lvlUp();
        }
        
        charInfo.Save();
    }
    
    
    
    
}