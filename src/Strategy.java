public abstract class Strategy implements Comparable
{	
	private boolean crashed = false;
	private double score = 0.0;
	public abstract String playerName();
	public abstract String author();
	private boolean ascending;
	public void setAscending(boolean isAscending) {
		ascending = isAscending;
	}
	public void setCrashed(){
		crashed= true;
	}
	public boolean crashed(){
		return crashed;
	}
	public void setScore(double newScore){
		score = newScore;
	}
	public double score(){
		return score;
	}
	public int compareTo(Object o){
		try{
			Strategy other = (Strategy)(o);
			
			//always puts crashed strategies at bottom of list
			boolean cr = crashed();
			boolean ocr = other.crashed();
			if (cr != ocr) {
				if (cr == ascending)
					return -1;
				else
					return 1;
			}
			/////
			
			
			else if (other.score() > this.score()){
				return -1;
			}
			else if(other.score() == this.score()){
				return other.playerName().compareTo(playerName());
			}
			else{
				return 1;
			}
		}
		catch(Exception e){
			return 0;
		}
	}
}
