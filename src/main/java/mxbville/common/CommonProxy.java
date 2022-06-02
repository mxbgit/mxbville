package mxbville.common;
import mxbville.MxBville;
import mxbville.common.config.MxBvilleConfig;
import mxbville.common.events.EventCoinsFound;
import mxbville.common.events.EventEntity;
import mxbville.common.gui.GuiHandler;
import mxbville.common.player.CapExPlayerProperties;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public void registerItemRenderer(Item item, int meta) {}
	
	public void preInit(FMLPreInitializationEvent event)
	{
		//config
		MxBvilleConfig.load(event.getModConfigurationDirectory());
		
		//gui
		NetworkRegistry.INSTANCE.registerGuiHandler(MxBville.instance, new GuiHandler());
		
		//capability
		CapExPlayerProperties.register();
		
		// events
		MinecraftForge.EVENT_BUS.register(new EventEntity());    
		MinecraftForge.EVENT_BUS.register(new EventCoinsFound());
	}
	
	public void init(FMLInitializationEvent event)
	{

	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
