package mxbville.common.config.jsondata;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import mxbville.util.MxRef;

public class JsonDataManager {

	private static final String professionsFileName	= "professions.json";
	
	// holds an arraylist of every profession with its quests and traderecipes
	private static JsonVillagerData villagerData;
	private static ArrayList<String> professionList;
	
	public static JsonVillagerData GetVillagerData()
	{
		return villagerData;
	}
	
	public static ArrayList<String> GetProfessionList()
	{
		return professionList;
	}
	
	
	public static void LoadData(File configDir) 
	{
		File subFolderConfig = new File(configDir, MxRef.MOD_ID);
		if(!subFolderConfig.exists()) subFolderConfig.mkdir();

		File file = new File(subFolderConfig, professionsFileName);
		
		if(file.exists())
		{
			try {
				loadDataFromFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				createDefaultDataFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadDataFromFile(File file) throws IOException
	{
		FileReader reader = new FileReader(file);
		villagerData = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonVillagerData.class);
		buildProfessionList(villagerData);
		reader.close();
	}
	
	private static void createDefaultDataFile(File file) throws IOException
	{
		//create default data
		villagerData 	= new JsonVillagerData();
		// build professionlist
		buildProfessionList(villagerData);
		
		//save to file
		FileWriter writter = new FileWriter(file);
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(villagerData);
		//System.out.println(json);
		writter.write(json);
		writter.close();
	}
	
	private static void buildProfessionList(JsonVillagerData villagerData)
	{
		professionList = new ArrayList<String>();
		villagerData.professions.forEach((profession) -> professionList.add(profession.name));
	}
}
