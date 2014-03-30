package GPS;

import java.util.ArrayList;

import DBMS.TownReader;

/**
 * Trieda GPS Entry
 * zaznam 1 trackpointu
 * @author Tomas
 *
 */
public class GpsEntry extends Place{
	private DandTime dandt;
	
	/**
	 * konstruktor, priradi hodnoty
	 * @param newLatitude 
	 * @param newLongitude
	 * @param DT - cas v String formate prevedia na Java.Date
	 */
	public GpsEntry(Double newLatitude, Double newLongitude, String DT)	{
		this.lat = newLatitude;
		this.lng = newLongitude;
		this.dandt = new DandTime(DT);
	}
	
	/**
	 * funkcia vrati nazov najblizsieho mesta s toleranciou v uhl. st. - param. 
	 * @param tolerance
	 * @return
	 */
	public Town GetNearestTown(double tolerance, TownReader TR){
		double nearestD = 999999999;
        Town nearestN = null;
        
        ArrayList<Town> towns = TR.getTownInTheNear(this, tolerance);
         
        for (Town town : towns){
        	double aux = this.GetDistanceTo(town);
        	if(aux < nearestD){
     	    	 nearestN = town;
     	    	 nearestD = aux;
     	     }
        }          
        return nearestN;  // !!!!! casto vrati null
	}
	
	/**
	 * vypis v tvare cas, lat, lng
	 */
	public String toString(){
		return ("time: " + this.dandt)+(" lat: " + this.lat)+(" lng: " + this.lng);
	}
	
	public DandTime getDandt() {
		return dandt;
	}

	public void setDandt(DandTime dandt) {
		this.dandt = dandt;
	}	
	
}
