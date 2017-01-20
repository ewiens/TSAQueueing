package tsa;
import java.util.LinkedList;
import java.util.List;
public class TSAController {
//----------Variables----------------------------------------------------------------------
	// people through each Zone
	final int HOURS_TO_RUN_FOR = 3;
	final int SECONDS_TO_RUN = HOURS_TO_RUN_FOR*3600;
	
	
	double AStaff = 1;
	double BStaff = 1;
	double CStaff = 1;
	double DStaff = 1;
	
	double PreAStaff =1;
	double PreBStaff =1;
	double PreCStaff =1;
		
	TSAZone ZoneA = new TSAZone("A",AStaff);
	TSAZone ZoneB = new TSAZone("B",BStaff);
	TSAZone ZoneC = new TSAZone("C",CStaff);
	TSAZone ZoneD = new TSAZone("D",DStaff);
	
	TSAZone ZonePreA = new TSAZone("Pre A", PreAStaff);
	TSAZone ZonePreB = new TSAZone("Pre B", PreAStaff);
	TSAZone ZonePreC = new TSAZone("Pre C", PreAStaff);
	
	
	
	
//----------Constructor--------------------------------------------------------------------	
		public TSAController (){
			
			int PassNum =0;
			for(int time =0; time <SECONDS_TO_RUN; time++){
				if (time%9==0){	
//					System.out.println("Passenger "+ PassNum +" at time "+time);
					TSAPass newPass = new TSAPass(PassNum,time);
					if (newPass.getPreCheck()){
						ZonePreA.addPass(newPass);
					}else{
						ZoneA.addPass(newPass);
					}
					PassNum++;
				}
				leaveZoneA(time);
				leaveZoneB(time);
				leaveZoneC(time);
				leaveZoneD(time);
				leavePreZoneA(time);
				leavePreZoneB(time);
				leavePreZoneC(time);
				
			} // end for	
			System.out.println("Total People at/through the checkpoint: "+PassNum);
			System.out.println("A People Served: "+ZoneA.getPassServed()+" People still waiting: "+ZoneA.getPassHere().size());
			System.out.println("B People Served: "+ZoneB.getPassServed()+" People still waiting: "+ZoneB.getPassHere().size());
			System.out.println("C People Served: "+ZoneC.getPassServed()+" People still waiting: "+ZoneC.getPassHere().size());
			System.out.println("D People Served: "+ZoneD.getPassServed()+" People still waiting: "+ZoneD.getPassHere().size());
			System.out.println("Pre A People Served: "+ZonePreA.getPassServed()+" People still waiting: "+ZonePreA.getPassHere().size());
			System.out.println("Pre B People Served: "+ZonePreB.getPassServed()+" People still waiting: "+ZonePreB.getPassHere().size());
			System.out.println("Pre C People Served: "+ZonePreC.getPassServed()+" People still waiting: "+ZonePreC.getPassHere().size());
		}
		
		
//-----------Methods-----------------------------------------------------------------------

		
		private void leaveZoneA(int time){
			if(ZoneA.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<AStaff&&p<ZoneA.getPassHere().size(); p++){
					if(ZoneA.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 11+time;
						ZoneA.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZoneA.getPassHere().get(p).getActionTime() == time){
						ZoneA.addPassToTotal();
						ZoneA.getPassHere().get(currentPass).setActionTime(0);
						ZoneB.addPass(ZoneA.getPassHere().get(currentPass));
						ZoneA.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leavePreZoneA(int time){
			if(ZonePreA.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreAStaff&&p<ZonePreA.getPassHere().size(); p++){
					if(ZonePreA.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 11+time;
						ZonePreA.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZonePreA.getPassHere().get(p).getActionTime() == time){
						ZonePreA.addPassToTotal();
						ZonePreA.getPassHere().get(currentPass).setActionTime(0);
						ZonePreB.addPass(ZonePreA.getPassHere().get(currentPass));
						ZonePreA.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leaveZoneB(int time){
			if(ZoneB.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<BStaff&&p<ZoneB.getPassHere().size(); p++){
					if(ZoneB.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 28+time;
						ZoneB.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZoneB.getPassHere().get(p).getActionTime() == time){
						if(ZoneB.getPassHere().get(p).getBodyCheck()){
							ZoneB.addPassToTotal();
							ZoneB.getPassHere().get(currentPass).setActionTime(0);
							ZoneD.addPass(ZoneB.getPassHere().get(currentPass));
							ZoneB.getPassHere().remove(currentPass);
						}else{
							ZoneB.addPassToTotal();
							ZoneB.getPassHere().get(currentPass).setActionTime(0);
							ZoneC.addPass(ZoneB.getPassHere().get(currentPass));
							ZoneB.getPassHere().remove(currentPass);
						}// end else
					}
					currentPass++;
				}
			}
		}
		private void leavePreZoneB(int time){
			if(ZonePreB.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<PreBStaff&&p<ZonePreB.getPassHere().size(); p++){
					if(ZonePreB.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 20+time;
						ZonePreB.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZonePreB.getPassHere().get(p).getActionTime() == time){
						if(ZonePreB.getPassHere().get(p).getBodyCheck()){
							ZonePreB.addPassToTotal();
							ZonePreB.getPassHere().get(currentPass).setActionTime(0);
							ZoneD.addPass(ZonePreB.getPassHere().get(currentPass));
							ZonePreB.getPassHere().remove(currentPass);
						}else{
							ZonePreB.addPassToTotal();
							ZonePreB.getPassHere().get(currentPass).setActionTime(0);
							ZonePreC.addPass(ZonePreB.getPassHere().get(currentPass));
							ZonePreB.getPassHere().remove(currentPass);
						}// end else
					}
					currentPass++;
				}
			}
		}
		private void leaveZoneC(int time){
			if(ZoneC.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<CStaff&&p<ZoneC.getPassHere().size(); p++){
					if(ZoneC.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 11+time;
						ZoneC.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZoneC.getPassHere().get(p).getActionTime() == time){
						ZoneC.addPassToTotal();
						ZoneC.getPassHere().get(currentPass).setActionTime(0);
						ZoneC.getPassHere().get(currentPass).setDone(true);
						ZoneC.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		private void leavePreZoneC(int time){
			if(ZonePreC.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<CStaff&&p<ZonePreC.getPassHere().size(); p++){
					if(ZonePreC.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 11+time;
						ZonePreC.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZonePreC.getPassHere().get(p).getActionTime() == time){
						ZonePreC.addPassToTotal();
						ZonePreC.getPassHere().get(currentPass).setActionTime(0);
						ZonePreC.getPassHere().get(currentPass).setDone(true);
						ZonePreC.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		
		private void leaveZoneD(int time){
			if(ZoneD.getPassHere().size()>0){
				int currentPass = 0;
				for(int p=0; p<DStaff&&p<ZoneD.getPassHere().size(); p++){
					if(ZoneD.getPassHere().get(p).getActionTime() ==0){
						int leaveTime = 11+time;
						ZoneD.getPassHere().get(p).setActionTime(leaveTime);
					}
					if(ZoneD.getPassHere().get(p).getActionTime() == time){
						ZoneD.addPassToTotal();
						ZoneD.getPassHere().get(currentPass).setActionTime(0);
						ZoneC.addPass(ZoneD.getPassHere().get(currentPass));
						ZoneD.getPassHere().remove(currentPass);
					}
				currentPass++;	
				}
			}
		}
		
		
}
