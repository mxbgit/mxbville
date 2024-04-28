package mxbville.common.blocks;

import mxbville.MxBville;
import mxbville.util.IHasModel;
import mxbville.util.ModelRegister;
import mxbville.util.MxRef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockBase extends Block implements IHasModel 
{
	public BlockBase( String name, Material material)
	{
		
		super(material);
		this.setUnlocalizedName(MxRef.MOD_ID + ":" + name);
		this.setRegistryName(name);
		this.setCreativeTab(MxBville.BLOCKTAB);
		
		ModelRegister.LIST.add(this);	
	}

	@Override
	public void registerModels() {
		MxBville.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
	}

}
