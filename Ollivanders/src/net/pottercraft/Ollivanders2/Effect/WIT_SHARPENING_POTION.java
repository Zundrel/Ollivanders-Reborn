package net.pottercraft.Ollivanders2.Effect;

import net.pottercraft.Ollivanders2.Effects;
import net.pottercraft.Ollivanders2.OEffect;
import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.entity.Player;

/**
 * Makes book learning more effective.
 *
 * @author Azami7
 */
public class WIT_SHARPENING_POTION extends OEffect implements Effect {
	private static final long serialVersionUID = -5459390518392602982L;

	public WIT_SHARPENING_POTION(Player sender, Effects effect, int duration) {
		super(sender, effect, duration);
	}

	@Override
	public void checkEffect(Ollivanders2 p, Player owner) {
		age(1);
	}
}
