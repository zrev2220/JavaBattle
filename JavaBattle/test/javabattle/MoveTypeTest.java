/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javabattle;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zac Hayes
 */
public class MoveTypeTest
{
	public MoveTypeTest() {}

	/**
	 * Test of getName method, of class MoveType.
	 */
	@Test
	public void testGetName()
	{
		System.out.println("getName");
		MoveType instance = MoveType.PHYSICAL_MELEE;
		String expResult = "Physical - Melee";
		String result = instance.getName();
		assertEquals(expResult, result);
		instance = MoveType.SPECIAL_PROJECTILE;
		expResult = "Special - Projectile";
		result = instance.getName();
		assertEquals(expResult, result);
	}

	/**
	 * Test of fromString method, of class MoveType.
	 */
	@Test
	public void testFromString()
	{
		System.out.println("fromString");
		String name = "Physical - Melee";
		MoveType expResult = MoveType.PHYSICAL_MELEE;
		MoveType result = MoveType.fromString(name);
		assertEquals(expResult, result);
	}
}
