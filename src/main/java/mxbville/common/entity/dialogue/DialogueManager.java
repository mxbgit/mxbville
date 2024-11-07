package mxbville.common.entity.dialogue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mxbville.common.calc.math.MxRand;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.util.MxRef;
import net.minecraft.entity.player.EntityPlayer;

public class DialogueManager {

	private EntityPlayer playerRef;
	private EntityMxVillager currentVillager;
	private String currentVillagerProfession;
	private String currentVillagerPersonality;
	private String currentVillagerOrigin;
	
	private Map<String,String> topics = new HashMap<String, String>();
	
	public DialogueManager() {}

	public void initChatTopics() {
        topics.put("News", this.getChatKey("News"));
	    topics.put("Origin", this.getChatKey("Origin"));
	    topics.put("Profession", this.getChatKey("Profession"));
	    topics.put("Rumors", this.getChatKey("Rumors"));
	}
	
	public void setPlayerRef(EntityPlayer playerRef) {
	    this.playerRef = playerRef;
	}
	
	public void setVillagerRefs(EntityMxVillager villagerRef) {
	    this.currentVillager               = villagerRef;
	    this.currentVillagerProfession     = villagerRef.get(EntityMxVillager.PROFESSIONID) ;
	    this.currentVillagerPersonality    = villagerRef.get(EntityMxVillager.PERSONALITY);
	    this.currentVillagerOrigin         = villagerRef.get(EntityMxVillager.AFFINITYID);
	}
	
	/**
	 * Builds a key string to identify the appropriate text line in the language file.
	 * Considers the current villager profession, personality, the given type of the text line
	 * and a random value between 0 and 4.
	 * 
	 * @return key string.
	 */
	public String getChatKey(String topicName) {
	    if (topicName == "Origin") {
	        return  (MxRef.MOD_ID + ":gui.letter_reply." + currentVillagerOrigin + "." + currentVillagerPersonality + topicName.toLowerCase());
	    }
		return (MxRef.MOD_ID + ":gui.villager." + currentVillagerProfession + "." + currentVillagerPersonality + topicName.toLowerCase());
	}
}