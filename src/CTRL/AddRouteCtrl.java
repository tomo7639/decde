package CTRL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRouteCtrl {
	
	public static void main(String [] args){
		//2013-03-26T18:59:02
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss"); 
		try {
			Date d = ft.parse("2013-03-26T18:59:02");
			System.out.println(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
