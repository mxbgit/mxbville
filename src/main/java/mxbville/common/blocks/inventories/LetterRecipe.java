package mxbville.common.blocks.inventories;

import static net.minecraftforge.common.ForgeHooks.getContainerItem;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class LetterRecipe {
	
	 /**
     * Is an array of ItemStack that composes the recipe.
     */
    public final NonNullList<ItemStack> inputList;
    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    private final ItemStack recipeOutput;
    
	public LetterRecipe(NonNullList<ItemStack> ingredientsIn, ItemStack output) {
		this.inputList = ingredientsIn;
		this.recipeOutput = output;
	}
	
    /**
     * Checks if a recipe matches the current crafting inventory.
     * Creates a temporary list of crafting ingridient itemstacks.
     * Checks the temporary list against the current crafting inventory
     * and removes the matching itemstacks from the temporary list.
     * <p>
     * If all itemstacks are removed after a set amount 
     * of steps (the inventory slot count) a match is found. 
     * 
     * @return true if all Items in this recipes ingriedientlist are present in the current crafting inventory
     */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		List<ItemStack> list = Lists.newArrayList(inputList);
	
		// The crafting grid of the letterstation is 1 x 3 (1 Row and 3 Columns) 
		for (int recipeSlotIterator = 0; recipeSlotIterator < 3; recipeSlotIterator++)
		{
			ItemStack itemstackCurrent = inv.getStackInRowAndColumn(0, recipeSlotIterator);
			
			if (!itemstackCurrent.isEmpty()) {
				boolean returnFlag = false;
				
                for (ItemStack expectedStack : list) {
                    if (itemstackCurrent.getItem() == expectedStack.getItem()
                    		&& (expectedStack.getMetadata() == OreDictionary.WILDCARD_VALUE 
                    		|| itemstackCurrent.getMetadata() == expectedStack.getMetadata()))
                    {
                    	returnFlag = true;
                        list.remove(expectedStack);
                        break;
                    }
                }
                
                // used to leave this function early
                if (!returnFlag) return false;
			}
		}
		
		// if the list is empty, than a match is found
		return list.isEmpty();
	}

	@Nonnull
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
		return this.recipeOutput.copy();
	}

	@Nonnull
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}
	
    public NonNullList<ItemStack> getInput() {
        return this.inputList;
    }
    
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> returnStack = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		IntStream.range(0, returnStack.size()).forEach(i -> returnStack.set(i, getContainerItem(inv.getStackInSlot(i))));
		return returnStack; 
	}
}
