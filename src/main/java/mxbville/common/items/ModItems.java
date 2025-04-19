package mxbville.common.items;

import mxbville.common.items.coins.ItemCoin;
import mxbville.common.items.coins.ItemWalletLarge;
import mxbville.common.items.coins.ItemWalletSmall;
import mxbville.common.items.documents.InvitationType;
import mxbville.common.items.documents.ItemApprovementStamp;
import mxbville.common.items.documents.ItemInvitation;
import mxbville.common.items.documents.ItemReplyMail;
import mxbville.common.items.documents.ItemResetScroll;
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

	public static final ItemCoin COIN_BRONZE = null;
	public static final ItemCoin COIN_SILVER = null;
	public static final ItemCoin COIN_GOLD = null;

	public static final Item CORN_SEED = null;
	public static final Item CORN_HORSE = null;

	public static final ItemApprovementStamp LETTER_APPROVEMENT_STAMP = null;

	public static final ItemInvitation LETTER_INVITATION_APPROVED = null;
	public static final ItemInvitation LETTER_INVITATION_BAIT = null;
	public static final ItemInvitation LETTER_INVITATION_JOB = null;
	public static final ItemInvitation LETTER_INVITATION_NORMAL = null;
	public static final ItemInvitation LETTER_INVITATION_OFFICIAL = null;

	public static final ItemReplyMail LETTER_REPLY = null;

	public static final ItemResetScroll RESET_SCROLL = null;
	public static final Item SUGAR_LUMPS = null;
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
				new ItemHorseAppleSlice(),
				new ItemCoin("coin_bronze"),
				new ItemCoin("coin_silver"),
				new ItemCoin("coin_gold"),
				new ItemCornSeeds("corn_seed"),
				new ItemHorseCorn(),
				new ItemHorseSugar(),
				new ItemApprovementStamp("letter_approvement_stamp"),
				new ItemInvitation("letter_invitation", InvitationType.APPROVED),
				new ItemInvitation("letter_invitation", InvitationType.BAIT),
				new ItemInvitation("letter_invitation", InvitationType.JOB),
				new ItemInvitation("letter_invitation", InvitationType.NORMAL),
				new ItemInvitation("letter_invitation", InvitationType.OFFICIAL),		
				new ItemReplyMail(),
				new ItemResetScroll("reset_scroll"),
				new ItemWalletSmall("wallet_small"),
				new ItemWalletLarge("wallet_large")
			};

			event.getRegistry().registerAll(items);

		}
	}
}
