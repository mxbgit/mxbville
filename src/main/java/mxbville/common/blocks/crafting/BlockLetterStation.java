package mxbville.common.blocks.crafting;

import mxbville.MxBville;
import mxbville.common.blocks.misc.BlockFacing;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLetterStation extends BlockFacing {

	public static final AxisAlignedBB LETTERSTATION_AABB_S = new AxisAlignedBB(0.1875D, 0,0.1875D, 0.8125D, 0.0625D, 0.9375D);
	public static final AxisAlignedBB LETTERSTATION_AABB_E = new AxisAlignedBB(0.1875D, 0,0.1875D, 0.9375D, 0.0625D, 0.8125D);
	public static final AxisAlignedBB LETTERSTATION_AABB_N = new AxisAlignedBB(0.1875D, 0,0.0625D, 0.8125D, 0.0625D, 0.8125D ); 
	public static final AxisAlignedBB LETTERSTATION_AABB_W = new AxisAlignedBB(0.0625D, 0,0.1875D, 0.8125D, 0.0625D, 0.8125D);
	
	
	public BlockLetterStation() {
		super("letterstation", Material.WOOD);
		this.setCreativeTab(MxBville.SPECIALTAB);
		this.setHardness(1.0F);
        this.setSoundType(SoundType.SNOW);
	}

	@Override
	public boolean onBlockActivated(World worldIn, 
									BlockPos pos, 
									IBlockState state, 
									EntityPlayer playerIn,
									EnumHand hand, 
									EnumFacing side, 
									float hitX, 
									float hitY, 
									float hitZ) 
	{
		if(!worldIn.isRemote){
			// TODO: Gui for writing letters
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		// TODO Auto-generated method stub
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int facingindex = ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
		switch (facingindex) {
		case 0:
			return LETTERSTATION_AABB_S;
		case 1:
			return LETTERSTATION_AABB_W;
		case 2:
			return LETTERSTATION_AABB_N;
		case 3:
			return LETTERSTATION_AABB_E;
		default:
			return LETTERSTATION_AABB_N;
		}
	}
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
}
