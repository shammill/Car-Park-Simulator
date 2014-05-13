/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 19/04/2014
 * 
 */
package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;



/**
 * Vehicle is an abstract class specifying the basic state of a vehicle and the methods used to 
 * set and access that state. A vehicle is created upon arrival, at which point it must either 
 * enter the car park to take a vacant space or become part of the queue. If the queue is full, then 
 * the vehicle must leave and never enters the car park. The vehicle cannot be both parked and queued 
 * at once and both the constructor and the parking and queuing state transition methods must 
 * respect this constraint. 
 * 
 * Vehicles are created in a neutral state. If the vehicle is unable to park or queue, then no changes 
 * are needed if the vehicle leaves the carpark immediately.
 * Vehicles that remain and can't park enter a queued state via {@link #enterQueuedState() enterQueuedState} 
 * and leave the queued state via {@link #exitQueuedState(int) exitQueuedState}. 
 * Note that an exception is thrown if an attempt is made to join a queue when the vehicle is already 
 * in the queued state, or to leave a queue when it is not. 
 * 
 * Vehicles are parked using the {@link #enterParkedState(int, int) enterParkedState} method and depart using 
 * {@link #exitParkedState(int) exitParkedState}
 * 
 * Note again that exceptions are thrown if the state is inappropriate: vehicles cannot be parked or exit 
 * the car park from a queued state. 
 * 
 * The method javadoc below indicates the constraints on the time and other parameters. Other time parameters may 
 * vary from simulation to simulation and so are not constrained here.  
 * 
 * @author Samuel Hammill
 *
 */
public abstract class Vehicle {
	
	private String vehID;
	private int arrivalTime;
	private int departureTime;
	private int parkingTime;
	
	private boolean isQueued = false;
	private boolean isParked = false;
	private boolean isSatisfied = false;
	private boolean wasQueued = false;
	private boolean wasParked = false;
	
	/**
	 * Vehicle Constructor
	 * @param vehID String identification number or plate of the vehicle
	 * @param arrivalTime int time (minutes) at which the vehicle arrives and is 
	 *        either queued, given entry to the car park or forced to leave
	 * @throws VehicleException if arrivalTime is <= 0 
	 * @author Samuel Hammill
	 */
	public Vehicle(String vehID, int arrivalTime) throws VehicleException  {
		
		if (arrivalTime <= 0) {
			throw new VehicleException("Arrival Time Must Be Greater Than 0.");
		}
		
		this.vehID = vehID;
		this.arrivalTime = arrivalTime;

	}
	
	
	/**
	 * Transition vehicle to parked state (mutator)
	 * Parking starts on arrival or on exit from the queue, but time is set here
	 * @param parkingTime int time (minutes) at which the vehicle was able to park
	 * @param intendedDuration int time (minutes) for which the vehicle is intended to remain in the car park.
	 *  	  Note that the parkingTime + intendedDuration yields the departureTime
	 * @throws VehicleException if the vehicle is already in a parked or queued state, if parkingTime < 0, 
	 *         or if intendedDuration is less than the minimum prescribed in asgnSimulators.Constants
	 * @author Samuel Hammill
	 */
	public void enterParkedState(int parkingTime, int intendedDuration) throws VehicleException {
		
		if (isQueued | isParked) {
			throw new VehicleException("Unable to queue, car already queued or parked.");
		}
		
		if (parkingTime <= 0) {
		 	throw new VehicleException("Parking Time Must Be Greater Than 0.");
		}
		
		if (intendedDuration < Constants.MINIMUM_STAY) {
			throw new VehicleException("Stay duration must be greater than or equal to the minimum defined.");
		}
		
		this.parkingTime = parkingTime;
		this.departureTime = parkingTime + intendedDuration;
		
		isParked = true;
		wasParked = true;
		isSatisfied = true;
	}
		
	
	/**
	 * Transition vehicle to queued state (mutator) 
	 * Queuing formally starts on arrival and ceases with a call to {@link #exitQueuedState(int) exitQueuedState}
	 * @throws VehicleException if the vehicle is already in a queued or parked state
	 * @author Samuel Hammill
	 */
	public void enterQueuedState() throws VehicleException {
		
		if (isQueued | isParked) {
			throw new VehicleException("Unable to queue, car already queued or parked.");
		}
		
		isQueued = true;
		wasQueued = true;
	}
	
	
	/**
	 * Transition vehicle from parked state (mutator) 
	 * @param departureTime int holding the actual departure time 
	 * @throws VehicleException if the vehicle is not in a parked state, is in a queued 
	 * 		  state or if the revised departureTime < parkingTime
	 * @author Samuel Hammill
	 */
	public void exitParkedState(int departureTime) throws VehicleException {
		
		if (!isParked) {
			throw new VehicleException("Unable to exitParkedState. Vehicle is not currently parked.");
		}	
		
		if (isQueued) {
			throw new VehicleException("Unable to exitParkedState. Vehicle is currently in the queue.");
		}	
		
		if (departureTime < parkingTime) {
			throw new VehicleException("Unable to exitParkedState. departureTime is less than parkingTime.");
		}	
		
		isParked = false;
		this.departureTime = departureTime;
	}
	
	
	/**
	 * Transition vehicle from queued state (mutator) 
	 * Queuing formally starts on arrival with a call to {@link #enterQueuedState() enterQueuedState}
	 * Here we exit and set the time at which the vehicle left the queue
	 * @param exitTime int holding the time at which the vehicle left the queue 
	 * @throws VehicleException if the vehicle is in a parked state or not in a queued state, or if 
	 *  exitTime is not later than arrivalTime for this vehicle
	 * @author Samuel Hammill
	 */
	public void exitQueuedState(int exitTime) throws VehicleException {
    	
		if (isParked) {
			throw new VehicleException("Unable to exitQueuedState. Vehicle is currently parked.");
		}	
		
		if (!isQueued) {
			throw new VehicleException("Unable to exitQueuedState. Vehicle is not currently queued.");
		}	
		
		if (exitTime <= arrivalTime) {
			throw new VehicleException("Unable to exitQueuedState. exitTime must be later than arrivalTime.");
		}
		
		isQueued = false;
		departureTime = exitTime; 
	}

    
	/**
	 * Simple getter for the arrival time 
	 * @return the arrivalTime
	 * @author Samuel Hammill
	 */
	public int getArrivalTime() {
    	return arrivalTime;
    }
    
	
	/**
	 * Simple getter for the departure time from the car park
	 * Note: result may be 0 before parking, show intended departure 
	 * time while parked; and actual when archived
	 * @return the departureTime
	 * @author Samuel Hammill
	 */
	public int getDepartureTime() {
    	return departureTime;    	
    }
    
    
	/**
	 * Simple getter for the parking time
	 * Note: result may be 0 before parking
	 * @return the parkingTime
	 * @author Samuel Hammill
	 */
	public int getParkingTime() {
    	return parkingTime;
    }
    
    
	/**
	 * Simple getter for the vehicle ID
	 * @return the vehID
	 * @author Samuel Hammill
	 */
	public String getVehID() {
    	return vehID;
    }
    
	
	/**
	 * Boolean status indicating whether vehicle is currently parked 
	 * @return true if the vehicle is in a parked state; false otherwise
	 * @author Samuel Hammill
	 */
	public boolean isParked() {
    	return isParked;
    }
    
	
	/**
	 * Boolean status indicating whether vehicle is currently queued
	 * @return true if vehicle is in a queued state, false otherwise 
	 * @author Samuel Hammill
	 */
	public boolean isQueued() {
    	return isQueued;
    }
    
	
	/**
	 * Boolean status indicating whether customer is satisfied or not
	 * Satisfied if they park; dissatisfied if turned away, or queuing for too long 
	 * Note that calls to this method may not reflect final status 
	 * @return true if satisfied, false if never in parked state or if queuing time exceeds max allowable 
	 * @author Samuel Hammill
	 */
	public boolean isSatisfied() {
    	return isSatisfied;
    }
    

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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
    	return str;
    }
    
	
	/**
	 * Boolean status indicating whether vehicle was ever parked
	 * Will return false for vehicles in queue or turned away 
	 * @return true if vehicle was or is in a parked state, false otherwise
	 * @author Samuel Hammill
	 */
	public boolean wasParked() {
    	return wasParked;
    }
    
	
	/**
	 * Boolean status indicating whether vehicle was ever queued
	 * @return true if vehicle was or is in a queued state, false otherwise 
	 * @author Samuel Hammill
	 */
	public boolean wasQueued() {
    	return wasQueued;
    }
            
}
