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
import asgn2Simulators.Constants;

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

	
	/**
	 * Creates and returns a string detailing information about an individual 
	 * vehicle at the end of the simulation. Used by the car parks finalState()
	 * method as it loops through all vehicles.
	 * @return A string containing all of the information about a vehicle.
	 * @author Samuel Hammill
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
    		str += "Vehicle was not queued\n";
    	}
    	
    	if (this.wasQueued() & !this.wasParked()) {
    		str += "Exceeded maximum acceptable queuing time by: " + ((this.getDepartureTime() - this.getArrivalTime()) - Constants.MAXIMUM_QUEUE_TIME) + "\n";
    	}
    	
    	if (this.wasParked()) {
    			str += "Entry to Car Park: " + this.getParkingTime() + "\n"
        			+ "Exit from Car Park: " + this.getDepartureTime() + "\n"
        			+ "Parking Time: " +  (this.getDepartureTime() - this.getParkingTime()) + "\n"
        			+ "Customer was satisfied\n";
    	}
    	else {
    		str += "Vehicle was not parked\nCustomer was not satisfied\n";
    	}
    			
    	if (this.isSmall()) {
			str += "Car can use small car parking space";
    	}
    	else {
    		str += "Car cannot use small parking space";
    	}
    	return str;
	}
}