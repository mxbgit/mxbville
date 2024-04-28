package mxbville.common.items.food;

import mxbville.MxBville;
import mxbville.common.blocks.ModBlocks;
import mxbville.util.IHasModel;
import mxbville.util.ModelRegister;
import mxbville.util.MxRef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemCornSeeds extends ItemSeeds implements IPlantable, IHasModel{

	public ItemCornSeeds(String name) {
		super(ModBlocks.CORNPLANT, Blocks.FARMLAND);
		this.setUnlocalizedName(MxRef.MOD_ID + "." + name);
		this.setRegistryName(name);
		this.setCreativeTab(MxBville.ITEMTAB);
		
		ModelRegister.LIST.add(this);
	}
	
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
		return EnumPlantType.Crop;
	}
	
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos){
		return ModBlocks.CORNPLANT.getDefaultState();
	}
	
	@Override
	public void registerModels() {
		MxBville.proxy.registerItemRenderer(this, 0);
	}
}
