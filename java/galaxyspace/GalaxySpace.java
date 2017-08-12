package galaxyspace;

import galaxyspace.core.configs.GSConfigCore;
import galaxyspace.core.events.GSEventHandler;
import galaxyspace.core.handler.GSColorRingClient;
import galaxyspace.core.proxy.CommonProxy;
import galaxyspace.core.registers.blocks.GSBlocks;
import galaxyspace.core.util.GSCreativeTabs;
import galaxyspace.systems.SolarSystem.SolarSystemPlanets;

import java.io.File;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.CreativeTabGC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;

import org.apache.logging.log4j.Level;



@Mod(
		modid = GalaxySpace.MODID,
		name = GalaxySpace.NAME, 
		version = GalaxySpace.VERSION, 
		acceptedMinecraftVersions = Constants.MCVERSION, 
		dependencies = Constants.DEPENDENCIES_FORGE + "required-after:galacticraftcore; required-after:galacticraftplanets;"
		//guiFactory = "micdoodle8.mods.galacticraft.core.client.gui.screen.ConfigGuiFactoryCore"
)


public class GalaxySpace
{
	public static final int major_version = 2;
	public static final int minor_version = 0;
	public static final int build_version = 0;
	
	public static final String MODID = "galaxyspace";
	public static final String NAME = "GalaxySpace";
    public static final String VERSION = "2.0.0";
    public static final String ASSET_PREFIX = "galaxyspace";
    public static final String TEXTURE_PREFIX = ASSET_PREFIX + ":";
    
    public static boolean debug = true;
    
    @SidedProxy(clientSide="galaxyspace.core.proxy.ClientProxy", serverSide="galaxyspace.core.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Instance(GalaxySpace.NAME)
    public static GalaxySpace instance;    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {
    	proxy.preload(event);
    	
    	new GSConfigCore(new File(event.getModConfigurationDirectory(), "GalaxySpace/core.conf"));
    	GSBlocks.registerBlocks();
    	
    	proxy.register_event(new GSEventHandler());
    	proxy.register_event(new GSColorRingClient());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
       this.registerComponents();

       SolarSystemPlanets.init();
       
       GSBlocks.registerRenders();
       
       GSCreativeTabs.GSBlocksTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsBlocks", new ItemStack(Item.getItemFromBlock(Blocks.DIRT)), null);
       GSCreativeTabs.GSItemsTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsItems", new ItemStack(Items.APPLE), null);
       GSCreativeTabs.GSArmorTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsArmor", new ItemStack(Items.BUCKET), null);
       GSCreativeTabs.GSRocketTab = new CreativeTabGC(CreativeTabs.getNextID(), "AddonsRocket", new ItemStack(Items.ARROW), null);
       
       proxy.load(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) 
    {
    	proxy.postload(event);
    }
    
    @EventHandler
	public void serverInit(FMLServerStartedEvent event)
	{
    	
	}
    
    private void registerComponents()
    {
        this.registerCreatures();
        this.registerNonMobEntities();
        this.registerTileEntities();
        this.registerRecipesWorkBench();
    }
    
    public void registerCreatures()
	{
    	
	}
    
    private void registerNonMobEntities()
    {
    	
    }
    
    private void registerTileEntities()
    {
    	
    }
    
    private void registerRecipesWorkBench()
    {
    	
    }
   
    
    public static void info(String message)
	{ 
		FMLRelaunchLog.log("Galaxy Space", Level.INFO, message);
	}
    
    public static void debug(String message)
   	{ 
   		if(debug) FMLRelaunchLog.log("[DEBUG] Galaxy Space", Level.INFO, message);
   	}

	public static void severe(String message)
	{
		FMLRelaunchLog.log("Galaxy Space", Level.ERROR, message);
	}
}
