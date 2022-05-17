package mxbville.common.events;

import mxbville.common.calc.math.MxRand;
import mxbville.common.items.ModItems;
import mxbville.common.player.ExtendedPlayerProperties;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventCoinsFound {
	
	/**
	 * Currently returns true all the time
	 * Checks the Configured WhiteList of dimension, where coin drops are allowed
	 * 
	 * 
	 * TODO: Make a config entry that lets users choose dimensions, where coins will spawn
	 * @param dimensionID to test
	 * @return
	 */
	private boolean isValidDimension(int dimensionID)
	{
		// validDimList = CONFIG_FILE.DimensionsForCoinDrops
		// if dimensionID is in validDimList true, else false
		
		return true;
	}
	
	/**
	 * Adds bronze coins to the dropslist every time a mob is killed.
	 * The amount depends on the players treasueHuntLevel (See ExtendedPlayerProperties)
	 * 
	 * TODO Make a config entry that lets users choose if coins can be collected by harvesting blocks
	 * @param event
	 */
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		if(!event.getEntityLiving().world.isRemote && 
			event.getEntityLiving() instanceof EntityMob && 
			event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			//if( CONFIG_FILE.DropCoinsOnKill){
				int l =  ExtendedPlayerProperties.get((EntityPlayer)event.getSource().getTrueSource()).treasureHuntLevel;
				int base = l * 2 + 1;
				int add = l + 3;
				event.getEntityLiving().dropItem(ModItems.COIN_BRONZE, MxRand.get().nextInt(add) + base);
			//}
		}
	}
	

	/**
	 * Adds bronze coins to the dropslist every time a block is harvested.
	 * The amount depends on the players treasueHuntLevel (See ExtendedPlayerProperties) 
	 * 
	 * TODO Make a config entry that lets users choose if coins can be collected by harvesting blocks
	 * @param event
	 */
	@SubscribeEvent
	public void onBlockHarvest(BlockEvent.HarvestDropsEvent event)
	{
		if(!event.getWorld().isRemote && event.getHarvester() != null)
		{
			//if( CONFIG_FILE.DropCoinsOnKill){
				// arbitrary 1 out of 5 calculation
				if(MxRand.get().nextInt(5) == 0)
				{
					int li = ExtendedPlayerProperties.get(event.getHarvester()).treasureHuntLevel;
					int base = li + 1;
					int add = li * 2 + 1;
					if(isValidDimension(event.getWorld().provider.getDimension()))
					{
						event.getDrops().add(new ItemStack(ModItems.COIN_BRONZE, MxRand.get().nextInt(add) + base));
					}
				}
			//}
		}
	}
}
