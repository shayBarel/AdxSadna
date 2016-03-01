
public class PI_indicator {
	//getting MarketSegment, and set of campaignData.
	//return double - the MarketSegment popularity.
	double popularityOfSegment(MarketSegment seg, Set<CampaignData> market)
	{
		double pop = 0;
		int reach = 0, size = 0, days = 0;
		for(CampaignData cd : market)
		{
			days = cd.dayEnd - cd.dayStart;
			size = cd.getTargetSegment().getSize();//not working.
			reach = cd.getReachImps();
			pop += reach/(size*days);
		}
		return pop;
	}

}
