package galaxyspace.core.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.google.common.collect.Maps;


public class ClientProxy extends CommonProxy {
	
	public static Map<String, String> GScapeMap = new HashMap<String, String>();
	public static Map<String, ResourceLocation> GScapesMap = Maps.newHashMap();
	
	public static Minecraft mc = FMLClientHandler.instance().getClient();

	

	@Override
    public void preload(FMLPreInitializationEvent event) {	
		super.preload(event);		
	}

    @Override
    public void load(FMLInitializationEvent event)
    {  
    	super.load(event);

    	registerEntityRenderers();
    	
    }
	
    @Override
    public void postload(FMLPostInitializationEvent event) {   			
    	super.postload(event);
	}
  
   
	public static void registerEntityRenderers()
    {
		
    }

	public void register_event(Object obj)
	{
    	FMLCommonHandler.instance().bus().register(obj);
    	MinecraftForge.EVENT_BUS.register(obj);
	}	
 }
