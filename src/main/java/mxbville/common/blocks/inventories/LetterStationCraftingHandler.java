package mxbville.common.blocks.inventories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mcp.MethodsReturnNonnullByDefault;
import mxbville.common.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import scala.collection.parallel.ParIterableLike.Forall;



public class LetterStationCraftingHandler
{
    private final List<LetterRecipe> LetterRecipeList = Lists.newArrayList();
	
	public static final LetterStationCraftingHandler CRAFTING_LETTERS = new LetterStationCraftingHandler();
	
	public static LetterStationCraftingHandler getLetStatCraftHandlrInstance() {
		return CRAFTING_LETTERS;
	}
	
	public LetterStationCraftingHandler() {
		this.setRecipes();
	}
	
	public void setRecipes() {
		// Outputs
		ItemStack commonOutput = new ItemStack(ModItems.LETTER_INVITATION);
		ItemStack rareOutput = new ItemStack(ModItems.LETTER_INVITATION);
		ItemStack specialOutput = new ItemStack(Items.EMERALD);
		// Inputs
		NonNullList<ItemStack> commonInput = NonNullList.create();
		commonInput.add(new ItemStack(Items.FEATHER));
		commonInput.add(new ItemStack(Items.DYE));
		commonInput.add(new ItemStack(Items.PAPER));
		
		NonNullList<ItemStack> rareInput = NonNullList.create();
		rareInput.add(new ItemStack(Items.PAPER));
		rareInput.add(new ItemStack(Items.DIAMOND));
		
		this.addRecipe(new LetterRecipe(commonInput,commonOutput));
		this.addRecipe(new LetterRecipe(rareInput,rareOutput));
	}
	
	/**
     * Adds an IRecipe to the list of crafting recipes.
     */
    public void addRecipe(LetterRecipe recipe) {
        this.LetterRecipeList.add(recipe);
    }

    /**
     * Adds an IRecipe to the list of crafting recipes.
     */
    public void addRecipes(LetterRecipe... recipes) {
        Arrays.stream(recipes).forEach(this::addRecipe);
    }

    /**
     * Removes an IRecipe to the list of crafting recipes.
     */
    public void removeRecipe(LetterRecipe recipe) {
        this.LetterRecipeList.remove(recipe);
    }

    public ItemStack findMatchingResult(InventoryCrafting craftColumn, World worldIn) {
    	
    	for(LetterRecipe letterRecipe : LetterRecipeList)
    	{
    		if(letterRecipe.matches(craftColumn, worldIn))
    		{
    			return letterRecipe.getCraftingResult(craftColumn);
    		}
    	}
    	return ItemStack.EMPTY;
    }
    
    @Nullable
    public LetterRecipe findMatchingRecipe(InventoryCrafting craftColumn, World worldIn) {
    	for(LetterRecipe letterRecipe : LetterRecipeList)
    	{
    		if(letterRecipe.matches(craftColumn, worldIn))
    		{
    			return letterRecipe;
    		}
    	}
    	return null;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftColumn, World worldIn) {
        for (LetterRecipe letterRecipe : LetterRecipeList) {
            if (letterRecipe.matches(craftColumn, worldIn)) {
                return letterRecipe.getRemainingItems(craftColumn);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftColumn.getSizeInventory(), ItemStack.EMPTY);

        IntStream.range(0, nonnulllist.size()).forEach(i -> nonnulllist.set(i, craftColumn.getStackInSlot(i)));

        return nonnulllist;
    }

    public List<LetterRecipe> getRecipeList() {
        return this.LetterRecipeList;
    }
}