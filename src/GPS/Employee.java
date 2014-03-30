package GPS;

import java.util.ArrayList;

public class Employee {
	
	private int ID;
	private String FName;
	private String SName;
	private String DName;
	private ArrayList<Route> routes;
	
	public Employee(int iD, String fName, String sName, String dName) {
		super();
		ID = iD;
		FName = fName;
		SName = sName;
		DName = dName;
	}
	
	public void addRoute(Route rt){
		routes.add(rt);
		//TODO vybav v DB
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFName() {
		return FName;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public String getSName() {
		return SName;
	}

	public void setSName(String sName) {
		SName = sName;
	}

	public String getDName() {
		return DName;
	}

	public void setDName(String dName) {
		DName = dName;
	}


}
