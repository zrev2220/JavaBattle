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
	public MoveResults result;
	public String useMsg;
	public String missMsg;
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
		this.result = null;
		this.useMsg = "";
		this.missMsg = "";

		this.executed = false;
	}

	/**
	 * Executes {@code user}'s {@code nextMove} using {@link Move.execute}. The
	 * {@code PlayerTurn}'s fields are then set to values based on the move's results.
	 */
	public void execute()
	{
		result = move.execute(user, target);
		if (result.insufficientSP)
		{
			useMsg = user.getMoveSPFailMessage(target);
			missMsg = user.getMoveMissMessage(target);
		}
		else
		{
			useMsg = user.getMoveUsageMessage(target);
			if (!result.hit)
				missMsg = move.getMissMessage(user, target);
		}
		executed = true;
	}
}
