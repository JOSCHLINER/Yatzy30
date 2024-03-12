import java.lang.reflect.Array;

public class Player {
    private int score = 10;
    private boolean isAlive = true;

    private String name = "";
    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "score=" + score +
                ", isAlive=" + isAlive +
                ", name=" + name +
                '}';
    }

    /**
     * Function to decrease lives of player.
     * If the score reaches zero the player is taken out of the game.
     */
    public boolean decreaseScore() {
        this.score -= 2;
        return this.checkLifeStatus();
    }

    public boolean decreaseScore(int decrease) {
        this.score -= decrease;
        return this.checkLifeStatus();
    }

    /**
     * Function to check if the player is still alive and to update the isAlive boolean accordingly.
     * @return true if player still alive, else false.
     */
    private boolean checkLifeStatus() {
        if (this.score <= 0) {
            this.isAlive = false;
            return false;
        }

        return true;
    }

    public int getScore() {
        return this.score;
    }

    public String getPlayerName()
    {
        return name;
    }
    public int[] throwDices(int dices) {

        int[] scores = new int[6];
        for (int i = 0; i < dices; i++) {
            scores[i] = this.throwDice();
        }

        return scores;
    }


    public boolean isAlive() {
        return isAlive;
    }

    private int throwDice(){
        return (int) Math.ceil(Math.random()*6);
    }
}
