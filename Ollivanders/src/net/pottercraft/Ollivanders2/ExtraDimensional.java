package net.pottercraft.Ollivanders2;

import java.util.HashSet;
import java.util.Set;

import net.pottercraft.Ollivanders2.StationarySpell.StationarySpellObj;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class ExtraDimensional extends StationarySpellObj {

	/**
    *
    */
	private static final long serialVersionUID = 3905882508601771210L;
	private int dimenRadius;
	private OLocation edLoc;

	public ExtraDimensional(Player player, Location location, StationarySpells name, Integer radius, Integer duration, Integer dimenRadius) {
		super(player, location, name, radius, duration);
		this.dimenRadius = dimenRadius;
		createSpace();
	}

	/**
	 * Creates the space in the extra dimension for this spell area.
	 */
	private void createSpace() {
		boolean spaceFree = false;
		Location loc = null;
		createLoop: while (!spaceFree) {
			double x = (int) (29000000 - Math.random() * 58000000) + 0.5;
			double z = (int) (29000000 - Math.random() * 58000000) + 0.5;
			double y = 230.0;
			loc = new Location(location.toLocation().getWorld(), x, y, z);
			for (Block block : getBlocksInCube(loc)) {
				if (block.getType() != Material.AIR) {
					continue createLoop;
				}
			}
			spaceFree = true;
		}
		edLoc = new OLocation(loc);
		for (Block block : getBlocksInCube(loc)) {
			block.setType(Material.BEDROCK);
		}
		for (Block block : getBlocksInRadius(loc, dimenRadius)) {
			block.setType(Material.AIR);
		}
		loc.getBlock().setType(Material.BEDROCK);
	}

	public Set<Block> getBlocksInCube(Location loc) {
		Block center = loc.getBlock();
		int blockRadius = (int) (dimenRadius + 1);
		Set<Block> blockList = new HashSet<Block>();
		for (int x = -blockRadius; x <= blockRadius; x++) {
			for (int y = -blockRadius; y <= blockRadius; y++) {
				for (int z = -blockRadius; z <= blockRadius; z++) {
					blockList.add(center.getRelative(x, y, z));
				}
			}
		}
		return blockList;
	}

	@Override
	public void kill() {
		flair(20);
		kill = true;
		for (Block block : getBlocksInCube(edLoc.toLocation())) {
			if (block.getType() != Material.BEDROCK) {
				for (ItemStack stack : block.getDrops()) {
					location.toLocation().getWorld().dropItem(location.toLocation(), stack);
				}
				BlockState state = block.getState();
				if (state instanceof InventoryHolder) {
					for (ItemStack stack : ((InventoryHolder) state).getInventory()) {
						if (stack != null) {
							location.toLocation().getWorld().dropItem(location.toLocation(), stack);
						}
					}
				}
				block.setType(Material.AIR);
			}
		}
		for (Entity entity : edLoc.toLocation().getWorld().getEntities()) {
			if (entity.getLocation().distance(edLoc.toLocation()) < dimenRadius) {
				entity.teleport(location.toLocation());
			}
		}
	}

	/**
	 * Returns the location of the spell in the extra dimension.
	 */
	public OLocation getEDLoc() {
		return edLoc;
	}

}
