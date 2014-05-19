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

	/**
	* Tests Car Constructor correctly sets vehId.
	* @author Samuel Hammill
	*/
	@Test
	public void testCarConstructorVehId() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertEquals(c.getVehID(), DEFAULT_VEH_ID);
	}
		
	/**
	* Tests Car Constructor correctly sets arrival time.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorArrivalTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertEquals(c.getArrivalTime(), DEFAULT_ARRIVAL_TIME);
	}
	
	
	/**
	* Tests Car Constructor correctly sets if the car is not small.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorIsNotSmall() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertFalse(c.isSmall());
	}
	
	/**
	* Tests Car Constructor correctly sets if the car is small.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorIsSmall() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		assertTrue(c.isSmall());
	}
	
	
	/**
	* Test that Car throws an exception if the arrival time entered is 0.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testCarConstructorZeroArrivalTime() throws VehicleException {
		new Car(DEFAULT_VEH_ID, ZERO_ARRIVAL_TIME, NOT_SMALL_CAR);
	}

	/**
	* Test that Car throws an exception if the arrival time entered less than 0.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testCarConstructorNegativeArrivalTime() throws VehicleException {
		 new Car(DEFAULT_VEH_ID, NEGATIVE_ARRIVAL_TIME, NOT_SMALL_CAR);
	}
	
	/**
	* Test that the vehicle is not created in a parked state.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorIsNotParked() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isParked());
	}
	
	/**
	* Test that the vehicle is not created in a queued state.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorIsNotQueued() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isQueued());
	}
	
	/**
	* Test that the vehicle is created in a satisfied state.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorIsSatisfied() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.isSatisfied());
	}
	
	/**
	* Test that the new vehicle has not parked yet.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorWasNotParked() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.wasParked());
	}
	
	/**
	* Test that the new vehicle has not queued yet.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarConstructorWasNotQueued() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertFalse(c.wasQueued());
	}
	
	/**
	* Test that the new Car is a type of vehicle.
	* @author Samuel Hammill
	*/	
	@Test
	public void testCarIsInstanceOfVehicle() throws VehicleException {
		 Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		 assertTrue(c instanceof Vehicle);
	}
	
	/**
	* Test that the new Car can be parked without error.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterParkedState() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(c.isParked());
	}
	
	/**
	* Test that the new car can be parked and made satisfied.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterParkedStateSatisfaction() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(c.isSatisfied());
	}
	
	
	/**
	* Test that the new Car can be parked after being queued without error.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterParkedStateFromQueue() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_QUEUE_DEPARTURE_TIME);
		c.enterParkedState(LATE_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertTrue(c.isParked());
	}
	
	/**
	* Test that the new Car parks, and sets the parking time correctly.
	* @author Samuel Hammill
	*/
	@Test
	public void testEnterParkedStateParkingTimeValid() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(c.getParkingTime(), DEFAULT_PARK_TIME);
	}
	
	/*
	* Test that the new Car parks, and sets the departure time correctly.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterParkedStateDepartureTimeValid() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		assertEquals(c.getDepartureTime(), (DEFAULT_PARK_TIME + DEFAULT_INTENDED_DURATION));
	}
	
	/**
	* Test parking an already parked vehicle.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileParked() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	/**
	* Test parking a vehicle still in the queue.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	/**
	* Test parking a vehicle with negative parking time.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateNegativeParkingTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(NEGATIVE_PARKING_TIME, DEFAULT_INTENDED_DURATION);
	}
	
	/**
	* Test parking a vehicle with too low of a intended duration.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterParkedStateInvalidDuration() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, INVALID_INTENDED_DURATION);
	}
	
	/**
	* Test that the new Car can be queued without error.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterQueuedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		assertTrue(c.isQueued());
	}
	
	/**
	* Test that the new car can be queued and made satisfied.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterQueuedStateSatisfaction() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		assertTrue(c.isSatisfied());
	}
	
	/**
	* Test that the cannot be queued while it is parked.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.enterQueuedState();
	}
	
	/**
	* Test that vehicle the cannot be queued while it is already queued.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testEnterQueuedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.enterQueuedState();
	}

	/**
	* Test that the vehicle can successfully unpark after parking.
	* @author Samuel Hammill
	*/	
	@Test
	public void testExitParkedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertFalse(c.isParked());
	}
	
	/**
	* Test that the vehicle can is successfully marked as having been parked.
	* @author Samuel Hammill
	*/	
	@Test
	public void testExitParkedStateWasParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(c.wasParked());
	}
	
	/**
	* Test that the vehicle can is successfully marked as having been satisfied.
	* @author Samuel Hammill
	*/	
	@Test
	public void testExitParkedStateIsSatisfied() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		assertTrue(c.isSatisfied());
	}
	
	/**
	* Test unparking without staying long enough.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateInvalidDepartureTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(INVALID_PARK_DEPARTURE_TIME);
	}
	
	/**
	* Test for VehicleException if vehicle tries to exit park while not parked.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhenNotParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	/**
	* Test for VehicleException if vehicle tries to exit park while not parked, after parking.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateAfterExiting() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	/**
	* Test for VehicleException if vehicle tries to exit park while queued.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitParkedStateWhileQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitParkedState(DEFAULT_PARK_DEPARTURE_TIME);
	}
	
	/**
	* Test that the vehicle can successfully exit queue after queuing.
	* @author Samuel Hammill	
	*/	
	@Test
	public void testExitQueuedState() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertFalse(c.isQueued());
	}
	
	/**
	* Test that the new motorcycle exits the queue without parking and is dissatisfied.
	* @author Samuel Hammill
	*/	
	@Test
	public void testEnterExitQueuedStateSatisfaction() throws VehicleException  {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertFalse(c.isSatisfied());
	}
	
	/**
	* Test that ExitQueue successfully sets Exit/Departure Time.
	* @author Samuel Hammill
	*/	
	@Test
	public void testExitQueuedStateDepartureTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertEquals(c.getDepartureTime(), DEFAULT_EXIT_QUEUE_TIME);
	}
	
	/**
	* Test that the vehicle is marked as having been queued after exiting.
	* @author Samuel Hammill
	*/	
	@Test
	public void testExitQueuedStateWasQueued() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		assertTrue(c.wasQueued());
	}
	
	/**
	* Test that the vehicle can't exit queue when parked.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateWhenParked() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterParkedState(DEFAULT_PARK_TIME, DEFAULT_INTENDED_DURATION);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	/**
	* Test that the vehicle can't exit queue after already exiting queue.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterExitingQueue() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	/**
	* Test that the vehicle can't exit queue after creation.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateAfterCreation() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.exitQueuedState(DEFAULT_EXIT_QUEUE_TIME);
	}
	
	/**
	* Test that the vehicle can't exit queue after creation.
	* @author Samuel Hammill
	*/	
	@Test(expected = VehicleException.class)
	public void testExitQueuedStateInvalidExitTime() throws VehicleException {
		Car c = new Car(DEFAULT_VEH_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		c.enterQueuedState();
		c.exitQueuedState(INVALID_EXIT_QUEUE_TIME);
	}
}
