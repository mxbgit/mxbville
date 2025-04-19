package mxbville.common.network.messages.villager;

import io.netty.buffer.ByteBuf;
import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiSetWaiting implements IMessage {
	private int dimension;
	private int entityVillagerID;
	private boolean setWaiting;
	
	public MessageGuiSetWaiting() {
	}
	
	public MessageGuiSetWaiting(int entityVillagerID, int dimension, boolean setWaiting) {
		this.entityVillagerID = entityVillagerID;
		this.dimension = dimension;
		this.setWaiting = setWaiting;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityVillagerID = buf.readInt();
		this.dimension = buf.readInt();
		this.setWaiting = buf.readBoolean();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityVillagerID);
		buf.writeInt(this.dimension);
		buf.writeBoolean(this.setWaiting);
		
	}
	
	public static class Handler implements IMessageHandler<MessageGuiSetWaiting, IMessage> {
        /**
         * This gets called when the packet is read and received.
         */
		@Override
		public IMessage onMessage(MessageGuiSetWaiting message, MessageContext ctx) {
			EntityPlayerMP playerHandler = ctx.getServerHandler().player;
			
			if(playerHandler.world.provider.getDimension() == message.dimension){
				Entity entity = playerHandler.world.getEntityByID(message.entityVillagerID);
        		if(entity != null && entity instanceof EntityMxVillager){
        			EntityMxVillager villager = (EntityMxVillager)entity;
        			villager.setWaiting(message.setWaiting);
        		}
			}
			return null;
		}
		
	}

}
