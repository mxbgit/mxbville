package mxbville.common.config.jsondata;

import java.util.ArrayList;

public class JsonProfession {
	
	public String name;
	
	public ArrayList<JsonQuest> quests;
	public ArrayList<JsonTradingRecipe> tradingRecipes;
	
	public String[] upgradeProfessionIDs;
	public String[] hiddenUpgradeProfessionIDs;
	public String[] upgradeRequirements;
	public String[] holdItems;
	public String textureName;
	
	public JsonProfession()
	{
		quests 			= new ArrayList<JsonQuest>();
		tradingRecipes 	= new ArrayList<JsonTradingRecipe>();
	}

}
