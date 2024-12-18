
abstract class ComputerGreedPlayer extends GreedPlayer{
	public void newGame(int maxRounds, int playerNum) {}
	public void noScoringCombinations(int[] dice) {}
	public void oneScoringCombination(int[] dice,
	ScoringCombination combination) {}
	public void gameOver(int[] scores, int playerNum, int winner) {}
	public boolean human() {
		return false;
	}
	public abstract String playerName();
	public abstract String author();
}