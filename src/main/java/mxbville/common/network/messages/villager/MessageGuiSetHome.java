package mxbville.common.network.messages.villager;

import io.netty.buffer.ByteBuf;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.common.network.ModNetwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiSetHome implements IMessage {
	private int dimension;
	private int entityVillagerID;
	private boolean isMoveIn;

	public MessageGuiSetHome() {
	}

	public MessageGuiSetHome(int entityVillagerID, int dimension, boolean isMoveIn) {
		this.entityVillagerID = entityVillagerID;
		this.dimension = dimension;
		this.isMoveIn = isMoveIn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityVillagerID = buf.readInt();
		this.dimension = buf.readInt();
		this.isMoveIn = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityVillagerID);
		buf.writeInt(this.dimension);
		buf.writeBoolean(this.isMoveIn);
	}

	public static class Handler implements IMessageHandler<MessageGuiSetHome, IMessage> {
        /**
         * This gets called when the packet is read and received.
         */
        @Override
		public IMessage onMessage(MessageGuiSetHome message, MessageContext ctx) {
			EntityPlayerMP playerHandler = ctx.getServerHandler().player;
			
			if(playerHandler.world.provider.getDimension() == message.dimension) {
				Entity entity = playerHandler.world.getEntityByID(message.entityVillagerID);
				if((entity != null) && (entity instanceof EntityMxVillager)) {
					EntityMxVillager villager = (EntityMxVillager)entity;
					if(message.isMoveIn) {
						// Check surrounding of the villager
						villager.checkCurrentPosForHome();
						villager.trySetCurrentPosAsHome();
					}else {
						villager.moveOutHome(playerHandler);
					}
				}
			}
			
			return null;
		}
	}
}