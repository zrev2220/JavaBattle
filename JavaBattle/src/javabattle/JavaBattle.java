package javabattle;
/* TODO:
	- Write PlayerTurn class to execute a player's move and record results (hit/miss,
SMAAAASH, KO, etc.)
	- Write Move.execute()
	- Write command line code and get a basic battle working
	- Write Statistics class to hold # of turns, etc.
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Singleton class holding all variables for a battle: two PlayerData objects
 * for each player, list of Move objects, PlayerTurn objects, Statistics object, etc.
 * @author Zac Hayes
 */
public class JavaBattle
{
	private static JavaBattle instance;

	public PlayerData[] player;
	public ArrayList<Move> moves;
	public ArrayList<Move> availableMoves;

	public Random myRandom = new Random();

	private JavaBattle()
	{
		this.player = new PlayerData[2];
		this.moves = new ArrayList<>();
		this.availableMoves = new ArrayList<>();
	}

	public static JavaBattle getInstance()
	{
		if (instance == null)
			instance = new JavaBattle();
		return instance;
	}

	public void config(PlayerData[] players, String movesetFilename)
	{
		this.player = players;
		try
		{
			MoveFile moveFile = new MoveFile(movesetFilename);
			this.moves = moveFile.getMoves();
		} catch (IOException ex)
		{
			System.err.println("Error reading move file - " + ex.getMessage());
		}
		setAvailableMoves();
	}

	public Move strToMove(String str)
	{
		for (Move move : moves)
		{
			if (move.name.equals(str))
				return move;
		}
		return null;
	}

	private void setAvailableMoves()
	{
		availableMoves.clear();
		for (int i = 0; i < 4; i++)
		{
			Move selected;
			do
			{
				selected = moves.get(myRandom.nextInt(moves.size()));
			}
			while (availableMoves.contains(selected));
			availableMoves.add(selected);
		}
	}
}