package mxbville.common.items.coins;

import java.util.List;

import javax.annotation.Nullable;

import mxbville.MxBville;
import mxbville.common.gui.GUIIDList;
import mxbville.common.items.ItemBase;
import mxbville.common.items.inventories.WalletInventoryProvider;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemWalletSmall extends ItemBase {
	
	// fixed amount of slots
	private int inventorySlots = 10;
	
	public ItemWalletSmall(String name) {
		super(name);
		setMaxStackSize(1);
		this.setCreativeTab(MxBville.ITEMTAB);
	}

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    	return new WalletInventoryProvider(inventorySlots);
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":walletsmall.item.info");
		tooltip.add(info);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			player.openGui(MxBville.instance, GUIIDList.WALLET_SMALL, player.world, 0, 0, 0);
		}
	
		return super.onItemRightClick(world, player, hand);
	}
		
}
