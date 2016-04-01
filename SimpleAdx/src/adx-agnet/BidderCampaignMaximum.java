import java.util.logging.Logger;

import tau.tac.adx.report.demand.CampaignOpportunityMessage;

/**
 * 
 */

/**
 * @author NAttar
 *
 */
public class BidderCampaignMaximum extends BidderCampaign 
{

	
	/** will compute the maximum bid possible for campagin.
	 * the bid Bn must satisfy : Bn < Cr * Rcampaignmax * Qn
	 * where : 
	 * Cr = campaign reach.
	 * Rcampaignmax = factor from spec. 
	 * Qn = our quality rating .
	 * @return
	 */
	public long GetMaximumBid (CampaignData campaign)
	{
		
		//get current quality rating of our agent .
		AgentData myagent = AgentData.GetActiveAgentInstance();
		double quality_rating = myagent.GetQualityRating();
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		log.fine(String.format("computing max bid, current quality rating: %f", quality_rating));
		
		
		double reach = campaign.getReachImps() ;
		
		double RCampaignMax = 0.001;
		
		double result = (reach * RCampaignMax * quality_rating) * 1000 - 1 ;
		return (long) result ;
		
	}
	

	/* (non-Javadoc)
	 * @see BidderCampaign#GenerateCampaignBid(tau.tac.adx.report.demand.CampaignOpportunityMessage)
	 */
	@Override
	public long GenerateCampaignBid(CampaignOpportunityMessage msg, CompetitionData competition) 
	{

		CampaignData cmp = new CampaignData(msg);
		
		return GetMaximumBid (cmp);
		
	}
	
}
