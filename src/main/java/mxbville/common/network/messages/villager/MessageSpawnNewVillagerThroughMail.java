package mxbville.common.network.messages.villager;

import io.netty.buffer.ByteBuf;
import mxbville.common.items.documents.ItemReplyMail;
import mxbville.util.MxRef;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
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
			
			EntityPlayer player = ctx.getServerHandler().player;
			ItemStack currentlyHoldItemstack = player.getHeldItemMainhand();
			
			if(currentlyHoldItemstack.getItem() instanceof ItemReplyMail){
				
				boolean isMale = ItemReplyMail.getMailSenderGender(currentlyHoldItemstack);
				//TODO: create villager entity instance with the letters parameters
				//TODO: calculate spawn position	
				//TODO: spawn villager 
				
				player.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.errortext", ""));
				currentlyHoldItemstack.shrink(1);
			}
			
			return null;
		}
		
	}
}
