package mxbville.common.items;

import mxbville.common.items.coins.ItemCoin;
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
						};
						
						event.getRegistry().registerAll(items);
		}
	}
}
