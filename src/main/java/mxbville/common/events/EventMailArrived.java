package mxbville.common.events;

import java.util.Random;

import mxbville.common.calc.math.MxRand;
import mxbville.common.items.documents.InvitationType;
import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EventMailArrived {
	
	private enum ReplyType {
		SUCCESS, 
		AMBUSH,
		FAIL, 	
	}
	
	private World worldRef;
	private EntityPlayer playerRef;
	private BlockPos eventCenter;
	private InvitationType currentlySendLetter = null;

	public EventMailArrived(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		this.worldRef = worldIn;
		this.playerRef = playerIn;
		this.eventCenter = pos;
		String sendLetterString = ExtendedPlayerProperties.get(playerIn).getCurrentlySendLetter();
		if ((sendLetterString != null) && (!sendLetterString.isEmpty()))
		{
			this.currentlySendLetter = InvitationType.valueOf(sendLetterString);
		}
		
	}
	
	
	public boolean resolve() {
		if (this.currentlySendLetter != null)
		{
			String reply = this.calculateReplyTyp().name();
			this.recieveReplyLetter(this.calculateReplyTyp());
			
			this.playerRef.sendMessage(new TextComponentString("Send letter: " + this.currentlySendLetter.name() + ". Recieved Result: " + reply));
		}else {
			this.playerRef.sendMessage(new TextComponentTranslation(MxRef.MOD_ID +":message.mail.errortext"));
		}
		
		// must return true for the calling Block action to finish properly
		return true;

	}
	
	private ReplyType calculateReplyTyp() {
		
		Integer result		 	= MxRand.get().nextInt(101);
		Integer successRange 	= this.currentlySendLetter.getSuccessRate();
		Integer ambushRange 	= successRange + this.currentlySendLetter.getAmbushRate();

		
		if (result > ambushRange )
		{
			return ReplyType.FAIL;
		} else if (result > successRange) 
		{
			return ReplyType.AMBUSH;
		} else  
		{
			return ReplyType.SUCCESS;
		}
	}
	
	private void recieveReplyLetter(ReplyType type) {
		
		ExtendedPlayerProperties.get(this.playerRef).receiveReply();
		// TODO: generate Success Mail 
		// wiht all the necessary villager Information 
		ItemStack mail = new ItemStack(Items.DIAMOND);
		
		this.dropMailStack(mail);
	}
	
	private void dropMailStack(ItemStack stack) {
		
		double x = (double)this.eventCenter.getX() + 0.5D;
		double y = (double)this.eventCenter.getY() + 0.5D;
		double z = (double)this.eventCenter.getZ() + 0.5D;
		EntityItem entityitem = new EntityItem(this.worldRef, x, y, z, stack);
		
		double d1 = this.playerRef.posX - x;
		double d3 = this.playerRef.posY - y;
		double d5 = this.playerRef.posZ - z;
		double d7 = (double)MathHelper.sqrt(d1 * d1 + d3 * d3 + d5 * d5);
		double d9 = 0.08D;
		entityitem.motionX = d1 * d9;
		entityitem.motionY = d3 * d9 + (double)MathHelper.sqrt(d7) * 0.05D;
		entityitem.motionZ = d5 * d9;
    
		entityitem.setDefaultPickupDelay();
		this.worldRef.spawnEntity(entityitem);    		
	}
}
