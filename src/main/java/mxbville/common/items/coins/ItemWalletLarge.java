package mxbville.common.items.coins;

import mxbville.MxBville;
import mxbville.common.gui.GUIIDList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemWalletLarge extends ItemWallet {

    private static final int INVENTORY_ROWS 	= 3;
    private static final int INVENTORY_COLUMNS 	= 7;
   
	public ItemWalletLarge(String name) {
		super(name, INVENTORY_ROWS, INVENTORY_COLUMNS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			player.openGui(MxBville.instance, GUIIDList.WALLET_LARGE, player.world, 0, 0, 0);
		}
	
		return super.onItemRightClick(world, player, hand);
	}

}
