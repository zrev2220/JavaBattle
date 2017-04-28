package javabattle;

/**
 *
 * @author Zac Hayes
 */
public class Statistics
{
	private static Statistics instance;

	public int turns;
	public String[] result;
	public String[] name;
	public int[] damageGiven;
	public int[] recovered;
	public int[] hitPercentage;
	public int[] smaaaashes;
	public int[] SPused;
	public int[] SPrecovered;
	public int[] defended;
	public int[] rested;

	private Statistics()
	{
		this.turns = 0;
		this.result = new String[]{"", ""};
		this.name = new String[]{"", ""};
		this.damageGiven = new int[]{0, 0};
		this.recovered = new int[]{0, 0};
		this.hitPercentage = new int[]{0, 0};
		this.smaaaashes = new int[]{0, 0};
		this.SPused = new int[]{0, 0};
		this.SPrecovered = new int[]{0, 0};
		this.defended = new int[]{0, 0};
		this.rested = new int[]{0, 0};
	}

	public static Statistics getInstance()
	{
		if (instance == null)
			instance = new Statistics();
		return instance;
	}

	public void update(PlayerTurn[] turns)
	{
		if (turns.length != 2)
			return;
		this.turns++;
		this.name[0] = JavaBattle.getInstance().player[0].name;
		this.name[1] = JavaBattle.getInstance().player[1].name;
		// determine which element of turns is for p1
		int p1 = (turns[0].user.name.equals(this.name[0])) ? 1 : 0;
		for (PlayerTurn t : turns)
		{
			// TODO: Enable various elements when they work
			this.damageGiven[p1] += t.result.damage;
//			this.recovered
//			this.hitPercentage TODO: Keep track of misses too and calculate this
			this.smaaaashes[p1] += (t.result.smaaaash) ? 1 : 0;
		}
	}
}
