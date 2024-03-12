import java.io.IOException;
import java.util.ArrayList;

public class Game {
    Board board = new Board();
    ArrayList<Player> players = new ArrayList<>();

    public Game() throws IOException {
        board.printGameStart();
        this.createPlayers();

        // call function to run game
        this.play();
    }

    int playerCount;
    private void createPlayers() {

        playerCount = board.getPlayerCount();
        for (int i = 0; i < playerCount; i++) {
            String playerName = board.getPlayerName();
            players.add(new Player(playerName));
        }
    }

    private void play() {

        int index = 0;
        int next = 0;
        while (playerCount > 1) {
            index = this.getNextAlivePlayer(next);
            next = this.getNextAlivePlayer(index + 1);

            this.update(players.get(index), players.get(next));
        }

        board.printVictoryMessage(players.get(this.getNextAlivePlayer(0)));
    }

    /**
     * Function to get the next player who is alive.
     *
     * @param index The index it starts its search on.
     * @return Returns the index of the next alive player. If none is found -1.
     */
    private int getNextAlivePlayer(int index) {
        int n = players.size();

        index %= n;
        int startindex = index;
        boolean wentRound = false;
        while (!players.get(index).isAlive()) {
            index = (index + 1) % n;

            if (index == startindex && wentRound) {
                return -1;
            }   else {
                wentRound = true;
            }

        }

        return index;
    }

    private void update(Player player, Player nextPlayer) {
        board.printDivider();
        board.printPlayerTurn(player);

        board.requestEnter();

        // make dice roll
        DiceRoll diceRoll = playerNormalDiceRoll(player);

        // print out dice roll results
        board.printThrowResults(diceRoll.throwResults, diceRoll.resultsPicked);
        board.printThrowResult(diceRoll.totalSum);

        // decreasing score of own score or next player
        if (diceRoll.totalSum > 30) {
            int decrease = this.decreaseDiceThrow(player, diceRoll.totalSum % 10);

            board.printScoreDecreaseMessage(nextPlayer, decrease);
            this.decreaseScore(nextPlayer, decrease);
            board.printPlayerScore(nextPlayer);

        }   else {
            board.printSelfScoreDecreasionMessage();
            this.decreaseScore(player);

            board.printPlayerScore(player);
        }
    }

    private DiceRoll playerNormalDiceRoll(Player player) {
        int result = 0;
        int[][] throwResults = new int[6][6];
        int[] resultsPicked = new int[6];
        for (int dices = 6; dices > 0; dices--) {
            int[] scores = player.throwDices(dices);
            throwResults[6 - dices] = scores;

            int resultPicked = this.pickResult(scores);

            resultsPicked[6 - dices] = resultPicked;
            result += resultPicked;
        }

        return new DiceRoll(throwResults, resultsPicked, result);
    }

    private int decreaseDiceThrow(Player player, int goal) {
        int[] results = player.throwDices(6);

        board.printDecreaseDiceThrow(results, goal);

        return calculateDecreaseAmount(results, goal);
    }

    /**
     * Decreasing the score of player.
     * If the players score reaches 0, a message is printed and the playerCount is reduced.
     * @param player
     */
    private void decreaseScore(Player player) {
        if (!player.decreaseScore()) {
            playerCount -= 1;
            board.printPlayerDeathMessage(player);
        }
    }

    /**
     * Decreasing the score of player.
     * If the players score reaches 0, a message is printed and the playerCount is reduced.
     * @param player
     * @param decrease
     */
    private void decreaseScore(Player player, int decrease) {
        if (!player.decreaseScore(decrease)) {
            playerCount -= 1;
            board.printPlayerDeathMessage(player);
        }
    }

    private int pickResult(int[] scores) {
        int maxScore = Integer.MIN_VALUE;
        for (int score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }

        return maxScore;
    }

    private int calculateDecreaseAmount(int[] rolls, int goal) {
        int count = 0;
        for (int roll : rolls) {
            if (roll == goal) {
                count++;
            }
        }

        return count * goal;
    }
}
