
abstract class GreedPlayer {
	abstract String name();
	public abstract int choose(GreedOption[] options, int[] dice, 
			int bank, int[] scores, int round, int maxRounds, int playerNum);
	public boolean human(){return false;}
	public void newGame(int maxRounds, int playerNum){}
	public void noScoringCombinations(int[] dice){}
	public void oneScoringCombination(int[] dice, ScoringCombination combination){}
	public void gameOver(int[] scores, int playerNum, int winner){}
}
