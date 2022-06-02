package mxbville.common.items;

import mxbville.common.items.coins.ItemCoin;
import mxbville.common.items.coins.ItemWalletLarge;
import mxbville.common.items.coins.ItemWalletSmall;
import mxbville.util.MxRef;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MxRef.MOD_ID)
public class ModItems {

	public static final Item COIN_BRONZE = null;
	public static final Item COIN_SILVER = null;
	public static final Item COIN_GOLD	 = null;
	
	public static final Item WALLET_SMALL = null;
	public static final Item WALLET_LARGE = null;

	
	@EventBusSubscriber(modid = MxRef.MOD_ID)
	public static class RegistrationHandler
	{
		@SubscribeEvent
		public static void onItemRegister(RegistryEvent.Register<Item> event)
		{
			// items
						final Item[] items = {
							new ItemCoin("coin_bronze"),
							new ItemCoin("coin_silver"),
							new ItemCoin("coin_gold"),
							new ItemWalletSmall("wallet_small"),
							new ItemWalletLarge("wallet_large")
						};
						
						event.getRegistry().registerAll(items);
		}
	}
}
