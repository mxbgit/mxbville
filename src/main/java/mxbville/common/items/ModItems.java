package mxbville.common.items;

import mxbville.common.items.coins.ItemCoin;
import mxbville.common.items.coins.ItemWalletSmall;
import mxbville.common.items.food.ItemCornSeeds;
import mxbville.common.items.food.ItemHorseAppleSlice;
import mxbville.common.items.food.ItemHorseCorn;
import mxbville.common.items.food.ItemHorseSugar;
import mxbville.util.MxRef;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MxRef.MOD_ID)
public class ModItems {

	public static final Item APPLE_SLICE = null;
	
	public static final Item COIN_BRONZE = null;
	public static final Item COIN_SILVER = null;
	public static final Item COIN_GOLD	 = null;
	
	public static final Item CORN_SEED = null; 
	public static final Item CORN_HORSE = null; 
	
	public static final Item SUGAR_LUMPS = null; 
	public static final Item WALLET_SMALL = null;
	
	
	@EventBusSubscriber(modid = MxRef.MOD_ID)
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void onItemRegister(RegistryEvent.Register<Item> event)
		{
			// items
			final Item[] items = {
				new ItemHorseAppleSlice(),
				new ItemCoin("coin_bronze"),
				new ItemCoin("coin_silver"),
				new ItemCoin("coin_gold"),
				new ItemCornSeeds("corn_seed"),
				new ItemHorseCorn(),
				new ItemHorseSugar(),
				new ItemWalletSmall("wallet_small")
			};
			
			event.getRegistry().registerAll(items);
		}
	}
}
