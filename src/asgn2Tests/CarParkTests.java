/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;
import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Log;
import asgn2Simulators.SimulationRunner;
import asgn2Simulators.Simulator;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;
import asgn2Vehicles.Car;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CarParkTests {

	CarPark CarP;
	MotorCycle mot;
	Car car;
	Car car1;
	Car sCar;
	Car sCar2;
    Simulator sim;
    Log log;
    SimulationRunner sr;
    
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// Test that ArchiveDepartingVehicles archives the vehicles that have
	// left after they have parked, without forcing them. Does not pass,
	// problems with finding the vehicles in iteration. it can find the
	// vehicle, but gets lost on the second. Come back to the rest of
	// these tests later.
/*
	@Test
	public void testArchiveDepartingVehicles() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(15, 5, 2, 5);
		CarP.parkVehicle(mot, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 1, Constants.MINIMUM_STAY);
		CarP.archiveDepartingVehicles(22, false);
		assertTrue(CarP.carParkEmpty() == true);

	}

	// not sure on this one, no getters for past and numdissatisfied

	@Test
	public void testArchiveNewVehicle() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(2, 1, 1, 2);
		sim = new Simulator (1,1,1,1,0,0);
		sr = new SimulationRunner(CarP, sim, log);
		
	}

	// not sure on this one, no getters for past and numdissatisfied

	@Test
	public void testArchiveQueueFailures() {
		fail("Not yet implemented"); // TODO
	}

	// Test to see if Carpark returns false. Create a carpark, leave it
	// empty, then see if CarParkEmpty returns false.

	@Test
	public void testCarParkEmpty() throws VehicleException, SimulationException {
		CarP = new CarPark(15, 5, 2, 5);
		mot = new MotorCycle("b1", 3);
	}

	// Test Carpark full to see if it returns true after being filled.

	@Test
	public void testCarParkFullTrue() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.carParkFull() == true);
	}

	// Test Carpark full to see if it returns false when just below it's 
	// capacity. Below if I try to add a small car instead of a large car,
	// then it will not add it... when there is only 1 motorcycle in there
	// and nothing else. is there something wrong with this?

	@Test
	public void testCarParkFullFalse() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		assertTrue(CarP.carParkFull() == false);
	}

	// Test to see that EnterQueue enters the vehicle into the queue by
	// setting isQueued to true. How to test that it removes v from the
	// queue? Cannot access the queue..

	@Test
	public void testEnterQueue() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new

		MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		assertTrue(mot.isQueued() == true);

	}

	// Test to see that ExitQueue exits the vehicle from the queue by setting
	// isQueued to false. How to test that it removes v from the queue?
	// Cannot access the queue..

	@Test
	public void testExitQueue() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.exitQueue(mot, 4);
		assertTrue(mot.isQueued() == false);
	}

	// Need to test the string output here, so should I write an entire
	// string output and test to see that they match?

	@Test
	public void testFinalState() {

	}

	// Test here that GetNumCars returns the correct number of cars in the //
	// CarPark after cars are parked.

	@Test
	public void testGetNumCars() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumCars() == 2);
	}

	// Test here that GetNumMotorCycles returns the correct number of cars in
	// the CarPark after cars are parked.

	@Test
	public void testGetNumCarsMotCycl() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumMotorCycles() == 1);
	}

	// Test here that GetNumSmallCars returns the correct number of cars in
	// the CarPark after cars are parked.

	@Test
	public void testGetNumSmallCars() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumSmallCars() == 1);
	}

	// Test getStatus by checking that the string it produces is correct.

	@Test
	public void testGetStatus() throws VehicleException, SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(2, 1, 1, 2);
		CarP.parkVehicle(car, 1, 20);
		CarP.parkVehicle(sCar, 2, 20);
		CarP.parkVehicle(mot, 3, 20);
		String s = CarP.getStatus(3);
		System.out.print(CarP.initialState());
		"3::0::P:3::C:2::S:1::M:1::D:0::A:0::Q:0".compareTo(s);

	}
	*/

	// Test InitialState by checking that the string it produces is correct.
	@Test
	public void testInitialState() throws VehicleException, SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(2, 1, 1, 2);
		CarP.parkVehicle(car, 1, 20);
		CarP.parkVehicle(sCar, 2, 20);
		CarP.parkVehicle(mot, 3, 20);
		String h = CarP.initialState();
		assertTrue("CarPark [maxCarSpaces: 2 maxSmallCarSpaces: 1 maxMotorCycleSpaces: 1 maxQueueSize: 2]".equals(h));
	}
/*

	@Test
	public void testNumVehiclesInQueue() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		car = new Car("c1", 3, false);
		CarP.enterQueue(car);
		sCar = new Car("sc1", 3, true);
		CarP.enterQueue(sCar);
		assertTrue(CarP.numVehiclesInQueue() == 3);
	}

	// Test that ParkVehicle parks a car and small car by seeing that it 
	// increments the getNumCars method.

	@Test
	public void testParkVehicleCars() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumCars() == 2);
	}

	// Test that ParkVehicle parks a small car by seeing that it increments
	// the // getNumSmallCars method.

	@Test
	public void testParkVehicleSmallCars() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumSmallCars() == 1);
	}

	// Test that ParkVehicle parks a motorCycle by seeing that it increments
	// the // getNumMotorCycle method. testParkVehicleSmallCars

	@Test
	public void testParkVehicleMotorCycle() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.getNumMotorCycles() == 1);
	}

	// ----------------------------------------------------------------------
	// ------------------------------------------------

	// Test proccessQueue to see that it removes a vehicle from the queue
	// after // it gets added // to the park after being in the queue.

	@Test
	public void testProcessQueueQueue() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		CarP.enterQueue(mot);
		CarP.processQueue(4, sim);
		assertTrue(CarP.queueEmpty() == true);

	}

	// Test proccessQueue to see that it adds a vehicle to the park after it
	// has been in the queue.

	@Test
	public void testProcessQueuePark() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		CarP.enterQueue(mot);
		CarP.processQueue(4, sim);
		assertTrue(CarP.getNumMotorCycles() == 1);

	}

	// Does QueueEmpty return true if the queue is empty?

	@Test
	public void testQueueEmptyTrue() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.exitQueue(mot, 4);
		assertTrue(CarP.queueEmpty() == true);
	}

	@Test
	public void testQueueFull() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		assertTrue(CarP.queueFull() == true);
	}

	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableCar() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		car = new Car("c1", 3, false);
		assertTrue(CarP.spacesAvailable(car) == true);
	}

	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableSmallCar() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		sCar = new Car("sc1", 3, true);
		assertTrue(CarP.spacesAvailable(sCar) == true);
	}

	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableMotorCycle() throws VehicleException,
			SimulationException {
		CarP = new CarPark(1, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		assertTrue(CarP.spacesAvailable(mot) == true);
	}

	// not sure on this one yet.

	@Test
	public void testToString() {

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are available in the
	// park by adding it to the park.
	// Not sure how else to test this one besides checking that carpark is not
	// empty,
	@Test
	public void testTryProcessNewVehiclesParkCar() throws SimulationException,
			VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.carParkEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park by adding it to the queue. How else to test besides seeing if
	// parkqueue is empty..? only status?
	@Test
	public void testTryProcessNewVehiclesQueueCar() throws SimulationException,
			VehicleException {
		CarP = new CarPark(2, 1, 1, 4);
		car = new Car("c1", 1, false);
		car1 = new Car("c2", 1, false);
		sCar = new Car("c3", 1, true);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(mot, 2, Constants.MINIMUM_STAY);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.queueEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park or queue, by adding it to the archive of dissatisfied customers.

	@Test
	public void testTryProcessNewVehiclesArchive() {

	}

	// Test to see that TryProcessNewVehicles processes a new small car when
	// spaces
	// are available in the
	// park by adding it to the park.
	@Test
	public void testTryProcessNewVehiclesParkSmallCar()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.carParkEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park by adding it to the queue.
	@Test
	public void testTryProcessNewVehiclesQueueSmallCar()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		car = new Car("c1", 1, false);
		car1 = new Car("c2", 1, false);
		sCar = new Car("c3", 1, true);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(mot, 2, Constants.MINIMUM_STAY);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.queueEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park or queue, by adding it to the archive of dissatisfied customers.
	@Test
	public void testTryProcessNewVehiclesSmallCarArchive() {

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are available in the
	// park by adding it to the park.
	@Test
	public void testTryProcessNewVehiclesParkMotorCycle()
			throws SimulationException, VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.carParkEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park by adding it to the queue.
	@Test
	public void testTryProcessNewVehiclesQueueMotorCycle()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		car = new Car("c1", 1, false);
		car1 = new Car("c2", 1, false);
		sCar = new Car("c3", 1, true);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 2, Constants.MINIMUM_STAY);
		CarP.parkVehicle(mot, 2, Constants.MINIMUM_STAY);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.queueEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the
	// park or queue, by adding it to the archive of dissatisfied customers.
	@Test
	public void testTryProcessNewVehiclesArchiveMotorCycle() {

	}

	@Test
	public void testUnparkVehicle() {
		fail("Not yet implemented");
	}*/

}
