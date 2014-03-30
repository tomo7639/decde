package GPS;

public class Place {
	protected double lat=0;
	protected double lng=0;
	
	/**
	 * funkcia vrati vzdialenost medzi THIS a druhym bodom "To" - param.
	 * @param To 
	 * @return
	 */
	public double GetDistanceTo(Place To)	{
		return GetDistanceTo(To.getLat(), To.getLng());
	}
	
	/**
	 * funkcia vrati vzdialenost medzi THIS a suradnicami "lat" a "lng" - param.
	 * @param lat
	 * @param lng
	 * @return
	 */
	public double GetDistanceTo(double lat, double lng){
		double diameter = lat/90*6378 + (90-lat)/90*6357; 
		double dx = (this.lat - lat) / 180 * diameter * Math.PI;
		double dy = (this.lng - lng) / 180 * diameter * Math.PI * Math.sin(lat / 180 * Math.PI );
		return Math.sqrt(dx*dx + dy*dy); 				
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLng() {
		return lng;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
}
