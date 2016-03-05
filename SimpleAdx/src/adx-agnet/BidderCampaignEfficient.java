
import java.util.Random;

import tau.tac.adx.agents.CampaignData;
import tau.tac.adx.report.demand.CampaignOpportunityMessage;

public class BidderCampaignEfficient extends BidderCampaign
{
	
	
	public BidderCampaignEfficient ()
	{
		super();
	}

	
	/*
	 * The campaign requires com.getReachImps() impressions. The competing
	 * Ad Networks bid for the total campaign Budget (that is, the ad
	 * network that offers the lowest budget gets the campaign allocated).
	 * The advertiser is willing to pay the AdNetwork at most 1$ CPM,
	 * therefore the total number of impressions may be treated as a reserve
	 * (upper bound) price for the auction.
	 */
	public long GenerateCampaignBid(CampaignOpportunityMessage msg) 
	{
		
		CampaignData cmp = new CampaignData(msg);
		
		long cmpimps = msg.getReachImps();
		//System.out.println("######## server demanded: " + cmpimps);
		//Random random = new Random();
		//long cmpBidMillis = random.nextInt((int)cmpimps);
		
		long cmpBidMillis = 0;
		double cmpBidMillisMinimum = 0;
		
		
		//assumption- we are interested in more campaigns.
	
		//make a bid on the campaign.
		//based on doubling the target impression amount by a fixed fraction.
		//(try to be lower than others)
		cmpBidMillisMinimum = ((0.1666 * (double) cmpimps)) + 1 ;
		cmpBidMillis = (long) cmpBidMillisMinimum;
	
		
		return cmpBidMillis;
	
	}
	

}
