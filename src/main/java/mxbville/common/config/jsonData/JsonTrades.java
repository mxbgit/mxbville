package mxbville.common.config.jsonData;

import java.util.ArrayList;

public class JsonTrades {
	public int professionId;
	public String professionName;
	
	public ArrayList<JsonTradingRecipe> tradingRecipes;

	public JsonTrades()
	{
		tradingRecipes = new ArrayList<JsonTradingRecipe>();
	}
}
