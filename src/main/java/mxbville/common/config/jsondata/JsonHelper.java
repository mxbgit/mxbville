package mxbville.common.config.jsondata;

import mxbville.common.calc.math.MxRand;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class JsonHelper {
	
	/**
	 * Objectives & rewards item stack string format: DOMAIN,ITEMNAME,AMOUNT,-FLAG-
	 * Example: minecraft,log,1,0 indicates a Vanillia Minecraft Oak Wood
	 * The amount should be between 1 and 64
	 * The -FLAG- value can be
	 * I. 	A datavalue
	 * II.	A '*' for a random datavalue
	 * III. The string 'potion'
	 * IV.	The string 'enchanted'
	 * 
	 * (Note that every Flag except I. needs further information after the flag entry)
	 * (see JsonVillagerData Class for further information)
	 * @return stack Itemstack (with NBT Tags in case of III. and IV.)	
	 */
	public static ItemStack stringToItemStack(String text)
	{
		String[] arrs = text.split(",");
		Item item = null;
		ItemStack stack = ItemStack.EMPTY;
		int datavalue = 0;
		
		//checks whether the Item is enchated,
		//an enchanted book, a Potion, 
		//or has an Random datavalue
		
		String dataflag = arrs[3];
		
		switch (dataflag)
		{
			case "enchanted":	// For enchanted items or books
				stack = generateStack(arrs[0],arrs[1],Integer.valueOf(arrs[2]),datavalue);
				// in case of items and armor the tagname is 'ench'
				String tagname = "ench";
				// but in case of an enchanted book its 'StoredEnchantments'
				if(arrs[1].equals("enchanted_book")) {
					tagname = "StoredEnchantments";
				}
				
				NBTTagCompound nbt = stack.getTagCompound();
				if (nbt == null) { nbt = new NBTTagCompound();}		
				NBTTagList nbtList = nbt.getTagList(tagname, 0);

				// arr[4] contains the amount of enchantments,
				// everything that follows is (ENCHANT_ID : ENCHANT_LVL)
				for (int i = 0; i < Integer.valueOf(arrs[4]); i++) {
					String[] ench = arrs[5 + i].split(":");
					
					NBTTagCompound tagEnchant = new NBTTagCompound();
					tagEnchant.setInteger("id", Integer.valueOf(ench[0]));
					tagEnchant.setInteger("lvl", Integer.valueOf(ench[1]));
					nbtList.appendTag(tagEnchant);
				}
					
				nbt.setTag(tagname, nbtList);
				stack.setTagCompound(nbt); 
				break;
			case "potion":	// For potions with effects that follow the IDENTIFIER:EFFECTNAME format (minecraft:long_water_breathing)
				stack = generateStack(arrs[0],arrs[1],Integer.valueOf(arrs[2]),datavalue);
				NBTTagCompound nbtag = stack.getTagCompound();
				if (nbtag == null) { nbtag = new NBTTagCompound();}
				// The identifier for a potion effect like "minecraft:long_water_breathing"
				// is within the string arrs[4] to allow different identifiers from other mods
				if (nbtag.hasKey("Potion")) {nbtag.setString("Potion", arrs[4]); }
				else {nbtag.setString("Potion", arrs[4]); }
			
				stack.setTagCompound(nbtag);
				break;
			case "*":	// For a random Datavalue 
				datavalue = MxRand.get().nextInt(Integer.valueOf(arrs[4]));
				stack = generateStack(arrs[0],arrs[1],Integer.valueOf(arrs[2]),datavalue);
				break;
			default:
				datavalue = Integer.valueOf(arrs[3]);
				stack = generateStack(arrs[0],arrs[1],Integer.valueOf(arrs[2]),datavalue);
				break;
		}
		return stack;
	}
	
	public static ItemStack[] stringsToItemStacks(String[] texts)
	{
		if(texts == null || texts.length == 0) return null;
		
		ItemStack[] stacks = new ItemStack[texts.length];
		for(int i =0;i<stacks.length;i++)
			stacks[i] = stringToItemStack(texts[i]);
		return stacks;
	}
	
	/**
	 *  This function lacks my changes to include enchantmens, potions and random data values...
	 *  But aslong as my Villagers only buy unenchanted stuff, no potions, and items with a
	 *  specified data value, no errors will occur
	 *  MxB.
	 */
	public static String itemStackToString(ItemStack stack)
	{
		ResourceLocation location = Item.REGISTRY.getNameForObject(stack.getItem());
		return String.format("%s,%s,%d,%d", location.getResourceDomain(), location.getResourcePath(), stack.getMaxStackSize(), stack.getItemDamage());
	}
	
	public static String[] itemStacksToStrings(ItemStack[] stacks)
	{
		if(stacks == null) return null;
		String[] texts = new String[stacks.length];
		for(int i =0;i<stacks.length;i++)
		{
			// there might be errors here
			texts[i] = itemStackToString(stacks[i]);
		}
		return texts;
	}
	
	private static ItemStack generateStack(String domain, String path, int amount, int datavalue) {
		Item item = Item.REGISTRY.getObject(new ResourceLocation(domain,path));
		if(item == null)
		{
			item = Items.EGG;//if we can't find the item, simply replace it with eggs.....
			datavalue = 0;
		}
		
		return new ItemStack(item, amount, datavalue);
	}
}

