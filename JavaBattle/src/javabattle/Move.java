package javabattle;

import java.util.Random;

/**
 * Class for moves. 
 * Stores name, minimum & maximum power, accuracy, SP cost, type, message to print 
 *	when used, and an array of messages to print when it misses/fails. 
 * Also stores status effects (if applicable), such as stat changes or conditions.
 * <p>Computes power by returning a random number between the min and max.
 * @author Zac Hayes
 */
public class Move
{
	public MoveKind kind;
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
	private boolean specialCase;
	
	private Random myRandom = new Random();
	
	/**
	 * Default constructor for {@link MoveKind.NORMAL}-kind moves
	 * @param moveKind Integer (NOT zero-based) corresponding to which {@link MoveKind} the move is (e.g. 1 would be a {@link MoveKind.NORMAL}-kind move)
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
	public Move(int moveKind, int threshold, String name, int min, int max, int accuracy, int sp, String type, String usageMsgs, String[] missMsgs, String desc)
	{
		this.kind = MoveKind.values()[moveKind - 1];
		this.battleThreshold = threshold;
		this.name = name;
		this.minimumPower = min;
		this.maximumPower = max;
		this.accuracy = accuracy;
		this.SPcost = sp;
		this.type = MoveType.fromString(name);
		this.usageMessage = usageMsgs;
		this.missMessages = missMsgs;
		this.description = desc;
		this.specialCase = false;
	}
	
	// TODO add other ctors

	/**
	 * @return Move's maximum power
	 */
	public int getMaximumPower() { return maximumPower;	}
	
	/**
	 * Generates a random value between the move's minimum and maximum power values
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
	
	// TODO continue adding Move class methods
}