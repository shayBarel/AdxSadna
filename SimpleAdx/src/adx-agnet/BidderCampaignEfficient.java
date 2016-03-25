
import java.util.Random;

import tau.tac.adx.report.demand.CampaignOpportunityMessage;
import tau.tac.adx.report.adn.MarketSegment;
import java.util.*;

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
	public long GenerateCampaignBid(CampaignOpportunityMessage msg, CompetitionData competition) 
	{
		
		CampaignData cmp = new CampaignData(msg);
		
		long cmpimps = msg.getReachImps();
		//System.out.println("######## server demanded: " + cmpimps);
	

		//get the segment of campaign (only the first)
		//handle couple of segments.
		Set<MarketSegment> segments = cmp.getTargetSegment();
		//get start and end days 
		int dayStart = (int) cmp.getDayStart();
		int dayEnd =  (int) cmp.getDayEnd();

		//get campaigns in market 
		Map<Integer,CampaignData> market = competition.GetAllCampaigns();
		
		//call price index class, to compute price.
		double pi = PI_indicator.popularityMultiSegmentsMultiDays(segments, market, dayStart, dayEnd);
		return (long) pi;
	
	}
	

}
