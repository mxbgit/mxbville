package mxbville.common.events;

import mxbville.common.player.CapExPlayerPropertiesProvider;
import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventEntity {
	
	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof EntityPlayer){
			event.addCapability(ExtendedPlayerProperties.key, new CapExPlayerPropertiesProvider((EntityPlayer)event.getObject()));
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdating(LivingUpdateEvent event) {
		if(!event.getEntity().world.isRemote)
		{
			//register extended player properties
			if(event.getEntity() instanceof EntityPlayer){

				ExtendedPlayerProperties playerProperties = ExtendedPlayerProperties.get((EntityPlayer)event.getEntity());
				
				if(playerProperties.hasSentInvitation() && playerProperties.getNewMailTimer() > 0)
				{
					playerProperties.decrNewMailTimer();
					if ( playerProperties.getNewMailTimer() == 0)
					{
						((EntityPlayer)event.getEntity()).sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.new"));
					}
				}

			}
		}

	}
	
	@SubscribeEvent
	void onClone(PlayerEvent.Clone event) {
	    NBTTagCompound temp = new NBTTagCompound();
	    ExtendedPlayerProperties old = ExtendedPlayerProperties.get(event.getOriginal());
	    ExtendedPlayerProperties current = ExtendedPlayerProperties.get(event.getEntityPlayer());
	    old.saveNBTData(temp);
	    current.loadNBTData(temp);
	}
}
