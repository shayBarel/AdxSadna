import java.util.Set;

import tau.tac.adx.report.adn.MarketSegment;

import tau.tac.adx.agents.CampaignData;

public class PI_indicator {
	//getting MarketSegment, and set of campaignData.
	//return double - the MarketSegment popularity.
	public static double popularityOfSegment(Set<MarketSegment> seg, Set<CampaignData> market)
	{
		double pop = 0;
		long reach = 0, size = 0, days = 0;
		for(CampaignData cd : market)
		{
			days = cd.getDayEnd() - cd.getDayStart();
			//size = cd.getTargetSegment().getSize();//not working.
			size = 500;//for test
			reach = cd.getReachImps();
			pop += reach / (size * days);
		}
		return pop;
	}
	public static double urgency(CampaignData cd, double reached, int day)
	{
		double urg = 0.5;
		long days = cd.getDayEnd() - cd.getDayStart();
		if(days - day > days/3)
			urg *= 1.2;
		return urg;
	}
	double MinBidValue = 0;
	
	static double impBidder(CampaignData cd, Set<CampaignData> market, int day, double ucsTargetLevel)
	{
		double bid = 0.0;
		double pop = popularityOfSegment(cd.getTargetSegment(), market);
		double reached = cd.getStats().getTargetedImps()/cd.getReachImps();
		double urg = urgency(cd, reached, day);
		
		if(pop > 0.4)
			bid = 0.5;
		else
			bid = 0.7;
		
		return bid;
		
		
	}

}