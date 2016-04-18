import java.util.Set;


import tau.tac.adx.demand.CampaignStats;
import tau.tac.adx.props.AdxQuery;
import tau.tac.adx.report.adn.MarketSegment;
import tau.tac.adx.report.demand.CampaignOpportunityMessage;
import tau.tac.adx.report.demand.InitialCampaignMessage;

/**
 * 
 */

/**
 * @author NAttar
 *
 */

public class CampaignData {

	
	/* campaign attributes as set by server */
	Long reachImps;
	long dayStart;
	long dayEnd;
	Set<MarketSegment> targetSegment;
	double videoCoef;
	double mobileCoef;
	int id;
	private AdxQuery[] campaignQueries;//array of queries relvent for the campaign.

	/* campaign info as reported */
	CampaignStats stats;
	double budget;

	public double getBudget() {
		return budget;
	}

	public CampaignData(InitialCampaignMessage icm) {
		reachImps = icm.getReachImps();
		dayStart = icm.getDayStart();
		dayEnd = icm.getDayEnd();
		targetSegment = icm.getTargetSegment();
		videoCoef = icm.getVideoCoef();
		mobileCoef = icm.getMobileCoef();
		id = icm.getId();

		stats = new CampaignStats(0, 0, 0);
		budget = 0.0;
	}

	public void setBudget(double d) {
		budget = d;
	}

	public CampaignData(CampaignOpportunityMessage com) {
		dayStart = com.getDayStart();
		dayEnd = com.getDayEnd();
		id = com.getId();
		reachImps = com.getReachImps();
		targetSegment = com.getTargetSegment();
		mobileCoef = com.getMobileCoef();
		videoCoef = com.getVideoCoef();
		stats = new CampaignStats(0, 0, 0);
		budget = 0.0;
		_completionPercent = 0.0 ;
		_remainingBudgetMillis = 0.0;
	}

	@Override
	public String toString() {
		return "Campaign ID " + id + ": " + "day " + dayStart + " to "
				+ dayEnd + " " + targetSegment + ", reach: " + reachImps
				+ " coefs: (v=" + videoCoef + ", m=" + mobileCoef + ")";
	}

	int impsTogo() {
		return (int) Math.max(0, reachImps - stats.getTargetedImps());
	}

	public AdxQuery[] getCampaignQueries() {
		return campaignQueries;
	}

	public void setCampaignQueries(AdxQuery[] campaignQueries) {
		this.campaignQueries = campaignQueries;
	}

	public Set<MarketSegment> getTargetSegment() {
		return targetSegment;
	}

	public void setTargetSegment(Set<MarketSegment> targetSegment) {
		this.targetSegment = targetSegment;
	}
	
	public long getDayStart() {
		return dayStart;
	}

	public void setDayStart(long dayStart) {
		this.dayStart = dayStart;
	}

	public long getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(long dayEnd) {
		this.dayEnd = dayEnd;
	}

	public Long getReachImps() {
		return reachImps;
	}

	public void setReachImps(Long reachImps) {
		this.reachImps = reachImps;
	}

	public CampaignStats getStats() {
		return stats;
	}


	void setStats(CampaignStats s) {
		stats.setValues(s);
	}

	
	//////////////////////
	//fields for our usage
	//////////////////////
	
	//the total remaining budget . (i.e. 9500) 
	protected double _remainingBudgetMillis = 0.0 ;		

	//the percentage of completion of campaign .
	//(legal values will be between 0.0 and about 1.5)
	protected double _completionPercent = 0.0 ;

	
	public double get_remainingBudgetMillis() {
		return _remainingBudgetMillis;
	}

	public void set_remainingBudgetMillis(double _remainingBudgetMillis) {
		this._remainingBudgetMillis = _remainingBudgetMillis;
	}

	public double get_completionPrecent() {
		return _completionPercent;
	}

	public void set_completionPrecent(double _completionPrecent) {
		this._completionPercent = _completionPrecent;
	}


	public void UpdateFromStats (CampaignStats stats)
	{
			
		//reduce remaining budget 
		double prevBedget = get_remainingBudgetMillis() ; 
		double newBudget = prevBedget - stats.getCost() ;
		set_remainingBudgetMillis(newBudget);
		
	}
	
	
}