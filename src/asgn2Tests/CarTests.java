/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;

/**
 * @author Samuel Hammill
 * 
 */
public class CarTests {

	// Defaults
	private String DEFAULT_VEH_ID = "C1";
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
	
	private boolean SMALL_CAR = true;
	private boolean NOT_SMALL_CAR = false;

	@Before
	public void setUp() throws Exception {
	//Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Tests Car Constructor correctly sets vehId.
	*/
	@Test
	public void testCarConstructorVehId() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertEquals(c.getVehID(), DEFAULT_VEH_ID);
	}
	
	// ------------------------------------------------------------------------------------------------------------------	
	/**
	* @author Samuel Hammill
	* 
	* Tests Car Constructor correctly sets arrival time.
	*/	
	@Test
	public void testCarConstructorArrivalTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertEquals(c.getArrivalTime(), DEFAULT_ARRIVAL_TIME);
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------	
	/**
	* @author Samuel Hammill
	* 
	* Tests Car Constructor correctly sets if the car is not small.
	*/	
	@Test
	public void testCarConstructorIsNotSmall() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertFalse(c.isSmall());
	}
	
	// ------------------------------------------------------------------------------------------------------------------	
	/**
	* @author Samuel Hammill
	* 
	* Tests Car Constructor correctly sets if the car is small.
	*/	
	@Test
	public void testCarConstructorIsSmall() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		assertTrue(c.isSmall());
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that Car throws an exception if the arrival time entered is 0.
	*/	
	@Test(expected = VehicleException.class)
	public void testCarConstructorZeroArrivalTime() throws VehicleException {
		new Car(DEFAULT_VEH_ID, ZERO_ARRIVAL_TIME, NOT_SMALL_CAR);
	}

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that Car throws an exception if the arrival time entered less than 0.
	*/	
	@Test(expected = VehicleException.class)
	public void testCarConstructorNegativeArrivalTime() throws VehicleException {
		 new Car(DEFAULT_VEH_ID, NEGATIVE_ARRIVAL_TIME, NOT_SMALL_CAR);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle is not created in a parked state.
	*/	
	@Test
	public void testCarConstructorIsNotParked() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle is not created in a queued state.
	*/	
	@Test
	public void testCarConstructorIsNotQueued() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle is not created in a satisfied state.
	*/	
	@Test
	public void testCarConstructorIsNotSatisfied() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isSatisfied());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new vehicle has not parked yet.
	*/	
	@Test
	public void testCarConstructorWasNotParked() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.wasParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new vehicle has not queued yet.
	*/	
	@Test
	public void testCarConstructorWasNotQueued() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.wasQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car is a type of vehicle.
	*/	
	@Test
	public void testCarIsInstanceOfVehicle() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertTrue(c instanceof Vehicle);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car can be parked without error.
	*/	
	@Test
	public void testEnterParkedState() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(c.isParked());
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car can be parked after being queued without error.
	*/	
	@Test
	public void testEnterParkedStateFromQueue() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_QUEUE_DEPARTURE_TIME);
		c.enterParkedState(LATE_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(c.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car parks, and sets the parking time correctly.
	*/
	@Test
	public void testEnterParkedStateParkingTimeValid() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(c.getParkingTime(), DEFAULT_PARK_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car parks, and sets the departure time correctly.
	*/	
	@Test
	public void testEnterParkedStateDepartureTimeValid() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(c.getDepartureTime(), (DEFAULT_PARK_TIME + DEFAULT_INTENDED_DURATION));
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test parking an already parked vehicle.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileParked() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test parking a vehicle still in the queue.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test parking a vehicle with negative parking time.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateNegativeParkingTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(NEGATIVE_PARKING_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test parking a vehicle with too low of a intended duration.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateInvalidDuration() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, INVALID_INTENDED_DURATION);
	}
	

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the new Car can be queued without error.
	*/	
	@Test
	public void testEnterQueuedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		assertTrue(c.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the cannot be queued while it is parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.enterQueuedState();
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that vehicle the cannot be queued while it is already queued.
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.enterQueuedState();
	}

	

	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can successfully unpark after parking.
	*/	
	@Test
	public void testExitParkedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertFalse(c.isParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can is successfully marked as having been parked.
	*/	
	@Test
	public void testExitParkedStateWasParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(c.wasParked());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can is successfully marked as having been satisfied.
	*/	
	@Test
	public void testExitParkedStateIsSatisfied() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(c.isSatisfied());
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test unparking without staying long enough.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateInvalidDepartureTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(INVALID_PARK_DEPARTURE_TIME);
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test for VehicleException if vehicle tries to exit park while not parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhenNotParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test for VehicleException if vehicle tries to exit park while not parked, after parking.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateAfterExiting() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test for VehicleException if vehicle tries to exit park while queued.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can successfully exit queue after queuing.
	*/	
	@Test
	public void testExitQueuedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertFalse(c.isQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that ExitQueue successfully sets Exit/Departure Time.
	*/	
	@Test
	public void testExitQueuedStateDepartureTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertEquals(c.getDepartureTime(), DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle is marked as having been queued after exiting.
	*/	
	@Test
	public void testExitQueuedStateWasQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertTrue(c.wasQueued());
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can't exit queue when parked.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateWhenParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can't exit queue after already exiting queue.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterExitingQueue() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can't exit queue after creation.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterCreation() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	// ------------------------------------------------------------------------------------------------------------------
	/**
	* @author Samuel Hammill
	* 
	* Test that the vehicle can't exit queue after creation.
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateInvalidExitTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(INVALID_EXIT_QUEUE_TIME);
	}

}
