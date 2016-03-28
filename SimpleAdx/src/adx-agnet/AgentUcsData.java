
public class AgentUcsData {

	int MAX_NUMBER_OF_AGENTS = 100;
	
	 double UcsDailylevel[] = new double[MAX_NUMBER_OF_AGENTS]; // should be initialized with all zero ( at start of the game )
	 double UcsDailyPrice[] = new double[MAX_NUMBER_OF_AGENTS]; // same
	double reachedImpression;

	
	public AgentUcsData() {
		
		for ( int i=0;i<MAX_NUMBER_OF_AGENTS;i++)
		{
			UcsDailylevel[i] = 0;
			UcsDailyPrice[i] = 0;
		}
	}
	
	 
		public double getReachedImpression() {
			return reachedImpression;
	}
		public void setReachedImpression(double reachedImpression) {
			this.reachedImpression = reachedImpression;
	}
		
		
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
