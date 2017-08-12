package galaxyspace.core.configs;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class GSConfigCore
{
    public static boolean loaded;

    static Configuration config;

    public GSConfigCore(File file)
    {
        if (!GSConfigCore.loaded)
        {
        	GSConfigCore.config = new Configuration(file);
        	GSConfigCore.syncConfig(true);
        }
    }

    public static boolean enableCheckVersion;

    public static boolean enableLeadGeneration;
    public static boolean enableOresGeneration;
    public static boolean enableDungeonsGeneration;
    public static boolean enableNewMenu;
    public static boolean enableFogVenus;

    public static int idSolarRadiation = 29;
    
    public static boolean enableMethaneParticle;
    public static boolean enableNewGalaxyMap;
    public static boolean enableUnreachable;
    public static boolean enableHardPressure;
    //public static boolean enableWorldEngine;
    
    public static String[] oregenIDs = { };
    
    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try
        {
            Property prop;

            if (!config.isChild)
            {
                if (load)
                {
                    config.load();
                }
            }

            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableCheckVersion", true);
            prop.setComment("Enable/Disable Check Version.");
            prop.setLanguageKey("gc.configgui.enableCheckVersion").setRequiresMcRestart(true);;
            enableCheckVersion = prop.getBoolean(true);
            propOrder.add(prop.getName());            
       
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableLeadGeneration", true);
            prop.setComment("Enable/Disable Generation Lead on Overworld.");
            prop.setLanguageKey("gc.configgui.enableLeadGeneration").setRequiresMcRestart(true);;
            enableLeadGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableOresGeneration", true);
            prop.setComment("Enable/Disable Generation Ores on Planets/Moon (Global Config).");
            prop.setLanguageKey("gc.configgui.enableOresGeneration").setRequiresMcRestart(true);;
            enableOresGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableDungeonsGeneration", true);
            prop.setComment("Enable/Disable Dungeons Generation on Planets/Moon (Global Config).");
            prop.setLanguageKey("gc.configgui.enableDungeonsGeneration").setRequiresMcRestart(true);;
            enableDungeonsGeneration = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableNewMenu", true);
            prop.setComment("Enable/Disable new Main Menu.");
            prop.setLanguageKey("gc.configgui.enableNewMenu").setRequiresMcRestart(true);;
            enableNewMenu = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableMethaneParticle", true);
            prop.setComment("Enable/Disable Methane Particles.");
            prop.setLanguageKey("gc.configgui.enableMethaneParticle").setRequiresMcRestart(true);;
            enableMethaneParticle = prop.getBoolean(true);
            propOrder.add(prop.getName());           
            
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "Other mods ores for GC to generate on GS planets", new String [] { });
            prop.setComment("Enter IDs of other mods' ores here for Galacticraft to generate them on GalaxySpace planets. Format is BlockName or BlockName:metadata. Use optional parameters at end of each line: /RARE /UNCOMMON or /COMMON for rarity in a chunk; /DEEP /SHALLOW or /BOTH for height; /SINGLE /STANDARD or /LARGE for clump size; /XTRARANDOM for ores sometimes there sometimes not at all.  /ONLYMOON or /ONLYMARS if wanted on one planet only.  If nothing specified, defaults are /COMMON, /BOTH and /STANDARD.  Repeat lines to generate a huge quantity of ores.");
            prop.setComment("/ONLYPHOBOS, /ONLYDEIMOS, /ONLYEUROPA, /ONLYIO, /ONLYENCELADUS, /ONLYVENUS, /ONLYMERCURY, /ONLYCERES if wanted on one planet only.");
            prop.setLanguageKey("gc.configgui.otherModOreGenIDs");
            oregenIDs = prop.getStringList();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "enableFogVenus", true);
            prop.setComment("Enable/Disable Fog on Venus.");
            prop.setLanguageKey("gc.configgui.enableFogVenus").setRequiresMcRestart(true);;
            enableFogVenus = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "idSolarRadiation", idSolarRadiation);
            prop.setComment("ID Potion 'Solar Radiation'");
            prop.setLanguageKey("gc.configgui.idSolarRadiation").setRequiresMcRestart(true);
            idSolarRadiation = prop.getInt();
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableNewGalaxyMap", true);
            prop.setComment("Enable/Disable New Galaxy Map.");
            prop.setLanguageKey("gc.configgui.enableNewGalaxyMap").setRequiresMcRestart(true);;
            enableNewGalaxyMap = prop.getBoolean(true);
            propOrder.add(prop.getName());
   
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableUnreachable", true);
            prop.setComment("Enable/Disable Unreachable Planets/Moons");
            prop.setLanguageKey("gc.configgui.enableUnreachable").setRequiresMcRestart(true);
            enableUnreachable = prop.getBoolean(true);
            propOrder.add(prop.getName());
            
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableHardPressure", true);
            prop.setComment("Enable/Disable Death of Atm. Pressure");
            prop.setLanguageKey("gc.configgui.enableHardPressure").setRequiresMcRestart(true);
            enableHardPressure = prop.getBoolean(true);
            propOrder.add(prop.getName());
            /*
            prop = config.get(Constants.CONFIG_CATEGORY_WORLDGEN, "enableWorldEngine", true);
            prop.comment = "Enable/Disable 'World Engine' - advanced world generation";
            prop.setLanguageKey("gc.configgui.enableWorldEngine").setRequiresMcRestart(true);
            enableWorldEngine = prop.getBoolean(true);
            propOrder.add(prop.getName());*/
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Core) has a problem loading it's config");
        }
    }
}
