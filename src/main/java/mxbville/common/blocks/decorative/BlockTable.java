package mxbville.common.blocks.decorative;

import mxbville.MxBville;
import mxbville.common.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockTable extends BlockBase{
	
	public BlockTable(String name)
	{
        super(name, Material.WOOD);
        this.setHardness(2.5F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(MxBville.SPECIALTAB);
        this.translucent = true;
    }

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

}
