import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcsHistory {

	
	int MAX_NUMBER_OF_AGENTS = 100;
	
	// this is only dummy for checking this is working well . the game number and it's data
	Map<Integer,AgentUcsData> GameStats; 
	
	UcsHistory(){
		
		GameStats = new HashMap<>();
		
		for (int i=0 ; i<=MAX_NUMBER_OF_AGENTS; i++)
		{
			GameStats.put(i, new AgentUcsData());
		}
		
	}
	
	public double getUcsDailylevel(int CurrentDay,int gameNum)
	{
		AgentUcsData agent = GameStats.get(gameNum);
		return agent.getUcsDailylevel(CurrentDay);
	}
	
	public double getUcsDailyPrice(int CurrentDay,int gameNum)
	{
		AgentUcsData agent = GameStats.get(gameNum);
		return agent.getUcsDailyPrice(CurrentDay);

	}
	
	
}
