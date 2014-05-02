package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;

public abstract class Vehicle {
	
	private String vehID;
	private int arrivalTime;
	private int departureTime;
	private int parkingTime;
	
	private int exitQueueTime;		// This might get the axe.
	
	private boolean isQueued;
	private boolean isParked;
	private boolean isSatisfied;
	private boolean wasQueued;
	private boolean wasParked;
	
	// Vehicle Constructor
	public Vehicle(java.lang.String vehID, int arrivalTime)
			throws VehicleException {
		
		if (arrivalTime <= 0) {
			throw new VehicleException("Arrival Time Must Be Greater Than 0.");
		}
		
		this.vehID = vehID;
		this.arrivalTime = arrivalTime;
	}
	
	
	// Transition vehicle to parked state (mutator) Parking starts on arrival or on exit from the queue, but time is set here
	public void enterParkedState(int parkingTime, int intendedDuration)
			throws VehicleException {
		
		if (isQueued == true | isParked == true) {
			throw new VehicleException("Unable to queue, car already queued or parked.");
		}
		
		if (parkingTime < 0) {
		 	throw new VehicleException("Parking Time Must Be Greater Than 0.");
		}
		
		if (intendedDuration < Constants.MINIMUM_STAY) {
			throw new VehicleException("Stay duration must be greater than the minimum defined.");
		}
		
		this.parkingTime = parkingTime;
		departureTime = parkingTime + intendedDuration;
		
		isParked = true;
		wasParked = true;
	}
		
	
	// Transition vehicle to queued state (mutator) Queuing formally starts on arrival and ceases with a call to exitQueuedState
	public void enterQueuedState()
            throws VehicleException {
		
		if (isQueued == true | isParked == true) {
			throw new VehicleException("Unable to queue, car already queued or parked.");
		}
		
		isQueued = true;
		wasQueued = true;
	}
	
	
	// Transition vehicle from parked state (mutator).
	public void exitParkedState(int departureTime)
            throws VehicleException {
		
		if (isParked == false) {
			throw new VehicleException("Unable to exitParkedState. Vehicle is not currently parked.");
		}	
		
		if (isQueued == true) {
			throw new VehicleException("Unable to exitParkedState. Vehicle is currently in the queue.");
		}	
		
		if (departureTime < parkingTime) {
			throw new VehicleException("Unable to exitParkedState. departureTime is less than parkingTime.");
		}	
		
		isParked = false;
		this.departureTime = departureTime; 
	}
	
	
	// Transition vehicle from queued state (mutator) Queuing formally starts on arrival with a call to 
	// enterQueuedState Here we exit and set the time at which the vehicle left the queue.
    public void exitQueuedState(int exitTime)
            throws VehicleException {
    	
		if (isParked == true) {
			throw new VehicleException("Unable to exitQueuedState. Vehicle is currently parked.");
		}	
		
		if (isQueued == false) {
			throw new VehicleException("Unable to exitQueuedState. Vehicle is not currently queued.");
		}	
		
		if (exitTime <= arrivalTime) {
			throw new VehicleException("Unable to exitQueuedState. exitTime must be later than arrivalTime.");
		}
		
		isQueued = false;
		exitQueueTime = exitTime; 	// This might need to be changed to exitQueueTime = arrivalTime, as it is never used elsewhere, 
									// and has no getter, but they want us to set it... We'll see...
	}

    // Simple getter for the arrival time.
    public int getArrivalTime() {
    	return arrivalTime;
    }
    
    // Simple getter for the departure time from the car park Note: result may be 0 before parking, show intended 
    // departure time while parked; and actual when archived.
    public int getDepartureTime() {
    	return departureTime;    	
    }
    
    
    // Simple getter for the parking time Note: result may be 0 before parking
    public int getParkingTime() {
    	return parkingTime;
    }
    
    
    // Simple getter for the vehicle ID
    public java.lang.String getVehID() {
    	return vehID;								// Unsure how to do override. Will come back to this.
    }
    
    // Boolean status indicating whether vehicle is currently parked.
    public boolean isParked() {
    	return isParked;
    }
    
    // Boolean status indicating whether vehicle is currently queued.
    public boolean isQueued() {
    	return isQueued;
    }
    
    // Boolean status indicating whether customer is satisfied or not Satisfied if they park; dissatisfied 
    // if turned away, or queuing for too long Note that calls to this method may not reflect final status.
    public boolean isSatisfied() {
    	return isSatisfied;
    }
    

    // Overrides: toString in class java.lang.Object 
    public java.lang.String toString() {			
    	return "Insert String Here";					// Unsure how to do override. Will come back to this.
    }
    
    // Boolean status indicating whether vehicle was ever parked Will return false for vehicles in queue or turned away
    public boolean wasParked() {
    	return wasParked;
    }
    
    // Boolean status indicating whether vehicle was ever queued
    public boolean wasQueued() {
    	return wasQueued;
    }
    
            
}
