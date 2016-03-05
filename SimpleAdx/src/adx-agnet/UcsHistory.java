import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcsHistory {

	// this is only dummy for checking this is working well . the game number and it's data
	Map<Integer,AgentUcsData> GameStats; 
	
	UcsHistory(){
		
		GameStats = new HashMap<>();
	}
	
	public double getUcsDailylevel(int CurrentDay,int gameNum)
	{
		AgentUcsData agent = GameStats.get(gameNum);
		return agent.getUcsDailyPrice(CurrentDay);
	}
	
	public double getUcsDailyPrice(int CurrentDay,int gameNum)
	{
		AgentUcsData agent = GameStats.get(gameNum);
		return agent.getUcsDailyPrice(CurrentDay);

	}
	
	
}
