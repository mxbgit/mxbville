package mxbville.common.blocks.decorative;

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

public class BlockAngledSupport extends BlockFacing {

	private static final double d0 = 0.1875D;
    private static final double d1 = 0.8125D;
    private static final double d2 = 0.25D;
    private static final double d3 = 0.75D;
    public static final AxisAlignedBB TOP = new AxisAlignedBB(0, d1, 0, 1D, 1D, 1D);
    public static final AxisAlignedBB NORTH_PART = new AxisAlignedBB(d2, 0, d1, d3, d1, 1D);
    public static final AxisAlignedBB SOUTH_PART = new AxisAlignedBB(d2, 0, 0, d3, d1, d0);
    public static final AxisAlignedBB EAST_PART = new AxisAlignedBB(0, 0, d2, d0, d1, d3);
    public static final AxisAlignedBB WEST_PART = new AxisAlignedBB(d1, 0, d2, 1D, d1, d3);
    public static final AxisAlignedBB NORTH_SHAPE = TOP.union(NORTH_PART);
    public static final AxisAlignedBB SOUTH_SHAPE = TOP.union(SOUTH_PART);
    public static final AxisAlignedBB EAST_SHAPE = TOP.union(EAST_PART);
    public static final AxisAlignedBB WEST_SHAPE = TOP.union(WEST_PART);
    
	public BlockAngledSupport(String name) {
		super(name, Material.WOOD);
		this.setHardness(2.5F);
        this.setSoundType(SoundType.WOOD);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
            	return NORTH_SHAPE;
        }
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

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    protected static EnumFacing getFacing(int meta)
    {
        switch (meta & 3)
        {
            case 0:
                return EnumFacing.NORTH;
            case 1:
                return EnumFacing.SOUTH;
            case 2:
                return EnumFacing.WEST;
            case 3:
            default:
                return EnumFacing.EAST;
        }
    }
    
    protected static int getMetaForFacing(EnumFacing facing)
    {
        switch (facing)
        {
            case NORTH:
                return 0;
            case SOUTH:
                return 1;
            case WEST:
                return 2;
            case EAST:
            default:
                return 3;
        }
    } 
    
    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return true;
    }
    
    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    	return 20;
    }
    
}
