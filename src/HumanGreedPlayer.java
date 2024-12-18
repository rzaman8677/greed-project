//Arseni Verner
//Chuck Ford
//Francis Hong
//Kevin Unglesbee

//#4

import java.io.*;
class HumanGreedPlayer extends GreedPlayer{
	private String name;
	public HumanGreedPlayer() {
		name = promptString("Enter your name: ", "Invalid input.");
	}
	public String name(){
		return name;
	}
	public String playerName(){
		return name+"'s Strategy";
	}
	public String author(){
		return name;
	}
	public boolean human(){
		return true;
	}
	public void newGame(int maxRounds, int playerNum){
		System.out.println("\nA new game has begun, " + name() + ".");
		System.out.println("There will be "+maxRounds+" rounds.");
		System.out.println("You are player number " + playerNum + ".");
	}
	public int choose(GreedOption[] options, int[] dice,
 	int bank, int[] scores, int round, int maxRounds, int playerNum){
 		int choice;
 		System.out.println("\n" + name() + ", your current bank is "
 		+bank+".");
 		System.out.println("The scores are:");
 		for (int i=0;i<scores.length;i++) {
 			System.out.print("Player "+(i+1)+" ");
 			
 			if (playerNum==i)
 				System.out.print("(" + name() + ") ");
 			
 			System.out.println("has "+scores[i]+" points.");
 		}
 		System.out.println("The current round is "+round+" out of "
 		+maxRounds+".");
 		String printDice="Your current dice are ";
 		for (int i=0;i<dice.length;i++)
 			printDice+=dice[i]+" ";
 		System.out.println(printDice);
 		System.out.println("\nYour choices are:");
 		for (int i=0;i<options.length;i++) {
 			System.out.print((i+1)+": ");
 			if (options[i].optionType() == GreedOption.SCORE)
 				System.out.print("Set aside ");
 			System.out.println(options[i]);
 		}
 		choice=promptPosInt("\nEnter your choice: ", 
 		"Invalid input. ", options.length)-1;	
 		return choice;
 	}
 	public void noScoringCombinations(int[] dice){
 		String die="\n" + name() + ", your dice are: ";
 		for (int i=0;i<dice.length;i++)
 			die+=dice[i]+" ";
 		System.out.println(die);
 		System.out.println("There are no scoring combinations.");  
 		System.out.println("Your turn is over.  No points were awarded.");
 	}
 	public void oneScoringCombination(int[] dice, 
 	ScoringCombination combination){
 		String die="\n" + name() + ", your dice are: ";
 		for (int i=0;i<dice.length;i++)
 			die+=dice[i]+" ";
 		System.out.println(die);
 		System.out.println(combination.toString()+
 		" is the only scoring combination.");
 		System.out.println("It has been chosen.");
 	}
 	public void gameOver(int[] scores, int playerNum, int winner){
 		System.out.println("\nThe game is over, " + name() + ".");
 		if (playerNum==winner)
 			System.out.println("You have won.");
 		else
 			System.out.println("Player "+winner+" has won the game");
 		for (int i=0;i<scores.length;i++){
 			if (i+1==playerNum)
 				System.out.println("You have scored "+
 				scores[i]+" points.");
 			else
 				System.out.println("Player "+(i+1)+" has scored "
 				+scores[i]+" points.");
 		}
 	}
 	private static int promptPosInt(String prompt,
 	String errorMessage) {
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
	private static int promptPosInt(String prompt,
	String errorMessage, int max) {
		boolean parsed = false;
		int num = 0;
		
		do {
			try {
				System.out.print(prompt);
				num = Integer.parseInt(inputString());
				
				if (num > 0 && num <= max)
					parsed = true;
				else
					System.out.print(errorMessage);
			} catch(Exception e) {
				System.out.print(errorMessage);
			}
		} while (!parsed);
		
		return num;
	}
	
	private static String promptString(String prompt,
	String errorMessage) {
		boolean parsed = false;
		String string = "";
		
		do {
			try {
				System.out.print(prompt);
				string = inputString();
				
				if (!string.equals(""))
					parsed = true;
				else
					System.out.print(errorMessage);
			} catch(Exception e) {
				System.out.print(errorMessage);
			}
		} while (!parsed);
		
		return string;
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
	
} 