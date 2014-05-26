/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 23/04/2014
 * 
 */
package asgn2Simulators;

import java.io.IOException;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.GUISimulator;

/**
 * Class to operate the simulation, taking parameters and utility methods from the Simulator
 * to control the CarPark, and using Log to provide a record of operation. 
 * @author hogan
 *
 */
public class SimulationRunner {
	private CarPark carPark;
	private Simulator sim;
	private Log log;
	
	/**
	 * Constructor just does initialisation 
	 * @param carPark CarPark currently used 
	 * @param sim Simulator containing simulation parameters
	 * @param log Log to provide logging services 
	 */
	public SimulationRunner(CarPark carPark, Simulator sim, Log log) {
		this.carPark = carPark;
		this.sim = sim;
		this.log = log;
	}
	
	
	/**
	 * Method to run the simulation from start to finish. Exceptions are propagated upwards from Vehicle,
	 * Simulation and Log objects as necessary 
	 * @throws VehicleException if Vehicle creation or operation constraints violated 
	 * @throws SimulationException if Simulation constraints are violated 
	 * @throws IOException on logging failures
	 */
	public void runSimulation() throws VehicleException, SimulationException, IOException {
	 	this.log.initialEntry(this.carPark, this.sim);
		
		for (int time=0; time<=Constants.CLOSING_TIME; time++) {
			//queue elements exceed max waiting time
			if (!this.carPark.queueEmpty()) {
				this.carPark.archiveQueueFailures(time);
			}
			//vehicles whose time has expired
			if (!this.carPark.carParkEmpty()) {
				//force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				this.carPark.archiveDepartingVehicles(time, force);
			}
			//attempt to clear the queue 
			if (!this.carPark.carParkFull()) {
				this.carPark.processQueue(time,this.sim);
			}
			// new vehicles from minute 1 until the last hour
			if (newVehiclesAllowed(time)) { 
				this.carPark.tryProcessNewVehicles(time,this.sim);
			}
			//Log progress 
			this.log.logEntry(time, this.carPark);
		}
		this.log.finalise(this.carPark);
	}

	
	/**
	 * Main program for the simulation 
	 * @param args Arguments to the simulation 
	 * @throws SimulationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SimulationException, IOException {
		int maxCarSpaces = Constants.DEFAULT_MAX_CAR_SPACES;
		int maxSmallCarSpaces = Constants.DEFAULT_MAX_SMALL_CAR_SPACES;
		int maxMotorCycleSpaces = Constants.DEFAULT_MAX_MOTORCYCLE_SPACES;
		int maxQueueSize = Constants.DEFAULT_MAX_QUEUE_SIZE;
		int seed = Constants.DEFAULT_SEED;
		double carProb = Constants.DEFAULT_CAR_PROB;
		double smallCarProb = Constants.DEFAULT_SMALL_CAR_PROB;
		double motorCycleProb = Constants.DEFAULT_MOTORCYCLE_PROB;
		double meanStay = Constants.DEFAULT_INTENDED_STAY_MEAN;
		double staySD = Constants.DEFAULT_INTENDED_STAY_SD;
			
		//Argument Processing 
		if (args.length != 0){
			if (args.length != 10) {
				System.out.println("You need to enter 10 numbers 5 ints, 5 floats, in the following format:");
				System.out.println("maxCarSpaces, maxSmallCarSpaces, maxMotorCycleSpaces, maxQueueSize, seed.");
				System.out.println("carProb (0-1), smallCarProb (0-1), motorCycleProb (0-1), meanStay, staySD.");
				System.exit(-1);
			}
			try	{
				maxCarSpaces = Integer.parseInt(args[0]);
				maxSmallCarSpaces = Integer.parseInt(args[1]);
				maxMotorCycleSpaces = Integer.parseInt(args[2]);
				maxQueueSize = Integer.parseInt(args[3]);
				seed = Integer.parseInt(args[4]);
			} catch (Exception e) {
				System.out.println("Error. First five inputs must be whole numbers. Ex: 100 20 20 10 100");
				System.exit(-1);
			}
			
			try { 
				carProb = Double.parseDouble(args[5]);
				smallCarProb = Double.parseDouble(args[6]);
				motorCycleProb = Double.parseDouble(args[7]);
				meanStay = Double.parseDouble(args[8]);
				staySD = Double.parseDouble(args[9]); 
			} catch (Exception e) {
				System.out.println("Error. Last five inputs must be floats. Ex: 1.0 0.2 0.05 120.0 39.6");
				System.exit(-1);
			}
			
			if (carProb < 0 || carProb > 1 || smallCarProb < 0 || smallCarProb > 1 || motorCycleProb < 0 
						|| motorCycleProb > 1 || meanStay < 0) {
				System.out.println("Error. Probability needs to be between 0 and 1 inclusive.\n Ex: carProb (0-1), smallCarProb (0-1), motorCycleProb (0-1)");
				System.exit(-1);
			}
		}
		
		// Run GUI
		new GUISimulator(maxCarSpaces, maxSmallCarSpaces, maxMotorCycleSpaces,  maxQueueSize, seed, 
				meanStay, staySD, carProb, smallCarProb, motorCycleProb);
		
		
		/* OLD SIMULATION. Code is now in GUI Simulator. Kept for legacy purposes.
		CarPark cp = new CarPark(maxCarSpaces, maxSmallCarSpaces, maxMotorCycleSpaces, maxQueueSize);
		Simulator s = null;
		Log l = null;
		
		try {
			s = new Simulator(seed, meanStay, staySD, carProb, smallCarProb, motorCycleProb);
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		
		//Run the simulation 
		SimulationRunner sr = new SimulationRunner(cp,s,l);
		try {
			sr.runSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} */
	}
		

	/**
	 * Helper method to determine if new vehicles are permitted
	 * @param time int holding current simulation time
	 * @return true if new vehicles permitted, false if not allowed due to simulation constraints. 
	 */
	private boolean newVehiclesAllowed(int time) {
		boolean allowed = (time >=1);
		return allowed && (time <= (Constants.CLOSING_TIME - 60));
	}

}
