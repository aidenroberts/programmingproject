package rpg.demo;

import java.util.Scanner;
import java.io.*;
public class RPGDemo {

    private int health, speed, damage;
    private String name;
    private static int level = 1, xp = 0, Class;
    private static RPGDemo character;
    private static Scanner input = new Scanner(System.in);
    
    
    public RPGDemo(int health, int speed, int damage) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
    }
    
    public static void main(String[] args) {
        mainMenu();
        
        for (int i = 1; i < 5; i++) {
            battle();
            CheckForGameOver();
            character.lvlUp();
        }
        
        Save();
    }
    
    
    public static void mainMenu() {
        character = new RPGDemo(0, 0, 0);
        char confirmation = ' ';
        
        System.out.println("1. Create New Profile");
        System.out.println("2. Load");
        System.out.print("What would you like to do? ");
        int choice = input.nextInt();
        
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice.");
            System.out.print("Enter  a valid option: ");
            choice = input.nextInt();
        }
        
        switch (choice) {
            case 1:
                while (confirmation != 'Y' && confirmation != 'y'){
                    
                    System.out.println("1. Warrior (Balanced Stats)");
                    System.out.println("2. Tank (High health and armor, low damage and very low speed)");
                    System.out.println("3. Speedster (High speed, low damage, health, and armor)");
                    System.out.println("4. Glass Cannon (High damage, low health and very low armor)");
                    System.out.print("Which class do you want (enter the number)? ");
                    Class = input.nextInt();
        
        
                    while (Class > 4 || Class < 1) {
                        System.out.println("Invalid class selection.");
                        System.out.print("Enter a valid class number: ");
                        Class = input.nextInt();
                    }
        
        
                    switch (Class) {
                        case 1:
                            character = new RPGDemo(75, 75, 75); break;
                        case 2:
                            character = new RPGDemo(100, 50, 60); break;
                        case 3:
                            character = new RPGDemo(50, 100, 40); break;
                        case 4:
                            character = new RPGDemo(50, 75, 100); break;
                    }
        
                    character.showStats();
                    System.out.print("Are you sure you want to use this class (Y/N)? ");
                    confirmation = input.next().charAt(0);
            
                    while (confirmation != 'Y' && confirmation != 'N' && confirmation != 'y' && confirmation != 'n') {
                        System.out.println("Invalid answer.");
                        System.out.print("Answer Y or N: ");
                        confirmation = input.next().charAt(0);
                    } 
                    
                }
                System.out.print("Enter your character's name: ");
                String name = input.next();
                
                System.out.print("Are you sure you want to use this character name " + name + "(Y/N)? ");
                confirmation = input.next().charAt(0);
            
                while (confirmation != 'Y' && confirmation != 'N' && confirmation != 'y' && confirmation != 'n') {
                    System.out.println("Invalid answer.");
                    System.out.print("Answer Y or N: ");
                    confirmation = input.next().charAt(0);
                }
                character.name = name;
                break;
            case 2:
                System.out.print("Enter the name of your character (case sensitive): ");
                String fileName = "E:/Software Development Program/" + input.next() + ".sav";
                try {
                    FileInputStream saveFile = new FileInputStream(fileName);
                    ObjectInputStream save = new ObjectInputStream(saveFile);
            
                    Class = (Integer)save.readObject();
                    level = (Integer)save.readObject();
                    character.health = (Integer)save.readObject();
                    character.speed = (Integer)save.readObject();
                    character.damage = (Integer)save.readObject();
                    character.name = (String)save.readObject();
                    xp = (Integer)save.readObject();
            
                    save.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                        
                }
            
                System.out.println("Name: " + character.name);
                switch (Class) {
                    case 1:
                        System.out.println("Class: Warrior"); break;
                    case 2:
                        System.out.println("Class: Tank"); break;
                    case 3:
                        System.out.println("Class: Speedster"); break;
                    case 4:
                        System.out.println("Class: Glass Cannon"); break;
                }
                System.out.println("Level " + level);
                System.out.println("XP: " + xp);
                character.showStats();
                System.out.print("Is this the correct save (Y/N)? ");
                confirmation = input.next().charAt(0);
            
                while (confirmation != 'Y' && confirmation != 'N' && confirmation != 'y' && confirmation != 'n') {
                        System.out.println("Invalid answer.");
                        System.out.print("Answer Y or N: ");
                        confirmation = input.next().charAt(0);
                }
            
                if (confirmation == 'n' || confirmation == 'N') {
                    System.exit(0);
                }
                break;
        }
    }
    
    
    public void lvlUp () {
        if (xp >= (100 * (1 + (level - 1) * .5))) {
            switch (Class) {
                case 1:
                    health = (int) (75 * Math.pow(1.1, (level))); break;
                case 2:
                    health = (int) (100 * Math.pow(1.1, (level))); break;
                case 3:
                    health = (int) (50 * Math.pow(1.1, (level))); break;
                default :
                    health = (int) (50 * Math.pow(1.1, (level))); break;
            }
            xp -= (100 * (1 + (level - 1) * .5));
            speed *= 1.1;
            damage *= 1.1;
            level++;
            System.out.println("You are now level " + level + ".");
            character.showStats();
            System.out.println("You need to get " + ((100 * (1 + (level - 1) * .25)) - xp) + " xp in order to level up to level " + (level + 1) + ".");
        }
    }
    
    
    public void showStats () {
        System.out.println("Health: " + health);
        System.out.println("Speed: " + speed);
        System.out.println("Damage: " + damage);
    }
    
    public static void battle() {
        RPGDemo enemy;
        boolean escaped = false;
        
        if ((int)(Math.random() * 2) == 1) {
            enemy = new RPGDemo(125, 25, 40);
            enemy.name = "a large bear";
        } else {
            enemy = new RPGDemo(75, 50, 25);
            enemy.name = "a small bear";
        }
        
        System.out.println("You just ran into " + enemy.name + ".");
        
        while (character.health > 0 && enemy.health > 0 && escaped == false) {
            System.out.println("1. Attack");
            System.out.println("2. Flee");
            System.out.println("3. Pass");
            System.out.print("Which option do you choose? ");
            int option = input.nextInt();
            
            while (option != 1 && option != 2 && option != 3) {
                System.out.println("Invalid option choice.");
                System.out.print("Enter a valid option: ");
                option = input.nextInt();
            }
            
            switch (option) {
                case 1:
                    if (enemy.speed > character.speed) {
                        System.out.println("Your enemy attacks.");
                        System.out.println("Your enemy dealt " + enemy.damage + " damage.");
                        character.attack(enemy.damage);
                        if (character.health != 0) {
                            System.out.println("You attack.");
                            System.out.println("You deal " + character.damage + " damage.");
                            enemy.attack(character.damage);
                            System.out.println("Your enemy has " + enemy.health + " health remaining.");
                        }
                    } else {
                        System.out.println("You attack.");
                        System.out.println("You deal " + character.damage + " damage.");
                        enemy.attack(character.damage);
                        System.out.println("Your enemy has " + enemy.health + " health remaining.");
                        if (enemy.health != 0) {
                            System.out.println("Your enemy attacks.");
                            System.out.println("Your enemy dealt " + enemy.damage + " damage.");
                            character.attack(enemy.damage);
                        }
                    }   break;
                case 2:
                    if ((int)(Math.random() * 2) == 1) {
                        System.out.println("You escaped the fight.");
                        escaped = true;
                    } else {
                        System.out.println("You could not escape.");
                        System.out.println("Your enemy attacks.");
                        System.out.println("Your enemy deals " + character.damage + " damage.");
                        character.attack(enemy.damage);
                    }   break;
                default:
                    System.out.println("You have passed.");
                    System.out.println("Your enemy attacks.");
                    System.out.println("Your enemy deals " + character.damage + " damage.");
                    character.attack(enemy.damage);
                    break;
            }
            System.out.println("You have " + character.health + " health remaining.");
        }
        if (enemy.health <= 0) {
            System.out.println("You won the fight.");
            if (enemy.name.equals("a small bear")) {
                System.out.println("You gained 50 xp.");
                xp += 50;
            } else {
                System.out.println("You gained 100 xp.");
                xp += 100;
            }
        }
    }
    
    public void attack(int damage) {
        health -= damage;
        
        if (health <= 0) {
            health = 0;
        }
    }
    
    public static void CheckForGameOver() {
        if (character.health <= 0) {
            System.out.println("Game Over.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            System.exit(0);
        }
    }
    
    public static void Save() {
        String fileName = "E:/Software Development Program/" + character.name + ".sav";
        try{
            FileOutputStream saveFile=new FileOutputStream(fileName);

            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            save.writeObject(Class);
            save.writeObject(level);
            save.writeObject(character.health);
            save.writeObject(character.speed);
            save.writeObject(character.damage);
            save.writeObject(character.name);
            save.writeObject(xp);

            save.close();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }
}