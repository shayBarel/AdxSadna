import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import tau.tac.adx.report.adn.MarketSegment;


public class PI_indicator {
	//getting MarketSegment, and set of campaignData.
	//return double - the MarketSegment popularity.
	
	//segment is a combination of 3 attributes i.e. <young, high, male>...
	//or combinations of 2 or 1 attributes.
	//the combination is given as a Set .
	public static double popularityOfSegment(Set<MarketSegment> segment, Map <Integer, CampaignData> market, int day)
	{
		
		//init. sum
		double pop = 0;

		for(Map.Entry<Integer, CampaignData> entery : market.entrySet())
		{
			CampaignData cd = entery.getValue();

			//if market campaign is for the segment we want,
			//(exact match), then include it in calculation
			//(compare 2 sets of segments)
			//TODO is not precise, possible partial overlap of segments.
			Set<MarketSegment> marketCampaignSegments = cd.getTargetSegment();
			if (marketCampaignSegments.equals(segment))
			{

				if(day <= cd.getDayEnd() && day >= cd.getDayStart())
				{
					double segmentSize = WeightSegment(marketCampaignSegments);
					long reach = cd.getReachImps();
					long days = (cd.getDayEnd() - cd.getDayStart()) + 1;
					
					pop += ((double)reach) / (segmentSize * ((double)days));
				}
				
			}
		}
		
		return pop;
	}
	
	/**
	 * calculate the popularity of a segment, 
	 * during a group of days .
	 * @param segment
	 * @param market
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	public static double popularitySegmentMultiDays
		(Set<MarketSegment> segment, Map <Integer, CampaignData> market, 
				int dayStart, int dayEnd)
	{
		
		double sum_popularity = 0 ;

		//for the segment, loop on all days .
		for (int i=dayStart; i<=dayEnd; i++)
		{
			if (popularityOfSegment(segment, market, i) > 0)
			{
				//add to the sum, the popularity of that segment in that day.
				sum_popularity += popularityOfSegment(segment, market, i);
			}
		}
		
		
		//finished iterating. 
		//now divide the sum by the "weights". (size of days)
		int numdays = (dayEnd - dayStart) + 1;
		double result = sum_popularity / (double)(numdays);
		return result ;
	}
	
	
	static double WeightSegment (Set<MarketSegment> segment)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());

		
		//get the size of segment (number of users) 
		int segment_size = Population.GetSegmentSize(segment) ;
				
		//divide by size of whole population to get "probability".
		double result = ((double) segment_size)/((double) GameFactorDefaults.POPULATION_SIZE);
		
		log.fine(String.format("price index : computed size of segment : %s, size is: %d"
				+ ", probability in population: %f",
				segment.toString(), segment_size, result)); 
		
		return result ;
	}
	
//	/**
//	 * return a weight of a group of segments .
//	 * @param segments
//	 * @return
//	 */
//	static double WeightGroupSegments (Set<MarketSegment> segments)
//	{
//		double sum_weight = 0 ;
//		
//		//just iterate on segments, and sum their weights.
//		for (MarketSegment s: segments)
//		{
//			sum_weight += WeightSegment(s);
//		}
//		return sum_weight;
//	}
	
	public static double urgency(CampaignData cd, double reached, int day)
	{
		double urg = 0.5;
		long days = cd.getDayEnd() - cd.getDayStart();
		if(days - day > days/3)
			urg *= 1.2;
		else
			urg = urg / 1.2;
		return urg;
	}
	double MinBidValue = 0;
	
	static double computeBidUrg(double pop, double reached,Map<Integer, CampaignData> myCmp,CampaignData cd)
	{
		double numOfCmp = 0;
		double temp = 0;
		for(MarketSegment ms: cd.getTargetSegment())
		{
			numOfCmp = numberOfCampaignsInSegment(ms, myCmp);
			temp = Math.min(numOfCmp, temp);
		}
		return 1/temp;
	}
	static int numberOfCampaignsInSegment(MarketSegment ms, Map<Integer, CampaignData> myCmp)
	{
		int num = 0;
		for(Map.Entry<Integer, CampaignData> entry: myCmp.entrySet())
		{
			for(MarketSegment m: entry.getValue().getTargetSegment())
			{
				if(m.equals(ms))
					num++;
			}
		}
			
		
		return num;
	}
	static double impBidder(CampaignData cd, Map<Integer, CampaignData> myCmp, Map <Integer, CampaignData> market, int day, double ucsTargetLevel)
	{
		double bid = 0.5;//TODO check min bid.
		
		double pop = popularitySegmentMultiDays(cd.getTargetSegment(), market, day - 1, day);
		double reached = cd.getStats().getTargetedImps()/cd.getReachImps();//percentage of completion
		
		double budget = cd.getBudget();
		
		double bidUrg = urgency(cd,reached,day) * computeBidUrg(pop, reached, myCmp, cd);
		
		
		if(bidUrg > 0.4)
			bid = 0.5;
		else
			bid = 0.7;
		
		return bid;
		
		
	}


}