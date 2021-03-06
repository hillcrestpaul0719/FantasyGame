/**
 * Entity class for the player and the rival elf
 * 
 * @version 06-18-2021
 * @author Jing Sun & Paul Lee
 */
public class Entity {
    
    //Instance variables
    private Position position;
    private String name;
    public Inventory inventory = new Inventory();
    private int health = 100;
    public boolean isAlive = true;
    
    /**
     * Entity constructor
     * 
     * @param n
     * @param p 
     */
    public Entity(String n, Position p) {
        name = n;
        position = p;
    }
    
    /**
     * Fetches the position of the entity
     * 
     * @return position
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Fetches the room of the entity
     * 
     * @param location
     * @return 
     */
    public Room getRoom(Location location) {
        return location.getRoom(position.getRoomX(), position.getRoomY());
    }
    
    /**
     * Checks whether the entity can move left or not
     * 
     * @param loc
     * @return 
     */
    public boolean canMoveLeft(Location loc) {
        return loc.getRoom(position.getRoomX()-1, position.getRoomY()) != null;
    }
    
    /**
     * Moves the entity left if it can
     * 
     * @param loc
     * @return 
     */
    public boolean moveLeft(Location loc) {
        if (canMoveLeft(loc)) {
            position.moveLeft();
            enterRoom(loc);
            return true;
        } else return false;
    }
    
    /**
     * Checks whether the entity can move right or not
     * 
     * @param loc
     * @return 
     */
    public boolean canMoveRight(Location loc) {
        return loc.getRoom(position.getRoomX()+1, position.getRoomY()) != null;
    }
    
    /**
     * Moves the entity right
     * 
     * @param loc
     * @return 
     */
    public boolean moveRight(Location loc) {
        if (canMoveRight(loc)) {
            position.moveRight();
            enterRoom(loc);
            return true;
        } else return false;
    }
    /**
     * Checks whether the entity can move forward or not
     * 
     * @param loc
     * @return 
     */
    public boolean canMoveForward(Location loc) {
        return loc.getRoom(position.getRoomX(), position.getRoomY()-1) != null;
    }
    
    /**
     * Moves the entity forward
     * 
     * @param loc
     * @return 
     */
    public boolean moveForward(Location loc) {
        if (canMoveForward(loc)) {
            position.moveForward();
            enterRoom(loc);
            return true;
        } else return false;
    }
    
    /**
     * Checks whether the entity can move behind or not
     * 
     * @param loc
     * @return 
     */
    public boolean canMoveBehind(Location loc) {
        return loc.getRoom(position.getRoomX(), position.getRoomY()+1) != null;
    }
    
    /**
     * Moves the entity behind
     * 
     * @param loc
     * @return 
     */
    public boolean moveBehind(Location loc) {
        if (canMoveBehind(loc)) {
            position.moveBehind();
            enterRoom(loc);
            return true;
        } else return false;
    }
    
    /**
     * Allows the entity to enter a room
     * 
     * @param loc 
     */
    public void enterRoom(Location loc) {
    }
    
    /**
     * Fetches the health of the entity
     * 
     * @return 
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Sets the health of the entity
     * 
     * @param health
     * @param location
     * @return 
     */
    public boolean setHealth(int health, Location location) {
        if (0 <= health && health <= 100) {
            this.health = health;
            if (health == 0) {
                isAlive = false;
                getRoom(location).inventory.expand(this.inventory);
            }
            return true;
        } else return false;
    }
    
    /**
     * Allows the entity to take damage
     * 
     * @param damage
     * @param location
     * @return 
     */
    public boolean takeDamage(int damage, Location location) {
        return setHealth(health - damage, location);
    }
    
    /**
     * Teleports the entity to a new direction
     * 
     * @param prevLoc
     * @param newLoc
     * @param verbose
     */
    public boolean teleport(Location prevLoc, Location newLoc, boolean verbose) {
        if (!prevLoc.exitRequirementMet(this, verbose)) return false;
        if (!newLoc.entryRequirementMet(this, verbose)) return false;
        position.setLocation(newLoc.getName());
        position.setRoomX(0);
        position.setRoomY(0);
        enterRoom(newLoc);
        return true;
    }

    /**
     * Allows the entity to drink potions
     *
     * @param verbose
     * @return
     */
    public boolean drinkPotion(boolean verbose) {
        if (health == 100) {
            if (verbose) System.out.println("Your health is full!");
            return false;
        }
        if (!inventory.updateItemQuantity("Potion", -1)) {
            if (verbose) System.out.println("You don't have any potions!");
            return false;
        }
        health = Math.min(100, health + 10);
        System.out.println("Your health has been restored to " + health + "%!");
        return true;
    }
}
