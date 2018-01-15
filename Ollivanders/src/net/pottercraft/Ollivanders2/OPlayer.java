package net.pottercraft.Ollivanders2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.pottercraft.Ollivanders2.Spell.Spells;

/**
 * Serializable player object
 *
 * @deprecated
 * @author lownes TODO Remove OPlayer. Breaks backwards compatibility. Will be
 *         done in the close future.
 */
@Deprecated
public class OPlayer implements Serializable {
	private static final long serialVersionUID = -2749540065635299805L;
	private Map<Spells, Integer> SpellCount = new HashMap<>();
	private List<OEffect> effects = new ArrayList<>();
	// This is the spell loaded into the wand for casting with left click
	private Spells spell;
	private int souls;
	private boolean invisible = false;
	private boolean muggleton = false;

	public OPlayer() {
		Spells[] spells = Spells.values();
		for (Spells spell : spells) {
			SpellCount.put(spell, 0);
		}
		souls = 0;
	}

	@Deprecated
	public Map<Spells, Integer> getSpellCount() {
		return SpellCount;
	}

	@Deprecated
	public void setSpellCount(Map<Spells, Integer> map) {
		SpellCount = map;
	}

	@Deprecated
	public Spells getSpell() {
		return spell;
	}

	@Deprecated
	public void setSpell(Spells s) {
		spell = s;
	}

	@Deprecated
	public void resetSpellCount() {
		Spells[] spells = Spells.values();
		for (Spells spell : spells) {
			SpellCount.put(spell, 0);
		}
	}

	@Deprecated
	public int getSouls() {
		return souls;
	}

	@Deprecated
	public void resetSouls() {
		souls = 0;
	}

	@Deprecated
	public void addSoul() {
		souls++;
	}

	@Deprecated
	public void subSoul() {
		souls--;
	}

	@Deprecated
	public List<OEffect> getEffects() {
		return effects;
	}

	@Deprecated
	public void addEffect(OEffect e) {
		effects.add(e);
	}

	@Deprecated
	public void remEffect(OEffect e) {
		effects.remove(e);
	}

	@Deprecated
	public void resetEffects() {
		effects.clear();
	}

	@Deprecated
	public boolean isInvisible() {
		return invisible;
	}

	@Deprecated
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/**
	 * Has the player been rendered invisible from all other players by repello
	 * muggleton
	 *
	 * @return true if player is hidden
	 */
	@Deprecated
	public boolean isMuggleton() {
		return muggleton;
	}

	@Deprecated
	public void setMuggleton(boolean mug) {
		muggleton = mug;
	}
}