package uDIY;

import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JPanel {

	/**
	 * Graph Class
	 * Creates a graph of the results using the JFreeChart library
	 * @author Tyler Shupack
	 * @version 12/08/2017
	 */
	private static final long serialVersionUID = 1090161090933628232L;
	
	private String projectName;
	
	private double totalCost;
	private double breakEven;
	
	/**
	 * Default Constructor
	 * Creates a default graph that will not display any values.
	 * @author Tyler Shupack
	 * @version 12/08/2017
	 */
	public Graph() {
		
		final XYDataset dataset = createDataset();
	    final JFreeChart chart = createChart(dataset);
	    final ChartPanel chartPanel = new ChartPanel(chart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));
	    add(chartPanel);
	}

	/**
	 * Enhanced Constructor
	 * Creates a graph based on the totalCost and breakEven values from the data class.
	 * @author Tyler Shupack
	 * @version 12/08/2017
	 */
	public Graph(String title, double totalC, double breakE) {
	   
		projectName = title;
		totalCost = totalC;
		breakEven = breakE;
		
       final XYDataset dataset = createDataset();
       final JFreeChart chart = createChart(dataset);
       final ChartPanel chartPanel = new ChartPanel(chart);
       chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));
       add(chartPanel);

   }

	/**
	 * Creates the data set
	 * Makes a data set of all lines needed to be graphed/vertices to connect to each other based 
	 * on series.
	 * @author Tyler Shupack
	 * @version 12/08/2017
	 */
	private XYDataset createDataset() {
       
       final XYSeries series1 = new XYSeries("Savings");
       series1.add(0.0, 0.0);
       series1.add(breakEven, totalCost);
       series1.add(breakEven * 1.5, totalCost * 1.5);

       final XYSeries series2 = new XYSeries("Project Cost");
       series2.add(0.0, totalCost);
       series2.add(breakEven, totalCost);
       series2.add(breakEven * 1.5, totalCost);
       
       final XYSeriesCollection dataset = new XYSeriesCollection();
       dataset.addSeries(series1);
       dataset.addSeries(series2);
               
       return dataset;
       
   }
   
	/**
	 * Creates the chart
	 * Sets up the chart options for when the graph is created.
	 * @author Tyler Shupack
	 * @version 12/08/2017
	 */
	private JFreeChart createChart(final XYDataset dataset) {
       
       final JFreeChart chart = ChartFactory.createXYLineChart(
           projectName + " Project Graph",      
           "Time (Days)",                   
           "Total Saved ($)",                     
           dataset,                  
           PlotOrientation.VERTICAL,
           true,                    
           true,                    
           false                    
       );

       chart.setBackgroundPaint(Color.white);
       
       //Sets all of the background/axis line display options
       final XYPlot plot = chart.getXYPlot();
       plot.setBackgroundPaint(Color.lightGray);
       plot.setDomainGridlinePaint(Color.BLACK);
       plot.setRangeGridlinePaint(Color.BLACK);
       
       //Determines which line/vertices are rendered on the graph
       final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       renderer.setSeriesLinesVisible(0, true);
       renderer.setSeriesShapesVisible(1, true);
       plot.setRenderer(renderer);

       //Determines how the axis will display
       final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
               
       return chart;
       
   }
   
}