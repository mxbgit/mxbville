package mxbville.common.player;

import mxbville.common.calc.math.MxRand;
import mxbville.util.MxRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedPlayerProperties {
	
	public static final ResourceLocation key 				= new ResourceLocation(MxRef.MOD_ID + ".expp");
	public static final int 			 NewMailTimerTotal 	= 800; // Default 2000
	public EntityPlayer 	player;
	public int 				treasureHuntLevel;
	public boolean 			hasSentInvitation;
	public int 				newMailTimer;

	public static ExtendedPlayerProperties get(EntityPlayer player)
	{
		return player.getCapability(CapExPlayerProperties.EXTENDED_PLAYER_PROPERTIES_CAPABILITY, null);
	}
	
	public void sendNewVillagerInvitation()
	{
		if(!this.hasSentInvitation){
			this.hasSentInvitation = true;
			this.resetMailTimer();
		}
	}
	
	public void resetMailTimer()
	{
		this.newMailTimer = (int)(ExtendedPlayerProperties.NewMailTimerTotal * (MxRand.get().nextFloat() * 0.5F + 0.5F));
	}
	
	public boolean hasNewVillagerMail()
	{
		return this.hasSentInvitation && this.newMailTimer <= 0;
	}
	
	public void receiveNewVillagerMail()
	{
		this.hasSentInvitation = false;
	}
	
	public void upgradeTreasureHuntLevelTo(int level)
	{
		treasureHuntLevel = level;
	}
	
	public ExtendedPlayerProperties(EntityPlayer player)
	{
		this.player = player;
	}
	
	public void init(Entity entity, World world)
	{
		this.resetMailTimer();
		this.hasSentInvitation = false;
		this.treasureHuntLevel = 0;
	}
	
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setInteger("treasurelvl", this.treasureHuntLevel);
		compound.setBoolean("invited", this.hasSentInvitation);
		compound.setInteger("newmailtimer", this.newMailTimer);
	}
	
	public void loadNBTData(NBTTagCompound compound)
	{
		this.hasSentInvitation = compound.getBoolean("invited");
		this.newMailTimer = compound.getInteger("newmailtimer");
		this.treasureHuntLevel = compound.getInteger("treasurelvl");
	}
}
