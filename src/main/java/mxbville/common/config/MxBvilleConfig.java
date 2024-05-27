package mxbville.common.config;

import mxbville.util.MxRef;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class MxBvilleConfig {
	

	private static final String configFileName = MxRef.MOD_ID + ".cfg";
	//properties
	public static boolean 	destroyBlocksDropCoins;		//ture: destroy blocks can drop coins
	public static boolean 	killMobsDropCoins;			//true: kill mobs can drop coins
	public static double 	invitationSuccess;			//0.75 Success means no ambush
	public static double	baitSuccess;				//0.10 Success means no ambush

	public static void load(File dir){
		Configuration conf = new Configuration(new File(dir, configFileName), MxRef.VERSION);
		Property pt = null;
		
		conf.load();
		
		//coin earning options
		pt = conf.get(Configuration.CATEGORY_GENERAL, "DestroyBlocksDropCoins", true);
		pt.setComment("Does destroying blocks drop coins? (default: true)");
		destroyBlocksDropCoins = pt.getBoolean();
		
		pt = conf.get(Configuration.CATEGORY_GENERAL, "KillMobsDropCoins", false);
		pt.setComment("Does killing mobs drop coins? (default: false)");
		killMobsDropCoins = pt.getBoolean();
		
		pt = conf.get(Configuration.CATEGORY_GENERAL, "invitationSuccess", 0.50);
		pt.setComment("Must be between 0.0 and 0.8. Success rate: default 55 | ambush rate 25 | failure rate 20 )");
		
		pt = conf.get(Configuration.CATEGORY_GENERAL, "baitSuccess", 0.70);
		pt.setComment("Must be between 0.0 and 0.8. Success rate: default 10 | ambush rate 70 | failure rate 20 )");
		
		conf.save();
	}
}
