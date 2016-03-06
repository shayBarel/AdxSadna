
public class AgentUcsData {

	
	 double []UcsDailylevel; // should be initialized with all zero ( at start of the game )
	 double []UcsDailyPrice; // same
	
	public  double getUcsDailylevel(int i) {
		return UcsDailylevel[i];
	}
	public  void setUcsDailylevel(double ucsDailylevel,int i) {
		UcsDailylevel[i] = ucsDailylevel;
	}
	public  double getUcsDailyPrice(int i) {
		return UcsDailyPrice[i];
	}
	public  void setUcsDailyPrice(double ucsDailyPrice,int i) {
		UcsDailyPrice[i] = ucsDailyPrice;
	}


}
