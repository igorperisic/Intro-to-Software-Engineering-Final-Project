import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;




/**
 * Main class file that creates the GUI interface for the program. 
 * Using JFram as the base.
 * ActionListener for the button handling. 
 * @author David Rodriguez, Igor Persisic, Frankincense Ramesh
 *
 */
public class Main extends JFrame implements ActionListener{
	
	// About Message about the members of the project
	String aboutMsg = "Team Members: Igor Perisic, David Rodriguez, Frankincense Ramesh" + "\r\n" + "Emails: iperisic@asu.edu, dprodri3@asu.edu, framesh@asu.edu";
	
	JMenuItem load = new JMenuItem("Load a Roster");	//"load a roster" object 
	JMenuItem add = new JMenuItem("Add Attendance");	//"Add Attendance" object
	JMenuItem save = new JMenuItem("Save");				//"save" object
	JMenuItem plot = new JMenuItem("Plot Data");		//"Plot data" object
	JMenuItem about = new JMenuItem("About");		    //about tab object

	loadRoster LR = new loadRoster();  					 // create an object to access methods and info from loadRoster class

				// main constructor that will create the GUI Interface
				Main() {
					
					JMenuBar jmb = new JMenuBar();			//MenuBar object
					setJMenuBar(jmb);					    //adds MenuBar to JFrame
					JMenu file = new JMenu("File");			//file tab object
					
						
					jmb.add(file);							//adds file to MenuBar
					jmb.add(about);							//adds about to MenuBar		
					
					file.add(load);		//adds "load a roster" object to the file tab. 
					file.add(add);		//adds "Add attendance" object to the file tab. 
					file.add(save);		//adds "Save" object to the file tab
					file.add(plot);		//adds "Plot Data" object to the file tab 		
					
					load.addActionListener(this);	// check for mouse click for load button
					save.addActionListener(this);	// check for mouse click for save button
					add.addActionListener(this);	// check for mouse click for add button
					about.addActionListener(this);	// check for mouse click for about button
					plot.addActionListener(this);	// check for mouse click for plot button
				}

				/**
				 * Display a table that will show-case both roster and roster after adding attendance. 
				 * @param dataList 2D String array that contains student information for each row
				 * @param Columns 1D String array that contains the header and "Date" information for each column
				 */
				public void display(String[][] dataList, String[] Columns) {
					
		
					//JTable
					JTable table = new JTable(dataList, Columns);
					
                    table.getColumnModel().getColumn(0).setMinWidth(80);
                    table.getColumnModel().getColumn(1).setMinWidth(100);
                    table.getColumnModel().getColumn(2).setMinWidth(100);
                    table.getColumnModel().getColumn(3).setMinWidth(150);
                    table.getColumnModel().getColumn(4).setMinWidth(90);
                    table.getColumnModel().getColumn(5).setMinWidth(70);
                    
                    for(int i = 6; i < Columns.length; i++) {
                    	table.getColumnModel().getColumn(i).setMinWidth(50);
                    }
					
					
					
					table.setPreferredScrollableViewportSize(new Dimension(500
							,50));
					table.setFillsViewportHeight(true);
					table.setAutoResizeMode(0);
					JScrollPane scrollPane = new JScrollPane(table);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPane.getViewport().add(table);
					scrollPane.setVisible(true);
					add(scrollPane);
					
				}
				
				
				/**
				 * Create a ChartPanel with all the information that was given by the CSV files. 
				 * @param chart A JFreeChart variables that contains all the info necessary to create a ChartPanel
				 */
				public void displayChart(JFreeChart chart) {
				    
				    // Create Panel  
				    ChartPanel panel = new ChartPanel(chart);  
				    setContentPane(panel); 
				}
				
				
				/**
				 * ActionEvent Type Listener for button handling. 
				 */
				@Override
				public void actionPerformed(ActionEvent e)
				{				
					if(e.getSource() == about) // Display about page
					{
						JOptionPane.showMessageDialog(null, aboutMsg);
					}
					
					if(e.getSource() == load) // load roster information and display it
					{			
						getContentPane().removeAll();
						try {
						LR.loadList();	
						display(LR.getData(),LR.getColumn());
						}
						catch(ArrayIndexOutOfBoundsException d) {
							JOptionPane.showMessageDialog(null, "Load CSV with the following format:\n"
														  + "School ID,First Name,Last Name,Major,Level,ASURITE ID");
						}
						revalidate();
					}
					else if(e.getSource() == save) // Save current attendance sheet information to new csv
					{
						getContentPane().removeAll();
						LR.saveList();
						revalidate();
					}
					else if(e.getSource() == add) // add attendance to current roster
					{
						getContentPane().removeAll();
						try { // Make sure that roster is loaded first before adding
						LR.add();
						display(LR.getData(),LR.getColumn());
						}
						catch(NullPointerException i) { // check for null pointer
							JOptionPane.showMessageDialog(null, "You need to low a roster first");
						}
						catch(IndexOutOfBoundsException out) { // check for array out of bounds
							JOptionPane.showMessageDialog(null, "You need to low a roster first");
						}
						revalidate();
					}
					else if(e.getSource() == plot) 	// display a plot with current attendance information
					{
						getContentPane().removeAll();
						try { // check for users not breaking code for ChartPanel rules
						LR.plotList();
						displayChart(LR.getChart());
						}
						catch(IllegalArgumentException o) {
							JOptionPane.showMessageDialog(null, "Something went wrong the chart\nLoad Roster Again...");
						}
						revalidate();
					}
					
				}
	
	/**
	 * main function that will create the general JFrame
	 * @param args
	 */
	public static void main(String[] args) {
		
		Main main = new Main();
		main.setTitle("CSE360 Final Project");	
		main.setSize(800, 600);					
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);	
		
	}
}