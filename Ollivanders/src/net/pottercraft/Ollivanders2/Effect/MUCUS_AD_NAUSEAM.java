package net.pottercraft.Ollivanders2.Effect;

import net.pottercraft.Ollivanders2.Effects;
import net.pottercraft.Ollivanders2.OEffect;
import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

public class MUCUS_AD_NAUSEAM extends OEffect implements Effect {
	private static final long serialVersionUID = -2751435323287216249L;

	public MUCUS_AD_NAUSEAM(Player sender, Effects effect, int duration) {
		super(sender, effect, duration);
	}

	@Override
	public void checkEffect(Ollivanders2 p, Player owner) {
		age(1);
		if (duration % 20 == 0) {
			World world = owner.getWorld();
			Slime slime = (Slime) world.spawnEntity(owner.getEyeLocation(), EntityType.SLIME);
			slime.setSize(1);
		}
	}
}