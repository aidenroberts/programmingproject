/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;



/**
 *
 * @author programming
 */
public class CharacterInfoAndLeveling {
    public int health, speed, damage;
    public static int level = 1, xp = 0, Class = 0;
    public static CharacterInfoAndLeveling character;
    public String name;
    public static Scanner input = new Scanner(System.in);
    private static ArrayList<String> saves = new ArrayList<>(), names = new ArrayList<>();
    
    
    public CharacterInfoAndLeveling(int health, int speed, int damage) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
    }
    
    public void mainMenu() {
        character = new CharacterInfoAndLeveling(0, 0, 0);
        char confirmation = ' ';
        int choice = 0;
        
        System.out.println("1. Create New Profile");
        System.out.println("2. Load");
        System.out.print("What would you like to do? ");
        String choiceString = input.next();
        
        while (choice != 1 && choice != 2) {
            try {
                choice = Integer.parseInt(choiceString);
            } catch (Exception ex) {
                System.out.println("Invalid choice.");
                System.out.print("Enter  a valid option: ");
                choiceString = input.next();
            }
            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice.");
                System.out.print("Enter  a valid option: ");
                choiceString = input.next();
            }
        }
        
        switch (choice) {
            case 1:
                while (confirmation != 'Y' && confirmation != 'y'){
                    
                    System.out.println("1. Warrior (Balanced Stats)");
                    System.out.println("2. Tank (High health and armor, low damage and very low speed)");
                    System.out.println("3. Speedster (High speed, low damage, health, and armor)");
                    System.out.println("4. Glass Cannon (High damage, low health and very low armor)");
                    System.out.print("Which class do you want (enter the number)? ");
                    String classString = input.next();
        
        
                    while (Class > 4 || Class < 1) {
                        try {
                            Class = Integer.parseInt(classString);
                        } catch (Exception ex) {
                            System.out.println("Invalid class selection.");
                            System.out.print("Enter a valid class number: ");
                            classString = input.next();
                        }
                        if (Class > 4 || Class < 1 && Class != 0) {
                            System.out.println("Invalid class selection.");
                            System.out.print("Enter a valid class number: ");
                            classString = input.next();
                        }
                    }
        
        
                    switch (Class) {
                        case 1:
                            character = new CharacterInfoAndLeveling(75, 75, 75); break;
                        case 2:
                            character = new CharacterInfoAndLeveling(100, 50, 60); break;
                        case 3:
                            character = new CharacterInfoAndLeveling(50, 100, 40); break;
                        case 4:
                            character = new CharacterInfoAndLeveling(50, 75, 100); break;
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
                Load();
            
                break;
        }
    }
    
    public void lvlUp () {
        if (xp >= (100 * (1 + (level - 1) * .5))) {
            switch (Class) {
                case 1:
                    character.health = (int) (75 * Math.pow(1.1, (level))); break;
                case 2:
                    character.health = (int) (100 * Math.pow(1.1, (level))); break;
                case 3:
                    character.health = (int) (50 * Math.pow(1.1, (level))); break;
                default :
                    character.health = (int) (50 * Math.pow(1.1, (level))); break;
            }
            character.xp -= (100 * (1 + (level - 1) * .5));
            character.speed *= 1.1;
            character.damage *= 1.1;
            character.level++;
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
    
    public void takeDamage(int damage) {
        health -= damage;
        
        if (health <= 0) {
            health = 0;
        }
    }
    
    public void CheckForGameOver() {
        if (character.health <= 0) {
            System.out.println("Game Over.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }
            System.exit(0);
        }
    }
    
    
    public void Save() {
        int num = 0;
        try{
                FileOutputStream saveFile=new FileOutputStream("E:/Software Development Program/SaveList.sav");

                ObjectOutputStream save = new ObjectOutputStream(saveFile);

                save.writeObject(saves);
                save.writeObject(names);
                
                save.close();
        } catch(Exception ex) {
            ex.printStackTrace();
                        
        }
        
        try {
            FileInputStream list = new FileInputStream("E:/Software Development Program/SaveList.sav");
            ObjectInputStream save = new ObjectInputStream(list);
            
            saves = (ArrayList<String>)save.readObject();
            names = (ArrayList<String>)save.readObject();
            
            save.close();
        } catch(Exception ex) {
            ex.printStackTrace();
            
        }
        
        String fileName = "E:/Software Development Program/" + character.name + ".sav";
        if (saves.isEmpty()) {
            saves.add(fileName);
            names.add(character.name);
        }
        else {
            System.out.print("Would you like to overwrite a save(Y/N)? ");
            char confirmation = input.next().charAt(0);

            while (confirmation != 'Y' && confirmation != 'N' && confirmation != 'y' && confirmation != 'n') {
                System.out.println("Invalid answer.");
                System.out.print("Answer Y or N: ");
                confirmation = input.next().charAt(0);
            }

            if (confirmation == 'y' || confirmation == 'Y') {
                if (saves.size() == 1) {
                    saves.set(0, fileName);
                } else {
                    for (int i = 0; i < saves.size(); i++) {
                        System.out.println((i + 1) + ".\t" + names.get(i));
                    }
                    System.out.print("Which save would you like to overrwrite(Enter the number)? ");
                    String numString = input.next();
                    while (num < 1 || num > saves.size()) {
                        try {
                            num = Integer.parseInt(numString);
                        } catch (Exception ex) {
                            System.out.println("Invalid choice.");
                            System.out.print("Enter a valid number: ");
                            numString = input.next();
                        }
                        if (num < 1 || num > saves.size() && num != 0) {
                            System.out.println("Invalid choice.");
                            System.out.print("Enter a valid number: ");
                            numString = input.next();
                        }
                    }
                    saves.set(num - 1, fileName);
                    names.set(num - 1, character.name);
                }
            } else if (confirmation == 'n' || confirmation == 'N' && names.contains(character.name)) {
                int counter = 0;
                while (names.contains(character.name)) {
                    counter++;
                    character.name = character.name + "(" + counter + ")";
                    fileName = "E:/Software Development Program/" + character.name + ".sav";
                }
                names.add(character.name);
                saves.add(fileName);
            } else {
                saves.add(fileName);
                names.add(character.name);
            }
        }
            
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
        
        try{
                FileOutputStream saveFile=new FileOutputStream("E:/Software Development Program/SaveList.sav");

                ObjectOutputStream save = new ObjectOutputStream(saveFile);

                save.writeObject(saves);
                save.writeObject(names);
                
                save.close();
            }
            catch(Exception exc){
                exc.printStackTrace();
            }
    }
    
    public static void Load() {
        char confirmation = ' ';
        int num = 0, option = 0;
        
        try {
            FileInputStream list = new FileInputStream("E:/Software Development Program/SaveList.sav");
            ObjectInputStream save = new ObjectInputStream(list);
            
            saves = (ArrayList<String>)save.readObject();
            names = (ArrayList<String>)save.readObject();
            
            save.close();
        } catch(Exception ex) {
            System.out.println("No files exist.");
            System.exit(0);
        }
        
        for (int i = 0; i < saves.size(); i++) {
            System.out.println((i + 1) + ".\t" + names.get(i));
        }
        System.out.print("Which save would you like to select(Enter the number)? ");
        String numString = input.next();
        while (num < 1 || num > saves.size()) {
            try {
                num = Integer.parseInt(numString);
            } catch (Exception ex) {
                System.out.println("Invalid choice.");
                System.out.print("Enter a valid number: ");
                numString = input.next();
            }
            if (num < 1 || num > saves.size() && num != 0) {
                System.out.println("Invalid choice.");
                System.out.print("Enter a valid number: ");
                numString = input.next();
            }
        }
        
        
        String fileName = saves.get(num - 1);
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
                System.out.println("No files exist.");
                System.exit(0);
            }
            while(confirmation != 'Y' && confirmation != 'y') {
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
                
                if (confirmation == 'N' || confirmation == 'n') {
                    System.out.println("1. Look again.");
                    System.out.println("2. Exit");
                    System.out.print("Which option do you choose? ");
                    String optionString = input.next();
            
                    while (option != 1 && option != 2 && option != 3) {
                        try {
                            option = Integer.parseInt(optionString);
                        } catch (Exception ex) {
                            System.out.println("Invalid option choice.");
                            System.out.print("Enter a valid option: ");
                            optionString = input.next();
                        }

                        if (option != 1 && option != 2 && option != 3 && option != 0){
                            System.out.println("Invalid option choice.");
                            System.out.print("Enter a valid option: ");
                            optionString = input.next();
                        }
                    }
                    
                    if (option == 2) System.exit(0);
                }
            }
            
    }
}
