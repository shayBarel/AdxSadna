


import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class UcsHistoryFile {
	

    public XStream xstream;
    public UcsDataList list;
	

	public UcsHistoryFile() {
		super();
	     try {
		      
	    	    xstream = new XStream();
	    	    xstream.alias("UCS-DATA", UcsDailyReport.class);
	    	    xstream.alias("UCS-LIST", UcsDataList.class);
	    	    xstream.addImplicitCollection(UcsDataList.class, "list");
	    	    list = new UcsDataList();


	    	  
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
		
		
		
	}

	public void AddToData(double level , double price,double target,double bid){
	      try {
	    	  
	      list.add(new UcsDailyReport(level,price,target,bid));
	    	  
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	}
	
	
	public void CreateFile(int GameNum){
	    String xml = xstream.toXML(list);
		
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream("history"+GameNum+".xml");
		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8")); //write XML header, as XStream doesn't do that for us
		    byte[] bytes = xml.getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
		    e.printStackTrace(); // this obviously needs to be refined.
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
		            e.printStackTrace(); // this obviously needs to be refined.
		        }
		    }
		}
		
	}
	
	public UcsDataList ReadFromFile(int GameNum){
		
		UcsDataList list = new UcsDataList(); //if there is an error during deserialization, this is going to be returned, is this what you want?
	    try{
	        File xmlFile = new File("history"+GameNum+".xml");
	        list = (UcsDataList) xstream.fromXML(xmlFile);       
	    }catch(Exception e){
	        System.err.println("Error in XML Read: " + e.getMessage());
	    }
	    return list;
		
		
	}
	
	

}
