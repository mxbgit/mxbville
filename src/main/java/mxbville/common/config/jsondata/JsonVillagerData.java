package mxbville.common.config.jsondata;

import java.util.ArrayList;

import mxbville.util.MxRef;

public class JsonVillagerData{
	
	public ArrayList<JsonProfession> professions;
	
	private static String 		B_COIN = MxRef.MOD_ID + ",coin_bronze";
	private static String 		S_COIN = MxRef.MOD_ID + ",coin_silver";
	private static String 		G_COIN = MxRef.MOD_ID + ",coin_gold";
	
	public JsonVillagerData()
	{
		professions = new ArrayList<JsonProfession>();
		init();
	}
	
	
	//================================================================================================
	//	New format for items: DOMAIN,ITEMNAME,AMOUNT,-FLAG-  					
	//  where -FLAG- can be:
	//	I. 		An Number, representing the datavalue
	//			(eg: "minecraft,double_plant,4,4")
	//	
	//	II. 	The '*' sign, followed by a NUMBER representing the max. datavalue.
	//			This is only applicable for items with different datavalues and results
	//			in a random datavalue with the range of [0 - NUMBER] (number inclusive)
	//			(eg: minecraft,fish,3,*,3)
	//
	//	III. 	The string 'potion' followed by a potion effect.
	//			(eg: "minecraft,potion,1,potion,minecraft:long_night_vision")
	//
	//  IV. 	The string 'enchanted' followed by a number representing 
	//		 	the amount of enchantments on the item and an enumeration of enchantment IDs and respectiv LVL numbers 
	//		 	(eg: "minecraft,iron_helmet,1,enchanted,2,6:1,5:2")
	//		 	(Note that the ench.ID and its level are separated by an ':')
	//================================================================================================
	public void init() {
		JsonProfession profession = null;
		
		//====== villager ======
		profession = new JsonProfession();
		profession.name = "villager";
		profession.textureName = "villager";
		// The Upgrade Requirements show, what item is needed to get to the current profession, not the next one.
		// So in this case a book is needed to upgrade TO profession id 0 (the villager)
		profession.upgradeRequirements = new String[]{"minecraft,book,1,0"};
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",2,0"}, "minecraft,bread,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",3,0"}, "minecraft,apple,1,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,cooked_chicken,1,0"}, new String[]{B_COIN + ",4,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,cooked_fish,1,0"}, new String[]{B_COIN + ",3,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,cooked_fish,1,0"}, new String[]{"minecraft,apple,2,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,cooked_chicken,1,0"}, new String[]{"minecraft,apple,2,0"}));
		
		profession.upgradeProfessionIDs = new String[]{"peasant","worker","scholar","banker"};
		profession.holdItems = new String[]{"minecraft,bread,1,0", "minecraft,apple,1,0"};
		
		this.professions.add(profession);
	
		//====== peasant ======
		profession = new JsonProfession();
		profession.name = "peasant";
		profession.textureName = "peasant";
		profession.upgradeRequirements = new String[]{B_COIN + ",28,0", "minecraft,iron_hoe,1,0"};
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",5,0"}, "minecraft,wheat_seeds,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",10,0"}, "minecraft,beetroot_seeds,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",8,0"}, "minecraft,carrot,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",8,0"}, "minecraft,potato,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",9,0"}, "minecraft,pumpkin_seeds,3,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,iron_hoe,1,0"}, new String[]{B_COIN + ",16,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,wheat,18,0"}, new String[]{B_COIN + ",10,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,beetroot,9,0"}, new String[]{B_COIN + ",8,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,pumpkin,12,0"}, new String[]{B_COIN + ",16,0"}));

		profession.upgradeProfessionIDs = new String[]{"farmer","butcher"};
		profession.hiddenUpgradeProfessionIDs = new String[]{"florist","fisherman","hunter","vintner"};
		profession.holdItems = new String[]{"minecraft,iron_hoe,1,0","minecraft,wheat,1,0"};
		
		this.professions.add(profession);
		
		//====== worker ======
		profession = new JsonProfession();
		profession.name = "worker";
		profession.textureName = "worker";
		profession.upgradeRequirements = new String[]{B_COIN + ",30,0","minecraft,crafting_table,1,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",20,0"}, "minecraft,iron_axe,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",20,0"}, "minecraft,iron_pickaxe,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",18,0"}, "minecraft,iron_shovel,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",16,0"}, "minecraft,iron_hoe,1,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,crafting_table,1,0"}, new String[]{B_COIN + ",10,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,crafting_table,1,0"}, new String[]{"minecraft,iron_pickaxe,1,0"}));
		
		profession.upgradeProfessionIDs = new String[]{"blacksmith","lumberjack"};
		profession.hiddenUpgradeProfessionIDs = new String[]{"mason","miner","guard","leatherworker","carpetmaker"};
		profession.holdItems = new String[]{"minecraft,iron_shovel,1,0"};

		this.professions.add(profession);
		
		//====== scholar ======
		profession = new JsonProfession();
		profession.name = "scholar";
		profession.textureName = "scholar";
		profession.upgradeRequirements = new String[]{S_COIN + ",2,0","minecraft,book,1,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",20,0"}, "minecraft,book,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",9,0"}, "minecraft,paper,3,0"));
		//profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0","minecraft,paper,1,0"}, MxRef.MOD_ID + ",reset_scroll,1,0"));
		
		profession.quests.add(new JsonQuest(new String[]{B_COIN + ",10,0"}, new String[]{"minecraft,book,1,0"}));
		//profession.quests.add(new JsonQuest(new String[]{"minecraft,book,5,0"}, new String[]{MxRef.MOD_ID + ",reset_scroll,1,0"}));
				
		profession.upgradeProfessionIDs = new String[]{"healer","librarian","mystic"};
		profession.hiddenUpgradeProfessionIDs = new String[]{"painter","treasurehunter"};
		profession.holdItems = new String[]{"minecraft,book,1,0"};
		
		this.professions.add(profession);
		
		//====== banker ======
		profession = new JsonProfession();
		profession.name = "banker";
		profession.textureName = "banker";
		profession.upgradeRequirements = new String[]{B_COIN + ",64,0",B_COIN + ",64,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",64,0"}, S_COIN + ",1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",64,0"}, G_COIN + ",1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, B_COIN + ",64,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{G_COIN + ",1,0"}, S_COIN + ",64,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,diamond,1,0"}, new String[]{S_COIN + ",2,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,emerald,2,0"}, new String[]{S_COIN + ",2,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,diamond,1,0"}, new String[]{"minecraft,ender_pearl,3,0"}));
		
		profession.holdItems = new String[]{S_COIN + ",1,0", G_COIN + ",64,0"};

		this.professions.add(profession);
		
		//====== farmer ======
		profession = new JsonProfession();
		profession.name = "farmer";
		profession.textureName = "farmer";
		
		profession.upgradeRequirements = new String[]{S_COIN + ",1,0", "minecraft,golden_hoe,1,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{"minecraft,wheat,20,0"}, B_COIN + ",15,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{"minecraft,beetroot,16,0"}, B_COIN + ",24,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{"minecraft,pumpkin,8,0"}, B_COIN + ",24,0"));
		//profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",2,0", "minecraft,wheat_seeds,16,0", "minecraft,pumpkin_seeds,16,0"}, Ref.MOD_ID + ",horse_food,1,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,golden_hoe,1,0"}, new String[]{B_COIN + ",24,0"}));
		
		profession.upgradeProfessionIDs = new String[]{"rancher"};
		profession.holdItems = new String[]{"minecraft,paper,1,0", "minecraft,golden_hoe,1,0" };

		this.professions.add(profession);

		//====== Butcher ======
		profession = new JsonProfession();
		profession.name = "butcher";
		profession.textureName = "butcher";
		profession.upgradeRequirements = new String[]{S_COIN + ",1,0", "minecraft,lead,1,0", "minecraft,iron_axe,1,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",12,0"}, "minecraft,porkchop,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",20,0"}, "minecraft,beef,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",18,0"}, "minecraft,mutton,3,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",10,0"}, "minecraft,chicken,2,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,iron_axe,1,0"}, new String[]{B_COIN + ",16,0"}));
		profession.quests.add(new JsonQuest(new String[]{B_COIN + ",20,0"}, new String[]{"minecraft,leather,2,0"}));
		
		profession.upgradeProfessionIDs = new String[]{"chef"};
		profession.holdItems = new String[]{"minecraft,iron_axe,1,0","minecraft,lead,1,0"};
		
		this.professions.add(profession);
				
		//====== florist ======
		profession = new JsonProfession();
		profession.name = "florist";
		profession.textureName = "florist";
		
		profession.upgradeRequirements = new String[]{S_COIN + ",2,0","minecraft,flower_pot,2,0", "minecraft,dirt,16,0"};
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",16,0"}, "minecraft,red_flower,2,*,8"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",24,0"}, "minecraft,red_flower,4,*,8"));
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",32,0"}, "minecraft,double_plant,4,4"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",32,0"}, "minecraft,double_plant,4,5"));
		
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_bonsai,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_heartmushroom,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_gardenia,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_hydrangeas,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_plumblossom,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_ranunculus,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_redrose,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{S_COIN + ",1,0"}, MxRef.MOD_ID + ",flower_rosyspiraea,1,0"));
		
		profession.quests.add(new JsonQuest(new String[]{"minecraft,flower_pot,1,0"}, new String[]{B_COIN + ",8,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,dirt,3,0"}, new String[]{B_COIN + ",5,0"}));
		profession.quests.add(new JsonQuest(new String[]{"minecraft,flower_pot,1,0", "minecraft,dirt,3,0"}, new String[]{MxRef.MOD_ID + ",flower_bonsai,1,0"}));
		
		profession.holdItems = new String[]{"minecraft,flower_pot,1,0", MxRef.MOD_ID + ",flower_gardenia,1,0", MxRef.MOD_ID + ",flower_redrose,1,0"};
	
		this.professions.add(profession);
				
		//====== fisherman ======
		profession = new JsonProfession();
		profession.name = "fisherman";
		profession.textureName = "fisherman";
		
		profession.upgradeRequirements = new String[]{S_COIN + ",2,0", "minecraft,fishing_rod,1,0"};
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",8,0"}, "minecraft,fish,3,*,3"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",14,0"}, "minecraft,fish,4,*,3"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",24,0"}, "minecraft,fishing_rod,1,0"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",64,0"}, "minecraft,fishing_rod,1,enchanted,1,61:1"));
		profession.tradingRecipes.add(new JsonTradingRecipe(new String[]{B_COIN + ",64,0"}, "minecraft,fishing_rod,1,enchanted,1,62:1"));
		
		profession.quests.add(new JsonQuest(new String[]{B_COIN + ",12,0"}, new String[]{"minecraft,fish,5,*,3"}));
		profession.quests.add(new JsonQuest(new String[]{B_COIN + ",50,0"}, new String[]{"minecraft,fishing_rod,1,enchanted,1,62:1"}));
		profession.quests.add(new JsonQuest(new String[]{B_COIN + ",50,0"}, new String[]{"minecraft,fishing_rod,1,enchanted,1,70:1"}));
		
		profession.upgradeProfessionIDs = new String[]{"sailor","pirate"};
		profession.holdItems = new String[]{"minecraft,fishing_rod,1,0", "minecraft,fish,1,0"};
		
		this.professions.add(profession);
				
	}
}
