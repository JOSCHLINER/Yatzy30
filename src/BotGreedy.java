public class BotGreedy implements Bot{
    @Override
    public int[] makeMove(int[] throwResults) {
        return new int[]{this.pickResult(throwResults)};
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
