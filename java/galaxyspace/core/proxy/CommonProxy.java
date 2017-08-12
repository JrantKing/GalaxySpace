package galaxyspace.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
    public void preload(FMLPreInitializationEvent event) {}
	
	
    public void load(FMLInitializationEvent event){}
	
	
    public void postload(FMLPostInitializationEvent event) {}

	
	public void register_event(Object obj)
	{
    	//FMLCommonHandler.instance().bus().register(obj);
    	MinecraftForge.EVENT_BUS.register(obj);
	}

}
