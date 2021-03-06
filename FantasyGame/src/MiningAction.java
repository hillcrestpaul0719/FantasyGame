import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * The action of mining
 * 
 * @version 06-18-2021
 * @author Jing Sun & Paul Lee
 */
public class MiningAction extends Action {
    
    /**
     * Constructor for mining
     */
    public MiningAction() {
        super("mine", "Mine for gold");
    }
    
    /**
     * Allows the player to mine
     */
    @Override
    public void perform(Player player, Location location) {
        
        //Variables
        Random rng = new Random();
        List<Item> items = new ArrayList<Item>();
        int quantity = 0;
        int chunkQuantity = 0;
        boolean empty = true;
        //Adds gold and gold chunks in the room to the items ArrayList
        System.out.print("You survey the area for valuables, and there is ");
        for (Item item:player.getRoom(location).inventory.getItems()) {
            if (item.getName().equals("Gold") ||
                    item.getName().equals("Gold Chunk")) items.add(item);
        }
        
        //Checks whether there's gold or gold chunks in the room and if so
        //prints the gold/gold chunks and their quantity
        if (items.size() > 0) {
            for (int i=0; i<items.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(items.get(i).quantity + " " + items.get(i).getName());
                quantity += items.get(i).quantity / 10 + 1;
                if (items.get(i).getName().equals("Gold Chunk")) 
                    chunkQuantity = items.get(i).quantity;
                if (items.get(i).quantity != 0) empty = false;
            }
        } else System.out.print("nothing");
        System.out.println(" in the room.");
        
        //Checks if the player has mined before, and displays a custom message if
        //they haven't
        if (!player.mined) {
            System.out.println("You wield a pickaxe for the first time, and are a bit nervous,");
            System.out.println("but you strike down on the gold.");
            System.out.println();
            player.mined = true;
        }
        
        //The actual action of mining, which takes time
        if (items.size() != 0) {
            System.out.print("You start mining...");
            int ore = rng.nextInt(quantity);
            if (ore < chunkQuantity) {
                try {
                    Thread.sleep(5000);
                    player.getRoom(location).inventory.give(player.inventory, "Gold Chunk", 1);
                    System.out.println("And find a gold chunk!");
                }
                catch (Exception e) {}
            }
            else {
                try {
                    Thread.sleep(1000);
                    int goldQuantity = player.getRoom(location).inventory.getItem("Gold").quantity;
                    if (goldQuantity > 9) {
                        player.getRoom(location).inventory.give(player.inventory, "Gold", 10);
                        System.out.println("and get 10 pieces of gold.");
                    }
                    else {
                        player.getRoom(location).inventory.give(player.inventory, "Gold", goldQuantity);
                        if (goldQuantity != 1)
                        System.out.println("and get " + goldQuantity + " pieces of gold.");
                        else System.out.println("and get 1 piece of gold.");
                    }
                }
                catch (Exception e) {}
            }
        }
        
        //Clears the room if there are no more gold and gold chunks in the room
        if (empty) player.getRoom(location).inventory.clear();
    }
}
