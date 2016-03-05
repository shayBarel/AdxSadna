
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class UcsBidder {
	
	
	int UcsInitialBid;  // at start will be initialized to 0.9
	double UCSTargetStrategy = 1.0D;
	double rSquered;
	double maxAmount;
	double avgUSCTarget;
	double avgAmountspent;
	int currentDay;
	// UCS DAILY RESULT ON array / classs 
	// 

	  private void setUSCAvrages(int dayTo, int gameNum)
	  {
	    double sumTarget = 0.0D;double sumPrice = 0.0D;
	    for (int i = 1; i < dayTo; i++)
	    {
	      try
	      {
	        if (this.uscDataHistory.getReport(i) != null) {
	          this.dailyAgentData = ((UCSDailyReport)this.uscDataHistory.getReport(i, gameNum));
	        }
	      }
	      catch (Exception e)
	      {
	        this.log.info("error with report! ********\n ");
	      }
	      sumTarget += this.dailyAgentData.getUCSLevel();
	      sumPrice += this.dailyAgentData.getUCSPrice();
	      
	      this.avgUSCTarget = (sumTarget / dayTo);
	      this.avgAmountspent = (sumPrice / dayTo);
	    }
	  }
	//create simple regression class ( from apache and calculate the regression)
	public int CalculateUcsPriceFromRegression(){
		
		return 0;
	}
	
	
	public int CalculateUcsPriceFromAvg(){
		
	}
	
	
}
