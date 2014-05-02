package asgn2Vehicles;

import asgn2Exceptions.VehicleException;


public class MotorCycle extends Vehicle {
	
	// MotorCycle constructor
	public MotorCycle(java.lang.String vehID, int arrivalTime)
			throws VehicleException {
		super(vehID, arrivalTime);
		
		if (arrivalTime <=0) {
			throw new VehicleException("arrivalTime must be > 0.");
		}
		
	}	
	
}