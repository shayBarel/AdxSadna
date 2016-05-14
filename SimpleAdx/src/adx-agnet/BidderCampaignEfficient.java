

import java.util.logging.Logger;

import tau.tac.adx.report.demand.CampaignOpportunityMessage;
import tau.tac.adx.report.adn.MarketSegment;
import java.util.*;

public class BidderCampaignEfficient extends BidderCampaign {

	public BidderCampaignEfficient() {
		super();
	}

	/*
	 * The campaign requires com.getReachImps() impressions. The competing Ad
	 * Networks bid for the total campaign Budget (that is, the ad network that
	 * offers the lowest budget gets the campaign allocated). The advertiser is
	 * willing to pay the AdNetwork at most 1$ CPM, therefore the total number
	 * of impressions may be treated as a reserve (upper bound) price for the
	 * auction.
	 */
	public long GenerateCampaignBid(CampaignOpportunityMessage msg, CompetitionData competition) {

		Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
		
		
		CampaignData pending_campaign = new CampaignData(msg);

		log.fine(String.format("computing optimal contract bid for campaign %d. ", pending_campaign.id));
	
		//tmp fix - avoid short campaign at start
		if (pending_campaign.getDayStart()<GameFactorDefaults.INITIAL_DAYS_HIGH_COMPETITION
				&& pending_campaign.getDayEnd()<GameFactorDefaults.INITIAL_DAYS_HIGH_COMPETITION)
		{
			log.fine(String.format("campaign is at start of game , giving max bid "));
			BidderCampaignMaximum max_bidder = new BidderCampaignMaximum();
			return max_bidder.GetMaximumBid(pending_campaign);
		}

		// get the segment of campaign (only the first)
		// handle couple of segments.
		Set<MarketSegment> segment = pending_campaign.getTargetSegment();
		// get start and end days
		int dayStart = (int) pending_campaign.getDayStart();
		int dayEnd = (int) pending_campaign.getDayEnd();

		// get campaigns in market
		Map<Integer, CampaignData> market = competition.GetAllCampaigns();
		market.put(pending_campaign.id, pending_campaign); // consider also the
															// pending campaign

		// call price index class, to compute price.
		double pi = PI_indicator.popularitySegmentMultiDays(pending_campaign, segment, market, dayStart, dayEnd);

		// update according to competition factor .
		double competition_level = AgentData.GetActiveAgentInstance().GetContractBidCompetitionLevel();
		double tmpResult = pi / competition_level; // the more competitive the lower the price
		
		log.fine(String.format("computed bid for campaign %d. price index: %f, "
				+" competition level: %f, final proposed bid: %f", 
				pending_campaign.id, pi, competition_level, tmpResult));
		

		// fix according to min.max bound
		long tmpResult2 = FixBidMinimumMaximumBound(pending_campaign, tmpResult);

		
		//get more extreme result (minimum or maximum)
		long result_bid = BringBidToMinOrMaxCompetitive(pending_campaign, tmpResult2);
		
		
		
		return result_bid;

	}

	private long FixBidMinimumMaximumBound(CampaignData pending_campaign, double original_bid) {

		// lower than minimum ?
		BidderCampaignMinimum min_bidder = new BidderCampaignMinimum();
		long min_bid = min_bidder.GetMinimumBid(pending_campaign);
		if (original_bid < min_bid) {
			Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
			log.fine(String.format(
					"tried to bid lower than minimum on campaign."
							+ " campaign id: %d, original bid: %f, new bid (minimum):%d",
					pending_campaign.id, original_bid, min_bid));
			return min_bid;
		}

		// higher than maximum ?
		BidderCampaignMaximum max_bidder = new BidderCampaignMaximum();
		long max_bid = max_bidder.GetMaximumBid(pending_campaign);
		if (original_bid > max_bid) {
			Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
			log.fine(String.format(
					"tried to bid higher than maximum on campaign."
							+ " campaign id: %d, original bid: %f, new bid (maximum):%d",
					pending_campaign.id, original_bid, max_bid));
			return max_bid;
		}

		// original bid is between the bounds ,
		// return it .
		return (long) original_bid;

	}

	
	/** 
	 * try to get more extreme results (bring to minimum or maximum) .
	 * note!: assumes bid is already between maximum and minimum . 
	 * @param pending_campaign
	 * @param original_bid
	 * @return
	 */
	private long BringBidToMinOrMaxCompetitive(CampaignData pending_campaign, double original_bid)
	{
		Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
		
		BidderCampaignMinimum min_bidder = new BidderCampaignMinimum();
		long min_bid = min_bidder.GetMinimumBid(pending_campaign);
			
		BidderCampaignMaximum max_bidder = new BidderCampaignMaximum();
		long max_bid = max_bidder.GetMaximumBid(pending_campaign);

		//take the average between min bid and max bid .
		double mid_point = (min_bid + max_bid) / 2 ;
		if (original_bid > mid_point)
		{
			//bring to max .
			log.fine(String.format("brought contract bid to max. original: %f, max: %d, mid point: %f", original_bid, max_bid, mid_point));
			return max_bid;
		}
		else
		{
			//bring to min .
			log.fine(String.format("brought contract bid to min. original: %f, max: %d, mid point: %f", original_bid, min_bid, mid_point));
			return min_bid ;
		}
			
		
	}
	
}
