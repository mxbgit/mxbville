package mxbville.common.blocks.inventories;

import mxbville.common.blocks.ModBlocks;
import mxbville.common.blocks.crafting.LetterStationCraftingHandler;
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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.IItemHandler;

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
		this.addSlotToContainer(new Slot(this.craftColumn, 1 , 30 + 1 * 18 , 25 )); // 1. Slot, Index is 1, then X coord then Y coord
		this.addSlotToContainer(new Slot(this.craftColumn, 2 , 30 + 2 * 18, 25 )); // 2. Slot, Index is 2, then X coord then Y coord
		this.addSlotToContainer(new Slot(this.craftColumn, 3 , 30 + 3 * 18 , 25 )); // 3. Slot, Index is 3, then X coord then Y coord
		// output Slots
		this.addSlotToContainer(new SlotCrafting(playerRef, this.craftColumn, this.craftResult, 0, 48, 60)); // Index 0, X coord 48 Y Coord 60
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
                /**
                 * 	legacy example:
                 *  this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
                 *  
                 *  Standard craftingtable inventory starts indexing the player inventory slots with a 9 ([..] + 9),
                 *  but in this case, all the crafting only takes up 4 slots. So I try to start the player inventory with a 5 ([..] + 5)    
                 *  
                 */ 
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 5, x, y));
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
		//
		//this.craftResult.setInventorySlotContents(0, LetterStationCraftingHandler.getInstance().findMatchingRecipe(this.craftColumn, this.worldRef));
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
