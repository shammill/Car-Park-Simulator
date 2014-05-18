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
import asgn2Simulators.Simulator;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Car;

import org.junit.Test;

public class CarParkTests {

	private MotorCycle mot1;
	private Car car1;
	private String DEFAULT_MOTORCYCLE_ID = "MC1";
	private String DEFAULT_CAR_ID = "C1";
	private boolean SMALL_CAR = true;
	private boolean NOT_SMALL_CAR = false;
	private boolean FORCE_LEAVE = true;
	private boolean NO_FORCE_LEAVE = false;
	private int DEFAULT_ARRIVAL_TIME = 1;
	private int DEFAULT_TIME = 1;
	private int DEFAULT_STAY_DURATION = Constants.MINIMUM_STAY;
	private int DEFAULT_DEPARTURE_TIME = (DEFAULT_ARRIVAL_TIME + DEFAULT_STAY_DURATION);
	private int LOW_MAX_CAR_SPACES = 2;
	private int LOW_MAX_SMALL_CAR_SPACES = 1;
	private int LOW_MAX_MOTOR_CYCLE_SPACES = 1;
	private int LOW_MAX_QUEUE_SIZE = 1;
	

	
	// Test that ArchiveDepartingVehicles archives the vehicles that have
	// left after they have parked, without forcing them.
	// passes
	@Test
	public void testArchiveDepartingVehiclesNoForce() throws VehicleException, SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		CarPark carPark = new CarPark();
		
		carPark.parkVehicle(motorCycle, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveDepartingVehicles(DEFAULT_DEPARTURE_TIME, NO_FORCE_LEAVE);
		
		String status = carPark.getStatus(DEFAULT_TIME);
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:3::Q:0|M:P>A||C:P>A||S:P>A|\n", status);
	}

	// Test that ArchiveDepartingVehicles archives the vehicles that have
	// left after they have parked, by forcing them. As this test uses the
	// getstatus
	// method, it also checks that ArchiveDepartingVehicles sets the string for
	// the output as well.
	// passes
	@Test
	public void testArchiveDepartingVehiclesForce() throws VehicleException, SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		CarPark carPark = new CarPark();
		
		carPark.parkVehicle(motorCycle, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveDepartingVehicles(DEFAULT_TIME, FORCE_LEAVE);
		
		String status = carPark.getStatus(DEFAULT_TIME);
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:3::Q:0|M:P>A||C:P>A||S:P>A|\n", status);

	}

	// Test that ArchiveDepartingVehicles unparks the car.
	// passes
	@Test
	public void testArchiveDepartingVehiclesUnParked() throws VehicleException,	SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		CarPark carPark = new CarPark();
		
		carPark.parkVehicle(motorCycle, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveDepartingVehicles(DEFAULT_TIME, FORCE_LEAVE);
		
		assertFalse(motorCycle.isParked());
		assertFalse(car.isParked());
		assertFalse(smallCar.isParked());
	}



	// Check to see that ArchiveNewVehicle archives new vehicles
	// passes
	@Test
	public void testArchiveNewVehicleAddsDissatisfied()	throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.archiveNewVehicle(motorCycle);
		carPark.archiveNewVehicle(car);
		carPark.archiveNewVehicle(smallCar);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:3::A:3::Q:0\n", status);
	}

	
	// Check to see that ArchiveNewVehicle throws a SimulationException if the
	// queue contains
	// the vehicle that is trying to get queued.
	// passes

	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicleQueued() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveNewVehicle(motorCycle);
	}

	
	// Check to see that ArchiveNewVehicle throws a SimulationException if the
	// queue contains the vehicle that is trying to get queued.
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicleParked() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveNewVehicle(motorCycle);
	}


	// Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	// archive when it has
	// been in the queue for too long.
	// passes
	@Test
	public void testArchiveQueueFailuresArchive() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(29);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:1::A:1::Q:0|M:Q>A|\n", status);
	}

	// Test ArchiveQueueFailures to see if it remove the vehicle from the queue
	// by setting
	// Vehicle.IsQueued to false once it has
	// been in the queue for too long.
	// passes

	@Test
	public void testArchiveQueueFailuresIsQueued() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(29);
		assertFalse(motorCycle.isQueued());
	}

	// Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	// archive when it has not
	// been in the queue for too long.
	// passes
	@Test
	public void testArchiveQueueFailuresArchiveFalse() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(25);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:1M\n", status);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// TEST CARPARKEMPTY NEEDS TO BE DONE HERE
	
	//---------------------------------------------------------------------------------------------------------------------

	// Test CarparkFull to see if it returns false when it is one short
	// of it's capacity, having a vehicle of each added to it.
	// passes
	@Test
	public void testCarParkFullFalse() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(3, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.carParkFull() == false);
	}

	// Test CarParkFull full to see if it returns true after being filled.
	// passes
	@Test
	public void testCarParkFullTrue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.carParkFull() == true);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test to see that EnterQueue enters the vehicle into the queue by
	// setting isQueued to true. How to test that it removes v from the
	// queue? Cannot access the queue..

	@Test
	public void testEnterQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.enterQueue(motorCycle);
		assertTrue(motorCycle.isQueued() == true);

	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test to see that ExitQueue exits the vehicle from the queue by setting
	// isQueued to false. How to test that it removes v from the queue? //
	// Cannot access the queue..
	// passes
	@Test
	public void testExitQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.exitQueue(motorCycle, 4);
		assertTrue(motorCycle.isQueued() == false);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Testing this one is a bit tricky as we need to test string output I think
	// and what people write may differ, so we could assume that given this test
	// is written
	// first, the string output could be written to match it.
	// HAS NOT PASSED - Sam maybe you want to do / have go at this?

	@Test
	public void testFinalState() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car("c1", 3, false);
		Car smallCar = new Car("sc1", 3, true);
		carPark.archiveNewVehicle(motorCycle);
		carPark.archiveNewVehicle(car);
		carPark.archiveNewVehicle(smallCar);
		String t = (carPark.finalState());
		System.out.println(t);
		// System.out.println(carPark.finalState());
		// assertTrue("")
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test here that GetNumCars returns the correct number of cars in the //
	// CarPark after cars are parked.

	@Test
	public void testGetNumCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumCars() == 2);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test here that GetNumMotorCycles returns the correct number of cars in
	// the CarPark after cars are parked.

	@Test
	public void testGetNumMotCycl() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumMotorCycles() == 1);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test here that GetNumSmallCars returns the correct number of cars in
	// the CarPark after cars are parked.

	@Test
	public void testGetNumSmallCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumSmallCars() == 1);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test getStatus by checking that the string it produces is correct. //
	// how this is returning false i don't know.

	@Test
	public void testGetStatus() throws VehicleException, SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car("c1", 3, false);
		Car smallCar = new Car("sc1", 3, true);
		CarPark carPark = new CarPark(2, 1, 1, 2);
		carPark.parkVehicle(car, 1, 20);
		carPark.parkVehicle(smallCar, 2, 20);
		carPark.parkVehicle(motorCycle, 3, 20);
		String status = carPark.getStatus(3);

		// System.out.print(carPark.initialState());
		System.out.print(carPark.getStatus(3));

		assertEquals("3::0::P:3::C:2::S:1::M:1::D:0::A:0::Q:0\n", status);

	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test InitialState by checking that the string it produces is correct.

	@Test
	public void testInitialState() throws VehicleException, SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car("c1", 3, false);
		Car smallCar = new Car("sc1", 3, true);
		CarPark carPark = new CarPark(2, 1, 1, 2);
		carPark.parkVehicle(car, 1, 20);
		carPark.parkVehicle(smallCar, 2, 20);
		carPark.parkVehicle(motorCycle, 3, 20);
		String h = carPark.initialState();
		assertTrue("CarPark [maxCarSpaces: 2 maxSmallCarSpaces: 1 maxMotorCycleSpaces: 1 maxQueueSize: 2]"
				.equals(h));
	}
	//---------------------------------------------------------------------------------------------------------------------


	@Test
	public void testNumVehiclesInQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(2, 1, 1, 3);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		Car car = new Car("c1", 3, false);
		carPark.enterQueue(car);
		Car smallCar = new Car("sc1", 3, true);
		carPark.enterQueue(smallCar);
		assertTrue(carPark.numVehiclesInQueue() == 3);
	}
	//---------------------------------------------------------------------------------------------------------------------

	// Test that ParkVehicle parks a car and small car by seeing that it //
	// increments the getNumCars method.

	@Test
	public void testParkVehicleCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumCars() == 2);
	}

	// Test that ParkVehicle parks a small car by seeing that it increments
	// the // getNumSmallCars method.

	@Test
	public void testParkVehicleSmallCars() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumSmallCars() == 1);
	}

	// Test that ParkVehicle parks a motorCycle by seeing that it increments
	// the getNumMotorCycle method. testParkVehicleSmallCars

	@Test
	public void testParkVehicleMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 1, 20);
		Car car = new Car("c1", 3, false);
		carPark.parkVehicle(car, 1, 20);
		Car smallCar = new Car("sc1", 3, true);
		carPark.parkVehicle(smallCar, 1, 20);
		assertTrue(carPark.getNumMotorCycles() == 1);
	}

	//---------------------------------------------------------------------------------------------------------------------


	// Test proccessQueue to see that it removes a vehicle from the queue //
	// after // it gets added // to the park after being in the queue.

	@Test
	public void testProcessQueueQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		carPark.enterQueue(motorCycle);
		carPark.processQueue(4, sim);
		assertTrue(carPark.queueEmpty() == true);

	}

	// Test proccessQueue to see that it adds a vehicle to the park after it
	// has been in the queue.

	@Test
	public void testProcessQueuePark() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		carPark.enterQueue(motorCycle);
		carPark.processQueue(4, sim);
		assertTrue(carPark.getNumMotorCycles() == 1);

	}
	//---------------------------------------------------------------------------------------------------------------------

	// Does QueueEmpty return true if the queue is empty?

	@Test
	public void testQueueEmptyTrue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.exitQueue(motorCycle, 4);
		assertTrue(carPark.queueEmpty() == true);
	}
	
//---------------------------------------------------------------------------------------------------------------------
   
	// Test Queuefull to see if it returns full when the queue is full.
	@Test
	public void testQueueFull() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		assertTrue(carPark.queueFull() == true);
	}
//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------

		// TEST CARPARKEMPTY NEEDS TO BE DONE HERE
		
		//---------------------------------------------------------------------------------------------------------------------

	
	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car car = new Car("c1", 3, false);
		assertTrue(carPark.spacesAvailable(car) == true);
	}

	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableSmallCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car smallCar = new Car("sc1", 3, true);
		assertTrue(carPark.spacesAvailable(smallCar) == true);
	}

	// Test to see that there is a space available for a car.

	@Test
	public void testSpacesAvailableMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(1, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 1);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		assertTrue(carPark.spacesAvailable(motorCycle) == true);
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
	public void testTryProcessNewVehiclesParkCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		carPark.tryProcessNewVehicles(4, sim);
		assertTrue(carPark.carParkEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the park by adding it to the queue. How else
	// to test besides seeing if parkqueue is empty..? only status?

	@Test
	public void testTryProcessNewVehiclesQueueCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		car1 = new Car("c2", 1, false);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(smallCar, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(motorCycle, 2, Constants.MINIMUM_STAY);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		carPark.tryProcessNewVehicles(4, sim);
		assertTrue(carPark.queueEmpty() == false);
	}

	// Test to see that TryProcessNewVehicles processes a new car when spaces
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.

	@Test
	public void testTryProcessNewVehiclesArchiveCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 1, 0, 0);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		car1 = new Car("c2", 1, false);
		carPark.parkVehicle(car, 1, 20);
		carPark.enterQueue(car1);
		carPark.tryProcessNewVehicles(4, sim);
		String t = carPark.getStatus(3);
		assertTrue("3::1::P:1::C:1::S:0::M:0::D:1::A:1::Q:1C|C:N>A|\n".equals(t));
	}

	// Test to see that TryProcessNewVehicles processes a new small car when
	// spaces are available in the park by adding it to the park.

	@Test
	public void testTryProcessNewVehiclesParkSmallCar()	throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		car1 = new Car("c2", 1, false);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(smallCar, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(motorCycle, 2, Constants.MINIMUM_STAY);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		carPark.tryProcessNewVehicles(4, sim);
		assertTrue(carPark.queueEmpty() == false);
	}
	
	// Test to see that TryProcessNewVehicles processes a new small car when spaces
	// are not available in the park by adding it to the queue.
	@Test
	public void testTryProcessNewVehiclesQueueSmallCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(smallCar, 1, Constants.MINIMUM_STAY);
		carPark.parkVehicle(car, 1, Constants.MINIMUM_STAY);
		carPark.tryProcessNewVehicles(4, sim);
		assertTrue(carPark.queueEmpty() == false);

	}

	// Test to see that TryProcessNewVehicles processes a new small car when spaces
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.

	@Test
	public void testTryProcessNewVehiclesSmallCarArchive() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 1, 1, 0);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		car1 = new Car("c2", 1, false);
		carPark.parkVehicle(car, 1, 20);
		carPark.enterQueue(car1);
		carPark.tryProcessNewVehicles(4, sim);
		String t = carPark.getStatus(3);
		assertTrue("3::1::P:2::C:2::S:1::M:0::D:0::A:0::Q:1C|S:N>P|\n".equals(t));
	}
	
	// Test to see that TryProcessNewVehicles processes a new MotorCycle when
	// spaces are available in the park by adding it to the park.

	@Test
	public void testTryProcessNewVehiclesParkMotorCycle() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		carPark.tryProcessNewVehicles(4, sim);
		assertFalse(carPark.carParkEmpty());
	}

	// Test to see that TryProcessNewVehicles processes a new Motorcycle when spaces
	// are not available in the park by adding it to the queue.

	@Test
	public void testTryProcessNewVehiclesQueueMotorCycle() throws SimulationException, VehicleException {
        CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		mot1 = new MotorCycle("b2", 3);
		carPark.parkVehicle(motorCycle, 1, Constants.MINIMUM_STAY);
		carPark.enterQueue(mot1);
		carPark.tryProcessNewVehicles(4, sim);
		assertFalse(carPark.queueEmpty());

	}
	// Test to see that TryProcessNewVehicles processes a new motorcycle when spaces
	// are not available in the park or queue, by adding it to the archive
	// of dissatisfied customers.
	
	@Test
	public void testTryProcessNewVehiclesArchiveMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		mot1 = new MotorCycle("b2", 3);
		carPark.parkVehicle(motorCycle, 2, Constants.MINIMUM_STAY);
		carPark.enterQueue(mot1);
		Simulator sim = new Simulator(1, 1, 1, 0, 0, 1);
		carPark.tryProcessNewVehicles(4, sim);
		String m = carPark.getStatus(3);
		assertTrue("3::1::P:2::C:0::S:0::M:2::D:0::A:0::Q:1M|M:N>P|\n".equals(m));
	}

//----------------------------------------------------------------------------------------------------------------------
	
	// Test Unpark Vehicle to see if it unparks a vehicle when it isn't parked.
	@Test(expected = SimulationException.class)
	public void testUnparkVehicle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.unparkVehicle(motorCycle, 5);
	}

	// Test Unpark Vehicle to see if it unparks a Car when it is parked.
	@Test
	public void testUnparkVehicleCar() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(2, 1, 1, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(car, 3, 20);
		carPark.unparkVehicle(car, 29);
		assertFalse(car.isParked());
	}

	// Test Unpark Vehicle to see if it unparks a Small Car when it is parked.
	@Test
	public void testUnparkVehicleSmallCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.parkVehicle(smallCar, 3, 20);
		carPark.unparkVehicle(smallCar, 5);
		assertFalse(smallCar.isParked());

	}

	// Test UnparkVehicle to see if it unparks a MotorCycle when it is parked.
	@Test
	public void testUnparkVehicleMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, 3, 20);
		carPark.unparkVehicle(motorCycle, 5);
		assertFalse(motorCycle.isParked());

	}

	// Test UnparkVehicle to see if it removes the number vehicles once
	// they have been unparked.
	@Test
	public void testUnparkVehicleArchived() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.parkVehicle(motorCycle, 3, 20);
		carPark.parkVehicle(car, 3, 20);
		carPark.parkVehicle(smallCar, 3, 20);
		carPark.unparkVehicle(motorCycle, 5);
		carPark.unparkVehicle(car, 5);
		carPark.unparkVehicle(smallCar, 5);
		System.out.println(carPark.getStatus(DEFAULT_TIME));
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:0\n", status);
	}
}
