package asgn2Vehicles;

import asgn2Vehicles.Vehicle;
import asgn2Exceptions.VehicleException;

public class Car extends Vehicle {
	
	private final boolean isSmall;
	
	public Car(java.lang.String vehID, int arrivalTime, boolean small)
			throws VehicleException {
		
		super(vehID, arrivalTime);
		
		if (arrivalTime <=0) {
			throw new VehicleException("arrivalTime must be > 0.");
		}
		
		isSmall = small; 
	}
	
	
	// Boolean status indicating whether car is small enough for small car parking spaces
	public boolean isSmall() {
		return isSmall;
	}
	
	// Overrides: toString in class Vehicle 
	public java.lang.String toString() {
		return "string override";
	}
	
}