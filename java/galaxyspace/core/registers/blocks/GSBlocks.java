package galaxyspace.core.registers.blocks;

import galaxyspace.core.util.GSClientUtil;
import galaxyspace.systems.SolarSystem.planets.mercury.blocks.BlockBasicMercury;

import java.util.List;
import java.util.Map;

import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.StackSorted;
import net.minecraft.block.Block;

import com.google.common.collect.Maps;


public class GSBlocks {
	
	public static Map<EnumSortCategoryBlock, List<StackSorted>> sortMapBlocks = Maps.newHashMap();
	
	public static Block MercuryBlocksBasic = new BlockBasicMercury("mercury_blocks").setHardness(2.2F);;
	
	public static void registerBlocks()
    {
		GSClientUtil.registerBlock(MercuryBlocksBasic);
		add();
    }	

	public static void add(){
		GSClientUtil.addVariants("mercury_blocks", "mercury_surface", "mercury_subsurface", "mercury_stone");
	}
	
	public static void registerRenders(){
		GSClientUtil.regVariants(MercuryBlocksBasic, 0, "mercury_surface");
		GSClientUtil.regVariants(MercuryBlocksBasic, 1, "mercury_subsurface");
		GSClientUtil.regVariants(MercuryBlocksBasic, 2, "mercury_stone");
		GSClientUtil.registerBlockRender(MercuryBlocksBasic);

	}	
	

	
	
}

