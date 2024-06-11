package mxbville.common.events;

import mxbville.common.calc.math.MxRand;
import mxbville.common.functions.PersonalityGenerator;
import mxbville.common.items.documents.InvitationType;
import mxbville.common.items.documents.ItemReplyMail;
import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
		if (this.currentlySendLetter != null) {this.recieveReplyLetter(this.calculateReplyTyp()); }
		// must return true for the calling Block action to finish properly
		return true;
	}
	
	/**
	 * Calculates the odds of a successful invitation,
	 * an ambush event or a simple fail text message.
	 * Takes the currently send letter into account.
	 * 
	 * @return replytype according to the calculated odds.
	 */
	private ReplyType calculateReplyTyp() {
		
		Integer result		 	= MxRand.get().nextInt(101);
		Integer successRange 	= this.currentlySendLetter.getSuccessRate() + 1; // +1 to get the intended success range covered in the success checks later 
		Integer ambushRange 	= successRange + this.currentlySendLetter.getAmbushRate();
		
		if (result > ambushRange)
		{
			// random result number is bigger than the successRange + the ambushRange.
			return ReplyType.FAIL;
		} else if (result > successRange) 
		{
			// random result number is only bigger than the successRange but within the ambushRange
			return ReplyType.AMBUSH;
		} else  
		{
			// random result number is within the success range 
			return ReplyType.SUCCESS;
		}
	}
	
	private void recieveReplyLetter(ReplyType type) {
		
		ExtendedPlayerProperties.get(this.playerRef).receiveReply();
		ItemStack mail = ItemStack.EMPTY;
		switch (type) {
		case SUCCESS:
			mail = EventMailArrived.generatePersonalReplyLetter(false);
			break;
		case AMBUSH:
			mail = EventMailArrived.generatePersonalReplyLetter(true);
			break;
		case FAIL:
			this.playerRef.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.fail"));
		default:
			break;
		}
		this.dropMailStack(mail);
	}

	/**
	 * If false, generates the gender, name, and affinity of the Villager.
	 * Then creates an ItemReply Itemstack to summon the Villager.
	 * 
	 * If true, creates an ItemReply Itemstack to summon an ambush with
	 * a fake name and an ambush reply text.
	 * 
	 * @param isAmbush
	 * @return ItemReply Itemstack with set nbt tags
	 */
	private static ItemStack generatePersonalReplyLetter(boolean isAmbush) {
		ItemStack stack = ItemStack.EMPTY;
		
		boolean male 					= MxRand.get().nextBoolean();
		String nameString 				= male?PersonalityGenerator.getRandomMaleName():PersonalityGenerator.getRandomFemaleName();
		String affinityString 			= PersonalityGenerator.getRandomAffinity();
		String mailTextTranslationKey 	= "";
		if (!isAmbush)
		{
			mailTextTranslationKey = affinityString + "." + MxRand.get().nextInt(3);
		}else {
			mailTextTranslationKey = "ambush." + MxRand.get().nextInt(10);
		}
		stack = ItemReplyMail.generateMail(nameString, mailTextTranslationKey, affinityString, isAmbush);
		
		return stack;
	}

	private void dropMailStack(ItemStack stack) {
		
		if (stack != ItemStack.EMPTY)
		{
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
}
