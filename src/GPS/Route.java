package GPS;


public class Route {
	
	public int ID;
	private DandTime start;
	private DandTime end;
	private double kmsLen;
	//private ArrayList<Town> wayPts; //4Hibernate
	//private ArrayList<Employee> participants;
	
	
	


	public Route(int iD, DandTime start, DandTime end, double kmsLen) {
		super();
		ID = iD;
		this.start = start;
		this.end = end;
		this.kmsLen = kmsLen;
	}

	public void setStart(DandTime start) {
		this.start = start;
	}
	
	public DandTime getEnd() {
		return end;
	}
	
	public void setEnd(DandTime end) {
		this.end = end;
	}
	
	public double getKmsLen() {
		return kmsLen;
	}
	
	public void setKmsLen(double kmsLen) {
		this.kmsLen = kmsLen;
	}
	
	public DandTime getStart() {
		return start;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
