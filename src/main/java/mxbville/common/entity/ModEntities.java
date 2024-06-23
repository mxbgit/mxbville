package mxbville.common.entity;

import mxbville.MxBville;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.util.MxRef;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	public static void init()
	{
		int id = 0;
		//villager
		EntityRegistry.registerModEntity(new ResourceLocation(MxRef.MOD_ID,"villager"), EntityMxVillager.class, "villager", id++, MxBville.instance, 80, 3, true, 0xFFFFFFFF, 0xFFFF6666);
		
	}

	public static void register(Class entityClass, String entityName, int id, int trackingRange, int updateFrequancy, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(MxRef.MOD_ID, entityName), entityClass, entityName, id, MxBville.instance, trackingRange, updateFrequancy, sendsVelocityUpdates);
	}
}
