package mxbville.common.blocks.decorative;

import mxbville.MxBville;
import mxbville.common.blocks.misc.BlockFacing;
import mxbville.common.calc.generators.ReplyMailGenerator;
import mxbville.common.items.ModItems;
import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
		if(!worldIn.isRemote) {
			// Invitation in Mainhand
			if (playerIn.getHeldItem(hand) != null && playerIn.getHeldItem(hand).getItem() == ModItems.LETTER_INVITATION)
			{
				// Was an Invitation Send already? 
				if(ExtendedPlayerProperties.get(playerIn).hasSentInvitation){
					playerIn.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.invitefailed"));	
				}else {	
					//If not creativemode
					if (!playerIn.capabilities.isCreativeMode){
						// remove Item from Hand
						ItemStack stack= playerIn.getHeldItem(hand);
						stack.shrink(1);
					}
					// Send Invitation
					ExtendedPlayerProperties.get(playerIn).sendNewVillagerInvitation();			
					playerIn.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.mailsend"));
				}
			// No Invitation in Mainhand	
			} else {
				// If a new Mail has arrived
				if(ExtendedPlayerProperties.get(playerIn).hasNewVillagerMail()){
					// reset the "hasNewVillagerMail" property
					ExtendedPlayerProperties.get(playerIn).receiveNewVillagerMail();
					// spawn item
					spawnReplyLetter(worldIn, pos, playerIn);
				} else {	
					// If no Invitation was send
					playerIn.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.nomail"));
				}
			}
		}
		return true;
	}

	private void spawnReplyLetter(World worldIn, BlockPos pos, EntityPlayer playerIn) 
	{
		// generate mail
		ItemStack mail = ReplyMailGenerator.generate(); 
		// drop mail item
		double x = (double)pos.getX() + 0.5D;
		double y = (double)pos.getY() + 0.5D;
		double z = (double)pos.getZ() + 0.5D;
		EntityItem entityitem = new EntityItem(worldIn, x, y, z, mail);
    
		double d1 = playerIn.posX - x;
		double d3 = playerIn.posY - y;
		double d5 = playerIn.posZ - z;
		double d7 = (double)MathHelper.sqrt(d1 * d1 + d3 * d3 + d5 * d5);
		double d9 = 0.08D;
		entityitem.motionX = d1 * d9;
		entityitem.motionY = d3 * d9 + (double)MathHelper.sqrt(d7) * 0.05D;
		entityitem.motionZ = d5 * d9;
    
		entityitem.setDefaultPickupDelay();
		worldIn.spawnEntity(entityitem);
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
