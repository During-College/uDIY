package uDIY;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Item Object Class
 * Stores information about an item
 * @author Parker Olive
 * @version 11/28/2017
 */
public class Item {
	
	/*===Private Item Fields===*/
	private String itemName;
	private double itemUnitPrice;
	private int itemQuantity;
	/*=========================*/
	
	/*=======Text Fields=======*/
	JTextField nameField;
	JTextField unitPriceField;
	JTextField quantityField;
	/*=========================*/
	
	/*=====Constant Values=====*/
	private final String ErrorText =  "Please Input a Number";
	private final String DEFAULTNAME = "Item Name";  // Default Item Name
	private final Dimension FIELDSIZE = new Dimension(150, 30);
	private final int NAME = 0;
	private final int UNITPRICE = 1;
	private final int QUANTITY = 2;
	private final String negNum = "Positive number please";
	/*=========================*/
	
	/**
	 * Default Constructor
	 * Creates a default item with a default name and 0 quantity and unitprice
	 * @author Parker Olive
	 * @version 11/28/2017
	 */
	public Item(){
		itemName = DEFAULTNAME;
		itemUnitPrice = 0;
		itemQuantity = 0;
		
		/*=========Fill Text Fields With Default Values=========*/
		nameField = SetUpTextField("Item Name", NAME);
		unitPriceField = SetUpTextField("Unit Price", UNITPRICE);
		quantityField = SetUpTextField("Quantity", QUANTITY);
		/*======================================================*/
	}
	
	/**
	 * Constructor
	 * @author Parker Olive
	 * @version 11/28/2017
	 * 
	 * @param name - Name of the item
	 * @param price - Price per unit of the item
	 * @param quantity - The quantity of the item
	 */
	public Item(String name, double unitPrice, int quantity){
		itemName = name;
		itemUnitPrice = unitPrice;
		itemQuantity = quantity;
		
		/*=========Fill Text Fields With Given Values=========*/
		nameField = SetUpTextField(name, NAME);
		unitPriceField = SetUpTextField(""+unitPrice, UNITPRICE);
		quantityField = SetUpTextField(""+quantity, QUANTITY);
		/*====================================================*/
	}
	
	public double TotalPrice(){
		return itemUnitPrice*((double)itemQuantity);
	}
	
	/**
	 * Creates and sets up a text field with default text that 
	 * disappears when it gains focus
	 * @author Parker Olive
	 * @version 11/28/2017
	 * 
	 * @param DefaultT the default text for the field
	 * @return the created button
	 */
	private JTextField SetUpTextField(String DefaultT, final int box){
		JTextField f = new JTextField(DefaultT);
		
		/*===Sets Text Fields to a fixed Size===*/
		f.setPreferredSize(FIELDSIZE);
		f.setMaximumSize(FIELDSIZE);
		f.setMinimumSize(FIELDSIZE);
		/*======================================*/
		
		/*sets up the default text to disappear and reappear*/
		f.addFocusListener(new FocusListener() {  
			final int on = box; //tells the listeners which field they are listening to
			
			/**
			 * clears the default text or error text form the text field when focus is gained
			 * @author Parker Olive
			 * @version 11/28/2017
			 * 
			 * @param e the Focus Event
			 */
    	    @Override  
    	    public void focusGained(FocusEvent e) {  
    	    	if (f.getText().equals(DefaultT) || f.getText().equals(ErrorText) || f.getText().equals(negNum)) //checks if the text is the default or error text
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
    	        if (f.getText().length() == 0) {  //check if the field is empty
    	        	if(on == NAME) {			  //check if the field is the name field and set the name to the default if it is
    	        		itemName = DEFAULTNAME;
    	        	} else if(on == UNITPRICE) {  //check if the field is the unit price field and set UnitPrice to 0 if it is
    	        		itemUnitPrice = 0;
    	        	} else {					  //check if the field is the quantity field and set Quantity to 0 if it is
    	        		itemQuantity = 0;
    	        	}
    	            f.setText(DefaultT);		  // Sets the text back to the default text for whichever field has lost focus
    	        } else {
    	        	if(on == NAME) {
    	        		itemName = f.getText();
    	        	} else if (on == UNITPRICE){
    	        		try {
    	        			if (Double.parseDouble(unitPriceField.getText()) < 0) {
    	        				unitPriceField.setText("Positive number please");
    	        			}
    	    			} catch(NumberFormatException n) {
    	    				unitPriceField.setText(ErrorText);				//Display Error message if entered text was not a number
    	    			}
    	        	} else {
    	        		try {
    	        			if (Double.parseDouble(quantityField.getText()) < 0) {
    	        				quantityField.setText(negNum);
    	        			}
    	    			} catch(NumberFormatException n) {
    	    				quantityField.setText(ErrorText);				//Display Error message if entered text was not a number
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
	 * @version 11/28/2017
	 */
	public void gatherData() {
		itemName = nameField.getText();	//get the name from the name field
		try {
			itemUnitPrice = Double.parseDouble(unitPriceField.getText());	//Parse the Unit Price from the text field
			double readQ = Double.parseDouble(quantityField.getText());		//Parse the Quantity from the text field
			itemQuantity = (int)Math.round(readQ);
		} catch (NumberFormatException n) {
			System.out.println(itemQuantity +" - "+ quantityField.getText());
		}
	}
	
	/**
	 * Gets the item name
	 * @author Parker Olive
	 * @version 11/28/2017
	 * 
	 * @return The item name
	 */
	public String getName() {
		return itemName;
	}
	
	/**
	 * Gets the item price per unit
	 * @author Parker Olive
	 * @version 11/28/2017
	 * 
	 * @return The item price per unit
	 */
	public Double getUnitPrice() {
		return itemUnitPrice;
	}
	
	/**
	 * Gets the item quantity
	 * @author Parker Olive
	 * @version 11/28/2017
	 * 
	 * @return The item quantity
	 */
	public int getQuantity() {
		return itemQuantity;
	}
	
	/**
	 * Generates a string with the item information
	 * @author Parker Olive
	 * @version 11/28/2017
	 */
	@Override
	public
	String toString(){
		return itemName + "\t$" + itemUnitPrice + "\t\t" + itemQuantity;
	}
}
