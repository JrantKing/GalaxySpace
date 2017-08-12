package galaxyspace.core.util;

import galaxyspace.GalaxySpace;

import java.util.List;

import micdoodle8.mods.galacticraft.core.wrappers.ModelTransformWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.common.registry.GameRegistry;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;

public class GSClientUtil  {

	public static void registerBlock(Block block){
		GameRegistry.register(block);
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
	}
	
	public static void registerBlockRender(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	public static void registerItemRender(Item item){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	public static void addVariants(String name, String... variants) { 
		Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(GalaxySpace.MODID, name)); 
		ResourceLocation[] variants0 = new ResourceLocation[variants.length]; 
		for (int i = 0; i < variants.length; ++i) { 
		variants0[i] = new ResourceLocation(GalaxySpace.TEXTURE_PREFIX + variants[i]); 
		} 
		ModelBakery.registerItemVariants(itemBlockVariants, variants0); 
		}
	
	public static void regVariants(Block block, int meta, String name){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(GalaxySpace.TEXTURE_PREFIX + name, "inventory"));
	}
}
