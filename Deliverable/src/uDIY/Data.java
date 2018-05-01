package uDIY;

import java.util.ArrayList;

/**
 * A class that stores and handles all data used by other classes
 * 
 * @author Matthew Hwang, Chris Kim
 * @version 11/28/2017
 */
public class Data {
    
	// Average price per kilowatt-hour
	private double energyVariable = 0.12;

	private ArrayList<Item> itemList;
	private ArrayList<Energy> energyList;
	
	/**
	 * Default Constructor
	 * @author Chris Kim
	 * @version 12/1/17
	 */
	public Data() {
		itemList = new ArrayList<Item>();
		energyList = new ArrayList<Energy>();
	}
	
	/**
  	 * Constructor
 	 * @author Chris Kim
 	 * @version 11/30/17
  	 */
  	public Data(ArrayList<Item> itemList, ArrayList<Energy> energyList) {
  		this.energyList = energyList;
  		this.itemList = itemList;
  	}
	
	/**
	 * Add an energy object to the list
	 * @author Matthew Hwang
	 * @version 12/5/2017
	 * @param oldEnergy The old energy value
	 * @param newEnergy The new energy value
	 */
	public void addEnergy(double oldEnergy, double newEnergy) {
		energyList.add(new Energy(oldEnergy, newEnergy));
	}
	
	/**
	 * Add an item object to the list
	 * @author Matthew Hwang
	 * @version 12/5/2017
	 * @param itemName The item name
	 * @param itemPrice The item price
	 * @param itemQuantity The item quantity
	 */
	public void addItem(String itemName, double itemPrice, int itemQuantity) {
		itemList.add(new Item(itemName, itemPrice, itemQuantity));
	}
	
	/**
	 * Calculates the total cost of materials
	 * @author Chris Kim, Matthew Hwang, Parker Olive
	 * @version 12/7/17
	 * @return The total cost of materials
	 */
	public double totalCost() {
		//the total cost of the materials
		double cost = 0.0;
		//adds the costs of each item together
		for (Item item: itemList) {
			cost += item.TotalPrice();
		}
		cost = Math.round(cost*100.0)/100.0; // Rounds to two decimals
		return cost;
	}
	
	/**
	 * Calculates the total new energy consumption
	 * @author Matthew Hwang
	 * @version 12/5/2017
	 * @return The total new energy consumption
	 */
	public double totalNewEnergy() {
		double consumption = 0.0;
		for (Energy energy: energyList) {
			consumption += energy.getNew();
		}
		return consumption;
	}
	
	/**
	 * Calculates the total old energy consumption
	 * @author Matthew Hwang, Parker Olive
	 * @version 12/6/2017
	 * @return The total new energy consumption
	 */
	public double totalOldEnergy() {
		double consumption = 0.0;
		for (Energy energy: energyList) {
			consumption += energy.getOld();
		}
		return consumption;
	}
	
	/**
	 * Calculates the total savings per day
	 * @author Chris Kim, Matthew Hwang
	 * @version 12/1/17
	 * @return The total savings per day
	 */
	public double totalSavings() {
		//the total savings the user will save on energy
		double savings = 0.0;
		//uses the energyVariable to calculate the savings based on monthly energy usage
		for(Energy energy: energyList) {
			savings += (energy.getDifference() * energyVariable * 24);
		}
		return savings;
	}
	
	/**
	 * Calculates the number of days required to break even after the project
	 * @author Chris Kim, Matthew Hwang
	 * @version 12/5/17
	 * @return The number of days required to break even after the project
	 */
	public int breakEven() {
		if (energyList.size() == 0) {
			return 0;
		} else {
			return (int) Math.ceil(totalCost() / totalSavings());
		}
	}
	
	/**
	 * Gets the item list
	 * @author Chris Kim
	 * @version 12/1/2017
	 * @return The item list
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	
	/**
	 * Gets the energy list
	 * @author Chris Kim
	 * @version 12/1/2017
	 * @return The energy list
 	 */
	public ArrayList<Energy> getEnergyList() {
		return energyList;
	}
}
