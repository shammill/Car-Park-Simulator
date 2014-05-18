/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 *
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * @author Laurence McCabe (Base Methods)
 * @author Samuel Hammill (Refactoring, Constants, & some methods)
 */
public class MotorCycleTests {

	// Defaults
	private String DEFAULT_VEH_ID = "MC1";
	private int DEFAULT_ARRIVAL_TIME = 1;
	private int DEFAULT_PARK_TIME = 1;
	private int DEFAULT_INTENDED_DURATION = Constants.MINIMUM_STAY;
	private int DEFAULT_QUEUE_DEPARTURE_TIME = 2;
	
	// Conditional
	private int LATE_PARK_TIME = 2;
	private int ZERO_ARRIVAL_TIME = 0;
	private int NEGATIVE_ARRIVAL_TIME = -1;
	private int NEGATIVE_PARKING_TIME = -1;
	private int INVALID_INTENDED_DURATION = (Constants.MINIMUM_STAY - 1);
	
	private int DEFAULT_PARK_DEPARTURE_TIME = (DEFAULT_PARK_TIME + Constants.MINIMUM_STAY);
	private int INVALID_PARK_DEPARTURE_TIME = 0;
	
	private int DEFAULT_EXIT_QUEUE_TIME = 2;
	private int INVALID_EXIT_QUEUE_TIME = 1;

	@Before
	public void setUp() throws Exception {
	//MotorCycle defaultValidMotorCycle = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Tests MotorCycle Constructor correctly sets vehId.
	*/
	@Test
	public void testMotorCycleConstructorVehId() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		assertEquals(m.getVehID(), DEFAULT_VEH_ID);
	}
	
	// ------------------------------------------------------------------------------------------------------------------	
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Tests MotorCycle Constructor correctly sets arrival time.
	*/	
	@Test
	public void testMotorCycleConstructorArrivalTime() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		assertEquals(m.getArrivalTime(), DEFAULT_ARRIVAL_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that motorcycle throws an exception if the arrival time entered is 0.
	*/	
	@Test(expected = VehicleException.class)
	public void testMotorCycleConstructorZeroArrivalTime() throws VehicleException {
		 new MotorCycle(DEFAULT_VEH_ID, ZERO_ARRIVAL_TIME);
	}

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that motorcycle throws an exception if the arrival time entered less than 0.
	*/	
	@Test(expected = VehicleException.class)
	public void testMotorCycleConstructorNegativeArrivalTime() throws VehicleException {
		new MotorCycle(DEFAULT_VEH_ID, NEGATIVE_ARRIVAL_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle is not created in a parked state.
	*/	
	@Test
	public void testMotorCycleConstructorIsNotParked() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertFalse(m.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle is not created in a queued state.
	*/	
	@Test
	public void testMotorCycleConstructorIsNotQueued() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertFalse(m.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle is not created in a satisfied state.
	*/	
	@Test
	public void testMotorCycleConstructorIsNotSatisfied() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertFalse(m.isSatisfied());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new vehicle has not parked yet.
	*/	
	@Test
	public void testMotorCycleConstructorWasNotParked() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertFalse(m.wasParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new vehicle has not queued yet.
	*/	
	@Test
	public void testMotorCycleConstructorWasNotQueued() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertFalse(m.wasQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle is a type of vehicle.
	*/	
	@Test
	public void testMotorCycleIsInstanceOfVehicle() throws VehicleException {
		 MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		 assertTrue(m instanceof Vehicle);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle can be parked without error.
	*/	
	@Test
	public void testEnterParkedState() throws VehicleException  {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(m.isParked());
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle can be parked after being queued without error.
	*/	
	@Test
	public void testEnterParkedStateFromQueue() throws VehicleException  {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(DEFAULT_QUEUE_DEPARTURE_TIME);
		m.enterParkedState(LATE_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(m.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle parks, and sets the parking time correctly.
	*/
	@Test
	public void testEnterParkedStateParkingTimeValid() throws VehicleException  {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(m.getParkingTime(), DEFAULT_PARK_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle parks, and sets the departure time correctly.
	*/	
	@Test
	public void testEnterParkedStateDepartureTimeValid() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(m.getDepartureTime(), (DEFAULT_PARK_TIME + DEFAULT_INTENDED_DURATION));
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test parking an already parked vehicle.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileParked() throws VehicleException  {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test parking a vehicle still in the queue.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileQueued() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test parking a vehicle with negative parking time.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateNegativeParkingTime() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(NEGATIVE_PARKING_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test parking a vehicle with too low of a intended duration.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateInvalidDuration() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, INVALID_INTENDED_DURATION);
	}
	

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the new motorcycle can be queued without error.
	*/	
	@Test
	public void testEnterQueuedState() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		assertTrue(m.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the cannot be queued while it is parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileParked() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.enterQueuedState();
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that vehicle the cannot be queued while it is already queued.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileQueued() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.enterQueuedState();
	}

	

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can successfully unpark after parking.
	*/	
	@Test
	public void testExitParkedState() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertFalse(m.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can is successfully marked as having been parked.
	*/	
	@Test
	public void testExitParkedStateWasParked() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(m.wasParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can is successfully marked as having been satisfied.
	*/	
	@Test
	public void testExitParkedStateIsSatisfied() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(m.isSatisfied());
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test unparking without staying long enough.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateInvalidDepartureTime() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitParkedState(INVALID_PARK_DEPARTURE_TIME);
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test for VehicleException if vehicle tries to exit park while not parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhenNotParked() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test for VehicleException if vehicle tries to exit park while not parked, after parking.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateAfterExiting() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test for VehicleException if vehicle tries to exit park while queued.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhileQueued() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can successfully exit queue after queuing.
	*/	
	@Test
	public void testExitQueuedState() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertFalse(m.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that ExitQueue successfully sets Exit/Departure Time.
	*/	
	@Test
	public void testExitQueuedStateDepartureTime() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertEquals(m.getDepartureTime(), DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle is marked as having been queued after exiting.
	*/	
	@Test
	public void testExitQueuedStateWasQueued() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertTrue(m.wasQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can't exit queue when parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateWhenParked() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can't exit queue after already exiting queue.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterExitingQueue() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can't exit queue after creation.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterCreation() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Laurence McCabe (Base Methods)
	* @author Samuel Hammill (Refactoring & Constants)
	* Test that the vehicle can't exit queue after creation.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateInvalidExitTime() throws VehicleException {
		MotorCycle m = new MotorCycle(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME);
		m.enterQueuedState();
		m.exitQueuedState(INVALID_EXIT_QUEUE_TIME);
	}

}