package mxbville.common.network.messages.villager;

import io.netty.buffer.ByteBuf;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.common.items.documents.ItemReplyMail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSpawnNewVillagerThroughMail implements IMessage {

	public MessageSpawnNewVillagerThroughMail() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<MessageSpawnNewVillagerThroughMail, IMessage> {

		@Override
		public IMessage onMessage(MessageSpawnNewVillagerThroughMail message, MessageContext ctx) {
			
			EntityPlayer playerHandler = ctx.getServerHandler().player;
			ItemStack currentlyHoldItemstack = playerHandler.getHeldItemMainhand();
			
			if(currentlyHoldItemstack.getItem() instanceof ItemReplyMail)
			{
				String villagerName     = ItemReplyMail.getMailSenderName(currentlyHoldItemstack);
				boolean isMale 		    = ItemReplyMail.getMailSenderGender(currentlyHoldItemstack);
                String affinity	        = ItemReplyMail.getMailAffinity(currentlyHoldItemstack); 
                //String affinityOrigin   = ItemReplyMail.getMailTextTranslationKey(currentlyHoldItemstack); 
				//TODO: Rework the affinity origin text generation, so the villager entity remembers its origin and doesn't invent a different one from his letter
                
                EntityMxVillager villager = new EntityMxVillager(playerHandler.world,
																villagerName,
																affinity,
																isMale );
				
				BlockPos spawnPos = this.calculateSpawnPos(playerHandler);
				villager.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), playerHandler.rotationYaw + 180, 0);
				ctx.getServerHandler().player.world.spawnEntity(villager);
				
				currentlyHoldItemstack.shrink(1);
			}
			
			return null;
		}
		
		
		private BlockPos calculateSpawnPos(EntityPlayer player) {
			double d = 2.0F;
			BlockPos resultPos = new BlockPos(	player.posX - Math.sin(player.rotationYaw / 180.0F * (float)Math.PI) * d,
												player.posY,
												player.posZ + Math.cos(player.rotationYaw / 180.0F * (float)Math.PI) * d
											);
			return resultPos;
		}
	}
}
