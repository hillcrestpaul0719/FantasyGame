import java.util.Scanner;
/**
 * Runs the game
 * 
 * @version 06-18-2021
 * @author Jing Sun & Paul Lee
 */
public class Main {
    
    /**
     * Main method
     * 
     * @param args 
     */
    public static void main(String[] args) {
        String seed;
        System.out.print("Enter a seed: ");
        Scanner scanner = new Scanner(System.in);
        seed = scanner.nextLine();

        System.out.println();

        Game game = new Game(seed);
        game.play();
    }
}
