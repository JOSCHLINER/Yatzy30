import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String gameStartMessage = "Press y to play n to exit the game";

        System.out.println(gameStartMessage);
        while (scanner.hasNext()) {


            String item = scanner.next();
            if (item.equals("y")) {
                System.out.println("Starting Game...");
                new Game();
            } else if (item.equals("n")) {
                System.out.println("Stopping Game");
                break;
            }

            System.out.println(gameStartMessage);
        }

    }

}