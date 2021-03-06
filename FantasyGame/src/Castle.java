import java.util.ArrayList;
import java.util.Random; // https://www.tutorialspoint.com/java/util/java_util_random.htm
/**
 * This is a Castle Location that generates rooms based on a seed
 * 
 * @version 06-18-2021
 * @author Jing Sun & Paul Lee
 */
class Castle extends Location {
    
    /**
     * Generates the rooms of the castle based on a given seed
     * 
     * @param seed 
     */
    public Castle(long seed) {
        super("castle");
        Random rng = new Random(seed);
        int x = 0;
        int y = 0;
        int direction;
        addRoom(new DarkRoom(0, 0, rng.nextLong()));
        while (getSize() < 25) {
            direction = rng.nextInt(4);
            if (direction == 0) x++;
            else if (direction == 1) x--;
            else if (direction == 2) y++;
            else y--;
            addRoom(new DarkRoom(x, y, rng.nextLong()));
        }

    }
    
    /**
     * Makes sure the player has at least 25 health before they can exit the castle
     * 
     * @param entity
     * @param verbose
     * @return 
     */
    @Override
    public boolean exitRequirementMet(Entity entity, boolean verbose) {
        if (entity.getHealth() >= 25) return true;
        else {
            if (verbose) System.out.println("Your health must be at least 25%.");
            return false;
        }
    }
}
