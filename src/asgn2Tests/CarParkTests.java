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
	MotorCycle mot1;
	MotorCycle mot2;
	Car car;
	Car car1;
	Car car2;
	Car sCar;
	Car sCar1;
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
	//---------------------------------------------------------------------------------------------------------------------

	// 2 CONSTRUCTORS STILL TO DO
	
	// ------------------------------------------------------------------------------------------------------------------------
	// Test that ArchiveDepartingVehicles archives the vehicles that have
	// left after they have parked, without forcing them.
	// passes
	@Test
	public void testArchiveDepartingVehiclesNoForce() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(15, 5, 2, 5);
		CarP.parkVehicle(mot, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 1, Constants.MINIMUM_STAY);
		CarP.archiveDepartingVehicles(22, false);
		String r = CarP.getStatus(3);
		System.out.println(r);
		assertTrue("3::0::P:0::C:0::S:0::M:0::D:0::A:3::Q:0|M:P>A||C:P>A||S:P>A|\n"
				.equals(r));

	}

	// Test that ArchiveDepartingVehicles archives the vehicles that have
	// left after they have parked, by forcing them. As this test uses the
	// getstatus
	// method, it also checks that ArchiveDepartingVehicles sets the string for
	// the output as well.
	// passes

	@Test
	public void testArchiveDepartingVehiclesForce() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(15, 5, 2, 5);
		CarP.parkVehicle(mot, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 1, Constants.MINIMUM_STAY);
		CarP.archiveDepartingVehicles(20, true);
		String r = CarP.getStatus(3);
		assertTrue("3::0::P:0::C:0::S:0::M:0::D:0::A:3::Q:0|M:P>A||C:P>A||S:P>A|\n"
				.equals(r));

	}

	// Test that ArchiveDepartingVehicles unparks the car.
	// passes

	@Test
	public void testArchiveDepartingVehiclesUnParked() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP = new CarPark(15, 5, 2, 5);
		CarP.parkVehicle(mot, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(sCar, 1, Constants.MINIMUM_STAY);
		CarP.archiveDepartingVehicles(20, true);
		assertTrue(mot.isParked() == false);
		assertTrue(car.isParked() == false);
		assertTrue(sCar.isParked() == false);

	}

	// Test that ArchiveDepartingVehicles throws a SimulationException when the
	// vehicle is not parked.
	// ***** this cannot be thrown as the vehicle needs to be parked to be in the
	// park.

	@Test(expected = Exception.class)
	public void testArchiveDepartingVehiclesSimExcep() throws VehicleException,
			SimulationException {
		mot = new MotorCycle("b1", 3);
		CarP = new CarPark(15, 5, 2, 5);
		CarP.enterQueue(mot);
		CarP.archiveDepartingVehicles(22, true);
	}

	// Test that ArchiveDepartingVehicles throws vehicle excep.. ?? how do i **
	// TO DO
	// test it's current state.?
	// ------------------------------------------------------------------------------------------------------------------------

	// Check to see that ArchiveNewVehicle archives new vehicles
	// passes

	@Test
	public void testArchiveNewVehicleAddsDissatisfied()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP.archiveNewVehicle(mot);
		CarP.archiveNewVehicle(mot1);
		CarP.archiveNewVehicle(mot2);
		String r = (CarP.getStatus(3));
		System.out.println("'" + r + "'");
		System.out.println(CarP.getStatus(3));
		assertTrue("3::0::P:0::C:0::S:0::M:0::D:3::A:3::Q:0\n".equals(r));
	}

	// Check to see that ArchiveNewVehicle throws a SimulationException if the
	// queue contains
	// the vehicle that is trying to get queued.
	// passes

	@Test(expected = Exception.class)
	public void testArchiveNewVehicleQueueExcep() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.archiveNewVehicle(mot);
	}

	

	// Check to see that ArchiveNewVehicle throws a SimulationException if the
	// queue contains
	// the vehicle that is trying to get queued.
	// passes

	@Test(expected = Exception.class)
	public void testArchiveNewVehicle() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		CarP.archiveNewVehicle(mot);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	// archive when it has
	// been in the queue for too long.
	// passes
	@Test
	public void testArchiveQueueFailuresArchive() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.archiveQueueFailures(29);
		String d = (CarP.getStatus(1));
		System.out.println(CarP.getStatus(1));
		System.out.println("'" + d + "'");
		assertTrue("1::0::P:0::C:0::S:0::M:0::D:1::A:1::Q:0|M:Q>A|\n".equals(d));
	}

	// Test ArchiveQueueFailures to see if it remove the vehicle from the queue
	// by setting
	// Vehicle.IsQueued to false once it has
	// been in the queue for too long.
	// passes

	@Test
	public void testArchiveQueueFailuresIsQueued() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.archiveQueueFailures(29);
		assertTrue(mot.isQueued() == false);
	}

	// Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	// archive when it has not
	// been in the queue for too long.
	// passes
	@Test
	public void testArchiveQueueFailuresArchiveFalse() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 3);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.archiveQueueFailures(27);
		String d = (CarP.getStatus(1));
		System.out.println(CarP.getStatus(1));
		assertTrue("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:1M\n".equals(d));
	}
	//---------------------------------------------------------------------------------------------------------------------

	// TEST CARPARKEMPTY NEEDS TO BE DONE HERE
	
	//---------------------------------------------------------------------------------------------------------------------

	// Test CarparkFull to see if it returns false when it is one short
	// of it's capacity, having a vehicle of each added to it.
	// passes
	@Test
	public void testCarParkFullFalse() throws VehicleException,
			SimulationException {
		CarP = new CarPark(3, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 1, 20);
		car = new Car("c1", 3, false);
		CarP.parkVehicle(car, 1, 20);
		sCar = new Car("sc1", 3, true);
		CarP.parkVehicle(sCar, 1, 20);
		assertTrue(CarP.carParkFull() == false);
	}

	// Test CarParkFull full to see if it returns true after being filled.
	// passes
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
	//---------------------------------------------------------------------------------------------------------------------

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
	//---------------------------------------------------------------------------------------------------------------------

	// Test to see that ExitQueue exits the vehicle from the queue by setting
	// isQueued to false. How to test that it removes v from the queue? //
	// Cannot access the queue..
	// passes
	@Test
	public void testExitQueue() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		CarP.exitQueue(mot, 4);
		assertTrue(mot.isQueued() == false);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Testing this one is a bit tricky as we need to test string output I think
	// and what people write may differ, so we could assume that given this test
	// is written
	// first, the string output could be written to match it.
	// HAS NOT PASSED - Sam maybe you want to do / have go at this?

	@Test
	public void testFinalState() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 3, false);
		sCar = new Car("sc1", 3, true);
		CarP.archiveNewVehicle(mot);
		CarP.archiveNewVehicle(car);
		CarP.archiveNewVehicle(sCar);
		String t = (CarP.finalState());
		System.out.println(t);
		// System.out.println(CarP.finalState());
		// assertTrue("")
	}
	//---------------------------------------------------------------------------------------------------------------------

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
	//---------------------------------------------------------------------------------------------------------------------

	// Test here that GetNumMotorCycles returns the correct number of cars in
	// the CarPark after cars are parked.

	@Test
	public void testGetNumMotCycl() throws VehicleException,
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
	//---------------------------------------------------------------------------------------------------------------------

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
	//---------------------------------------------------------------------------------------------------------------------

	// Test getStatus by checking that the string it produces is correct. //
	// how this is returning false i don't know.

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
		String t = "3::0::P:3::C:2::S:1::M:1::D:0::A:0::Q:0null";
		// System.out.print(CarP.initialState());
		System.out.print(CarP.getStatus(3));

		assertTrue("3::0::P:3::C:2::S:1::M:1::D:0::A:0::Q:0\n".equals(s));

	}
	//---------------------------------------------------------------------------------------------------------------------

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
		assertTrue("CarPark [maxCarSpaces: 2 maxSmallCarSpaces: 1 maxMotorCycleSpaces: 1 maxQueueSize: 2]"
				.equals(h));
	}
	//---------------------------------------------------------------------------------------------------------------------


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
	//---------------------------------------------------------------------------------------------------------------------

	// Test that ParkVehicle parks a car and small car by seeing that it //
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
	// the getNumMotorCycle method. testParkVehicleSmallCars

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

	//---------------------------------------------------------------------------------------------------------------------


	// Test proccessQueue to see that it removes a vehicle from the queue //
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
	//---------------------------------------------------------------------------------------------------------------------

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
	
//---------------------------------------------------------------------------------------------------------------------
   
	// Test Queuefull to see if it returns full when the queue is full.
	@Test
	public void testQueueFull() throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		CarP.enterQueue(mot);
		assertTrue(CarP.queueFull() == true);
	}
//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------

		// TEST CARPARKEMPTY NEEDS TO BE DONE HERE
		
		//---------------------------------------------------------------------------------------------------------------------

	
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
	//---------------------------------------------------------------------------------------------------------------------

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are available in the park by adding it to the park. Not sure how
	// else to test this one besides checking that carpark is not empty,
	@Test
	public void testTryProcessNewVehiclesParkCar() throws SimulationException,
			VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.carParkEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the park by adding it to the queue. How else
	// to test besides seeing if parkqueue is empty..? only status?

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
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.

	@Test
	public void testTryProcessNewVehiclesArchiveCar()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		car = new Car("c1", 1, false);
		car1 = new Car("c2", 1, false);
		CarP.parkVehicle(car, 1, 20);
		CarP.enterQueue(car1);
		CarP.tryProcessNewVehicles(4, sim);
		String t = CarP.getStatus(3);
		assertTrue("3::1::P:1::C:1::S:0::M:0::D:1::A:1::Q:1C|C:N>A|\n".equals(t));
	}

	// Test to see that TryProcessNewVehicles processes a new small car when
	// spaces are available in the park by adding it to the park.

	@Test
	public void testTryProcessNewVehiclesParkSmallCar()
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
	
	// Test to see that TryProcessNewVehicles processes a new small car when spaces
	// are not available in the park by adding it to the queue.
	@Test
	public void testTryProcessNewVehiclesQueueSmallCar() throws SimulationException,
			VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		sCar = new Car("c3", 1, true);
		car = new Car("c1", 1, false);
		CarP.parkVehicle(sCar, 1, Constants.MINIMUM_STAY);
		CarP.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.queueEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new small car when spaces
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.

	@Test
	public void testTryProcessNewVehiclesSmallCarArchive() throws SimulationException, VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		car = new Car("c1", 1, false);
		car1 = new Car("c2", 1, false);
		CarP.parkVehicle(car, 1, 20);
		CarP.enterQueue(car1);
		CarP.tryProcessNewVehicles(4, sim);
		String t = CarP.getStatus(3);
		assertTrue("3::1::P:2::C:2::S:1::M:0::D:0::A:0::Q:1C|S:N>P|\n".equals(t));
	}
	
	// Test to see that TryProcessNewVehicles processes a new MotorCycle when
	// spaces are available in the park by adding it to the park.

	@Test
	public void testTryProcessNewVehiclesParkMotorCycle()
			throws SimulationException, VehicleException {
		CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.carParkEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new Motorcycle when spaces
	// are not available in the park by adding it to the queue.

	@Test
	public void testTryProcessNewVehiclesQueueMotorCycle()
			throws SimulationException, VehicleException {
        CarP = new CarPark(2, 1, 1, 1);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		mot = new MotorCycle("b1", 3);
		mot1 = new MotorCycle("b2", 3);
		CarP.parkVehicle(mot, 1, Constants.MINIMUM_STAY);
		CarP.enterQueue(mot1);
		CarP.tryProcessNewVehicles(4, sim);
		assertTrue(CarP.queueEmpty() == false);

	}
	// Test to see that TryProcessNewVehicles processes a new motorcycle when spaces
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.
	
	@Test
	public void testTryProcessNewVehiclesArchiveMotorCycle()
			throws VehicleException, SimulationException {
		CarP = new CarPark(2, 1, 1, 1);
		mot = new MotorCycle("b1", 3);
		mot1 = new MotorCycle("b2", 3);
		CarP.parkVehicle(mot, 2, Constants.MINIMUM_STAY);
		CarP.enterQueue(mot1);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		CarP.tryProcessNewVehicles(4, sim);
		String m = CarP.getStatus(3);
		assertTrue("3::1::P:2::C:0::S:0::M:2::D:0::A:0::Q:1M|M:N>P|\n".equals(m));
	}

//----------------------------------------------------------------------------------------------------------------------
	
	// Test Unpark Vehicle to see if it unparks a vehicle when it isn't parked.
	@Test(expected = Exception.class)
	public void testUnparkVehicle() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		mot = new MotorCycle("b1", 3);
		CarP.unparkVehicle(mot, 5);
	}

	// Test Unpark Vehicle to see if it unparks a Car when it is parked.
	@Test
	public void testUnparkVehicleCar() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		car = new Car("c1", 1, false);
		CarP.parkVehicle(car, 3, 20);
		CarP.unparkVehicle(car, 29);
		assertTrue(car.isParked() == false);
	}

	// Test Unpark Vehicle to see if it unparks a Small Car when it is parked.

	@Test
	public void testUnparkVehicleSmallCar() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		sCar = new Car("c3", 1, true);
		CarP.parkVehicle(sCar, 3, 20);
		CarP.unparkVehicle(sCar, 5);
		assertTrue(sCar.isParked() == false);

	}

	// Test UnparkVehicle to see if it unparks a MotorCycle when it is parked.

	@Test
	public void testUnparkVehicleMotorCycle() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		mot = new MotorCycle("b1", 3);
		CarP.parkVehicle(mot, 3, 20);
		CarP.unparkVehicle(mot, 5);
		assertTrue(mot.isParked() == false);

	}

	// Test UnparkVehicle to see if it removes the number vehicles once
	// they have been unparked.

	@Test
	public void testUnparkVehicleArchived() throws VehicleException,
			SimulationException {
		CarP = new CarPark(2, 1, 1, 4);
		mot = new MotorCycle("b1", 3);
		car = new Car("c1", 1, false);
		sCar = new Car("c3", 1, true);
		CarP.parkVehicle(mot, 3, 20);
		CarP.parkVehicle(car, 3, 20);
		CarP.parkVehicle(sCar, 3, 20);
		CarP.unparkVehicle(mot, 5);
		CarP.unparkVehicle(car, 5);
		CarP.unparkVehicle(sCar, 5);
		System.out.println(CarP.getStatus(1));
		String s = (CarP.getStatus(1));
		assertTrue("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:0\n".equals(s));
	}
}
