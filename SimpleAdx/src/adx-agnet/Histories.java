
import tau.tac.adx.report.adn.AdNetworkKey;
import tau.tac.adx.report.adn.AdNetworkReportEntry;

import java.util.HashMap;
import java.util.Map;

public class Histories {
	public Histories(){}
	Map<Integer,Map<AdNetworkKey,AdNetworkReportEntry>> AdNetworkReportHistory = new HashMap<Integer,Map<AdNetworkKey,AdNetworkReportEntry>>();
	Map<Integer, Double> CostHistory = new HashMap<Integer, Double>();
	Map<Integer, Double> BankHistory = new HashMap<Integer, Double>();

	public void addCostHistory(int day, double cost)
	{
		CostHistory.put(day, cost);
	}
	public void addBankHistory(int day, double bank)
	{
		BankHistory.put(day, bank);
	}
	public Map<Integer, Double> getCostHistory() {
		return CostHistory;
	}
	public void setCostHistory(Map<Integer, Double> costHistory) {
		CostHistory = costHistory;
	}
	public Map<Integer, Double> getBankHistory() {
		return BankHistory;
	}
	public void setBankHistory(Map<Integer, Double> bankHistory) {
		BankHistory = bankHistory;
	}
	public Map<Integer, Map<AdNetworkKey, AdNetworkReportEntry>> getAdNetworkReportHistory() {
		return AdNetworkReportHistory;
	}
	public void addAdnetworkReport(Map<AdNetworkKey, AdNetworkReportEntry> report, int day)
	{
		AdNetworkReportHistory.put(day, report);
	}
	public void setAdNetworkReportHistory(Map<Integer, Map<AdNetworkKey, AdNetworkReportEntry>> adNetworkReportHistory) {
		AdNetworkReportHistory = adNetworkReportHistory;
	}
	


}
