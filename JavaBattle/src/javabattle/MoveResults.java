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

	public MoveResults(Move move, boolean hit_, boolean noSp)
	{
		this.hit = hit_;
		this.insufficientSP = noSp;
		this.smaaaash = false;
		this.damage = 0;
		this.critical = false;
	}

	public MoveResults(Move move, boolean hit_, int damage_, boolean smaaaash_, boolean critical_)
	{
		this.hit = hit_;
		this.damage = damage_;
		this.smaaaash = smaaaash_;
		this.critical = critical_;
	}
}
