package mxbville.common.config.jsonData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.GsonBuilder;

import mxbville.util.MxRef;

public class DataLoader {
	
	private static final String jsonProfessionFileName 	= "professions.json";

	private static JsonMxBData mxbData;
	private static String B_COIN = MxRef.MOD_ID + ",bronze_coin";
	private static String S_COIN = MxRef.MOD_ID + ",silver_coin";
	private static String G_COIN = MxRef.MOD_ID + ",gold_coin";
	
	public static JsonMxBData GetMxBData()
	{
		return mxbData;
	}
	
	public static void loadData(File configDir) {
		//TODO: Load data from seperate files to better handle config adjustments
		File subFolderConfigFile = new File(configDir, MxRef.MOD_ID);
		if (!subFolderConfigFile.exists()) subFolderConfigFile.mkdir();
		File fileProfessions = new File(subFolderConfigFile, jsonProfessionFileName);
		
		if(fileProfessions.exists())
		{
			try {
				loadDataFromFile(fileProfessions);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				createDefaultDataFile(fileProfessions);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public static void loadDataFromFile(File file) throws IOException {
		FileReader reader = new FileReader(file);		
		mxbData = new GsonBuilder().setPrettyPrinting().create().fromJson(reader, JsonMxBData.class);		
		reader.close();
	}
	
	public static void createDefaultDataFile(File file) throws IOException {
		//TODO: Create seperate files to better handle config adjustments
		//create default data
		mxbData = createDefaultData();
		//save to file
		FileWriter writter = new FileWriter(file);
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(mxbData);
		//System.out.println(json);
		writter.write(json);
		writter.close();
	}
	
	private static JsonMxBData createDefaultData() {
		JsonMxBData data = new JsonMxBData();
		JsonProfession profession = null;
		
		//====== villager ======
		profession = new JsonProfession();
		profession.name = "villager";
		// The Upgrade Requirements show, what item is needed to get to the current profession, not the next one.
		// So in this case a book is needed to upgrade TO profession id 0 (the villager)
		profession.upgradeRequirements = new String[]{"minecraft,book,1,0"};
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",2,0"}, "minecraft,bread,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",3,0"}, "minecraft,apple,1,0"));
		
		profession.upgradeProfessionIDs = new String[]{"peasant","worker","scholar","banker"};
		profession.holdItems = new String[]{"minecraft,bread,1,0", "minecraft,apple,1,0"};
		data.professions.add(profession);
		
		//====== peasant ======
		profession = new JsonProfession();
		profession.name = "peasant";
		profession.upgradeRequirements = new String[]{B_COIN + ",28,0", "minecraft,iron_hoe,1,0"};
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",5,0"}, "minecraft,wheat_seeds,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",10,0"}, "minecraft,beetroot_seeds,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",8,0"}, "minecraft,carrot,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",8,0"}, "minecraft,potato,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",9,0"}, "minecraft,pumpkin,1,0"));
		
		profession.upgradeProfessionIDs = new String[]{"farmer","butcher"};
		profession.affinityUpgradeOptions = new String[]{"florist","fisherman","hunter"};
		profession.holdItems = new String[]{"minecraft,iron_hoe,1,0","minecraft,wheat,1,0"};
		profession.textureName = "peasant";
		
		data.professions.add(profession);

		return data;
	}
}
