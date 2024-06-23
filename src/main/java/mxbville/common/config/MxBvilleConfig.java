package mxbville.common.config;

import mxbville.MxBville;
import mxbville.util.MxRef;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

import net.minecraftforge.fml.common.Loader;

public class MxBvilleConfig {
	
	public static Configuration config;
	private static final String configFileName = MxRef.MOD_ID + ".cfg";
	// Config Categories
	public static final String GENERAL	 	= "general";
	public static final String PROFESSIONS  = "mails";
	public static final String VILLAGERS 	= "villagers";
	
	
	//properties
	public boolean 	destroyBlocksDropCoins 	= true;		//true: destroy blocks can drop coins
	public boolean 	killMobsDropCoins		= true;		//true: kill mobs can drop coins
	public boolean  oneVillagerPerRoom		= true;		//true: Allows only one Villager within its found home bounds
	public boolean  freeUpgrading			= false;	//true: no item will be cunsumed on upgrading villagers
	public String[] bannedProfessionsList 	= new String[] {};
	public String[] affinityList 			= new String[] {"farmer","butcher","florist","fisherman","hunter","vintner","miller","blacksmith",
															"lumberjack","mason","miner","guard","tanner","weaver","healer","mystic","librarian",
															"painter","treasurehunter","bard","brewer","mayor","jeweler"};
	
	
	
    public void init(Configuration config) {
    	this.destroyBlocksDropCoins = config.getBoolean("destroyBlocksDropCoins", GENERAL, true, "Does destroying blocks drop coins?");
    	this.killMobsDropCoins 		= config.getBoolean("killMobsDropCoins", GENERAL, true, "Does killing mobs drop coins?");
    	this.oneVillagerPerRoom		= config.getBoolean("oneVillagerPerRoom", GENERAL, true, "Is more than one Vilager allowed in its home?");
    	this.freeUpgrading			= config.getBoolean("freeUpgrading",GENERAL,false,"If true, no item will be cunsumed on upgrading villagers");
    	this.bannedProfessionsList	= config.getStringList("bannedProfessionsList",PROFESSIONS, new String[] {},"Bann Professions by name");
    	this.affinityList			= config.getStringList("affinityList", PROFESSIONS, new String[] {
    																		"farmer","butcher","florist","fisherman","hunter","vintner","miller","blacksmith",
    																		"lumberjack","mason","miner","guard","tanner","weaver","healer","mystic","librarian",
    																		"painter","treasurehunter","bard","brewer","mayor","jeweler"}, 
    														"List of Professions, that are considered as affinity.");
    }
	
	public static void loadConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), configFileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                MxBville.LOGGER.warn("Could not create a new MxBVille config file.");
                MxBville.LOGGER.warn(e.getLocalizedMessage());
            }
        }
        config = new Configuration(configFile);
        config.load();
    }

    public static void syncConfig() {
    	MxBville.MXCONFIG.init(config);
        config.save();
    }

}
