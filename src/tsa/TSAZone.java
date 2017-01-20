package tsa;

import java.util.LinkedList;
import java.util.List;

public class TSAZone {
	String name;
	List<TSAPass> passHere;
	int maxQueueLength;
	int currentQueueLength;
	
	public TSAZone(String PassedName){
		name = PassedName;
		passHere = new LinkedList<>();
		maxQueueLength = 0;
		currentQueueLength = 0;
		
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

}
