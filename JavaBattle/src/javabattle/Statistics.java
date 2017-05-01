package javabattle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

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
	public int[] hits;
	public int[] misses;
	public double[] hitPercentage;
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
		this.hits = new int[]{0, 0};
		this.misses = new int[]{0, 0};
		this.hitPercentage = new double[]{0.0, 0.0};
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

	public void update(PlayerTurn turn)
	{
		this.turns++;
		this.name[0] = JavaBattle.getInstance().player[0].name;
		this.name[1] = JavaBattle.getInstance().player[1].name;
		// determine which element of turns is for p1
		int p1 = (turn.user.name.equals(this.name[0])) ? 0 : 1;
		// TODO: Enable various elements when they work
		this.result[p1] = (JavaBattle.getInstance().player[p1].HP > JavaBattle.getInstance().player[p1 ^ 1].HP) ? "Victory" : "Defeat";
		this.damageGiven[p1] += turn.result.damage;
//			this.recovered
		if (turn.result.hit)
			this.hits[p1] += 1;
		else
			this.misses[p1] += 1;
		if (this.hits[p1] == 0 && this.misses[p1] == 0)
			this.hitPercentage[p1] = 0.0;
		else if (this.misses[p1] == 0)
			this.hitPercentage[p1] = 100.0;
		else
			this.hitPercentage[p1] = (double) this.hits[p1] / (this.hits[p1] + this.misses[p1]) * 100;
		this.smaaaashes[p1] += (turn.result.smaaaash) ? 1 : 0;
		if (!turn.result.insufficientSP)
			this.SPused[p1] += turn.move.SPcost;
//		this.SPrecovered
//		this.defended
//		this.rested
	}

	public void print()
	{
		try (PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("stats.txt"))))
		{
			outFile.printf("Turns: %d%n", this.turns);
			for (int i = 0; i < 2; i++)
			{
				this.result[i] = (JavaBattle.getInstance().player[i].HP > JavaBattle.getInstance().player[i ^ 1].HP) ? "Victory" : "Defeat";
				outFile.printf("==== %s ====\t%s%n", this.name[i], this.result[i]);
				outFile.printf("Damage Given: %d%n", this.damageGiven[i]);
//				outFile.printf("HP Recovered: %d%n", this.recovered[i]);
				DecimalFormat format = new DecimalFormat("#0.0");
				outFile.printf("Hit Percentage: %s%%%n", format.format(this.hitPercentage[i]));
				outFile.printf("SMAAAASH!! Attacks: %d%n", this.smaaaashes[i]);
				outFile.printf("SP Used: %d%n", this.SPused[i]);
//				outFile.printf("SP Recovered: %d%n", this.SPrecovered[i]);
//				outFile.printf("Turns Defending: %d%n", this.defended[i]);
//				outFile.printf("Turns Resting: %d%n", this.rested[i]);
				if (i == 0)
					outFile.println();
			}
			outFile.flush();
		}
		catch (IOException ex)
		{
			System.err.println("An error occurred while printing statistics: " + ex.getMessage());
		}
	}
}
