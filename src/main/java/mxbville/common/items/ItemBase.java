package mxbville.common.items;

import mxbville.MxBville;
import mxbville.util.IHasModel;
import mxbville.util.ModelRegister;
import mxbville.util.MxRef;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name)
	{
		this.setUnlocalizedName(MxRef.MOD_ID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		ModelRegister.LIST.add(this);
	}
	
	
	@Override
	public void registerModels() {
		MxBville.proxy.registerItemRenderer(this, 0);
		
	}

}
