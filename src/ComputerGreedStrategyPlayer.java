
public class ComputerGreedStrategyPlayer extends ComputerGreedPlayer {
	private GreedStrategy strat = null;
	
	ComputerGreedStrategyPlayer(GreedStrategy theStrategy){
		strat = theStrategy;
	}
	
	public String author(){return strat.author();}
	public String name(){return playerName();}
	public String playerName() {return strat.playerName();}
	public int choose(GreedOption[] options, int[] dice, 
			int bank, int[] scores, int round, int maxRounds, int playerNum){
		return strat.choose(options, dice, bank);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
