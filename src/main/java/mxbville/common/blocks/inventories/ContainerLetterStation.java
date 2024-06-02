package mxbville.common.blocks.inventories;

import javax.annotation.Nullable;

import mxbville.common.blocks.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
	Crafting Matrix:_______________
	|  __                          |
	| |  | Slot 0                  |
	|  __                          |
	| |  | Slot 1                  |
	|  __                          |
	| |  | Slot 2__                |  
	|           |  | Output Slot 3 |
	|______________________________| 
*/
public class ContainerLetterStation extends Container {
	
	/** Crafting Inventory Slots */
	public InventoryCrafting craftColumn 	= new InventoryCrafting(this, 1, 3);
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	
	private final World 		worldRef;
	private final BlockPos 		blockPosRef;
	private final EntityPlayer 	playerRef;
	
	public ContainerLetterStation(InventoryPlayer playerInventory, World parWorld, BlockPos blockPos)
	{
		this.worldRef 		= parWorld;
		this.blockPosRef 	= blockPos;
		this.playerRef 		= playerInventory.player;
		
		this.addGuiSlots();
        this.addPlayerSlots(playerInventory);
	}

	private void addGuiSlots()
	{
		// Input  Slots
		this.addSlotToContainer(new Slot(this.craftColumn, 0 , 35, 8 + 0 * 23)); 
		this.addSlotToContainer(new Slot(this.craftColumn, 1 , 35, 8 + 1 * 23)); 
		this.addSlotToContainer(new Slot(this.craftColumn, 2 , 35, 8 + 2 * 23)); 
		// output Slots
		this.addSlotToContainer(new SlotLetterStationCraftResult(playerRef, this.craftColumn, this.craftResult, 3, 87, 56)); 
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
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		if(!this.worldRef.isRemote)
		{
			this.setCraftResult(this.findCraftResult());
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		if (this.worldRef.getBlockState(this.blockPosRef).getBlock() != ModBlocks.LETTERSTATION)
		{
			return false;
		}
		else
		{
			// only interact within a reasonable distance 
			return playerIn.getDistance(
						(double) this.blockPosRef.getX() + 0.5D,
						(double) this.blockPosRef.getY() + 0.5D,
						(double) this.blockPosRef.getZ() + 0.5D
					) <= 64.0D;
		}
	}
	
	/**
	 * Sets the given itemstack into the craft result slot of this Container.
	 * Synchronizes the Client and the Server to render the itemstack within the gui. 
	 * 
	 * @param stack The Itemstack that will be set into the craft result slot. Can be an Empty itemstack 
	 */
	private void setCraftResult(ItemStack stack) {
		EntityPlayerMP 	entityplayermp = (EntityPlayerMP)this.playerRef;
        this.craftResult.setInventorySlotContents(3, stack);
        entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 3, stack));
	}

	/**
	 * Returns an itemstack. Invokes a craftingHandler to find a matching recipe.
	 * In case of no matching recipe, an empty itemstack is returned.
	 *  
	 * @return the result itemstack from a matching recipe or an empty itemstack
	 */
	private ItemStack findCraftResult() {
         LetterRecipe letterRecipe = LetterStationCraftingHandler.getLetStatCraftHandlrInstance().findMatchingRecipe(this.craftColumn, this.worldRef);
         if(letterRecipe != null)
	     {
	       	 return letterRecipe.getCraftingResult(craftColumn);
	     }else {
	      	 return ItemStack.EMPTY;
	     }
	}
	
	@Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
    {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.craftColumn.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1, this.craftColumn.getSizeInventory(), this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, this.craftColumn.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
    }
	
    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
     * is null for the initial slot that was double-clicked.	
     */
	@Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.worldRef.isRemote)
        {
            this.clearContainer(playerIn, this.worldRef, this.craftColumn);
        }
    }
}
