package javabattle;

/**
 *
 * @author Zac Hayes
 */
public class PlayerTurn
{
	public PlayerData user;
	public PlayerData target;
	public Move move;
	public String useMsg;
	public String missMsg;
	public boolean hit;
	public boolean smaaaash;
	public int damage;
	public boolean critical;
	/* TODO Un-comment when these classes exist
	public StatusCondition condition;
	public StatChangeThingy statThing;
	public DefenseThingy defenseThing;
	
	   TODO Add these fields if necessary
	public int spRecovered;
	*/
	
	public boolean executed;

	/**
	 * Default constructor for PlayerTurn
	 * @param user_ PlayerData attacking this turn
	 * @param target_ PlayerData being attacked this turn
	 */
	public PlayerTurn(PlayerData user_, PlayerData target_)
	{
		this.user = user_;
		this.target = target_;
		this.move = user_.nextMove;
		this.useMsg = "";
		this.missMsg = "";
		this.hit = false;
		this.smaaaash = false;
		this.damage = 0;
		this.critical = false;
		this.executed = false;
	}
	
	/**
	 * Executes {@code user}'s {@code nextMove} using {@link Move.execute}. The 
	 * {@code PlayerTurn}'s fields are then set to values based on the turn's results.
	 */
	public void execute()
	{
		// TODO Implement Move.execute()
//		move.execute(...);
		useMsg = move.getUsageMessage(user, target);
		
		executed = true;
	}
}
