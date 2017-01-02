package javabattle;
// TODO Add methods for more complex queries to move list (e.g. how many cause conditions)

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Class for a text file containing move data (.mv files).
 * <p>Can read a .mv and store the moves it contains in an ArrayList, then 
 * return the list when requested. Also can provide move list statistics, such 
 * as number of moves meeting a certain criteria.
 * @author Zac Hayes
 */
public class MoveFile
{
	private ArrayList<Move> moves = null;
	private int numMoves = 0;
	public Move defend = null;
	public Move rest = null;
	
	private Random myRandom = new Random();
	
	/**
	 * Constructs a MoveFile object by reading the .mv file named {@code filename} 
	 * and storing the contained moves in an ArrayList.
	 * @param filename Filename of the .mv to open
	 * @throws java.io.IOException If the file doesn't exist, is not a .mv file, or if an exception occurs during the IO itself
	 */
	public MoveFile(String filename) throws IOException
	{
		// check if file exists
		File moveFile = new File(filename);
		if (!moveFile.exists())
			throw new IOException("The file \"" + moveFile.getName() + "\" does not exist.");
		// check if file is not a .mv file
		if (!moveFile.getName().endsWith(".mv"))
			throw new IOException("\"" + moveFile.getName() + "\" is not a move (.mv) file.");
		// read move file
		ArrayList<String> lineList = new ArrayList<>();
		try (BufferedReader inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8")))
		{
			for (int i = 0; inputFile.ready(); i++)
			{
				lineList.add(inputFile.readLine());
			}
		} catch (IOException ex)
		{
			throw ex;
		}
		// decipher data lines
		moves = new ArrayList<>();
		StringTokenizer myTokenizer;
		try
		{
			for (int i = 0; i < lineList.size(); i += 3)
			{
				// first line - general data
				myTokenizer = new StringTokenizer(lineList.get(i), "~");
				boolean isSpecialCase = false;
				MoveKind moveKind;
				try
				{
					moveKind = MoveKind.values()[Integer.valueOf(myTokenizer.nextToken()) - 1];
				} catch (NumberFormatException ex)
				{
					// special case move (kind no. had a '!' before it)
					String num = ex.getMessage().substring(20, ex.getMessage().lastIndexOf("\""));
					moveKind = MoveKind.values()[Integer.valueOf(num) - 1];
					isSpecialCase = true;
				}
				int threshold = Integer.valueOf(myTokenizer.nextToken());
				String name = myTokenizer.nextToken();
				int min = 0;
				int max = 0;
				double ratio = 0.0;
				if (moveKind != MoveKind.DEFENSE)
				{
					min = Integer.valueOf(myTokenizer.nextToken());
					max = Integer.valueOf(myTokenizer.nextToken());
				} else
				{
					ratio = Double.valueOf(myTokenizer.nextToken());
				}
				int acc = Integer.valueOf(myTokenizer.nextToken());
				int sp = Integer.valueOf(myTokenizer.nextToken());
				String type = myTokenizer.nextToken();
				// now a whole bunch of variables only a few of which we'll use
				String st_conType = "";
				int stMin_conChance = 0, stMax_conDur = 0;
				String[] condsHealed = null;
				if (moveKind == MoveKind.HEALING)
				{
					condsHealed = myTokenizer.nextToken().split("/");
				} else if (moveKind == MoveKind.STAT || moveKind == MoveKind.CONDITION)
				{
					st_conType = myTokenizer.nextToken();
					stMin_conChance = Integer.valueOf(myTokenizer.nextToken());
					stMax_conDur = Integer.valueOf(myTokenizer.nextToken());
				}
				String useTxt = myTokenizer.nextToken();
				// second line - miss messages
				myTokenizer = new StringTokenizer(lineList.get(i + 1), "~");
				String[] missMsgs = new String[myTokenizer.countTokens()];
				int j = 0;
				while (myTokenizer.hasMoreTokens())
				{
					missMsgs[j] = myTokenizer.nextToken();
					j++;
				}
				// third line - description
				String desc = lineList.get(i + 2);
				// construct move
				Move newMove = null;
				switch (moveKind)
				{
					// TODO Un-comment these cases once the other Move ctors work
//				case STAT:
//				case CONDITION:
//					newMove = new Move(moveKind, threshold, name, min, max, acc, sp, type, st_conType, stMin_conChance, stMax_conDur, useTxt, missMsgs, desc));
//					break;
//				case HEALING:
//					newMove = new Move(moveKind, threshold, name, min, max, acc, sp, type, condsHealed, useTxt, missMsgs, desc));
//					break;
//				case DEFENSE:
//					newMove = new Move(moveKind, threshold, name, ratio, acc, sp, type, useTxt, missMsgs, desc);
//					break;
					default:
						newMove = new Move(moveKind, threshold, name, min, max, acc, sp, type, useTxt, missMsgs, desc);
						break;
				}
				newMove.setSpecialCase(isSpecialCase);
				moves.add(newMove);
			}
		} catch (NoSuchElementException ex)
		{
			throw new IOException("The .mv file \"" + moveFile.getName() + "\" is not configured properly.");
		}
		// add Defend and Rest to moves
		// TODO Use the next line instead of the next next once DEFENSE-type moves are working
//		Move defend = new Move(MoveKind.DEFENSE, 0, "Defend", 0.0, 100, 0, MoveType.DEFENSE_GUARD.getName(), "* is on guard.", new String[]{"..."}, "...");
		defend = new Move(MoveKind.NORMAL, 0, "Defend", 0, 0, 0, 0, MoveType.DEFENSE_GUARD.getName(), "* is on guard.", new String[]{"..."}, "...");
		defend.setSpecialCase(true);
		rest = new Move(MoveKind.NORMAL, 0, "Rest", 0, 0, 0, 0, MoveType.OTHER.getName(), "* is resting.", new String[]{"..."}, "...");
		rest.setSpecialCase(true);
		
		this.numMoves = moves.size();
	}
	
	/**
	 * @return ArrayList of moves stored by the object
	 */
	public ArrayList<Move> getMoves() { return this.moves; }
	
	/**
	 * @return Number of moves stored by the object
	 */
	public int getNumMoves() { return this.numMoves; }
	
	/**
	 * Returns a random {@link Move} from the MoveFile's {@code moves} ArrayList
	 * @return A random {@link Move} from the objects list of moves
	 */
	public Move getRandomMove()
	{
		return this.moves.get(myRandom.nextInt(this.moves.size()));
	}
}
