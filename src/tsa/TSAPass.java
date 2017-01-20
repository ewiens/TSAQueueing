package tsa;

import java.util.Random;

public class TSAPass {
	
	int patientNum;
	int arrivalTime;
	int timeWaited;
	boolean isDone;
	boolean isPreCheck;
	boolean isBodyCheck;
	boolean isBagCheck;
	final double PROB_BODY_CHECK =.29;
	final double PROB_BAG_CHECK = .29;
	final double PROB_PRE_CHECK = .45;
	
	int actionTime;

	Random myRand = new Random();
	
	public TSAPass(int passedPatientNum, int passedArrivalTime){
		patientNum = passedPatientNum;
		arrivalTime = passedArrivalTime;
		isDone = false;
		isPreCheck = preCheck();
		if (isPreCheck){
			isBodyCheck = bodyCheck();
			isBagCheck = bagCheck();
		}
	}
	
	private boolean preCheck(){
		if (myRand.nextDouble()>PROB_PRE_CHECK){
			return false;
		}
		return true;
	}
	public boolean getPreCheck(){
		return isPreCheck;
	}
	
	private boolean bodyCheck(){
		if (myRand.nextDouble()>PROB_BODY_CHECK){
			return false;
		}
		return true;
	}
	public boolean getBodyCheck(){
		return isBodyCheck;
	}
	private boolean bagCheck(){
		if (myRand.nextDouble()>PROB_BAG_CHECK){
			return false;
		}
		return true;
	}
	public boolean getBagCheck(){
		return isBagCheck;
	}
	
	public int getActionTime (){
		return actionTime;
	}
	public void setActionTime (int actionTime){
		this.actionTime = actionTime;
	}
	public void setDone(boolean isDone){
		this.isDone = isDone;
	}

}
