package javabattle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Runs console version of game. All prompts and results are printed to and
 *  input received from the output window/command line. This version of the
 *  game is run when the program is run with the argument {@code -console}.
 * @author Zac Hayes
 */
public class ConsoleVersion
{
	public static void start()
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
		PlayerTurn turn; // TODO: <turn> not used. ...Use it?
		Random myRandom = new Random();
		boolean doneBattling = false;
		PlayerData[] player = JavaBattle.getInstance().player;
		do
		{
			// print moves and player status
			JavaBattle.getInstance().setAvailableMoves();
			printMovesAndStatus(player);
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
					if (numValue < 0 || numValue > 4)
						return "!! " + thing + " is outside the range of moveset choices! Choose a number between 1 and 4!";
					return null;
				}
			};
			getMoveSelection(player[0], input, intRangeValidator);
			getMoveSelection(player[1], input, intRangeValidator);
			// determine which player goes first
			int firstRand = myRandom.nextInt(2);
			PlayerTurn first = new PlayerTurn(player[firstRand], player[firstRand ^ 1]);
			PlayerTurn second = new PlayerTurn(player[firstRand ^ 1], player[firstRand]);
			// execute moves
			first.execute();
			MoveResults result = first.result;
			System.out.println(first.useMsg);
			pause(1500);
			if (result.smaaaash)
			{
				System.out.println("\nSMAAAASH!!\n");
				pause(1500);
			}
			if (result.hit)
				System.out.printf("%sHP of damage to %s!\n", result.damage, second.user.name);
			else
				System.out.println(first.missMsg);
			pause(1500);
			if (!result.critical)
			{
				// secondPlayer's turn
				second.execute();
				result = second.result;
				System.out.println(second.useMsg);
				pause(1500);
				if (result.smaaaash)
				{
					System.out.println("\nSMAAAASH!!\n");
					pause(1500);
				}
				if (result.hit)
					System.out.printf("%sHP of damage to %s!\n", result.damage, first.user.name);
				else
					System.out.println(second.missMsg);
				pause(1500);
			}
			doneBattling = result.critical;
			if (!doneBattling)
				System.out.println();
		} while (!doneBattling);

		PlayerData defeatedPlayer = (player[0].HP == 0) ? player[0] : player[1];
		PlayerData winner = (defeatedPlayer.equals(player[0])) ? player[1] : player[0];
		int r = myRandom.nextInt(100);
		if (r < 40)
			System.out.println(defeatedPlayer.name + " got hurt and collapsed...");
		else if (r < 60)
			System.out.println(defeatedPlayer.name + " was KO'd!");
		else if (r < 80)
			System.out.println(defeatedPlayer.name + " fainted...");
		else if (r < 90)
			System.out.println(defeatedPlayer.name + " has taken enough damage and decided to quit.");
		else
			System.out.println(defeatedPlayer.name + " has had enough of this nonsense.");

		pause(2000);
		System.out.println("\n" + winner.name + " won!");
	}

	private static void getMoveSelection(PlayerData player, Scanner input, Validator<String> intRangeValidator)
	{
		int selection = prompt(player.name + ", choose your move (0 to quit): ", input, intRangeValidator);
		if (selection == 0)
		{
			System.out.println("The battle has ended sooner than was expected. Now go awkwardly in peace.");
			pause(1500);
			System.exit(0);
		}
		player.nextMove = JavaBattle.getInstance().availableMoves.get(selection - 1);
	}

	private static void printMovesAndStatus(PlayerData[] player)
	{
		System.out.println("----Moves----");
		// determine length of longest move name to set format flag
		int maxLength = 0;
		int maxPowStrLength = 0;
		int maxAccLength = 0;
		int maxSPLength = 0;
		for (Move move : JavaBattle.getInstance().availableMoves)
		{
			maxLength = (move.name.length() > maxLength) ? move.name.length() : maxLength;
			maxPowStrLength = (move.getPowerRangeString().length() > maxPowStrLength) ? move.getPowerRangeString().length() : maxPowStrLength;
			maxAccLength = (String.valueOf(move.accuracy).length() > maxAccLength) ? String.valueOf(move.accuracy).length() : maxAccLength;
			maxSPLength = (String.valueOf(move.SPcost).length() > maxSPLength) ? String.valueOf(move.SPcost).length() : maxSPLength;
		}
		maxAccLength++;
		String moveNameFlag = "%-" + String.valueOf(maxLength) + "s";
		String powStrFlag = "%-" + String.valueOf(maxPowStrLength) + "s";
		String accFlag = "%-" + String.valueOf(maxAccLength) + "s";
		String moveSPFlag = "%-" + String.valueOf(maxSPLength) + "s";
		for (int i = 0; i < 4; i++)
		{
			Move theMove = JavaBattle.getInstance().availableMoves.get(i);
			System.out.printf(" %d - " + moveNameFlag + "  Pow: " + powStrFlag + "  Acc: " + accFlag + "  SP: " + moveSPFlag + "\n",
				(i + 1),
				theMove.name,
				theMove.getPowerRangeString().replace("HP", ""),
				theMove.accuracy + "%",
				theMove.SPcost);
		}
		String nameFlag = "%-" + String.valueOf(Math.max(player[0].name.length(), player[1].name.length())) + "s";
		String hpFlag = String.valueOf("%" + String.valueOf(Math.max(player[0].maxHP, player[1].maxHP)).length() + "d");
		String spFlag = String.valueOf("%" + String.valueOf(Math.max(player[0].maxSP, player[1].maxSP)).length() + "d");
		System.out.printf(nameFlag + ": " + hpFlag + "HP  " + spFlag + "SP\n", player[0].name, player[0].HP, player[0].SP);
		System.out.printf(nameFlag + ": " + hpFlag + "HP  " + spFlag + "SP\n", player[1].name, player[1].HP, player[1].SP);
	}

	private static void pause(int delay)
	{
		try
		{
			Thread.sleep(delay);
		} catch (InterruptedException ex)
		{
			System.err.println("Something went wrong: " + ex.getMessage());
		}
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