import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Control class that will hold all four functions of options for the GUI interface.
 * The back end code will take care of the data and organize it in a way for the user. 
 * @author David Rodriguez, Igor Persisic, Frankincense Ramesh
 *
 */
public class loadRoster {

	protected sourceData Utility = new sourceData();	// make an object from the sourceData class to store data
	private String[][] data; // store final data for rows for Main class use
	private String[] header; // store final header for columns for Main class use
	private JFreeChart chart; // store final chart for chart for Main class use
	
	/**
	 * Store the current 2D array for rows
	 * @param a set current String 2D array
	 */
	public void setData(String[][] a) {
		this.data = a;
	}
	
	/**
	 * Returns the String 2D array to the main for implementation
	 * @return String 2D array data for use in table or chart
	 */
	public String[][] getData() {
		 return data;
	}
	
	/**
	 * Store the current 1D array for columns/headers
	 * @param b set current String 1D array 
	 */
	public void setColumn(String[] b) {
		this.header = b;
	}
	
	/**
	 *  Returns the String 1D array to the Main for implementation
	 * @return String 1D array header for use in table or chart
	 */
	public String[] getColumn() {
		 return header;
	}
	
	/**
	 * Set current chart JFreeChart variable
	 * @param chart is a JFreeChart variable that stores all the necessities 
	 * 		  for building a ChartPanel
	 */
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	
	/**
	 * Returns the JFreeChart variable 
	 * @return chart variable for to the Main for implementation
	 */
	public JFreeChart getChart() {
		return chart;
	}
	
	    /**
	     * Load a roster from a CSV file and display it 
	     */
		public void loadList() {
			
		// Just in case user wants to start fresh clear all data
		Utility.clearHeader();      
		Utility.clearLinkedList();
		Utility.clearRow();
			
		// Header place holder for creating initial header
		String[] columnNames = {"ID", "First Name", "Last Name", "Program", "Level", "ASURITE"};
		//JFileChooser
		File file;		
		int response;
		JFileChooser chooser = new JFileChooser("");
		
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = chooser.showOpenDialog(null);
		
		// check the user choice for file, if true start to copy info to linked list
		if(response == JFileChooser.APPROVE_OPTION)
		{
			file = chooser.getSelectedFile();
			
			// create a linked list for storage of strings
			LinkedList<String> list = new LinkedList<String>();
			
			String line = ""; 			// store each line individually
			String split;	 		 	// split the string to not include commas
			BufferedReader br = null; 	// create the buffer reader for file
			int row = 0; 	 			// count the number of lines/rows in file
			
			
			//  Make sure no error happens when reading file. Keep code running
			try {
				
				br = new BufferedReader(new FileReader(file));
				
				// read each line in the file
				while ((line = br.readLine()) != null) { 
					
					list.add(line); // store each line in each individual node
					row++;			// increment row for each line added
				}
			}
			
			// file not found (keep code running)
			catch(FileNotFoundException e) { 
				e.printStackTrace();
			}
			
			// IO exception (keep code running)
			catch (IOException e) {
				e.printStackTrace();
			}
			
			// passes the try then close file 
			finally {
				if(br != null) {
					try {
						br.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
							
			};
			
			// send the current linked list to be saved in dataSource
			Utility.setData(list);
			String dataList[][] = new String[row][6]; // create the initial dataList for table purposes
			String[] data;							  // create the initial data array for header purposes
			
			// for loop for getting rid of the commas and inserting each piece of information 
			// into their own box in the table.
			for(int i = 0; i < list.size(); i++) {
				
				split = list.get(i);		// get the info from current node
				data = split.split(",", 6); // get rid of commas and save info
				
				// iterate and store row information
				for(int j = 0; j < 6; j++) {		
						dataList[i][j] = data[j];
				}	
			}
			
			Utility.setRow(6);			//send current row size to the dataSource
			Utility.setInitialHeader(); // Initialize current header in the dataSource
			setData(dataList);			// save current Data list
			setColumn(columnNames);		// save current Column names
	}
}
		
		/*
		 * Add attendance to the current roster.
		 * Also handle some niches in data and explain to user in GUI interface
		 */
		public void add() {
					
			//JFileChooser
			File file;		
			int response;
			JFileChooser chooser = new JFileChooser("");
			
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			response = chooser.showOpenDialog(null);
			
			// if true open CSV file
			if(response == JFileChooser.APPROVE_OPTION) {
					
				LinkedList<String> roster = new LinkedList<String>();	// hold roster data
				LinkedList<String> list = new LinkedList<String>();		// hold new CSV attendance data
				ArrayList<String> header = new ArrayList<String>();		// hold header data
				roster = Utility.getData(); 			// get linked list of roster
				int ss = Utility.getRow() + 1;			// get current row size and expand it for new information
				String[] columnNames = new String[ss];	// Create a temp header string with new row size

				// create info for choosing dates
		        String[] dates = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		        String[] month = {"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
				
		        // comboBox choices for GUI components for user choosing date for attendance
				JComboBox jcd = new JComboBox(dates);
				JComboBox jcm = new JComboBox(month);
				jcd.setEditable(true);
		        jcm.setEditable(true);
					  

		        //create a JOptionPane
		        int optionType = JOptionPane.OK_CANCEL_OPTION;
		        int result = JOptionPane.showConfirmDialog(null, jcm, "Select Month", optionType);
		        int result2 = JOptionPane.showConfirmDialog(null, jcd, "Select Day", optionType);
		        
		        // if both inputs are a pass then save info to the stored header in sourceData
		        if(result == JOptionPane.OK_OPTION & result2 ==JOptionPane.OK_OPTION ) {
		        	String month1 = (String) jcm.getSelectedItem();
		        	Object date1 = jcd.getSelectedItem();
		        	String fullDate  = month1 + " " + date1;// get the date 
		        	header = Utility.getHeader();			// get header 
					header.add(fullDate);					// save header with date
		    
		        }
		        else {
		        	return;
		        }
		        
		        // Create temp header for use in implementation of table and chart
				for(int i = 0; i < ss; i++) {
					columnNames[i] = header.get(i);
				}
				
				
				String line = "";				// for saving each line individually in file
				String split;					// split string to get rid of commas
				String splitCheck;				// comparison for second set of comma seperated values
				BufferedReader br = null;		// create buffered reader
					
				file = chooser.getSelectedFile();
				
				// try to open file
				try {
					
					br = new BufferedReader(new FileReader(file));
					
					// store each line individually in linked list
					while ((line = br.readLine()) != null) {
						
						list.add(line);
					}
				}
				
				// file not found (keep code running)
				catch(FileNotFoundException e) {
					e.printStackTrace();
				}
				
				// IO exception (keep code running)
				catch (IOException e) {
					e.printStackTrace();
				}
				
				// if info is stored then close file
				finally {
					if(br != null) {
						try {
							br.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				// comparison and store variables for data specification
				String compare; 	
				String compare1;
				String ASURITE;
				String addASURITE;
				String TIME;
				String temp = ""; // used for conviences.
				
				// data manipulation and current status
				int intTemp = list.size();
				int tempData;
				int tempData2;
				int num;
				int num2;
				int success = 0;
				
				// for advising the user in GUI implementation of niches in data
				split = roster.get(0);
				int sizeOfList = Utility.countComma(split) + 1; // expected row size
				int loadedUser = 0;
				int extraUser = 0;
				int duplicate = 0;
				  
				
				// for duplication checking
				String[] data;
				String[] dataCheck;
					
				
							// look for duplicates in ASURITE user names and combine times
							for(int i = 0; i < intTemp; i++) {
								
								for(int j = 1; j < intTemp; j++) {
									split = list.get(i); 		// get current node
									splitCheck = list.get(j);	// get next node
								
									num = Utility.countComma(split); 			// count # of commas in first node 
									data = split.split(",", num + 1);			// split first set of data 
									num2 = Utility.countComma(splitCheck);		// count # of commas in second node
									dataCheck = splitCheck.split(",", num2 + 1);// slit second set of data
									compare = data[0];							// get the first ASURITE ID
									compare1 = dataCheck[0];					// get the second ASURITE ID
									
										// compare if the ASURITE IDs are the same
										if(compare.equals(compare1) && j != i) {
									
											// get the times from the two nodes
											temp = data[1];						// get the time
											tempData = Integer.parseInt(temp);	// convert to into
											temp = dataCheck[1];				//"					"
											tempData2 = Integer.parseInt(temp); //"					"
									
											// add the times
											tempData = tempData + tempData2;
									
											// remove one of the nodes
											// convert int back to string
											temp = String.valueOf(tempData);
											data[1] = temp;
									
											// remove duplicate
											list.remove(j);
											
											// store new info in strong
											temp = data[0] + "," + data[1];
											
											list.set(i, temp); // put new info in the other duplicate node
											i = 0;			   // reset to the beginning of linked list and search again
											j = 1;
											intTemp--;		   // since we removed a node, decrease index size
										}
								}
							}
							
							
				// Arrays for checking extra attendees in attendance list but not in roster
				String[] extra = new String[list.size()];
				String[] extraTime = new String[list.size()];
				
				// This loop will add the time to all nodes in the linked list. 
				for(int i = 0; i < roster.size(); i++) {
					split = roster.get(i);				// get String from node
					num = Utility.countComma(split);	// get comma count
					data = split.split(",", num + 1);	// get rid of commas and seperate info
					ASURITE = data[5];					// get ASURITE ID from roster
					
					// iterate through attendance CSV for extra attendees not in roster
					for(int j = 0; j < list.size(); j++) {
						splitCheck = list.get(j);				// get String from node
						dataCheck = splitCheck.split(",", 2);	// get commas and seperate info
						addASURITE = dataCheck[0];				// Get ASURITE ID from list 
						TIME = dataCheck[1];					// Get time from list
						
						
						// check for similar ASU ID
						if(ASURITE.equals(addASURITE)) {	
							
							temp = split;					// get split string equal to temp
							temp = temp.concat("," + TIME); // concat time at the end of it
						
							roster.set(i, temp);			// set the new String with time
							
							success++;						// user was in roster and attendance
							
							if(duplicate == 0) {			// take note of now loaded user onto roster
								loadedUser++;
							}
						}
					}
					duplicate = 0;// reset duplicate
					
				}// end of check for loop
				
				// check for extra users
				for(int i = 0; i < list.size(); i++) {
					splitCheck = list.get(i);				// get String from node
					dataCheck = splitCheck.split(",", 2);	// get commas and seperate info
					addASURITE = dataCheck[0];				// Get ASURITE ID from list 
					
					for(int j = 0; j < roster.size(); j++) {
						split = roster.get(j);				// get String from node
						num = Utility.countComma(split);	// get comma count
						data = split.split(",", num + 1);	// get rid of commas and seperate info
						ASURITE = data[5];					// get ASURITE ID from roster
						
						// check for similar ASU ID
						if(ASURITE.equals(addASURITE)) {
							success++;
						}
					}
					
					// finds extra users in the attendance list that are not in the roster
					if(success == 0) {
						extra[extraUser] = dataCheck[0];	 // get the ASURITE ID and save it to array
						extraTime[extraUser] = dataCheck[1]; // get the time associated with the ASURITE and save it
						extraUser++;
					}
				success = 0; // reset success
				}
				
				
				
				// display absent for information not inputed. 
				for(int i = 0; i < roster.size(); i++) {
					split = roster.get(i);	
					num = Utility.countComma(split);
					data = split.split(",", num + 1);
					
					if(sizeOfList != num) {
					
						temp = split;					// temp = split (String with commas)
						temp = temp.concat(",0"); 		// concat 0 since they were abset
						
						roster.set(i, temp);			// set the node with 0
					}					
				}// end of check for loop


				// get the new dataList with current information
				String dataList[][] = new String[roster.size()][ss];
				String[] count;

				// iterate and set initialize dataList with current data in linked list
				for(int i = 0; i < roster.size(); i++) {
					
					split = roster.get(i);				// get row slash node
					num = Utility.countComma(split);	// count number of commas
					count = split.split(",", num + 1);	// split string without commas
					
					for(int j = 0; j < num + 1; j++) {
							dataList[i][j] = count[j]; // i is row while j is column
					}
				}
			
				
				Utility.setRow(ss);			// update max row
				Utility.setData(roster);	// update linked list data
				setData(dataList);			// update data List for GUI
				setColumn(columnNames);		// update column names for GUI

				
				// Niche adjustments that tells users of extra attendees not in roster
				String work = ""; // represents all extra attendees names
				String a = "";	  // represents total number of extra attendees
				String b = "";	  // represents total number of loaded attendees info
				
				// check if they are extra users 
				if(extraUser != 0) {
	  
				    a = "Data loaded for " + loadedUser + " users in the roster.\n";   // display total loaded attendees
				    b = extraUser + " additional attendees was found:\n";    		   // display total extra attendees
				    
				    // iterate through extra array that stored names
				    for(int i = 0; i < extraUser; i++) {
				    	work = work.concat(extra[i] + ", connected for " + extraTime[i] + " minutes.\n"); // concat each user
				    }
				    
				    JOptionPane.showMessageDialog(null, a + b + work); // combine strings and display
				}
				
				// else notify user
				else {
					
				    a = "Data loaded for " + loadedUser + " users in the roster.\n";   // display total loaded attendees
				    b = extraUser + " additional attendees was found:\n";  			   // notify that there were no extra attendees not in roster
				    
				    
					JOptionPane.showMessageDialog(null, a + b); // combine strings and display
				}
			}		
		}
		
		/**
		 * 
		 */
		void saveList() {
			
			String temp = ""; // used for conviences.
			String split;	  // holds string from node
			String[] data;	  // holds string without commas
			int count;		  // number of columns
			
			// get the final header and concat together back in CSV format
			for(int j = 0; j < Utility.getHeader().size(); j++) {
				temp = temp.concat(Utility.getHeader().get(j) + ",");
			}
			
			Utility.getData().add(0,temp); // add it to the first node so that it can be at the top of the new CSV file
			
			//JFileChooser for creating new file
		    JFileChooser chooser = new JFileChooser();						
		    chooser.setCurrentDirectory(new File("/home/me/Documents"));
		    int retrival = chooser.showSaveDialog(null);
		    if (retrival == JFileChooser.APPROVE_OPTION) {
		    	
		    	
		    	try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".csv")) {
		    	     		
		    		// spread the information per row
		    		for(int i = 0; i < Utility.getData().size(); i++) {
		    			split = Utility.getData().get(i);	// get string in node
		    			count = Utility.countComma(split);	// count commas
		    			data = split.split(",", count + 1); // get rid of commas
		    			temp = "";							// initialize temp empty every time due to concat issues
		    				
		    				// for loop to rebuild file with info and make sure that there are no commas at the end of strings
		    				for(int o = 0; o < count + 1; o++) {
		    					
		    					if(o == count) {
		    						temp = temp.concat(data[o]); // conat info at the end of string
		    					}
		    					
		    					else {
		    						temp = temp.concat(data[o] + ","); // not the end of string add comma
		    					}	
		    				}
		    				fw.write(temp+ "\n"); // write the line in file
		    		}
		    	} 
		    	
		    	
		    	catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		}
		
		/**
		 * This method will display all the information in a chart using JFreeChart
		 */
		public void plotList() {    
			  
			
	    	XYDataset dataset = createDataset(); 				// create a new dataset that have all data from the linked list
	    	
		    // Create chart  
		    JFreeChart chart = ChartFactory.createScatterPlot(    // create a chart object that will hold every aspect feature for chart
		        "Attendance",   
		        "Percentages", "Count of Students", dataset);  	  // Y axis is how many students (from 0 to total row in the table) and 
		    													  // X-axis is % of attendance. 100% is 75 minutes or more. 
		      
		    XYPlot xyPlot = (XYPlot) chart.getPlot();			// set X-Y plot 
		    xyPlot.setDomainCrosshairVisible(true);				// set X-Axis visible
		    xyPlot.setRangeCrosshairVisible(true);				// set Y-Axis visible
		    XYItemRenderer renderer = xyPlot.getRenderer();		// color render
		    renderer.setSeriesPaint(0, Color.blue);				// set to blue
		    
		    // set the x axis
		    NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();	// get domain object
		    domain.setRange(0, 1);										// set range of possible values
		    domain.setTickUnit(new NumberTickUnit(.05));				// increment by 0.5 from 0 to 1
		    
		    
		    
		    //set the y axis
		    NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();		// set range object
		    range.setRange(0,Utility.getData().size());					// set range of possible values
		    range.setTickUnit(new NumberTickUnit(1));					// increment by 1 from 0 to nodes in linked list
		    new ChartPanel(chart);										// make a chart panel with chart object
		   
		    //Changes background color  
		    XYPlot plot = (XYPlot)chart.getPlot();  					// Make a plot 
		    plot.setBackgroundPaint(new Color(255,228,196));  			// Color it 
		     	
		    setChart(chart);											// save current iteration of chart
	    	} 
		  
		/**
		 * This method will enter the data into the chart
		 * @return dataset object to be used in chart GUI (Contains for both each index of x and y coordinates)
		 */
		  private XYDataset createDataset() {  
		    XYSeriesCollection dataset = new XYSeriesCollection();  // create dataset object
		  
		    // Create dataset  
	    	for(int w = 6; w < Utility.getRow(); w++) {
	    		String date = Utility.getHeader().get(w);	// get the date from the header
	    	
		    
		    XYSeries series = new XYSeries(date);			// set the series with date title
		    
		    String split;		// holds string from node
		    String temp;		// for conveince
		    int num;			// for holding # of commas
		    String[] data;		// After split is delimited
		    
			 // make the data insertion dynamic
			for(int i = 0; i < Utility.getData().size(); i++) {
			   
					split = Utility.getData().get(i);	// get string from node
					num = Utility.countComma(split);	// get # of commas
					data = split.split(",", num + 1);	// delimit the string
					
					temp = data[w];						// get the time in node at their respective column
					double x =  Integer.parseInt(temp); // parse string to int
					
					// if greater than 75 minutes then set to 75 (100% max)
					if(x > 75) {
						x = 75;
					}
					// else divide x by 75 and save decimal
					else {
						x = x / 75;
					}
				    int y = i;	// save current row in chart hierarchy
					
				    series.add(x,y); // add to series
			 }
			
		    dataset.addSeries(series); // add to dataset
	    	}
		    return dataset;    // return dataset
	     }
}