package mxbville.client.gui;

import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class DialogManager {
	private static DialogManager instance;

	private EntityMxVillager currentVillager;
	private EntityPlayer currentPlayer;
	
	private String villagerPersonality;
	private String villagerProfession;

	private DialogManager() {}

	public static DialogManager getInstance() {
	    if (instance == null) {
	        instance = new DialogManager();
	    }
	    return instance;
	}
	
	public void prepareDialog(EntityMxVillager villager, EntityPlayer player) {
		this.currentVillager = villager;
		this.currentPlayer = player;
		
		this.villagerPersonality = villager.get(EntityMxVillager.PERSONALITY);
		this.villagerProfession = villager.get(EntityMxVillager.PROFESSIONID);
	}
	
	public String getGenericTranslationKey(String key1, String key2) {
        return I18n.format(MxRef.MOD_ID + ":gui.villager." + villagerPersonality + "." + key1 + "." + key2);
	}
	
	public String getGenericTranslationKey(String key1, Integer number) {
        return I18n.format(MxRef.MOD_ID + ":gui.villager." + villagerPersonality + "." + key1  + "." + number, currentPlayer.getName());
	}
	
	public String getTranslationKey(String key1, String key2) {
        return I18n.format(MxRef.MOD_ID + ":gui.villager." + villagerProfession + "." + villagerPersonality + "." + key1 + "." + key2);
	}

	public String getTranslationKey(String key1, Integer number) {
        return I18n.format(MxRef.MOD_ID + ":gui.villager." + villagerProfession + "." + villagerPersonality + "." + key1 + "." + number, currentPlayer.getName());
	}
	
	public void endDialog() {
		this.currentPlayer = null;
		this.currentVillager = null;
		this.villagerPersonality = "";
		this.villagerProfession = "";
	}

}
