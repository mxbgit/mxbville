package mxbville.common.entity.ai;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class VillagerAILookAtInteractPlayer extends EntityAIWatchClosest {
	private final EntityMxVillager villager;

    public VillagerAILookAtInteractPlayer(EntityMxVillager villager)
    {
        super(villager, EntityPlayer.class, 8.0F);
        this.villager = villager;
    }
    
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villager.get(EntityMxVillager.IS_INTERACTING) )
        {
            this.closestEntity = this.villager.getInteractingPlayer();
            return true;
        }
        else
        {
            return false;
        }
}
}
