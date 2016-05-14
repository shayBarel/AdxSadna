import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UcsHistory {

	
	int MAX_NUMBER_OF_DAYS = 1000;
	
	// this is only dummy for checking this is working well . the game number and it's data
	Map<Integer,UcsDailyReport> GameStats; 
	UcsHistoryFile ucsFILE;
	
	UcsHistory(){
		
		GameStats = new HashMap<Integer,UcsDailyReport>();

		//Initialize map for safety resons.
		
		for (int i=0 ; i<=MAX_NUMBER_OF_DAYS; i++)
		{
			GameStats.put(i, new UcsDailyReport(0,0,0,0));
		}
		
		ucsFILE = new UcsHistoryFile();
		
		
	}
	
	
	public UcsDailyReport getUcsDaily(int day)
	{
		return GameStats.get(day);
	}
	
	public void setUcsDaily(int day ,double level,double price,double target,double bid)
	{
		UcsDailyReport udp = new UcsDailyReport(level,price,target,bid);
		
		GameStats.put(day, udp);
		
		ucsFILE.AddToData(level, price, target, bid);
	}
	
}
