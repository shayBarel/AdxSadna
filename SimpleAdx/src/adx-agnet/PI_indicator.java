import java.util.Map;
import java.util.Set;

import tau.tac.adx.report.adn.MarketSegment;


public class PI_indicator {
	//getting MarketSegment, and set of campaignData.
	//return double - the MarketSegment popularity.
	public static double popularityOfSegment(MarketSegment seg, Map <Integer, CampaignData> market, int day)
	{
		double pop = 0;
		long reach = 0, days = 0;

		for(Map.Entry<Integer, CampaignData> entery : market.entrySet())
		{
			CampaignData cd = entery.getValue();
			days = (cd.getDayEnd() - cd.getDayStart()) + 1;
			
			//if market campaign has the segment we want,
			//include it in calculation
			Set<MarketSegment> marketCampaignSegments = cd.getTargetSegment();
			if (marketCampaignSegments.contains(seg))
			{

				if(day <= cd.getDayEnd() && day >= cd.getDayStart())
				{
					double segmentsSize = 0 ;
					segmentsSize = WeightGroupSegments(marketCampaignSegments);
					reach = cd.getReachImps();
					pop += ((double)reach) / (segmentsSize * ((double)days));
				}
				
			}
		}
		
		return pop;
	}
	
	/**
	 * calculate the popularity of a group of segments, 
	 * during a group of days .
	 * @param segments
	 * @param market
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	public static double popularityMultiSegmentsMultiDays
		(Set<MarketSegment> segments, Map <Integer, CampaignData> market, 
				int dayStart, int dayEnd)
	{
		
		double sum_popularity = 0 ;
		//loop on all segments 
		for(MarketSegment s : segments)
		{
			//for each segment, loop on all days .
			for (int i=dayStart; i<=dayEnd; i++)
			{
				//add to the sum, the popularity of that segment in that day.
				sum_popularity += WeightSegment(s) * popularityOfSegment(s, market, i);
			}
		}
		
		//finished iterating. 
		//now divide the sum by the "weights". (size of days and weight of group of segments)
		int numdays = (dayEnd - dayStart) + 1;
		double weightGroupSegments = WeightGroupSegments(segments) ;
		
		
		double result = sum_popularity / (((double)(numdays))*weightGroupSegments);
		return result ;
	}
	
	
	static double WeightSegment (MarketSegment segment)
	{
		return 0.05 ;
	}
	
	/**
	 * return a weight of a group of segments .
	 * @param segments
	 * @return
	 */
	static double WeightGroupSegments (Set<MarketSegment> segments)
	{
		double sum_weight = 0 ;
		
		//just iterate on segments, and sum their weights.
		for (MarketSegment s: segments)
		{
			sum_weight += WeightSegment(s);
		}
		return sum_weight;
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
	
	static double impBidder(CampaignData cd, Map <Integer, CampaignData> market, int day, double ucsTargetLevel)
	{
		double bid = 0.0;
		//double pop = popularityOfSegment(cd.getTargetSegment(), market, day);
		double pop = cd.getReachImps() ;
		double reached = cd.getStats().getTargetedImps()/cd.getReachImps();
		double urg = urgency(cd, reached, day);
		
		if(pop > 0.4)
			bid = 0.5;
		else
			bid = 0.7;
		
		return bid;
		
		
	}


}