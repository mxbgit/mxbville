package mxbville.common.village.profession;

import java.util.ArrayList;

import mxbville.MxBville;
import mxbville.common.calc.math.MxRand;
import mxbville.common.config.jsonData.DataLoader;
import mxbville.common.config.jsonData.JsonMxBData;
import mxbville.common.config.jsonData.JsonProfession;
import mxbville.common.config.jsonData.JsonStackTranslater;
import mxbville.common.config.jsonData.JsonTradingRecipe;
import mxbville.common.functions.reg.IRegistrable;
import mxbville.common.functions.reg.Registry;
import mxbville.common.village.trading.TradingRecipe;
import mxbville.util.MxRef;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Profession implements IRegistrable {

	private String regID;
	protected TradingRecipeList tradingRecipeList;
	protected String[] upgradeToNextOptionIDs;
	protected String[] affinityUpgradeOptions;
	
	protected ItemStack[] upgradeToCurentNeeds;
	protected ItemStack[] holdItems;
	
	//protected List<Quest> quests;
		
	protected String unlocalized;
	
	protected ResourceLocation texture_0_f;
	protected ResourceLocation texture_1_f;
	protected ResourceLocation texture_2_f;
	protected ResourceLocation texture_3_f;
	protected ResourceLocation texture_4_f;
	protected ResourceLocation texture_0_m;
	protected ResourceLocation texture_1_m;
	protected ResourceLocation texture_2_m;
	protected ResourceLocation texture_3_m;
	protected ResourceLocation texture_4_m;
	
	@Override
	public String getRegID() {
		return this.regID;
	}

	@Override
	public void setRegID(String regID) {
		this.regID = regID;		
	}

	public Profession(JsonProfession proData){
		unlocalized = MxRef.MOD_ID + ":profession." + proData.name;
		loadProfessionData(proData);
	}
	
	private void loadProfessionData(JsonProfession proData) {
		
		this.createTextures(proData.name);
		tradingRecipeList = new TradingRecipeList();
		if(proData.tradingRecipes != null)
		{
			for(JsonTradingRecipe recipe : proData.tradingRecipes)
			{
				this.tradingRecipeList.add(new TradingRecipe(
						JsonStackTranslater.stringsToItemStacks(recipe.inputs),
						JsonStackTranslater.stringToItemStack(recipe.output)));
			}	
		}
		
		//upgrading
		upgradeToNextOptionIDs = proData.upgradeProfessionIDs == null?null:proData.upgradeProfessionIDs.clone();
		affinityUpgradeOptions = proData.affinityUpgradeOptions == null?null:proData.affinityUpgradeOptions.clone();
		upgradeToCurentNeeds = JsonStackTranslater.stringsToItemStacks(proData.upgradeRequirements);
		
		//items on hands
		this.holdItems = JsonStackTranslater.stringsToItemStacks(proData.holdItems);
		/*
		//quests 
		this.quests = new ArrayList<Quest>();	
		if(proData.quests != null)
		{
			for(JsonQuest quest : proData.quests)
			{
				this.quests.add(new Quest(
						JsonHelper.stringsToItemStacks(quest.objectives),
						JsonHelper.stringsToItemStacks(quest.rewards)));
			}	
		}
		*/
	}
	
	public ArrayList<Profession> getUpgradeToNextOptions(){
		ArrayList<Profession> list = new ArrayList<Profession>();
		if(this.upgradeToNextOptionIDs != null && this.upgradeToNextOptionIDs.length > 0)
		{
			for(int i = 0;i<this.upgradeToNextOptionIDs.length;i++){
				Profession profession = registry.get(this.upgradeToNextOptionIDs[i]);		
				if(!isProIDBanned(profession.getRegID())){
					list.add(profession);
				}
			}
		}
		return list;
	}
	
	public String[] getAffinityUpgradeOptions() {
		return this.affinityUpgradeOptions;
	}
	
	private boolean isProIDBanned(String professionName){
		if(MxBville.MXCONFIG.bannedProfessionsList == null || MxBville.MXCONFIG.bannedProfessionsList.length == 0 ) {
			return false;
		}else {
			for (int i = 0; i <MxBville.MXCONFIG.bannedProfessionsList.length; i++) {
				if (professionName == MxBville.MXCONFIG.bannedProfessionsList[i])
				{
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Returns null if upgrading is set to free in mainconfig.
	 * 
	 * @return List of ItemStacks
	 */
	public ItemStack[] getUpgradeToCurentNeeds(){
		return MxBville.MXCONFIG.freeUpgrading?null:this.upgradeToCurentNeeds;
	}
	
	
	public ResourceLocation getTexture(boolean isMale, int faceVariant){
		if (isMale) {
			switch (faceVariant) {
			case 0:
				return this.texture_0_m;
			case 1:
				return this.texture_1_m;
			case 2:
				return this.texture_2_m;
			case 3:
				return this.texture_3_m;
			case 4:
				return this.texture_4_m;
			default:
				return this.texture_0_m;
			}
		}else {
			switch (faceVariant) {
			case 0:
				return this.texture_0_f;
			case 1:
				return this.texture_1_f;
			case 2:
				return this.texture_2_f;
			case 3:
				return this.texture_3_f;
			case 4:
				return this.texture_4_f;
			default:
				return this.texture_0_f;
			}
		}
	}
	
	public TradingRecipeList getTradingRecipeList(){
		return this.tradingRecipeList;
	}
		
	protected void createTextures(String name) {

		this.texture_0_m = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "0_m.png");
		
		this.texture_1_m = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "1_m.png");
		this.texture_2_m = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "2_m.png");
		this.texture_3_m = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "3_m.png");
		this.texture_4_m = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "4_m.png");
		
		this.texture_0_f = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "0_f.png");
		
		this.texture_1_f = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "1_f.png");
		this.texture_2_f = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "2_f.png");
		this.texture_3_f = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "3_f.png");
		this.texture_4_f = new ResourceLocation(MxRef.MOD_ID + ":textures/entity/villager/" + name + "/" + name + "4_f.png");
	}
	
	public ItemStack getRandomHoldItem(){
		if(MxRand.get().nextFloat() < 0.6F || this.holdItems == null || this.holdItems.length < 1)
			return ItemStack.EMPTY;
		else
			return this.holdItems[MxRand.get().nextInt(this.holdItems.length)];
	}
	
	
	public String getUnloalizedDisplayName()
	{
		return this.getUnlocalized() + ".name";
	}
	
	public String getUnloalizedDescription(){
		return this.getUnlocalized() + ".desc";
	}
	
	protected String getUnlocalized()
	{
		return unlocalized;
	}
	
	
	public static Registry<Profession> registry = new Registry<Profession>();
	
	public static void init(){
		
		JsonMxBData data = DataLoader.GetMxBData();
		
		for(JsonProfession pro : data.professions)
		{
			registry.register(pro.name, new Profession(pro));
		}
	}
	
	public class TradingRecipeList extends ArrayList<TradingRecipe>{
	}
}
