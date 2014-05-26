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
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/** 
 * A panel that is responsible for displaying our simulation 
 * data in the form of a chart. 
 * 
 * */
@SuppressWarnings("serial")
public class ChartPanel extends JPanel {

	ArrayList<Integer> parkedVehicles;
	ArrayList<Integer> parkedCars;
	ArrayList<Integer> parkedSmallCars;
	ArrayList<Integer> parkedMotorCycles;
	ArrayList<Integer> vehiclesInQueue;
	ArrayList<Integer> vehiclesArchived;
	ArrayList<Integer> totalVehicles;
	ArrayList<Integer> dissatisfiedVehicles;
	
	
    /**
     * Constructor that gets the data from the simulation used to display
     * simulation information. Also sets up objects for the display.
     * @param title Frame display title
     */
    public ChartPanel(ArrayList<Integer> parkedVehicles, ArrayList<Integer> parkedCars, ArrayList<Integer> parkedSmallCars, 
    					ArrayList<Integer> parkedMotorCycles, ArrayList<Integer> vehiclesInQueue, ArrayList<Integer> vehiclesArchived,
    					ArrayList<Integer> totalVehicles, ArrayList<Integer> dissatisfiedVehicles) {

    	this.parkedVehicles = parkedVehicles;
    	this.parkedCars = parkedCars;
    	this.parkedSmallCars = parkedSmallCars;
    	this.parkedMotorCycles = parkedMotorCycles;
    	this.vehiclesInQueue = vehiclesInQueue;
    	this.vehiclesArchived = vehiclesArchived;
    	this.totalVehicles = totalVehicles;
    	this.dissatisfiedVehicles = dissatisfiedVehicles;
    	
        final TimeSeriesCollection dataset = createTimeSeriesAllData(); 
        JFreeChart chart = createTimeSeriesChart(dataset);
        this.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }
    
    
    /**
     * Constructor that gets the data from the simulation used to display
     * simulation information. Also sets up objects for the display.
     * @param title Frame display title
     */
    public ChartPanel(ArrayList<Integer> parkedVehicles, ArrayList<Integer> parkedCars, ArrayList<Integer> parkedSmallCars, 
    					ArrayList<Integer> parkedMotorCycles, ArrayList<Integer> vehiclesInQueue) {

    	this.parkedVehicles = parkedVehicles;
    	this.parkedCars = parkedCars;
    	this.parkedSmallCars = parkedSmallCars;
    	this.parkedMotorCycles = parkedMotorCycles;
    	this.vehiclesInQueue = vehiclesInQueue;
    	
        final TimeSeriesCollection dataset = createTimeSeriesVehicleData(); 
        JFreeChart chart = createTimeSeriesChart(dataset);
        this.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }
    
    
    /**
     * Constructor that gets the data from the simulation used to display
     * simulation information. Also sets up objects for the display.
     * @param title Frame display title
     */
    public ChartPanel(ArrayList<Integer> totalVehicles, ArrayList<Integer> dissatisfiedVehicles) {

    	this.totalVehicles = totalVehicles;
    	this.dissatisfiedVehicles = dissatisfiedVehicles;
    	
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (!totalVehicles.isEmpty()) {
	        dataset.addValue(dissatisfiedVehicles.get(dissatisfiedVehicles.size()-1), "Dissatisfied Customers", "");
	        dataset.addValue(totalVehicles.get(totalVehicles.size()-1), "Total Vehicles", "");
        }
        JFreeChart chart = createBarChart(dataset);
        this.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        this.add(btnPanel, BorderLayout.SOUTH);
    }
    

    /**
     * Private method creates the dataset for each time point
	 * @return collection of time series for the plot
	 * @author Samuel Hammill
	 */
	private TimeSeriesCollection createTimeSeriesAllData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries numVehicles = new TimeSeries("Total Vehicles");
		TimeSeries totalParked = new TimeSeries("Parked Vehicles");
		TimeSeries totalCars = new TimeSeries("Cars"); 
		TimeSeries totalSmallCars = new TimeSeries("Small Cars"); 
		TimeSeries totalMotorCycles = new TimeSeries("MotorCycles");
		TimeSeries totalQueue = new TimeSeries("Queue");
		TimeSeries totalDissatisfied = new TimeSeries("Dissatisfied");
		TimeSeries totalArchived = new TimeSeries("Archived");

		int vehicles = 1;
		int parked = 1;
		int cars = 1;
		int smallCars = 0;
		int motorCycles = 0;
		int queued = 0;
		int dissatisfied = 0;
		int archived = 0;

		
		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();
				
			// Loop through all time points and plot our graph.
			for (int i=0; i<=18*60; i++) {
				cal.set(2014,0,1,6,i);
		        Date timePoint = cal.getTime();
		        
		        //If there is data plot it, otherwise, plot with default values 
				if (!parkedVehicles.isEmpty()) {
					vehicles = totalVehicles.get(i);
					parked = parkedVehicles.get(i);
					cars = parkedCars.get(i);
			        smallCars = parkedSmallCars.get(i);
			        motorCycles = parkedMotorCycles.get(i);
			        queued = vehiclesInQueue.get(i);
			        dissatisfied = dissatisfiedVehicles.get(i);
			        archived = vehiclesArchived.get(i);
				}
				
				
		        // Add the points to the graph.
				numVehicles.add(new Minute(timePoint),vehicles);
				totalParked.add(new Minute(timePoint),parked);
				totalCars.add(new Minute(timePoint),cars);
				totalSmallCars.add(new Minute(timePoint),smallCars);
				totalMotorCycles.add(new Minute(timePoint),motorCycles);
				totalQueue.add(new Minute(timePoint),queued);
				totalDissatisfied.add(new Minute(timePoint),dissatisfied);
				totalArchived.add(new Minute(timePoint),archived);
			}
		
		//Collection
		tsc.addSeries(totalDissatisfied);
		tsc.addSeries(totalCars);
		tsc.addSeries(totalArchived);
		tsc.addSeries(totalQueue);
		tsc.addSeries(totalSmallCars);
		tsc.addSeries(totalMotorCycles);
		tsc.addSeries(totalParked);
		tsc.addSeries(numVehicles);
		
		return tsc;
	}
	
	
    /**
     * Private method creates the dataset for each time point
	 * @return collection of time series for the plot
	 * @author Samuel Hammill
	 */
	private TimeSeriesCollection createTimeSeriesVehicleData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		TimeSeries totalParked = new TimeSeries("Parked Vehicles");
		TimeSeries totalCars = new TimeSeries("Cars"); 
		TimeSeries totalSmallCars = new TimeSeries("Small Cars"); 
		TimeSeries totalMotorCycles = new TimeSeries("MotorCycles");
		TimeSeries totalQueue = new TimeSeries("Queue");

		int parked = 1;
		int cars = 1;
		int smallCars = 0;
		int motorCycles = 0;
		int queued = 0;

		
		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();
				
			// Loop through all time points and plot our graph.
			for (int i=0; i<=18*60; i++) {
				cal.set(2014,0,1,6,i);
		        Date timePoint = cal.getTime();
		        
		        //If there is data plot it, otherwise, plot with default values 
				if (!parkedVehicles.isEmpty()) {
					parked = parkedVehicles.get(i);
					cars = parkedCars.get(i);
			        smallCars = parkedSmallCars.get(i);
			        motorCycles = parkedMotorCycles.get(i);
			        queued = vehiclesInQueue.get(i);
				}
				
				
		        // Add the points to the graph.
				totalParked.add(new Minute(timePoint),parked);
				totalCars.add(new Minute(timePoint),cars);
				totalSmallCars.add(new Minute(timePoint),smallCars);
				totalMotorCycles.add(new Minute(timePoint),motorCycles);
				totalQueue.add(new Minute(timePoint),queued);
			}
		
		//Collection
		tsc.addSeries(totalParked);
		tsc.addSeries(totalCars);
		tsc.addSeries(totalSmallCars);
		tsc.addSeries(totalMotorCycles);
		tsc.addSeries(totalQueue);
		
		return tsc;
	}
	

    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createTimeSeriesChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "", "hh:mm", "Vehicles", dataset, true, true, false);
        
        final XYPlot plot = chart.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return chart;
    }
    
    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createBarChart(final DefaultCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart("End Report", "", "Number of Vehicles", dataset, PlotOrientation.VERTICAL, true, false, false);
        
        final CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        return chart;
    } 
    
    
    
}