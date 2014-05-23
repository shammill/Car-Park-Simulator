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
package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/** 
 * Example code based on the Stack Overflow example and the 
 * standard JFreeChart demos showing the construction of a time series 
 * data set. Some of the data creation code is clearly a quick hack
 * and should not be taken as indicative of the required coding style. 
 * @see http://stackoverflow.com/questions/5048852
 * 
 *  */
@SuppressWarnings("serial")
public class ChartPanel extends JPanel {

	ArrayList<Integer> parkedVehicles;
	ArrayList<Integer> parkedCars;
	ArrayList<Integer> parkedSmallCars;
	ArrayList<Integer> parkedMotorCycles;
	
    /**
     * Constructor shares the work with the run method. 
     * @param title Frame display title
     */
    public ChartPanel(ArrayList<Integer> parkedVehicles, ArrayList<Integer> parkedCars, ArrayList<Integer> parkedSmallCars, ArrayList<Integer> parkedMotorCycles) {

    	this.parkedVehicles = parkedVehicles;
    	this.parkedCars = parkedCars;
    	this.parkedSmallCars = parkedSmallCars;
    	this.parkedMotorCycles = parkedMotorCycles;
    	
        final TimeSeriesCollection dataset = createTimeSeriesData(); 
        JFreeChart chart = createChart(dataset);
        this.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }

    /**
     * Private method creates the dataset. Lots of hack code in the 
     * middle, but you should use the labelled code below  
	 * @return collection of time series for the plot
	 * @author Samuel Hammill
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
		
			// Loop through all time points and plot our graph.
			for (int i=0; i<=18*60; i++) {
				cal.set(2014,0,1,6,i);
		        Date timePoint = cal.getTime();
		        
		        //If there is data plot it, otherwise, plot with default values 
				if (!parkedVehicles.isEmpty()) {
					vehicles = parkedVehicles.get(i);
					cars = parkedCars.get(i);
			        smallCars = parkedSmallCars.get(i);
			        motorCycles = parkedMotorCycles.get(i);   
				}
		        
		        // Add the points to the graph.
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
}