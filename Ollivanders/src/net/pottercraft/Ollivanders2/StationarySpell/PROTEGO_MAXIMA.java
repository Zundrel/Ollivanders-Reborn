package net.pottercraft.Ollivanders2.StationarySpell;

import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Hurts any entities within 0.5 meters of the spell wall.
 *
 * @author lownes
 */
public class PROTEGO_MAXIMA extends StationarySpellObj implements StationarySpell {
	private static final long serialVersionUID = -9049483736794110648L;
	double damage;

	public PROTEGO_MAXIMA(Player player, Location location, StationarySpells name, Integer radius, Integer duration, double damage) {
		super(player, location, name, radius, duration);
		this.damage = damage;
	}

	@Override
	public void checkEffect(Ollivanders2 p) {
		age();
		Location loc = location.toLocation();
		for (Entity e : loc.getWorld().getEntities()) {
			if (e instanceof Player) {
				Player ply = (Player) e;
				if (ply.isPermissionSet("Ollivanders2.BYPASS")) {
					if (ply.hasPermission("Ollivanders2.BYPASS")) {
						continue;
					}
				}
			}
			double distance = e.getLocation().distance(loc);
			if (distance > radius - 0.5 && distance < radius + 0.5) {
				if (e instanceof LivingEntity) {
					((LivingEntity) e).damage(damage);
				} else {
					e.remove();
				}
				flair(10);
			}
		}
	}
}