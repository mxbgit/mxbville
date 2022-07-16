package mxbville.common.items.documents;

import mxbville.common.items.ItemBase;
import mxbville.common.items.ModItems;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
	 *  Generiert die Persoenlichkeit eines neuen Villegers und bindet die Daten an ein ItemMail Objekt, das hier erschaffen wird
	 * @param sender	Name des Villegers
	 * @param content	Begruessungstext des Villegers
	 * @param traitID	ACHTUNG: ID, die auf einen Persoenlichkeitszweig hinweist. Mussueberall dort, wo ein String verlangt ist, uebersetzt werden
	 * @param mailtype	Intwert zwischen 0 und 2. 1 := Maennlich, 2:= Weiblich
	 * @return
	 */
	public static ItemStack generateMail(String sender, String content, int traitID, int mailtype)
	{
		ItemStack mail = new ItemStack(ModItems.LETTER_REPLY);
		setMailSender(mail, sender);
		setMailContent(mail, content);
		setMailTraitID(mail, traitID);
		setMailType(mail, mailtype);
		return mail;
	}
	

	private static boolean checkStack(ItemStack stack){
		if(stack.getItem() == ModItems.LETTER_REPLY){
			if(!stack.hasTagCompound()){
				stack.setTagCompound(new NBTTagCompound());
			}
			return true;
		}
		return false;
	}

	public static void setMailContent(ItemStack stack, String content){
		if(checkStack(stack)){
			stack.getTagCompound().setString("content", content);
		}
	}
	
	public static void setMailTraitID(ItemStack stack, int traitID){
		if(checkStack(stack)){
			stack.getTagCompound().setInteger("trait", traitID);
		}
	}
	
	public static void setMailSender(ItemStack stack, String sender){
		if(checkStack(stack)){
			stack.getTagCompound().setString("sender", sender);
		}
	}
	
	public static void setMailType(ItemStack stack, int mailType){
		if(checkStack(stack)){
			stack.getTagCompound().setInteger("mailtype", mailType);
		}
	}
	
	
	public static String getMailContent(ItemStack stack){
		if(checkStack(stack)){
			String content = stack.getTagCompound().getString("content");
			if(content != null && content != "") 
				return content;
		}
		return "???";
	}
	
	public static String getMailSender(ItemStack stack){
		if(checkStack(stack)){
			String sender = stack.getTagCompound().getString("sender");
			if(sender != null && sender != "") 
				return sender;
		}
		return "???";
	}
	
	public static int getMailTraitId(ItemStack stack){
		if(checkStack(stack)){
			return stack.getTagCompound().getInteger("trait");
		}
		return 0;
	}
	
	public static String getMailTraitString(ItemStack stack){
		if(checkStack(stack)){
			String traitString = I18n.format(MxRef.MOD_ID + ":mail.trait" + ItemReplyLetter.getMailTraitId(stack) + ".content");
			if(traitString != null && traitString != "") 
				return traitString;
		}
		return "???";
	}
	
	
	public static int getMailType(ItemStack stack){
		if(checkStack(stack)){
			return stack.getTagCompound().getInteger("mailtype");
		}
		return MailType_Common;
	}
}
