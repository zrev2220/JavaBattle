package javabattle;

/**
 * @author Zac Hayes
 */
public class MoveResults
{
	public boolean hit;
	public boolean insufficientSP;
	public boolean smaaaash;
	public int damage;
	public boolean critical;

	/**
	 * Constructor for a move result where the move missed, either by accuracy or insufficient SP.
	 * @param move The move used
	 * @param hit_ Boolean value for whether the move hit or missed
	 * @param noSp Boolean value for whether the user had enough SP to use the move or not
	 */
	public MoveResults(Move move, boolean hit_, boolean noSp)
	{
		this.hit = hit_;
		this.insufficientSP = noSp;
		this.smaaaash = false;
		this.damage = 0;
		this.critical = false;
	}

	/**
	 * Constructor for a move result where the move hit. Configures the MoveResult 
	 * object with a damage value and whether the attack was a SMAAAASH or if it 
	 * was a critical hit.
	 * @param move The move used
	 * @param hit_ Boolean value for whether the move hit or missed
	 * @param damage_ Amount of damage inflicted
	 * @param smaaaash_ Boolean value for whether or not the move was a SMAAAASH
	 * @param critical_ Boolean value for whether or not the move caused a critical hit (i.e. the target was KO'd)
	 */
	public MoveResults(Move move, boolean hit_, int damage_, boolean smaaaash_, boolean critical_)
	{
		this.hit = hit_;
		this.damage = damage_;
		this.smaaaash = smaaaash_;
		this.critical = critical_;
	}
}
