
public class StrategyEvaluator {

	public static int evaluateStrategy(GreedStrategy theStrategy, int turns){
		ComputerGreedStrategyPlayer player 
		= new ComputerGreedStrategyPlayer(theStrategy);
		int score
		= (new GreedGame(false)).evaluatePlayer(player, turns);
		return score;
	}
	
	public static double practiceGreed(){
		return (new GreedGame(true))
				.evaluatePlayer(new HumanGreedPlayer(), 1);
	}
	
	public static void main(String[] args) {
		GreedStrategy strat = new ZamanGreedStrategy();
		System.out.println(evaluateStrategy(strat, 100000));
	}

}
