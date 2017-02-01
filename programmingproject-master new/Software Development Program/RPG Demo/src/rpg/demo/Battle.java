/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.demo;

/**
 *
 * @author programming
 */
public class Battle extends CharacterInfoAndLeveling {
    
    public Battle(int health, int speed, int damage) {
        super(health, speed, damage);
    }
    
    public void battle() {
        CharacterInfoAndLeveling enemy;
        boolean escaped = false;
        int option = 0;
        
        if ((int)(Math.random() * 2) == 1) {
            enemy = new CharacterInfoAndLeveling(125, 25, 40);
            enemy.name = "a large bear";
        } else {
            enemy = new CharacterInfoAndLeveling(75, 50, 25);
            enemy.name = "a small bear";
        }
        
        System.out.println("You just ran into " + enemy.name + ".");
        
        while (character.health > 0 && enemy.health > 0 && escaped == false) {
            System.out.println("1. Attack");
            System.out.println("2. Flee");
            System.out.println("3. Pass");
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
            
            switch (option) {
                case 1:
                    if (enemy.speed > character.speed) {
                        System.out.println("Your enemy attacks.");
                        System.out.println("Your enemy dealt " + enemy.damage + " damage.");
                        character.takeDamage(enemy.damage);
                        if (character.health != 0) {
                            System.out.println("You attack.");
                            System.out.println("You deal " + character.damage + " damage.");
                            enemy.takeDamage(character.damage);
                            System.out.println("Your enemy has " + enemy.health + " health remaining.");
                        }
                    } else {
                        System.out.println("You attack.");
                        System.out.println("You deal " + character.damage + " damage.");
                        enemy.takeDamage(character.damage);
                        System.out.println("Your enemy has " + enemy.health + " health remaining.");
                        if (enemy.health != 0) {
                            System.out.println("Your enemy attacks.");
                            System.out.println("Your enemy dealt " + enemy.damage + " damage.");
                            character.takeDamage(enemy.damage);
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
                        character.takeDamage(enemy.damage);
                    }   break;
                default:
                    System.out.println("You have passed.");
                    System.out.println("Your enemy attacks.");
                    System.out.println("Your enemy deals " + character.damage + " damage.");
                    character.takeDamage(enemy.damage);
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
    
    
}
