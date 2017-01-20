package tsa;

import java.util.LinkedList;
import java.util.List;

public class TSAZone {
	String name;
	List<TSAPass> passHere;
	int maxQueueLength;
	int currentQueueLength;
	double TeamsOpen;
	int PassServed;
	
	public TSAZone(String PassedName, double Teams){
		name = PassedName;
		passHere = new LinkedList<>();
		maxQueueLength = 0;
		currentQueueLength = 0;
		TeamsOpen = Teams;
		PassServed =0;
	}

	
	public void addPass(TSAPass arrivedPatient)
	{
		passHere.add(arrivedPatient);
		
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public List<TSAPass> getPassHere(){
		return passHere;
	}
	
	public void addPassToTotal(){
		PassServed++;
	}
	
	public int getPassServed(){
		return PassServed;
	}

}
