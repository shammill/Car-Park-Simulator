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

	// All tests done at boundaries - I am pretty sure they are so far.
	/**
	 * @throws java.lang.Exception
	 */

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

	/**
	 * Test method for
	 * {@link asgn2Vehicles.MotorCycle#MotorCycle(java.lang.String, int)}.
	 * 
	 * @throws VehicleException
	 */
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
	/**
	 * Test method for
	 * {@link asgn2Vehicles.Vehicle#Vehicle(java.lang.String, int)}.
	 * 
	 * @throws VehicleException
	 */

	// Tested using testGetVehId and GetVehArrivalTime. Do we need to test this
	// again?
	@Test
	public void testVehicle() throws VehicleException {

	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getVehID()}.
	 * 
	 * @throws VehicleException
	 */
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

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getArrivalTime()}.
	 * 
	 * @throws VehicleException
	 */
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

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterQueuedState()}.
	 * 
	 * @throws VehicleException
	 * @throws SimulationException
	 */
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

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitQueuedState(int)}.
	 * 
	 * @throws VehicleException
	 */
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

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterParkedState(int, int)}.
	 * 
	 * @throws VehicleException
	 */
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
	@Test(expected = Exception.class)
	public void testEnterParkedStateParkingTime() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.getParkingTime() == 1);
	}

	// Test to see that EnterParkedState sets the departureTime.
	@Test(expected = Exception.class)
	public void testEnterParkedStateDepartureTime() throws VehicleException {
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.getParkingTime() == Constants.MINIMUM_STAY + 1);
	}

	// Test to see that EnterParkedState sets isParked.
	@Test(expected = Exception.class)
	public void testEnterParkedStateIsParked() throws VehicleException {
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.isParked() == true);
	}

	// Test to see that EnterParkedState sets wasParked.
	@Test(expected = Exception.class)
	public void testEnterParkedStateWasParked() throws VehicleException {
		mot.enterParkedState(1, Constants.MINIMUM_STAY);
		assertTrue(mot.wasParked() == true);
	}

	// Test to see that EnterParkedState sets isSatisfied.
	@Test(expected = Exception.class)
	public void testEnterParkedStateIsSatisfied() throws VehicleException {
		mot = new MotorCycle("b1", 3);
		mot.enterParkedState(1, Constants.MINIMUM_STAY - 1);
		assertTrue(mot.isSatisfied() == true);

	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitParkedState(int)}.
	 */
	@Test
	public void testExitParkedStateInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#exitParkedState()}.
	 */
	@Test
	public void testExitParkedState() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 */
	@Test
	public void testIsParked() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 */
	@Test
	public void testIsQueued() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 */
	@Test
	public void testGetParkingTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getDepartureTime()}.
	 */
	@Test
	public void testGetDepartureTime() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasQueued()}.
	 */
	@Test
	public void testWasQueued() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#wasParked()}.
	 */
	@Test
	public void testWasParked() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isSatisfied()}.
	 */
	@Test
	public void testIsSatisfied() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
