import com.sun.javafx.PlatformUtil;

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
            players.add(new Player(playerName, true));
        }

        int botCount = board.getBotCount();
        for (int i = 0; i < botCount; i++) {
            players.add(new Player("Bot" + i, false));
        }
        playerCount += botCount;
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
        int result = 0;
        for (int dices = 6; dices > 0;) {
            int[] scores = player.throwDices(dices);
            int[] pickedResults;
            if (isPlayer(player)) {
                board.printThrowResults(scores);
                pickedResults = board.pickResults(scores, dices);
            }   else {
                pickedResults = player.getBot().makeMove(scores);
                board.printThrowResults(scores, pickedResults);
            }
            dices -= pickedResults.length;

            result += this.calculateResult(pickedResults);

            board.showSleep();
        }

        // print out dice roll results
        board.printThrowResult(result);

        // decreasing score of own score or next player
        if (result > 30) {
            int decrease = this.decreaseDiceThrow(player, result % 10);

            board.printScoreDecreaseMessage(nextPlayer, decrease);
            this.decreaseScore(nextPlayer, decrease);
            board.printPlayerScore(nextPlayer);

        }   else {
            board.printSelfScoreDecreasionMessage();
            this.decreaseScore(player);

            board.printPlayerScore(player);
        }
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

    private int calculateDecreaseAmount(int[] rolls, int goal) {
        int count = 0;
        for (int roll : rolls) {
            if (roll == goal) {
                count++;
            }
        }

        return count * goal;
    }

    public boolean isPlayer(Player player) {
        return player.isPlayer();
    }

    private int calculateResult(int[] scores) {

        int result = 0;
        for (int score : scores) {
            result += score;
        }
        return result;
    }
}