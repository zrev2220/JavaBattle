package javabattle;

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
}