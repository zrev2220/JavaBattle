// TODO Add test class
package javabattle;

import java.security.InvalidParameterException;

/**
 * Enum for types of moves
 * @author Zac Hayes
 */
public enum MoveType
{
	PHYSICAL_MELEE ("Physical - Melee"),
	PHYSICAL_PROJECTILE ("Physical - Projectile"),
	SPECIAL_MELEE ("Special - Melee"),
	SPECIAL_PROJECTILE ("Special - Projectile"),
	STATUS ("Status"),
	HEALING_PHYSICAL ("Healing - Physical"),
	HEALING_SPECIAL ("Healing - Special"),
	DEFENSE_GUARD ("Defense - Guard"),
	DEFENSE_COUNTER ("Defense - Counter"),
	DEFENSE_REFLECT ("Defense - Reflect"),
	DEFENSE_ABSORB ("Defense - Absorb"),
	OTHER ("Other");
	
	private final String name;

	private MoveType(String name_)
	{
		this.name = name_;
	}
	
	/**
	 * @return String representation of move type
	 */
	public String getName() { return this.name; }
	
	/**
	 * Returns a MoveType value corresponding to given {@code name}.
	 * @param name String value of requested type (in format as returned by {@link getName()})
	 * @return MoveType value corresponding to name
	 */
	public static MoveType fromString(String name)
	{
		for (MoveType type : MoveType.values())
		{
			System.out.println(type.getName());
			if (type.getName().equals(name))
				return type;
		}
		throw new InvalidParameterException("The given String does not correspond to any MoveType value.");
	}
}
