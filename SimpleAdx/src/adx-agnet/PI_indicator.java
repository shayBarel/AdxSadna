import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import tau.tac.adx.report.adn.MarketSegment;


public class PI_indicator 
{
	
	
	/**
	 * compute the popularity of a given segment in a given day.
	 * the segment must be a partitioned segment (one that has 3 attributes. ) 
	 * @param segment
	 * @param market - a collection of active campaigns that represent the market .
	 * @param day
	 * @return
	 */

	static private double popularityOfSegment(CampaignData campaign, 
			Set<MarketSegment> segment, 
			Map <Integer, CampaignData> market, int day)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		
	
		//init. sum
		double pop = 0;

		//validate segment is 3-partitioned .
		if (segment.size() != 3 )
		{
			log.severe(String.format("tried to compute size of exact segment while segment is not exact. size of segment : %d", 
					segment.size()));
		}
		
		log.finer(String.format("computing segment popularity for campaign %d,"
				+ " segment: %s, during day: %d", campaign.id, segment, day));

			
		//iterate all campaigns in market
		for(Map.Entry<Integer, CampaignData> entry : market.entrySet())
		{
			CampaignData marketCampaign = entry.getValue();

			//get partition of the market campaign, 
			//and see if our segment is contained .
			Set<MarketSegment> marketCampaignSegment = marketCampaign.getTargetSegment();
			Set<Set<MarketSegment>> marketCampaignSegmentPartitioned = 
					Population.GetPartitionedSegments(marketCampaignSegment) ;
			
			
			//if market campaign contains the segment we want,
			//and also overlaps in our days, 
			//then we add to the "popularity" of segment . 
			if (marketCampaignSegmentPartitioned.contains(segment)
					&& day <= marketCampaign.getDayEnd() && day >= marketCampaign.getDayStart())
			{
				
					log.fine(String.format("while computing segment popularity for campaign %d,"
							+ " segment: %s", campaign.id, segment));
					
					log.fine(String.format("intersects with market campaign %d, of segment: %s",
							marketCampaign.id, marketCampaignSegment));
				
					long reach = marketCampaign.getReachImps();
					double segmentSize = WeightSegment(marketCampaignSegment);
					long days = (marketCampaign.getDayEnd() - marketCampaign.getDayStart()) + 1;
					pop += ((double)reach) / (segmentSize * ((double)days));			
			}
		}
		
		return pop;
	}
	
	/**
	 * calculate the popularity of a segment,  during a group of days .
	 * the segment may have any number of attributes 
	 * (be i.e. "male", "female, old",  or "female, old, high income" )
	 * uses the subroutine that computed popularity for a small segment.  
	 * @param segment 
	 * @param market - a collection of active campaigns that represent the market .
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	public static double popularitySegmentMultiDays
		(CampaignData campaign, Set<MarketSegment> segment, 
				Map <Integer, CampaignData> market, int dayStart, int dayEnd)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		
		
		double sum_popularity = 0 ;

		log.fine(String.format("computing segment popularity for campaign %d,"
				+ " of segment: %s, during days: %d to %d", 
				campaign.id, segment, dayStart, dayEnd));

		
		//first, partition segment into 3-partition
		Set<Set<MarketSegment>> partition = Population.GetPartitionedSegments(segment) ;

		//for each small segment, find its popularity over all days .
		for (Set<MarketSegment> sub_segment : partition) 
		{
			
		
			//for the segment, loop on all days .
			for (int i=dayStart; i<=dayEnd; i++)
			{
				double sub_segment_popularity = popularityOfSegment(
						campaign, sub_segment, market, i);
				if (sub_segment_popularity > 0)
				{
					//add to the sum, the popularity of that segment in that day.
					//weighted by the size of sub segment .
					sum_popularity += WeightSegment(sub_segment)
							 * sub_segment_popularity;
				}
			}
		}
		
		//finished iterating. 
		//now divide the sum by the "weights". (size of days)
		int numdays = (dayEnd - dayStart) + 1;
		double result = sum_popularity / 
				( (WeightSegment(segment)) * ((double)numdays));
		
		//print result
		log.fine(String.format("computing segment popularity for campaign %d,"
				+ " of segment: %s, during days: %d to %d. popularity is: %f", 
				campaign.id, segment, dayStart, dayEnd, result));

	
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
		
		log.finer(String.format("price index : computed size of segment : %s, size is: %d"
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
		
		double pop = popularitySegmentMultiDays(cd, cd.getTargetSegment(), market, day - 1, day);
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