package mxbville.common.blocks.food;

import mxbville.MxBville;
import mxbville.util.IHasModel;
import mxbville.util.ModelRegister;
import mxbville.util.MxRef;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMxBCake extends BlockCake implements IHasModel
{
	private int 	food_level;
	private float 	saturation_level;
	private Potion  positive_effect;
	
	public BlockMxBCake(String namesufix) {
		super();
		this.setUnlocalizedName(MxRef.MOD_ID + ":cake_" + namesufix);
		this.setRegistryName("cake_" + namesufix);
		this.setHardness(0.5F);
		this.setCreativeTab(MxBville.SPECIALTAB);
		
		this.food_level = 2;
		this.saturation_level = 0.1F;
		this.positive_effect = null;
		
		ModelRegister.LIST.add(this);	
	}
	
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
        {
            return this.eatPieceOfCake(worldIn, pos, state, playerIn);
        }
        else
        {
            ItemStack itemstack = playerIn.getHeldItem(hand);
            return this.eatPieceOfCake(worldIn, pos, state, playerIn) || itemstack.isEmpty();
        }
	}
	
    private boolean eatPieceOfCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if (!player.canEat(false))
        {
            return false;
        }
        else
        {
            player.addStat(StatList.CAKE_SLICES_EATEN);
            player.getFoodStats().addStats(this.food_level, this.saturation_level);
            if(this.positive_effect != null) {
            	player.addPotionEffect(new PotionEffect(this.positive_effect, 10*20, 2, false, true));
            }
            int i = ((Integer)state.getValue(BITES)).intValue();

            if (i < 6)
            {
                worldIn.setBlockState(pos, state.withProperty(BITES, Integer.valueOf(i + 1)), 3);
            }
            else
            {
                worldIn.setBlockToAir(pos);
            }

            return true;
        }
    }
	
	public BlockMxBCake setSaturationLevel(float saturation) {
		this.saturation_level = saturation;
		return this;
	}
	
	public BlockMxBCake setFoodLevel(int foodlevel) {
		this.food_level = foodlevel;
		return this;
	}
	
	public BlockMxBCake setPositiveEffect(Potion effect) {
		this.positive_effect = effect;
		return this;
	}
	
	@Override
	public void registerModels() {
		MxBville.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0);
		
	}

}
