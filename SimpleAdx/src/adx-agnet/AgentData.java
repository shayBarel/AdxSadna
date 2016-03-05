
/** 
 * a "global" class that contains all data about our agent .
 * may be replaced by a singleton.
 * @author NAttar
 *
 */
public class AgentData 
{
	static double []UcsDailylevel; // should be initialized with all zero
	static double []UcsDailyPrice; // same
	static UcsHistory ucsHistory; // history of the game

	public static double getUcsDailylevel(int i) {
		return UcsDailylevel[i];
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

	static double _QualityRating = 1.0;
	
	
	public static double GetQualityRating () 
	{
		return _QualityRating;
	}
	
	public static void SetQualityRating (double new_qr) 
	{
		_QualityRating = new_qr;
	}
	
}
