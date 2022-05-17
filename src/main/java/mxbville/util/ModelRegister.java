package mxbville.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ModelRegister {

	//for item model registration
	public static final Set<IHasModel> LIST = new HashSet<>();
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(IHasModel objectWithModel : LIST)
		{
			objectWithModel.registerModels();
		}
	}
}
