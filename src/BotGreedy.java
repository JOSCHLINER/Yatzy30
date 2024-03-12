public class BotGreedy implements Bot{
    @Override
    public DiceRoll makeMove(int[][] throwResults) {
        int result = 0;
        int[][] resultsPicked = new int[6][];
        for (int dices = 6; dices > 0; dices--) {
            int resultPicked = this.pickResult(throwResults[6 - dices]);

            resultsPicked[6 - dices].append(resultPicked);
            result += resultPicked;
        }

        return new DiceRoll(throwResults, resultsPicked, result);
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
}
