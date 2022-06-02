package mxbville.common.items.inventories;

import javax.annotation.Nonnull;

import mxbville.common.items.coins.ItemCoin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotWallet extends SlotItemHandler{

	public SlotWallet(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	 // only allow coins
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        if (stack.getItem() instanceof ItemCoin)
        {
            return true;
        }
        return false;
    }

}
