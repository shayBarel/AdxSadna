import java.util.logging.Logger;

import tau.tac.adx.props.AdxQuery;

public class BidderImpressions 
{

	public static final double budgetFraction = 0.5 ;
	
	public static final double anotherFactor = 5.0 ;
	
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
		
		
		//remaining budget 
		double remainBudget = campaign.get_remainingBudgetMillis() * 1000;
		log.fine(String.format("remain budget: %f", remainBudget));
		
		//TODO - use data in query (ad type, publisher, segment etc.)
		
		//use a part of budget
		double wantedbudget = budgetFraction * remainBudget ;
		log.fine(String.format("all remaining budget: %f, fraction of remaining budget:%f", remainBudget, wantedbudget));
		
		
		//TODO if
		
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
		
		double result = (wantedbudget / impsToGo) * anotherFactor ;
		log.fine(String.format("final decision: %f", result));
				
		
		return result ;
		
	}
	
	
}
