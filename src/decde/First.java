package decde;

import java.util.ArrayList;

import GPS.GpxDecoder;
import GPS.Town;
import GUI.Logon_dlg;

public class First {

	public static void main(String[] args) {
         
		GpxDecoder trk2 = new GpxDecoder("trk2.gpx");
		System.out.println(trk2.GetNrOfItems());
		System.out.println(trk2.GetItemI(3474).GetDistanceTo(48.7794, 18.5831));
		System.out.println(trk2.GetItemI(3474).GetDistanceTo(48.7714, 18.6242));
		System.out.println(trk2.getStart());
		
        System.out.println("trasa: ");
        ArrayList<Town> lt = trk2.GetTownList(0.01);
        for (Town t : lt){
        	System.out.println(t.getName());
        }
        
        System.out.println(trk2.GetRouteLen());
       
	    @SuppressWarnings("unused")
		Logon_dlg lgd = Logon_dlg.displayMe();	
	    //System.out.println("wfdedfefrefrefe");
	            
   }
	
	public static void go(){
		
	}
}
