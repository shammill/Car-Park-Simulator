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

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;
import asgn2Vehicles.Car;

/**
 * @author hogan
 * 
 */
public class MotorCycleTests {
	MotorCycle mot;
	CarPark CarP;
	Car car;
	Car sCar;

	// All tests done at boundaries - I am pretty sure they are, so not much need so far to be testing many
	// boundaries in this class.

	// Do we need this one?
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	// Do we need this one?

	@After
	public void tearDown() throws Exception {
	}

	// ------------------------------------------------------------------------------------------------------------------
	@Test
	public void testMotorCycle() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		assertTrue(mot.getVehID() == "b1");
		assertTrue(mot.getArrivalTime() == 3);

	}

	// Test that motorcycle throws an exception if the arrival time entered is 0
	// or less.
	@Test(expected = Exception.class)
	public void testMotorCycleException() throws VehicleException {
		mot = new MotorCycle("b1", 0);
	}

	// ------------------------------------------------------------------------------------------------------------------

	// Tested using testGetVehId and GetVehArrivalTime. Do we need to test this
	// again?
	@Test
	public void testVehicle() throws VehicleException {

	}

	// ------------------------------------------------------------------------------------------------------------------
	// Test that GetVehId returns the correct Vehicle id when passed string id's
	// through it's 2 concrete classes:
	// MotorCycle and car. should this be done in a separate vehicleTest class?
	@Test
	public void testGetVehID() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		assertTrue(mot.getVehID() == "b1");
		assertTrue(car.getVehID() == "c1");
		assertTrue(sCar.getVehID() == "sc1");
	}

	// ------------------------------------------------------------------------------------------------------------------
	// Test that GetArrivalTime returns the correct arrival time for car, small
	// car and motorcycle.
	@Test
	public void testGetArrivalTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		assertTrue(mot.getArrivalTime() == 3);
		assertTrue(car.getArrivalTime() == 3);
		assertTrue(sCar.getArrivalTime() == 3);
	}

	// ------------------------------------------------------------------------------------------------------------------
	// Adds vehicles to queue then checks to see if EnterQueuedState returns
	// true.
	// Too basic, need to test it more as it does not incorporate other ways of
	// storing
	// vehicles into queues, like through trynewprocessVehicle...
	@Test
	public void testEnterQueuedState() throws VehicleException,
			SimulationException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		MotorCycle mot1 = new MotorCycle("b1", 3);
		mot1.enterQueuedState();
		assertTrue(mot1.isQueued() == true);

		// Test to see if EnterQueuedState throws an
		// exception when
		// trying to add a vehicle to a queue after it has already been added.
	}

	@Test(expected = Exception.class)
	public void testEnterQueuedStateQueuedException() throws VehicleException,
			SimulationException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		mot.enterQueuedState();
		mot.enterQueuedState();
	}

	// Test to see if EnterQueuedState throws an exception
	// when
	// trying to add a vehicle to a queue after it has been parked.

	@Test(expected = Exception.class)
	public void testEnterQueuedStateParkedException() throws VehicleException,
			SimulationException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		MotorCycle mot1 = new MotorCycle("b1", 3);
		mot1.enterParkedState(2, 25);
		mot1.enterQueuedState();
	}

	// -------------------------------------------------------------------------------------------------------------------
	// Test that ExitQueuedState removes a queued vehicle from the queue, by
	// setting
	// Isqueued to false.
	@Test
	public void testExitQueuedState() throws VehicleException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		mot.enterQueuedState();
		mot.exitQueuedState(4);
		assertTrue(mot.isQueued() == false);
	}

	// Test that ExitQueuedState throws an exception if the vehicle is currently
	// parked

	@Test(expected = Exception.class)
	public void testExitQueuedStateParkedException() throws VehicleException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		mot.enterParkedState(25, 25);
		mot.exitQueuedState(4);
	}

	// Test that ExitQueuedState throws an exception if the vehicle is not
	// currently
	// queued

	@Test(expected = Exception.class)
	public void testExitQueuedStateQueuedException() throws VehicleException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		mot.exitQueuedState(4);
	}

	// Test that ExitQueuedState throws an exception if the arrival time of the
	// vehicle is equal to
	// or less than it's arrival time.

	@Test(expected = Exception.class)
	public void testExitQueuedStateTimeException() throws VehicleException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b", 3);
		mot.enterQueuedState();
		mot.exitQueuedState(3);
	}

	// -------------------------------------------------------------------------------------------------------------------

	// Test to see that EnterParkedState parks the vehicle by setting isParked
	// to true.
	@Test
	public void testEnterParkedState() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, 25);
		assertTrue(mot.isParked() == true);
	}

	// Test to see that EnterParkedState throws an exception if the vehicle is
	// already parked.
	@Test(expected = Exception.class)
	public void testEnterParkedStateParkedException() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, 25);
		mot.enterParkedState(2, 25);
	}

	// Test to see that EnterParkedState throws an exception if the vehicle is
	// already queued.
	@Test(expected = Exception.class)
	public void testEnterParkedStateQueuedException() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterQueuedState();
		mot.enterParkedState(2, 25);
	}

	// Test to see that EnterParkedState throws an exception if the parking time
	// entered is 0 or less.
	@Test(expected = Exception.class)
	public void testEnterParkedStateTimeException() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(0, 25);
	}

	// Test to see that EnterParkedState sets the parkingTime.
	@Test
	public void testEnterParkedStateParkingTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.getParkingTime() == 1);
	}

	// Test to see that EnterParkedState sets the departureTime.
	@Test
	public void testEnterParkedStateDepartureTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.getParkingTime() == 1);
	}

	// Test to see that EnterParkedState sets isParked to true.
	@Test
	public void testEnterParkedStateIsParked() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.isParked() == true);
	}

	// Test to see that EnterParkedState sets wasParked to true.
	@Test
	public void testEnterParkedStateWasParked() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.wasParked() == true);
	}

	// Test to see that EnterParkedState sets isSatisfied to true.
	@Test
	public void testEnterParkedStateIsSatisfied() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.isSatisfied() == true);

		// -------------------------------------------------------------------------------------------------------------------

	}

	// Test that ExitParkedState exits the vehicle by setting isParked to false.
	@Test
	public void testExitParkedStateIsParked() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		mot.exitParkedState(2);
		assertTrue(mot.isParked() == false);
	}

	// Test that ExitParkedState exits the vehicle by setting the departure time
	// correctly.
	@Test
	public void testExitParkedStateDepartureTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		mot.exitParkedState(2);
		assertTrue(mot.getDepartureTime() == 2);
	}

	// Test that ExitParkedState throws an exception if the vehicle is not
	// parked
	@Test(expected = Exception.class)
	public void testExitParkedStateNotParked() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.exitParkedState(2);
	}

	// Test that ExitParkedState throws an exception if the vehicle is queued.
	// This exception cannot be thrown, as the vehicle cannot be parked and
	// queued. - Or can it?
	// If the vehicle is not parked, the the methods first exception is thrown.
	// -
	// So we need to park the vehicle first to bypass the first exception, but
	// then it cannot
	// be queued (as it is already parked), - so we cannot get to the point to
	// throw the second exception.
	@Test(expected = Exception.class)
	public void testExitParkedStateIsQueuedException() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		mot.enterQueuedState(); // how to do this?
		mot.exitParkedState(2); // how to do this?
	}

	// Test that ExitParkedState throws an exception if the departure time is
	// less than the parking time.
	@Test(expected = Exception.class)
	public void testExitParkedStateDepartureTimeException()
			throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		mot.exitParkedState(0);
	}

	// -------------------------------------------------------------------------------------------------------------------

	// Test that IsParked returns true after a vehicle has been parked.
	@Test
	public void testIsParkedTrue() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.isParked() == true);
	}

	// Test that IsParked returns false when a vehicle has not been parked.
	@Test
	public void testIsParkedFalse() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		assertTrue(mot.isParked() == false);
	}

	// Test that IsParked returns false when a vehicle has exited parked state.
	// *** No need to do as the enterState and exitState test for these..?????

	@Test
	public void testIsParkedFalseAfterParkedAndExited() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		mot.exitParkedState(2);
		assertTrue(mot.isParked() == false);
	}

	// -------------------------------------------------------------------------------------------------------------------

	// Test that IsQueued returns true after a vehicle has been queued.
	@Test
	public void testIsQueuedTrue() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterQueuedState();
		assertTrue(mot.isQueued() == true);
	}

	// Test that IsQueued returns false after a vehicle has not been queued.
	@Test
	public void testIsQueuedFalse() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		assertTrue(mot.isQueued() == false);
	}

	// Test that IsQueued returns false after a vehicle has queued and exited.
	// *** No need to do as the enterQueue and exitQueue test for these..?????
	@Test
	public void testIsQueuedFalseAfterQueuedAndExited() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		assertTrue(mot.isQueued() == false);
	}

	// -------------------------------------------------------------------------------------------------------------------

	// Test that GetParkingTime returns the correct parking time.
	@Test
	public void testGetParkingTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, 25);
		assertTrue(mot.getParkingTime() == 2);
	}

	// -------------------------------------------------------------------------------------------------------------------

	// Test that getDepartureTime returns the correct departure time.
	// Can use enterParkedState or exitParkedState to test this..
	// Does it really matter which one we use? does it make a difference?
	// Do more test for this one?
	@Test
	public void testGetDepartureTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, Constants.MINIMUM_STAY);
		assertTrue(mot.getDepartureTime() == 22);
	}

	// -------------------------------------------------------------------------------------------------------------------
	// Test that WasQueued is set to true after the vehicle exits the queue.
	// Can use enterParkedState or exitParkedState to test this..
	// Does it really matter which one we use? does it make a difference?
	// Do more test for this one?
	@Test
	public void testWasQueuedTrue() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterQueuedState();
		assertTrue(mot.wasQueued() == true);
	}

	// Test that WasQueued is not set to true when a vehicle is not queued.
	// Can use enterQueuedState or exitQueuedState to test this..
	// Does it really matter which one we use? does it make a difference?
	// Do more test for this one?
	@Test
	public void testWasQueuedFalse() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterQueuedState();
		assertTrue(mot.wasQueued() == true);
	}
	// -------------------------------------------------------------------------------------------------------------------
	// Test that WasParked is set to true when a vehicle is parked.
	// Can use enterParkedState or exitParkedState to test this..
	// Does it really matter which one we use? does it make a difference?
	// Do more test for this one?
	@Test
	public void testWasParked() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, Constants.MINIMUM_STAY);
		assertTrue(mot.wasParked() == true);
	}
	// -------------------------------------------------------------------------------------------------------------------

	// Test to see if IsSatisfied is set to true after a car is parked.
	// Do more test for this one?
	@Test
	public void testIsSatisfied() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(2, Constants.MINIMUM_STAY);
		assertTrue(mot.isSatisfied() == true);
	}
	// -------------------------------------------------------------------------------------------------------------------

	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
