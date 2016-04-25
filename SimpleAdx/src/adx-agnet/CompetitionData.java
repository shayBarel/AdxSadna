/**
 * class that holds all data about a competition . 
 * @author NAttar
 *
 */

import java.util.*;

public class CompetitionData 
{

	
	public CompetitionData ()
	{
		_othersCampaigns = new HashMap<Integer, CampaignData>();
		_myCampaigns = new HashMap<Integer, CampaignData>();
	}
	
	
	/**
	 * We maintain 2 collections (2 disjoint sets),
	 *  mapped by the campaign id: 
	 *  1. one for campaigns won by our agent.
	 *  2. one for campaigns won by other agents.
	 * 
	 */
	Map <Integer, CampaignData> _myCampaigns;
	//saves other agents campaigns .
	Map <Integer, CampaignData> _othersCampaigns;
	
	
	/** 
	 * build a map of all campagins
	 * (union of the 2 collections) 
	 * @return
	 */
	public Map<Integer, CampaignData> GetAllCampaigns ()
	{
		Map<Integer, CampaignData> allCampaigns = new HashMap<Integer, CampaignData> ();
		allCampaigns.putAll(GetMyCampaigns());
		allCampaigns.putAll(GetOthersCampaigns());
		return allCampaigns;
	}
	
	public Map<Integer, CampaignData> GetMyCampaigns() 
	{
		return _myCampaigns;
	}
	
	//calculate the number of impressions left for campaigns,
	//that are still active in dayBiddingFor .
	public double TatalImpLeft(int dayBiddingFor){
		
		Map<Integer, CampaignData> map = GetMyCampaigns();
		double sum = 0 ;
		   Iterator it = map.entrySet().iterator();
		   
			System.err.println("######## MAP SIZE IS : "+map.size()); 

		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        CampaignData camp = (CampaignData)pair.getValue();
		        //sum campaign's left impressions
		        //only if campaign is still active in time period. 
		        if (camp.getDayEnd() >= dayBiddingFor)
		        {
		        	sum += camp.impsTogo();
		        }
		        //it.remove(); // avoids a ConcurrentModificationException
		            
		    }
	
		return sum;
	}

	public void SetMyCampaigns(Map<Integer, CampaignData> myCampaigns) 
	{
		this._myCampaigns = myCampaigns;
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
