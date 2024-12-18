/*Eric Bell
 *Period 4b
 *Computer Science 4 APGT
 *Greed Project 1*/
class GreedOption {
	public final static int ENDTURN=0;
	public final static int ROLLAGAIN=1;
	public final static int SCORE=2;
	private int type;
	private String answer = "";
	GreedOption(int type){
		this.type = type;
		setAnswer();
	}
	private void setAnswer(){
		if (type == 0){
			answer= "End your turn";
		}
		else if (type == 1){
			answer = "Roll your remaining dice";
		}
	}
	public int optionType(){
		return type;
	}
	public String toString(){
		return answer;
	}
}