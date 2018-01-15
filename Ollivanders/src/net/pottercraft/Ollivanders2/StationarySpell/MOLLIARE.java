package net.pottercraft.Ollivanders2.StationarySpell;

import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Negates fall damage.
 *
 * @author lownes
 */
public class MOLLIARE extends StationarySpellObj implements StationarySpell {
	private static final long serialVersionUID = 5478646518540746362L;

	public MOLLIARE(Player player, Location location, StationarySpells name, Integer radius, Integer duration) {
		super(player, location, name, radius, duration);
	}

	@Override
	public void checkEffect(Ollivanders2 p) {
		age();
	}
}