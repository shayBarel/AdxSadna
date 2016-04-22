import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.sics.isl.transport.Transportable;
import se.sics.tasim.aw.Agent;
import se.sics.tasim.aw.Message;
import se.sics.tasim.props.SimulationStatus;
import se.sics.tasim.props.StartInfo;
import tau.tac.adx.ads.properties.AdType;
import tau.tac.adx.demand.CampaignStats;
import tau.tac.adx.devices.Device;
import tau.tac.adx.props.AdxBidBundle;
import tau.tac.adx.props.AdxQuery;
import tau.tac.adx.props.PublisherCatalog;
import tau.tac.adx.props.PublisherCatalogEntry;
import tau.tac.adx.props.ReservePriceInfo;
import tau.tac.adx.report.adn.AdNetworkReport;
import tau.tac.adx.report.adn.MarketSegment;
import tau.tac.adx.report.adn.AdNetworkReportEntry;
import tau.tac.adx.report.adn.AdNetworkKey;
import tau.tac.adx.report.demand.AdNetBidMessage;
import tau.tac.adx.report.demand.AdNetworkDailyNotification;
import tau.tac.adx.report.demand.CampaignOpportunityMessage;
import tau.tac.adx.report.demand.CampaignReport;
import tau.tac.adx.report.demand.CampaignReportKey;
import tau.tac.adx.report.demand.InitialCampaignMessage;
import tau.tac.adx.report.demand.campaign.auction.CampaignAuctionReport;
import tau.tac.adx.report.publisher.AdxPublisherReport;
import tau.tac.adx.report.publisher.AdxPublisherReportEntry;
import edu.umich.eecs.tac.props.Ad;
import edu.umich.eecs.tac.props.BankStatus;

/**
 * 
 * @author Mariano Schain
 * Test plug-in
 * 
 */
public class SimpleAdNetwork extends Agent {
	
	
	public static int gameNum = 1;
	

	private final Logger log = Logger
			.getLogger(SimpleAdNetwork.class.getName());

	/*
	 * Basic simulation information. An agent should receive the {@link
	 * StartInfo} at the beginning of the game or during recovery.
	 */
	@SuppressWarnings("unused")
	private StartInfo startInfo;

	/**
	 * Messages received:
	 * 
	 * We keep all the {@link CampaignReport campaign reports} delivered to the
	 * agent. We also keep the initialization messages {@link PublisherCatalog}
	 * and {@link InitialCampaignMessage} and the most recent messages and
	 * reports {@link CampaignOpportunityMessage}, {@link CampaignReport}, and
	 * {@link AdNetworkDailyNotification}.
	 */
	private final Queue<CampaignReport> campaignReports;
	private PublisherCatalog publisherCatalog;
	private InitialCampaignMessage initialCampaignMessage;
	private AdNetworkDailyNotification adNetworkDailyNotification;
	// dummy code to be deleted ;
	//AgentUcsData TempAgentData = new AgentUcsData();
	public UcsHistory ucsHistory;
	
	public double previousBid;
	/*
	 * The addresses of server entities to which the agent should send the daily
	 * bids data
	 */
	private String demandAgentAddress;
	private String adxAgentAddress;

	/*
	 * we maintain a list of queries - each characterized by the web site (the
	 * publisher), the device type, the ad type, and the user market segment
	 */
	private AdxQuery[] queries;

	/**
	 * Information regarding the latest campaign opportunity announced
	 */
	private CampaignData pendingCampaign;

	
	//class that saves data about the current competition.
	private CompetitionData _CurrentCompetition;
	
	
	public CompetitionData GetCurrCompetition() 
	{
		return _CurrentCompetition;
	}

	public void SetCurrCompetition(CompetitionData _competition) 
	{
		_CurrentCompetition = _competition;
	}

	/*
	 * the bidBundle to be sent daily to the AdX
	 */
	private AdxBidBundle bidBundle;

	/*
	 * The current bid level for the user classification service
	 */
	private double ucsBid;

	/*
	 * The targeted service level for the user classification service
	 */
	private double ucsTargetLevel;

	/*
	 * current day of simulation
	 */
	private int day;

	//list of publishers.
	private String[] publisherNames;

	
	public SimpleAdNetwork() 
	{
		campaignReports = new LinkedList<CampaignReport>();

		
		//change default log level
		log.setLevel(Level.FINE);
	}

	@Override
	protected void messageReceived(Message message) {
		try {
			Transportable content = message.getContent();

			// log.fine(message.getContent().getClass().toString());

			if (content instanceof InitialCampaignMessage) {
				handleInitialCampaignMessage((InitialCampaignMessage) content);
			} else if (content instanceof CampaignOpportunityMessage) {
				handleICampaignOpportunityMessage((CampaignOpportunityMessage) content);
			} else if (content instanceof CampaignReport) {
				handleCampaignReport((CampaignReport) content);
			} else if (content instanceof AdNetworkDailyNotification) {
				handleAdNetworkDailyNotification((AdNetworkDailyNotification) content);
			} else if (content instanceof AdxPublisherReport) {
				handleAdxPublisherReport((AdxPublisherReport) content);
			} else if (content instanceof SimulationStatus) {
				handleSimulationStatus((SimulationStatus) content);
			} else if (content instanceof PublisherCatalog) {
				handlePublisherCatalog((PublisherCatalog) content);
			} else if (content instanceof AdNetworkReport) {
				handleAdNetworkReport((AdNetworkReport) content);
			} else if (content instanceof StartInfo) {
				handleStartInfo((StartInfo) content);
			} else if (content instanceof BankStatus) {
				handleBankStatus((BankStatus) content);
			} else if(content instanceof CampaignAuctionReport) {
				hadnleCampaignAuctionReport((CampaignAuctionReport) content);
			} else if (content instanceof ReservePriceInfo) {
				// ((ReservePriceInfo)content).getReservePriceType();
			} else {
				this.log.info("UNKNOWN Message Received: " + content);
			}

		} 
		
		catch (Exception e) 
		{
			this.log.log(Level.SEVERE,
					"Exception thrown while trying to parse message." + e);
			this.log.log(Level.SEVERE,
					"Exception thrown while trying to parse message." + e);
			
			e.printStackTrace();
			
			System.err.println("Exception thrown while trying to parse message." + e); 
		}

		
	}

	private void hadnleCampaignAuctionReport(CampaignAuctionReport content) {
		// ingoring - this message is obsolete
	}

	private void handleBankStatus(BankStatus content) 
	{
		log.fine("bank status: Day " + day + " :" + content.toString());
	}

	/**
	 * Processes the start information.
	 * 
	 * @param startInfo
	 *            the start information.
	 */
	protected void handleStartInfo(StartInfo startInfo) {
		this.startInfo = startInfo;
	}

	/**
	 * Process the reported set of publishers
	 * 
	 * @param publisherCatalog
	 */
	private void handlePublisherCatalog(PublisherCatalog publisherCatalog) {
		this.publisherCatalog = publisherCatalog;
		generateAdxQuerySpace();
		getPublishersNames();

	}

	/**
	 * On day 0, a campaign (the "initial campaign") is allocated to each
	 * competing agent. The campaign starts on day 1. The address of the
	 * server's AdxAgent (to which bid bundles are sent) and DemandAgent (to
	 * which bids regarding campaign opportunities may be sent in subsequent
	 * days) are also reported in the initial campaign message
	 */
	private void handleInitialCampaignMessage(
			InitialCampaignMessage campaignMessage) {
		log.info(campaignMessage.toString());
		
		day = 0;

		initialCampaignMessage = campaignMessage;
		demandAgentAddress = campaignMessage.getDemandAgentAddress();
		adxAgentAddress = campaignMessage.getAdxAgentAddress();

		CampaignData campaignData = new CampaignData(initialCampaignMessage);
		double newBudget = initialCampaignMessage.getBudgetMillis()/1000.0 ;
		campaignData.setBudget(newBudget);
		campaignData.set_remainingBudgetMillis(newBudget);
		genCampaignQueries(campaignData);

		log.fine(String.format("Initial campaign: %d. campaign budget is: %f", 
				campaignData.id, newBudget));
		
		
		/*
		 * The initial campaign is already allocated to our agent so we add it
		 * to our allocated-campaigns list.
		 */
		log.info("Day " + day + ": Allocated campaign - " + campaignData);
		//myCampaigns.put(initialCampaignMessage.getId(), campaignData);
		CompetitionData curr_competition = GetCurrCompetition();
		curr_competition.GetMyCampaigns().put(initialCampaignMessage.getId(), campaignData);
		
	}

	/**
	 * On day n ( > 0) a campaign opportunity is announced to the competing
	 * agents. The campaign starts on day n + 2 or later and the agents may send
	 * (on day n) related bids (attempting to win the campaign). The allocation
	 * (the winner) is announced to the competing agents during day n + 1.
	 */
	private void handleICampaignOpportunityMessage(
			CampaignOpportunityMessage com) {

		day = com.getDay();

		pendingCampaign = new CampaignData(com);
		log.info("Day " + day + ": Campaign opportunity - " + pendingCampaign);

		
		log.fine(String.format("computing bid for campaign %d (days: %d to %d,"
				+ " segment: %s, reach: %d).", 
				pendingCampaign.id, pendingCampaign.getDayStart() , 
				pendingCampaign.getDayEnd(), pendingCampaign.getTargetSegment(), 
				pendingCampaign.getReachImps() ) );
		
		
		BidderCampaign campBidder = new BidderCampaignEfficient();
		//BidderCampaign campBidder = new BidderCampaignMinimum();
		//BidderCampaign campBidder = new BidderCampaignMaximum();
		CompetitionData competition = GetCurrCompetition();
		long cmpBidMillis = campBidder.GenerateCampaignBid(com,competition);
		
		//log contract bid 
		log.fine(String.format("bidding for campaign %d (days: %d to %d,"
				+ " segment: %s, reach: %d).", 
				pendingCampaign.id, pendingCampaign.getDayStart() , 
				pendingCampaign.getDayEnd(), pendingCampaign.getTargetSegment(), 
				pendingCampaign.getReachImps() ) );
		
		log.fine(String.format("given bid value : %d .", cmpBidMillis ) );
				
		
		log.info("Day " + day + ": Campaign total budget bid (millis): " + cmpBidMillis);

		/*
		 * Adjust ucs bid s.t. target level is achieved. Note: The bid for the
		 * user classification service is piggybacked
		 */
           
		
		if (adNetworkDailyNotification != null) {
			double ucsLevel = adNetworkDailyNotification.getServiceLevel();
			
			
			if (day > 1)
			{
				log.info("Day " + day + ": enter the value to ucs History, level: " + ucsLevel+"price"+adNetworkDailyNotification.getPrice()+"previous bid"+previousBid);

				ucsHistory.setUcsDaily(day-1, ucsLevel, adNetworkDailyNotification.getPrice(), 0,previousBid );
			}
			

			
			log.info("Day " + day + "Total imp are : "+competition.TatalImpLeft());

			
			UcsBidder ucsBidder = new UcsBidder(ucsHistory,competition.TatalImpLeft());
		
			ucsBidder.setCurrentUcsLevel(ucsLevel);
			ucsBidder.setPreviousBid(previousBid);
			//ucsBidder.setUSCAvrages(day,2); // calculating the average.


			//ucsBid = 0.1 + random.nextDouble()/10.0;
			
			//try to always win maximum UCS level.
			ucsBid = ucsBidder.getUCSbid(day,gameNum);
			log.info("Day " + day + ": ucs level reported: " + ucsLevel);
		} else {
			int Day2;
			Day2 = day -1;
			if (day < 1 )
				Day2 = day;
			ucsHistory.setUcsDaily(Day2, 0, 0, 0,previousBid );
			UcsBidder ucsBidder = new UcsBidder(ucsHistory,competition.TatalImpLeft());
			ucsBidder.setPreviousBid(previousBid);

			ucsBid = ucsBidder.getUCSbid(day,gameNum);
			log.info("Day " + day + ": Initial ucs bid is " + ucsBid);
		}

		
		//temporary fix
		//when UCS turned out negative or NaN- fixing it to be some constant .
		if (ucsBid < 0 || Double.isNaN(ucsBid)) 
		{
			ucsBid = GameFactorDefaults.UCS_DEFAULT_LEVEL;
		}
		
		previousBid = ucsBid;
		
		/* Note: Campaign bid is in millis */
		AdNetBidMessage bids = new AdNetBidMessage(ucsBid, pendingCampaign.id, cmpBidMillis);
		sendMessage(demandAgentAddress, bids);
	}

	/**
	 * On day n ( > 0), the result of the UserClassificationService and Campaign
	 * auctions (for which the competing agents sent bids during day n -1) are
	 * reported. The reported Campaign starts in day n+1 or later and the user
	 * classification service level is applicable starting from day n+1.
	 */
	private void handleAdNetworkDailyNotification(
			AdNetworkDailyNotification notificationMessage) {

		adNetworkDailyNotification = notificationMessage;

		log.info("Day " + day + ": Daily notification for campaign "
				+ adNetworkDailyNotification.getCampaignId());

		String campaignAllocatedTo = " allocated to "
				+ notificationMessage.getWinner();

		
		CompetitionData competition = GetCurrCompetition() ;

		//handle won campaign (if any)
		if ((pendingCampaign.id == adNetworkDailyNotification.getCampaignId())
				&& (notificationMessage.getCostMillis() != 0)) {

			
			//we won the campaign 
			
			//update campaign data according to received budget
			double newBudget = notificationMessage.getCostMillis()/1000.0 ;
			pendingCampaign.setBudget(newBudget);
			pendingCampaign.set_remainingBudgetMillis(newBudget);
			genCampaignQueries(pendingCampaign);
			
			//put campaign in list of won campaigns
			competition.GetMyCampaigns().put(pendingCampaign.id, pendingCampaign);
			
			
			log.fine(String.format("WON campaign %d at cost (Millis): %d. campaign budget is: %f", 
					adNetworkDailyNotification.getCampaignId(), notificationMessage.getCostMillis(),
					pendingCampaign.get_remainingBudgetMillis()));
			
			
			//updating competition level (lowering the competition)
			double current_competition_level = AgentData.GetActiveAgentInstance().GetContractBidCompetitionLevel();
			current_competition_level = current_competition_level  /  GameFactorDefaults.CONTRACT_BIDS_COMPETITION_DEFAULT_FACTOR ;
			AgentData.GetActiveAgentInstance().SetContractBidCompetitionLevel(current_competition_level);
			log.fine("updating competition level to " + String.valueOf(current_competition_level));
	
		}
		else
		{
			//we haven't won .
			log.fine("did not win .");
			
			//updating competition level 
			double current_competition_level = AgentData.GetActiveAgentInstance().GetContractBidCompetitionLevel();
			current_competition_level = current_competition_level * GameFactorDefaults.CONTRACT_BIDS_COMPETITION_DEFAULT_FACTOR ;
			AgentData.GetActiveAgentInstance().SetContractBidCompetitionLevel(current_competition_level);
			log.fine("updating competition level to " + String.valueOf(current_competition_level));
			
			//add the campaign to other's campaigns .
			competition.GetOthersCampaigns().put(pendingCampaign.id, pendingCampaign);
		}
		
		log.info("Day " + day + ": " + campaignAllocatedTo
				+ ". UCS Level set to " + notificationMessage.getServiceLevel()
				+ " at price " + notificationMessage.getPrice()
				+ " Quality Score is: " + notificationMessage.getQualityScore());
		
		//save quality rating 
		double new_qr = notificationMessage.getQualityScore() ;
		AgentData.GetActiveAgentInstance().SetQualityRating(new_qr);
		log.fine(String.format("new quality rating: %f", new_qr));
		
	}

	/**
	 * The SimulationStatus message received on day n indicates that the
	 * calculation time is up and the agent is requested to send its bid bundle
	 * to the AdX.
	 */
	private void handleSimulationStatus(SimulationStatus simulationStatus) {
		log.info("Day " + day + " : Simulation Status Received");
		sendBidAndAds();
		log.info("Day " + day + " ended. Starting next day");
		++day;
	}

	/**
	 * 
	 */
	protected void sendBidAndAds() {
		
		bidBundle = new AdxBidBundle();

		/*
		 * 
		 */

		int dayBiddingFor = day + 1;

		Random random = new Random();

		/* A random bid, fixed for all queries of the campaign */
		/*
		 * Note: bidding per 1000 imps (CPM) - no more than average budget
		 * revenue per imp
		 */
		//default - random bid
		double rbid = 10.0*random.nextDouble();
		

		//loop through all campaigns
		CompetitionData competition = GetCurrCompetition();
		Map <Integer, CampaignData> allCampaigns = competition.GetAllCampaigns();
		Map<Integer, CampaignData> myCampaigns = competition.GetMyCampaigns();
		for (Map.Entry<Integer, CampaignData> entry : myCampaigns.entrySet())
		{
			
			log.info(entry.getKey() + "/" + entry.getValue());
		    
		    CampaignData cmp = entry.getValue();
		    



			/*
			 * add bid entries w.r.t. each active campaign with remaining contracted
			 * impressions.
			 * 
			 * for now, a single entry per active campaign is added for queries of
			 * matching target segment.
			 */
	
			if ((dayBiddingFor >= cmp.dayStart)
					&& (dayBiddingFor <= cmp.dayEnd)
					&& (cmp.impsTogo() > 0)) {
	
				int entCount = 0;
	
				for (AdxQuery query : cmp.getCampaignQueries()) 
				{
					
					log.fine(String.format("handle query for bid bundle: %s", query.toString()));
					
					

				    //rbid = PI_indicator.impBidder(cmp, myCampaigns, allCampaigns, day, ucsTargetLevel);
					BidderImpressions bidder = new BidderImpressions() ;
					rbid = bidder.GenerateImpressionBid(cmp, query, dayBiddingFor);
					log.fine(String.format("generated impression bid for query %s, and campaign %d. "
							+" bid: %f",
							query.toString(), cmp.id, rbid));
					
					
					//TODO this seems strange . should remove this check .
					if (cmp.impsTogo() - entCount > 0) 
					{
						/*
						 * among matching entries with the same campaign id, the AdX
						 * randomly chooses an entry according to the designated
						 * weight. by setting a constant weight 1, we create a
						 * uniform probability over active campaigns(irrelevant because we are bidding only on one campaign)
						 */
						if (query.getDevice() == Device.pc) {
							if (query.getAdType() == AdType.text) {
								entCount++;
							} else {
								entCount += cmp.videoCoef;
							}
						} else {
							if (query.getAdType() == AdType.text) {
								entCount+=cmp.mobileCoef;
							} else {
								entCount += cmp.videoCoef + cmp.mobileCoef;
							}

						}
						bidBundle.addQuery(query, rbid, new Ad(null),
								cmp.id, 1);
					}
				}
				double impressionLimit = cmp.impsTogo();
				
				//raising the budget limit by a fixed factor.
				//(higher than budget - allow to lose.) 
				double budgetLimit = cmp.budget * 10;
				
				bidBundle.setCampaignDailyLimit(cmp.id,
						(int) impressionLimit, budgetLimit);
	
				log.info("Day " + day + ": Updated " + entCount
						+ " Bid Bundle entries for Campaign id " + cmp.id);
				
			}
		
			//finished with campaign, move on to next.
		}
		
		//send the bid bundle after finishing with all campaigns
		if (bidBundle != null) 
		{
			log.info("Day " + day + ": Sending BidBundle");
			sendMessage(adxAgentAddress, bidBundle);
		}
		
	}

	/**
	 * Campaigns performance w.r.t. each allocated campaign
	 */
	private void handleCampaignReport(CampaignReport campaignReport) {

		campaignReports.add(campaignReport);

		CompetitionData competition = GetCurrCompetition();
		
		/*
		 * for each campaign, the accumulated statistics from day 1 up to day
		 * n-1 are reported
		 */
		for (CampaignReportKey campaignKey : campaignReport.keys()) 
		{
			int cmpId = campaignKey.getCampaignId();
			
			//get campaign statistics
			CampaignStats cstats = campaignReport.getCampaignReportEntry(
					campaignKey).getCampaignStats();
			
			//find campaign in our collection, and update its statistics .
			CampaignData campaign = competition.GetMyCampaigns().get(cmpId);
			if (campaign != null)
			{
			campaign.setStats(cstats);
			
			//update campaign based on statistics .
			campaign.UpdateFromStats(cstats);
			
			
			log.fine("Day " + day + ": Updating campaign " + cmpId + " stats: "
					+ cstats.getTargetedImps() + " tgtImps "
					+ cstats.getOtherImps() + " nonTgtImps. Cost of imps is "
					+ cstats.getCost());
			

			log.fine(String.format("campaign %d: remaining budget was updated to %f", 
					cmpId, campaign.get_remainingBudgetMillis()));
			}
		
		}
	}

	/**
	 * Users and Publishers statistics: popularity and ad type orientation
	 */
	private void handleAdxPublisherReport(AdxPublisherReport adxPublisherReport) {
		log.info("Publishers Report: ");
		for (PublisherCatalogEntry publisherKey : adxPublisherReport.keys()) {
			AdxPublisherReportEntry entry = adxPublisherReport
					.getEntry(publisherKey);
			log.info(entry.toString());
		}
	}

	/**
	 * 
	 * @param AdNetworkReport
	 */
	private void handleAdNetworkReport(AdNetworkReport adnetReport) 
	{

		log.fine("Day " + day + " : AdNetworkReport");
		
		 for (AdNetworkKey adnetKey : adnetReport.keys()) 
		 {
			 //double rnd = Math.random(); 
			 //if (rnd > 0.95) 
			// { 	
				 AdNetworkReportEntry entry = adnetReport.getAdNetworkReportEntry(adnetKey);
				 log.fine(adnetKey + " " + entry); 
			 //} 
		 }
		
	}

	@Override
	protected void simulationSetup() {
		
		Random random = new Random();

		day = 0;
		bidBundle = new AdxBidBundle();

		//reset competition data .
		SetCurrCompetition(new CompetitionData());
		
		//reset agent data .
		AgentData.ResetAgent();
		
		/* initial bid between 0.1 and 0.2 */
		ucsBid = 0.1 + random.nextDouble()/10.0;
		
		
		//init UCS history .
		
		init_UcsHistory();


		log.fine("AdNet " + getName() + " simulationSetup");
	}
	
	public void init_UcsHistory(){
		ucsHistory = new UcsHistory();
		// dummy code to be deleted ;
		//AgentUcsData TempAgentData = new AgentUcsData();
		for (int i =0 ; i<=20;i++)
		{
			//TempAgentData.setUcsDailylevel(day,i);
			//TempAgentData.setUcsDailyPrice(day,i*2);
			
			/**need to be replaced by real values !!!!!!! 
			 * the values should be a mean of several of different games 
			 * and also the current game so the result would be 
			 * proper 
			 * it will be using in new class which collect all reports ( will be done tommorow) .
			 * **/
			ucsHistory.setUcsDaily(day, i, i*2, i, i);
			
		}
	}
	
	
	@Override
	protected void simulationFinished() {
		campaignReports.clear();
		bidBundle = null;
	}

	/**
	 * A user visit to a publisher's web-site results in an impression
	 * opportunity (a query) that is characterized by the the publisher, the
	 * market segment the user may belongs to, the device used (mobile or
	 * desktop) and the ad type (text or video).
	 * 
	 * An array of all possible queries is generated here, based on the
	 * publisher names reported at game initialization in the publishers catalog
	 * message
	 */
	private void generateAdxQuerySpace() {
		if (publisherCatalog != null && queries == null) {
			Set<AdxQuery> querySet = new HashSet<AdxQuery>();

			/*
			 * for each web site (publisher) we generate all possible variations
			 * of device type, ad type, and user market segment
			 */
			for (PublisherCatalogEntry publisherCatalogEntry : publisherCatalog) {
				String publishersName = publisherCatalogEntry
						.getPublisherName();
				for (MarketSegment userSegment : MarketSegment.values()) {
					Set<MarketSegment> singleMarketSegment = new HashSet<MarketSegment>();
					singleMarketSegment.add(userSegment);

					querySet.add(new AdxQuery(publishersName,
							singleMarketSegment, Device.mobile, AdType.text));

					querySet.add(new AdxQuery(publishersName,
							singleMarketSegment, Device.pc, AdType.text));

					querySet.add(new AdxQuery(publishersName,
							singleMarketSegment, Device.mobile, AdType.video));

					querySet.add(new AdxQuery(publishersName,
							singleMarketSegment, Device.pc, AdType.video));

				}

				/**
				 * An empty segments set is used to indicate the "UNKNOWN"
				 * segment such queries are matched when the UCS fails to
				 * recover the user's segments.
				 */
				querySet.add(new AdxQuery(publishersName,
						new HashSet<MarketSegment>(), Device.mobile,
						AdType.video));
				querySet.add(new AdxQuery(publishersName,
						new HashSet<MarketSegment>(), Device.mobile,
						AdType.text));
				querySet.add(new AdxQuery(publishersName,
						new HashSet<MarketSegment>(), Device.pc, AdType.video));
				querySet.add(new AdxQuery(publishersName,
						new HashSet<MarketSegment>(), Device.pc, AdType.text));
			}
			queries = new AdxQuery[querySet.size()];
			querySet.toArray(queries);
		}
	}
	
	/*genarates an array of the publishers names
	 * */
	private void getPublishersNames() {
		if (null == publisherNames && publisherCatalog != null) {
			ArrayList<String> names = new ArrayList<String>();
			for (PublisherCatalogEntry pce : publisherCatalog) {
				names.add(pce.getPublisherName());
			}

			publisherNames = new String[names.size()];
			names.toArray(publisherNames);
		}
	}
	/*
	 * genarates the campaign queries relevant for the specific campaign, and assign them as the campaigns campaignQueries field 
	 */
	private void genCampaignQueries(CampaignData campaignData) {
		Set<AdxQuery> campaignQueriesSet = new HashSet<AdxQuery>();
		for (String PublisherName : publisherNames) {
			campaignQueriesSet.add(new AdxQuery(PublisherName,
					campaignData.targetSegment, Device.mobile, AdType.text));
			campaignQueriesSet.add(new AdxQuery(PublisherName,
					campaignData.targetSegment, Device.mobile, AdType.video));
			campaignQueriesSet.add(new AdxQuery(PublisherName,
					campaignData.targetSegment, Device.pc, AdType.text));
			campaignQueriesSet.add(new AdxQuery(PublisherName,
					campaignData.targetSegment, Device.pc, AdType.video));
		}

		campaignData.setCampaignQueries(new AdxQuery[campaignQueriesSet.size()]);
		campaignQueriesSet.toArray(campaignData.getCampaignQueries());
		log.info("!!!!!!!!!!!!!!!!!!!!!!"+Arrays.toString(campaignData.getCampaignQueries())+"!!!!!!!!!!!!!!!!");

	}


}
