package mxbville.common.player;

import mxbville.util.MxRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ExtendedPlayerProperties {
	
	public static final ResourceLocation key = new ResourceLocation(MxRef.MOD_ID + ".expp");
	public EntityPlayer player;
	public int treasureHuntLevel;
	
	public static ExtendedPlayerProperties get(EntityPlayer player)
	{
		return player.getCapability(CapExPlayerProperties.EXTENDED_PLAYER_PROPERTIES_CAPABILITY, null);
	}
	
	
	public ExtendedPlayerProperties(EntityPlayer player)
	{
		this.player = player;
	}
	
	public void init(Entity entity, World world)
	{
		this.treasureHuntLevel = 0;
	}
	
	public void saveNBTData(NBTTagCompound compound)
	{
		compound.setInteger("treasurelvl", this.treasureHuntLevel);
	}
	
	public void loadNBTData(NBTTagCompound compound)
	{
		this.treasureHuntLevel = compound.getInteger("treasurelvl");
	}
}
