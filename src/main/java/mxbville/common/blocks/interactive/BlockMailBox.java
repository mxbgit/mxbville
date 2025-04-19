package mxbville.common.blocks.interactive;

import mxbville.MxBville;
import mxbville.common.blocks.misc.BlockFacing;
import mxbville.common.calc.math.MxRand;
import mxbville.common.events.EventMailArrived;
import mxbville.common.items.ModItems;
import mxbville.common.items.documents.ItemInvitation;
import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMailBox extends BlockFacing {

	public static final AxisAlignedBB MAILBOX_AABB_W_E = new AxisAlignedBB(0, 0, 0.1875D, 1, 0.5625D, 0.8125D);
	public static final AxisAlignedBB MAILBOX_AABB_N_S = new AxisAlignedBB(0.1875D, 0, 0,  0.8125D, 0.5625D, 1);
	
	public BlockMailBox() {
		super("mailbox" ,Material.WOOD);
		this.setHardness(2.5F);
		this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(MxBville.SPECIALTAB);	
	}
	
	/**
	 * Covers the following three cases: 
	 * 1. Checks the player hand for a valid letter item to send.
	 *	  If the held item is valid, tries to send a letter.
	 * 2. If no item or no valid item is held, checks for the arrival of a response letter.
	 *    Triggers MailArrived Event if possible. 
	 * 3. If no letter has been sent, outputs a text message to the current player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if (!worldIn.isRemote) {
			if (this.isHeldItemValid(playerIn, hand)) {
				this.sendLetterIfPossible(playerIn, hand);
			} else {
				if (ExtendedPlayerProperties.get(playerIn).hasNewVillagerMail()) {
					EventMailArrived event = new EventMailArrived(worldIn, pos, playerIn);
					event.resolve();
				} else {
					playerIn.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.nomail"));
				}
			}
		}

		return true;
	}

	/**
	 * Returns true if the itemstack in the mainhand of the given player is in the
	 * local list of valid items. Returns false otherwise. An Empty Hand returns
	 * always false.
	 * 
	 * @param playerIn Player reference to check the held item.
	 * @param hand     The hand, that will be checked.
	 * @return true if item in hand is in local list, false otherwise
	 */
	private boolean isHeldItemValid(EntityPlayer playerIn, EnumHand hand) {
		if (playerIn.getHeldItem(hand) != null) {
			Item heldItem = playerIn.getHeldItem(hand).getItem();
			if (heldItem instanceof ItemInvitation && (
					heldItem == ModItems.LETTER_INVITATION_APPROVED
					|| heldItem == ModItems.LETTER_INVITATION_BAIT 
					|| heldItem == ModItems.LETTER_INVITATION_JOB
					|| heldItem == ModItems.LETTER_INVITATION_NORMAL
					|| heldItem == ModItems.LETTER_INVITATION_OFFICIAL)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Tries to consume the player held item and send an invitation. Fails with a
	 * text prompt if an invitation has been send recently.
	 * 
	 * @param playerIn
	 * @param hand
	 */
	private void sendLetterIfPossible(EntityPlayer playerIn, EnumHand hand) {
		if (ExtendedPlayerProperties.get(playerIn).hasSentInvitation()) {
			// invitation has been sent recently.
			playerIn.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.invitefailed"));
		} else {
			ItemStack currentStack = playerIn.getHeldItem(hand);
			ItemInvitation currentInvitation = (ItemInvitation) currentStack.getItem();
			if (!playerIn.capabilities.isCreativeMode) {
				// remove Item from player hand
				currentStack.shrink(1);
			}
			// Send Invitation
			ExtendedPlayerProperties.get(playerIn).sendNewMail(currentInvitation.getType().name());
			playerIn.sendMessage(
					new TextComponentTranslation(MxRef.MOD_ID + ":message.mail.mailsend." + MxRand.get().nextInt(3)));
		}

	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
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
