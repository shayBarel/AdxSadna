import tau.tac.adx.agents.CampaignData;
import tau.tac.adx.report.demand.CampaignOpportunityMessage;

/**
 * 
 */

/**
 * @author NAttar
 *
 */
public class BidderCampaignMinimum extends BidderCampaign {

	
	/** will compute the minimum bid possible for campagin.
	 * the bid Bn must satisfy : Bn > (Cr * Rcampaignmin)/Qn
	 * where : 
	 * Cr = campaign reach.
	 * Rcampaignmin = factor from spec. 
	 * Qn = our quality rating .
	 * @return
	 */
	public long GetMinimumBid (CampaignData campaign)
	{
		
		//get current quality rating of our agent .
		AgentData myagent = AgentData.GetInstance();
		double quality_rating = myagent.GetQualityRating();
		
		double reach = campaign.getReachImps() ;
		
		double RCampaignMin = 0.0001;
		
		double result = ((reach * RCampaignMin)/quality_rating) * 1000 + 1;
		return (long) result ;
		
	}
	
	/* (non-Javadoc)
	 * @see BidderCampaign#GenerateCampaignBid(tau.tac.adx.report.demand.CampaignOpportunityMessage)
	 */
	@Override
	public long GenerateCampaignBid(CampaignOpportunityMessage msg) {

		CampaignData cmp = new CampaignData(msg);
		
		return GetMinimumBid (cmp);
		
	}

}
