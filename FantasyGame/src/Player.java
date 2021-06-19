import java.util.*;
/**
 * This class represents the sole character of the game.
 *
 * @version 06-18-2021
 * @author Jing Sun & Paul Lee
 */
public class Player extends Entity {
    /**
     * Player constructor
     * 
     * @param n
     * @param p 
     */
    public Player(String n, Position p) {
        super(n, p);
        inventory.addItem(new Item("Potion"));
    }
    
    /**
     * Allows the player to look around in a location
     * 
     * @param location 
     */
    public void look(Location location) {
        getRoom(location).look();

        ArrayList<String> directions = new ArrayList<String>();
        if (canMoveLeft(location)) directions.add("to the left");
        if (canMoveRight(location)) directions.add("to the right");
        if (canMoveForward(location)) directions.add("forward");
        if (canMoveBehind(location)) directions.add("behind");
        if (directions.size() == 0) System.out.println("\nThere are no adjacent rooms. Looks like you are surrounded!\n");
        else System.out.println("\nFrom this room you can enter the room " + Util.humanList(directions) + ".\n");

        pickUpItems(location);
    }
    
    /**
     * Allows the player to enter a room
     * 
     * @param location 
     */
    @Override
    public void enterRoom(Location location) {
        Room room = getRoom(location);

        room.enter(this, location, true);

        look(location);
    }
    
    /**
     * Allows the player to pick up items
     * 
     * @param location 
     */
    public void pickUpItems(Location location) {
        System.out.print("You find ");
        List<Item> items = getRoom(location).inventory.getItems();
        if (items.size() > 0) {
            for (int i=0; i<items.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(items.get(i).quantity + " " + items.get(i).getName());
            }
        } else System.out.print("nothing");
        System.out.println(" in the room.");
        inventory.expand(getRoom(location).inventory);
    }
    
    /**
     * Prints the status of the player, such as its health
     */
    public void printStatus() {
        System.out.println("===== Status =====");
        System.out.println("Health: " + getHealth() + "%");
        System.out.println("Position: Currently in " + getPosition().getLocation() + " at X=" + getPosition().getRoomX() + " Y=" + getPosition().getRoomY() + ".");
        System.out.println("\n===== Inventory =====");
        if (inventory.size() > 0) {
            for (Item item:inventory.getItems()) {
                System.out.println(item.quantity + " " + item.getName());
            }
        } else System.out.println("There is nothing in your inventory. :(");
    }
    
    /**
     * Prints a map of the location the player is currently in
     * 
     * @param location 
     */
    public void printMap(Location location) {
        location.map(getPosition().getRoomX(), getPosition().getRoomY());
    }
    
    /**
     * Allows the player to drink potions
     * 
     * @param amount
     * @return 
     */
    public boolean drinkPotion(int amount) {
        if (getHealth() == 100) return false;
        else {
            int newHealth = getHealth() + amount * 10;
            if (newHealth > 100) setHealth(100);
            else setHealth(newHealth);
            inventory.updateItemQuantity("Potion", -1 * amount);
            return true;
        }
    }
   
}
