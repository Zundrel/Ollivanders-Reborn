package net.pottercraft.Ollivanders2;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import net.minecraft.server.v1_12_R1.EnumChatFormat;
import net.pottercraft.Ollivanders2.Book.O2Books;
import net.pottercraft.Ollivanders2.Effect.BARUFFIOS_BRAIN_ELIXIR;
import net.pottercraft.Ollivanders2.Effect.LYCANTHROPY;
import net.pottercraft.Ollivanders2.Effect.MEMORY_POTION;
import net.pottercraft.Ollivanders2.Effect.WIT_SHARPENING_POTION;
import net.pottercraft.Ollivanders2.Effect.WOLFSBANE_POTION;
import net.pottercraft.Ollivanders2.Spell.MORTUOS_SUSCITATE;
import net.pottercraft.Ollivanders2.Spell.PORTUS;
import net.pottercraft.Ollivanders2.Spell.SpellProjectile;
import net.pottercraft.Ollivanders2.Spell.Spells;
import net.pottercraft.Ollivanders2.Spell.Transfiguration;
import net.pottercraft.Ollivanders2.StationarySpell.ALIQUAM_FLOO;
import net.pottercraft.Ollivanders2.StationarySpell.COLLOPORTUS;
import net.pottercraft.Ollivanders2.StationarySpell.MOLLIARE;
import net.pottercraft.Ollivanders2.StationarySpell.NULLUM_APPAREBIT;
import net.pottercraft.Ollivanders2.StationarySpell.NULLUM_EVANESCUNT;
import net.pottercraft.Ollivanders2.StationarySpell.PROTEGO_TOTALUM;
import net.pottercraft.Ollivanders2.StationarySpell.REPELLO_MUGGLETON;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpellObj;
import net.pottercraft.Ollivanders2.StationarySpell.StationarySpells;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

/**
 * Listener for events from the plugin
 *
 * @author lownes
 * @author Azami7
 */
public class OllivandersListener implements Listener {

	Ollivanders2 p;

	public OllivandersListener(Ollivanders2 plugin) {
		p = plugin;
	}

	/**
	 * Fires on player move
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		protegoTotalum(event);
	}

	/**
	 * Doesn't let players cross a protego totalum
	 */
	private void protegoTotalum(PlayerMoveEvent event) {
		if (event.getPlayer().isPermissionSet("Ollivanders2.BYPASS")) {
			if (event.getPlayer().hasPermission("Ollivanders2.BYPASS")) {
				return;
			}
		}
		Location toLoc = event.getTo();
		Location fromLoc = event.getFrom();
		for (StationarySpellObj spell : p.getStationary()) {
			if (spell instanceof PROTEGO_TOTALUM && toLoc.getWorld().getUID().equals(spell.location.getWorldUUID()) && fromLoc.getWorld().getUID().equals(spell.location.getWorldUUID())) {
				int radius = spell.radius;
				Location spellLoc = spell.location.toLocation();
				if (((fromLoc.distance(spellLoc) < radius - 0.5 && toLoc.distance(spellLoc) > radius - 0.5) || (toLoc.distance(spellLoc) < radius + 0.5 && fromLoc.distance(spellLoc) > radius + 0.5)) && spell.active) {
					event.setCancelled(true);
					spell.flair(10);
				}
			}
		}
	}

	/**
	 * Checks if a player is inside an active floo fireplace and is saying a
	 * destination
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onFlooChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String chat = event.getMessage();
		for (StationarySpellObj stat : p.getStationary()) {
			if (stat instanceof ALIQUAM_FLOO) {
				ALIQUAM_FLOO aliquam = (ALIQUAM_FLOO) stat;
				if (player.getLocation().getBlock().equals(aliquam.getBlock()) && aliquam.isWorking()) {
					// Floo network
					if (player.isPermissionSet("Ollivanders2.Floo")) {
						if (!player.hasPermission("Ollivanders2.Floo")) {
							player.sendMessage(ChatColor.getByChar(p.getConfig().getString("chatColor")) + "You do not have permission to use the Floo Network.");
							return;
						}
					}
					aliquam.stopWorking();
					List<ALIQUAM_FLOO> alis = new ArrayList<ALIQUAM_FLOO>();
					Location destination;
					for (StationarySpellObj ali : p.getStationary()) {
						if (ali instanceof ALIQUAM_FLOO) {
							ALIQUAM_FLOO dest = (ALIQUAM_FLOO) ali;
							alis.add(dest);
							if (dest.getFlooName().equals(chat.trim().toLowerCase())) {
								destination = dest.location.toLocation();
								destination.setPitch(player.getLocation().getPitch());
								destination.setYaw(player.getLocation().getYaw());
								player.teleport(destination);
								return;
							}
						}
					}
					int randomIndex = (int) (alis.size() * Math.random());
					destination = alis.get(randomIndex).location.toLocation();
					destination.setPitch(player.getLocation().getPitch());
					destination.setYaw(player.getLocation().getYaw());
					player.teleport(destination);
					return;
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		String message = event.getMessage();
		List<OEffect> effects = p.getO2Player(sender).getEffects();

		if (Ollivanders2.debug) {
			p.getLogger().info("onPlayerChat: message = " + message);
		}

		/**
		 * Handle player spells that effect the chat. Need to do this first sine
		 * they may affect the chat message itself, which would change later
		 * chat effects.
		 */
		if (effects != null) {
			if (Ollivanders2.debug) {
				p.getLogger().info("onPlayerChat: Handling player effects");
			}

			for (OEffect effect : effects) {
				// If SILENCIO is affecting the player, remove all chat
				// recipients and do not allow a spell cast.
				if (effect.name == Effects.SILENCIO) {
					if (Ollivanders2.debug) {
						p.getLogger().info("onPlayerChat: SILENCIO");
					}

					if (sender.isPermissionSet("Ollivanders2.BYPASS")) {
						if (!sender.hasPermission("Ollivanders2.BYPASS")) {
							event.getRecipients().clear();
							return;
						}
					} else {
						event.getRecipients().clear();
						return;
					}
				}
			}
		}

		/**
		 * Parse to see if they were casting a spell
		 */
		Spells spell = Spells.decode(message);
		if (Ollivanders2.debug) {
			if (spell != null) {
				p.getLogger().info("Spells:decode(): spell is " + spell);
			} else {
				p.getLogger().info("Spells:decode(): no spell found");
			}
		}

		/**
		 * Handle stationary spells that affect chat
		 */
		Set<Player> recipients = event.getRecipients();
		List<StationarySpellObj> stationaries = p.checkForStationary(sender.getLocation());
		Set<StationarySpellObj> muffliatos = new HashSet<>();
		for (StationarySpellObj stationary : stationaries) {
			if (Ollivanders2.debug) {
				p.getLogger().info("onPlayerChat: handling stationary spells");
			}

			if (stationary.name.equals(StationarySpells.MUFFLIATO) && stationary.active) {
				muffliatos.add(stationary);
			}
		}

		double chatDistance = p.getChatDistance();

		// If sender is in a MUFFLIATO, remove recepients not also in the
		// MUFFLIATO radius

		// Also handles removing recipients from spells said outside the chat
		// falloff distance.
		Set<Player> remRecipients = new HashSet<>();
		for (Player recipient : recipients) {
			double distance;
			try {
				distance = recipient.getLocation().distance(sender.getLocation());
			} catch (IllegalArgumentException e) {
				distance = -1;
			}
			if (spell != null) {
				if (distance > chatDistance || distance == -1) {
					remRecipients.add(recipient);
				}
			}

			if (muffliatos.size() > 0) {
				if (Ollivanders2.debug) {
					p.getLogger().info("onPlayerChat: MUFFLIATO detected");
				}

				for (StationarySpellObj muffliato : muffliatos) {
					Location recLoc = recipient.getLocation();
					if (!muffliato.isInside(recLoc)) {
						remRecipients.add(recipient);
					}
				}
			}
		}

		for (Player remRec : remRecipients) {
			if (Ollivanders2.debug) {
				p.getLogger().info("onPlayerChat: update recipients");
			}

			try {
				if (remRec.isPermissionSet("Ollivanders2.BYPASS")) {
					if (!remRec.hasPermission("Ollivanders2.BYPASS")) {
						recipients.remove(remRec);
					}
				} else {
					recipients.remove(remRec);
				}
			} catch (UnsupportedOperationException e) {
				p.getLogger().warning("Chat was unable to be removed due " + "to a unmodifiable set.");
			}
		}

		/**
		 * Handle spell casting
		 */
		// If the spell is valid AND player is allowed to cast spells per server
		// permissions
		if (spell != null && p.canCast(sender, spell, true)) {
			if (p.getConfig().getBoolean("bookLearning") && p.getO2Player(sender).getSpellCount(spell) == 0) {
				// if bookLearning is set to true then spell count must be > 0
				// to cast this spell
				if (Ollivanders2.debug) {
					p.getLogger().info("onPlayerChat: bookLearning enforced");
				}
				sender.sendMessage(ChatColor.getByChar(p.getConfig().getString("chatColor")) + "You do not know that spell yet. To learn a spell, you'll need to read a book about that spell.");

				return;
			}

			boolean castSuccess = true;

			// if they are not holding their destined wand, casting success is
			// reduced
			if (!p.holdsWand(sender)) {
				if (Ollivanders2.debug) {
					p.getLogger().info("onPlayerChat: player not holding destined wand");
				}

				int uses = p.getO2Player(sender).getSpellCount(spell);
				castSuccess = Math.random() < (1.0 - (100.0 / (uses + 101.0)));
			}

			if (castSuccess) {
				if (Ollivanders2.debug) {
					p.getLogger().info("onPlayerChat: begin casting " + spell);
				}

				String[] words = message.split(" ");

				if (spell == Spells.APPARATE) {
					apparate(sender, words);
					event.setMessage("apparate");
				} else if (spell == Spells.PORTUS) {
					p.addProjectile(new PORTUS(p, sender, Spells.PORTUS, 1.0, words));
				} else {
					O2Player o2p = p.getO2Player(sender);
					o2p.setWandSpell(spell);
					p.setO2Player(sender, o2p);
				}
			}
		} else {
			if (Ollivanders2.debug) {
				p.getLogger().info("Either no spell cast attempted or not allowed to cast");
			}
		}

		if (Ollivanders2.debug) {
			p.getLogger().info("onPlayerChat: return");
		}
	}

	/**
	 * Apparates sender to either specified location or to eye target location.
	 * Respects anti-apparition and anti-disapparition spells.
	 *
	 * @param sender
	 *            - Player apparating
	 * @param words
	 *            - Typed in words
	 */
	private void apparate(Player sender, String[] words) {
		boolean canApparateOut = true;
		for (StationarySpellObj stat : p.getStationary()) {
			if (stat instanceof NULLUM_EVANESCUNT && stat.isInside(sender.getLocation()) && stat.active) {
				stat.flair(10);
				canApparateOut = false;
			}
		}
		if (sender.isPermissionSet("Ollivanders2.BYPASS")) {
			if (sender.hasPermission("Ollivanders2.BYPASS")) {
				canApparateOut = true;
			}
		}
		if (canApparateOut) {
			int uses = p.incSpellCount(sender, Spells.APPARATE);
			Location from = sender.getLocation().clone();
			Location to;
			if (words.length == 4) {
				try {
					to = new Location(sender.getWorld(), Double.parseDouble(words[1]), Double.parseDouble(words[2]), Double.parseDouble(words[3]));
				} catch (NumberFormatException e) {
					to = sender.getLocation().clone();
				}
			} else {
				Location eyeLocation = sender.getEyeLocation();
				Material inMat = eyeLocation.getBlock().getType();
				int distance = 0;
				while ((inMat == Material.AIR || inMat == Material.FIRE || inMat == Material.WATER || inMat == Material.STATIONARY_WATER || inMat == Material.LAVA || inMat == Material.STATIONARY_LAVA) && distance < 160) {
					eyeLocation = eyeLocation.add(eyeLocation.getDirection());
					distance++;
					inMat = eyeLocation.getBlock().getType();
				}
				to = eyeLocation.subtract(eyeLocation.getDirection()).clone();
			}
			to.setPitch(from.getPitch());
			to.setYaw(from.getYaw());
			Double distance = from.distance(to);
			Double radius;
			if (p.holdsWand(sender)) {
				radius = 1 / Math.sqrt(uses) * distance * 0.1 * p.wandCheck(sender);
			} else {
				radius = 1 / Math.sqrt(uses) * distance * 0.01;
			}
			Double newX = to.getX() - (radius / 2) + (radius * Math.random());
			Double newZ = to.getZ() - (radius / 2) + (radius * Math.random());
			to.setX(newX);
			to.setZ(newZ);
			boolean canApparateIn = true;
			for (StationarySpellObj stat : p.getStationary()) {
				if (stat instanceof NULLUM_APPAREBIT && stat.isInside(to) && stat.active) {
					stat.flair(10);
					canApparateIn = false;
				}
			}
			if (sender.isPermissionSet("Ollivanders2.BYPASS")) {
				if (sender.hasPermission("Ollivanders2.BYPASS")) {
					canApparateIn = true;
				}
			}
			if (canApparateIn) {
				sender.getWorld().createExplosion(sender.getLocation(), 0);
				sender.teleport(to);
				sender.getWorld().createExplosion(sender.getLocation(), 0);
				for (Entity e : sender.getWorld().getEntities()) {
					if (from.distance(e.getLocation()) <= 2) {
						e.teleport(to);
					}
				}
			}
		}
	}

	/**
	 * Monitors chat events for the owl-post keywords and enacts the owl-post
	 * system
	 *
	 * @param event
	 *            Chat event of type AsyncPlayerChatEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void owlPost(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		Server server = sender.getServer();
		World world = sender.getWorld();
		String message = event.getMessage();
		String[] splited = message.split("\\s+", 3);
		if (splited.length == 3) {
			if (splited[0].equalsIgnoreCase("deliver") && splited[1].equalsIgnoreCase("to")) {
				for (Entity entity : world.getEntities()) {
					if (entity.getLocation().distance(sender.getLocation()) <= 10) {
						Creature owl;
						if (Ollivanders2.mcVersionCheck() && entity instanceof Parrot) {
							owl = (Parrot) entity;
						} else if (entity instanceof Ocelot) {
							owl = (Ocelot) entity;
							Ocelot o = (Ocelot) owl;
							if (!o.isTamed()) {
								continue;
							}
						} else {
							continue;
						}

						// if (owl.isTamed())
						// {
						for (Entity item : world.getEntities()) {
							if (item instanceof Item && item.getLocation().distance(owl.getLocation()) <= 2) {
								Player recipient = server.getPlayer(splited[2]);
								if (recipient != null) {
									if (recipient.isOnline()) {
										if (recipient.getWorld().getUID().equals(world.getUID())) {
											if (Ollivanders2.mcVersionCheck()) {
												world.playSound(owl.getLocation(), Sound.ENTITY_PARROT_AMBIENT, 1, 0);
											} else {
												world.playSound(owl.getLocation(), Sound.ENTITY_CAT_PURREOW, 1, 0);
											}
											owl.teleport(recipient.getLocation());
											item.teleport(recipient.getLocation());
											if (Ollivanders2.mcVersionCheck()) {
												world.playSound(owl.getLocation(), Sound.ENTITY_PARROT_AMBIENT, 1, 0);
											} else {
												world.playSound(owl.getLocation(), Sound.ENTITY_CAT_PURREOW, 1, 0);
											}
										} else {
											if (Ollivanders2.mcVersionCheck()) {
												world.playSound(owl.getLocation(), Sound.ENTITY_PARROT_HURT, 1, 0);
											} else {
												world.playSound(owl.getLocation(), Sound.ENTITY_CAT_HISS, 1, 0);
											}
											sender.sendMessage(ChatColor.getByChar(p.getConfig().getString("chatColor")) + splited[2] + " is not in this world.");
										}
									} else {
										if (Ollivanders2.mcVersionCheck()) {
											world.playSound(owl.getLocation(), Sound.ENTITY_PARROT_HURT, 1, 0);
										} else {
											world.playSound(owl.getLocation(), Sound.ENTITY_CAT_HISS, 1, 0);
										}
										sender.sendMessage(ChatColor.getByChar(p.getConfig().getString("chatColor")) + splited[2] + " is not online.");
									}
								} else {
									if (Ollivanders2.mcVersionCheck()) {
										world.playSound(owl.getLocation(), Sound.ENTITY_PARROT_HURT, 1, 0);
									} else {
										world.playSound(owl.getLocation(), Sound.ENTITY_CAT_HISS, 1, 0);
									}
									sender.sendMessage(ChatColor.getByChar(p.getConfig().getString("chatColor")) + splited[2] + " is not online.");
								}
								return;
							}
						}
						// }
					}
				}
			}
		}
	}

	/**
	 * This creates the spell projectile.
	 */
	private void createSpellProjectile(Player player, Spells name, double wandC) {
		if (Ollivanders2Common.libsDisguisesSpells.contains(name) && !Ollivanders2.libsDisguisesEnabled) {
			return;
		}

		// spells go here, using any of the three types of m
		String spellClass = "net.pottercraft.Ollivanders2.Spell." + name.toString();
		@SuppressWarnings("rawtypes")
		Constructor c = null;
		try {
			// Maybe you have to use Integer.TYPE here instead of Integer.class
			c = Class.forName(spellClass).getConstructor(Ollivanders2.class, Player.class, Spells.class, Double.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			p.addProjectile((SpellProjectile) c.newInstance(p, player, name, wandC));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();

		if (action == null || player == null) {
			return;
		}

		/**
		 * A left click is used to cast a spell.
		 */
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
			O2Player o2p = p.getO2Player(player);
			Spells spell = o2p.getWandSpell();
			if (spell != null) {
				double wandC;
				boolean playerHoldsWand = p.holdsWand(player);
				if (playerHoldsWand) {
					if (Ollivanders2.debug) {
						p.getLogger().info("OllivandersListener:onPlayerInteract: player holds a wand");
					}

					wandC = p.wandCheck(player);
					allyWand(player);
				} else {
					if (Ollivanders2.debug) {
						p.getLogger().info("OllivandersListener:onPlayerInteract: player does not hold a wand");
					}

					wandC = 10;
				}

				createSpellProjectile(player, spell, wandC);
				int spellc = p.getSpellNum(player, spell);
				if (spellc < 100 || spell == Spells.AVADA_KEDAVRA || !playerHoldsWand) {
					if (Ollivanders2.debug) {
						p.getLogger().info("OllivandersListener:onPlayerInteract: allow cast spell");
					}

					o2p.setWandSpell(null);
					p.setO2Player(player, o2p);
				}

				if (spellc >= 100 && wandC != 1) {
					if (Ollivanders2.debug) {
						p.getLogger().info("OllivandersListener:onPlayerInteract: allow cast spell");
					}

					o2p.setMasterSpell(null);
					p.setO2Player(player, o2p);
				}
			}
		}

		/**
		 * A right click is used to determine if the wand is the player's
		 * destined wand or to set the mastered spell in the wand for non-verbal
		 * casting if they are sneaking while clicking.
		 */
		if (p.holdsWand(player) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			O2Player o2p = p.getO2Player(player);
			Spells castSpell = o2p.getWandSpell();
			Location location = player.getLocation();
			location.setY(location.getY() + 1.6);

			if (p.wandCheck(player) == 1) {
				if (o2p.hasMasterSpells() && p.getConfig().getBoolean("nonVerbalSpellCasting")) {
					if (Ollivanders2.debug) {
						p.getLogger().info("OllivandersListener:onPlayerInteract: shift mastered spell");
					}

					if (player.isSneaking()) {
						o2p.shiftBackMasterSpell();
					} else {
						o2p.shiftMasterSpell();
					}

					Spells spell = o2p.getMasterSpell();
					if (spell != null) {
						String spellName = Spells.recode(spell);
						player.sendMessage("Spell set to " + EnumChatFormat.AQUA + spellName.substring(0, 1).toUpperCase() + spellName.substring(1));
					} else {
						if (Ollivanders2.debug) {
							player.sendMessage("You have not mastered any spells.");
						}
					}
				} else if (!o2p.hasMasterSpells()) {
					// play a sound and visual effect when they right-click
					// their destined wand with no spell
					player.getWorld().playEffect(location, Effect.ENDER_SIGNAL, 0);
					player.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerLoginEvent event) {
		// Map<UUID, OPlayer> map = p.getOPlayerMap();
		Player player = event.getPlayer();
		UUID pid = player.getUniqueId();

		O2Player o2p = p.getO2Player(player);

		p.setPlayerTeamColor(event.getPlayer());
		o2p.setPlayerName(player.getDisplayName());
		p.setO2Player(player, o2p);

		p.getLogger().info("Player " + player.getDisplayName() + " joined.");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (p.getConfig().getBoolean("deathExpLoss")) {
			O2Player o2p = p.getO2Player(event.getEntity());

			o2p.resetSpellCount();
			o2p.setWandSpell(null);
			o2p.resetSouls();
			o2p.resetEffects();

			p.setO2Player(event.getEntity(), o2p);
		}
	}

	/**
	 * This checks if a player kills another player, and if so, adds a soul to
	 * the attacking player's oplayer
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player damaged = (Player) event.getEntity();
			if (event.getDamager() instanceof Player) {
				Player attacker = (Player) event.getDamager();
				if (((Damageable) damaged).getHealth() - event.getDamage() <= 0) {
					p.getO2Player(attacker).addSoul();
				}
			}
			if (event.getDamager() instanceof Wolf) {
				Wolf wolf = (Wolf) event.getDamager();
				if (wolf.isAngry()) {
					boolean hasLy = false;
					O2Player o2p = p.getO2Player(damaged);
					for (OEffect effect : o2p.getEffects()) {
						if (effect.name == Effects.LYCANTHROPY) {
							hasLy = true;
						}
					}
					if (!hasLy) {
						o2p.addEffect(new LYCANTHROPY(damaged, Effects.LYCANTHROPY, 100));
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageEvent event) {
		if (checkSpongify(event)) {
			return;
		}
		// Horcrux code
		List<StationarySpellObj> stationarys = p.getStationary();
		if (event.getEntity() instanceof Player) {
			Damageable plyr = (Damageable) event.getEntity();
			UUID name = ((Player) event.getEntity()).getUniqueId();
			if ((plyr.getHealth() - event.getDamage()) <= 0) {
				for (StationarySpellObj stationary : stationarys) {
					if (stationary.name == StationarySpells.HORCRUX && stationary.getPlayerUUID().equals(name)) {
						Location tp = stationary.location.toLocation();
						tp.setY(tp.getY() + 1);
						plyr.teleport(tp);
						p.getO2Player((Player) plyr).resetEffects();
						Collection<PotionEffect> potions = ((Player) event.getEntity()).getActivePotionEffects();
						for (PotionEffect potion : potions) {
							((Player) event.getEntity()).removePotionEffect(potion.getType());
						}
						event.setCancelled(true);
						plyr.setHealth(((Player) plyr).getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getBaseValue());
						p.remStationary(stationary);
						return;
					}
				}
			}
		}
	}

	/**
	 * Checks to see if the entity was within a spongify stationary spell
	 * object. If so, cancells the damage event
	 *
	 * @param event
	 *            - The EntityDamageEvent
	 * @return - True if the entity was within spongify
	 */
	private boolean checkSpongify(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		for (StationarySpellObj spell : p.getStationary()) {
			if (spell instanceof MOLLIARE && event.getCause() == DamageCause.FALL) {
				if (spell.isInside(entity.getLocation()) && spell.active) {
					event.setCancelled(true);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Cancels any block place event inside of a colloportus object
	 *
	 * @param event
	 *            - BlockEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloBlockPlaceEvent(BlockPlaceEvent event) {
		if (p.isInsideOf(StationarySpells.COLLOPORTUS, event.getBlock().getLocation())) {
			if (event.getPlayer().isPermissionSet("Ollivanders2.BYPASS")) {
				if (!event.getPlayer().hasPermission("Ollivanders2.BYPASS")) {
					event.getBlock().breakNaturally();
				}
			} else {
				event.getBlock().breakNaturally();
			}
		}
	}

	/**
	 * Cancels any block break event inside of a colloportus object
	 *
	 * @param event
	 *            - BlockEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloBlockBreakEvent(BlockBreakEvent event) {
		if (p.isInsideOf(StationarySpells.COLLOPORTUS, event.getBlock().getLocation())) {
			if (event.getPlayer().isPermissionSet("Ollivanders2.BYPASS")) {
				if (!event.getPlayer().hasPermission("Ollivanders2.BYPASS")) {
					event.setCancelled(true);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}

	/**
	 * Cancels any block physics event inside of a colloportus object
	 *
	 * @param event
	 *            - BlockEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloBlockPhysicsEvent(BlockPhysicsEvent event) {
		if (p.isInsideOf(StationarySpells.COLLOPORTUS, event.getBlock().getLocation())) {
			event.setCancelled(true);
			return;
		}
	}

	/**
	 * Cancels any block interact event inside a colloportus object
	 *
	 * @param event
	 *            - PlayerInteractEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.isInsideOf(StationarySpells.COLLOPORTUS, event.getClickedBlock().getLocation())) {
				if (event.getPlayer().isPermissionSet("Ollivanders2.BYPASS")) {
					if (!event.getPlayer().hasPermission("Ollivanders2.BYPASS")) {
						event.setCancelled(true);
					}
				} else {
					event.setCancelled(true);
				}
			}
		}
	}

	/**
	 * Cancels any piston extend event inside a colloportus
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloPistonExtend(BlockPistonExtendEvent event) {
		ArrayList<COLLOPORTUS> collos = new ArrayList<COLLOPORTUS>();
		for (StationarySpellObj stat : p.getStationary()) {
			if (stat instanceof COLLOPORTUS) {
				collos.add((COLLOPORTUS) stat);
			}
		}
		List<Block> blocks = event.getBlocks();
		BlockFace direction = event.getDirection();
		for (Block block : blocks) {
			Block newBlock = block.getRelative(direction.getModX(), direction.getModY(), direction.getModZ());
			for (COLLOPORTUS collo : collos) {
				if (collo.isInside(newBlock.getLocation()) || collo.isInside(block.getLocation())) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	/**
	 * Cancels any piston retract event inside of a colloportus
	 *
	 * @param event
	 *            - BlockPistonRetractEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	@SuppressWarnings("deprecation")
	public void onColloPistonRetract(BlockPistonRetractEvent event) {
		if (event.isSticky()) {
			if (p.isInsideOf(StationarySpells.COLLOPORTUS, event.getRetractLocation())) {
				event.setCancelled(true);
				return;
			}
		}
	}

	/**
	 * Cancels any block change by an entity inside of a colloportus
	 *
	 * @param event
	 *            - EntityChangeBlockEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onColloEntityChangeBlock(EntityChangeBlockEvent event) {
		Location loc = event.getBlock().getLocation();
		Entity entity = event.getEntity();
		if (p.isInsideOf(StationarySpells.COLLOPORTUS, loc)) {
			event.setCancelled(true);
			if (event.getEntityType() == EntityType.FALLING_BLOCK) {
				loc.getWorld().dropItemNaturally(loc, new ItemStack(((FallingBlock) entity).getMaterial()));
			}
		}
	}

	/**
	 * If a block is broken that is temporary, prevent it from dropping
	 * anything.
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTemporaryBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		List<Block> tempBlocks = p.getTempBlocks();
		if (tempBlocks.contains(block)) {
			event.setCancelled(true);
			tempBlocks.remove(block);
			block.setType(Material.AIR);
		}
	}

	/**
	 * If a block is a tempBlock or is inside colloportus, then don't blow it
	 * up.
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onExplosion(EntityExplodeEvent event) {
		List<Block> blockListCopy = new ArrayList<Block>();
		blockListCopy.addAll(event.blockList());
		List<Block> tempBlocks = p.getTempBlocks();
		for (Block block : blockListCopy) {
			if (tempBlocks.contains(block)) {
				event.blockList().remove(block);
			}
			for (StationarySpellObj stat : p.getStationary()) {
				if (stat instanceof COLLOPORTUS) {
					if (stat.isInside(block.getLocation())) {
						event.blockList().remove(block);
					}
				}
			}
		}
	}

	/**
	 * If a wand is not already allied with a player, this allies it.
	 *
	 * @param player
	 *            - Player holding a wand.
	 */
	public void allyWand(Player player) {
		ItemStack wand = player.getInventory().getItemInMainHand();
		ItemMeta wandMeta = wand.getItemMeta();
		List<String> wandLore = wandMeta.getLore();
		if (wandLore.size() == 1) {
			wandLore.add(player.getUniqueId().toString());
			wandMeta.setLore(wandLore);
			wand.setItemMeta(wandMeta);
			player.getInventory().setItemInMainHand(wand);
		} else {
			return;
		}
	}

	/**
	 * Prevents a transfigured entity from changing any blocks by exploding.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void transfiguredEntityExplodeCancel(EntityExplodeEvent event) {
		if (event.getEntity() != null) {
			for (SpellProjectile proj : p.getProjectiles()) {
				if (proj instanceof Transfiguration) {
					Transfiguration trans = (Transfiguration) proj;
					if (trans.getToID() == event.getEntity().getUniqueId()) {
						event.setCancelled(true);
					}
				}
			}
		}
	}

	/**
	 * When an item is picked up by a player, if the item is a portkey, the
	 * player will be teleported there.
	 *
	 * @param event
	 *            - PlayerPickupItemEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void portkeyPickUp(EntityPickupItemEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			Item item = event.getItem();
			ItemMeta meta = item.getItemStack().getItemMeta();
			List<String> lore;
			if (meta.hasLore()) {
				lore = meta.getLore();
			} else {
				lore = new ArrayList<>();
			}
			for (String s : lore) {
				if (s.startsWith("Portkey")) {
					String[] portArray = s.split(" ");
					Location to;
					to = new Location(Bukkit.getServer().getWorld(UUID.fromString(portArray[1])), Double.parseDouble(portArray[2]), Double.parseDouble(portArray[3]), Double.parseDouble(portArray[4]));
					to.setDirection(player.getLocation().getDirection());
					for (Entity e : player.getWorld().getEntities()) {
						if (player.getLocation().distance(e.getLocation()) <= 2) {
							e.teleport(to);
						}
					}
					player.teleport(to);
					lore.remove(lore.indexOf(s));
					meta.setLore(lore);
					item.getItemStack().setItemMeta(meta);
					return;
				}
			}
		}
	}

	/**
	 * Cancels any targeting of players with the Cloak of Invisibility or inside
	 * of a REPELLO_MUGGLETON while the targeting entity is outside it.
	 *
	 * @param event
	 *            - EntityTargetEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void cloakPlayer(EntityTargetEvent event) {
		Entity target = event.getTarget();
		if (target instanceof Player) {
			if (p.getO2Player((Player) target).isInvisible()) {
				event.setCancelled(true);
			}
		}
		if (target != null) {
			for (StationarySpellObj stat : p.getStationary()) {
				if (stat instanceof REPELLO_MUGGLETON) {
					if (stat.isInside(target.getLocation())) {
						if (!stat.isInside(event.getEntity().getLocation())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	/**
	 * Cancels any targeting of players who own inferi by that inferi
	 *
	 * @param event
	 *            - EntityTargetEvent
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void inferiTarget(EntityTargetEvent event) {
		Entity target = event.getTarget();
		Entity entity = event.getEntity();
		for (SpellProjectile sp : p.getProjectiles()) {
			if (sp instanceof MORTUOS_SUSCITATE) {
				Transfiguration trans = (Transfiguration) sp;
				if (trans.getToID() == entity.getUniqueId() && trans.player == target) {
					event.setCancelled(true);
				}
			}
		}
	}

	/**
	 * This drops a random wand when a witch dies
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void witchWandDrop(EntityDeathEvent event) {
		if (event.getEntityType() == EntityType.WITCH && p.getConfig().getBoolean("witchDrop")) {
			int wandType = Math.abs(Ollivanders2.random.nextInt() % 4);
			int coreType = Math.abs(Ollivanders2.random.nextInt() % 4);
			String[] woodArray = { "Spruce", "Jungle", "Birch", "Oak" };
			String[] coreArray = { "Spider Eye", "Bone", "Rotten Flesh", "Gunpowder" };
			ItemStack wand = new ItemStack(Material.STICK);
			List<String> lore = new ArrayList<String>();
			lore.add(woodArray[wandType] + " and " + coreArray[coreType]);
			ItemMeta meta = wand.getItemMeta();
			meta.setLore(lore);
			meta.setDisplayName("Wand");
			wand.setItemMeta(meta);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), wand);
		}
	}

	/**
	 * Fires if a player right clicks a cauldron that is being heated from
	 * underneath
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void cauldronClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (Ollivanders2.debug) {
				p.getLogger().info("OllivandersListener:cauldronClick: enter");
			}

			Block block = event.getClickedBlock();
			if (block.getType() == Material.CAULDRON && p.holdsWand(event.getPlayer())) {
				if (Ollivanders2.debug) {
					p.getLogger().info("Block right-clicked was a cauldron");
				}

				Block under = block.getRelative(BlockFace.DOWN);
				if (under.getType() == Material.FIRE || under.getType() == Material.LAVA || under.getType() == Material.STATIONARY_LAVA) {
					if (Ollivanders2.debug) {
						p.getLogger().info("Cauldron is over a hot block");
					}

					PotionParser.parse(block, p);
				} else {
					if (Ollivanders2.debug) {
						p.getLogger().info("Cauldron is over a hot block");
					}
				}
			}
		}
	}

	/**
	 * When a player consumes something, see if it was a potion and apply the
	 * effect if it was.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDrink(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		if (item.getType() == Material.POTION) {
			O2Player o2p = p.getO2Player(event.getPlayer());
			ItemMeta meta = item.getItemMeta();
			if (meta.hasLore()) {
				for (String lore : meta.getLore()) {
					if (lore.equals("Memory Potion")) {
						for (OEffect effect : o2p.getEffects()) {
							if (effect instanceof MEMORY_POTION) {
								effect.duration = 3600;
								return;
							}
						}
						o2p.addEffect(new MEMORY_POTION(event.getPlayer(), Effects.MEMORY_POTION, 3600));
						return;
					} else if (lore.equals("Baruffio's Brain Elixir")) {
						for (OEffect effect : o2p.getEffects()) {
							if (effect instanceof BARUFFIOS_BRAIN_ELIXIR) {
								effect.duration = 3600;
								return;
							}
						}
						o2p.addEffect(new BARUFFIOS_BRAIN_ELIXIR(event.getPlayer(), Effects.BARUFFIOS_BRAIN_ELIXIR, 3600));
						return;
					} else if (lore.equals("Wolfsbane Potion")) {
						for (OEffect effect : o2p.getEffects()) {
							if (effect instanceof WOLFSBANE_POTION) {
								effect.duration = 3600;
								return;
							}
						}
						o2p.addEffect(new WOLFSBANE_POTION(event.getPlayer(), Effects.WOLFSBANE_POTION, 3600));
						return;
					} else if (lore.equals("Wit-Sharpening Potion")) {
						for (OEffect effect : o2p.getEffects()) {
							if (effect instanceof WIT_SHARPENING_POTION) {
								effect.duration = 3600;
								return;
							}
						}
						o2p.addEffect(new WIT_SHARPENING_POTION(event.getPlayer(), Effects.WIT_SHARPENING_POTION, 3600));
						return;
					}
				}
			}
		}
	}

	/**
	 * Event fires when a player right clicks with a broom in their hand
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void broomClick(PlayerInteractEvent event) {
		if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) && (event.getPlayer().getInventory().getItemInMainHand() != null) && (this.p.isBroom(event.getPlayer().getInventory().getItemInMainHand()))) {
			UUID playerUid = event.getPlayer().getUniqueId();
			Set<UUID> flying = OllivandersSchedule.getFlying();
			if (flying.contains(playerUid)) {
				flying.remove(playerUid);
			} else {
				flying.add(playerUid);
			}
		}
	}

	/**
	 * Process book read events when bookLearning is enabled.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBookRead(PlayerInteractEvent event) {
		// only run this if bookLearning is enabled
		if (!p.getConfig().getBoolean("bookLearning"))
			return;

		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
			Player player = event.getPlayer();

			ItemStack heldItem = player.getInventory().getItemInMainHand();
			if (heldItem.getType() == Material.WRITTEN_BOOK) {
				if (p.debug)
					p.getLogger().info(player.getDisplayName() + " reading a book and book learning is enabled.");

				// reading a book, if it is a spell book we want to let the
				// player "learn" the spell.
				List<String> bookLore = heldItem.getItemMeta().getLore();

				O2Books.readLore(bookLore, player, p);
			}
		}
	}

	/**
	 * When a user holds their spell journal, replace it with an updated version
	 * of the book.
	 *
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSpellJournalHold(PlayerItemHeldEvent event) {
		// only run this if spellJournal is enabled
		if (event == null || !p.getConfig().getBoolean("spellJournal"))
			return;

		Player player = event.getPlayer();
		int slotIndex = event.getNewSlot();

		ItemStack heldItem = player.getInventory().getItem(slotIndex);
		if (heldItem != null && heldItem.getType() == Material.WRITTEN_BOOK) {
			BookMeta bookMeta = (BookMeta) heldItem.getItemMeta();
			if (bookMeta.getTitle().equalsIgnoreCase("Spell Journal")) {
				O2Player o2Player = p.getO2Player(player);
				ItemStack spellJournal = o2Player.getSpellJournal();

				player.getInventory().setItem(slotIndex, spellJournal);
			}
		}
	}
}