/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabattle;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zac Hayes
 */
public class MoveTest
{
	private Move testMove;
	
	public MoveTest() {}
	
	@Before
	public void BeforeTest()
	{
		System.out.println(MoveType.PHYSICAL_MELEE.getName());
		testMove = new Move(1, 0, "Move", 0, 10, 0, 0, MoveType.PHYSICAL_MELEE.getName(), "Used", new String[]{"Miss"}, "A move");
	}

	/**
	 * Test of getMaximumPower method, of class Move.
	 */
	@Test
	public void testGetMaximumPower()
	{
		System.out.println("getMaximumPower");
		int expResult = 10;
		int result = testMove.getMaximumPower();
		assertEquals(expResult, result);
	}

	/**
	 * Test of isSpecialCase method, of class Move.
	 */
	@Test
	public void testIsSpecialCase()
	{
		System.out.println("isSpecialCase");
		boolean expResult = false;
		boolean result = testMove.isSpecialCase();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setSpecialCase method, of class Move.
	 */
	@Test
	public void testSetSpecialCase()
	{
		System.out.println("setSpecialCase");
		boolean flag = true;
		testMove.setSpecialCase(flag);
		assert testMove.isSpecialCase();
		testMove.setSpecialCase(!flag);
		assert !testMove.isSpecialCase();
	}

	/**
	 * Test of getPower method, of class Move.
	 */
	@Test
	public void testGetPower()
	{
		System.out.println("getPower");
		int override = 7;
		int result = testMove.getPower(-1);
		assert result <= 10 && result >= 0; // 10 and 0 are the move's max and min power values
		result = testMove.getPower(override);
		assertEquals(override, result);
	}

	/**
	 * Test of getPowerRangeString method, of class Move.
	 */
	@Test
	public void testGetPowerRangeString()
	{
		System.out.println("getPowerRangeString");
		String expResult = "0-10";
		String result = testMove.getPowerRangeString();
		assertEquals(expResult, result);
	}
}
