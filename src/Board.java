import java.util.Scanner;

public class Board {
    Scanner scanner = new Scanner(System.in);

    public void printGameStart() {
        System.out.println("Game has started.");
    }

    public void showResult(String s) {
        System.out.println(s);
    }

    public void printThrowResults(int[][] scores, int[] picked) {

        String out = " ┌───────────────────────┬─────────┐\n " +
                "│ Thrown                │ Picked  │\n " +
                "├───┬───┬───┬───┬───┬───┼─────────┤\n";
        for (int i = 0; i < 6; i++) {

            // putting scores into the table
            for (int j = 0; j < 6; j++) {
                out += (" │ " + (scores[i][j] > 0 ? scores[i][j] : " "));
            }

            out += " │       " + picked[i] + " │\n";

            // adding divider; isn't added on the last row
            if (i != 5) {
                out += " ├───┼───┼───┼───┼───┼───┼─────────┤\n";
            }

        }
        out += " └───┴───┴───┴───┴───┴───┴─────────┘\n";

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
        System.out.printf("It is now %s's turn\n", player.getPlayerName());
        this.printPlayerScore(player);
    }

    public String getPlayerName() {
        return scanner.next();
    }

    public void requestEnter() {
        System.out.println("Press <Enter> to continue");
        scanner.nextLine();
    }

    public int getPlayerCount() {
        return scanner.nextInt();
    }

    private String createFillerString(char filler, int length) {
        return String.format("%" + length + "s", "").replace(' ', filler);
    }
}
