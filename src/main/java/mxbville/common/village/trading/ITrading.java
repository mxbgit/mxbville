package mxbville.common.village.trading;

import mxbville.common.village.profession.Profession.TradingRecipeList;

public interface ITrading {
	TradingRecipeList getTradingRecipeList();
	void onTrade();
}
