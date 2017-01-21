package tsa;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class TSAController {
//----------Variables-----------------------------------------------------------------------
	final int HOURS_TO_RUN_FOR = 3;
	final int SECONDS_TO_RUN = HOURS_TO_RUN_FOR*3600;
	
	Random myRand = new Random();
	
	double ArrivalTime = 9;
	double ArrivalStDev = 9;
	
	double DocCheckTime = 11;
	double DocCheckStDev = 4;
	
	double PreBagUpTime = 25;
	double BagUpStDev = 9;
	double BagUpTime = 33;
	double MMTime = 5; 
	double MMStDev = 1;
	double GetBagTime = 30;
	double GetBagStDev = 9;
	double PreGetBagTime = 25;
	double PreGetBagStDev =9;
	double AddPersonScreeningTime = 60;
	double AddPersonScreeningStDev = 20;
	double AddBagScreeningTime = 90;
	double AddBagScreeningStDev = 20;
	
	double DocCheckStaff = 1;
	double BagUpStaff = 3*1; //3 people can have their stuff up
	double MMStaff = 1;
	double GetBagStaff = 3*1;
	double AddScreeningStaff = 1;
	
	double PreDocCheckStaff =1;
	double PreBagUpStaff =1;
	double PreMMStaff = 1;
	double PreGetBagStaff =1;
		
	TSAZone DocCheck = new TSAZone("Document Check ",DocCheckStaff);
	TSAZone BagUp = new TSAZone("Bag Up ",BagUpStaff);
	TSAZone MM = new TSAZone("MM wave ", MMStaff);
	TSAZone GetBag = new TSAZone("Get Bag ",GetBagStaff);
	TSAZone AddScreening = new TSAZone("Additional Screening ",AddScreeningStaff);
	
	TSAZone PreDocCheck = new TSAZone("Pre Document Check ", PreDocCheckStaff);
	TSAZone PreBagUp = new TSAZone("Pre Bag Up ", PreDocCheckStaff);
	TSAZone PreMM = new TSAZone("Pre MM wave ", PreMMStaff);
	TSAZone PreGetBag = new TSAZone("Pre Get Bag ", PreDocCheckStaff);
	

	double ArrivalSum = 0;
	double DocCheckSum = 0;
	double PreDocCheckSum = 0;
	double BagUpSum =0;
	double PreBagUpSum =0;
	double MMSum =0;
	double PreMMSum =0;
	double GetBagSum =0;
	double PreGetBagSum =0;
	double AddPersonScreenSum =0;
	double AddBagScreenSum =0;
	
	double RegTime =0;
	double PreTime =0;

	int PrePass =0;
	int RegPass =0;
	int BodyScreen =0;
	int BagScreen =0;
	
//----------Constructor--------------------------------------------------------------------	
		public TSAController (){
			
			int PassNum =0;
			double nextTime = 0;
			for(int time =0; time <SECONDS_TO_RUN; time++){
				if (time == nextTime){
					double RandTime = myRand.nextGaussian();
					nextTime = (Math.ceil(Math.abs(RandTime*ArrivalStDev+ArrivalTime)))+time;
					ArrivalSum+=nextTime-time;
//					System.out.println("Passenger "+ PassNum +" at time "+time+". Next at "+nextTime);
					TSAPass newPass = new TSAPass(PassNum,time);
					if (newPass.getPreCheck()){
						PreDocCheck.addPass(newPass);
						PrePass++;
					}else{
						DocCheck.addPass(newPass);
						RegPass++;
					}
					PassNum++;
				}
				leaveDocCheck(time);
				leaveBagUp(time);
				leaveMM(time);
				leaveGetBag(time);
				leaveAddScreening(time);
				leavePreDocCheck(time);
				leavePreMM(time);
				leavePreBagUp(time);
				leavePreGetBag(time);
				
			} // end for	
			
			System.out.println("Total People at/through the checkpoint: "+PassNum);
			average(ArrivalSum,PassNum,"Arrival");
			System.out.println("");
			
			System.out.println("Total People at/through the checkpoint: "+RegPass);
			average(RegTime,RegPass,"Regular passengers");
			System.out.println("");
			
			System.out.println("Total People at/through the checkpoint: "+PrePass);
			average(PreTime,PrePass,"Pre Check Passengers");
			System.out.println("");
			
			System.out.println(DocCheck.getName()+DocCheck.getPassServed()+" People still waiting: "+DocCheck.getPassHere().size());
			average(DocCheckSum,RegPass, "Document Check");
			System.out.println("");
			
			System.out.println(BagUp.getName()+BagUp.getPassServed()+" People still waiting: "+BagUp.getPassHere().size());
			average(BagUpSum,RegPass, "Bag Up");
			System.out.println("");
			
			System.out.println(MM.getName()+MM.getPassServed()+" People still waiting: "+MM.getPassHere().size());
			average(MMSum,RegPass, "MM wave");
			System.out.println("");
			
			System.out.println(GetBag.getName()+GetBag.getPassServed()+" People still waiting: "+GetBag.getPassHere().size());
			average(GetBagSum,RegPass, "Get Bag");
			System.out.println("");
			
			System.out.println(PreDocCheck.getName()+PreDocCheck.getPassServed()+" People still waiting: "+PreDocCheck.getPassHere().size());
			average(PreDocCheckSum,PrePass, "Pre Check Document Check");
			System.out.println("");
			
			System.out.println(PreBagUp.getName()+PreBagUp.getPassServed()+" People still waiting: "+PreBagUp.getPassHere().size());
			average(PreBagUpSum,PrePass, "Pre Check Bag Up");
			System.out.println("");			
			
			System.out.println(PreMM.getName()+PreMM.getPassServed()+" People still waiting: "+PreMM.getPassHere().size());
			average(PreMMSum,PrePass, "Pre Check MM Wave");
			System.out.println("");
			
			System.out.println(PreGetBag.getName()+PreGetBag.getPassServed()+" People still waiting: "+PreGetBag.getPassHere().size());
			average(PreGetBagSum,PrePass, "Pre Check Get Bag");
			System.out.println("");
			
			System.out.println(AddScreening.getName()+AddScreening.getPassServed()+" People still waiting: "+AddScreening.getPassHere().size());
			average(AddBagScreenSum,BagScreen, "Additional Bag Screening");
			average(AddPersonScreenSum,BodyScreen, "Additional Body Pat Down");
			
		}
		
		
//-----------Methods-----------------------------------------------------------------------

		
		private void leaveDocCheck(int time){
			if(DocCheck.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<DocCheckStaff&&p<DocCheck.getPassHere().size(); p++){
					if(DocCheck.getPassHere().get(p).getActionTime() ==0){ // give them a leave time
						double leaveTime = time+getTime(DocCheckTime, DocCheckStDev);
						DocCheck.getPassHere().get(p).setActionTime(leaveTime);
						DocCheckSum=DocCheckSum+leaveTime-time;
					}
					if(DocCheck.getPassHere().get(p).getActionTime() == time){ // check if they leave
						DocCheck.addPassToTotal();
						DocCheck.getPassHere().get(currentPass).setActionTime(0);
						BagUp.addPass(DocCheck.getPassHere().get(currentPass));
						DocCheck.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leavePreDocCheck(int time){
			if(PreDocCheck.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreDocCheckStaff&&p<PreDocCheck.getPassHere().size(); p++){
					if(PreDocCheck.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(DocCheckTime, DocCheckStDev);
						PreDocCheck.getPassHere().get(p).setActionTime(leaveTime);
						PreDocCheckSum=PreDocCheckSum+leaveTime-time;
					}
					if(PreDocCheck.getPassHere().get(p).getActionTime() == time){
						PreDocCheck.addPassToTotal();
						PreDocCheck.getPassHere().get(currentPass).setActionTime(0);
						PreBagUp.addPass(PreDocCheck.getPassHere().get(currentPass));
						PreDocCheck.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leaveBagUp(int time){
			if(BagUp.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<BagUpStaff&&p<BagUp.getPassHere().size(); p++){
					if(BagUp.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(BagUpTime, BagUpStDev);
						BagUp.getPassHere().get(p).setActionTime(leaveTime);
						BagUpSum=BagUpSum+leaveTime-time;
					}
					if(BagUp.getPassHere().get(p).getActionTime() == time){
						BagUp.addPassToTotal();
						BagUp.getPassHere().get(currentPass).setActionTime(0);
						MM.addPass(BagUp.getPassHere().get(currentPass));
						BagUp.getPassHere().remove(currentPass);
					}
					currentPass++;
				}
			}
		}
		private void leavePreBagUp(int time){
			if(PreBagUp.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreBagUpStaff&&p<PreBagUp.getPassHere().size(); p++){
					if(PreBagUp.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(PreBagUpTime, BagUpStDev);
						PreBagUp.getPassHere().get(p).setActionTime(leaveTime);
						PreBagUpSum=PreBagUpSum+leaveTime-time;
					}
					if(PreBagUp.getPassHere().get(p).getActionTime() == time){
						PreBagUp.addPassToTotal();
						PreBagUp.getPassHere().get(currentPass).setActionTime(0);
						PreMM.addPass(PreBagUp.getPassHere().get(currentPass));
						PreBagUp.getPassHere().remove(currentPass);
					}
					currentPass++;
				}
			}
		}
		private void leaveMM(int time){
			if(MM.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreMMStaff&&p<MM.getPassHere().size(); p++){
					if(MM.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(MMTime, MMStDev);
						MM.getPassHere().get(p).setActionTime(leaveTime);
						MMSum=MMSum+leaveTime-time;
					}
					if(MM.getPassHere().get(p).getActionTime() == time){
						if(MM.getPassHere().get(p).getBodyCheck()){
							MM.addPassToTotal();
							MM.getPassHere().get(currentPass).setActionTime(0);
							AddScreening.addPass(MM.getPassHere().get(currentPass));
							MM.getPassHere().remove(currentPass);
						}else{
							MM.addPassToTotal();
							MM.getPassHere().get(currentPass).setActionTime(0);
							GetBag.addPass(MM.getPassHere().get(currentPass));
							MM.getPassHere().remove(currentPass);
						}// end else
					}
					currentPass++;
				}
			}
		}
		private void leavePreMM(int time){
			if(PreMM.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreMMStaff&&p<PreMM.getPassHere().size(); p++){
					if(PreMM.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(MMTime, MMStDev);
						PreMM.getPassHere().get(p).setActionTime(leaveTime);
						PreMMSum=PreMMSum+leaveTime-time;
					}
					if(PreMM.getPassHere().get(p).getActionTime() == time){
						if(PreMM.getPassHere().get(p).getBodyCheck()){
							PreMM.addPassToTotal();
							PreMM.getPassHere().get(currentPass).setActionTime(0);
							AddScreening.addPass(PreMM.getPassHere().get(currentPass));
							PreMM.getPassHere().remove(currentPass);
						}else{
							PreMM.addPassToTotal();
							PreMM.getPassHere().get(currentPass).setActionTime(0);
							PreGetBag.addPass(PreMM.getPassHere().get(currentPass));
							PreMM.getPassHere().remove(currentPass);
						}// end else
					}
					currentPass++;
				}
			}
		}
		private void leaveGetBag(int time){
			if(GetBag.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<GetBagStaff&&p<GetBag.getPassHere().size(); p++){
					if(GetBag.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(GetBagTime, GetBagStDev);
						GetBag.getPassHere().get(p).setActionTime(leaveTime);
						GetBagSum=GetBagSum+leaveTime-time;
					}
					if(GetBag.getPassHere().get(p).getActionTime() == time){
						GetBag.addPassToTotal();
						GetBag.getPassHere().get(currentPass).setActionTime(0);
						GetBag.getPassHere().get(currentPass).setDone(true);
						RegTime = RegTime +GetBag.getPassHere().get(currentPass).TotalTime(time);
						GetBag.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leavePreGetBag(int time){
			if(PreGetBag.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<GetBagStaff&&p<PreGetBag.getPassHere().size(); p++){
					if(PreGetBag.getPassHere().get(p).getActionTime() ==0){
						double leaveTime = time+getTime(PreGetBagTime, PreGetBagStDev);
						PreGetBag.getPassHere().get(p).setActionTime(leaveTime);
						PreGetBagSum=PreGetBagSum+leaveTime-time;
					}
					if(PreGetBag.getPassHere().get(p).getActionTime() == time){
						PreGetBag.addPassToTotal();
						PreGetBag.getPassHere().get(currentPass).setActionTime(0);
						PreGetBag.getPassHere().get(currentPass).setDone(true);
						PreTime = PreTime +PreGetBag.getPassHere().get(currentPass).TotalTime(time);
						PreGetBag.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		
		private void leaveAddScreening(int time){
			if(AddScreening.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<AddScreeningStaff&&p<AddScreening.getPassHere().size(); p++){
					if(AddScreening.getPassHere().get(p).getBagCheck()){ // if person check
						if(AddScreening.getPassHere().get(p).getActionTime() ==0){
							double leaveTime = time+getTime(AddBagScreeningTime, AddBagScreeningStDev);
							AddScreening.getPassHere().get(p).setActionTime(leaveTime);
							AddBagScreenSum=AddBagScreenSum+leaveTime-time;
						}
						if(AddScreening.getPassHere().get(p).getActionTime() == time){
							AddScreening.addPassToTotal();
							AddScreening.getPassHere().get(currentPass).setActionTime(0);
							if(AddScreening.getPassHere().get(p).isPreCheck){
								PreGetBag.addPass(AddScreening.getPassHere().get(currentPass));
							}else{
								GetBag.addPass(AddScreening.getPassHere().get(currentPass));
							}
							AddScreening.getPassHere().remove(currentPass);
							BagScreen++;
						}
					}else{ // body pat down
						if(AddScreening.getPassHere().get(p).getActionTime() ==0){
							double leaveTime = time+getTime(AddPersonScreeningTime, AddPersonScreeningStDev);
							AddScreening.getPassHere().get(p).setActionTime(leaveTime);
							AddPersonScreenSum=AddPersonScreenSum+leaveTime-time;
						}
						if(AddScreening.getPassHere().get(p).getActionTime() == time){
							AddScreening.addPassToTotal();
							AddScreening.getPassHere().get(currentPass).setActionTime(0);
							if(AddScreening.getPassHere().get(p).isPreCheck){
								PreGetBag.addPass(AddScreening.getPassHere().get(currentPass));
							}else{
								GetBag.addPass(AddScreening.getPassHere().get(currentPass));
							}AddScreening.getPassHere().remove(currentPass);
							BodyScreen++;
						}
					}
						
				currentPass++;	
				}
			}
		}
		
		private double getTime(double avg, double stdev){
			double gaus = myRand.nextGaussian();
			double newTime = Math.ceil(Math.abs(gaus*stdev+avg));
			return newTime;
		}
		
		private void average(double sum, double total, String location){
			NumberFormat formatter = new DecimalFormat("#0.000");
			String average = formatter.format(sum/total);
			System.out.println("Average time for "+location+" : "+average);
		}
}
