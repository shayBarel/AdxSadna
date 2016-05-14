import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import tau.tac.adx.report.adn.MarketSegment;


public class DemandCalculator 
{
	
	
	/**
	 * compute the demand of a given segment in a given day.
	 * the segment must be a partitioned segment (one that has 3 attributes. ) 
	 * @param segment
	 * @param market - a collection of active campaigns that represent the market .
	 * @param day
	 * @return
	 */

	static private double demandOfSegment(CampaignData campaign, 
			Set<MarketSegment> segment, 
			Map <Integer, CampaignData> market, int day)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		
	
		//init. sum
		double sum_demand = 0;

		//validate segment is 3-partitioned .
		if (segment.size() != 3 )
		{
			log.severe(String.format("tried to compute size of exact segment while segment is not exact. size of segment : %d", 
					segment.size()));
		}
		
		log.finer(String.format("computing segment demand for campaign %d,"
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
			//then we add to the "demand" of segment . 
			if (marketCampaignSegmentPartitioned.contains(segment)
					&& day <= marketCampaign.getDayEnd() && day >= marketCampaign.getDayStart())
			{
				
					log.fine(String.format("while computing segment demand for campaign %d,"
							+ " segment: %s", campaign.id, segment));
					
					log.fine(String.format("intersects with market campaign %d, of segment: %s",
							marketCampaign.id, marketCampaignSegment));
				
					long reach = marketCampaign.getReachImps();
					double segmentSize = WeightSegment(marketCampaignSegment);
					long days = (marketCampaign.getDayEnd() - marketCampaign.getDayStart()) + 1;
					sum_demand += ((double)reach) / (segmentSize * ((double)days));			
			}
		}
		
		return sum_demand;
	}
	
	/**
	 * calculate the demand of a segment,  during a group of days .
	 * the segment may have any number of attributes 
	 * (be i.e. "male", "female, old",  or "female, old, high income" )
	 * uses the subroutine that computed demand for a small segment.  
	 * @param segment 
	 * @param market - a collection of active campaigns that represent the market .
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	public static double demandOfSegmentMultiDays
		(CampaignData campaign, Set<MarketSegment> segment, 
				Map <Integer, CampaignData> market, int dayStart, int dayEnd)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		
		
		double sum_demand = 0 ;

		log.fine(String.format("computing segment demand for campaign %d,"
				+ " of segment: %s, during days: %d to %d", 
				campaign.id, segment, dayStart, dayEnd));

		
		//first, partition segment into 3-partition
		Set<Set<MarketSegment>> partition = Population.GetPartitionedSegments(segment) ;

		//for each small segment, find its demand over all days .
		for (Set<MarketSegment> sub_segment : partition) 
		{
			
		
			//for the segment, loop on all days .
			for (int i=dayStart; i<=dayEnd; i++)
			{
				double sub_segment_demand = demandOfSegment(
						campaign, sub_segment, market, i);
				if (sub_segment_demand > 0)
				{
					//add to the sum, the demand of that segment in that day.
					//weighted by the size of sub segment .
					sum_demand += WeightSegment(sub_segment)
							 * sub_segment_demand;
				}
			}
		}
		
		//finished iterating. 
		//now divide the sum by the "weights". (size of days)
		int numdays = (dayEnd - dayStart) + 1;
		double result = sum_demand / 
				( (WeightSegment(segment)) * ((double)numdays));
		
		//print result
		log.fine(String.format("computing segment demand for campaign %d,"
				+ " of segment: %s, during days: %d to %d. demand is: %f", 
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
		
		log.finer(String.format("demand calculator : computed size of segment : %s, size is: %d"
				+ ", probability in population: %f",
				segment.toString(), segment_size, result)); 
		
		return result ;
	}
	
	


}