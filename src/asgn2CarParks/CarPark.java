package asgn2CarParks;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Simulator;
import asgn2Simulators.Constants;
import asgn2Vehicles.Vehicle;

public class CarPark {
	
	private final int maxCarSpaces;
	private final int maxSmallCarSpaces;
    private final int maxMotorCycleSpaces;
    private final int maxQueueSize;
    
    private int numCars;
    private int shit;
    
	//CarPark constructor sets the basic size parameters. Uses default parameters
	public CarPark() {
		maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES;
	    maxSmallCarSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
	    maxMotorCycleSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
	    maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
	}
	
	//CarPark constructor sets the basic size parameters.
	public CarPark(int maxCarSpaces, int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;

	}
	
	// Archives vehicles exiting the car park after a successful stay. Includes transition via Vehicle.exitParkedState().
	public void archiveDepartingVehicles(int time, boolean force)
			throws VehicleException, SimulationException {
		if () {
			;
		}
	}
	
	// Method to archive new vehicles that don't get parked or queued and are turned away.
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		
	}
	
	// Archive vehicles which have stayed in the queue too long.
	public void archiveQueueFailures(int time) throws VehicleException {
		
	}
	
	// Simple status showing whether carPark is empty.
	public boolean carParkEmpty() {
		return false;
	}
	
	// Simple status showing whether carPark is full.
	public boolean carParkFull() {
		return false;
	}
	
	// Method to add vehicle successfully to the queue Precondition is a test that spaces are available
	// includes transition through Vehicle.enterQueuedState.
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		
	}
	
	// Method to remove vehicle from the queue after which it will be parked or removed altogether.
	// Includes transition through Vehicle.exitQueuedState.
	public void exitQueue(Vehicle v, int exitTime) throws SimulationException, VehicleException {
		
	}
	

	// State dump intended for use in logging the final state of the carpark All spaces and queue positions should be empty and so we dump the archive
	public java.lang.String finalState() {
		
	}
	
	// Simple getter for number of cars in the car park.
	public int getNumCars() {
		return 123456;
	}
	
	// Simple getter for number of motorcycles in the car park.
	public int getNumMotorCycles() {
		return 123456;
	}
	
	public int getNumSmallCars() {
		return 123456;
	}
	
	
	/**
     * **USE, BUT YOU MAY NEED TO CHANGE THE VAR NAMES ***
	 * Method used to provide the current status of the car park. 
	 * Uses private status String set whenever a transition occurs. 
	 * Example follows (using high probability for car creation). At time 262, 
	 * we have 276 vehicles existing, 91 in car park (P), 84 cars in car park (C), 
	 * of which 14 are small (S), 7 MotorCycles in car park (M), 48 dissatisfied (D),
	 * 176 archived (A), queue of size 9 (CCCCCCCCC), and on this iteration we have 
	 * seen: car C go from Parked (P) to Archived (A), C go from queued (Q) to Parked (P),
	 * and small car S arrive (new N) and go straight into the car park<br>
	 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>P|
	 * @return String containing current state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + this.spaces.size() + "::"
		+ "C:" + this.numCars + "::S:" + this.numSmallCars 
		+ "::M:" + this.numMotorCycles 
		+ "::D:" + this.numDissatisfied 
		+ "::A:" + this.past.size()  
		+ "::Q:" + this.queue.size(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	

	/**
     * SAME COMMENTS
	 * State dump intended for use in logging the initial state of the carpark.
	 * Mainly concerned with parameters. 
	 * @return String containing dump of initial carpark state 
	 */
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	
	// Simple status showing number of vehicles in the queue.
	public int numVehiclesInQueue() {
		return 123456;
	}
	
	// Method to add vehicle successfully to the car park store. Precondition is a test that spaces are available. 
	// Includes transition via Vehicle.enterParkedState.
	public void parkVehicle(Vehicle v, int time, int intendedDuration)
			throws SimulationException, VehicleException {
		
	}
	
	// Silently process elements in the queue, whether empty or not. If possible, add them to the car park. 
	// Includes transition via exitQueuedState where appropriate Block when we reach the first element that can't be parked.
	public void processQueue(int time, Simulator sim) 
			throws VehicleException, SimulationException {
		
	}
	
	// Simple status showing whether queue is empty.
	public boolean queueEmpty() {
		return false;
	}
	
	// Simple status showing whether queue is full
	public boolean queueFull() {
		return false;
	}
	
	// Method determines, given a vehicle of a particular type, whether there are spaces available for that type
	// in the car park under the parking policy in the class header.
	public boolean spacesAvailable(Vehicle v) {
		return false;
	}
	
	
	// Overrides: toString in class java.lang.Object 
	public java.lang.String toString() {
		return "Insert String Here.";
	}
	
	// Method to try to create new vehicles (one trial per vehicle type per time point)
	// and to then try to park or queue (or archive) any vehicles that are created.
	public void tryProcessNewVehicles(int time, Simulator sim)
			throws VehicleException, SimulationException {
		
	}
	
	// Method to remove vehicle from the carpark. For symmetry with parkVehicle, include transition via Vehicle.exitParkedState. 
	// So vehicle should be in parked state prior to entry to this method.
	public void unparkVehicle(Vehicle v, int departureTime)
			throws VehicleException, SimulationException {
		
	}
	
}
