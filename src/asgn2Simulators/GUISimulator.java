/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import java.awt.HeadlessException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;

import org.jfree.ui.RefineryUtilities;

import static javax.swing.border.TitledBorder.*;
import asgn2CarParks.CarPark;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Simulators.Log;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;


/**
 * Class to operate the simulation, and display it using a GUI. 
 * @author Samuel Hammill
 * @author Laurence Mccabe
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame {
	
	// Constants
	private int WIDTH = 825;
	private int HEIGHT = 786;
	
	// Parameter fields, log, and input button and panels.
	private JFormattedTextField  seedText;
	private JFormattedTextField  carProbText;
	private JFormattedTextField  smallCarProbText;
	private JFormattedTextField  motorCycleProbText;
	private JFormattedTextField  meanStayText;
	private JFormattedTextField  staySDText;
	private JFormattedTextField  maxCarSpacesText;
	private JFormattedTextField  maxSmallCarSpacesText;
	private JFormattedTextField  maxMotorCycleSpacesText;
	private JFormattedTextField  maxQueueSizeText;
	private JTextArea logText;
	private JButton submitButton;
	private JPanel parameterBox;
	private JPanel parameters;
	private JPanel parametersLeft;
	private JPanel parametersRight;
	private JPanel logArea;
	private JPanel chartPanel;
	
	// Simulation Components
	private int seed;
	private double carProb;
	private double smallCarProb;
	private double motorCycleProb;       
	private double meanStay;
	private double staySD;
	private int maxCarSpaces;
	private int maxSmallCarSpaces;
	private int maxMotorCycleSpaces;
	private int maxQueueSize;
	
	ArrayList<Integer> totalVehicles = new ArrayList<Integer>();
	ArrayList<Integer> parkedVehicles = new ArrayList<Integer>();
	ArrayList<Integer> parkedCars = new ArrayList<Integer>();
	ArrayList<Integer> parkedSmallCars = new ArrayList<Integer>();
	ArrayList<Integer> parkedMotorCycles = new ArrayList<Integer>();
	ArrayList<Integer> vehiclesInQueue = new ArrayList<Integer>();
	ArrayList<Integer> vehiclesArchived = new ArrayList<Integer>();
	ArrayList<Integer> dissatisfiedVehicles = new ArrayList<Integer>();
	
		
	/**
	 * No argument constructor.
	 * @author Samuel Hammill
	 */
	public GUISimulator() {
		this(Constants.DEFAULT_MAX_CAR_SPACES, Constants.DEFAULT_MAX_SMALL_CAR_SPACES, Constants.DEFAULT_MAX_MOTORCYCLE_SPACES, 
			Constants.DEFAULT_MAX_QUEUE_SIZE, Constants.DEFAULT_SEED, Constants.DEFAULT_INTENDED_STAY_MEAN, 
			Constants.DEFAULT_INTENDED_STAY_SD, Constants.DEFAULT_CAR_PROB, Constants.DEFAULT_SMALL_CAR_PROB, Constants.DEFAULT_MOTORCYCLE_PROB);
	}
	

	/**
	 * Takes Command Line Input or default simulator parameters and sets up our GUI.
	 * @author Samuel Hammill
	 */
	public GUISimulator(int maxCarSpaces, int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize, int seed, 
		double meanStay, double staySD, double carProb, double smallCarProb, double motorCycleProb) throws HeadlessException {
		
		// Take Simulation parameters so we can display them in our GUI, and use them in our simulation.
		this.seed = seed;
		this.carProb = carProb;
		this.smallCarProb = smallCarProb;
		this.motorCycleProb = motorCycleProb;
		this.meanStay = meanStay;
		this.staySD = staySD;
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		
		// Setup our GUI
		initialiseUI();
	}
	

	/**
	 * Responsible for creating and displaying our GUI.
	 * @author Samuel Hammill
	 */
	private void initialiseUI() {
		setupFrame();
		setupPanels();
	    setupParameterTextFields();
	    addParametersToPanel();
	    setupRunSimulationButton();
		createChart();
	   
		// Add our top level panels onto the frame and render it visible.
		this.add(parameterBox);
		this.add(logArea);
		this.add(chartPanel);
        RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}
	
	
	/**
	 * @param args
	 * @throws SimulationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SimulationException, IOException {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new GUISimulator();
            }
        });
    }
	
	
	/**
	 * Takes input from parameter fields and starts the simulation.
	 * @author Samuel Hammill
	 */
	private void processAndStartSimulation() {
		submitButton.setEnabled(false);
		logText.setText("");
		
		// Process input parameters.
		seed = Integer.parseInt(seedText.getText());
		carProb = Double.parseDouble(carProbText.getText());
		smallCarProb = Double.parseDouble(smallCarProbText.getText());
		motorCycleProb = Double.parseDouble( motorCycleProbText.getText());
		meanStay = Double.parseDouble(meanStayText.getText());
		staySD = Double.parseDouble(staySDText.getText());
		maxCarSpaces = Integer.parseInt( maxCarSpacesText.getText());
		maxSmallCarSpaces = Integer.parseInt(maxSmallCarSpacesText.getText());
		maxMotorCycleSpaces = Integer.parseInt(maxMotorCycleSpacesText.getText());
		maxQueueSize = Integer.parseInt(maxQueueSizeText.getText());
		
		// Catch probability over 1.0 and set it back to 1.0 before running the simulation.
		if (carProb > 1) {
			carProb = 1.0;
			carProbText.setText("1.0");
		}
		if (smallCarProb > 1) {
			smallCarProb = 1.0;
			smallCarProbText.setText("1.0");
		}
		if (motorCycleProb > 1) {
			motorCycleProb = 1.0;
			motorCycleProbText.setText("1.0");
		}
		
		clearArrays();
		
		try {
			startSimulation();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	

	/**
	 * Method to run the simulation from start to finish. Exceptions are propagated upwards from Vehicle,
	 * Simulation and Log objects as necessary.
	 * Creates Carpark, Simulator, and Log Objects and runs the simulation in a loop.
	 * @throws VehicleException if Vehicle creation or operation constraints violated 
	 * @throws SimulationException if Simulation constraints are violated 
	 * @throws IOException on logging failures
	 * @author Samuel Hammill
	 */
	private void startSimulation() throws VehicleException, SimulationException, IOException {
		
		CarPark carPark = new CarPark(maxCarSpaces, maxSmallCarSpaces, maxMotorCycleSpaces, maxQueueSize);
		Simulator sim = null;
		Log log = null;
		
		try {
			sim = new Simulator(seed, meanStay, staySD, carProb, smallCarProb, motorCycleProb);
			log = new Log();
		} catch (IOException | SimulationException e1) {
			e1.printStackTrace();
			System.exit(-1);
		}
		
	 	log.initialEntry(carPark, sim);
	 	logText.append("Start of Simulation\n\n");
		
		for (int time=0; time<=Constants.CLOSING_TIME; time++) {
			//queue elements exceed max waiting time
			if (!carPark.queueEmpty()) {
				carPark.archiveQueueFailures(time);
			}
			//vehicles whose time has expired
			if (!carPark.carParkEmpty()) {
				//force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				carPark.archiveDepartingVehicles(time, force);
			}
			//attempt to clear the queue 
			if (!carPark.carParkFull()) {
				carPark.processQueue(time, sim);
			}
			// new vehicles from minute 1 until the last hour
			if (newVehiclesAllowed(time)) { 
				carPark.tryProcessNewVehicles(time, sim);
			}
			//Log progress
			log.logEntry(time, carPark);
			parseStatus(carPark.getStatus(time));
			
		}
		log.finalise(carPark);
		finaliseGUI();
		createChart();
	}
	
	
	/**
	 * Creates the chart and adds it to a panel on our frame.
	 * @author Samuel Hammill
	 */
	private void createChart() {
		chartPanel.removeAll();
		ChartPanel newChart = new ChartPanel(parkedVehicles, parkedCars, parkedSmallCars, parkedMotorCycles);
		chartPanel.add(newChart);
		chartPanel.revalidate();
	}
	
	
	/**
	 * 
	 * Parses the status string for us to create a chart based off the simulation data.
	 * @author Samuel Hammill
	 * @author Laurence Mccabe
	 */	
	private void parseStatus(String status) {
		// Constants for specific data positions in a string array.
		int VEHICLES_CREATED = 2;
		int PARKED_VEHICLES = 5;
		int CARS = 8;
		int SMALL_CARS = 11;
		int MOTOR_CYCLES = 14;
		int DISSATISFIED = 17;
		int ARCHIVED = 20;
		int QUEUED = 23;
		
		logText.append(status);
		
		String [] statusArray = status.split(":");
		totalVehicles.add(Integer.parseInt(statusArray[VEHICLES_CREATED]));
		parkedVehicles.add(Integer.parseInt(statusArray[PARKED_VEHICLES]));		  
		parkedCars.add(Integer.parseInt(statusArray[CARS]));
		parkedSmallCars.add(Integer.parseInt(statusArray[SMALL_CARS]));	
		parkedMotorCycles.add(Integer.parseInt(statusArray[MOTOR_CYCLES]));
		dissatisfiedVehicles.add(Integer.parseInt(statusArray[DISSATISFIED]));
		vehiclesArchived.add(Integer.parseInt(statusArray[ARCHIVED]));
		vehiclesInQueue.add(Integer.parseInt(statusArray[QUEUED].replaceAll("[^\\d]", "")));
	}
	
	/**
	 * Prints a message after the end of the simulation
	 * informing the user that the simulation is complete
	 * and the output file was generated.
	 * @author Samuel Hammill
	 */
	private void finaliseGUI() {
		submitButton.setEnabled(true);
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		logText.append("\nSimulation Complete\nOutput file written: " + timeLog);
	}
	
	
	/**
	 * Clears previous simulation array data.
	 * @author Samuel Hammill
	 */
	private void clearArrays() {
		totalVehicles.clear();
		parkedVehicles.clear();	  
		parkedCars.clear();
		parkedSmallCars.clear();
		parkedMotorCycles.clear();
		dissatisfiedVehicles.clear();
		vehiclesArchived.clear();
		vehiclesInQueue.clear();
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
	
	
	
	/**
	 * Method to setup our Jframe parameters.
	 * @author Samuel Hammill
	 */
	private void setupFrame() {
		this.setTitle("Car Park Simulator");
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	/**
	 * Method to setup our panels for containing elements and layout.
	 * @author Samuel Hammill
	 * @author Laurence Mccabe
	 */
	private void setupPanels() {
		// Create our panels to manage our customisable parameters.
	    parameterBox = new JPanel();
	    parameterBox.setBounds(10, 455, 340, 295);
	    parameterBox.setLayout(new BoxLayout(parameterBox, BoxLayout.PAGE_AXIS));
		parameterBox.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Simulation Parameters", CENTER, TOP));
	    
	    parameters = new JPanel();
	    parameters.setLayout(new GridLayout(0, 2, 20, 15));
	    parameters.setBorder(new EmptyBorder(0, 10 , 10, 10));
	    
	    parametersLeft = new JPanel();
	    parametersRight = new JPanel();
	    parametersLeft.setLayout(new GridLayout(0, 1));
	    parametersRight.setLayout(new GridLayout(0, 1));
	    
		parameterBox.add(parameters);
		parameters.add(parametersLeft);
		parameters.add(parametersRight);

		// Create a panel to display our log. Add a Text Area to go into it.
	    logArea = new JPanel();
		logArea.setBounds(370, 455, 440, 295);
		
		logText = new JTextArea(18,38);
		logText.setEditable(false);
		logText.setLineWrap(true);

	    JScrollPane scrollPane = new JScrollPane(logText);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    logArea.add(scrollPane);
	    
	    // Create a panel to hold our chart.
	    chartPanel = new JPanel();
	    chartPanel.setBounds(5, 5, 810, 440);
	    //chartPanel.setBackground(Color.RED);
	    //chartPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Chart", CENTER, TOP));
	}
	
	
	/**
	 * Method to setup NumberFormats for input processing, and JFormattedTextFields
	 * for text input from user before simulation starts.
	 * @author Samuel Hammill
	 */
	private void setupParameterTextFields() {
	    // Create NumberFormats to manage our parameter input easily.
	    NumberFormat intFormat = NumberFormat.getNumberInstance();
	    intFormat.setMaximumFractionDigits(0);
	    intFormat.setMaximumIntegerDigits(9);
	    intFormat.setGroupingUsed(false);
	    NumberFormat probFormat = NumberFormat.getNumberInstance();
	    probFormat.setMinimumFractionDigits(1);
	    probFormat.setMaximumIntegerDigits(1);
	    probFormat.setGroupingUsed(false);
	    NumberFormat doubleFormat = NumberFormat.getNumberInstance();
	    doubleFormat.setMinimumFractionDigits(1);
	    doubleFormat.setGroupingUsed(false);
	    
		// Setup parameter text fields and give them NumberFormats
	    seedText = new JFormattedTextField(intFormat);
	    carProbText = new JFormattedTextField(probFormat);
	    smallCarProbText = new JFormattedTextField(probFormat);
	    motorCycleProbText = new JFormattedTextField(probFormat);
	    meanStayText = new JFormattedTextField(doubleFormat);
	    staySDText = new JFormattedTextField(doubleFormat);
	    maxCarSpacesText = new JFormattedTextField(intFormat);
	    maxSmallCarSpacesText = new JFormattedTextField(intFormat);
	    maxMotorCycleSpacesText = new JFormattedTextField(intFormat);
	    maxQueueSizeText = new JFormattedTextField(intFormat);
	    
	    // Center text in the middle of the text box.
	    seedText.setHorizontalAlignment(JTextField.CENTER);
	    carProbText.setHorizontalAlignment(JTextField.CENTER);
	    smallCarProbText.setHorizontalAlignment(JTextField.CENTER);
	    motorCycleProbText.setHorizontalAlignment(JTextField.CENTER);
	    meanStayText.setHorizontalAlignment(JTextField.CENTER);
	    staySDText.setHorizontalAlignment(JTextField.CENTER);
	    maxCarSpacesText.setHorizontalAlignment(JTextField.CENTER);
	    maxSmallCarSpacesText.setHorizontalAlignment(JTextField.CENTER);
	    maxMotorCycleSpacesText.setHorizontalAlignment(JTextField.CENTER);
	    maxQueueSizeText.setHorizontalAlignment(JTextField.CENTER);
	    
	    // Set the initial text to appear as the CLI or Default arguments.
	    seedText.setValue(new Integer(seed));
	    carProbText.setValue(new Double(carProb));
	    smallCarProbText.setValue(new Double(smallCarProb));
	    motorCycleProbText.setValue(new Double(motorCycleProb));
	    meanStayText.setValue(new Double(meanStay));
	    staySDText.setValue(new Double(staySD));
	    maxCarSpacesText.setValue(new Integer(maxCarSpaces));
	    maxSmallCarSpacesText.setValue(new Integer(maxSmallCarSpaces));
	    maxMotorCycleSpacesText.setValue(new Integer(maxMotorCycleSpaces));
	    maxQueueSizeText.setValue(new Integer(maxQueueSize));
	}	
	
	
	/**
	 * Add the individual parameters and labels to the parameter panels.
	 * @author Samuel Hammill
	 */
	private void addParametersToPanel() {
	    JLabel seedLabel = new JLabel("Random Seed:");
	    JLabel carProbLabel = new JLabel("Car Probabilty: (0-1)");
	    JLabel smallCarProbLabel = new JLabel("Small Car Probabilty:");
	    JLabel motorCycleProbLabel = new JLabel("Motor Cycle Probabilty:");
	    JLabel meanStayLabel = new JLabel("Average Stay Time:");
	    JLabel staySDLabel = new JLabel("Stay Time SD:");
	    JLabel maxCarSpacesLabel = new JLabel("Max Car Spaces:");
	    JLabel maxSmallCarSpacesLabel = new JLabel("Max Small Car Spaces:");
	    JLabel maxMotorCycleSpacesLabel = new JLabel("Max Motor Cycle Spaces:");
	    JLabel maxQueueSizeLabel = new JLabel("Max Queue Size:");
		
		parametersLeft.add(seedLabel);
		parametersLeft.add(seedText);
		parametersLeft.add(carProbLabel);
		parametersLeft.add(carProbText);
		parametersLeft.add(smallCarProbLabel);
		parametersLeft.add(smallCarProbText);
		parametersLeft.add(motorCycleProbLabel);
		parametersLeft.add(motorCycleProbText);
		parametersLeft.add(meanStayLabel);
		parametersLeft.add(meanStayText);

		parametersRight.add(maxQueueSizeLabel);
		parametersRight.add(maxQueueSizeText);
		parametersRight.add(maxCarSpacesLabel);
		parametersRight.add(maxCarSpacesText);
		parametersRight.add(maxSmallCarSpacesLabel);
		parametersRight.add(maxSmallCarSpacesText);
		parametersRight.add(maxMotorCycleSpacesLabel);
		parametersRight.add(maxMotorCycleSpacesText);
		parametersRight.add(staySDLabel);
		parametersRight.add(staySDText);
	}

	
	private void setupRunSimulationButton() {
		// Create and setup a button to run our simulation.
	    submitButton = new JButton("RUN SIMULATION");
	    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

	    submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	processAndStartSimulation();
            }
        });
		parameterBox.add(submitButton);
		parameterBox.add(Box.createRigidArea(new Dimension(0, 6)));
	}
	
}
