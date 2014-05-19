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
import java.util.Calendar;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
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
public class GUISimulator extends JFrame implements Runnable {
	
	// Constants
	private int WIDTH = 1024;
	private int HEIGHT = 786;
	
	// Parameter fields, log, and input button.
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
	 * @author Laurence Mccabe
	 */
	private void initialiseUI() {
		
		// Create and set frame parameters.
		JFrame frame = new JFrame("Car Park Simulator");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		// Create our panels to manage our customisable parameters.
	    JPanel parameterBox = new JPanel();
	    parameterBox.setBounds(75, 15, 400, 300);
	    parameterBox.setLayout(new BoxLayout(parameterBox, BoxLayout.PAGE_AXIS));
		parameterBox.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Simulation Parameters", CENTER, TOP));
	    
	    JPanel parameters = new JPanel();
	    parameters.setLayout(new GridLayout(0, 2, 40, 15));
	    parameters.setBorder(new EmptyBorder(0, 10 , 10, 10));
	    
	    JPanel parametersLeft = new JPanel();
	    JPanel parametersRight = new JPanel();
	    parametersLeft.setLayout(new GridLayout(0, 1));
	    parametersRight.setLayout(new GridLayout(0, 1));
	    
		parameterBox.add(parameters);
		parameters.add(parametersLeft);
		parameters.add(parametersRight);

	    
		// Create a panel to display our log. Add a Text Area to go into it.
	    JPanel logArea = new JPanel();
		logArea.setBounds(400, 10, 770, 730);
		
		logText = new JTextArea(45,38);
		logText.setEditable(false);
		logText.setLineWrap(true);

	    JScrollPane scrollPane = new JScrollPane(logText);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    logArea.add(scrollPane);
		
	    
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
	    
	    
		// Setup parameter text fields and labels and 
	    // populate them with CLI arguments, or default values.
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
	    
	    JLabel seedLabel = new JLabel("Random Seed:");
	    JLabel carProbLabel = new JLabel("Car Probabilty: (0-1)");
	    JLabel smallCarProbLabel = new JLabel("Small Car Probabilty: (0-1)");
	    JLabel motorCycleProbLabel = new JLabel("Motor Cycle Probabilty: (0-1)");
	    JLabel meanStayLabel = new JLabel("Average Stay Time:");
	    JLabel staySDLabel = new JLabel("Stay Time SD:");
	    JLabel maxCarSpacesLabel = new JLabel("Max Car Spaces:");
	    JLabel maxSmallCarSpacesLabel = new JLabel("Max Small Car Spaces:");
	    JLabel maxMotorCycleSpacesLabel = new JLabel("Max Motor Cycle Spaces:");
	    JLabel maxQueueSizeLabel = new JLabel("Max Queue Size:");
	    
	    
	    // Add the individual parameter boxes and labels to the parameter panels.
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
	    // End of Parameter boxes and labels.
	    
	    
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

		// Add our components onto the frame and render it visible.
		frame.add(parameterBox);
		frame.add(logArea);
		frame.setVisible(true);
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
			logText.append(carPark.getStatus(time));
		}
		log.finalise(carPark);
		finaliseGUI();
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
	 * Helper method to determine if new vehicles are permitted
	 * @param time int holding current simulation time
	 * @return true if new vehicles permitted, false if not allowed due to simulation constraints. 
	 */
	private boolean newVehiclesAllowed(int time) {
		boolean allowed = (time >=1);
		return allowed && (time <= (Constants.CLOSING_TIME - 60));
	}
	
	
	
	/**
	 * @param args
	 * @throws SimulationException 
	 * @throws IOException 
	 */
	/*public static void main(String[] args) throws SimulationException, IOException {
		GUISimulator g = new GUISimulator("l");
		CarPark cp = new CarPark(20,5,5,5);
		Simulator sim = new Simulator(1,1,1,1,1,1);
		g.initialEntry(cp, sim);
		g.finalise(cp);
		// TODO Auto-generated method stub

	}*/
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
