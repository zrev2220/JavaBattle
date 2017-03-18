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
			ConsoleVersion.start();
		}
	}
}