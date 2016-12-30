package javabattle;

/**
 * Stores a Player's name, HP, HP max, SP, SP max, sprite, next move, and defense state. 
 * Also stores stats (offense, defense, speed, and guts; scale of 0 - 20, normal value = 10)
 * @author Zac Hayes
 */
public class PlayerData
{
	public String name;
	public int HP;
	public int maxHP;
	public int SP;
	public int maxSP;
	public String sprite;
	public Move nextMove;
	
	/**
	 * PlayerData constructor
	 * @param name Player's name
	 * @param hp Player's HP; sets both current HP and max HP
	 * @param sp Player's SP; sets both current SP and max SP
	 * @param sprite Filename of player's sprite
	 */
	public PlayerData(String name, int hp, int sp, String sprite)
	{
		this.name = name;
		this.HP = hp;
		this.maxHP = hp;
		this.SP = sp;
		this.maxSP = sp;
		this.sprite = sprite;
		this.nextMove = null;
	}
}