import tau.tac.adx.report.demand.CampaignOpportunityMessage;

public abstract class BidderCampaign 
{

	public BidderCampaign() {
		super();
	}
	
	//supposed to generate the campaign bid .
	abstract public long GenerateCampaignBid(CampaignOpportunityMessage msg);

}