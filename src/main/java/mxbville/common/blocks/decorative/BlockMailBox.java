package mxbville.common.blocks.decorative;

import mxbville.MxBville;
import mxbville.common.blocks.misc.BlockFacing;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMailBox extends BlockFacing
{
	public static final AxisAlignedBB MAILBOX_AABB_W_E = new AxisAlignedBB(0, 0, 0.1875D, 1, 0.5625D, 0.8125D);
	public static final AxisAlignedBB MAILBOX_AABB_N_S = new AxisAlignedBB(0.1875D, 0, 0,  0.8125D, 0.5625D, 1);
	
	public BlockMailBox() {
		super("mailbox" ,Material.WOOD);
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(MxBville.SPECIALTAB);	
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		// TODO Auto-generated method stub
		return BlockFaceShape.UNDEFINED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int facingindex = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
		if (facingindex == 0 || facingindex == 2 )
		{
			return MAILBOX_AABB_N_S;
		}else
		{
			return MAILBOX_AABB_W_E;
		}

	}
}
