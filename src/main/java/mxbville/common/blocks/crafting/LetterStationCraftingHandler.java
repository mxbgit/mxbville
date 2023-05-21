package mxbville.common.blocks.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.Instance;

public class LetterStationCraftingHandler 
{
	@Instance
	public static final LetterStationCraftingHandler CRAFTING_LETTERS = new LetterStationCraftingHandler();
	
	public static LetterStationCraftingHandler getInstance() {
		return CRAFTING_LETTERS;
	}
	
	public IRecipe getResult(InventoryCrafting inventory, World parWorld) {
		return null;
		
	}
}
