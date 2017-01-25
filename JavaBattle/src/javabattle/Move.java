package javabattle;

import java.util.Random;

/**
 * Class for moves.
 * Stores name, minimum & maximum power, accuracy, SP cost, type, message to print
 * when used, and an array of messages to print when it misses/fails.
 * Also stores status effects (if applicable), such as stat changes or conditions.
 * <p>Computes power by returning a random number between the min and max.
 * @author Zac Hayes
 */
public class Move
{
	public int battleThreshold;
	public String name;
	private int minimumPower;
	private int maximumPower;
	public double counterRatio;
	public int accuracy;
	public int SPcost;
	public MoveType type;
	public String usageMessage;
	public String[] missMessages;
	public String description;

	private Random myRandom = new Random();

	/**
	 * Default constructor for {@link MoveKind.NORMAL}-kind moves
	 * @param threshold Battle threshold; move will not be used in battles where both players start with less than this much HP
	 * @param name Move name
	 * @param min Move's minimum power
	 * @param max Move's maximum power
	 * @param accuracy Move's accuracy
	 * @param sp Move's SP cost
	 * @param type String value for the type of move, in the format as returned by {@link MoveKind.getName()}
	 * @param usageMsgs Message printed when move is used
	 * @param missMsgs Array of possible messages to print when move misses/fails. If move costs SP, the first element should be a message
	 * for when the user does not have enough SP.
	 * @param desc Description of move
	 */
	public Move(int threshold, String name, int min, int max, int accuracy, int sp, String type, String usageMsgs, String[] missMsgs, String desc)
	{
		this.battleThreshold = threshold;
		this.name = name;
		this.minimumPower = min;
		this.maximumPower = max;
		this.accuracy = accuracy;
		this.SPcost = sp;
		this.type = MoveType.fromString(type);
		this.usageMessage = usageMsgs;
		this.missMessages = missMsgs;
		this.description = desc;
	}

	// TODO add other ctors

	/**
	 * @return Move's maximum power
	 */
	public int getMaximumPower() { return this.maximumPower; }

	/**
	 * Generates a random value between the move's minimum and maximum power values.
	 * @param override If a fixed value is desired, returns this value. Generates random as usual if value is -1 or outside the min to max range
	 * @return Random value between min and max power
	 */
	public int getPower(int override)
	{
		if (!(override == -1 || (override < minimumPower && override > maximumPower)))
			// return override value
			return override;
		// else compute random value as usual
		int range = maximumPower - minimumPower;
		if (range == 0)
			return maximumPower;
		else
			return (minimumPower + myRandom.nextInt(range + 1));
	}

	/**
	 * Generates a random value between the move's minimum and maximum power values.
	 * Same as calling getPower(-1).
	 * @return Random value between min and max power
	 */
	public int getPower()
	{
		return getPower(-1);
	}

	/**
	 * @return String representation of move's power range, min to max
	 */
	public String getPowerRangeString()
	{
		// TODO Un-comment this when other move types work
//		if (this.healInt == 1 || this.healInt == 3)
//		{
//			// restores HP
//			if (minimumPower == maximumPower)
//				return String.valueOf("+" + maximumPower);
//			else
//				return String.valueOf("+" + minimumPower + "-+" + maximumPower);
//		}
//		else if (this.kindInt == Move.DEFENSE)
//		{
//			return "x" + String.valueOf(counterRatio);
//		}
//		else
		{
			if (minimumPower == maximumPower)
				return String.valueOf(maximumPower);
			else
				return String.valueOf(minimumPower + "-" + maximumPower);
		}
	}

	/**
	 * Returns the move's {@code usageMessage} with the user's name in place of
	 * every '*' and the target's name in place of every '`'.
	 * @param user PlayerData using the move
	 * @param target PlayerData the move is being used on
	 * @return This move's {@code usageMessage} with the user and target's names inserted
	 */
	public String getUsageMessage(PlayerData user, PlayerData target)
	{
		String msg = "";
		if (user.SP < this.SPcost)
			msg = this.missMessages[0];
		else
			msg = this.usageMessage;
		return msg.replace("*", user.name).replace("`", target.name);
	}

	/**
	 * Returns a random miss message from the move's {@code missMessages} array
	 * with the user's name in place of every '*' and the target's name in place
	 * of every '`'. If the user does not have enough SP to use the move, returns
	 * the first miss message (for when the user doesn't have enough SP).
	 * @param user PlayerData using the move
	 * @param target PlayerData the move is being used on
	 * @return Appropriate miss message with the user and target's names inserted
	 */
	public String getMissMessage(PlayerData user, PlayerData target)
	{
		String msg;
		boolean noSP = user.SP < this.SPcost;
		if (noSP)
			msg = "but * does not have enough SP!";
		else
			if (this.SPcost != 0)
				// can't pick the first one
				msg = this.missMessages[myRandom.nextInt(this.missMessages.length - 1) + 1];
			else
				msg = this.missMessages[myRandom.nextInt(this.missMessages.length)];
		return msg.replace("*", user.name).replace("`", target.name);
	}

	// TODO Add methods related to stats, conditions, healing, or defense

	/**
	 * Executes the move from {@code user} to {@code target}. Checks accuracy for
	 * miss/hit, inflicts damage, deducts SP, determines SMAAAASH, determines critical
	 * hit, and returns a {@link MoveResults} object reflecting these data.
	 * @param user PlayerData attacking with the move
	 * @param target PlayerData being attacked
	 * @return {@link MoveResults} object configured with the move's outcome data
	 */
	public MoveResults execute(PlayerData user, PlayerData target)
	{
		// check user's SP if move requires it
		if (this.SPcost != 0)
		{
			if (user.SP < this.SPcost)
				return new MoveResults(this, false, true);
			else
				// deduct sp and continue
				user.SP -= this.SPcost;
		}
		// see if move misses or not
		int rand = myRandom.nextInt(100);
		boolean miss = rand >= this.accuracy;
		if (miss)
		{
			// return miss
			return new MoveResults(this, false, false);
		}
		else
		{
			int power = getPower();
			// check for SMAAAASH
			boolean smaaaash = false;
			if (this.type == MoveType.PHYSICAL_MELEE)
			{
				rand = myRandom.nextInt(100);
				smaaaash = rand < 10;
				if (smaaaash)
					power *= 2.0 + myRandom.nextDouble();
			}
			target.HP -= power;
			// if critical hit, check guts for survival
			rand = myRandom.nextInt(100);
			if (target.HP <= 0 && rand < 10)
				// target survives with 1 HP
				target.HP = 1;
			// round target's HP to 0 if below
			if (target.HP <= 0)
				target.HP = 0;

			return new MoveResults(this, true, power, smaaaash, target.HP == 0);
		}
	}
}