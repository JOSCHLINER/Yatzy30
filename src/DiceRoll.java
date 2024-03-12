public class DiceRoll {
    public int totalSum;
    public int[][] throwResults;
    public int[] resultsPicked;

    public DiceRoll(int[][] throwResults, int[] resultsPicked, int totalSum) {
        this.throwResults = throwResults;
        this.resultsPicked = resultsPicked;
        this.totalSum = totalSum;
    }

}
