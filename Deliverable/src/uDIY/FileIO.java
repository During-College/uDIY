package uDIY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A class that handles file input and output
 * @author Parker Olive, Matthew Hwang
 * @version 11/30/2017
 */
public class FileIO {
	/*===Storage For Read-Write===*/
	private static final ArrayList<Item> fileItems = new ArrayList<Item>();
	private static final ArrayList<Energy> fileEnergy= new ArrayList<Energy>();
	private String projectName = "";
	/*============================*/
	
	/**
	 * Saves the project as a text file (csv)
	 * @author Parker Olive
	 * @version 11/30/2017
	 * 
	 * @param projetName the project's name
	 * @param energy the list of energies
	 * @param item the list of items
	 * @param f the file to save to
	 */
	public void save(String projectName, ArrayList<Energy> energy, ArrayList<Item> item, File f) {
		FileWriter fwrite;
		try {
			fwrite = new FileWriter(f);
			fwrite.write(projectName+"\n");
			fwrite.write(energy.size()+"\n"); // Write the number of energies before all energies
			for(Energy e: energy){
				// Write each energy to the file with commas separating old and new
				fwrite.write(e.getOld() + "," + e.getNew()+"\n");
			}
			fwrite.write(item.size()+"\n"); // write the number of items before all items
			for(Item i: item){
				// Write each item to the file with commas separating name, unit price and quantity
				fwrite.write(i.getName() + "," + i.getUnitPrice() + "," + i.getQuantity()+"\n"); 
			}
			fwrite.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Sets the project name
	 * @author Matthew Hwang
	 * @version 12/6/2017
	 * @param theName The replacement project name
	 */
	public void setProjectName(String theName) {
		projectName = theName;
	}
	
	/**
	 * Opens an existing project (csv)
	 * @author Parker Olive, Matthew Hwang (small modifications)
	 * @version 11/30/2017
	 * 
	 * @param f the file to open
	 */
	public void open(File f) {
		fileItems.clear();
		fileEnergy.clear();
		try {
			Scanner input = new Scanner(f);
			Scanner parser;
			int numEnergy = 1;
			int numItem = 1;
			String line = "";
			
			if(input.hasNextLine())
				projectName = input.nextLine();
			if(input.hasNextLine())
				line = input.nextLine(); //gets the number of energies line
			parser = new Scanner(line);
			numEnergy = parser.nextInt(); //reads how many energies there are
			parser.close();
			for(int i = 0; i < numEnergy; i++){
				if(input.hasNextLine()){
					line = input.nextLine(); //gets entire line of an energy to be parsed
					parser = new Scanner(line);
					parser.useDelimiter(","); //ignores commas
					//reads the line into an energy
					Energy e = new Energy(parser.nextDouble(), parser.nextDouble());
					fileEnergy.add(e);
					parser.close();
				}
			}
			if(input.hasNextLine())
				line = input.nextLine(); //gets the number of items line
			parser = new Scanner(line);
			numItem = parser.nextInt(); //reads how many items there are
			for(int i = 0; i < numItem; i++){
				if(input.hasNextLine()){
					line = input.nextLine(); //gets entire line of an item to be parsed
					parser = new Scanner(line);
					parser.useDelimiter(","); //ignores commas
					//reads line into item
					Item item = new Item(parser.next(),parser.nextDouble(), parser.nextInt());
					fileItems.add(item);
					parser.close();
				}
			}
			input.close();
			
			
		} catch (FileNotFoundException e) {
			//user tries to open an non-existent file
			JOptionPane.showMessageDialog(null, "File Not Found");
		}
	}
	
	/**
	 * Getter for the project name
	 * @author Matthew Hwang
	 * @version 12/5/2017
	 * @return The project name
	 */
	String getProjectName() {
		return projectName;
	}
	
	/**
	 * Getter for the energy list from opening
	 * @author Parker Olive
	 * @version 11/30/2017
	 * 
	 * @return the energy list
	 */
	ArrayList<Energy> getEnergyList(){
		return fileEnergy;
	}
	/**
	 * Getter for the item list from opening
	 * @author Parker Olive
	 * @version 11/30/2017
	 * 
	 * @return the item list
	 */
	ArrayList<Item> getItemList(){
		return fileItems;
	}
	
	
	
	/**
	 * Exports the project as an easily readable file for the user (txt)
	 * @author Parker Olive, Matthew Hwang
	 * @version 12/5/2017
	 * 
	 * @param data The data handler
	 * @param f the file to export to
	 */
	public void export(Data data, File f) {
		FileWriter fwrite;
		try {
			String newLine = System.getProperty("line.separator");
			fwrite = new FileWriter(f);
			//write all the energies to the file
			fwrite.write("Project Name: " + projectName + newLine + newLine + newLine);
			fwrite.write("Energy Consumption:" + newLine);
			fwrite.write("\tOld Energy\tNew Energy" + newLine);
/*			There are already methods for most of these 
			double oldEnergyTotal = 0.0; just needed a copy for old energy total
			double newEnergyTotal = 0.0;
			double totalItemCost = 0.0;
*/
			for(Energy e: data.getEnergyList()){
				fwrite.write("\t" + e.toString() + newLine);
			}
			fwrite.write(newLine + "Totals:\t" + data.totalOldEnergy() + " kWh\t" + data.totalNewEnergy() + " kWh" + newLine);
			fwrite.write("Savings per day: $" + data.totalSavings() + newLine + newLine + newLine);
			//write all the items to the file
			fwrite.write("Materials:" + newLine);
			fwrite.write("\tName\tPrice/Unit\tQuantity" + newLine);
			for(Item i: data.getItemList()){
				fwrite.write("\t" + i.toString() + newLine);
			}
			fwrite.write(newLine + "Total Material Cost: $" + data.totalCost() + newLine + newLine);
			fwrite.write("Days to Recoup Costs: " + data.breakEven());
			fwrite.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
