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

import java.lang.reflect.Array;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Car;

import org.junit.Test;


/**
 * @author Samuel Hammill
 * @author Laurence Mccabe
 */
public class CarParkTests {

	// Defaults
	private String DEFAULT_MOTORCYCLE_ID = "MC1";
	private String DEFAULT_CAR_ID = "C1";
	private int DEFAULT_ARRIVAL_TIME = 1;
	private int DEFAULT_TIME = 1;
	private int DEFAULT_STAY_DURATION = Constants.MINIMUM_STAY;
	private int DEFAULT_DEPARTURE_TIME = (DEFAULT_ARRIVAL_TIME + DEFAULT_STAY_DURATION);
	private int DEFAULT_MAX_CAR_SPACES = 100;
	private int DEFAULT_MAX_SMALL_CAR_SPACES = 20;
	private int DEFAULT_MAX_MOTOR_CYCLE_SPACES = 10;
	private int DEFAULT_MAX_QUEUE_SIZE = 10;
	
	// Conditional
	private boolean SMALL_CAR = true;
	private boolean NOT_SMALL_CAR = false;
	private boolean FORCE_LEAVE = true;
	private boolean NO_FORCE_LEAVE = false;
	private int LOW_MAX_CAR_SPACES = 2;
	private int BOUNDRY_MAX_CAR_SPACES = 3;
	private int LOW_MAX_SMALL_CAR_SPACES = 1;
	private int LOW_MAX_MOTOR_CYCLE_SPACES = 1;
	private int LOW_MAX_QUEUE_SIZE = 1;
	private int MAX_QUEUE_SIZE_TWO = 2;
	private int MAX_QUEUE_SIZE_THREE = 3;
	private int HIGH_QUEUE_TIME = 1000;
	private int LEAVE_QUEUE_TIME = 10;
	private int LEAVE_QUEUE_BOUNDRY = 27;
	private int LEAVE_QUEUE_LOWER_BOUNDRY = 25;


	/**
	* Ensure our no parameter constructor works and CarPark is empty.
	* @author Samuel Hammill
	*/
	@Test
	public void testCarParkNoParameterConstructorParkEmpty() {
		CarPark carPark = new CarPark();
		assertTrue(carPark.carParkEmpty());
	}

	/**
	*  Ensure our no parameter constructor works and queue is empty.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkNoParameterConstructorQueueEmpty() {
		CarPark carPark = new CarPark();
		assertEquals(0, carPark.numVehiclesInQueue());
	}

	/**
	*  Ensure our no parameter constructor works and no vehicles exist.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkNoParameterConstructorNoVehicles() {
		CarPark carPark = new CarPark();
		assertEquals(0, carPark.getNumCars());
		assertEquals(0, carPark.getNumSmallCars());
		assertEquals(0, carPark.getNumMotorCycles());
	}

	/**
	*  Ensure our parameter constructor works and CarPark is empty.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkConstructorParkEmpty() {
		CarPark carPark = new CarPark(DEFAULT_MAX_CAR_SPACES, DEFAULT_MAX_SMALL_CAR_SPACES, 
									DEFAULT_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		assertTrue(carPark.carParkEmpty());
	}
	
	/**
	*  Ensure our parameter constructor works and queue is empty.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkConstructorQueueEmpty() {
		CarPark carPark = new CarPark(DEFAULT_MAX_CAR_SPACES, DEFAULT_MAX_SMALL_CAR_SPACES, 
									DEFAULT_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		assertEquals(0, carPark.numVehiclesInQueue());
	}

	/**
	*  Ensure our parameter constructor works and no vehicles exist.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkConstructorNoVehicles() {
		CarPark carPark = new CarPark(DEFAULT_MAX_CAR_SPACES, DEFAULT_MAX_SMALL_CAR_SPACES, 
									DEFAULT_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		assertEquals(0, carPark.getNumCars());
		assertEquals(0, carPark.getNumSmallCars());
		assertEquals(0, carPark.getNumMotorCycles());
	}
	
	/**
	*  Test that ArchiveDepartingVehicles archives the vehicles that have
	*  left after they have parked, without forcing them.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/ 
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

	/**
	*  Test that ArchiveDepartingVehicles archives the vehicles that have left after
	*  they have parked, by forcing them. As this test uses the getstatus method,
	*  it also checks that ArchiveDepartingVehicles sets the string for the output as well.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/ 
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
	
	/**
	*  Test that ArchiveDepartingVehicles unparks the vehicles.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/ 
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
	
	/**
	*  Test that ArchiveDepartingVehicles unparks all vehicles at the end of the day.
	*  @author Samuel Hammill
	*/ 
	@Test
	public void testArchiveDepartingVehiclesParkEmpty() throws VehicleException,	SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		CarPark carPark = new CarPark();
		
		carPark.parkVehicle(motorCycle, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveDepartingVehicles(DEFAULT_TIME, FORCE_LEAVE);
		
		assertTrue(carPark.carParkEmpty());
	}
	
	/**
	*  Test that ArchiveDepartingVehicles doesn't affect the queue.
	*  @author Samuel Hammill
	*/ 
	@Test
	public void testArchiveDepartingVehiclesQueueEmpty() throws VehicleException,	SimulationException {
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		CarPark carPark = new CarPark();
		carPark.enterQueue(smallCar);
		
		carPark.parkVehicle(motorCycle, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_TIME, DEFAULT_STAY_DURATION);
		
		carPark.archiveDepartingVehicles(DEFAULT_TIME, FORCE_LEAVE);
		
		assertFalse(carPark.queueEmpty());
	}
	
	

	/**
	*  Check to see that ArchiveNewVehicle archives new vehicles.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/ 
	@Test
	public void testArchiveNewVehicleAddsDissatisfied()	throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.archiveNewVehicle(motorCycle);
		carPark.archiveNewVehicle(car);
		carPark.archiveNewVehicle(smallCar);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:3::A:3::Q:0\n", status);
	}

	/**
	*  Check to see that ArchiveNewVehicle throws a SimulationException if the
	*  queue contains the vehicle that is trying to get archived.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/ 
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicleQueued() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveNewVehicle(motorCycle);
	}

	/**
	*  Check to see that ArchiveNewVehicle throws a SimulationException if the
	*  carpark contains the vehicle that is trying to get archived.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test(expected = SimulationException.class)
	public void testArchiveNewVehicleParked() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.archiveNewVehicle(motorCycle);
	}
	
	/**
	*  Check to see that a New Archived Vehicle is dissatisfied.
	*  @author Samuel Hammill
	*/
	@Test
	public void testArchiveNewVehicleDissatisfied() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.archiveNewVehicle(car);
		
		assertFalse(car.isSatisfied());
	}
	
	/**
	*  Check to see that a New Archived Vehicle was never parked.
	*  @author Samuel Hammill
	*/
	@Test
	public void testArchiveNewVehicleNeverParked() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.archiveNewVehicle(car);
		
		assertFalse(car.wasParked());
	}
	
	/**
	*  Check to see that a New Archived Vehicle was never queued.
	*  @author Samuel Hammill
	*/
	@Test
	public void testArchiveNewVehicleNeverQueued() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.archiveNewVehicle(car);
		
		assertFalse(car.wasQueued());
	}
	
	/**
	*  Check to see that a New Archived Vehicle is not parked.
	*  @author Samuel Hammill
	*/
	@Test
	public void testArchiveNewVehicleNotParked() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.archiveNewVehicle(car);
		
		assertFalse(car.isParked());
	}
	
	/**
	*  Check to see that a New Archived Vehicle is not queued.
	*  @author Samuel Hammill
	*/
	@Test
	public void testArchiveNewVehicleNotQueued() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.archiveNewVehicle(car);
		assertFalse(car.isQueued());
	}
	

	/**
	*  Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	*  archive when it has been in the queue for too long. Boundry.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testArchiveQueueFailuresArchiveBoundry() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(LEAVE_QUEUE_BOUNDRY);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		System.out.println(status);
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:1::A:1::Q:0|M:Q>A|\n", status);
	}
	
	
	/**
	*  Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	*  archive when it has been in the queue for too long. High wait time.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testArchiveQueueFailuresArchiveHigh() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(HIGH_QUEUE_TIME);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:1::A:1::Q:0|M:Q>A|\n", status);
	}
	

	/**
	*  Test ArchiveQueueFailures to see if it remove the vehicle from the queue by setting
	*  Vehicle. IsQueued to false once it has been in the queue for too long.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testArchiveQueueFailuresIsQueued() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(HIGH_QUEUE_TIME);
		assertFalse(motorCycle.isQueued());
	}

	/**
	*  Test ArchiveQueueFailures to see if it adds queue failure vehicles to the
	*  archive when it has not been in the queue for too long.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testArchiveQueueFailuresArchiveFalse() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.archiveQueueFailures(LEAVE_QUEUE_LOWER_BOUNDRY);
		
		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:1M\n", status);
	}
	
	
	/**
	*  Test carParkEmpty() to see if it correctly identifies if
	*  the car park is empty with a car inside.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkEmptyFalseCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.carParkEmpty());
	}
	
	/**
	*  Test carParkEmpty() to see if it correctly identifies if
	*  the car park is empty with a motor cycle inside.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkEmptyFalseMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle m = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(m, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.carParkEmpty());
	}
	
	
	/**
	*  Test carParkEmpty() to see if it correctly identifies if
	*  the car park is empty after creation.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkEmptyTrue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		assertTrue(carPark.carParkEmpty());
	}
	
	
	/**
	*  Test carParkEmpty() to see if it correctly identifies if
	*  the car park is empty after a motorcycle leaves.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkEmptyTrueAfterMotorCycleLeaves() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle m = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(m, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(m, DEFAULT_DEPARTURE_TIME);
		
		assertTrue(carPark.carParkEmpty());
	}
	
	/**
	*  Test carParkEmpty() to see if it correctly identifies if
	*  the car park is empty after a car leaves.
	*  @author Samuel Hammill
	*/
	@Test
	public void testCarParkEmptyTrueAfterCarLeaves() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		Car c = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(c, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(c, DEFAULT_DEPARTURE_TIME);
		
		assertTrue(carPark.carParkEmpty());
	}
	

	/**
	*  Test CarparkFull to see if it returns false when it is one short
	*  of it's capacity, having a vehicle of each added to it.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testCarParkFullFalse() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(BOUNDRY_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.carParkFull());
	}

	/**
	*  Test CarParkFull full to see if it returns true after being filled.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testCarParkFullTrue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.carParkFull());
	}

	/**
	*  Test to see that EnterQueue enters the vehicle into the queue by
	*  setting isQueued to true.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testEnterQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.enterQueue(motorCycle);
		assertTrue(motorCycle.isQueued());
	}
	
	/**
	* Test to see that enterQueue throws exception if queue is full.
	*  setting isQueued to true.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test(expected = SimulationException.class)
	public void testEnterQueueWhenQueueIsFull() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 0);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.enterQueue(car);
	}

	/**
	*  Test to see that ExitQueue exits the vehicle from the queue by setting
	*  isQueued to false.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testExitQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.exitQueue(motorCycle, LEAVE_QUEUE_TIME);
		
		assertFalse(motorCycle.isQueued());
	}
	
	/**
	*  Test to see that exitQueue throws exception if vehicle not in queue.
	*  @author Samuel Hammill
	*/
	@Test(expected = SimulationException.class)
	public void testExitQueueWhenNotInQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.exitQueue(motorCycle, LEAVE_QUEUE_TIME);
	}	

	
	/**
	*  Testing this one is a bit tricky as we need to test string output I think
	*  and what people write may differ, so we could assume that given this test is written
	*  first, the string output could be written to match it.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testFinalState() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.archiveNewVehicle(motorCycle);
		carPark.archiveNewVehicle(car);
		carPark.archiveNewVehicle(smallCar);
		
		System.out.println(carPark.finalState());
		// assertTrue("")
	}


	/**
	*  Test here that GetNumCars returns the correct number of cars in the
	*  CarPark after cars are parked.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertEquals(2, carPark.getNumCars());
	}
	
	
	/**
	*  Test here that GetNumCars returns the correct number of cars in the
	*  CarPark after cars are parked and removed.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumCarsAfterRemoval() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car car2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);

		carPark.unparkVehicle(car, DEFAULT_DEPARTURE_TIME);
		carPark.unparkVehicle(smallCar, DEFAULT_DEPARTURE_TIME);
		
		assertEquals(1, carPark.getNumCars());
	}
	

	/**
	*  Test here that GetNumMotorCycles returns the correct number of cars in
	*  the CarPark after cars are parked.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);

		assertEquals(1, carPark.getNumMotorCycles());
	}
	
	/**
	*  Test here that GetNumCars returns the correct number of cars in the
	*  CarPark after cars are parked and removed.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumMotorCycleAfterRemoval() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);

		carPark.unparkVehicle(motorCycle, DEFAULT_DEPARTURE_TIME);
		carPark.unparkVehicle(smallCar, DEFAULT_DEPARTURE_TIME);
		
		assertEquals(1, carPark.getNumMotorCycles());
	}

	/**
	*  Test here that GetNumSmallCars returns the correct number of cars in
	*  the CarPark after cars are parked.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumSmallCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertEquals(1, carPark.getNumSmallCars());
	}
	
	/**
	*  Test here that GetNumCars returns the correct number of cars in the
	*  CarPark after cars are parked  and removed.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetNumSmallCarsAfterRemoval() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car smallCar2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);

		carPark.unparkVehicle(car, DEFAULT_DEPARTURE_TIME);
		carPark.unparkVehicle(smallCar, DEFAULT_DEPARTURE_TIME);
		
		assertEquals(1, carPark.getNumSmallCars());
	}
	

	/**
	*  Test getStatus by checking that the string it produces is correct.
	*  how this is returning false i don't know.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testGetStatus() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);

		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		String status = carPark.getStatus(DEFAULT_TIME);

		assertEquals("1::0::P:3::C:2::S:1::M:1::D:0::A:0::Q:0\n", status);
	}


	/**
	*  Test InitialState by checking that the string it produces is correct.
	*  how this is returning false i don't know.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testInitialState() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_TWO);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);

		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		String str = carPark.initialState();
		assertEquals(str, "CarPark [maxCarSpaces: 2 maxSmallCarSpaces: 1 maxMotorCycleSpaces: 1 maxQueueSize: 2]");
	}


	/**
	*  Test numVehiclesInQueue returns the right number of vehicles.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testNumVehiclesInQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.enterQueue(motorCycle);
		carPark.enterQueue(car);
		carPark.enterQueue(smallCar);
		
		assertEquals(3, carPark.numVehiclesInQueue());
	}
	
	/**
	*  Test numVehiclesInQueue returns the right number of vehicles.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testNumVehiclesInQueueAfterLeaving() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, MAX_QUEUE_SIZE_THREE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.enterQueue(motorCycle);
		carPark.enterQueue(car);
		carPark.enterQueue(smallCar);
		carPark.exitQueue(motorCycle, LEAVE_QUEUE_TIME);
		carPark.exitQueue(car, LEAVE_QUEUE_TIME);
		
		assertEquals(1, carPark.numVehiclesInQueue());
	}
	
	
	/**
	*  Test that ParkVehicle throws exception if no car spaces are available.
	*  @author Samuel Hammill
	*/
	@Test(expected = SimulationException.class)
	public void testParkVehicleNoCarSpacesAvailable() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(0, DEFAULT_MAX_SMALL_CAR_SPACES, DEFAULT_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
	}
	
	/**
	*  Test that ParkVehicle throws exception if no small car spaces are available.
	*  @author Samuel Hammill
	*/
	@Test(expected = SimulationException.class)
	public void testParkVehicleNoSmallCarSpacesAvailable() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(1, 0, DEFAULT_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
	}
	
	/**
	*  Test that ParkVehicle throws exception if no motor cycle spaces are available.
	*  @author Samuel Hammill
	*/
	@Test(expected = SimulationException.class)
	public void testParkVehicleNoMotorCycleSpacesAvailable() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(DEFAULT_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, DEFAULT_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
	}
	
	/**
	*  Test that ParkVehicle parks a car and small car by seeing that it
	*  increments the getNumCars method.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testParkVehicleCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.getNumCars() == 2);
	}

	/**
	*  Test that ParkVehicle parks a small car by seeing that it increments
	*  the getNumSmallCars method.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testParkVehicleSmallCars() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertEquals(1, carPark.getNumSmallCars());
	}

	/**
	*  Test that ParkVehicle parks a motorCycle by seeing that it increments
	*  the getNumMotorCycle method. testParkVehicleSmallCars.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testParkVehicleMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertEquals(1, carPark.getNumMotorCycles());
	}
	
	/**
	*  Test proccessQueue to see that it removes a vehicle from the queue
	*  after it gets added to the park after being in the queue.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testProcessQueueQueue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark();
		Simulator sim = new Simulator();
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.enterQueue(car);
		carPark.processQueue(LEAVE_QUEUE_TIME, sim);
		
		assertTrue(carPark.queueEmpty());
	}

	/**
	*  Test proccessQueue to see that it adds a vehicle to the park after it
	*  has been in the queue.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testProcessQueuePark() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark();
		Simulator sim = new Simulator();
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.enterQueue(car);
		carPark.processQueue(LEAVE_QUEUE_TIME, sim);
		assertEquals(1, carPark.getNumCars());
	}

	/**
	*  Tests the queueEmpty returns true if queue is empty.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testQueueEmptyTrue() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		carPark.exitQueue(motorCycle, LEAVE_QUEUE_TIME);
		assertTrue(carPark.queueEmpty());
	}
	

	/**
	*  Test queueFull to see if it returns full when the queue is full.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testQueueFull() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.enterQueue(motorCycle);
		assertTrue(carPark.queueFull());
	}

	/**
	*  Test to see that there is a space available for a car.
	*  @author Laurence Mccabe (Base Method)
	*  @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testSpacesAvailableCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		assertTrue(carPark.spacesAvailable(car));
	}
	
	
	/**
	*  Test to see that there are no spaces available for a car.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableCarFalseCars() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);

		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(car));
	}
	
	/**
	*  Test to see that there are spaces available for a car.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableCarFalseSmallCars1() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(BOUNDRY_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car car1 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car3 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.spacesAvailable(car3));
	}
	
	/**
	*  Test to see that there are no spaces available for a car.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableCarFalseSmallCars2() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(BOUNDRY_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car car1 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car3 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car car4 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car3, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(car4));
	}
	
	
	/**
	*  Test to see that there are no spaces available for a car after small cars and motorcycles have taken some.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableCarFalseSmallCarsAndCycles1() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(BOUNDRY_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		
		Car carN = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car carN2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car carS = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(carS, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(carN, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(carN2));
	}
	
	
	/**
	*  Test to see that there are no spaces available for a car after small cars and motorcycles have taken some.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableCarFalseSmallCarsAndCycles2() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(BOUNDRY_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		Car carN = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car carS = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car carS2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(carS, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(carS2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(carN));
	}
	

	/**
	*  Test to see that there is a space available for a small car.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableSmallCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		assertTrue(carPark.spacesAvailable(smallCar));
	}
	
	/**
	*  Test to see that there is a space available for a small car after motorcycles park.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableTrueSmallCarWithMotorcycles1() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.spacesAvailable(smallCar));
	}
	
	/**
	*  Test to see that there is a space available for a small car after motorcycles park.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableTrueSmallCarWithMotorcycles2() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		Car smallCar1 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.spacesAvailable(smallCar1));
	}
	
	/**
	*  Test to see that there is a space available for a small car after motorcycles park.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableFalseSmallCarWithMotorcycles() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar1 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle2, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(car));
	}
	
	/**
	*  Test to see that there is a space available for a small car after motorcycles park.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableTrueSmallCarWithMotorcycles3() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle1 = new MotorCycle(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar1 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle1, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertTrue(carPark.spacesAvailable(smallCar1));
	}
	

	/**
	*  Test to see that there is a space available for a motorcycle.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableMotorCycle1() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(1, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		assertTrue(carPark.spacesAvailable(motorCycle));
	}
	
	/**
	*  Test to see that there is a space available for a motorcycle.
	*  @author Samuel Hammill
	*/
	@Test
	public void testSpacesAvailableMotorCycle2() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(1, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		assertFalse(carPark.spacesAvailable(motorCycle));
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new car when spaces
	* are available in the park by adding it to the park.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesParkCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 1, 1);
		carPark.tryProcessNewVehicles(DEFAULT_ARRIVAL_TIME, sim);
		
		assertFalse(carPark.carParkEmpty());

	}

	/**
	* Test to see that TryProcessNewVehicles processes a new car when spaces
	* are not available in the park by adding it to the queue.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesQueueCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.parkVehicle(car, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(smallCar, 2, Constants.MINIMUM_STAY);
		carPark.parkVehicle(motorCycle, 2, Constants.MINIMUM_STAY);
		
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 0, 0);
		carPark.tryProcessNewVehicles(DEFAULT_TIME, sim);
		assertFalse(carPark.queueEmpty());
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new car when spaces
	* are not available in the park or queue, by adding it to the archive
	* of dissatisfied customers.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesArchiveCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 0, 0);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car car2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.enterQueue(car2);
		carPark.tryProcessNewVehicles(DEFAULT_TIME, sim);
		String str = carPark.getStatus(DEFAULT_TIME);
		assertEquals(str, "1::1::P:1::C:1::S:0::M:0::D:1::A:1::Q:1C|C:N>A|\n");
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new small car when
	* spaces are available in the park by adding it to the park.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesParkSmallCar()	throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 0, 0);
		carPark.tryProcessNewVehicles(DEFAULT_ARRIVAL_TIME, sim);
		assertFalse(carPark.queueEmpty());
	}
	
	/**
	* Test to see that TryProcessNewVehicles processes a new small car when spaces
	* are not available in the park by adding it to the queue.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesQueueSmallCar() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 1, 0);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		
		carPark.tryProcessNewVehicles(DEFAULT_ARRIVAL_TIME, sim);
		assertFalse(carPark.queueEmpty());
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new small car when spaces
	* are not available in the park or queue, by adding it to the archive
	* of dissatisfied customers.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesSmallCarArchive() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 1, 1, 0);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car car2 = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.enterQueue(car2);
		carPark.tryProcessNewVehicles(DEFAULT_ARRIVAL_TIME, sim);
		String t = carPark.getStatus(DEFAULT_TIME);
		assertTrue("1::1::P:2::C:2::S:1::M:0::D:0::A:0::Q:1C|S:N>P|\n".equals(t));
	}
	
	/**
	* Test to see that TryProcessNewVehicles processes a new MotorCycle when
	* spaces are available in the park by adding it to the park.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesParkMotorCycle() throws SimulationException, VehicleException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 0, 0, 1);
		carPark.tryProcessNewVehicles(DEFAULT_ARRIVAL_TIME, sim);
		
		assertFalse(carPark.carParkEmpty());
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new Motorcycle when spaces
	* are not available in the park by adding it to the queue.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/
	@Test
	public void testTryProcessNewVehiclesQueueMotorCycle() throws SimulationException, VehicleException {
        CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 0, 0, 1);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.enterQueue(motorCycle2);
		
		carPark.tryProcessNewVehicles(DEFAULT_TIME, sim);
		assertFalse(carPark.queueEmpty());
	}

	/**
	* Test to see that TryProcessNewVehicles processes a new motorcycle when spaces
	* are not available in the park or queue, by adding it to the archive
	* of dissatisfied customers.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/	
	@Test
	public void testTryProcessNewVehiclesArchiveMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, LOW_MAX_QUEUE_SIZE);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		MotorCycle motorCycle2 = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.enterQueue(motorCycle2);
		
		Simulator sim = new Simulator(Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, Constants.DEFAULT_INTENDED_STAY_SD, 0, 0, 1);;
		carPark.tryProcessNewVehicles(DEFAULT_TIME, sim);
		
		String m = carPark.getStatus(DEFAULT_TIME);
		assertTrue("1::1::P:2::C:0::S:0::M:2::D:0::A:0::Q:1M|M:N>P|\n".equals(m));
	}

	
	/**
	* Test Unpark Vehicle to see if it unparks a vehicle when it isn't parked.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/	
	@Test(expected = SimulationException.class)
	public void testUnparkVehicle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.unparkVehicle(motorCycle, DEFAULT_DEPARTURE_TIME);
	}

	/**
	* Test Unpark Vehicle to see if it unparks a Car when it is parked.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/	
	@Test
	public void testUnparkVehicleCar() throws VehicleException,	SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(car, DEFAULT_DEPARTURE_TIME);
		
		assertFalse(car.isParked());
	}

	/**
	* Test Unpark Vehicle to see if it unparks a Small Car when it is parked.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/	
	@Test
	public void testUnparkVehicleSmallCar() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(smallCar, DEFAULT_DEPARTURE_TIME);

		assertFalse(smallCar.isParked());
	}

	/**
	* Test UnparkVehicle to see if it unparks a MotorCycle when it is parked.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/	
	@Test
	public void testUnparkVehicleMotorCycle() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(motorCycle, DEFAULT_DEPARTURE_TIME);
		
		assertFalse(motorCycle.isParked());
	}

	/**
	* Test UnparkVehicle to see if it removes the number vehicles once they have been unparked.
	* @author Laurence Mccabe (Base Method)
	* @author Samuel Hammill (Refactoring & Constants)
	*/		
	@Test
	public void testUnparkVehicleArchived() throws VehicleException, SimulationException {
		CarPark carPark = new CarPark(LOW_MAX_CAR_SPACES, LOW_MAX_SMALL_CAR_SPACES, LOW_MAX_MOTOR_CYCLE_SPACES, 4);
		MotorCycle motorCycle = new MotorCycle(DEFAULT_MOTORCYCLE_ID, DEFAULT_ARRIVAL_TIME);
		Car car = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, NOT_SMALL_CAR);
		Car smallCar = new Car(DEFAULT_CAR_ID, DEFAULT_ARRIVAL_TIME, SMALL_CAR);
		
		carPark.parkVehicle(car, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(car, DEFAULT_DEPARTURE_TIME);
		carPark.parkVehicle(smallCar, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(smallCar, DEFAULT_DEPARTURE_TIME);
		carPark.parkVehicle(motorCycle, DEFAULT_ARRIVAL_TIME, DEFAULT_STAY_DURATION);
		carPark.unparkVehicle(motorCycle, DEFAULT_DEPARTURE_TIME);

		String status = (carPark.getStatus(DEFAULT_TIME));
		assertEquals("1::0::P:0::C:0::S:0::M:0::D:0::A:0::Q:0\n", status);
	}
	
	
	/*
	 * Confirm that the API spec has not been violated through the
	 * addition of public fields, constructors or methods that were
	 * not requested
	 */
	@Test
	public void NoExtraPublicMethods() {
		//Extends Object, extras less toString() 
		final int ExtraMethods = 21; 
		final int NumObjectClassMethods = Array.getLength(Object.class.getMethods());
		final int NumCarParkClassMethods = Array.getLength(CarPark.class.getMethods());
		assertTrue("obj:"+NumObjectClassMethods+":cp:"+NumCarParkClassMethods,(NumObjectClassMethods+ExtraMethods)==NumCarParkClassMethods);
	}
	
	@Test 
	public void NoExtraPublicFields() {
		//Same as Vehicle 
		final int NumObjectClassFields = Array.getLength(Object.class.getFields());
		final int NumCarParkClassFields = Array.getLength(CarPark.class.getFields());
		assertTrue("obj:"+NumObjectClassFields+":cp:"+NumCarParkClassFields,(NumObjectClassFields)==NumCarParkClassFields);
	}
	
	@Test 
	public void NoExtraPublicConstructors() {
		//One extra cons used. 
		final int NumObjectClassConstructors = Array.getLength(Object.class.getConstructors());
		final int NumCarParkClassConstructors = Array.getLength(CarPark.class.getConstructors());
		assertTrue(":obj:"+NumObjectClassConstructors+":cp:"+NumCarParkClassConstructors,(NumObjectClassConstructors+1)==NumCarParkClassConstructors);
	}
}