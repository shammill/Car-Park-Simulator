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
	private JFormattedTextField seedText;
	private JFormattedTextField carProbText;
	private JFormattedTextField smallCarProbText;
	private JFormattedTextField motorCycleProbText;
	private JFormattedTextField meanStayText;
	private JFormattedTextField staySDText;
	private JFormattedTextField maxCarSpacesText;
	private JFormattedTextField maxSmallCarSpacesText;
	private JFormattedTextField maxMotorCycleSpacesText;
	private JFormattedTextField maxQueueSizeText;
	private JTextArea logText;
	private JButton submitButton;
	private JPanel parameterBox;
	private JPanel parameters;
	private JPanel parametersLeft;
	private JPanel parametersRight;
	private JPanel logArea;
	private JPanel chartPanelAllData;
	private JPanel chartPanelVehicles;
	private JPanel chartPanelBarGraph;
	
	// Simulation Paramters
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
	
	// ArrayLists containing our simulation datasets for charting.
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
	    setupChartButtonAllData();
	    setupChartButtonVehicles();
	    setupChartButtonBarGraph();
		createChart();
	   
		// Add our top level panels onto the frame and render it visible.
		this.add(parameterBox);
		this.add(logArea);
		this.add(chartPanelAllData);
		this.add(chartPanelVehicles);
		this.add(chartPanelBarGraph);
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
	 * Used by our start simulation button.
	 * Takes user input from parameter fields, and starts the simulation.
	 * @author Samuel Hammill
	 */
	private void processAndStartSimulation() {
		submitButton.setEnabled(false);
		logText.setText("");
		
		getTextFieldInputs();
		updateTextFields();
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
	 * Creates the charts and adds them to a panel on our frame.
	 * @author Samuel Hammill
	 */
	private void createChart() {
		chartPanelAllData.removeAll();
		chartPanelVehicles.removeAll();
		chartPanelBarGraph.removeAll();
		
		ChartPanel newChartAllData = new ChartPanel(parkedVehicles, parkedCars, parkedSmallCars, parkedMotorCycles, 
												vehiclesInQueue, vehiclesArchived, totalVehicles, dissatisfiedVehicles);
		ChartPanel newChartVehicles = new ChartPanel(parkedVehicles, parkedCars, parkedSmallCars, parkedMotorCycles, vehiclesInQueue);
		ChartPanel newChartBarGraph = new ChartPanel(totalVehicles, dissatisfiedVehicles);
		
		chartPanelAllData.add(newChartAllData);
		chartPanelVehicles.add(newChartVehicles);
		chartPanelBarGraph.add(newChartBarGraph);
		
		chartPanelAllData.setVisible(true);
		chartPanelVehicles.setVisible(false);
		chartPanelBarGraph.setVisible(false);
		
		chartPanelAllData.revalidate();
	}
	
	
	/**
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
	 * Clears previous simulation array data, so we can re-use the arrays.
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
	 */
	private void setupPanels() {
		setupParameterPanels();
		setupLogPanel();
		setupChartPanels();
	}
	
	
	/**
	 * Method to create our panels to manage our customisable parameters..
	 * @author Samuel Hammill
	 */
	private void setupParameterPanels() {
		int PBOX_X_COORD = 10;
		int PBOX_Y_COORD = 455;
		int PBOX_X_SIZE = 340;
		int PBOX_Y_SIZE = 295;
		
		parameterBox = new JPanel();
	    parameterBox.setBounds(PBOX_X_COORD, PBOX_Y_COORD, PBOX_X_SIZE, PBOX_Y_SIZE);
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
	}
	
	
	/**
	 * Method to create our panel to manage our log text.
	 * @author Samuel Hammill
	 * @author Laurence Mccabe
	 */
	private void setupLogPanel() {
		int LOG_X_COORD = 370;
		int LOG_Y_COORD = 455;
		int LOG_X_SIZE = 440;
		int LOG_Y_SIZE = 295;
		int LOG_TEXT_X_SIZE = 18;
		int LOG_TEXT_Y_SIZE = 38;
		
	    logArea = new JPanel();
		logArea.setBounds(LOG_X_COORD, LOG_Y_COORD, LOG_X_SIZE, LOG_Y_SIZE);
		
		logText = new JTextArea(LOG_TEXT_X_SIZE, LOG_TEXT_Y_SIZE);
		logText.setEditable(false);
		logText.setLineWrap(true);
	
	    JScrollPane scrollPane = new JScrollPane(logText);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    logArea.add(scrollPane);
	}
	
	
	/**
	 * Method to create our panel to manage our chart panels.
	 * @author Samuel Hammill

	 */
	private void setupChartPanels() {
		int CHART_X_COORD = 10;
		int CHART_Y_COORD = 5;
		int CHART_X_SIZE = 700;
		int CHART_Y_SIZE = 445;
		
	    chartPanelAllData = new JPanel();
	    chartPanelAllData.setBounds(CHART_X_COORD, CHART_Y_COORD, CHART_X_SIZE, CHART_Y_SIZE);
	    
	    chartPanelVehicles = new JPanel();
	    chartPanelVehicles.setBounds(CHART_X_COORD, CHART_Y_COORD, CHART_X_SIZE, CHART_Y_SIZE);
	    
	    chartPanelBarGraph = new JPanel();
	    chartPanelBarGraph.setBounds(CHART_X_COORD, CHART_Y_COORD, CHART_X_SIZE, CHART_Y_SIZE);
	}
	
	
	/**
	 * Method to setup NumberFormats for input processing, and JFormattedTextFields
	 * for text input from user before simulation starts.
	 * @author Samuel Hammill
	 */
	private void setupParameterTextFields() {
		int HIGHEST_DIGITS = 9;
		int MAX_DECIMAL_PLACES = 1;
		int MAX_PROBABILITY_DIGITS = 1;
		int NO_DECIMALS = 0;
		
	    // Create NumberFormats to manage our parameter input easily.
	    NumberFormat intFormat = NumberFormat.getNumberInstance();
	    intFormat.setMaximumFractionDigits(NO_DECIMALS);
	    intFormat.setMaximumIntegerDigits(HIGHEST_DIGITS);
	    intFormat.setGroupingUsed(false);
	    NumberFormat probFormat = NumberFormat.getNumberInstance();
	    probFormat.setMinimumFractionDigits(MAX_DECIMAL_PLACES);
	    probFormat.setMaximumIntegerDigits(MAX_PROBABILITY_DIGITS);
	    probFormat.setGroupingUsed(false);
	    NumberFormat doubleFormat = NumberFormat.getNumberInstance();
	    doubleFormat.setMinimumFractionDigits(MAX_DECIMAL_PLACES);
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

	
	/**
	 * Create and setup a button to run our simulation.
	 * When pressed, the text fields are processed and 
	 * the data is passed to a new chart object.
	 * @author Samuel Hammill
	 */	
	private void setupRunSimulationButton() {
		int X_SIZE = 0;
		int Y_SIZE = 6;

	    submitButton = new JButton("RUN SIMULATION");
	    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

	    submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	processAndStartSimulation();
            }
        });
		parameterBox.add(submitButton);
		parameterBox.add(Box.createRigidArea(new Dimension(X_SIZE, Y_SIZE)));
	}

	
	
	/**
	 * Get user input from the various text fields on the GUI.
	 * Parses strings and converts negative values to positives.
	 * @author Samuel Hammill
	 */	
	private void getTextFieldInputs() {
		double ONE_HUNDRED_PERCENT = 1.0;
		
		seed = Math.abs(Integer.parseInt(seedText.getText()));
		carProb = Math.abs(Double.parseDouble(carProbText.getText()));
		smallCarProb = Math.abs(Double.parseDouble(smallCarProbText.getText()));
		motorCycleProb =(Math.abs(Double.parseDouble( motorCycleProbText.getText())));
		meanStay = Math.abs(Double.parseDouble(meanStayText.getText()));
		staySD = Math.abs(Double.parseDouble(staySDText.getText()));
		maxCarSpaces = Math.abs(Integer.parseInt( maxCarSpacesText.getText()));
		maxSmallCarSpaces = Math.abs(Integer.parseInt(maxSmallCarSpacesText.getText()));
		maxMotorCycleSpaces = Math.abs(Integer.parseInt(maxMotorCycleSpacesText.getText()));
		maxQueueSize = Math.abs(Integer.parseInt(maxQueueSizeText.getText()));
		
		// Catch probability over 100% (1.0) and set it back to 100% (1.0) before running the simulation.
		if (carProb > ONE_HUNDRED_PERCENT) {
			carProb = ONE_HUNDRED_PERCENT;
		}
		if (smallCarProb > ONE_HUNDRED_PERCENT) {
			smallCarProb = ONE_HUNDRED_PERCENT;
		}
		if (motorCycleProb > ONE_HUNDRED_PERCENT) {
			motorCycleProb = ONE_HUNDRED_PERCENT;
		}	
	}
	
	
	/**
	 * Updates text fields with sanitised values for user feedback.
	 * @author Samuel Hammill
	 */	
	private void updateTextFields() {
		carProbText.setText(String.valueOf(carProb));
		smallCarProbText.setText(String.valueOf(smallCarProb));
		motorCycleProbText.setText(String.valueOf(motorCycleProb));
		seedText.setText(String.valueOf(seed));
		meanStayText.setText(String.valueOf(meanStay));
		staySDText.setText(String.valueOf(staySD));
		maxCarSpacesText.setText(String.valueOf(maxCarSpaces));
		maxSmallCarSpacesText.setText(String.valueOf(maxSmallCarSpaces));
		maxMotorCycleSpacesText.setText(String.valueOf(maxMotorCycleSpaces));
		maxQueueSizeText.setText(String.valueOf(maxQueueSize));
	}
	
	/**
	 * Setup button to display the first chart (All data)
	 * When pressed, hides/displays appropriate charts.
	 * @author Samuel Hammill
	 */	
	private void setupChartButtonAllData() {
		int X_COORD = 710;
		int Y_COORD = 150;
		int SIZE_X = 90;
		int SIZE_Y = 30;
		
		JButton chartButton = new JButton("All Data");
	    chartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

	    chartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	toggleChartAllData();
            }
        });
	    chartButton.setBounds(X_COORD, Y_COORD, SIZE_X, SIZE_Y);
	    this.add(chartButton);
	}
	
	/**
	 * Setup button to display the second chart (Vehicles)
	 * When pressed, hides/displays appropriate charts.
	 * @author Samuel Hammill
	 */	
	private void setupChartButtonVehicles() {
		int X_COORD = 710;
		int Y_COORD = 190;
		int SIZE_X = 90;
		int SIZE_Y = 30;
		
		JButton chartButton = new JButton("Vehicles");
	    chartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

	    chartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	toggleChartVehicles();
            }
        });
	    chartButton.setBounds(X_COORD, Y_COORD, SIZE_X, SIZE_Y);
	    this.add(chartButton);
	}
	
	/**
	 * Setup button to display the third chart (Bar Graph)
	 * When pressed, hides/displays appropriate charts.
	 * @author Samuel Hammill
	 */
	private void setupChartButtonBarGraph() {
		int X_COORD = 710;
		int Y_COORD = 230;
		int SIZE_X = 90;
		int SIZE_Y = 30;
		
		JButton chartButton = new JButton("Bar Chart");
	    chartButton.setAlignmentX(Component.CENTER_ALIGNMENT);

	    chartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	toggleChartBarGraph();
            }
        });
	    chartButton.setBounds(X_COORD, Y_COORD, SIZE_X, SIZE_Y);
	    this.add(chartButton);
	}
	
	
	/**
	 * Hides all charts, besides AllData which is set to visible.
	 * @author Samuel Hammill
	 */
	private void toggleChartAllData() {
		chartPanelAllData.setVisible(true);
		chartPanelVehicles.setVisible(false);
		chartPanelBarGraph.setVisible(false);
	}
	
	
	/**
	 * Hides all charts, besides Vehicles which is set to visible.
	 * @author Samuel Hammill
	 */
	private void toggleChartVehicles() {
		chartPanelAllData.setVisible(false);
		chartPanelVehicles.setVisible(true);
		chartPanelBarGraph.setVisible(false);
	}
	
	
	/**
	 * Hides all charts, besides the Bar Graph which is set to visible.
	 * @author Samuel Hammill
	 */
	private void toggleChartBarGraph() {
		chartPanelAllData.setVisible(false);
		chartPanelVehicles.setVisible(false);
		chartPanelBarGraph.setVisible(true);
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
}
