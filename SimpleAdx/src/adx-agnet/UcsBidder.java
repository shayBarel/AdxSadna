
//import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;



public class UcsBidder {
	
	double TargetUCS;
	double ImpressionLeft;
	double previousBid;
	double CurrentUcsLevel;
	
	double c1,c2,c3,c4,c5;



	// UCS DAILY RESULT ON array / classs 
	// 
	UcsHistory ucsHistroy;
	private final Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());	
	
	
public UcsBidder(UcsHistory ucsHistroy,double ImpressionLeft) {
		super();
		previousBid = 0.0;
		this.ImpressionLeft = ImpressionLeft;
		this.ucsHistroy = ucsHistroy;
	}

	// this astrategy is only for the first 20 days .
//	  public void setUSCAvrages(int NumOfDay, int gameNum)
//	  {	  
//	    double SumUSCLevel = 0.0;
//	    double sumPrice = 0.0;
//	    
//	    
//	    for (int i = 1; i < NumOfDay; i++)
//	    {
//
////	     if (agentData.UcsHistory.getCurrentAgentDataReport(i,gameNum) != null) {
////	        TempagentData= agentData.uscHistory.getCurrentAgentDataReport(i, gameNum);
////	     }
//	    
//	    
//	      SumUSCLevel += ucsHistroy.getUcsDailylevel(i,gameNum);
//	      sumPrice += ucsHistroy.getUcsDailyPrice(i,gameNum);
//	     
//	    }
//	    
//	      this.avgUSCLevel = (SumUSCLevel / NumOfDay);
//	      this.avgUcsPrice = (sumPrice / NumOfDay);
//	  }
	  
	  public double getPreviousBid() {
	return previousBid;
}

public void setPreviousBid(double previousBid) {
	this.previousBid = previousBid;
}

	public void CalculateTargetUCS(int NumOfDay,int gameNum)
	  {
		  if(getImpressionLeft()==0)
		  {
			  log.log(Level.FINE, "Ucs impression left is 0");

			  TargetUCS=0;
		  }
		  else if (getImpressionLeft()<5000)
		  {
			  log.log(Level.FINE, "Ucs impression left is 5000");

			  TargetUCS=0.65; 
		  }
		  else if (getImpressionLeft()<15000)
		  {
			  
			  log.log(Level.FINE, "Ucs impression left is 15000");

			  TargetUCS=0.75; 
		  }
		  else {
			  
			  log.log(Level.FINE, "Ucs impression left is more than 15000");

			  TargetUCS=0.85; 
		  }
		  
		  if (NumOfDay < 5){
			  
			  log.log(Level.FINE, "Ucs Num of days is 5");

			  TargetUCS = 0.95;
		  }	  
		  
	  }
	  
	  public double getTargetUCS(){
		  return TargetUCS;
	  }
	
	  
	  public double getUCSbid(int day,int gameNum){
		  CalculateTargetUCS(day,gameNum);
		  
		  //avoid bidding if target is 0 .
		  if (getTargetUCS() <= 0)
		  {
			  return 0 ;
		  }
		  
		   // this is the target value we need to get to ...
		  double ucsBid;
		  double ucsBid1;
		  
		  if (day < 20 ){
			  
			  log.log(Level.FINE, "UcsBid algo for days less than 20");
			  
			  ucsBid1 = Math.min(0.12 + 0.19*getTargetUCS()
					  + (0.1-day*0.016),0.12 -day*0.01
					  + this.getPreviousBid()*(1+(getTargetUCS()-this.getCurrentUcsLevel())));
			  	  			  
			  ucsBid = ucsBid1;
			  
		  }
		  else {
			  
			  log.log(Level.FINE, "UcsBid algo for days follow 20 ( regression ) ");

			  SimpleRegression regression = new SimpleRegression();
			  
			  /** regression implementation : **/
			  
			  for (int i=0 ; i<= day-1;i++)
			  {
				  UcsDailyReport udp = this.ucsHistroy.getUcsDaily(i);
				  
				  double lev = udp.getLevel();
				  double pri = udp.getPrice();
				  
				  log.fine(String.format("Ucs history for day:%d level: %f, price%f ", i, lev, pri));
				  
				  if (lev!=0 && pri!=0)
					  regression.addData(pri, lev);
				  
			  }
		 
			  double slope = regression.getSlope();
			  double intercept = regression.getIntercept();
			  
			  if (slope == 0 || slope == Double.NaN || intercept == Double.NaN){
				  log.log(Level.FINE, "Ucs regression slope/intercept is 0/NaN  got random value");  

				  ucsBid = Math.random()*0.5;
			  }
			  
			  else{
				  ucsBid = (getTargetUCS()-intercept)/slope;
				  
				  log.log(Level.FINE, String.format("target UCS level for next day(day %d) : %f", day, getTargetUCS()));

				  log.log(Level.FINE, String.format("Calculated UcsBid result/slope/inter are : %f %f %f",ucsBid,slope,intercept));

			  }
			  
					  
		  }
		  //previousBid = ucsBid;
		  
		  
		  log.log(Level.FINE, String.format("UcsBid result is : %f ",ucsBid));
		  
		  
		  // border of ucsbid
		  
		  
		  if (ucsBid > 0.4){
			  ucsBid = 0.4;
		  }
		  


		  return ucsBid;
		  
	  }

		public double getImpressionLeft() {
			return ImpressionLeft;
		}

		public void setImpressionLeft(double impressionLeft) {
			ImpressionLeft = impressionLeft;
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
