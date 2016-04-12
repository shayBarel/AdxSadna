import java.util.logging.Logger;

import tau.tac.adx.props.AdxQuery;

public class BidderImpressions 
{

	public static final double budgetFraction = 0.5 ;
	
	public static final double anotherFactor = 5.0 ;
	
	double GenerateImpressionBid (CampaignData campaign , AdxQuery query)
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
		
		//TODO if
		
		double result = (wantedbudget / impsToGo) * anotherFactor ;
		log.fine(String.format("final decision: %f", result));
				
		
		return result ;
		
	}
	
	
}
