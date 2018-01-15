package net.pottercraft.Ollivanders2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Object to hold prophecies.
 *
 * @author lownes
 */
public class Prophecy implements Serializable {

	/**
    *
    */
	private static final long serialVersionUID = -3162828608067812174L;
	private UUID playerUUID;
	private final String effect;
	private final long start;
	private final int duration;
	private final int second = 1000;
	private final int minute = second * 60;
	private final int hour = minute * 60;
	private final int day = hour * 24;

	public Prophecy(Player player) {
		playerUUID = player.getUniqueId();
		PotionEffectType[] effects = PotionEffectType.values();
		PotionEffectType possible = null;
		while (possible == null) {
			possible = effects[(int) Math.abs(Ollivanders2.random.nextInt() % effects.length)];
		}
		effect = possible.getName();
		long currentTime = System.currentTimeMillis();
		FileConfiguration config = Bukkit.getPluginManager().getPlugin("Ollivanders2").getConfig();
		long maxDuration = config.getLong("divinationMaxDuration");
		long maxStartOffset = config.getLong("divinationMaxStartOffset");
		start = currentTime + (long) Math.abs(Ollivanders2.random.nextInt() % maxStartOffset);
		duration = (int) Math.abs(Ollivanders2.random.nextInt() % maxDuration);
	}

	public Prophecy(Player player, long start, int duration) {
		playerUUID = player.getUniqueId();
		PotionEffectType[] effects = PotionEffectType.values();
		PotionEffectType possible = null;
		while (possible == null) {
			possible = effects[(int) Math.abs(Ollivanders2.random.nextInt() % effects.length)];
		}
		effect = possible.getName();
		long currentTime = System.currentTimeMillis();
		this.start = currentTime + start;
		this.duration = duration;
	}

	public PotionEffect toPotionEffect() {
		return new PotionEffect(PotionEffectType.getByName(effect), 15 * 20, 0);
	}

	public List<String> toLore() {
		List<String> lore = new ArrayList<String>();
		lore.add("Prophecy concerning " + Bukkit.getPlayer(playerUUID).getName() + ":");
		lore.add("A " + PotionEffectType.getByName(effect).getName() + " effect will begin in");
		long timeTo = start - System.currentTimeMillis();
		int days = (int) (timeTo / (day));
		timeTo -= days * day;
		int hours = (int) (timeTo / (hour));
		timeTo -= hours * hour;
		int minutes = (int) (timeTo / (minute));
		timeTo -= minutes * minute;
		int seconds = (int) (timeTo / (second));
		lore.add(days + " days, " + hours + " hours, " + minutes + " minutes, and " + seconds + " seconds");
		lore.add("and last for");
		int dur = duration;
		int durh = (int) (dur / (hour));
		timeTo -= hours * hour;
		int durm = (int) (dur / (minute));
		timeTo -= minutes * minute;
		int durs = (int) (dur / (second));
		lore.add(durh + " hours, " + durm + " minutes, and " + durs + " seconds");
		return lore;
	}

	public boolean isActive() {
		long now = System.currentTimeMillis();
		if (now > this.start && now < this.start + this.duration) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFinished() {
		long now = System.currentTimeMillis();
		if (now > this.start + this.duration) {
			return true;
		} else {
			return false;
		}
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

}
