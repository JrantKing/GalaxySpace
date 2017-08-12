package alexsocol.galaxyadditions.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import alexsocol.galaxyadditions.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom.EnumType;
import net.minecraft.item.Item;

public class CreateJsonFileHelper {

	public static void addBlockJsonFiles(Block block){
		try{
			String path = "D://GA-1.11.2/src/main/resources/assets/galaxyadditions/";
			File blockStates = new File(path + "/blockstates/", block.getUnlocalizedName().substring(5) + ".json");
			File modelBlock = new File(path + "/models/block/", block.getUnlocalizedName().substring(5) + ".json");
			File modelItemBlock = new File(path + "/models/item/", block.getUnlocalizedName().substring(5) + ".json");
			
		 if(blockStates.createNewFile()){
			 blockstateJson(block, blockStates);
		 } 
		 if(modelBlock.createNewFile()){
			 modelBlockJson(block, modelBlock);
		 } 
		 if(modelItemBlock.createNewFile()){
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
			String path = "D://GA-1.11.2/src/main/resources/assets/galaxyadditions/";
			File modelItemFile = new File(path + "/models/item/", item.getUnlocalizedName().substring(5) + ".json");
		 if(modelItemFile.createNewFile()){
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
}
