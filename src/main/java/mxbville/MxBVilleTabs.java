package mxbville;

import mxbville.common.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class MxBVilleTabs extends CreativeTabs
{
	private int tab;
	
	public MxBVilleTabs(int par1, String par2Str, int par3)
	{
		super(par1,par2Str);
		this.tab = par3;
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		switch(this.tab)
		{
			case 1:
				return new ItemStack(ModItems.COIN_BRONZE);
			case 2:
				return new ItemStack(ModItems.COIN_SILVER);
			case 3:
				return new ItemStack(ModItems.COIN_GOLD);
			default:
				return new ItemStack(ModItems.COIN_BRONZE);
			}
	}

}
