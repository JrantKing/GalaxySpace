package alexsocol.galaxyadditions.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alexsocol.galaxyadditions.ModInfo;
import alexsocol.galaxyadditions.blocks.LightningRod;
import alexsocol.galaxyadditions.blocks.LightningRod.EnumLightningRod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom.EnumType;
import net.minecraft.item.Item;

public class CreateJsonFileHelper {

	public static String path = "D://GA-1.11.2/src/main/resources/assets/galaxyadditions/";
    //          ***********BLOCKS************
	public static void addBlockJsonFiles(Block block){
		try{
			File blockStates = new File(path + "/blockstates/", block.getUnlocalizedName().substring(5) + ".json");
			File modelBlock = new File(path + "/models/block/", block.getUnlocalizedName().substring(5) + ".json");
			File modelItemBlock = new File(path + "/models/item/", block.getUnlocalizedName().substring(5) + ".json");
			
		 if(blockStates.createNewFile()){
			 blockstateJson(block, blockStates);
		 } else if(blockStates.exists()){
			 blockstateJson(block, blockStates);
		 }
		 if(modelBlock.createNewFile()){
			 modelBlockJson(block, modelBlock);
		 } else if(modelBlock.exists()){
			 modelBlockJson(block, modelBlock);
		 }
		 if(modelItemBlock.createNewFile()){
			 modelItemBlockJson(block, modelItemBlock);
		 } else if(modelItemBlock.exists()){
			 modelItemBlockJson(block, modelItemBlock);
		 }
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	private static void blockstateJson(Block block, File file){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"variants\": {"
		+     " \"normal\": { "
		+         "\"model\" : "
		+          "\"" + ModInfo.MODID + ":" + block.getUnlocalizedName().substring(5) + "\""
		+         "}"
		+     "}"
		+ "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	private static void modelBlockJson(Block block, File file){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + ModInfo.MODID + ":blocks/" + block.getUnlocalizedName().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
		
	}
	private static void modelItemBlockJson(Block block, File file){
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"block/cube_all\", "
		+     " \"textures\": { "
		+         "\"all\" : "
		+          "\"" + ModInfo.MODID + ":blocks/" + block.getUnlocalizedName().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	//           ***********ITEMS************
	public static void addItemJsonFiles(Item item){
		try{
			File modelItemFile = new File(path + "/models/item/", item.getUnlocalizedName().substring(5) + ".json");
		 if(modelItemFile.createNewFile()){
			 modelItemJson(item, modelItemFile);
		 } else if(modelItemFile.exists()){
			 modelItemJson(item, modelItemFile);
		 }
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	private static void modelItemJson(Item item, File file) {
		try{
		FileWriter writer = new FileWriter(file);
		writer.write(
		"{"
		+ " \"parent\": \"item/generated\", "
		+     " \"textures\": { "
		+         "\"layer0\" : "
		+          "\"" + ModInfo.MODID + ":items/" + item.getUnlocalizedName().substring(5) + "\""
		+       "}"
		+   "}"
		);
		writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	
    //          ***********BLOCKS META************
	public static void addBlockMetadataJsonFiles(Block block, String[] variants, String property){
		try{
			File blockstateItemBlockMeta = new File(path + "/blockstates/", block.getUnlocalizedName().substring(5) + ".json");
			for(int i = 0; i < variants.length; i++){
				File modelBlockMeta = new File(path + "/models/block/", block.getUnlocalizedName().substring(5) + "." + variants[i] + ".json");
				File modelItemBlockMeta = new File(path + "/models/item/", block.getUnlocalizedName().substring(5) + "." + variants[i] + ".json");
				
				if(modelBlockMeta.createNewFile()){
					modelBlockMetaJson(block, variants, modelBlockMeta, i);
				} else if(modelBlockMeta.exists()){
					modelBlockMetaJson(block, variants, modelBlockMeta, i);
				}
				if(modelItemBlockMeta.createNewFile()){
					modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i);
				} else if(modelItemBlockMeta.exists()){
					modelItemBlockMetaJson(block, variants, modelItemBlockMeta, i);
				}
			}
		 if(blockstateItemBlockMeta.createNewFile()){
			 blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
		 } else if (blockstateItemBlockMeta.exists()){
			 blockstateItemBlockMetaJson(block, blockstateItemBlockMeta, variants, property);
		 }
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	private static void blockstateItemBlockMetaJson(Block block, File file, String[] variants, String property) {
		try{
		  FileWriter writer = new FileWriter(file);
		  writer.write("{ \"variants\": {");
		 for(int i = 0; i < variants.length; i++){
	      String string = "\"" + property + "=" + variants[i] + "\"" + ": { \"model\": " + "\"" + ModInfo.MODID + ":" + block.getUnlocalizedName().substring(5) + "." + variants[i] + "\"}";
	     if(variants[i] != variants[0]){
		    writer.write("," + string);
		 } else {
			writer.write(string);
		 }}
          writer.write("}}");		
		  writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	private static void modelBlockMetaJson(Block block, String[] variants, File file, int i){
		try{
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"block/cube_all\", "
			+     " \"textures\": { "
			+         "\"all\" : "
			+          "\"" + ModInfo.MODID + ":blocks/" + block.getUnlocalizedName().substring(5) + "." + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
	private static void modelItemBlockMetaJson(Block block, String[] variants, File file, int i){
		try{
			FileWriter writer = new FileWriter(file);
			writer.write(
			"{"
			+ " \"parent\": \"block/cube_all\", "
			+     " \"textures\": { "
			+         "\"all\" : "
			+          "\"" + ModInfo.MODID + ":blocks/" + block.getUnlocalizedName().substring(5) + "." + variants[i] + "\""
			+       "}"
			+   "}"
			);
			writer.close();
		} catch(IOException ex){
			System.out.println(ex);
		}
	}
}
