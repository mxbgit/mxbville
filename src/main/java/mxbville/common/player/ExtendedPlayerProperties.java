package mxbville.common.player;

import mxbville.common.calc.math.MxRand;
import mxbville.util.MxRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ExtendedPlayerProperties {
	
	public static final ResourceLocation key = new ResourceLocation(MxRef.MOD_ID + ".expp");;
	
	public static final int NewMailTimerTotal = 300; //2000 Default
	private boolean 		invitationSent;			//has the player sent an invivation?
	private int 			newMailTimer;			//how much time left to receive a new mail?
	private String			currentlySendLetter = "";
	private int 			treasureHuntLevel;
	public EntityPlayer player;

	public ExtendedPlayerProperties(EntityPlayer player){
		this.player = player;
	}

	public void init(Entity entity)
	{
		this.resetMailTimer();
		this.invitationSent = false;
		this.treasureHuntLevel = 0;
		this.currentlySendLetter = "normal";
	}
	
	public static ExtendedPlayerProperties get(EntityPlayer player)
	{
		return player.getCapability(CapExPlayerProperties.EXTENDED_PLAYER_PROPERTIES_CAPABILITY, null);
	}
	
	public int getTreasureHuntLevel() {
		return this.treasureHuntLevel;
	}

	public void upgradeTreasureHuntLevelTo(int level)
	{
		if(level > 0)
			this.treasureHuntLevel = level;
	}
	
	public String getCurrentlySendLetter() {
		return this.currentlySendLetter;
	}
		
	public boolean hasSentInvitation() {
		return this.invitationSent;
	}
	
	public void receiveReply(){
		this.invitationSent = false;
	}
	
	public void sendNewMail(String type) {
		if(!this.hasSentInvitation())
		{
			this.invitationSent = true;
			this.currentlySendLetter = type;
			this.resetMailTimer();
		}
	}
	
	public boolean hasNewVillagerMail() {
		return this.invitationSent && this.newMailTimer <= 0;
	}
	
	public void decrNewMailTimer() {
		--this.newMailTimer;
	}
	
	public int getNewMailTimer() {
		return this.newMailTimer;
	}
	
	public void resetMailTimer() {
		this.newMailTimer = (int)(ExtendedPlayerProperties.NewMailTimerTotal * (MxRand.get().nextFloat() * 0.5F + 0.5F));
	}

	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setInteger("treasurelvl", this.treasureHuntLevel);
		compound.setBoolean("invited", this.invitationSent);
		compound.setInteger("newmailtimer", this.newMailTimer);
		compound.setString("currentlySendLetter", this.currentlySendLetter);
	}
	
	public void loadNBTData(NBTTagCompound compound)
	{
		this.treasureHuntLevel = compound.getInteger("treasurelvl");
		this.invitationSent		= compound.getBoolean("invited");
		this.newMailTimer		= compound.getInteger("newmailtimer");
		this.currentlySendLetter = compound.getString("currentlySendLetter");
	}
}
