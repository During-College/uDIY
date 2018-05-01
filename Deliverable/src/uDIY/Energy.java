package uDIY;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Energy Object Class
 * Stores information about an energies
 * @author Parker Olive
 * @version 11/29/2017
 */
public class Energy {
	/*===Private Energy Fields===*/
	private double oldEnergy;
	private double newEnergy;
	/*=========================*/
	
	/*=======Text Fields=======*/
	JTextField oldField;
	JTextField newField;
	/*=========================*/
	
	
	/*=====Constant Values=====*/
	private final String ErrorText =  "Please Input a Number";
	private final String negNum = "Positive number please";
	private final String DEFAULTOLDFIELD = "Old Energy";
	private final String DEFAULTNEWFIELD = "New Energy";
	private final Dimension FIELDSIZE = new Dimension(150, 30);
	private final boolean OLD = true;
	private final boolean NEW = false;
	/*=========================*/
	
	/**
	 * Default Constructor
	 * Creates a default energy with 0 old and new energy
	 * @author Parker Olive
	 * @version 11/29/2017
	 */
	public Energy(){
		oldEnergy = 0.0;
		newEnergy = 0.0;
		oldField = setUpTextField(DEFAULTOLDFIELD, OLD);
		newField = setUpTextField(DEFAULTNEWFIELD, NEW);
	}
	
	/**
	 * Constructor
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @param old the value to store in oldEnergy
	 * @param new the value to store in newEnergy
	 */
	public Energy(double oldE, double newE){
		oldEnergy = oldE;
		newEnergy = newE;
		
		/*===Fill Text Fields With Given Values===*/
		oldField = setUpTextField(""+oldE, OLD);
		newField = setUpTextField(""+newE, NEW);
		/*========================================*/
	}
	
	/**
	 * Calculates the percentage difference between old and new energy
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @return the percentage difference (-) when new is more than old (double)
	 */
	public double percentSaved(){
		if (oldEnergy - newEnergy > 0) {
			return newEnergy/oldEnergy;
		} else {
			return (oldEnergy/newEnergy) * -1;  //Negative to indicate that the savings in energy is negative
		}
	}
	
	/**
	 * Creates and sets up a text field with default text that 
	 * disappears when it gains focus
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @param DefaultT the default text for the field
	 * @return the created button
	 */
	private JTextField setUpTextField(String DefaultT, final boolean oldnew){
		JTextField f = new JTextField(DefaultT);
		
		/*===Sets Text Fields to a fixed Size===*/
		f.setPreferredSize(FIELDSIZE);
		f.setMaximumSize(FIELDSIZE);
		f.setMinimumSize(FIELDSIZE);
		/*======================================*/
		
		/*sets up the default text to disappear and reappear*/
		f.addFocusListener(new FocusListener() {  
			final boolean on = oldnew; //tells the listeners which field they are listening to
    	    
			/**
			 * clears the default text or error text form the text field when focus is gained
			 * @author Parker Olive
			 * @version 11/29/2017
			 * 
			 * @param e the Focus Event
			 */
			@Override  
    	    public void focusGained(FocusEvent e) {  
    	    	if (f.getText().equals(DefaultT) || f.getText().equals(ErrorText) || f.getText().equals(negNum))	//checks if the text is the default or error text
	            		f.setText("");  //clears the text field
    	    }  

			/**
    	     * checks a text field when it has lost focus and either replaces the default text or displays an error message
    	     * @author Parker Olive, Matthew Hwang (Minor Modifications)
    	     * @version 12/5/2017
    	     * 
    	     * @param e the Focus Event
    	     */
    	    @Override  
    	    public void focusLost(FocusEvent e) { 
    	        if (f.getText().length() == 0) {	//check if the field is empty
    	        	if(on == NEW) {					//check if the field is the New Energy field and set it to 0 if it is
    	        		newEnergy = 0;
    	        	} else {
    	        		oldEnergy = 0;				//check if the field is the New Energy field and set it to 0 if it is
    	        	}
    	            f.setText(DefaultT);			// Sets the text back to the default text for whichever field has lost focus
    	        } else {
    	        	if(on == NEW) {
    	        		try {
    	        			if (Double.parseDouble(newField.getText()) < 0) {
    	        				newField.setText(negNum);
    	        			}
    	    			} catch(NumberFormatException n) {
    	    				newField.setText(ErrorText);			//Display Error message if entered text was not a number
    	    			}
    	        	} else {
    	        		try {
    	        			if (Double.parseDouble(oldField.getText()) < 0) {
    	        				oldField.setText("Positive number please");
    	        			}
    	    			} catch(NumberFormatException n) {
    	    				oldField.setText(ErrorText);			//Display Error message if entered text was not a number
    	    			}
    	        	}
    	        }
    	        	

    	    }  
    	});
		
		return f;
	}
	
	/**
	 * Pulls the date from the text fields into the private fields
	 * @author Parker Olive
	 * @version 11/29/2017
	 */
	public void gatherData() {
		try {
			oldEnergy = Double.parseDouble(oldField.getText());	//Parse the oldEnergy from the text field
			newEnergy = Double.parseDouble(newField.getText());	//Parse the newEnergy from the text field
		} catch (NumberFormatException n) {
			//if there was an error do nothing
		}
	}
	
	/**
	 * Gets the old Energy
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @return The old Energy
	 */
	public double getOld(){
		return oldEnergy;
	}
	
	/**
	 * Gets the new Energy
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @return The new Energy
	 */
	public double getNew(){
		return newEnergy;
	}
	
	/**
	 * Gets the difference between the old and new Energies
	 * @author Parker Olive
	 * @version 11/29/2017
	 * 
	 * @return the difference between the old and new Energies
	 */
	public double getDifference() {
		return oldEnergy - newEnergy;
	}
	
	/**
	 * Generates a string with the Energy information
	 * @author Parker Olive
	 * @version 11/29/2017
	 */
	@Override
	public
	String toString(){
		return oldEnergy + " kWh\t" + newEnergy + " kWh";
	}
	
	
}
