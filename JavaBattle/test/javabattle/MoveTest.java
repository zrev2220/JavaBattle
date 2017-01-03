/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabattle;

import java.util.Arrays;
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
		testMove = new Move(MoveKind.NORMAL, 0, "Move", 0, 10, 90, 0, MoveType.PHYSICAL_MELEE.getName(), "* used the test move on `!",
			new String[]{
				"* missed!",
				"Just missed!",
				"` barely evaded it!"},
			"A move");
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

	/**
	 * Test of getUsageMessage method, of class Move.
	 */
	@Test
	public void testGetUsageMessage()
	{
		System.out.println("getUsageMessage");
		PlayerData user = new PlayerData(0, "Joe", 100, 30, "");
		PlayerData target = new PlayerData(1, "Bill", 100, 30, "");
		String expResult = "Joe used the test move on Bill!";
		String result = testMove.getUsageMessage(user, target);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getMissMessage method, of class Move.
	 */
	@Test
	public void testGetMissMessage()
	{
		System.out.println("getMissMessage");
		PlayerData user = new PlayerData(0, "Tom", 100, 30, "");
		PlayerData target = new PlayerData(1, "Dick", 100, 30, "");
		String result = testMove.getMissMessage(user, target);
		assert Arrays.binarySearch(testMove.missMessages, result.replace(user.name, "*").replace(target.name, "`")) >= 0;
		Move anotherTestMove = new Move(testMove.kind, testMove.battleThreshold, "Another move", 0, 10, 90, 40, testMove.type.getName(), "* used another test move!",
			new String[]{
				"* tried to use another test move,",
				"* missed!",
				"Just missed!",
				"` barely evaded it!"},
			"Another test move.");
		String expResult = "Tom tried to use another test move,";
		result = anotherTestMove.getMissMessage(user, target);
		assertEquals(expResult, result);
	}
}
