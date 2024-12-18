import java.io.*;
import java.util.*;

public class GreedGame {
	private boolean verbose;
	
	GreedGame(GreedPlayer player, boolean verboseMode) {
		verbose = verboseMode;
	}
	
	GreedGame(boolean verboseMode){
		this(null, verboseMode);
	}
	
	private static int[] rollRemainingDice(int numberOfDice) {
		int[] dice = new int[numberOfDice];
		
		for (int index = 0; index < numberOfDice; ++index)
			dice[index] = rndInt(1,6);
		
		return dice;
	}
	
	private void Turn(GreedPlayer player, int[] scores, int round,
	int maxRounds, int playerNum) {
		int remainingDice = 6;
		int bank = 0;
		boolean turnOver = false;
		boolean rolledBefore = false;
		boolean reRoll = false;
		
		do {
			int[] dice = rollRemainingDice(remainingDice);
			reRoll=false;
			
			if (verbose && !player.human()) {
				System.out.print("Dice rolls: ");
				
				for (int index = 0; index < dice.length; ++index)
					System.out.print(" " + dice[index]);
				
				System.out.println();
			}
			
			ScoringCombination[] combos =
			ScoringCombination.allContainedIn(dice);
			GreedOption[] choices = combos;
			GreedOption choice = null;
			
			if (choices.length == 0) {
				bank = 0;
				player.noScoringCombinations(dice);
				
				if (verbose && !player.human()) {
					System.out.print(
					"Turn over because there are no combinations:");
					
					for (int index = 0; index < dice.length; ++index)
						System.out.print(" " + dice[index]);
					
					System.out.println();
				}
				
				turnOver = true;
			} else {
				if (choices.length == 1) {
					choice = choices[0];
					player.oneScoringCombination(dice,
					(ScoringCombination)choice);
				}
				
				boolean hasBeenRemoved = false;
				reRoll = false;
				
				do {
					
					if (choice == null) {
						if (hasBeenRemoved) {
							choices = new GreedOption[combos.length+2];
							
							for (int index = 0; index < combos.length;
							++index)
								choices[index] = combos[index];
							
							choices[choices.length-2] =
							new GreedOption(GreedOption.ROLLAGAIN);
							choices[choices.length-1] =
							new GreedOption(GreedOption.ENDTURN);
						}
						
						int playerChoiceNum = player.choose(choices, dice,
							bank, scores, round, maxRounds, playerNum);
						if (playerChoiceNum >= 0 && playerChoiceNum <
						choices.length)
							choice = choices[playerChoiceNum];
						else
							choice = new GreedOption(GreedOption.ENDTURN);
					}
					
					if (verbose && !player.human()) {
						System.out.println("Choice: " + choice.toString());
					}
					
					if (choice.optionType() == GreedOption.SCORE) {
						hasBeenRemoved = true;
						
						dice = ((ScoringCombination)choice).remove(dice);
						remainingDice = dice.length;
						
						if (remainingDice == 0) {
							remainingDice = 6;
						}
						
						bank += ((ScoringCombination)choice).getValue();
						combos = ScoringCombination.allContainedIn(dice);
						
						if (hasBeenRemoved) {
							choices = new GreedOption[combos.length+2];
								
							for (int index = 0; index < combos.length;
							++index)
								choices[index] = combos[index];
							
							choices[choices.length-2] =
							new GreedOption(GreedOption.ROLLAGAIN);
							choices[choices.length-1] =
							new GreedOption(GreedOption.ENDTURN);
						} else if (rolledBefore) {
							choices = combos;
						}
						
						choice = null;
					} else if (choice.optionType() ==
					GreedOption.ENDTURN) {
						turnOver = true;
					} else if (choice.optionType() ==
					GreedOption.ROLLAGAIN) {
						reRoll = true;
					}
					
					if (choices.length<1) {
						turnOver = true;
					}
				} while (!turnOver && !reRoll);
			}
		} while (!turnOver);
		
		scores[playerNum] += bank;
	}
	
	public int evaluatePlayer(GreedPlayer player, int turns) {
		int[] scores = {0};
		
		for (int turnNum = 0; turnNum < turns; ++turnNum)
			Turn(player, scores, 1, 1, 0);
		
		return (int) (((float)(scores[0])) / ((float)turns));
	}
	
	public int evaluatePlayer(GreedPlayer player) {
		return evaluatePlayer(player, promptPosInt(
		"Enter number of turns to evaluate player with: ",
		"Invalid input. "));
	}
	
	public int[] play(GreedPlayer[] player, int maxRounds) {
		int[] scores = new int[player.length];
		
		for (int index = 0; index < scores.length; ++index)
			scores[index] = 0;
		
		for (int round = 1; round <= maxRounds; ++round) {
			int[] newOrder = newOrder(player.length);
			
			for (int index = 0; index < player.length;
			++index) {
				int playerNum = newOrder[index];
				Turn(player[playerNum], scores, round, maxRounds,
				playerNum);
			}
		}
		
		return scores;
	}
	
	public static String GreedGameBetweenHumanPlayers(int numPlayers) {
		GreedPlayer[] player = new GreedPlayer[numPlayers];
		GreedGame game = new GreedGame(null, false);
		
		for (int index = 0; index < numPlayers; ++index)
			player[index] = new HumanGreedPlayer();
		
		int rounds = promptPosInt("Enter number of rounds to play: ",
		"Invalid input. ");
		
		int[] scores = game.play(player, rounds);
		
		int maxScore = -1;
		String winner = "";
		
		for (int index = 0; index < scores.length; ++index)
			if (maxScore < scores[index]) {
				maxScore = scores[index];
				winner = player[index].name();
			}
		
		return winner;
	}
	
	public static String GreedGameBetweenHumanPlayers() {
		return GreedGameBetweenHumanPlayers(promptPosInt(
		"Enter number of players for the game: ", "Invalid input. "));
	}
	
	private static int promptPosInt(String prompt, String errorMessage) {
		boolean parsed = false;
		int num = 0;
		
		do {
			try {
				System.out.print(prompt);
				num = Integer.parseInt(inputString());
				
				if (num > 0)
					parsed = true;
				else
					System.out.print(errorMessage);
			} catch(Exception e) {
				System.out.print(errorMessage);
			}
		} while (!parsed);
		
		return num;
	}
	
	private static String inputString() {
		String inp = "";
		
		try {
			BufferedReader buff = new
			BufferedReader(new InputStreamReader(System.in));
			inp = buff.readLine();
		} catch(IOException e) {
			inp = "";
		}
		
		return inp;
	}
	
	private static int rndInt(int min, int max) {
		return (int) ((max - min + 1.) * Math.random()) + min;
	}
	
	public static void HumanComputerGreedGame(int[] thresholds) {
		int computers = promptPosInt("Enter number of simple " +
		"computer players: ", "Invalid input. ");
		int rounds = promptPosInt("Enter number of rounds to play: ",
		"Invalid input. ");
		
		GreedPlayer[] players = new GreedPlayer[computers + 1];
		
		for (int index = 0; index < computers; ++index)
			players[index] = null;//new SimpleComputerPlayer(thresholds);
		
		players[players.length-1] = new HumanGreedPlayer();
		
		GreedGame game = new GreedGame(null, true);
		
		int[] scores = game.play(players, rounds);
		
		System.out.println("\nScores: ");
		
		for (int index = 0; index < scores.length; ++index) {
			System.out.println(players[index].name() + ": " +
			scores[index]);
		}
	}
	
	public static int[] ComputerGreedCompetition(ComputerGreedPlayer
	players[], int games, int rounds) {
		int[] scores;
		int[] winners = new int[players.length];
		int crawlLength=50;
		int timeSinceLastCrawl = 0;
		int increment=games/crawlLength;
		String crawling = "=";
		System.out.println("");
		for (int block=1; block <= crawlLength; block++)
			System.out.print(crawling);
		System.out.println("");
		GreedGame game = new GreedGame(null, false);
		for (int index = 0; index < winners.length; ++index)
			winners[index] = 0;
		
		for (int gameNum = 0; gameNum < games; ++gameNum) {
			timeSinceLastCrawl++;
			if (timeSinceLastCrawl>=increment){
				System.out.print(crawling);
				timeSinceLastCrawl=0;
			}
			int maxScore = 0;
			scores = game.play(players, rounds);
			
			for (int index = 0; index < scores.length; ++index)
				if (scores[index] > maxScore)
					maxScore = scores[index];
			
			for (int index = 0; index < scores.length; ++index)
				if (scores[index] == maxScore)
					winners[index]++;
		}
		System.out.println("");
		return winners;
	}
	
	public static int[] newOrder(int size) {
		ArrayList<Integer> indexList = new ArrayList();
		for (int index = 0; index < size; ++index)
			indexList.add(index);
		
		int[] newOrder = new int[size];
		
		for (int index = 0; index < size; ++index)
			newOrder[index] = indexList.remove((int)
			(Math.random() * indexList.size()));
		
		return newOrder;
	}
	
	public static void main(String[] args){
		System.out.println("Your score was "
		+(new GreedGame(true)).evaluatePlayer(new 
				HumanGreedPlayer(),1));
	}
}