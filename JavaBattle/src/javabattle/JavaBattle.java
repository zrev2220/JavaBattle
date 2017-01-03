package javabattle;
/* TODO:
	- Add Javadoc to Move.java, MoveResults.java
	- Write PlayerTurn class to execute a player's move and record results (hit/miss,
SMAAAASH, KO, etc.)
	- Write Move.execute()
	- Write command line code and get a basic battle working
	- Write Statistics class to hold # of turns, etc.
*/

import java.util.Random;

/**
 * Singleton class holding all variables for a battle: two PlayerData objects
 * for each player, list of Move objects, PlayerTurn objects, Statistics object, etc.
 * @author Zac Hayes
 */
public class JavaBattle
{
	private static JavaBattle instance;
	private JavaBattle() {}
	public static JavaBattle getInstance()
	{
		if (instance == null)
			instance = new JavaBattle();
		return instance;
	}
	
	public PlayerData[] player = new PlayerData[2];
	public Move[] moves = null;
	public Move[] availableMoves = new Move[4];
	
	public Random myRandom = new Random();
	
	public String getResourcePath(String filename)
	{
		return "";
	}
}