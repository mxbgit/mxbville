package mxbville.common.items.inventories;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;

public class ContainerWalletSmall extends Container {

	// fixed row and column count
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 5;
    
    private final static int X_START = 30;
    private final static int Y_START = 10;
    private final static int SLOTSIZE_OFFSET = 18;
    
	public ContainerWalletSmall(EntityPlayer par1Player, InventoryPlayer playerInventory, IItemHandler itemInventory)
	{
		
		addStorageSlots(itemInventory);
        addPlayerSlots(playerInventory);
    }
	
	private void addPlayerSlots(IInventory playerInventory) 
    {
		// Slots for the main inventory 
        for (int row = 0; row < 3; ++row) 
        {
            for (int col = 0; col < 9; ++col) 
            {
                int x = 8 + col * 18;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar 0-9
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 142;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }
	
	// Slots for the container inventory
    private void addStorageSlots(IItemHandler inventory) 
    {
    	int slotIndex = 0;
        
        for (int i = 0; i < NUM_ROWS; i++)
        {
            for (int j = 0; j < NUM_COLS; j++)
            {
                int x = X_START + (SLOTSIZE_OFFSET * j);
                int y = Y_START + (SLOTSIZE_OFFSET * i);
                this.addSlotToContainer(new SlotWallet(inventory, slotIndex, x, y));
                slotIndex++;
            }
        }
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        int inventoryMaxSize = NUM_ROWS * NUM_COLS;
        

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (index < inventoryMaxSize) {
                if (!this.mergeItemStack(itemstack1, inventoryMaxSize, this.inventorySlots.size(), true)) {
                	return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, inventoryMaxSize, false)) {
            	return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        
        return itemstack;
    }
    
    
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slot, int button, ClickType flag, EntityPlayer player) {
		// this will prevent the player from interacting with the item that opened the inventory:
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem(EnumHand.MAIN_HAND)) {
			return ItemStack.EMPTY;
		}
		return super.slotClick(slot, button, flag, player);
	}
}
