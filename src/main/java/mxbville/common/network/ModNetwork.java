package mxbville.common.network;

import mxbville.common.network.messages.villager.MessageGuiSetInteracting;
import mxbville.common.network.messages.villager.MessageSpawnNewVillagerThroughMail;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {

	private static SimpleNetworkWrapper networkInstance;
	
	public static SimpleNetworkWrapper getInstance() {
		return networkInstance;
	}
	
	public static void init() {
		networkInstance = NetworkRegistry.INSTANCE.newSimpleChannel("netvlgbto");
		initMessages();
	}
	
	private static void initMessages() {
		int id = 0;
		networkInstance.registerMessage(MessageSpawnNewVillagerThroughMail.Handler.class, MessageSpawnNewVillagerThroughMail.class, id++, Side.SERVER);
		networkInstance.registerMessage(MessageGuiSetInteracting.Handler.class, MessageGuiSetInteracting.class, id++, Side.SERVER);
	}
}
