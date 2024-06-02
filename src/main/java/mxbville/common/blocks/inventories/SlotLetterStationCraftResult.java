package mxbville.common.blocks.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;

public class SlotLetterStationCraftResult extends SlotCrafting{

	private final InventoryCrafting 			craftingColumnRef;
	private final LetterStationCraftingHandler 	craftinHandler;
	
	public SlotLetterStationCraftResult(EntityPlayer player, InventoryCrafting craftingInventory,
			IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	{
		super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
		this.craftinHandler = LetterStationCraftingHandler.getLetStatCraftHandlrInstance();
		this.craftingColumnRef = craftingInventory;
	}
	

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		this.onCrafting(stack);
		ForgeHooks.setCraftingPlayer(thePlayer);
		NonNullList<ItemStack> nonNullList = craftinHandler.getRemainingItems(craftingColumnRef, thePlayer.world);
		ForgeHooks.setCraftingPlayer(null);
		this.replaceItemsInCraftinInventory(thePlayer, craftingColumnRef, nonNullList);
		return stack;
	}
	
	
	private void replaceItemsInCraftinInventory(EntityPlayer player, InventoryCrafting craftColumn, NonNullList<ItemStack> input) {
		for (int i = 0; i < input.size(); ++i) {
            ItemStack slotStack = craftColumn.getStackInSlot(i);
            ItemStack inputStack = input.get(i);

            if (!slotStack.isEmpty()) {
            	craftColumn.decrStackSize(i, 1);
                slotStack = craftColumn.getStackInSlot(i);
            }

            if (!inputStack.isEmpty()) {
                if (slotStack.isEmpty()) {
                	craftColumn.setInventorySlotContents(i, inputStack);
                } else if (ItemStack.areItemsEqual(slotStack, inputStack) && ItemStack.areItemStackTagsEqual(slotStack, inputStack)) {
                    inputStack.grow(slotStack.getCount());
                    craftColumn.setInventorySlotContents(i, inputStack);
                } else if (!player.inventory.addItemStackToInventory(inputStack)) {
                    player.dropItem(inputStack, false);
                }
            }
        }
	}
}
