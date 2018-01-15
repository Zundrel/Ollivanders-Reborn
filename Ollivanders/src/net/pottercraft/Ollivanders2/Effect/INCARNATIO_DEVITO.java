package net.pottercraft.Ollivanders2.Effect;

import net.pottercraft.Ollivanders2.Effects;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created by Azami7 on 6/27/17.
 *
 * Turn target player in to a chicken.
 *
 * @see IncarnatioEffectSuper
 * @version Ollivanders2
 * @author lownes
 * @author Azami7
 */
public class INCARNATIO_DEVITO extends IncarnatioEffectSuper {
	private static final long serialVersionUID = 5022656918536598190L;

	public INCARNATIO_DEVITO(Player sender, Effects effect, int duration) {
		super(sender, effect, duration);

		animalShape = EntityType.CHICKEN;
		name = "Chicken";
	}
}
