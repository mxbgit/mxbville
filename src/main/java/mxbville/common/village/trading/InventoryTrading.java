package mxbville.common.village.trading;

import mxbville.util.MxRef;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryTrading implements IInventory {

	private ItemStack[] inventoryItems = new ItemStack[5]; // 4 inputs and 1 output
	
	private ITrading trader;
	private EntityPlayer player;
	
	private int currentRecipeIndex;
	private TradingRecipe currentRecipe;
	
	public InventoryTrading(EntityPlayer player, ITrading trader){
		for (int i = 0; i < 5; i++) {
			inventoryItems[i] = ItemStack.EMPTY;
		}
		this.trader = trader;
		this.player = player;
	}
	
	@Override
	public String getName() {
		return MxRef.MOD_ID + ".trading";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return this.inventoryItems.length;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= this.getSizeInventory() ?ItemStack.EMPTY : this.inventoryItems[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (!this.inventoryItems[index].isEmpty())
        {
            if (index == 4)
            {
                ItemStack take = this.inventoryItems[index];
                this.inventoryItems[index] = ItemStack.EMPTY;
                return take;
            }
            else if (this.inventoryItems[index].getCount() <= count)
            {
                ItemStack take = this.inventoryItems[index];
                this.inventoryItems[index] = ItemStack.EMPTY;

                if (this.inventoryResetNeededOnSlotChange(index))
                {
                    this.resetRecipeAndSlots();
                }
                return take;
            }
            else
            {
                ItemStack take = this.inventoryItems[index].splitStack(count);

                if (this.inventoryItems[index].getCount() == 0)
                {
                    this.inventoryItems[index] = ItemStack.EMPTY;
                }

                if (this.inventoryResetNeededOnSlotChange(index))
                {
                    this.resetRecipeAndSlots();
                }

                return take;
            }
        }
        else
        {
            return ItemStack.EMPTY;
        }
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if(!this.inventoryItems[index].isEmpty()){
			ItemStack removed = this.inventoryItems[index];
			this.inventoryItems[index] = ItemStack.EMPTY;
			return removed;
		}
		else{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventoryItems[index] = stack;
		
		if (stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (this.inventoryResetNeededOnSlotChange(index))
        {
            this.resetRecipeAndSlots();
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		this.resetRecipeAndSlots();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventoryItems.length; ++i) {
            this.inventoryItems[i] = ItemStack.EMPTY;
		}
	}
	
	private boolean inventoryResetNeededOnSlotChange(int index){
        return index >=0 && index < 4;
    }

	public void resetRecipeAndSlots(){
        this.currentRecipe = this.trader.getTradingRecipeList().get(this.currentRecipeIndex);
        
        ItemStack output = ItemStack.EMPTY;
        if(this.currentRecipe.match(this.inventoryItems)){
        	output = this.currentRecipe.getItemOutput().copy();
        }
        // Set OutputOutput
        this.setInventorySlotContents(4, output);
	}
	
	public boolean tradeCurrentRecipe(){
		if(this.currentRecipe != null){
			return this.currentRecipe.trade(this.inventoryItems);
		}
		return false;
	}
	
	public void setCurrentRecipeIndex(int currentRecipeIndexIn)
    {
        this.currentRecipeIndex = currentRecipeIndexIn;
        this.resetRecipeAndSlots();
    }
	
	
}
