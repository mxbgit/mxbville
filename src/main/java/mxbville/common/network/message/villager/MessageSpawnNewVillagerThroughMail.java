package mxbville.common.network.message.villager;

import io.netty.buffer.ByteBuf;
import mxbville.common.items.ModItems;
import mxbville.common.items.documents.ItemReplyLetter;
import mxbville.util.MxRef;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class MessageSpawnNewVillagerThroughMail implements IMessage {
	
	public MessageSpawnNewVillagerThroughMail(){
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Handler implements IMessageHandler<MessageSpawnNewVillagerThroughMail, IMessage> {
    /**
     * This gets called when the packet is read and received.
     */
		@Override
		public IMessage onMessage(MessageSpawnNewVillagerThroughMail message, MessageContext ctx)
		{
			//spawn villager
			EntityPlayer player = ctx.getServerHandler().player;
			
			ItemStack hold = player.getHeldItemMainhand();
			if(hold.getItem() == ModItems.LETTER_REPLY) {
				
				int 	gender 	= ItemReplyLetter.getMailType(hold);
				String  trait 	= ItemReplyLetter.getMailTraitId(hold);
				
				if(gender == ItemReplyLetter.MailType_NewVillagerMale || gender == ItemReplyLetter.MailType_NewVillagerFemale) {
					String name = ItemReplyLetter.getMailSender(hold);
					/*
					EntityVillager villager = new EntityVillager(player.world, name, trait, gender == ItemReplyLetter.MailType_NewVillagerMale);
					
					double d = 2.0F;
            		double x = player.posX - Math.sin(player.rotationYaw / 180.0F * (float)Math.PI) * d;
            		double z = player.posZ + Math.cos(player.rotationYaw / 180.0F * (float)Math.PI) * d;
            		double y = player.posY;

            		villager.setLocationAndAngles(x, y, z, player.rotationYaw + 180, 0);
            		
            		ctx.getServerHandler().player.world.spawnEntity(villager);
            		*/
            		
            		//player.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.newjoined", villager.getName()));
            		hold.shrink(1);
  
				}
			}
			
			return null;
		}
 
	}	

}
