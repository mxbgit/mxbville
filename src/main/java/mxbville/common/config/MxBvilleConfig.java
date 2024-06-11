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
	public static final String GENERAL	 = "general";
	public static final String MAILS 	 = "mails";
	public static final String VILLAGERS = "villagers";
	
	
	//properties
	public boolean 	destroyBlocksDropCoins 	= true;		//true: destroy blocks can drop coins
	public boolean 	killMobsDropCoins		= true;		//true: kill mobs can drop coins
	public String[] affinityList 			= new String[] {"farmer","butcher","florist","fisherman","hunter","vintner","miller","blacksmith",
															"lumberjack","mason","miner","guard","tanner","weaver","healer","mystic","librarian",
															"painter","treasurehunter","bard","brewer","mayor","jeweler"};
	
	
    public void init(Configuration config) {
    	this.destroyBlocksDropCoins = config.getBoolean("Does destroying blocks drop coins", GENERAL, true, "Whether destroying Blocks lead to coindrops.");
    	this.killMobsDropCoins 		= config.getBoolean("Does killing mobs drop coins", GENERAL, true, "Whether killing mobs drop coins.");
    	this.affinityList			= config.getStringList("List of Professions, that are considered as affinity", MAILS, new String[] {
    																		"farmer","butcher","florist","fisherman","hunter","vintner","miller","blacksmith",
    																		"lumberjack","mason","miner","guard","tanner","weaver","healer","mystic","librarian",
    																		"painter","treasurehunter","bard","brewer","mayor","jeweler"}, 
    														"These professions are considered as affinity professions. Certain Villager professions can only be unlocked if the villager has the right affinity");
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
