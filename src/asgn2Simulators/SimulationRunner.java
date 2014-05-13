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

/**
 * Class to operate the simulation, taking parameters and utility methods from the Simulator
 * to control the CarPark, and using Log to provide a record of operation. 
 * @author hogan
 *
 */
public class SimulationRunner {
	int a = 0;
	int b = 0;
	int c = 0;
	int d = 0;
	int z = 0;
	double f = 0;
	double g = 0;
	double h = 0;
	double i = 0;
	double j = 0;
	private CarPark carPark;
	private Simulator sim;
	
	private Log log;
	
	/**
	 * Constructor just does initialisation 
	 * @param carPark CarPark currently used 
	 * @param sim Simulator containing simulation parameters
	 * @param log Log to provide logging services 
	 */
	public SimulationRunner(CarPark carPark, Simulator sim,Log log) {
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
		this.log.initialEntry(this.carPark,this.sim);
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
			this.log.logEntry(time,this.carPark);
		}
		this.log.finalise(this.carPark);
	}

	/**
	 * Main program for the simulation 
	 * @param args Arguments to the simulation 
	 * @throws SimulationException 
	 */
	public static void main(String[] args) throws SimulationException {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int z = 0;
		double f = 0;
		double g = 0;
		double h = 0;
		double i = 0;
		double j = 0;
			
		CarPark cp = new CarPark();
		Simulator s = null;
		Log l = null; 
		try {
			s = new Simulator();
			l = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		
		//TODO: Implement Argument Processing 
        System.out.println(" enter 10 numbers 5 ints, 5 doubles into main");
		
		if (args.length != 10){
			System.out.println(" You need to enter 10 numbers 5 ints, 5 doubles into main!!!!!!");
}
		try
		{a = Integer.parseInt(args[0]);
		b = Integer.parseInt(args[1]);
		c = Integer.parseInt(args[2]);
		d = Integer.parseInt(args[3]);
		z = Integer.parseInt(args[4]);
		} catch (Exception e)
		{System.out.println("Wrong input needs to be an int");
		}
		
		try
		{f = Double.parseDouble(args[5]);
		g = Double.parseDouble(args[6]);
		h = Double.parseDouble(args[7]);
		i = Double.parseDouble(args[8]);
		j = Double.parseDouble(args[9]);
		} catch (Exception e)
		{System.out.println("Wrong input needs to be a double");
		}
		if (f <=0 || f >1 || g <=0 || g >1 || h <=0 || h >1 || i <=0 || i >1 || j <=0 || j >1){
		System.out.println("Wrong input needs to be between 0 and 1 inclusive");}
			
			System.out.print(a +"  "  +b  +"  " + c  +"  "  +d  +"  " +z +" ");
			System.out.print(f  + "  " +g  +"  " + h  +"  " + i  +"  "+ j);
		
			cp = new CarPark(a,b,c,d);
			s =  new Simulator(z,f,g,h,i,j);
		//Run the simulation 
		SimulationRunner sr = new SimulationRunner(cp,s,l);
		try {
			sr.runSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
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
