package mxbville.common.gui;

import mxbville.client.gui.GuiWalletSmall;
import mxbville.client.gui.itemgui.GuiMail;
import mxbville.common.gui.common.ContainerEmpty;
import mxbville.common.items.coins.ItemWalletSmall;
import mxbville.common.items.inventories.ContainerWalletSmall;
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
					if (heldItemStack != ItemStack.EMPTY && heldItemStack.getItem() instanceof ItemWalletSmall)
			        {
					    IItemHandler walletInventory = heldItemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			            if (walletInventory != null)
			            {
			                return new ContainerWalletSmall(player, player.inventory, walletInventory);
			            }
			        }
					break;
				case GUIIDList.MAIL_REPLY: return new ContainerEmpty();
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
					if (heldItemStack != ItemStack.EMPTY && heldItemStack.getItem() instanceof ItemWalletSmall)
			        {
					    ItemStackHandler walletInventory = (ItemStackHandler) heldItemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				        if (walletInventory != null)
				        {
				        	return new GuiWalletSmall(new ContainerWalletSmall(player, player.inventory, walletInventory));
				        }
			        }
					break;
				case GUIIDList.MAIL_REPLY: return new GuiMail(player);
				default:break;
			}
		}
		return null;
	}
}
