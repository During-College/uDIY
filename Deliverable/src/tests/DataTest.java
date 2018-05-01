package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uDIY.Data;
import uDIY.Energy;
import uDIY.Item;

/**
 * Data Test Class
 * @author Parker Olive, Tyler Shupack, Chris Kim
 * @version 12/06/2017
 */
public class DataTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * tests the constructor for the data class
	 * @author Parker Olive
	 * @version 12/6/2017
	 */
	@Test
	public void testConstructor() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		
		//Test Fields for Item
		final String testName = "item 123";
		final double testPrice = 1.55;
		final int testQuantity = 42;
		
		//test fields for energy
		final double testOldEnergy = 543.21;
		final double testNewEnergy = 123.45;
		
		//adds the an item with the test fields and adds it to the itemList
		Item item = new Item(testName, testPrice, testQuantity);
		itemList.add(item);
		
		//adds the an energy with the test fields and adds it to the energyList
		Energy energy = new Energy(testOldEnergy,testNewEnergy);
		energyList.add(energy);
		
		//creates a data with the created list 
		Data data = new Data(itemList, energyList);
		
		//test that the itemList is correct
		assertEquals("Constructor Does not work for Item", item.toString(), data.getItemList().get(0).toString());
		//test that the energyList is correct
		assertEquals("Constructor Does not work for Energy", energy.toString(), data.getEnergyList().get(0).toString());
	}
	
	/**
	 * Tests the totalCost() method.
	 * @author Chris Kim
	 * @version 12/11/17
	 */
	@Test
	public void testTotalCost() {
		Data test = new Data();
		double total = 3.0;
		test.addItem("Name", 1.5, 2);
		assertEquals("totalCost returns the wrong value.", total, test.totalCost(), 0.001);
	}
	
	/**
	 * Tests the totalNewEnergy() and totalOldEnergy() methods.
	 * @author Tyler Shupack
	 * @version 12/11/17 
	 */

	@Test
	public void testTotalEnergy() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		
		Energy energy1 = new Energy(6, 5);
		Energy energy2 = new Energy(3, 2);
		
		energyList.add(energy1);
		energyList.add(energy2);
		
		Data data = new Data(itemList, energyList);
		
		assertEquals("Old energy total is incorrect", 9, data.totalOldEnergy(), .01);
		assertEquals("New energy total is incorrect", 7, data.totalNewEnergy(), .01);
		
		assertNotEquals("Testing old energy total for incorrect assertion", 7, data.totalOldEnergy(), .01);
		assertNotEquals("Testing new energy total for incorrect assertion", 8, data.totalNewEnergy(), .01);
		
	}
	
	/**
	 * Tests the totalSavings() method.
	 * @author Tyler Shupack
	 * @version 12/11/17 
	 */

	@Test
	public void testTotalSavings() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		
		Energy energy1 = new Energy(6, 4);
		Energy energy2 = new Energy(3, 2);
		
		energyList.add(energy1);
		energyList.add(energy2);
		
		Data data = new Data(itemList, energyList);
		
		assertEquals("Total savings is incorrect", 8.64, data.totalSavings(), .01);
		
		assertNotEquals("Testing total savings for incorrect assertion", 8, data.totalSavings(), .01);
		
	}
	
	/**
	 * Tests the totalBreakEven() method.
	 * @author Tyler Shupack
	 * @version 12/11/17 
	 */

	@Test
	public void testBreakEven() {
		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Energy> energyList = new ArrayList<Energy>();
		
		Energy energy1 = new Energy(6, 4);
		Energy energy2 = new Energy(3, 2);
		
		Item item1 = new Item("shit", 8.0, 8);
		
		itemList.add(item1);
		energyList.add(energy1);
		energyList.add(energy2);
		
		Data data = new Data(itemList, energyList);
		
		assertEquals("Break even is incorrect", 8, data.breakEven(), .01);
		
		assertNotEquals("Testing total savings for incorrect assertion", 7, data.breakEven(), .01);
		
	}
}
