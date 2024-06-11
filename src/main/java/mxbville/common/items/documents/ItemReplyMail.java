package mxbville.common.items.documents;

import java.util.List;

import javax.annotation.Nullable;

import mxbville.MxBville;
import mxbville.common.gui.GUIIDList;
import mxbville.common.items.ItemBase;
import mxbville.common.items.ModItems;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemReplyMail extends ItemBase {
	
	public ItemReplyMail() {
		super("letter_reply");
		this.setMaxStackSize(1);
	}
	
	/**
	 * Creates an ItemReply ItemStack 
	 * with set content for the Villager to generate.
	 *  
	 * @param senderName string. Name of the villager, who will be generated
	 * @param mailText	string. Text that is displayed in the reply gui
	 * @param affinityString string. Profession name, the villager has an affinity to
	 * 
	 * @return itemstack. ItemReplyMail with set content for villager generation
	 */
	public static ItemStack generateMail(String senderName, String mailTextTranslationKey, String affinityString, boolean ambushFlag)
	{
		ItemStack mail = new ItemStack(ModItems.LETTER_REPLY);
		setMailSenderName(mail, senderName);
		setMailTextTranslationKey(mail, mailTextTranslationKey);
		setMailAffinity(mail, affinityString);
		setAmbushFlag(mail, ambushFlag);
		return mail;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		//ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		if(!worldIn.isRemote)
		{
			playerIn.openGui(MxBville.instance, GUIIDList.LETTER_REPLY, worldIn, 0, 0, 0);
		} 
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	private static boolean checkStack(ItemStack stack){
		if(stack.getItem() == ModItems.LETTER_REPLY)
		{
			if(!stack.hasTagCompound()){
				stack.setTagCompound(new NBTTagCompound());
			}
			return true;
		}
		return false;
	}
	
	public static void setAmbushFlag(ItemStack stack, boolean isAmbush) {
		if(checkStack(stack)){
			stack.getTagCompound().setBoolean("isambush", isAmbush);
		}
	}
	
	public static boolean getAmbushFlag(ItemStack stack) {
		if (checkStack(stack)) {
			boolean isAmbush = stack.getTagCompound().getBoolean("isambush");
			return isAmbush;
		}
		return false;
	}
	
	public static void setMailTextTranslationKey(ItemStack stack, String content){
		if(checkStack(stack)){
			stack.getTagCompound().setString("mailtextkey", content);
		}
	}
	
	public static String getMailTextTranslationKey(ItemStack stack) {
		if (checkStack(stack))
		{
			String mailText = stack.getTagCompound().getString("mailtextkey");
			if ((mailText != null) && (mailText.trim().length() != 0))
				return mailText;
		}
		return "...";
	}
	
	public static void setMailSenderName(ItemStack stack, String senderName){
		if(checkStack(stack))
		{
			stack.getTagCompound().setString("sendername", senderName);
		}
	}
	
	public static String getMailSenderName(ItemStack stack){
		if(checkStack(stack))
		{
			String sender = stack.getTagCompound().getString("sendername");
			if((sender != null) && (sender.trim().length() != 0)) 
				return sender;
		}
		return "???";
	}
		
	public static void setMailAffinity(ItemStack stack, String affinityString){
		if(checkStack(stack))
		{
			stack.getTagCompound().setString("affinity", affinityString);
		}
	}
	
	/**
	 * Flag for ambushes.
	 * 
	 * @param stack
	 * @return string. A profession title or the string "ambush"
	 */
	public static String getMailAffinity(ItemStack stack){
		if(checkStack(stack))
		{
			String affinity = stack.getTagCompound().getString("affinity");
			if((affinity != null) && (affinity.trim().length() != 0)) 
				return affinity;
		}
		return "villager";
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":letter_reply.item.info", getMailSenderName(stack));
		tooltip.add(info);
	}
	
	
}
