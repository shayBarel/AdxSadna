import java.util.logging.Logger;

/** 
 * a "global" class that contains all data about our agent .
 * implements a singleton behavior.
 * @author NAttar
 *
 */
public class AgentData 
{
	//Implementation of singleton (optional).
	private static AgentData _instance = null ;

	public static AgentData GetActiveAgentInstance() 
	{
		if(_instance == null) 
		{
			_instance = new AgentData();
		}
		return _instance;
	}
	
	/**
	 * reset the instance (is called between simulations).
	 */
	public static void ResetAgent ()
	{
		_instance = null; 
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		log.fine("agent data instance was reset .");
	}
	
	//end Implementation of singleton.
	///////////////////////////////

	
	//ctor 
	public AgentData() {}
		
		
		
	//////////////////////////////
	/// agent data & capabilities.
	//////////////////////////////
	
	double _QualityRating = 1.0;
	double _ContractBidCompetitionLevel =  GameFactorDefaults.CONTRACT_BIDS_COMPETITION_LEVEL_INITIAL ;
	
	public double GetContractBidCompetitionLevel() {
		return _ContractBidCompetitionLevel;
	}

	public void SetContractBidCompetitionLevel(double _ContractBidCompetitionLevel) {
		this._ContractBidCompetitionLevel = _ContractBidCompetitionLevel;
	}

	public double GetQualityRating () 
	{
		return _QualityRating;
	}
	
	public void SetQualityRating (double new_qr) 
	{
		_QualityRating = new_qr;
	}
	
	

	
}
