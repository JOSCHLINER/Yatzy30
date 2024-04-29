import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Board {
    Scanner scanner = new Scanner(System.in);

    public void printGameStart() {
        System.out.println("Game has started.");
    }

    public void showResult(String s) {
        System.out.println(s);
    }

    public void printThrowResults(int[] scores, int[] picked) {

        int n = picked.length;
        int pickedBorderLength = Math.max(7, n * 4 - 1);
        String pickedBorder = createFillerString('─', pickedBorderLength + 1);

        String out = "Pick the dices you wish to save by writing their value. You have to pick at least one and can pick as many as you like. Once you feel that you have picked all the dices you want enter b\n ┌───────────────────────┬" +
                pickedBorder + "┐\n " +
                "│ Rolled                │ Picked" +
                createFillerString(' ', pickedBorderLength - 6) +
                "│\n " +
                "├───┬───┬───┬───┬───┬───┼" +
                pickedBorder +
                "┤\n";

        // putting scores into the table
        for (int i = 0; i < 6; i++) {
            out += (" │ " + (scores[i] > 0 ? scores[i] : " "));
        }

        // putting the picked items in table
        out += " │ ";
        for (int i = 0; i < picked.length; i ++) {
            if (i != 0) {
                out += " ,";
            }

            out += picked[i];
        }
        out += createFillerString(' ', pickedBorderLength - (n * 3) + 1) +
                " │\n" +
            " └───┴───┴───┴───┴───┴───┴" +
            pickedBorder +
            "┘\n";

        System.out.printf(out);
    }

    public void printThrowResults(int[] scores) {
        String out = " ┌───────────────────────┐\n " +
                "│ Rolled                │\n " +
                "├───┬───┬───┬───┬───┬───┤\n";

        // putting scores into the table
        for (int i = 0; i < 6; i++) {
            out += (" │ " + (scores[i] > 0 ? scores[i] : " "));
        }

        out += " │\n" +
                " └───┴───┴───┴───┴───┴───┘\n";

        System.out.printf(out);
    }

    public void printThrowResult(int result) {
        String out = " ┌───────────────┬────┐\n " +
                "│ The total sum │ %2d │\n " +
                "└───────────────┴────┘\n";
        System.out.printf(out, result);
    }

    public void printSelfScoreDecreasionMessage() {
        System.out.println(" You score wasn't greater then 30. That means your score will be lowered by 10 points.");
    }

    public void printScoreDecreaseMessage(Player player, int decreased) {
        System.out.printf(" Players %s's score was decreased by %d.", player.getPlayerName(), decreased);
    }

    public void printPlayerDeathMessage(Player player) {
        System.out.println(player.getPlayerName() + "'s score reached 0! Player now out of game!");
        return;
    }

    public void printPlayerScore(Player player) {
        System.out.printf(" %s's score is %d\n", player.getPlayerName(), player.getScore());
    }

    public void printDecreaseDiceThrow(int[] rollResults, int goal) {
        int n = rollResults.length * 4 - 1;
        String border = createFillerString('─', n);

        String out = "\n Your goal to reach is: %d\n" +
                " ┌" + border + "┐\n" +
                " │ Your dice rolls: " + createFillerString(' ', n - 18) + "│\n" +
                " ├" + border + "┤\n ";
        for (int roll : rollResults) {
            out += "│ " + roll + " ";
        }

        out += "│\n" +
                " └" + border + "┘ \n";

        System.out.printf(out, goal);
    }

    public void printVictoryMessage(Player player) {
        int n = player.getPlayerName().length();
        String border = createFillerString('─', n + 27);

        String out = " ┌" + border + "┐\n " +
                "│ The victorious player is %s │\n " +
                "└" + border + "┘\n";
        System.out.printf(out, player.getPlayerName());

    }

    public void printDivider() {
        System.out.printf("\n" + createFillerString('-', 25) + "\n");
    }

    public void printPlayerTurn(Player player) {
        System.out.printf(" It is now %s's turn\n", player.getPlayerName());
        this.printPlayerScore(player);
    }

    public String getPlayerName() {
        System.out.printf("What should the name of the player be? ");
        String playerName = scanner.next();
        System.out.println();
        return playerName;
    }

    public void requestEnter() {
        System.out.println(" Press <Enter> to continue");
        scanner.nextLine();
        System.out.println();
    }

    public int getPlayerCount() {
        System.out.printf("How many players do you want to play with? ");
        int playerCount = scanner.nextInt();
        System.out.println();
        return playerCount;
    }

    public int getBotCount() {
        System.out.printf("With how may bots do you want to play? ");
        int playerCount = scanner.nextInt();
        System.out.println();
        return playerCount;
    }

    private String createFillerString(char filler, int length) {
        try {
            return String.format("%" + length + "s", "").replace(' ', filler);
        } catch (Exception e) {
            System.out.println(length);
            return "&";
        }
    }

    public void showSleep() {
        System.out.println();
        for (int i = 0; i < 5; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.printf(".");
            }   catch (java.lang.InterruptedException e) {
                return;
            }
        }
        System.out.println();
    }

    public int[] pickResults(int[]scores, int amountToPick) {
        System.out.printf("You have to pick at least one number, but at most %d numbers.\n", amountToPick);
        ArrayList<Integer> picked = new ArrayList<Integer>();
        while (scanner.hasNextLine() && picked.size() < amountToPick) {
            String item = scanner.next();
            try {

                int converted = Integer.parseInt(item);
                if (this.isAllowedPick(scores, picked, converted)) {
                    picked.add(converted);
                }   else {
                    System.out.println("That number you cannot choose!");
                }
            } catch (NumberFormatException e) {

                // checking if user want to exit and if user can exit
                if (item.equals("b") && !picked.isEmpty()) {
                    System.out.printf("Exiting");
                    break;
                }

                System.out.println("That is not a number retry");
            }
        }

        // converting arraylist into array
        int[] out = new int[picked.size()];
        for (int i = 0; i < picked.size(); i++) {
            out[i] = picked.get(i);
        }

        return out;
    }

    private boolean isAllowedPick(int[] list, ArrayList<Integer> picked, int needle) {

        for (int item : list) {

            // check if the needle is in the list
            if (item == needle) {

                // checking if the user can pick the item anymore
                if (countOccurrence(list, needle) > countOccurrence(picked, needle)) {
                    return true;
                }   else {
                    return false;
                }
            }
        }
        return false;
    }

    private int countOccurrence(int[] list, int needle) {
        int count = 0;
        for (int item : list) {
            if (item == needle) {
                count += 1;
            }
        }
        return count;
    }

    private int countOccurrence(ArrayList<Integer> list, int needle) {
        return Collections.frequency(list, needle);
    }
}
