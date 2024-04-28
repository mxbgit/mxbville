package mxbville.common.blocks.decorative;

import mxbville.MxBville;
import mxbville.util.IHasModel;
import mxbville.util.ModelRegister;
import mxbville.util.MxRef;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBarsPanel extends BlockTrapDoor implements IHasModel {

	 private static final double d0 = 3D;
	 private static final double d1 = 16D - d0;
	 protected static final AxisAlignedBB EAST_OPEN_AABB 	= new AxisAlignedBB(0.0D, 0.0D, 0.0D, d0, 16.0D, 16.0D);
	 protected static final AxisAlignedBB WEST_OPEN_AABB 	= new AxisAlignedBB(d1, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	 protected static final AxisAlignedBB SOUTH_OPEN_AABB 	= new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, d0);
	 protected static final AxisAlignedBB NORTH_OPEN_AABB 	= new AxisAlignedBB(0.0D, 0.0D, d1, 16.0D, 16.0D, 16.0D);
	 protected static final AxisAlignedBB BOTTOM_AABB 		= new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16.0D, d0, 16.0D);
	 protected static final AxisAlignedBB TOP_AABB 			= new AxisAlignedBB(0.0D, d1, 0.0D, 16.0D, 16.0D, 16.0D);
	 
	
	public BlockBarsPanel()
	{
		super(Material.IRON);
		this.setUnlocalizedName(MxRef.MOD_ID + ":barspanel");
		this.setRegistryName("barspanel");
		this.setCreativeTab(MxBville.SPECIALTAB);
		this.setHardness(5.0F);
		this.setHarvestLevel("pickaxe", 2);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.METAL);
		
		ModelRegister.LIST.add(this);	
	}
	
	@Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
  {
      state = state.cycleProperty(OPEN);
      worldIn.setBlockState(pos, state, 2);
      this.playSound(playerIn, worldIn, pos, ((Boolean)state.getValue(OPEN)).booleanValue());
      return true;
  }
	

	@Override
	public void registerModels() {
		MxBville.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
				
	}


}
