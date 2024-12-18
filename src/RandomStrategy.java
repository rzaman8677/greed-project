
public class RandomStrategy extends GreedStrategy {

	@Override
	public int choose(GreedOption[] options, int[] dice, int bank) {
		return (int)(Math.random()*options.length);
	}

	@Override
	public String playerName() {
		// TODO Auto-generated method stub
		return "Random Player";
	}

	@Override
	public String author() {
		// TODO Auto-generated method stub
		return "Random";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
