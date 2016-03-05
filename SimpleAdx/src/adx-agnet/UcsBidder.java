
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class UcsBidder {
	
	
	int UcsInitialBid;  // at start will be initialized to 0.9
	double avgUSCLevel;
	double avgUcsPrice;
	int currentDay;
	// UCS DAILY RESULT ON array / classs 
	// 
	AgentData agentData;
	
	
	
// this astrategy is only for the first 20 days .
	  public void setUSCAvrages(int NumOfDay, int gameNum)
	  {
		  
		AgentData TempagentData;
		  
	    double SumUSCLevel = 0.0;
	    double sumPrice = 0.0;
	    
	    
	    for (int i = 1; i < NumOfDay; i++)
	    {

	     if (agentData.UcsHistory.getCurrentAgentDataReport(i,gameNum) != null) {
	        TempagentData= agentData.uscHistory.getCurrentAgentDataReport(i, gameNum);
	     }
	    
	    
	      SumUSCLevel += TempagentData.getUcsDailylevel(i);
	      sumPrice += TempagentData.getUcsDailyPrice(i);
	      

	    }
	    
	      this.avgUSCLevel = (SumUSCLevel / NumOfDay);
	      this.avgUcsPrice = (sumPrice / NumOfDay);
	  }
	  
	  public double getUSCAverageLevel()
	  {
		  return avgUSCLevel;
	  }
	  
	  public double getUSCAveragePrice()
	  {
		  return avgUcsPrice;
	  }

	  
	  
	  
	//create simple regression class ( from apache and calculate the regression)
//	public int CalculateUcsPriceFromRegression(){
//		
//		return 0;
//	}      
// need to do it on seperate class
	

	
	
}
