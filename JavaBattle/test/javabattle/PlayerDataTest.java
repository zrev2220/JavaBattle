package javabattle;

import org.junit.Test;

/**
 * Test class for PlayerData
 * @author Zac Hayes
 */
public class PlayerDataTest
{
	/**
	 * Default constructor
	 */
	public PlayerDataTest() {}
	
	/**
	 * Test method
	 */
	@Test
	public void testSomeMethod()
	{
		PlayerData p = new PlayerData(0, "Me", 10, 5, "nofile.png");
		assert p.name.equals("Me");
		assert p.HP == 10;
		assert p.maxHP == 10;
		assert p.SP == 5;
		assert p.maxSP == 5;
		assert p.sprite.equals("nofile.png");
	}
}