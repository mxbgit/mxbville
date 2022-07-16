package mxbville.common.village.trait;

import java.util.Random;

import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;

public class Trait {

	private int traitID;
	private int[] allTraits = new int[] {
		1, // Profession Name
		2, // Profession Name
		3, // Profession Name
	};
	
	private static Random rand = new Random();
	
	public Trait() {
		this.traitID = allTraits[rand.nextInt(allTraits.length)];
	}
	
	public String getLetterText() {
		return I18n.format(MxRef.MOD_ID + ":trait.letter." + traitID + ".info");
	}
	
	public String getChatText() {
		return I18n.format(MxRef.MOD_ID + ":trait.chat." + traitID + ".text");
	}
}
