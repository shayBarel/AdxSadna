import tau.tac.adx.report.adn.MarketSegment;
import tau.tac.adx.users.AdxUser;
import tau.tac.adx.users.properties.Age;
import tau.tac.adx.users.properties.Gender;
import tau.tac.adx.users.properties.Income;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


/**
 * this class is supposed to provide population segment sizes.
 * @author NAttar
 *
 */

public class Population 
{

	
	//get values of fine partitioned segments from table .
	//gets the keys: age, gender, income - 
	// and returns the size of "fine" partitioned segment
	//(corresponds to row in table).
	//the game's actual "segments" (such as <male, young, high>) 
	//will be later created by summing multiple rows .
	static public int GetFinePartitionedSegmentSize ( Age age, Gender gender, Income income )
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());

		
		//low income
		
		//young, male, low 
		if (age.equals(Age.Age_18_24) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 526 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 371 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 263 ;
		}		
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 71 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 322 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 283 ;
		}	
		

		//old, male, low 
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 290 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 284 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.male) && income.equals(Income.low)){
			return 461 ;
		}		
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 280 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 245 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.male) && income.equals(Income.medium)){
			return 235 ;
		}			
		

		//young, female, low 
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 546 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 460 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 403 ;
		}		
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 52 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 264 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 255 ;
		}	
		

		//old, female, low 
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 457 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 450 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.female) && income.equals(Income.low)){
			return 827 ;
		}		
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 275 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 228 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.female) && income.equals(Income.medium)){
			return 164 ;
		}			
		
		
		
		//high income
		
		
		//young, male, high 
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 11 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 140 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 185 ;
		}		
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 5 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 51 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 125 ;
		}	
		

		//old, male, high 
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 197 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 157 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.male) && income.equals(Income.high)){
			return 103 ;
		}		
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 163 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 121 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.male) && income.equals(Income.very_high)){
			return 67 ;
		}			
		

		//young, female, high 
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 6 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 75 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 104 ;
		}		
		else if (age.equals(Age.Age_18_24) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 3 ;
		}
		else if (age.equals(Age.Age_25_34) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 21 ;
		}
		else if (age.equals(Age.Age_35_44) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 47 ;
		}	
		

		//old, female, high 
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 122 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 109 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.female) && income.equals(Income.high)){
			return 53 ;
		}		
		else if (age.equals(Age.Age_45_54) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 57 ;
		}
		else if (age.equals(Age.Age_55_64) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 48 ;
		}
		else if (age.equals(Age.Age_65_PLUS) && gender.equals(Gender.female) && income.equals(Income.very_high)){
			return 18 ;
		}					
		
		
		
		//error - unknown segment.
		log.severe(String.format("Unknown segment size, for segment: %s, %s,%s",
				age.toString(), gender.toString(), income.toString())) ;
		return 0 ;
	}
	
	
	//triple partitions
	//YOUNG, 	MALE,	LOW
	//OLD, 		MALE,	LOW
	//YOUNG,	FEMALE, LOW
	//OLD, 		FEMALE,	LOW
	//YOUNG,	MALE, 	HIGH
	//OLD,		MALE,	HIGH
	//YOUNG,	FEMALE, HIGH
	//OLD,		FEMALE,	HIGH
	
	//partitioned segment = segment with triple partition (i.e. FEMALE, 	OLD,	HIGH)
	static public int Get3PartitionedSegmentSize (Set<MarketSegment> seg)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());
		
		int result = 0 ;
		
		//segment is not 3-partition .
		if (seg.size() != 3)
		{
			log.severe(String.format("tried to compute size of exact segment while segment is not exact. size of segment : %d", 
					seg.size()));
			
			return 0 ;
		}
		
		//3-partition segment
		
		//YOUNG, 	MALE,	LOW
		if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.MALE) 
				&& seg.contains(MarketSegment.LOW_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.male, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.male, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.male, Income.medium) ;
		}

		//OLD, 		MALE,	LOW		
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.MALE) 
				&& seg.contains(MarketSegment.LOW_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.male, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.male, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.male, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.male, Income.medium) ;
		}
		

		//YOUNG,	FEMALE, LOW
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.FEMALE) 
				&& seg.contains(MarketSegment.LOW_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.female, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.female, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.female, Income.medium) ;	
		}

		//OLD, 		FEMALE,	LOW
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.FEMALE) 
				&& seg.contains(MarketSegment.LOW_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.female, Income.low) ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.female, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.female, Income.medium) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.female, Income.medium) ;
		}

		
		//high income
		
		//YOUNG, 	MALE,	HIGH
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.MALE) 
				&& seg.contains(MarketSegment.HIGH_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.male, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.male, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.male, Income.very_high) ;
		}

		//OLD, 		MALE,	HIGH		
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.MALE) 
				&& seg.contains(MarketSegment.HIGH_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.male, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.male, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.male, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.male, Income.very_high) ;
		}
		

		//YOUNG,	FEMALE, HIGH
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.FEMALE) 
				&& seg.contains(MarketSegment.HIGH_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_18_24, Gender.female, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_25_34, Gender.female, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_35_44, Gender.female, Income.very_high) ;	
		}

		//OLD, 		FEMALE,	HIGH
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.FEMALE) 
				&& seg.contains(MarketSegment.HIGH_INCOME) )
		{
			result = 0 ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.female, Income.high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_45_54, Gender.female, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_55_64, Gender.female, Income.very_high) ;
			result += GetFinePartitionedSegmentSize(Age.Age_65_PLUS, Gender.female, Income.very_high) ;
		}		
		
		else 
		{
			//error - did not find segment
			log.severe(String.format("Unknown segment : %s", seg.toString())) ;	
			return 0;
		}


		//trace log segment size
		log.fine(String.format("Segment : %s size is : %d", seg.toString(), result)) ;
		
		return result;
	}
	
	
	static public int Get2PartitionedSegmentSize (Set<MarketSegment> seg)
	{
		
		int result = 0 ;
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());

		//1 GROUP: NO AGE
		
		//MALE, LOW
		if (seg.contains(MarketSegment.LOW_INCOME) && seg.contains(MarketSegment.MALE))
		{
			//YOUNG , MALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , MALE, LOW
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.MALE) ;
			partition2.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}
		
		//FEMALE, LOW
		else if (seg.contains(MarketSegment.LOW_INCOME) && seg.contains(MarketSegment.FEMALE))
		{
			//YOUNG , FEMALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.FEMALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , FEMALE, LOW
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
			
		}

		//MALE, HIGH
		else if (seg.contains(MarketSegment.HIGH_INCOME) && seg.contains(MarketSegment.MALE))
		{
			//YOUNG , MALE, HIGH
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , MALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.MALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
			
		}
		
		//FEMALE, HIGH
		else if (seg.contains(MarketSegment.HIGH_INCOME) && seg.contains(MarketSegment.FEMALE))
		{
			//YOUNG , FEMALE, HIGH
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.FEMALE) ;
			partition1.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , FEMALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}	
		
					
		//2ND GROUP: NO INCOME
		
		//YOUNG, MALE
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.MALE))
		{
			//YOUNG , MALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//YOUNG , MALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.YOUNG) ;
			partition2.add(MarketSegment.MALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}
		
		//OLD, MALE
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.MALE))
		{
			//OLD , MALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.OLD) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , MALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.MALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}
		
		//YOUNG, FEMALE
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.FEMALE))
		{
			//YOUNG , FEMALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.FEMALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//YOUNG , FEMALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.YOUNG) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}
		
		//OLD, FEMALE
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.FEMALE))
		{
			//OLD , FEMALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.OLD) ;
			partition1.add(MarketSegment.FEMALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , FEMALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}			
		
					
		//3RD GROUP: NO GENDER
		
		//YOUNG, LOW
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.LOW_INCOME))
		{
			//YOUNG , MALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//YOUNG , FEMALE, LOW
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.YOUNG) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}		
		
		//OLD, LOW
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.LOW_INCOME))
		{
			//OLD , MALE, LOW
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.OLD) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , FEMALE, LOW
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.LOW_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}	
		
		//YOUNG, HIGH
		else if (seg.contains(MarketSegment.YOUNG) && seg.contains(MarketSegment.HIGH_INCOME))
		{
			//YOUNG , MALE, HIGH
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//YOUNG , FEMALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.YOUNG) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}		
		
		//OLD, HIGH
		else if (seg.contains(MarketSegment.OLD) && seg.contains(MarketSegment.HIGH_INCOME))
		{
			//OLD , MALE, HIGH
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.OLD) ;
			partition1.add(MarketSegment.MALE) ;
			partition1.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition1);

			//OLD , FEMALE, HIGH
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			partition2.add(MarketSegment.HIGH_INCOME) ;
			result += Get3PartitionedSegmentSize(partition2);
		}				
		else 
		{
			//error - did not find segment
			log.severe(String.format("Unknown segment : %s", seg.toString())) ;	
			return 0;
		}


		//trace log segment size
		log.fine(String.format("Segment of 2 attributes: %s, size is : %d", seg.toString(), result)) ;
						
		
		return result; 
	}
	
	
	static public int Get1PartitionedSegmentSize (Set<MarketSegment> seg)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());

		int result = 0 ;
		
		//LOW INCOME
		if (seg.contains(MarketSegment.LOW_INCOME))
		{
			
			//LOW, MALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.LOW_INCOME) ;
			partition1.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//LOW, FEMALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.LOW_INCOME) ;
			partition2.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}
	
		
		//HIGH INCOME
		else if (seg.contains(MarketSegment.HIGH_INCOME))
		{
			
			//HIGH, MALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.HIGH_INCOME) ;
			partition1.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//HIGH, FEMALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.HIGH_INCOME) ;
			partition2.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}
		
		//MALE
		else if (seg.contains(MarketSegment.MALE))
		{
			
			//HIGH, MALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.HIGH_INCOME) ;
			partition1.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//LOW, MALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.LOW_INCOME) ;
			partition2.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}		
		
		//FEMALE
		else if (seg.contains(MarketSegment.FEMALE))
		{
			
			//HIGH, FEMALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.HIGH_INCOME) ;
			partition1.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//LOW, FEMALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.LOW_INCOME) ;
			partition2.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}		
	
		//YOUNG
		else if (seg.contains(MarketSegment.YOUNG))
		{
			
			//YOUNG, MALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.YOUNG) ;
			partition1.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//YOUNG, FEMALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.YOUNG) ;
			partition2.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}			

		//OLD
		else if (seg.contains(MarketSegment.OLD))
		{
			
			//OLD, MALE
			Set<MarketSegment> partition1 = new HashSet<MarketSegment>();
			partition1.add(MarketSegment.OLD) ;
			partition1.add(MarketSegment.MALE) ;
			result += Get2PartitionedSegmentSize(partition1);	
			
			//OLD, FEMALE
			Set<MarketSegment> partition2 = new HashSet<MarketSegment>();
			partition2.add(MarketSegment.OLD) ;
			partition2.add(MarketSegment.FEMALE) ;
			result += Get2PartitionedSegmentSize(partition2);	
		}
		else 
		{
			//error - did not find segment
			log.severe(String.format("Unknown segment : %s", seg.toString())) ;	
			return 0;
		}


		//trace log segment size
		log.fine(String.format("Segment of 1 attributes: %s, size is : %d", seg.toString(), result)) ;
						
		
		return result ;
		
	}
	
	
	//get size of segment (it may be partitioned) 
	static public int GetSegmentSize (Set<MarketSegment> seg)
	{
		
		Logger log = Logger
				.getLogger(SimpleAdNetwork.class.getName());

		int result = 0 ;
		
		//segment of 1 attribute (i.e. "female" )
		if (seg.size() == 1)
		{
			result = Get1PartitionedSegmentSize(seg);	
		}
		
		//segment of 2 attributes (i.e. "young, high" )
		else if (seg.size() == 2)
		{
			result = Get2PartitionedSegmentSize(seg);
		}

		//segment of 3 attributes (i.e. "young, female, high" )
		else if (seg.size() == 3)
		{
			result = Get3PartitionedSegmentSize(seg);
			
		}
		else
		{
			//error
			log.severe(String.format("could not get segment population size. segment attributes length is unsupported."
					+ "segment: %s, number of attributes %d:", seg.toString(), seg.size()));
		}
	
		log.fine(String.format("calculated size of segment: %s size: %d.",
				seg.toString(), result ));
	
		
		return result ;
	}
	
	
}
