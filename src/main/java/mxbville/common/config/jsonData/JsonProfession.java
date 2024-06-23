package mxbville.common.config.jsonData;

import java.util.ArrayList;

public class JsonProfession {
	public String id;
	public String name;
	
	//TODO public ArrayList<JsonQuest> quests;
	public ArrayList<JsonTradingRecipe> tradingRecipes;
	
	public String[] upgradeProfessionIDs;
	public String[] affinityUpgradeOptions;
	public String[] upgradeRequirements;
	public String[] holdItems;
	public String textureName;
	
	public JsonProfession()
	{
		//TODO quests 			= new ArrayList<JsonQuest>();
		tradingRecipes 	= new ArrayList<JsonTradingRecipe>();
	}
}
