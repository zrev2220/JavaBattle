package javabattle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
	 *  input received from the output window/command line. This version of the
	 *  game is run when the program is run with the argument {@code -console}.
	 */
	public static void runConsoleVersion()
	{
		Scanner input = new Scanner(System.in);
		doSetup(input);
		doBattle(input);
	}

	private static void doSetup(Scanner input)
	{
		System.out.println("Welcome to JavaBattle!\n");
		System.out.println("----Setup----");
		String p1name, p2name;
		int p1hp, p1sp, p2hp, p2sp;
		String moveset = "Default";
		boolean setupDone = false;
		do
		{
			Validator<String> numberValidator = new Validator<String>()
			{
				@Override
				public String validate(String thing)
				{
					if (thing.contains("."))
						return "!! \"" + thing + "\" is a decimal number! Enter an integer!";
					try
					{
						int n = Integer.valueOf(thing);
						if (n < 0)
							return "!! \"" + thing + "\" is a negative number! Enter something positive!";
					} catch (NumberFormatException ex)
					{
						if (!thing.equals(""))
							return "!! \"" + thing + "\" is not a number! Enter a number this time!";
					}
					return null;
				}
			};
			boolean exit = false;
			do
			{
				System.out.print("Player 1's name: ");
				p1name = input.nextLine();
				if (p1name.equals(""))
				{
					System.out.println("!! You didn't enter anything! That's not gonna work!");
					continue;
				}
				else if (p1name.length() > 16)
				{
					System.out.println("!! That name's too long. Pick a shorter one (less than 17 characters).");
					continue;
				}
				exit = true;
			} while (!exit);
			p1hp = prompt("Player 1's HP (default 300): ", input, numberValidator, 300);
			p1sp = prompt("Player 1's SP (default 50): ", input, numberValidator, 50);
			exit = false;
			do
			{
				System.out.print("Player 2's name: ");
				p2name = input.nextLine();
				if (p2name.equals(""))
				{
					System.out.println("!! You didn't enter anything! That's not gonna work!");
					continue;
				}
				else if (p2name.equals(p1name))
				{
					System.out.println("!! That's the same as Player 1's name! You'll have to enter something else!");
					continue;
				}
				else if (p1name.length() > 16)
				{
					System.out.println("!! That name's too long. Pick a shorter one (less than 17 characters).");
					continue;
				}
				exit = true;
			} while (!exit);
			p2hp = prompt("Player 2's HP (default 300): ", input, numberValidator, 300);
			p2sp = prompt("Player 2's SP (default 50): ", input, numberValidator, 50);
			// load move files from resources folder
			File movesetsFolder = new File("resources/movesets");
			ArrayList<String> movesetFiles = new ArrayList<>(Arrays.asList(movesetsFolder.list()));
			if (movesetFiles.size() != 1)
			{
				System.out.println("Movesets:");
				ArrayList<String> choices = new ArrayList<>();
				boolean defaultPresent = movesetFiles.contains("Default.mv");
				int count = 0;
				if (defaultPresent)
				{
					choices.add("Default.mv");
					System.out.println("  1 - Default");
					count = 2;
				}
				for (String file : movesetFiles)
				{
					if (!(defaultPresent && file.equals("Default.mv")))
					{
						choices.add(file);
						System.out.println("  " + count + " - " + file.substring(0, file.lastIndexOf(".mv")));
						count++;
					}
				}
				int choice = prompt("Choose a moveset: ", input, new Validator<String>()
				{
					@Override
					public String validate(String thing)
					{
						if (thing.contains("."))
							return "!! \"" + thing + "\" is a decimal number! Enter an integer!";
						int numValue;
						try
						{
							numValue = Integer.valueOf(thing);
						} catch (NumberFormatException ex)
						{
							return "!! \"" + thing + "\" is not a number! Enter a number this time!";
						}
						if (numValue < 1 || numValue > choices.size())
							return "!! " + thing + " is outside the range of moveset choices! Choose a number between 1 and " + choices.size() + "!";
						return null;
					}
				});
				moveset = movesetFiles.get(choice - 1).substring(0, movesetFiles.get(choice).lastIndexOf(".mv"));
			}

			// confirm setup
			System.out.println("Let's confirm:");
			System.out.println("  Player 1: " + p1name + " -- " + p1hp + "HP " + p1sp + "SP");
			System.out.println("  Player 2: " + p2name + " -- " + p2hp + "HP " + p2sp + "SP");
			System.out.println("  Using moveset: " + moveset);
			String response;
			exit = false;
			do
			{
				System.out.print("Is this correct? (Y/N) ");
				response = input.nextLine().toUpperCase();
				if (response.equals(""))
				{
					System.out.println("!! You didn't enter anything! That's not gonna work!");
					continue;
				}
				if (!(response.equals("Y") || response.equals("N")))
				{
					System.out.println("!! That's not a Y or an N! Enter a Y for yes or an N for no!");
					continue;
				}
				exit = true;
			} while (!exit);
			if (response.equals("Y"))
			{
				System.out.println("Okay, to battle!\n");
				moveset = "resources/movesets/" + moveset + ".mv";
				setupDone = true;
			}
			else
			{
				System.out.println("All right, then let's try again....\n");
			}
		}
		while (!setupDone);
		// configure JavaBattle instance
		PlayerData p1 = new PlayerData(0, p1name, p1hp, p1sp, "");
		PlayerData p2 = new PlayerData(1, p2name, p2hp, p2sp, "");
		JavaBattle.getInstance().config(new PlayerData[]{p1, p2}, moveset);
	}

	private static void doBattle(Scanner input)
	{
		PlayerTurn turn;
		Random myRandom = new Random();
		boolean doneBattling = false;
		do
		{
			// print moves and player status
			JavaBattle.getInstance().setAvailableMoves();
			System.out.println("----Moves----");
			PlayerData[] player = JavaBattle.getInstance().player;
			for (int i = 0; i < 4; i++)
			{
				System.out.println(" " + (i + 1) + " - " + JavaBattle.getInstance().availableMoves.get(i).name);
			}
			String nameFlag = "%-" + String.valueOf(Math.max(player[0].name.length(), player[1].name.length())) + "s";
			String hpFlag = String.valueOf("%" + String.valueOf(Math.max(player[0].maxHP, player[1].maxHP)).length() + "d");
			System.out.printf(nameFlag + ": " + hpFlag + "HP   %sSP\n", player[0].name, player[0].HP, player[0].SP);
			System.out.printf(nameFlag + ": " + hpFlag + "HP   %sSP\n", player[1].name, player[1].HP, player[1].SP);
			// get players' moves
			Validator<String> intRangeValidator = new Validator<String>()
			{
				@Override
				public String validate(String thing)
				{
					if (thing.contains("."))
						return "!! \"" + thing + "\" is a decimal number! Enter an integer!";
					int numValue;
					try
					{
						numValue = Integer.valueOf(thing);
					} catch (NumberFormatException ex)
					{
						return "!! \"" + thing + "\" is not a number! Enter a number this time!";
					}
					if (numValue < 1 || numValue > 4)
						return "!! " + thing + " is outside the range of moveset choices! Choose a number between 1 and 4!";
					return null;
				}
			};
			int selection = prompt(player[0].name + ", choose your move: ", input, intRangeValidator);
			player[0].nextMove = JavaBattle.getInstance().availableMoves.get(selection - 1);
			selection = prompt(player[1].name + ", choose your move: ", input, intRangeValidator);
			player[1].nextMove = JavaBattle.getInstance().availableMoves.get(selection - 1);
			// determine which player goes first
			int firstRand = myRandom.nextInt(2);
			PlayerData firstPlayer = player[firstRand];
			PlayerData secondPlayer = player[firstRand ^ 1];
			// execute moves
			MoveResults result = firstPlayer.executeMove(secondPlayer);
			System.out.println(firstPlayer.getMoveUsageMessage(secondPlayer));
			if (result.hit)
				System.out.printf("%sHP of damage to %s!\n", result.damage, secondPlayer.name);
			else
				System.out.println(firstPlayer.getMoveMissMessage(secondPlayer));
			if (!result.critical)
			{
				// secondPlayer's turn
				result = secondPlayer.executeMove(firstPlayer);
				System.out.println(secondPlayer.getMoveUsageMessage(firstPlayer));
				if (result.hit)
					System.out.printf("%sHP of damage to %s!\n", result.damage, firstPlayer.name);
				else
					System.out.println(secondPlayer.getMoveMissMessage(firstPlayer));
			}
			doneBattling = result.critical;
			// TODO Add SP values to moves (so we can see what stuff costs); Add end game sequence; Check SMAAAASHing?
		} while (!doneBattling);
	}

	private static int prompt(String msg, Scanner in, Validator<String> validator)
	{
		int result = 0;
		String response = "";
		boolean exit = false;
		while (!exit)
		{
			System.out.print(msg);
			response = in.nextLine();
			try
			{
				String valid = validator.validate(response);
				if (valid != null)
					throw new Exception(valid);
				result = Integer.valueOf(response);
				exit = true;
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		return result;
	}

	private static int prompt(String msg, Scanner in, Validator<String> validator, int auto)
	{
		int result = 0;
		String response = "";
		boolean exit = false;
		while (!exit)
		{
			System.out.print(msg);
			response = in.nextLine();
			try
			{
				String valid = validator.validate(response);
				if (valid != null)
					throw new Exception(valid);
				if (response.equals(""))
					// pressed enter with no input, use <auto> value
					return auto;
				result = Integer.valueOf(response);
				exit = true;
			}
			catch (Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		return result;
	}
}

interface Validator<T>
{
	public String validate(T thing);
}