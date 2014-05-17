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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
	private int HEIGHT = 768;
	
	// JPanels
	private JPanel parameters;
	private JPanel logArea;
	
	// Textboxes for Simulation Components
	private JTextField seedText;
	private JTextField carProbText;
	private JTextField smallCarProbText;
	private JTextField motorCycleProbText;
	private JTextField meanStayText;
	private JTextField staySDText;
	private JTextField maxCarSpacesText;
	private JTextField maxSmallCarSpacesText;       
	private JTextField maxMotorCycleSpacesText;
	private JTextField maxQueueSizeText;
	
	private JTextArea logText;
	
	// Labels for text boxes
	private JLabel seedLabel;
	private JLabel carProbLabel;
	private JLabel smallCarProbLabel;
	private JLabel motorCycleProbLabel;
	private JLabel meanStayLabel;
	private JLabel staySDLabel;
	private JLabel maxCarSpacesLabel;
	private JLabel maxSmallCarSpacesLabel;
	private JLabel maxMotorCycleSpacesLabel;
	private JLabel maxQueueSizeLabel;
	
	// Other items.
	private JScrollPane scroll;
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
		initialiseComponents();
		
		try {
			startSimulation();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} 
	}
	
	private void initialiseComponents() {
		
		// Set window parameters.
		setTitle("Car Park Simulator");
	    setVisible(true);
	    setSize(WIDTH, HEIGHT);
	    setLayout(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
		// Create our panels and set location.
		parameters = new JPanel();
		logArea = new JPanel();
		logArea.setBounds(400, 10, 620, 700);
		
		parameters.setBounds(10, 10, 190, 301);
		parameters.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1, true), "Simulation Parameters", CENTER, TOP));
		
		// Add components to panels
		logText = new JTextArea(40,40);
		logText.setEditable(false);
		scroll = new JScrollPane(logText);
		//logText.add(scroll, positionConstraints(Position.TOPCENTRE, 20));
		
		logText.setLineWrap(true);
	    
		// Setup parameter text fields and labels.
		seedText = new JTextField(String.valueOf(seed));
		carProbText = new JTextField(String.valueOf(carProb));
		smallCarProbText = new JTextField(String.valueOf(smallCarProb));
		motorCycleProbText = new JTextField(String.valueOf(motorCycleProb));
		meanStayText = new JTextField(String.valueOf(meanStay));
		staySDText = new JTextField(String.valueOf(staySD));
		maxCarSpacesText = new JTextField(String.valueOf(maxCarSpaces));
		maxSmallCarSpacesText = new JTextField(String.valueOf(maxSmallCarSpaces));
		maxMotorCycleSpacesText = new JTextField(String.valueOf(maxMotorCycleSpaces));
		maxQueueSizeText = new JTextField(String.valueOf(maxQueueSize));
	
		seedLabel = new JLabel("Random Seed:");
		carProbLabel = new JLabel("Car Probabilty:");
		smallCarProbLabel = new JLabel("Small Car Probabilty:");
		motorCycleProbLabel = new JLabel("Motor Cycle Probabilty:");
		meanStayLabel = new JLabel("Average Stay Time:");
		staySDLabel = new JLabel("Stay Time SD:");
		maxCarSpacesLabel = new JLabel("Max Car Spaces:");
		maxSmallCarSpacesLabel = new JLabel("Max Small Car Spaces:");
		maxMotorCycleSpacesLabel = new JLabel("Max Motor Cycle Spaces:");
		maxQueueSizeLabel = new JLabel("Max Queue Size:");
		
		//seedLabel.setHorizontalAlignment(JTextField.RIGHT);
		
		submitButton = new JButton("Submit");
		
		// Add panels to frame.
		add(parameters);
		add(logArea);
		logArea.add(logText);
		
		
		parameters.add(seedLabel);
		parameters.add(seedText);
		parameters.add(carProbLabel);
		parameters.add(carProbText);
		parameters.add(smallCarProbLabel);
		parameters.add(smallCarProbText);
		parameters.add(motorCycleProbLabel);
		parameters.add(motorCycleProbText);
		parameters.add(meanStayLabel);
		parameters.add(meanStayText);
		parameters.add(staySDLabel);
		parameters.add(staySDText);
		parameters.add(maxCarSpacesLabel);
		parameters.add(maxCarSpacesText);
		parameters.add(maxSmallCarSpacesLabel);
		parameters.add(maxSmallCarSpacesText);
		parameters.add(maxMotorCycleSpacesLabel);
		parameters.add(maxMotorCycleSpacesText);
		parameters.add(maxQueueSizeLabel);
		parameters.add(maxQueueSizeText);
		parameters.add(maxQueueSizeText);
		parameters.add(submitButton);

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
	 	initialEntry(carPark, sim);
		
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
		finalise(carPark);
	}
	
	
	public void finalise(CarPark cp) throws IOException {
		logText.append("\n" + getLogTime() + ": End of Simulation\n");
		logText.append(cp.finalState());
	//	String r = cp.finalState();
	//	gui = new GUISimulator(r);
	}
	
	public void initialEntry(CarPark cp,Simulator sim) throws IOException {
		logText.append(getLogTime() + ": Start of Simulation\n");
		logText.append(sim.toString() + "\n");
		logText.append(cp.initialState() + "\n\n");
	}

	public void logEntry(int time,CarPark cp) throws IOException {
		logText.append(cp.getStatus(time));
	}
	
	private String getLogTime() {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		return timeLog;
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
