package mxbville.common.blocks.inventories;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mxbville.common.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

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
		ItemStack letterNormal 		= new ItemStack(ModItems.LETTER_INVITATION_NORMAL);
		ItemStack letterBait 		= new ItemStack(ModItems.LETTER_INVITATION_BAIT);
		ItemStack letterApproved 	= new ItemStack(ModItems.LETTER_INVITATION_APPROVED);

		// Inputs
		NonNullList<ItemStack> ingridientsNormal = NonNullList.create();
		ingridientsNormal.add(new ItemStack(Items.FEATHER));
		ingridientsNormal.add(new ItemStack(Items.DYE));
		ingridientsNormal.add(new ItemStack(Items.PAPER));
		
		NonNullList<ItemStack> ingridientsBait = NonNullList.create();
		ingridientsBait.add(new ItemStack(ModItems.LETTER_INVITATION_NORMAL));
		ingridientsBait.add(new ItemStack(ModItems.COIN_SILVER));
		
		NonNullList<ItemStack> ingridientsBait_2 = NonNullList.create();
		ingridientsBait_2.add(new ItemStack(ModItems.LETTER_INVITATION_NORMAL));
		ingridientsBait_2.add(new ItemStack(Items.GOLD_NUGGET));
		
		NonNullList<ItemStack> ingridientsApproved = NonNullList.create();
		ingridientsApproved.add(new ItemStack(ModItems.LETTER_INVITATION_NORMAL));
		ingridientsApproved.add(new ItemStack(ModItems.LETTER_APPROVEMEND_STAMP));
		
		// Adding the Recipes to the crafting list
		this.addRecipe(new LetterRecipe(ingridientsNormal,letterNormal));
		this.addRecipe(new LetterRecipe(ingridientsBait,letterBait));
		this.addRecipe(new LetterRecipe(ingridientsBait_2,letterBait));
		this.addRecipe(new LetterRecipe(ingridientsApproved,letterApproved));
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