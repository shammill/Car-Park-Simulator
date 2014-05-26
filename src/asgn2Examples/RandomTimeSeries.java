/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Examples 
 * 11/05/2014
 * 
 */
package asgn2Examples;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/** 
 * Example code based on the Stack Overflow example and the 
 * standard JFreeChart demos showing the construction of a time series 
 * data set. Some of the data creation code is clearly a quick hack
 * and should not be taken as indicative of the required coding style. 
 * @see http://stackoverflow.com/questions/5048852
 * 
 *  */
@SuppressWarnings("serial")
public class RandomTimeSeries extends JPanel {

	ArrayList<Integer> parkedVehicles;
	ArrayList<Integer> parkedCars;
	ArrayList<Integer> parkedSmallCars;
	ArrayList<Integer> parkedMotorCycles;
	
    /**
     * Constructor shares the work with the run method. 
     * @param title Frame display title
     */
    public RandomTimeSeries(ArrayList<Integer> parkedVehicles, ArrayList<Integer> parkedCars, ArrayList<Integer> parkedSmallCars, ArrayList<Integer> parkedMotorCycles) {

    	this.parkedVehicles = parkedVehicles;
    	this.parkedCars = parkedCars;
    	this.parkedSmallCars = parkedSmallCars;
    	this.parkedMotorCycles = parkedMotorCycles;
    	
        final TimeSeriesCollection dataset = createTimeSeriesData(); 
        JFreeChart chart = createChart(dataset);
        this.add(new ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }

    /**
     * Private method creates the dataset. Lots of hack code in the 
     * middle, but you should use the labelled code below  
	 * @return collection of time series for the plot 
	 */
	private TimeSeriesCollection createTimeSeriesData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection(); 
		TimeSeries vehTotal = new TimeSeries("Total Vehicles");
		TimeSeries carTotal = new TimeSeries("Total Cars"); 
		TimeSeries smallCarTotal = new TimeSeries("Total Small Cars"); 
		TimeSeries mcTotal = new TimeSeries("MotorCycles");

		
		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();
		
		int vehicles = 0;
		int cars = 1;
		int smallCars = 1;
		int motorCycles = 0;
		
			//Hack loop to make it interesting. Grows for half of it, then declines
			for (int i=0; i<=18*60; i++) {
				//These lines are important 
				cal.set(2014,0,1,6,i);
		        Date timePoint = cal.getTime();
		        
				if (!parkedVehicles.isEmpty()) {
					vehicles = parkedVehicles.get(i);
					cars = parkedCars.get(i);
			        smallCars = parkedSmallCars.get(i);
			        motorCycles = parkedMotorCycles.get(i);
			        
				}
		        //HACK ENDS
		        
		        //This is important - steal it shamelessly 
				mcTotal.add(new Minute(timePoint),motorCycles);
				carTotal.add(new Minute(timePoint),cars);
				smallCarTotal.add(new Minute(timePoint),smallCars);
				vehTotal.add(new Minute(timePoint),vehicles);
			}

		
		//Collection
		tsc.addSeries(vehTotal);
		tsc.addSeries(carTotal);
		tsc.addSeries(smallCarTotal);
		tsc.addSeries(mcTotal);
		return tsc;
	}
	
	/**
	 * Utility method to implement a <a href="http://en.wikipedia.org/wiki/Bernoulli_trial">Bernoulli Trial</a>, 
	 * a coin toss with two outcomes: success (probability successProb) and failure (probability 1-successProb)
	 * @param successProb double holding the success probability 
	 * @param rng Random object 
	 * @return true if trial was successful, false otherwise
	 */
	private boolean randomSuccess(double successProb,Random rng) {
		boolean result = rng.nextDouble() <= successProb;
		return result;
	}

    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "", "hh:mm", "Vehicles", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return result;
    }
/*
    /**
     * Simple main GUI runner 
     * @param args ignored 
     *//*
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RandomTimeSeries demo = new RandomTimeSeries(TITLE);
                //demo.pack();
                //RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
            }
        });
    }*/
}