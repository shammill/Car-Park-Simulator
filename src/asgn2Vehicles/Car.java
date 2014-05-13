/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 20/04/2014
 * 
 */
package asgn2Vehicles;


/**
 * 
 * The Car class is a specialisation of the Vehicle class to cater for production cars
 * This version of the class does not cater for model types, but records whether or not the 
 * vehicle can use a small parking space. 
 * @author Samuel Hammill
 *
 */
import asgn2Exceptions.VehicleException;

public class Car extends Vehicle {
	
	private boolean small;
	
	/**
	 * The Car Constructor - small set at creation, not mutable. 
	 * @param vehID - identification number or plate of the vehicle
	 * @param arrivalTime - time (minutes) at which the vehicle arrives and is 
	 *        either queued or given entry to the carpark 
	 * @param small - indicator whether car is regarded as small or not
	 * @throws VehicleException if arrivalTime is <= 0  
	 * @author Samuel Hammill
	 */
	public Car(String vehID, int arrivalTime, boolean small) throws VehicleException {
		
		super(vehID, arrivalTime);
		this.small = small;
	}
	
	
	/**
	 * Boolean status indicating whether car is small enough for small 
	 * car parking spaces  
	 * @return true if small parking space, false otherwise
	 * @author Samuel Hammill
	 */
	public boolean isSmall() {
		return this.small;
	}

	
	/* (non-Javadoc)
	 * @see asgn2Vehicles.Vehicle#toString()
	 */
	@Override
	public String toString() {
		String str = "";
    	str += "Vehicle vehID: " + this.getVehID() + "\n"
    			+ "Arrival Time: " + this.getArrivalTime() + "\n";
    			
    	if (this.wasQueued()) {
    		if (this.wasParked()) {
    			str += "Exit from Queue: " + this.getParkingTime() + "\n"
    					+ "Queuing Time: " + (this.getParkingTime() - this.getArrivalTime()) + "\n";	
    		}
    		else {
    			str += "Exit from Queue: " + this.getDepartureTime() + "\n"
    					+ "Queuing Time: " + (this.getDepartureTime() - this.getArrivalTime()) + "\n";
    		}
    	}
    	else {
    		str += "Vehicle was not queued.\n";
    	}
    	
    	if (this.wasParked()) {
    			str += "Entry to Car Park: " + this.getParkingTime() + "\n"
        			+ "Exit from Car Park: " + this.getDepartureTime() + "\n"
        			+ "Parking Time: " +  (this.getDepartureTime() - this.getParkingTime()) + "\n"
        			+ "Customer was satisfied.\n";
    	}
    	else {
    		str += "Customer was not satisfied.\n";
    	}
    			
    	if (this.isSmall()) {
			str += "Car can use small car parking space.\n";
    	}
    	else {
    		str += "Car can not use small car parking space.\n";
    	}
    	return str;
	}
}