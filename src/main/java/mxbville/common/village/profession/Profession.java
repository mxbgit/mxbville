package mxbville.common.village.profession;

import java.util.ArrayList;
import java.util.List;

import mxbville.common.calc.math.MxRand;
import mxbville.common.config.MxBvilleConfig;
import mxbville.common.config.jsondata.JsonDataManager;
import mxbville.common.config.jsondata.JsonHelper;
import mxbville.common.config.jsondata.JsonProfession;
import mxbville.common.config.jsondata.JsonTradingRecipe;
import mxbville.common.config.jsondata.JsonVillagerData;
import mxbville.common.village.trading.TradingRecipe;
import mxbville.common.village.trading.TradingRecipeList;
import mxbville.util.MxRef;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Profession implements IRegisterable{
	private String regID;

	//trading recipe list
	protected TradingRecipeList tradingRecipeList;
	//texture 
	
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
	
	//what professions can upgrade from this profession
	protected String[] upgradeToNextOptionIDs;
	protected String[] hiddenUpgradeOptionIDs;
	//what items are needed to upgrade to this profession(maximum: 3 stacks)
	protected ItemStack[] upgradeToCurentNeeds;
	//what items the villager will hold (will randomly select if there are more than 1 items in the list)
	protected ItemStack[] holdItems;
	//quests
	//protected List<Quest> quests;
	
	//unlocalized name
	protected String unlocalized;

	@Override
	public String getRegID() {
		return regID;
	}

	@Override
	public void setRegID(String regID) {
		this.regID = regID;
	}
	
	public Profession(JsonProfession proData){
		unlocalized = MxRef.MOD_ID + ":profession." + proData.name;
		loadProfessionData(proData);
	}
	
	private void loadProfessionData(JsonProfession proData)
	{
		//skin texture
		this.createTextures(proData.textureName);
		
		//trading
		tradingRecipeList = new TradingRecipeList();
		if(proData.tradingRecipes != null)
		{
			for(JsonTradingRecipe recipe : proData.tradingRecipes)
			{
				this.tradingRecipeList.add(new TradingRecipe(
						JsonHelper.stringsToItemStacks(recipe.inputs),
						JsonHelper.stringToItemStack(recipe.output)));
			}	
		}
		
		//upgrading
		upgradeToNextOptionIDs = proData.upgradeProfessionIDs == null?null:proData.upgradeProfessionIDs.clone();
		hiddenUpgradeOptionIDs = proData.hiddenUpgradeProfessionIDs == null?null:proData.hiddenUpgradeProfessionIDs.clone();
		upgradeToCurentNeeds = JsonHelper.stringsToItemStacks(proData.upgradeRequirements);
		
		//items on hands
		this.holdItems = JsonHelper.stringsToItemStacks(proData.holdItems);
		
		//quests 
		/*
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
	
	public String[] getHiddenUpgradeOptionIDs() {
		return this.hiddenUpgradeOptionIDs;
	}
	
	/**
	 * Returns the available Upgrading Professions for the current Villager.
	 * Adds certain hidden upgrade professions, if the requirements are met.
	 * 
	 * @return Array of Professions
	 */
	public ArrayList<Profession> getUpgradeToNextOptions()
	{
		ArrayList<Profession> list = new ArrayList<Profession>();
		if(this.upgradeToNextOptionIDs != null && this.upgradeToNextOptionIDs.length > 0){
			for(int i = 0;i<this.upgradeToNextOptionIDs.length;i++){
				Profession p = registry.get(this.upgradeToNextOptionIDs[i]);
				if (p != null){
					if(!isProIDBanned(p.getRegID())){
						list.add(p);
					}
				}
			}
		}		
			
		return list;
	}
	
	
	private boolean isProIDBanned(String proid){
		if(MxBvilleConfig.proIDBanList == null)
			return false;
		else{
			for(int i =0;i<MxBvilleConfig.proIDBanList.length;i++){
				if(proid == MxBvilleConfig.proIDBanList[i])
					return true;
			}
			return false;
		}
	}
	
	
	
	public ResourceLocation getTexture(boolean male, int skinid){
		if (male) {
			switch (skinid) {
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
			switch (skinid) {
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
	
	protected void createTextures(String name){
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
	
	
	/*
	public List<Quest> getQuests(){
		return this.quests;
	}
	*/
	protected String getUnlocalized()
	{
		return unlocalized;
	}
	
	
	//---------------------------------
	//registry
	public static ProfessionRegistry<Profession> registry = new ProfessionRegistry<Profession>();
	
	public static void init(){
		
		JsonVillagerData data = JsonDataManager.GetVillagerData();
		/*
		ArrayList<String> list = JsonDataManager.GetProfessionList();
		for (int x = 0; x < list.size(); x++) {
			System.out.println(list.get(x));
		}
		System.out.println("Random trait this time: " + list.get(MxRand.get().nextInt(list.size())));
		*/
		
		for(JsonProfession pro : data.professions)
		{
			registry.register(pro.name, new Profession(pro));
		}
	}
}