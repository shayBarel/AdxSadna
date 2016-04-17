import tau.tac.adx.report.adn.MarketSegment;

import java.util.HashSet;
import java.util.Set;


public class Test_Population 
{

	public static void main (String [] args) 
	{

		//test paritioning 
		TestPartitionFrom3Attributes(); 
		
		TestPartitionFrom2Attributes () ;
		
		TestPartitionFrom1Attribute () ;
		
		//test sizes .
		TestSegmentSize3Attributes();
		
		TestSegmentSize2Attributes();
		
		TestSegmentSize1Attributes();
		
	}
	
	public static void TestPartitionFrom3Attributes () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.MALE) ;
		segment.add(MarketSegment.LOW_INCOME) ;
		segment.add(MarketSegment.YOUNG) ;
		
		//test parition
		Set<Set<MarketSegment>> partitioned = Population.GetPartitionedSegments(segment) ;
		System.out.println(partitioned);		
		
	}
	
	public static void TestPartitionFrom2Attributes () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.MALE) ;
		segment.add(MarketSegment.LOW_INCOME) ;
		
		//test parition
		Set<Set<MarketSegment>> partitioned = Population.GetPartitionedSegments(segment) ;
		System.out.println(partitioned);
	}
	
	
	public static void TestPartitionFrom1Attribute () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.HIGH_INCOME) ;
		
		//test parition
		Set<Set<MarketSegment>> partitioned = Population.GetPartitionedSegments(segment);
		System.out.println(partitioned);
	}
	
	public static void TestSegmentSize3Attributes () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.MALE) ;
		segment.add(MarketSegment.LOW_INCOME) ;
		segment.add(MarketSegment.YOUNG) ;
		
		//test size
		int size = Population.GetSegmentSize(segment) ;
		System.out.println(String.format("size of segment %s, size is: %d", segment, size));			
	}

	public static void TestSegmentSize2Attributes () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.FEMALE) ;
		segment.add(MarketSegment.LOW_INCOME) ;	
		//test size
		int size = Population.GetSegmentSize(segment) ;
		System.out.println(String.format("size of segment %s, size is: %d", segment, size));			
	}

	public static void TestSegmentSize1Attributes () 
	{
		//generate a segment .
		Set<MarketSegment> segment  = new HashSet <MarketSegment> ();
		segment.add(MarketSegment.LOW_INCOME) ;	
		//test size
		int size = Population.GetSegmentSize(segment) ;
		System.out.println(String.format("size of segment %s, size is: %d", segment, size));			
	}
	
}
