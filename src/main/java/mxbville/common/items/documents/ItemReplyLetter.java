package mxbville.common.items.documents;

import mxbville.MxBville;
import mxbville.common.gui.GUIIDList;
import mxbville.common.items.ItemBase;
import mxbville.common.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemReplyLetter extends ItemBase
{
	public static final int MailType_Common = 0;
	public static final int MailType_NewVillagerMale = 1;
	public static final int MailType_NewVillagerFemale = 2;
	
	public ItemReplyLetter(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	/**
	 * Generates the mail Item with certain villager personality information
	 * @param sender		Name of the villager
	 * @param introduction	one of fife possible introduction texts for the mail
	 * @param traitHint		text that contains information about the villager and hints at its traitID
	 * @param traitID		name of the villagers potential profession
	 * @param mailtype		number that indicates gender. 1 := male, 2:= female
	 * @return
	 */
	public static ItemStack generateMail(String sender, String introduction, String traitHint, String traitID, int mailtype)
	{
		ItemStack mail = new ItemStack(ModItems.LETTER_REPLY);
		setMailSender(mail, sender);
		setMailContent(mail, introduction, traitHint);
		setMailTraitID(mail, traitID);
		setMailType(mail, mailtype);
		return mail;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		ItemStack itemstack = playerIn.getHeldItem(hand);
		
		if(!worldIn.isRemote){
			playerIn.openGui(MxBville.instance, GUIIDList.MAIL_REPLY, worldIn, 0, 0, 0);
		} 
		
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
	

	private static boolean checkStack(ItemStack stack)
	{
		if(stack.getItem() == ModItems.LETTER_REPLY) {
			if(!stack.hasTagCompound()){
				stack.setTagCompound(new NBTTagCompound());
			}
			return true;
		}
		return false;
	}

	public static void setMailContent(ItemStack stack, String content, String traitHint)
	{
		if(checkStack(stack)) {
			stack.getTagCompound().setString("content", content + " " + traitHint);
		}
	}
	
	
	public static void setMailTraitID(ItemStack stack, String traitID)
	{
		if(checkStack(stack)) {
			stack.getTagCompound().setString("trait", traitID);
		}
	}
	
	public static void setMailSender(ItemStack stack, String sender)
	{
		if(checkStack(stack)) {
			stack.getTagCompound().setString("sender", sender);
		}
	}
	
	public static void setMailType(ItemStack stack, int mailType)
	{
		if(checkStack(stack)) {
			stack.getTagCompound().setInteger("mailtype", mailType);
		}
	}
	
	
	public static String getMailContent(ItemStack stack)
	{
		if(checkStack(stack)) {
			String content = stack.getTagCompound().getString("content");
			if(content != null && content != "") 
				return content;
		}
		return "???";
	}
	
	public static String getMailSender(ItemStack stack)
	{
		if(checkStack(stack)) {
			String sender = stack.getTagCompound().getString("sender");
			if(sender != null && sender != "") 
				return sender;
		}
		return "???";
	}
	
	public static String getMailTraitId(ItemStack stack)
	{
		if(checkStack(stack)) {
			return stack.getTagCompound().getString("trait");
		}
		return "???";
	}
	
	public static int getMailType(ItemStack stack){
		if(checkStack(stack)) {
			return stack.getTagCompound().getInteger("mailtype");
		}
		return MailType_Common;
	}
}
