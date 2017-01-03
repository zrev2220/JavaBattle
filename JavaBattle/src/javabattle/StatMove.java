package javabattle;

/**
 *
 * @author Zac Hayes
 */
public class StatMove extends Move
{
	
	public StatMove(int threshold, String name, int min, int max, int accuracy, int sp, String type, String usageMsgs, String[] missMsgs, String desc)
	{
		super(threshold, name, min, max, accuracy, sp, type, usageMsgs, missMsgs, desc);
	}
	
}
