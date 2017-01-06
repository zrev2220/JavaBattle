package javabattle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Zac Hayes
 */
public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
		
		if (argsList.contains("-console"))
		{
			runConsoleVersion();
		}
	}
	
	/**
	 * Runs console version of game. All prompts and results are printed to and 
	 * input received from the output window/command line. This version of the 
	 * game is run when the program is run with the argument {@code -console}.
	 */
	public static void runConsoleVersion()
	{
		// TODO Add exception handling for bad input (default for no input, try again otherwise)
		// TODO Add "Is this correct?" at end and start over if no
		// TODO Start battle once setup's over
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to JavaBattle!\n");
		System.out.println("----Setup----");
		do
		{			
			System.out.print("Player 1's name: ");
			String p1name = input.nextLine();
			System.out.print("Player 1's HP (default 300): ");
			int p1hp = input.nextInt();
			System.out.print("Player 1's SP (default 50): ");
			int p1sp = input.nextInt();
			input.nextLine();
			System.out.print("Player 2's name: ");
			String p2name = input.nextLine();
			System.out.print("Player 2's HP (default 300): ");
			int p2hp = input.nextInt();
			System.out.print("Player 2's SP (default 50): ");
			int p2sp = input.nextInt();
			// load move files from resources folder
			File movesetsFolder = new File("resources/movesets");
			ArrayList<String> movesetFiles = new ArrayList<>(Arrays.asList(movesetsFolder.list()));
			if (movesetFiles.size() != 1)
			{
				System.out.print("Movesets:");
				boolean defaultPresent = movesetFiles.contains("Default.mv");
				int count = 0;
				if (defaultPresent)
				{
					System.out.println("1 - Default");
					count = 2;
				}
				for (String file : movesetFiles)
				{
					System.out.println(count + file.substring(0, file.lastIndexOf(".mv")));
					count++;
				}
				System.out.print("Choose a moveset: ");
			}
		}
		while (true);
	}
}