package GPS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DandTime {

	private Date val;
	
	public DandTime(String DT){
		//time: 2009-10-17T18:37:26Z
			
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss"); 
		try {
			val = ft.parse(DT);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isNull(){
		return (val == null);
	}
	
	public DandTime(Date d){
		this.val = d;
	}
	
	public String getDT(){
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
		return ft.format(val);
	}

}
