package net.pottercraft.Ollivanders2.Effect;

import net.pottercraft.Ollivanders2.Effects;
import net.pottercraft.Ollivanders2.OEffect;
import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.entity.Player;

public class BARUFFIOS_BRAIN_ELIXIR extends OEffect implements Effect {
	private static final long serialVersionUID = 1383219938049231470L;

	public BARUFFIOS_BRAIN_ELIXIR(Player sender, Effects effect, int duration) {
		super(sender, effect, duration);
	}

	@Override
	public void checkEffect(Ollivanders2 p, Player owner) {
		age(1);
	}
}
