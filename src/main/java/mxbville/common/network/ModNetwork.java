package mxbville.common.network;

import mxbville.common.network.message.player.MessageSyncExtendedPlayerProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {

	private static SimpleNetworkWrapper instance;
	public static SimpleNetworkWrapper getInstance(){
		return instance;
	}
		
	public static void init(){
			instance = NetworkRegistry.INSTANCE.newSimpleChannel("netvlgbto");
			initMessages();
	}	
		
	private static void initMessages(){
		int id = 0;
		
		id = 100;
		instance.registerMessage(MessageSyncExtendedPlayerProperties.Handler.class, MessageSyncExtendedPlayerProperties.class, id++, Side.CLIENT);
	}
}
