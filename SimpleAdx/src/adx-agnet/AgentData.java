
/** 
 * a "global" class that contains all data about our agent .
 * implements a singleton behavior.
 * @author NAttar
 *
 */
public class AgentData 
{
	static double []UcsDailylevel; // should be initialized with all zero ( at start of the game )
	static double []UcsDailyPrice; // same
	static double reachedImpression;

	public static double getUcsDailylevel(int i) {
		return UcsDailylevel[i];
	}
	
	

	public static double getReachedImpression() {
		return reachedImpression;
	}



	public static void setReachedImpression(double reachedImpression) {
		AgentData.reachedImpression = reachedImpression;
	}



	public static void setUcsDailylevel(double ucsDailylevel,int i) {
		UcsDailylevel[i] = ucsDailylevel;
	}

	public static double getUcsDailyPrice(int i) {
		return UcsDailyPrice[i];
	}

	public static void setUcsDailyPrice(double ucsDailyPrice,int i) {
		UcsDailyPrice[i] = ucsDailyPrice;
	}

	//Implementation of singleton.
	private static AgentData _instance = null ;

	protected AgentData() {
		//prevent instantiation
	}
	
	public static AgentData GetInstance() 
	{
		if(_instance == null) 
		{
			_instance = new AgentData();
		}
		return _instance;
	}
	
	//end Implementation of singleton.
	///////////////////////////////
	
	//////////////////////////////
	/// agent data & capabilities.
	//////////////////////////////

	
	double _QualityRating = 1.0;
	
	public double GetQualityRating () 
	{
		return _QualityRating;
	}
	
	public void SetQualityRating (double new_qr) 
	{
		_QualityRating = new_qr;
	}
	
	

	
}
