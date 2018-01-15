package net.pottercraft.Ollivanders2.Effect;

import net.pottercraft.Ollivanders2.Effects;
import net.pottercraft.Ollivanders2.Ollivanders2;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created by Azami7 on 6/27/17.
 *
 * Turns target player in to a cow.
 *
 * @author lownes
 */
public class INCARNATIO_VACCULA extends IncarnatioEffectSuper {
	private static final long serialVersionUID = 5907186366547228088L;

	public INCARNATIO_VACCULA(Player sender, Effects effect, int duration) {
		super(sender, effect, duration);

		int rand = Math.abs(Ollivanders2.random.nextInt() % 100);
		if (rand == 0)
			animalShape = EntityType.MUSHROOM_COW;
		else
			animalShape = EntityType.COW;

		name = "Cow";
	}
}
