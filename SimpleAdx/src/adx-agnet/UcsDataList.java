import java.util.ArrayList;
import java.util.List;

public class UcsDataList {
	
	   private List<UcsDailyReport> list;

	    public UcsDataList(){
	        list = new ArrayList<UcsDailyReport>();
	    }

	    public void add(UcsDailyReport p){
	        list.add(p);
	    }

}
