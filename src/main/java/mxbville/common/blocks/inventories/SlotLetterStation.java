package mxbville.common.blocks.inventories;

import javax.annotation.Nonnull;

import mxbville.common.items.ModItems;
import mxbville.common.items.documents.ItemInvitation;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotLetterStation extends SlotItemHandler{

	public SlotLetterStation(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	 
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (stack == new ItemStack(Items.PAPER)
        	|| stack == new ItemStack(Items.DYE)
        	|| stack == new ItemStack(Items.FEATHER)
            || stack.getItem() instanceof ItemInvitation)
        {
            return true;
        }
        return false;
    }
}
