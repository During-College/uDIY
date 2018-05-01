package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uDIY.Energy;

/**
 * Energy Test Class
 * @author Tyler Shupack, Matthew Hwang
 * @version 12/11/2017
 */
public class EnergyTest {

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
	
	ArrayList<Energy> energyList = new ArrayList<Energy>();
	
	private double oldEnergy = 4;
	private double newEnergy = 2;
	
	Energy energy = new Energy(oldEnergy, newEnergy);
	
	/**
	 * tests the constructor for the energy class
	 * @author Matthew Hwang
	 * @version 12/11/2017
	 */
	@Test
	public void testEnergyConstructor() {
		// Empty constructor test
		Energy energy1 = new Energy();
		assertEquals("empty constructor for Energy has wrong old value", 0.0, energy1.getOld(), 0.01);
		assertEquals("empty constructor for Energy has wrong new value", 0.0, energy1.getNew(), 0.01);
		
		Energy energy2 = new Energy(5.2, 2.7);
		assertEquals("filled constructor for Energy has wrong old value", 5.2, energy2.getOld(), 0.01);
		assertEquals("filled constructor for Energy has wrong new value", 2.7, energy2.getNew(), 0.01);
	}
	
	/**
	 * tests the percentSaved() method
	 * @author Tyler Shupack
	 * @version 12/11/2017
	 */
	@Test
	public void testPercentSaved() {
		assertEquals("Percent saved is incorrect", .50, energy.percentSaved(), .01);
		
		assertNotEquals("Testing percent saved for incorrect assertion", .25, energy.percentSaved(), .01);
		
	}
	
	/**
	 * tests the getDifference() method
	 * @author Tyler Shupack
	 * @version 12/11/2017
	 */
	@Test
	public void testGetDifference() {
		assertEquals("Percent saved is incorrect", 2, energy.getDifference(), .01);
		
		assertNotEquals("Testing percent saved for incorrect assertion", 1, energy.getDifference(), .01);
		
	}

}
