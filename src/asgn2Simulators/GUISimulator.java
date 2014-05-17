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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;



/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
	 JPanel jp = new JPanel();
     JLabel jl = new JLabel();
     JTextField jt = new JTextField(30);
     JTextArea status = new JTextArea(20,20);
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String getstatuslog) throws HeadlessException {
     setTitle(getstatuslog);
     setVisible(true);
     setSize(400,200);
     setDefaultCloseOperation(EXIT_ON_CLOSE);
     jp.add(jt);
     jp.add(status);
     add(jp);
    // JScrollPane scrollPane = 
 	//	    new JScrollPane(status,
 		//                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
 		//                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
 
     
	}
	
	public void finalise(CarPark cp) throws IOException {
		status.append("\n" + getLogTime() + ": End of Simulation\n");
		status.append(cp.finalState());
	//	String r = cp.finalState();
	//	gui = new GUISimulator(r);
	}
	
	public void initialEntry(CarPark cp,Simulator sim) throws IOException {
		status.append(getLogTime() + ": Start of Simulation\n");
		status.append(sim.toString() + "\n");
		status.append(cp.initialState() + "\n\n");
	}

	public void logEntry(int time,CarPark cp) throws IOException {
		status.append(cp.getStatus(time));
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

}
