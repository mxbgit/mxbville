package mxbville;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mxbville.common.CommonProxy;
import mxbville.util.MxRef;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MxRef.MOD_ID, name = MxRef.NAME, version = MxRef.VERSION, acceptedMinecraftVersions = MxRef.MC_VERSION)
public class MxBville {
	
	public static MxBville instance;
	
	//tabs
	
    public static final Logger LOGGER = LogManager.getLogger(MxRef.MOD_ID);


    @SidedProxy(clientSide = MxRef.CLIENT_PROXY_CLASS, serverSide = MxRef.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	this.proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	this.proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	this.proxy.postInit(event);
    }
    
}
