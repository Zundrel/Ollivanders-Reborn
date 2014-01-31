package me.cakenggt.Ollivanders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Provides the methods to parse a book for spells
 * @author cakenggt
 *
 */
public class SpellBookParser{

	public final static String ACCIO = "Accio will pull an item toward you. The strength "
			+ "of the pull is determined by your experience.";
	public final static String AGUAMENTI = "Aguamenti will cause water to erupt against "
			+ "the surface you cast it on.";
	public final static String ALARTE_ASCENDARE = "Alarte Ascendare will shoot the target "
			+ "high into the air. It's strength is determined by your experience.";
	public final static String ALOHOMORA = "Alohomora is a spell used to unlock the effects "
			+ "of the locking spell.";
	public final static String APARECIUM = "Aparecium will cause any area spells to reveal "
			+ "their borders. The amount of revealing depends on your experience.";
	public final static String APPARATE = "Apparition is a two sided spell. To apparate to "
			+ "a predetermined location, simply say apparate and list your x, y, and z "
			+ "coordinates. To apparate to the location of your cursor, within 140 meters, "
			+ "just say the word apparate. Your accuracy is determined by the distance "
			+ "traveled and your experience.";
	public final static String AQUA_ERUCTO = "Aqua eructo shoots a jet of water from your "
			+ "wand tip. The range of this jet is determined by your experience.";
	public final static String ARRESTO_MOMENTUM = "Arresto momentum will immediately slow down "
			+ "any entity. The amount an entity is slowed down is determined by your experience.";
	public final static String ASCENDIO = "Ascendio will propel the caster into the air. The "
			+ "strength of the propulsion depends on your experience.";
	public final static String AVADA_KEDAVRA = "Avada kedavra is a forbidden curse which will "
			+ "directly damage a living being. The amount of damage done is determiend "
			+ "by your experience.";
	public final static String AVIS = "Avis will cause a bat to fly out of the end of your wand. "
			+ "The amount of time the bat is alive depends on your experience.";
	public final static String BOMBARDA = "Bombarda creates an explosion which doesn't damage "
			+ "the terrain. The strength of the explosion depends on your experience.";
	public final static String BOMBARDA_MAXIMA = "Bombarda maxima creates an explosion which doesn't "
			+ "damage the terrain. The explosion is twice as large as the one created by "
			+ "bombarda. The strength of the explosion depends on your experience.";
	public final static String CARPE_RETRACTUM = "Carpe Retractum will pull a living entity towards "
			+ "you. The strength of the pull depends on your experience.";
	public final static String COLLOPORTUS = "Colloportus will lock all blocks within it's area into "
			+ "place, not letting them be changed. This spell will not age like other area spells do, "
			+ "and must be cancelled with the unlocking spell.";
	public final static String CONFUNDO = "Confundo causes the target to become confused. The duration "
			+ "of this confusion is determined by your experience.";
	public final static String CRESCERE_PROTEGAT = "Crescere Protegat will grow a stationary spell's "
			+ "radius, up to a limit determined by your experience. Only the creator of the "
			+ "stationary spell can affect it with this spell.";
	public final static String DEFODIO = "Defodio is a gouging spell that will mine a line of blocks, "
			+ "the length of which is determined by your experience.";
	public final static String DELETRIUS = "Deletrius will cause an item entity to stop existing.";
	public final static String DEPRIMO = "Deprimo creates an immense downward pressure which will "
			+ "cause all blocks within a radius to fall like sand. The radius is determined "
			+ "by your experience.";
	public final static String DEPULSO = "Depulso will repel any entity you hit with it. The strength "
			+ "of the repulsion depends on your experience.";
	public final static String DIMMINUENDO = "Dimminuendo will cause any creature hit to turn into "
			+ "a smaller version. The time duration, for some of the creatures, will depend on your "
			+ "experience.";
	public final static String DUCKLIFORS = "Ducklifors will transfigure an entity into a chicken. "
			+ "The length of the transfiguration depends on your experience.";
	public final static String DURO = "Duro will transfigure an entity into a stone. The length "
			+ "of the transfiguration depends on your experience. If the stone is destroyed, "
			+ "then the entity will die.";
	public final static String DRACONIFORS = "Ducklifors will transfigure an entity into a Dragon. "
			+ "The length of the transfiguration depends on your experience.";
	public final static String EBUBLIO = "Ebublio, the bubble head charm, will grant your target the ability "
			+ "to breathe underwater. The duration of this effect depends on your experience.";
	public final static String ENGORGIO = "Engorgio will grow a baby animal, grow a slime, and grow a zombie "
			+ "from a baby into an adult or from adult into a giant. The effects of this spell depend on your "
			+ "experience. Growing giants is not possible until complete mastery of the spell is achieved.";
	public final static String EPISKEY = "Episkey will heal minor injuries. The duration of the healing "
			+ "effect depends on your experience.";
	public final static String EQUUSIFORS = "Equusifors will transfigure an entity into a horse. "
			+ "The length of the transfiguration depends on your experience.";
	public final static String ET_INTERFICIAM_ANIMAM_LIGAVERIS = "The unholy incantation, "
			+ "et interficiam animam ligaveris, will create a horcrux.";
	public final static String EVANESCO = "Evanesco will vanish an entity. "
			+ "The length of the vanishment depends on your experience.";
	public final static String EVERTE_STATUM = "Everte Statum will throw another player backwards. "
			+ "The force of the throw depends on your experience.";
	public final static String EXPELLIARMUS = "Expelliarmus will cause an entity's "
			+ "held item to be flung at you with a force which depends on your experience.";
	public final static String FIANTO_DURI = "Fianto duri will lengthen the duration of a stationary "
			+ "spell, by an amount depending on your experience.";
	public final static String FIENDFYRE = "Fiendfyre is a hellish curse which summons cursed creatures. "
			+ "A mix of magma cubes, blazes, and ghasts are spawned depending on your experience with "
			+ "the spell. These creatures will be spawned if the spell hits a block or if the spell reaches "
			+ "the edge of it's range, which is determined by your experience.";
	public final static String FINITE_INCANTATEM = "Finite Incantatem will reduce the duration of an effect "
			+ "on a player. It can also lessen the effects of a spell on an item. It's strength depends on "
			+ "your experience in the spell.";
	public final static String FLAGRANTE = "Flagrante will cause an item to burn it's bearer when picked "
			+ "up. The length of the burn depends on your experience.";
	public final static String FRANGE_LIGNEA = "Frange lignea will cause a log of the spruce, oak, birch, or "
			+ "jungle species to explode into coreless wands. The number of wands dropped depends "
			+ "on your experience.";
	public final static String GEMINIO = "Geminio will cause an item to duplicate when held "
			+ "by a person. The amount of duplications depends on your experience.";
	public final static String GLACIUS = "Glacius will cause a great cold to descend in a radius "
			+ "from it's impact point which freezes blocks. The radius and duration of the freeze "
			+ "depend on your experience.";
	public final static String GLACIUS_DUO = "Glacius Duo will freeze blocks in a radius twice that of "
			+ "glacius, but for half the time.";
	public final static String GLACIUS_TRIA = "Glacius Tria will freeze blocks in a radius four times "
			+ "that of glacius, but for one quarter the time.";
	public final static String HARMONIA_NECTERE_PASSUS = "Harmonia Nectere Passus, if inside of a "
			+ "vanishing cabinet, will transport you to it's twin.";
	public final static String HERBIVICUS = "Herbivicus causes crops within a radius to grow. The radius "
			+ "is determined by your experience.";
	public final static String HORREAT_PROTEGAT = "Horreat Protegat will shrink a stationary spell's "
			+ "radius, down to a limit determined by your experience. Only the creator of the "
			+ "stationary spell can affect it with this spell.";
	public final static String IMMOBULUS = "Immobulus immobilizes an entity for an amount of time depending "
			+ "on your experience.";
	public final static String IMPEDIMENTA = "Impedimenta will slow an entity by a degree and for an amount "
			+ "of time depending on your experience.";
	public final static String INCENDIO = "Incendio will burn blocks and entities it passes by. It's range "
			+ "and duration depend on your experience.";
	public final static String INCENDIO_DUO = "Incendio duo will burn blocks and entities it passes by. It's "
			+ "radius is twice that of incendio and it's duration half. It's range depends on your experience.";
	public final static String INCENDIO_TRIA = "Incendio duo will burn blocks and entities it passes by. It's "
			+ "radius is four times that of incendio and it's duration one quarter. It's range depends on your "
			+ "experience.";
	public final static String INFORMOUS = "Informous will give information on a stationary spell, an entity, or, "
			+ "if pointed into the sky and allowed to travel far enough, the weather. It's range "
			+ "depends on your experience.";
	public final static String LIGATIS_COR = "Ligatis cor will bind one of the four types of coreless wands to one "
			+ "of the four types of wand cores: spider eye, rotten flesh, bone, and sulfur. Make sure the two "
			+ "items are near each other when this spell is cast. You can only use this on one coreless wand and "
			+ "one core material at a time.";
	public final static String LUMOS = "Lumos will cause you to gain sight in the dark for an amount of time "
			+ "determined by your experience.";
	public final static String LUMOS_DUO = "Lumos duo will create a line of glowstone along your line of sight. "
			+ "The duration of the glowstone depends on your experience.";
	public final static String LUMOS_MAXIMA = "Lumos maxima will spawn a glowstone at the impact site that will "
			+ "exist for a duration depending on your experience.";
	public final static String MELOFORS = "Places a melon on the target's head.";
	public final static String MUFFLIATO = "Muffliato creates a stationary spell which only allows the people "
			+ "inside to hear anything spoken inside the effect. The duration of the spell depends on "
			+ "your experience.";
	public final static String NULLUM_APPAREBIT = "Nullum apparebit creates a stationary spell which will not "
			+ "allow apparition into it. The duration depends on your experience.";
	public final static String NULLUM_EVANESCUNT = "Nullum evanescunt creates a stationary spell which will not "
			+ "allow disapparition out of it. The duration depends on your experience.";
	public final static String OBLIVIATE = "Obliviate will cause the target to forget some of their magical experience, "
			+ "how much depending on your experience.";
	public final static String OPPUGNO = "Oppugno will cause any entities transfigured by you to attack the targeted "
			+ "entity.";
	public final static String PACK = "When this hits a chest, it will suck any items nearby into it. The radius is "
			+ "dependent on your experience.";
	public final static String PARTIS_TEMPORUS = "Partis temporus, if cast at a stationary spell that you have cast, "
			+ "will cause that stationary spell's effects to stop for a short time.";
	public final static String PIERTOTUM_LOCOMOTOR = "Piertotum locomotor, if cast at an iron or snow block, will "
			+ "transfigure that block into an iron or snow golem. This transfiguration's duration depends "
			+ "on your experience.";
	public final static String PORTUS = "Portus is a spell which creates a portkey. To cast it, hold a wand in your hand "
			+ "and look directly at the item you wish to enchant. Then say 'Portus x y z', where x y and z are the coordinates "
			+ "you wish the portkey to link to. When this item is picked up, the holder and the entities around them will be "
			+ "transported to the destination. Anti-apparition and anti-disapparition spells will stop this, but only if present "
			+ "during the creation of the portkey, and will cause the creation to fail. If the portkey is successfully made, then "
			+ "it can be used to go to that location regardless of the spells put on it. A portkey creation will not fail if the "
			+ "caster of the protective enchantments is the portkey maker. Portkeys can be used to cross worlds as well, if you use "
			+ "a portkey which was made in a different world. If the enchantment is said incorrectly, then the portkey will be created "
			+ "linking to the caster's current location.";
	public final static String PROTEGO = "Protego is a shield spell which, while you are crouching, will cause any spells "
			+ "cast at it to bounce off.";
	public final static String PROTEGO_HORRIBILIS = "Protego horribilis is a stationary spell which will destroy any "
			+ "spells crossing it's barrier. It's duration depends on your experience.";
	public final static String PROTEGO_MAXIMA = "Protego maxima is a stationary spell which will hurt any entities close "
			+ "to it's boundary. It's duration depends on your experience.";
	public final static String PROTEGO_TOTALUM = "Protego totalum is a stationary spell which will prevent any entities "
			+ "from crossing it's boundary. It's duration depends on your experience.";
	public final static String REDUCTO = "Reducto creates an explosion which will damage the terrain. It's power depends "
			+ "on your experience.";
	public final static String REPARIFARGE = "Reparifarge will cause the duration of the transfiguration on the targeted "
			+ "entity to decrease by an amount that depends on your experience.";
	public final static String REPARO = "Reparo will repair the duration of a tool.";
	public final static String SCUTO_CONTERAM = "Scuto conteram will shorten the duration of a stationary "
			+ "spell, by an amount depending on your experience.";
	public final static String SILENCIO = "Silencio silences the target for a duration depending on your experience. "
			+ "During this time, the target can only cast nonverbal spells.";
	public final static String SPONGIFY = "Spongify softens the ground in a radius around the site. All fall damage "
			+ "will be negated in this radius for a time duration depending on your experience.";
	public final static String STUPEFY = "Stupefy will stun an opponent for a duration depending on your experience.";
	public final static String TERGEO = "Tergeo will siphon off a block of water where it hits. It will also disable any "
			+ "aguamenti-placed water blocks nearby.";
	public final static String WINGARDIUM_LEVIOSA = "Wingardium leviosa will allow you to lift up blocks within a radius of "
			+ "the spell's impact, as long as you are crouching. The radius depends on your experience. When you drop "
			+ "the blocks, they will fall like sand.";

	/**
	 * Encodes in the lore of the book the spells and levels the author is at
	 * @param p - The plugin
	 * @param player - The author
	 * @param meta - The BookMeta of the book
	 * @return newMeta - The new BookMeta of the book, which is passed to the event
	 */
	public static BookMeta encode(Ollivanders p, Player player, BookMeta meta){
		String pageString = getPageString(meta);
		List<String> spellStrings = spellList();
		List<String> lore = new ArrayList<String>();
		for (String spell : spellStrings){
			if (pageString.contains(spell)){
				String newLore = spell + ":" + p.getSpellNum(player, Spells.decode(spell));
				lore.add(newLore);
			}
		}
		BookMeta newMeta = meta.clone();
		newMeta.setLore(lore);
		return newMeta;
	}

	/**
	 * Gets a lowercase string composed of all pages of the book
	 * @param meta BookMeta from PlayerEditBookEvent.getNewBookMeta()
	 * @return String in lowercase of all page text
	 */
	private static String getPageString(BookMeta meta){
		List<String> pages = meta.getPages();
		String pageString = "";
		for (String page : pages){
			pageString = pageString.concat(page);
		}
		pageString = pageString.toLowerCase();
		pageString = pageString.replace('\n', ' ');
		return pageString;
	}

	/**
	 * Gets a list of all Spells converted into lower case and spaces for underscores
	 * @return List of strings of human readable spells
	 */
	private static List<String> spellList(){
		Spells[] spellsList = Spells.values();
		List<String> spellStrings = new ArrayList<String>();
		for (Spells spell : spellsList){
			spellStrings.add(Spells.recode(spell));
		}
		return spellStrings;
	}

	/**
	 * Takes a book and decodes the spells in lore, if any, into player uses
	 * @param p - The Plugin
	 * @param player - The player reading the book
	 * @param imeta - The book's metadata
	 */
	public static void decode(Ollivanders p, Player player, ItemMeta imeta){
		if (imeta.hasLore()){
			List<String> lore = imeta.getLore();
			String[] line;
			int bookNum;
			int pSpellNum;
			Spells spell = null;
			for (String s : lore){
				line = s.split(":");
				if (line.length == 2){
					spell = Spells.decode(line[0]);
					bookNum = Integer.parseInt(line[1]);
					if (spell != null){
						pSpellNum = p.getSpellNum(player, spell);
						if (pSpellNum < bookNum){
							p.setSpellNum(player, spell, (int)(pSpellNum+((bookNum-pSpellNum)/2)));
						}
					}
				}
			}
		}
	}

	/**
	 * This creates the books for the /Okit command
	 * @return - A list of books
	 */
	public static List<ItemStack> makeBooks(int amount){
		Map<String, String> bookMap = books();
		List<ItemStack> books = new ArrayList<ItemStack>();
		for (String title : bookMap.keySet()){
			ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
			BookMeta bm = (BookMeta) item.getItemMeta();
			bm.setAuthor("cakenggt");
			bm.setTitle(title);
			bm.setPages(splitEqually(bookMap.get(title), 250));
			bm = kitEncode(bm, 20);
			item.setItemMeta(bm);
			item.setAmount(amount);
			System.out.println(title);
			System.out.println("Adding " + bm.getTitle());
			books.add(item);
		}
		//code for the debug book
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bm = (BookMeta) item.getItemMeta();
		String title = "DEBUGGER";
		bm.setAuthor("cakenggt");
		bm.setTitle(title);
		String inside = "";
		for (String str : spellList()){
			inside += str + " ";
		}
		bm.setPages(splitEqually(inside, 250));
		bm = kitEncode(bm, 200);
		item.setItemMeta(bm);
		item.setAmount(amount);
		System.out.println(title);
		System.out.println("Adding " + bm.getTitle());
		books.add(item);
		return books;
	}

	/**
	 * This splits a string into equal segments.
	 * @param text
	 * @param size
	 * @return List of strings of size size or less
	 */
	private static List<String> splitEqually(String text, int size) {
		List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);
		for (int start = 0; start < text.length(); start += size) {
			ret.add(text.substring(start, Math.min(text.length(), start + size)));
		}
		return ret;
	}

	/**
	 * Encodes in the lore of the book the spells and levels specified in the kit
	 * @param meta - The BookMeta of the book
	 * @param level - The level to encode the spells in
	 * @return newMeta - The new BookMeta of the book, which is passed to the event
	 */
	private static BookMeta kitEncode(BookMeta meta, int level){
		String pageString = getPageString(meta);
		List<String> spellStrings = spellList();
		List<String> lore = new ArrayList<String>();
		for (String spell : spellStrings){
			if (pageString.contains(spell)){
				String newLore = spell + ":" + level;
				lore.add(newLore);
			}
		}
		BookMeta newMeta = meta.clone();
		newMeta.setLore(lore);
		return newMeta;
	}

	/**
	 * Returns a map of all books mapped to their titles
	 * @return Map whose keys are the titles, entries are the book text
	 */
	private static Map<String, String> books(){
		Map<String, String> bookMap = new HashMap<String, String>();
		// \n is a newline
		final String N = "\n";
		bookMap.put("Achievements in Charming",
				AGUAMENTI + N + EBUBLIO + N + HERBIVICUS + N +
						LUMOS_DUO + N + LUMOS_MAXIMA + N + DIMMINUENDO + N + 
						EPISKEY);
		bookMap.put("Extreme Incantations",
				ALARTE_ASCENDARE + N + LUMOS_MAXIMA + N + 
						OBLIVIATE + N + ASCENDIO);
		bookMap.put("Quintessence: A Quest",
				CRESCERE_PROTEGAT + N + FIANTO_DURI + N + HORREAT_PROTEGAT + N + 
						NULLUM_APPAREBIT + N + NULLUM_EVANESCUNT + N + PARTIS_TEMPORUS + N + 
						PROTEGO_HORRIBILIS + N + PROTEGO_MAXIMA + N + PROTEGO_TOTALUM + N + 
						SCUTO_CONTERAM);
		bookMap.put("The Standard Book of Spells, Grade 1",
				INCENDIO + N + LUMOS + N + REPARO + N + SPONGIFY + N + 
						WINGARDIUM_LEVIOSA + N + COLLOPORTUS + N + ALOHOMORA);
		bookMap.put("The Standard Book of Spells, Grade 2", 
				EXPELLIARMUS + N + IMMOBULUS + N + INCENDIO + N + LUMOS + N + 
						OBLIVIATE + N + ALOHOMORA + N + ENGORGIO + N + 
						FINITE_INCANTATEM);
		bookMap.put("The Standard Book of Spells, Grade 3", 
				AQUA_ERUCTO + N + BOMBARDA + N + EXPELLIARMUS + N + 
						GLACIUS + N + LUMOS_DUO + N + REPARO + N + DRACONIFORS + N + 
						CARPE_RETRACTUM);
		bookMap.put("The Standard Book of Spells, Grade 4", 
				ACCIO + N + ARRESTO_MOMENTUM + N + BOMBARDA_MAXIMA + N + 
						DUCKLIFORS + N + FIANTO_DURI + N + PROTEGO_HORRIBILIS + N + 
						PROTEGO_MAXIMA + N + PROTEGO_TOTALUM + N + GLACIUS_DUO);
		bookMap.put("The Standard Book of Spells, Grade 5", 
				ACCIO + N + EQUUSIFORS + N + EXPELLIARMUS + N + INCENDIO + N + 
						PROTEGO + N + REPARO + N + SCUTO_CONTERAM + N + STUPEFY + N + 
						WINGARDIUM_LEVIOSA + N + GLACIUS_TRIA + N + DEPULSO);
		bookMap.put("The Standard Book of Spells, Grade 6", 
				APPARATE + N + CRESCERE_PROTEGAT + N + HORREAT_PROTEGAT + N + 
						INCENDIO_DUO);
		bookMap.put("The Standard Book of Spells, Grade 7", 
				NULLUM_APPAREBIT + N + NULLUM_EVANESCUNT + N + PARTIS_TEMPORUS + N + 
						PIERTOTUM_LOCOMOTOR + N + PORTUS + N + INCENDIO_TRIA);
		bookMap.put("Basic Hexes for the Busy and Vexed", 
				IMMOBULUS);
		bookMap.put("A Compendium of Common Curses and Their Counter-Actions", 
				INCENDIO_DUO + N + SILENCIO);
		bookMap.put("Confronting the Faceless", 
				AVADA_KEDAVRA + N + IMPEDIMENTA);
		bookMap.put("Curses and Counter-Curses",
				"");
		bookMap.put("Dark Arts Defence: Basics for Beginners", 
				ARRESTO_MOMENTUM + N + HARMONIA_NECTERE_PASSUS + N + EPISKEY);
		bookMap.put("Defensive Magical Theory", 
				INFORMOUS + N + SILENCIO + N + ASCENDIO);
		bookMap.put("The Dark Arts Outsmarted",
				AVADA_KEDAVRA + N + FIENDFYRE + N + INFORMOUS + N + HARMONIA_NECTERE_PASSUS);
		bookMap.put("The Dark Forces: A Guide to Self-Protection", 
				LUMOS);
		bookMap.put("Guide to Advanced Occlumency", 
				"");
		bookMap.put("Jinxes for the Jinxed", 
				MUFFLIATO + N + SPONGIFY + N + FLAGRANTE + N + DIMMINUENDO + N + 
				FINITE_INCANTATEM);
		bookMap.put("Practical Defensive Magic and Its Use Against the Dark Arts",
				"");
		bookMap.put("Self-Defensive Spellwork", 
				APARECIUM + N + DEPRIMO + N + DEPULSO);
		bookMap.put("Updated Counter-Curse Handbook (Second Revised Edition)", 
				ALARTE_ASCENDARE + N + FLAGRANTE + N + INCENDIO_TRIA + N + 
				FINITE_INCANTATEM);
		bookMap.put("Magick Moste Evile",
				FIENDFYRE);
		bookMap.put("Secrets of the Darkest Art", 
				"The most horrifying and destructive act man can do is the "
						+ "creation of a horcrux. By splitting one's soul, one is able "
						+ "to resurrect with all of their magical experience intact. "
						+ "However, this action has a terrible cost, for as long as "
						+ "the soul is split, when the body takes damage, it will take "
						+ "a multiplied amount, based on the number of horcruxes one has"
						+ "made. The only known way of destroying a horcrux is with fiendfyre.\n" + 
						ET_INTERFICIAM_ANIMAM_LIGAVERIS);
		bookMap.put("A Beginner's Guide to Transfiguration", 
				"Transfiguration involves the transformation of one entity into "
						+ "another. All transfiguration has a time duration, after which "
						+ "the entity will transfigure back into it's previous state.\n" + 
						DELETRIUS + N + DUCKLIFORS + N + REPARIFARGE);
		bookMap.put("A Guide to Advanced Transfiguration", 
				AVIS + N + DURO + N + EVANESCO + N + PIERTOTUM_LOCOMOTOR);
		bookMap.put("Intermediate Transfiguration", 
				AVIS + N + EQUUSIFORS + N + OPPUGNO + N + DRACONIFORS);
		bookMap.put("Theories of Transubstantial Transfiguration", 
				"Transubstantial transfiguration encompasses the spells which "
						+ "seem to break the rules of transfiguration, namely that an "
						+ "entity must be transfigured into another entity and that "
						+ "the transfiguration has a time duration.\n" + 
						EVANESCO + N + GEMINIO + N + REPARIFARGE);
		bookMap.put("Madcap Magic for Wacky Warlocks", 
				DEPRIMO + N + MELOFORS);
		bookMap.put("Saucy Tricks for Tricky Sorts", 
				CONFUNDO + N + EVERTE_STATUM + N + MELOFORS);
		bookMap.put("Book of Spells", 
				ACCIO + N + AGUAMENTI + N + APARECIUM + N + AVIS + N + 
						DEFODIO + N + DURO + N + EBUBLIO + N + EXPELLIARMUS + N + 
						GEMINIO + N + IMPEDIMENTA + N + INCENDIO + N + LUMOS + N + 
						OPPUGNO + N + PROTEGO + N + REDUCTO + N + REPARO + N + 
						STUPEFY + N + WINGARDIUM_LEVIOSA + N + ALOHOMORA + N + 
						ENGORGIO);
		bookMap.put("Easy Spells to Fool Muggles", 
				CONFUNDO + N + EVERTE_STATUM);
		bookMap.put("Wizard's Spells, Volume 1", 
				GLACIUS + N + COLLOPORTUS + N + TERGEO + N + CARPE_RETRACTUM);
		bookMap.put("Wizard's Spells, Volume 2", 
				BOMBARDA + N + MUFFLIATO + N + REDUCTO + N + GLACIUS_DUO);
		bookMap.put("Wizard's Spells, Volume 3", 
				BOMBARDA_MAXIMA + N + PACK + N + GLACIUS_TRIA);
		bookMap.put("Practical Magic", 
				APPARATE + N + AQUA_ERUCTO + N + DEFODIO + N + DELETRIUS + N + 
						HERBIVICUS + N + PORTUS + N + TERGEO + N + PACK);
		bookMap.put("The Secrets of Wandlore",
				"The secrets of wandlore are not to be easily had, however " +
						"they will be related in this book with the greatest of ease.\n" +
						FRANGE_LIGNEA + N + LIGATIS_COR);
		return bookMap;
	}
}