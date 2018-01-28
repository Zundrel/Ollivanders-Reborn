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
 * @author lownes
 */
@Deprecated
public class OPlayer implements Serializable {
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

	public Map<Spells, Integer> getSpellCount() {
		return SpellCount;
	}

	public void setSpellCount(Map<Spells, Integer> map) {
		SpellCount = map;
	}

	public Spells getSpell() {
		return spell;
	}

	public void setSpell(Spells s) {
		spell = s;
	}

	public void resetSpellCount() {
		Spells[] spells = Spells.values();
		for (Spells spell : spells) {
			SpellCount.put(spell, 0);
		}
	}

	public int getSouls() {
		return souls;
	}

	public void resetSouls() {
		souls = 0;
	}

	public void addSoul() {
		souls++;
	}

	public void subSoul() {
		souls--;
	}

	public List<OEffect> getEffects() {
		return effects;
	}

	public void addEffect(OEffect e) {
		effects.add(e);
	}

	public void remEffect(OEffect e) {
		effects.remove(e);
	}

	public void resetEffects() {
		effects.clear();
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	/**
	 * Has the player been rendered invisible from all other players by repello
	 * muggleton
	 *
	 * @return true if player is hidden
	 */
	public boolean isMuggleton() {
		return muggleton;
	}

	public void setMuggleton(boolean mug) {
		muggleton = mug;
	}
}