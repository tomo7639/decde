package GPS;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import DBMS.TownReader;

public class GpxDecoder {
	
	private NodeList trackpts;
	
	/** 
	 * @param filename - cesta k suboru na dekodovanie
	 */
	public GpxDecoder(String filename){
		Parse(filename);
	}
	
	private void Parse(String filename) 
	{
		File route = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(route);
			doc.getDocumentElement().normalize();
			trackpts = doc.getElementsByTagName("trkpt");	
		} 
		catch (Exception e) {
			System.err.println("ERROR: GPX file opening has failed");	
			e.printStackTrace();
		}		
	}
			
	/**
	 * @return pocet trackpointov v tracku 
	 */
	public int GetNrOfItems(){
		return trackpts.getLength();
	}
	
	/**
	 * @param i
	 * @return GpsEntry - "i"-ty trackpoint
	 */
	public GpsEntry GetItemI(int i) 
	{
		try {
			Node trkpt = trackpts.item(i);
			NamedNodeMap trkptLL = trkpt.getAttributes();
    	    
		    Double newLatitude = Double.parseDouble(trkptLL.getNamedItem("lat").getTextContent());
		    Double newLongitude = Double.parseDouble(trkptLL.getNamedItem("lon").getTextContent());
		    String time = ((Element)trkpt).getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
		    		    
			return new GpsEntry(newLatitude,newLongitude,time);	
		}
		catch (Exception e){
			System.err.println("ERROR: No such an index");
			e.printStackTrace();
		}		
		return new GpsEntry(0.0,0.0,"2000-00-01T00:00:00Z");	
	}	
	
	/**
	 ** @return double dlzku trasy v tracku
	 */
	public double GetRouteLen(){   //zmen na protected
		double dist = 0;
		GpsEntry prev = GetItemI(0);
		for (int i=1; i<GetNrOfItems();i++)	{
			GpsEntry actual = GetItemI(i);
			dist += prev.GetDistanceTo(actual);
			prev = actual;
		}		
		return dist;
	}
	
   
	/**
	 ** @param tolerance - tolerancia v uhl. st.	
	 * @return ArrayList<Town> - mesta - waypointy na ceste 
	 */
	public ArrayList<Town> GetTownList (double tolerance){    //zmen na protected
		
		ArrayList<Town> reslt = new ArrayList<Town>();
		int n = GetNrOfItems();
		if (n==0){
			return reslt;
		}
		
		TownReader TR = new TownReader();		
		reslt.add(GetItemI(0).GetNearestTown(tolerance, TR));
		for (int i = 1;i<n;i++){
			Town akt = GetItemI(i).GetNearestTown(tolerance, TR);
			if(akt!=null && reslt.get(0)==null){
				reslt.set(0, akt);
			}
			if (akt != null && !akt.getName().equals(reslt.get(reslt.size()-1).getName())){
				reslt.add(akt);
			}				
		}
		TR.closeCnn();
		return reslt;
	}
	
	public DandTime getStart(){
		return GetItemI(0).getDandt();
	}
	
	public DandTime getEnd(){
		return GetItemI(GetNrOfItems()-1).getDandt();
	}
}


