package mxbville.common.blocks.decorative;

import mxbville.common.blocks.misc.BlockFacing;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStep extends BlockFacing {
	
	protected static final AxisAlignedBB POST_SHAPE = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.25D, 0.625D);
	protected static final AxisAlignedBB JOIST_SHAPE_NS	= new AxisAlignedBB(0, 0.25D, 0.25D, 1D, 0.4375D, 0.75D);
	protected static final AxisAlignedBB JOIST_SHAPE_EW = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.4375D, 1D);
	protected static final AxisAlignedBB SEAT_SHAPE_NS = POST_SHAPE.union(JOIST_SHAPE_NS);
	protected static final AxisAlignedBB SEAT_SHAPE_EW = POST_SHAPE.union(JOIST_SHAPE_EW);
	
	public static final PropertyBool ATTACHED = PropertyBool.create("attached");

	public BlockStep(String name) {
		super(name, Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ATTACHED, Boolean.valueOf(false)));
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		EnumFacing facing = state.getValue(FACING);
	    
	    boolean attached = state.getValue(ATTACHED);
        switch (facing){
            case NORTH:case SOUTH:
               return (attached) ? SEAT_SHAPE_NS : JOIST_SHAPE_NS;
            case EAST:case WEST:
               return (attached) ? SEAT_SHAPE_EW : JOIST_SHAPE_EW;
        }
        return SEAT_SHAPE_NS;
    }
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
	{
		EnumFacing  placementDir;
		
        if(facing == EnumFacing.DOWN || facing == EnumFacing.UP){
            placementDir = placer.getHorizontalFacing().getOpposite();
        }else {
            placementDir = facing.rotateY();
        }
		
		return this.getDefaultState().withProperty(FACING, placementDir).withProperty(
				ATTACHED, isInAttachablePos(world, pos, facing));
	}
	
	protected BlockStateContainer createBlockState() 
	{
	    return new BlockStateContainer(this, new IProperty[] {FACING,ATTACHED});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState()
				.withProperty(FACING, getFacing(meta))
				.withProperty(ATTACHED, Boolean.valueOf((meta & 4) != 0));
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
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
        int i = 0;
        i = i | getMetaForFacing((EnumFacing)state.getValue(FACING));

        if (((Boolean)state.getValue(ATTACHED)).booleanValue())
        {
            i |= 4;
        }
        
        return i;
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
    
    /**
     * 
     * @param worldIn Interface to the world
     * @param pos The position this block is being placed at
     * @param face The side this block is being placed on
     * @return
     */
    private boolean isInAttachablePos(IBlockAccess worldIn, BlockPos pos, EnumFacing face)
    {
        if (worldIn.getBlockState(pos.down()).getBlock() instanceof BlockFence) {
        	return true;
        }
    	return worldIn.isSideSolid(pos.down(), face, true);
    }
}
