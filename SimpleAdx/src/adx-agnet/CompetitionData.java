/**
 * class that holds all data about a competition . 
 * @author NAttar
 *
 */

import java.util.*;

public class CompetitionData 
{

	/**
	 * We maintain a collection (mapped by the campaign id) of the campaigns won
	 * by our agent.
	 */
	Map <Integer, CampaignData> _myCampaigns;
	//saves other agents campaigns .
	Map <Integer, CampaignData> _othersCampaigns;
	
	
	public Map<Integer, CampaignData> GetMyCampaigns() 
	{
		return _myCampaigns;
	}

	public void SetMyCampaigns(Map<Integer, CampaignData> myCampaigns) 
	{
		this._myCampaigns = myCampaigns;
	}

	public CompetitionData ()
	{
		_othersCampaigns = new HashMap<Integer, CampaignData>();
		_myCampaigns = new HashMap<Integer, CampaignData>();
	}
	
	public Map <Integer , CampaignData> GetOthersCampaigns() 
	{
		return _othersCampaigns;
	}

	public void SetOthersCampaigns(Map <Integer , CampaignData> allCampaigns) 
	{
		_othersCampaigns = allCampaigns;
	}
	
	
}
