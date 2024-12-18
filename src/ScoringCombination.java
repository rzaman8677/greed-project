/*Eric Bell
 *Period 4B
 *Computer Science 4 APGT
 *Greed Project 2*/
 import java.util.*;

class ScoringCombination extends GreedOption{
	private int value;
	private int[] combo;
	private static ScoringCombination[] allCombos= null;
	ScoringCombination(int[] dice, int value){
		super(2);
		this.value= value;
		this.combo = dice;
	}
	public int optionType(){
		return GreedOption.SCORE;
	}
	public boolean contains(int[] dice){
		boolean contains=true;
		boolean[] used = new boolean[dice.length];
		for (int i=0;i<used.length;i++) used[i]=false;
		for (int comboDie=0; comboDie<combo.length;comboDie++){
			boolean found = false;
			for (int rolledDie=0; rolledDie<dice.length && !found; rolledDie++){
				if (!used[rolledDie] && dice[rolledDie]==combo[comboDie]){
					found=true;
					used[rolledDie]=true;
				}
			}
			contains = contains && found;
		}
		return contains;
		/* Old version
		Arrays.sort(dice);
		String answer = "";
		String check = "";
		boolean finalAnswer = false;
		answer = construct(combo);
		check = construct(dice);
		if (check.indexOf(answer) > -1){
			finalAnswer =true;
		}
		return finalAnswer;
		*/
	}
	public int[] remove(int[] dice){
		Arrays.sort(dice);
		String answer = "";
		String check = "", toInt="";
		answer = construct(combo);
		check = construct(dice);
		int start = check.indexOf(answer);
		int count=0;
		int[] finalAnswer=null;
		if (start !=-1)
			toInt = check.substring(0,start) + check.substring(start+answer.length());
		finalAnswer = new int[toInt.length()];
		for (count =0; count < toInt.length(); count++){
			finalAnswer[count] = Integer.parseInt(toInt.substring(count, count+1));
		}
		return finalAnswer;
	}
	private String construct(int[] myCombo){
		String check = "";
		for (int count = 0; count <myCombo.length; count++){
			check += String.valueOf(myCombo[count]);
		}
		return check;
	}
	
	public int numDice(){
		return combo.length;
	}
	public int getValue(){
		return value;
	}
	public int getDie(int n){
		return combo[n];
	}
	public String toString(){
		String answer ="";
		if (combo.length ==1){
			answer+= "one ";
		}
		answer+= construct(combo);
		answer += " (" + String.valueOf(value) + " points)";
		return answer;
	}
	public static ScoringCombination[] allCombination(){
		if (allCombos==null){
			int[][] combo = 
				{{5},{1},
					{2,2,2},{3,3,3},{4,4,4},{5,5,5},{6,6,6},
					{1,1,1,1},{1,2,3,4,5,6},
					{1,1,2,2,3,3}, {2,2,3,3,4,4}, {3,3,4,4,5,5}, {4,4,5,5,6,6},
					{1,1,1,1,1,1},{2,2,2,2,2,2},{3,3,3,3,3,3},{4,4,4,4,4,4},{5,5,5,5,5,5},{6,6,6,6,6,6}};
			int[] values = {50, 100, 200, 300, 400, 500, 600, 1000, 1000, 
					2000, 2000, 2000, 2000,
					5000, 5000, 5000, 5000, 5000, 5000};
			ScoringCombination[] scArray = new ScoringCombination[combo.length];
			ScoringCombination sc;
			for (int count= 0; count < combo.length; count++){
				sc = new ScoringCombination(combo[count], values[count]);
				scArray[count]=sc;
			}
			allCombos=scArray;
		} 
		return allCombos;
	}
	public static ScoringCombination[] allContainedIn(int[] dice){
		ScoringCombination[] scs = allCombination();
		boolean[] answers = new boolean[scs.length];
		int numberOfAnswers = 0;
		for (int count = 0; count < scs.length; count++){
			if (scs[count].contains(dice)){
				answers[count]= true;
				numberOfAnswers++;
			}
		}
		ScoringCombination[] answer = new ScoringCombination[numberOfAnswers];
		for (int count =0; count < scs.length; count++){
			if (answers[count]){
				answer[--numberOfAnswers] = scs[count];
			}
		}
		return answer;	
	}
	
	public static int[] makeArrayFromString(String s){
		int[] ret=new int[s.length()];
		for (int i=0; i<s.length();i++)
			ret[i]=s.charAt(i)-'0';
		return ret;
	}
	
	public static boolean testContainedIn(String dice, String combo, boolean expected){
		int d[] = makeArrayFromString(dice);
		int c[] = makeArrayFromString(combo);
		ScoringCombination sc = new ScoringCombination(c,0);
		boolean found = sc.contains(d);
		System.out.print("Is "+dice+" contained in "+combo+"? ");
		if (found)
			System.out.print("Yes. ");
		else
			System.out.print("No. ");
		boolean succeeded = found == expected;
		if (succeeded)
			System.out.println("ok");
		else
			System.out.println("*** BAD!!! ***");
		
		return succeeded;
	}
	
	public static void main (String args[]){
		int[] combo ={2,2,2};
		int[] first ={3,2,2,2};
		int[] second = {2,2,2,3};
		int[] third = {3,3,2,2,3};
		int[] fourth = {1};
		int[] fifth = {3,2,2,2,3};
		int[] sixth = {3,2,3,2,3,2};
		int[] seventh = {1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6};
		ScoringCombination sc = new ScoringCombination(combo,200);
		System.out.println("Is 222 in: ");
		System.out.println ("222" + sc.contains(combo));
		System.out.println ("3222" + sc.contains(first));
		System.out.println ("2223" + sc.contains(second));
		System.out.println ("33223" + sc.contains(third));
		System.out.println ("1" + sc.contains(fourth));
		System.out.println ("32223" + sc.contains(fifth));
		System.out.println ("323232" + sc.contains(sixth));
		System.out.println();
		System.out.println("Removing numbers");
		System.out.println();
		System.out.println("222" + c(sc.remove(combo)));
		System.out.println("3222" +c(sc.remove(first)));
		System.out.println("2223" +c(sc.remove(second)));
		System.out.println("32223" +c(sc.remove(fifth)));
		System.out.println("323232" +c(sc.remove(sixth)));
		testContainedIn("51515","555",true);
		testContainedIn("51511","555",false);
		testContainedIn("55511","555",true);
		testContainedIn("11555","555",true);
		testContainedIn("5115","555",false);
		testContainedIn("555","555",true);
		testContainedIn("55","555",false);
		testContainedIn("5","555",false);
		testContainedIn("5","555",false);
		testContainedIn("1","555",false);
		testContainedIn("152535","555",true);
		testContainedIn("525351","555",true);
		testContainedIn("155255","555",true);
		testContainedIn("5555","555",true);
		testContainedIn("555555","555",true);
		testContainedIn("511111","5",true);
		testContainedIn("11151","5",true);
		testContainedIn("11","5",false);
		testContainedIn("12346","5",false);
		testContainedIn("111111","5",false);
		testContainedIn("555555","555555",true);
		testContainedIn("555556","555555",false);
		testContainedIn("655555","555555",false);
		testContainedIn("556555","555555",false);
		testContainedIn("312465","123456",true);
		testContainedIn("123456","123456",true);
		testContainedIn("613245","123456",true);
		testContainedIn("512346","123456",true);
		testContainedIn("312466","123456",false);
		testContainedIn("312165","123456",false);
		testContainedIn("332465","123456",false);
		testContainedIn("312462","123456",false);
		testContainedIn("312265","123456",false);
							
		System.out.println();
		System.out.println("Printing combo");
		System.out.println();
		System.out.println(sc);
		System.out.println();
		System.out.println("Printing all combos");
		System.out.println();
		ScoringCombination[] scs = allCombination();
		for (int count= 0; count < scs.length; count++){
			System.out.println(scs[count]);
		}
		System.out.println("Printing...");
		scs = allContainedIn(seventh);
		for (int count= 0; count < scs.length; count++){
			System.out.println(scs[count]);
		}
	}
	private static String c(int[] combo){
		String check = "";
		for (int count = 0; count <combo.length; count++){
			check += String.valueOf(combo[count]);
		}
		return check;
	}
}
