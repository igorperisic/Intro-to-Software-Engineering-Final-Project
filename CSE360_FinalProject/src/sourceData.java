import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The sourceData class will  hold all the information saved from the .csv file.
 * It can be accessed at any given time by the load roster for dynamic change.
 * @author David Rodriguez, Igor Persisic, Frankincense Ramesh
 *
 */
public class sourceData {

	public LinkedList<String> data = new LinkedList<String>(); // globally accessible linked list containing roster information
	public ArrayList<String> header = new ArrayList<String>(); // containing header information with dates
	public int rowSize; // current rowSize that will hold current size of rows for roster
	
	
	/*
	 * Clear arraylist when loading new roster. 
	 */
	public void clearHeader() {
		header.clear();
	}
		
	/*
	 * Clear linked List when loading a new roster
	 */
	public void clearLinkedList() {
		data.clear();
	}
	
	/*
	 * Clear row size when loading a new roster
	 */
	public void clearRow() {
		rowSize = 0;
	}
	
	/*
	 * Set the the public header after the import of roster
	 */
	public void setInitialHeader() {
			this.header.add("ID");
			this.header.add("First Name");
			this.header.add("Last Name");
			this.header.add("Program");
			this.header.add("Level");
			this.header.add("ASURITE");
	}
	
	/*
	 * get the roster data and set it equal to this linked list
	 */
	public void setData(LinkedList<String> roster) {
			this.data = roster;
	}
	
	/*
	 * If a requested return the current roster data 
	 */
	public LinkedList<String> getData() {
		return data;
	}
	
	/*
	 * Retrieve the row current row size
	 */
	public void setRow(int a) {
		this.rowSize = a;
	}

	/*
	 * Return the current row size
	 */
	public int getRow() {
		return rowSize;
	}
	
	/*
	 * set the current header for roster
	 */
	public void setHeader(ArrayList<String> header) {
		this.header = header;
	}

	/*
	 * return the current header for roster
	 */
	public ArrayList<String> getHeader() {
		return header;
	}
	/*
	 * Utility used in dynamically keeping track of the size of data in CSV and LinkedList
	 */
	public int countComma(String a) {
		int commas = 0;
		
		for(int i = 0; i < a.length(); i++) {
		    if(a.charAt(i) == ',') 
		    	commas++;
		}
		return commas;
	}
}
