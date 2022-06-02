package mxbville.common.gui;

import mxbville.client.gui.GuiWallet;
import mxbville.common.items.coins.ItemWallet;
import mxbville.common.items.coins.ItemWalletSmall;
import mxbville.common.items.inventories.ContainerWallet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// Villager guis
		if(ID >= 100 && ID <200){
		
		// Item and Block guis
		}else {
			ItemStack heldItemStack = player.inventory.getCurrentItem();
			
			switch(ID) {
				case GUIIDList.WALLET_SMALL: 
				case GUIIDList.WALLET_LARGE: 
					if (heldItemStack != ItemStack.EMPTY && heldItemStack.getItem() instanceof ItemWallet)
					 {
						int rows 	= ((ItemWallet) heldItemStack.getItem()).getInventoryRows();
						int columns = ((ItemWallet) heldItemStack.getItem()).getInventoryCols();

					    IItemHandler walletInventory = heldItemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			            if (walletInventory != null)
			            {
			                return new ContainerWallet(player.inventory, walletInventory, rows, columns);
			            }
			        }
					break;
				default:break;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// Villager guis
		if(ID >= 100 && ID <200){
		
		// Item and Block guis
		}else {
			ItemStack heldItemStack = player.inventory.getCurrentItem();
			
			switch(ID) {
				case GUIIDList.WALLET_SMALL: 
				case GUIIDList.WALLET_LARGE: 
					if (heldItemStack != ItemStack.EMPTY && heldItemStack.getItem() instanceof ItemWallet)
			        {
						int rows 	= ((ItemWallet) heldItemStack.getItem()).getInventoryRows();
						int columns = ((ItemWallet) heldItemStack.getItem()).getInventoryCols();

					    ItemStackHandler walletInventory = (ItemStackHandler) heldItemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				        if (walletInventory != null)
				        {
				        	return new GuiWallet(new ContainerWallet(player.inventory, walletInventory, rows, columns), ID);
				        }
			        }
					break;
				default:break;
			}
		}
		return null;
	}
}
