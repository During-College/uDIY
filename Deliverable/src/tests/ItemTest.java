package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uDIY.Item;

/**
 * Item Test Class
 * @author Tyler Shupack
 * @version 12/11/2017
 */
public class ItemTest {

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
	
	ArrayList<Item> itemList = new ArrayList<Item>();
	
	Item item1 = new Item("shit", 1.5, 2);
	Item item2 = new Item("test", 3.0, 4);
	
	/**
	 * tests the constructor for the item class
	 * @author 
	 * @version 12/11/2017
	 */
	@Test
	public void testItemConstructor() {
		
	}
	
	/**
	 * tests the totalPrice() method
	 * @author Tyler Shupack
	 * @version 12/11/2017
	 */
	@Test
	public void testTotalPrice() {
		assertEquals("item1 had an incorrect total", 3, item1.TotalPrice(), .01);
		assertEquals("item2 had an incorrect total", 12, item2.TotalPrice(), .01);
		
		assertNotEquals("testing incorrect assertion for item1", 2, item1.TotalPrice(), .01);
		assertNotEquals("testing incorrect assertion for item2", 11, item1.TotalPrice(), .01);
	}
	
}