import java.util.logging.Logger;

import tau.tac.adx.ads.properties.AdType;
import tau.tac.adx.devices.Device;
import tau.tac.adx.props.AdxQuery;

public class BidderImpressions 
{

	public static final double budgetFraction = 0.5 ;
	
	public static final double anotherFactor = 3.0 ;
	
	public static final double IMPRESSION_URGENCY_FACTOR = 2.5 ;
	
	public static final double MIN_PERCENT_COMPLETION = 0.85 ;
	
	double GenerateImpressionBid (CampaignData campaign, AdxQuery query, int dayBiddingFor)
	{
		
		
		Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
		log.fine(String.format("computing price for impressions . campagin : %s query: %s.",
				campaign.toString(), query.toString()));
		
		//needed reach 
		int impsToGo = campaign.impsTogo();
		log.fine(String.format("impressions to go: %d", impsToGo));
		
		//percentage of completion so far
		double percentCompletion = campaign.getStats().getTargetedImps()/((double)campaign.getReachImps());
		log.fine(String.format("percent of completion: %f", percentCompletion));
		
		
		//compute estimated completion percent , according to campaign rate so far.
		double estimatedPercentNextDay = 
				ComputeEstimatedPercentNextDayByRate (campaign, percentCompletion, dayBiddingFor) ;
		
		//try to limit campaign with good rate of completion
		if (estimatedPercentNextDay > GameFactorDefaults.IMPRESSIONS_LIMIT_FOR_EXPECTED_GOOD_COMPLETION_PERCENT )
		{
			log.fine(String.format("campaign %d is expected to reach too high completion percent "
					+" (over %f) "
					+ " on next day: %d, expected percent: %f" 
					+ " current day: %d, current percent: %f",
					campaign.id, GameFactorDefaults.IMPRESSIONS_LIMIT_FOR_EXPECTED_GOOD_COMPLETION_PERCENT, 
					dayBiddingFor, estimatedPercentNextDay,
					(dayBiddingFor-1), percentCompletion));
			return 0.0 ;
		}
		
		//remaining budget 
		double remainBudget = campaign.get_remainingBudgetMillis() * 1000;
		log.fine(String.format("remain budget: %f", remainBudget));
		
		//TODO - use data in query (ad type, publisher, segment etc.)
		
		//use a part of budget
		double wantedbudget = budgetFraction * remainBudget ;
		log.fine(String.format("all remaining budget: %f, fraction of remaining budget:%f", remainBudget, wantedbudget));
			
		//fix cases when budget turn negative
		if (wantedbudget <= 0)
		{
			wantedbudget = budgetFraction * campaign.getBudget() ;
			log.fine(String.format("remain budget turned negative, "
					+" use original budget instead (risk overdraft):%f", wantedbudget));
		}
				
		
		//urgency: when approaching end of campaign - raise the impression bid by factor .
		if (( dayBiddingFor == campaign.dayEnd  ||  dayBiddingFor == (campaign.dayEnd-1) )
			&& (percentCompletion < MIN_PERCENT_COMPLETION)) 
		{
			
			wantedbudget = wantedbudget * IMPRESSION_URGENCY_FACTOR ; 
			
			log.fine(String.format("reaching end campaign with low completion percent %f."
					+ " (bidding for day %d, campaign ends in day %d.) "
					+ " raising price by factor %f. updated price :%f "
					, percentCompletion, dayBiddingFor, campaign.dayEnd, IMPRESSION_URGENCY_FACTOR, wantedbudget));
		}
		
		
		//debug - do properly
		double videoFactor = 1.0;
		if (query.getAdType().equals(AdType.video))
		{
			videoFactor = 1.4;
		}


		double mobileFactor = 1.0;
		if (query.getDevice().equals(Device.mobile))
		{
			mobileFactor = 1.4;
		}

		
		
		if (dayBiddingFor>=1 && dayBiddingFor<7)
		{
			wantedbudget = wantedbudget * IMPRESSION_URGENCY_FACTOR ;
		}
		
		
		double result = (wantedbudget / impsToGo) * anotherFactor * videoFactor * mobileFactor ;
		log.fine(String.format("final decision: %f", result));
				
		
		return result ;
		
	}
	
	
	
	double ComputeEstimatedPercentNextDayByRate (CampaignData campaign, double percentSoFar, 
			int dayBiddingFor)
	{
		
		Logger log = Logger.getLogger(SimpleAdNetwork.class.getName());
		
		//note: current day we already have a running impressions. 
		//the last day with data (that is counted inside the completion rate
		//is actually dayBiddingFor-2).
		int currentDay = dayBiddingFor - 1; 
		int daysSoFar = currentDay - (int) campaign.getDayStart();
		
		log.fine(String.format("while computing estimated completion percent for next day: %d."
				+ ", current (now running) day: %d, percent so far: %f, days so far: %d", 
				dayBiddingFor, currentDay, percentSoFar, daysSoFar));
		
		if (daysSoFar <= 0)
		{
			//no point in calculating on first days
			return 0.0 ; 
		}
		
		//compute completion rate so far
		double rate = percentSoFar / (double) daysSoFar ;
		
		//compute estimated completion percent for next day 
		double result = percentSoFar + rate;
		log.fine (String.format("estimated completion percent: %f for day: %d", 
				result, dayBiddingFor)) ; 
		
		return result ; 
	}
	
}
