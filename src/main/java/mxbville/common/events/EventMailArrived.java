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
	private InvitationType currentlySendLetterType = null;

	public EventMailArrived(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		this.worldRef = worldIn;
		this.playerRef = playerIn;
		this.eventCenter = pos;
		String sendLetterString = ExtendedPlayerProperties.get(playerIn).getCurrentlySendLetter();
		if ((sendLetterString != null) && (!sendLetterString.isEmpty())) {
			this.currentlySendLetterType = InvitationType.valueOf(sendLetterString);
		}
	}
	
	public void resolve() {
		if (this.currentlySendLetterType != null) {
			// notify that a reply has been received.
			ExtendedPlayerProperties.get(this.playerRef).receiveReply();
			ReplyType type = this.calculateReplyTyp();
			if (type == ReplyType.FAIL) {
				this.playerRef.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.fail"));
			}else {
				this.dropMailStack(EventMailArrived.generatePersonalReplyLetter(type));
			}
		}
	}
	
	/**
	 * Calculates the odds of a successful invitation, an ambush event or a simple
	 * fail text message. Takes the currently send letter into account.
	 * 
	 * @return replytype according to the calculated odds.
	 */
	private ReplyType calculateReplyTyp() {

		Integer result = MxRand.get().nextInt(101);
		Integer successRange = this.currentlySendLetterType.getSuccessRate() + 1; // +1 to get the intended success range
		Integer ambushRange = successRange + this.currentlySendLetterType.getAmbushRate();

		if (result > ambushRange) {
			// random result number is bigger than the successRange + the ambushRange.
			return ReplyType.FAIL;
		} else if (result > successRange) {
			// random result number is bigger than the successRange but within the
			// ambushRange
			return ReplyType.AMBUSH;
		} else {
			// random result number is within the success range
			return ReplyType.SUCCESS;
		}
	}

	/**
	 * Generates the gender, name, and affinity of the Villager. If the ReplyType is
	 * 'ambush', generates an ambush text and sets the ambush flag in the ItemReply
	 * letter. Then creates an ItemReply Itemstack to summon the Villager or an
	 * ambush.
	 * 
	 * @param replytype ambush or success
	 * @return ItemReply Itemstack with set nbt tags
	 */
	private static ItemStack generatePersonalReplyLetter(ReplyType type) {
		ItemStack stack = ItemStack.EMPTY;

		boolean isMale = MxRand.get().nextBoolean();
		String nameString = isMale ? PersonalityGenerator.getRandomMaleName(): PersonalityGenerator.getRandomFemaleName();
		String affinityString = PersonalityGenerator.getRandomAffinity();
		String mailTextTranslationKey = "";
		boolean isAmbush = false;
		if (type == ReplyType.AMBUSH) {
			mailTextTranslationKey = "ambush." + MxRand.get().nextInt(10);
			isAmbush = true;
		} else {
			mailTextTranslationKey = affinityString + "." + MxRand.get().nextInt(3);
		}
		stack = ItemReplyMail.generateMail(nameString, mailTextTranslationKey, affinityString, isAmbush, isMale);

		return stack;
	}

	/**
	 * Generates a reply letter item and spawns it in the world.
	 * @param stack the itemstack that will be spawned
	 */
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
