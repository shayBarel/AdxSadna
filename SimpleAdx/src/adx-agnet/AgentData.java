
/** 
 * a "global" class that contains all data about our agent .
 * implements a singleton behavior.
 * @author NAttar
 *
 */
public class AgentData 
{

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
