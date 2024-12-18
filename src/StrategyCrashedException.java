public class StrategyCrashedException extends Exception
{
	int crashDex = -1;  //Changed from: int crashDex; to int crashDex = -1;  EBB 9-13-05
	StrategyCrashedException() {}
	
	StrategyCrashedException(int dex)
	{
		crashDex = dex;
	}
	
	int getCauseIndex()
	{
		return crashDex;
	}
}
