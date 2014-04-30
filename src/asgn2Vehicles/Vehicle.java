package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

public abstract class Vehicle {
	
	// Vehicle Constructor
	public Vehicle(java.lang.String vehID, int arrivalTime)
			throws VehicleException {
		
	}
	
	// Transition vehicle to parked state (mutator) Parking starts on arrival or on exit from the queue, but time is set here
	public void enterParkedState(int parkingTime, int intendedDuration)
			throws VehicleException {
		
	}
	
	// Transition vehicle to queued state (mutator) Queuing formally starts on arrival and ceases with a call to exitQueuedState
	public void enterQueuedState()
            throws VehicleException {
		
	}
	
	// Transition vehicle from parked state (mutator).
	public void exitParkedState(int departureTime)
            throws VehicleException {
		
	}
	
	// Transition vehicle from queued state (mutator) Queuing formally starts on arrival with a call to 
	// enterQueuedState Here we exit and set the time at which the vehicle left the queue.
    public void exitQueuedState(int exitTime)
            throws VehicleException {
		
	}

    // Simple getter for the arrival time.
    public int getArrivalTime() {
    	return 123456;
    }
    
    // Simple getter for the departure time from the car park Note: result may be 0 before parking, show intended 
    // departure time while parked; and actual when archived.
    public int getDepartureTime() {
    	return 123456;    	
    }
    
    
    // Simple getter for the parking time Note: result may be 0 before parking
    public int getParkingTime() {
    	return 123456;
    }
    
    
    // Simple getter for the vehicle ID
    public java.lang.String getVehID() {
    	return "Insert String Here";
    }
    
    // Boolean status indicating whether vehicle is currently parked.
    public boolean isParked() {
    	return false;
    }
    
    // Boolean status indicating whether vehicle is currently queued.
    public boolean isQueued() {
    	return false;
    }
    
    // Boolean status indicating whether customer is satisfied or not Satisfied if they park; dissatisfied 
    // if turned away, or queuing for too long Note that calls to this method may not reflect final status.
    public boolean isSatisfied() {
    	return false;
    }
    

    // Overrides: toString in class java.lang.Object 
    public java.lang.String toString() {
    	return "Insert String Here";
    }
    
    // Boolean status indicating whether vehicle was ever parked Will return false for vehicles in queue or turned away
    public boolean wasParked() {
    	return false;
    }
    
    // Boolean status indicating whether vehicle was ever queued
    public boolean wasQueued() {
    	return false;
    }
    
            
}
