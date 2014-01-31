package Spell;

import java.util.List;

import me.cakenggt.Ollivanders.Ollivanders;
import me.cakenggt.Ollivanders.SpellProjectile;
import me.cakenggt.Ollivanders.Spells;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Disarms an entity of it's held item, flinging the item in the direction 
 * of the caster with force determined by the spell level.
 * @author lownes
 *
 */
public class EXPELLIARMUS extends SpellProjectile implements Spell{

	public EXPELLIARMUS(Ollivanders p, Player player, Spells name, Double rightWand){
		super(p, player, name, rightWand);
	}


	public void checkEffect() {
		move();
		List<LivingEntity> entities = getLivingEntities(1);
		if (entities.size() > 0){
			LivingEntity entity = entities.get(0);
			ItemStack itemInHand = entity.getEquipment().getItemInHand().clone();
			if (itemInHand.getType() != Material.AIR){
				if (holdsWand(entity)){
					allyWand(itemInHand);
				}
				entity.getEquipment().setItemInHand(null);
				allyWand(itemInHand);
				Item item = entity.getWorld().dropItem(entity.getEyeLocation(), itemInHand);
				item.setVelocity(player.getEyeLocation().toVector().subtract(item.getLocation().toVector()).normalize().multiply(usesModifier/10));
			}
			kill = true;
		}
	}

	/**Does the player hold a wand item?
	 * @param player - Player to check.
	 * @return True if the player holds a wand. False if not.
	 */
	public boolean holdsWand(LivingEntity entity){
		ItemStack held;
		if (entity.getEquipment().getItemInHand() != null){
			held = entity.getEquipment().getItemInHand();
			if (held.getType() == Material.STICK || held.getType() == Material.BLAZE_ROD){
				List<String> lore = held.getItemMeta().getLore();
				if (lore.get(0).split(" and ").length == 2){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	/**Modifies the ItemStack, allying it to the disarming player.
	 * @param item - ItemStack that may be a wand.
	 */
	private void allyWand(ItemStack wand){
		ItemMeta wandMeta = wand.getItemMeta();
		List<String> wandLore = wandMeta.getLore();
		wandLore.set(1, player.getName());
		wandMeta.setLore(wandLore);
		wand.setItemMeta(wandMeta);
	}
}