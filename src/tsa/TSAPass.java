package tsa;

import java.util.Random;

public class TSAPass {
	
	int passNum;
	int arrivalTime;
	int timeWaited;
	boolean isDone;
	boolean isPreCheck;
	boolean isBodyCheck;
	boolean isBagCheck;
	final double PROB_BODY_CHECK =.29;
	final double PROB_BAG_CHECK = .29;
	final double PROB_PRE_CHECK = .45;
	
	double actionTime;

	Random myRand = new Random();
	
	public TSAPass(int passedPassNum, int passedArrivalTime){
		passNum = passedPassNum;
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
	
	public double getActionTime (){
		return actionTime;
	}
	public void setActionTime (double actionTime){
		this.actionTime = actionTime;
	}
	public void setDone(boolean isDone){
		this.isDone = isDone;
	}
	public int TotalTime(int currentTime){
		return currentTime-arrivalTime;
	}

}
