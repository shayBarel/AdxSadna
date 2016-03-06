
//import org.apache.commons.math3.stat.regression.SimpleRegression;

public class UcsBidder {
	
	
	int UcsInitialBid;  // at start will be initialized to 0.9
	double avgUSCLevel;
	double avgUcsPrice;
	int currentDay;
	double TargetUCS;
	double ImpressionLeft;
	double previousBid;
	double CurrentUcsLevel;



	// UCS DAILY RESULT ON array / classs 
	// 
	UcsHistory ucsHistroy;
	
	
	
public UcsBidder(UcsHistory ucsHistroy,double ImpressionLeft) {
		super();
		 avgUSCLevel = 0.0;
		 avgUcsPrice = 0.0;
		previousBid = 0.0;
		this.ImpressionLeft = ImpressionLeft;
	}

	// this astrategy is only for the first 20 days .
	  public void setUSCAvrages(int NumOfDay, int gameNum)
	  {	  
	    double SumUSCLevel = 0.0;
	    double sumPrice = 0.0;
	    
	    
	    for (int i = 1; i < NumOfDay; i++)
	    {

//	     if (agentData.UcsHistory.getCurrentAgentDataReport(i,gameNum) != null) {
//	        TempagentData= agentData.uscHistory.getCurrentAgentDataReport(i, gameNum);
//	     }
	    
	    
	      SumUSCLevel += ucsHistroy.getUcsDailylevel(i,gameNum);
	      sumPrice += ucsHistroy.getUcsDailyPrice(i,gameNum);
	     
	    }
	    
	      this.avgUSCLevel = (SumUSCLevel / NumOfDay);
	      this.avgUcsPrice = (sumPrice / NumOfDay);
	  }
	  
	  public void CalculateTargetUCS(int NumOfDay,int gameNum)
	  {
		  if(getImpressionLeft()==0)
		  {
			  TargetUCS=0;
		  }
		  else if (getImpressionLeft()<5000)
		  {
			  TargetUCS=0.65; 
		  }
		  else if (getImpressionLeft()<15000)
		  {
			  TargetUCS=0.75; 
		  }
		  else {
			  TargetUCS=0.85; 
		  }
		  
		  if (NumOfDay < 5){
			  TargetUCS = 0.95;
		  }	  
		  
	  }
	  
	  public double getTargetUCS(){
		  return TargetUCS;
	  }
	
	  
	  public double getUCSbid(int day,int gameNum){
		  CalculateTargetUCS(day,gameNum);
		  double ucsBid;
		  double ucsBid1;
		  double ucsBid2;
		  
		  if (day < 20 ){
			  ucsBid1 = Math.min(0.12 + 0.19*getTargetUCS()
					  + (0.1-day*0.016),0.12 -day*0.01
					  + previousBid*(1+(getTargetUCS()-avgUSCLevel)));
			  
			  ucsBid2 = Math.min(0.12 + 0.19*getTargetUCS()
			  + (0.1-day*0.016),0.12 -day*0.01
			  + previousBid*(1+(getTargetUCS()-CurrentUcsLevel)));
			  
			  ucsBid = Math.min(ucsBid1, ucsBid2);
			  
		  }
		  else {
			  
			  ucsBid =  Math.random()*4; // to be replaced by regression.
			  
		  }
		  previousBid = ucsBid;
		  return ucsBid;
		  
	  }

		public double getImpressionLeft() {
			return ImpressionLeft;
		}

		public void setImpressionLeft(double impressionLeft) {
			ImpressionLeft = impressionLeft;
		}

	  
	  public double getUSCAverageLevel()
	  {
		  return avgUSCLevel;
	  }
	  
	  public double getUSCAveragePrice()
	  {
		  return avgUcsPrice;
	  }


		public double getCurrentUcsLevel() {
			return CurrentUcsLevel;
		}

		public void setCurrentUcsLevel(double currentUcsLevel) {
			CurrentUcsLevel = currentUcsLevel;
		}
	  
	  
	//create simple regression class ( from apache and calculate the regression)
//	public int CalculateUcsPriceFromRegression(){
//		
//		return 0;
//	}      
// need to do it on seperate class
	

	
	
}