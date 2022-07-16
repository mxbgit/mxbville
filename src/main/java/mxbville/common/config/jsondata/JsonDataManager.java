package mxbville.common.config.jsondata;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.GsonBuilder;

import mxbville.util.MxRef;

public class JsonDataManager {

	private static final String idMapFileName 		= "profession_id_map.json";
	private static final String professionsFileName	= "professions.json";
	
	// holds an arraylist of every profession with its quests and traderecipes
	private static JsonProfessionIDMap  professionIDMap;
	private static JsonVillagerData 	villagerData;
	
	
	public static JsonVillagerData GetVillagerData()
	{
		return villagerData;
	}
	
	public static JsonProfessionIDMap GetProfessionIDMap()
	{
		return professionIDMap;
	}
	
	public static void LoadData(File configDir) 
	{
		loadConfigFile(idMapFileName, configDir);
		//loadConfigFile(professionsFileName, configDir);
	}
	
	private static void loadConfigFile(String filename, File configDir) 
	{
		File subFolderConfig = new File(configDir, MxRef.MOD_ID);
		if(!subFolderConfig.exists()) subFolderConfig.mkdir();

		File file = new File(subFolderConfig, filename);
		
		if(file.exists())
		{
			try {
				loadDataFromFile(file, filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				createDefaultDataFile(file, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private static void loadDataFromFile(File file, String filename) throws IOException
	{
		FileReader reader = new FileReader(file);
		if (filename.contains("id")) {
			professionIDMap = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonProfessionIDMap.class);
		}
		if (!filename.contains("id")) {
			villagerData = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonVillagerData.class);
		}		
		reader.close();
	}
	
	private static void createDefaultDataFile(File file, String filename) throws IOException
	{
		//create default data
		//villagerData 	= createDefaultData();
		professionIDMap = new JsonProfessionIDMap();

		//save to file
		FileWriter writter = new FileWriter(file);
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(professionIDMap);
		//System.out.println(json);
		writter.write(json);
		writter.close();
	}
}
