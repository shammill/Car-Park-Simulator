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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
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
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
	
	// Constants
	private int WIDTH = 1024;
	private int HEIGHT = 786;
	
	private JTextArea logText;
	
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
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(int maxCarSpaces, int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize, int seed, 
		double meanStay, double staySD, double carProb, double smallCarProb, double motorCycleProb) throws HeadlessException {
		
		// Take Simulation parameters so we can display them in our GUI.
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
		
		try {
			startSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
	}
	
	private void initialiseUI() {
		
		// Create and set frame parameters.
		JFrame frame = new JFrame("Car Park Simulator");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		// Create our panels and set location.
	    JPanel parameterBox = new JPanel();
	    parameterBox.setLayout(new BoxLayout(parameterBox, BoxLayout.PAGE_AXIS));
	    
	    parameterBox.setBounds(75, 15, 400, 300);
		parameterBox.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Simulation Parameters", CENTER, TOP));
	    
	    JPanel parameters = new JPanel();
	    parameters.setLayout(new GridLayout(0, 2, 40, 15));
	    parameters.setBorder(new EmptyBorder(0, 10 , 10, 10));
	    
	    JPanel parametersLeft = new JPanel();
	    parametersLeft.setLayout(new GridLayout(0, 1));
	    
	    JPanel parametersRight = new JPanel();
	    parametersRight.setLayout(new GridLayout(0, 1));
	    
	    JPanel logArea = new JPanel();
		logArea.setBounds(400, 10, 770, 730);

		parameterBox.add(parameters);
		parameters.add(parametersLeft);
		parameters.add(parametersRight);

	    
		// Add components to panels
		logText = new JTextArea(45,38);
		logText.setEditable(false);
		logText.setLineWrap(true);

	    JScrollPane scrollPane = new JScrollPane(logText);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    logArea.add(scrollPane);
		
	    
		// Setup parameter text fields and labels.
	    JTextField seedText = new JTextField(String.valueOf(seed));
	    JTextField carProbText = new JTextField(String.valueOf(carProb));
	    JTextField smallCarProbText = new JTextField(String.valueOf(smallCarProb));
	    JTextField motorCycleProbText = new JTextField(String.valueOf(motorCycleProb));
	    JTextField meanStayText = new JTextField(String.valueOf(meanStay));
	    JTextField staySDText = new JTextField(String.valueOf(staySD));
	    JTextField maxCarSpacesText = new JTextField(String.valueOf(maxCarSpaces));
	    JTextField maxSmallCarSpacesText = new JTextField(String.valueOf(maxSmallCarSpaces));
	    JTextField maxMotorCycleSpacesText = new JTextField(String.valueOf(maxMotorCycleSpaces));
	    JTextField maxQueueSizeText = new JTextField(String.valueOf(maxQueueSize));
	    
	    /*seedText.setMaximumSize(new Dimension(200,200));
	    carProbText.setMaximumSize(new Dimension(70,70));
	    smallCarProbText.setMaximumSize(new Dimension(70,70));
	    motorCycleProbText.setMaximumSize(new Dimension(70,70));
	    meanStayText.setMaximumSize(new Dimension(200,200));
	    staySDText.setMaximumSize(new Dimension(200,200));
	    maxCarSpacesText.setMaximumSize(new Dimension(200,200));
	    maxSmallCarSpacesText.setMaximumSize(new Dimension(200,200));
	    maxMotorCycleSpacesText.setMaximumSize(new Dimension(200,200));
	    maxQueueSizeText.setMaximumSize(new Dimension(200,200));*/
	    
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
	
	    JLabel seedLabel = new JLabel("Random Seed:");
	    JLabel carProbLabel = new JLabel("Car Probabilty:");
	    JLabel smallCarProbLabel = new JLabel("Small Car Probabilty:");
	    JLabel motorCycleProbLabel = new JLabel("Motor Cycle Probabilty:");
	    JLabel meanStayLabel = new JLabel("Average Stay Time:");
	    JLabel staySDLabel = new JLabel("Stay Time SD:");
	    JLabel maxCarSpacesLabel = new JLabel("Max Car Spaces:");
	    JLabel maxSmallCarSpacesLabel = new JLabel("Max Small Car Spaces:");
	    JLabel maxMotorCycleSpacesLabel = new JLabel("Max Motor Cycle Spaces:");
	    JLabel maxQueueSizeLabel = new JLabel("Max Queue Size:");
		
	    JButton submitButton = new JButton("Submit");
	    submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


		// Add panels to frame.
		parametersLeft.add(seedLabel);
		parametersLeft.add(seedText);

		parametersRight.add(maxQueueSizeLabel);
		parametersRight.add(maxQueueSizeText);
		
		parametersLeft.add(carProbLabel);
		parametersLeft.add(carProbText);

		parametersRight.add(maxCarSpacesLabel);
		parametersRight.add(maxCarSpacesText);
		
		
		parametersLeft.add(smallCarProbLabel);
		parametersLeft.add(smallCarProbText);

		parametersRight.add(maxSmallCarSpacesLabel);
		parametersRight.add(maxSmallCarSpacesText);

		
		parametersLeft.add(motorCycleProbLabel);
		parametersLeft.add(motorCycleProbText);
		
		parametersRight.add(maxMotorCycleSpacesLabel);
		parametersRight.add(maxMotorCycleSpacesText);

		parametersLeft.add(meanStayLabel);
		parametersLeft.add(meanStayText);

		parametersRight.add(staySDLabel);
		parametersRight.add(staySDText);


		//parameters.add(submitButton);
		parameterBox.add(submitButton);
		parameterBox.add(Box.createRigidArea(new Dimension(0, 6)));
		
		frame.add(parameterBox);
		frame.add(logArea);
		
		frame.setVisible(true);
	}
	

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
	 	initialEntry();
		
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
			logEntry(time, carPark);
		}
		log.finalise(carPark);
	}
	
	
	public void initialEntry() throws IOException {
		logText.append("Start of Simulation\n\n");
	}

	public void logEntry(int time,CarPark cp) throws IOException {
		String statusString = cp.getStatus(time);
		logText.append(statusString);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

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
