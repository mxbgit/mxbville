package mxbville.common.entity.ai;

import mxbville.common.calc.math.MxRand;
import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class VillagerAIWander extends EntityAIBase {

	private EntityMxVillager villager;
    private double speed;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int executionChance;

    public VillagerAIWander(EntityMxVillager villagerIn, double speedIn)
    {
        this(villagerIn, speedIn, 50);
    }
    
    public VillagerAIWander(EntityMxVillager villagerIn, double speedIn, int chance)
    {
        this.villager = villagerIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(1);
    }
	    
    public boolean shouldExecute()
    {

        if (this.villager.getRNG().nextInt(this.executionChance) != 0)
        {
            return false;
        }
        
        if(isCurrentlyBusy()){
        	return false;
        }
        
        Vec3d vec3 = null;
        
        if(this.villager.getHome() == null){
        	vec3 = new Vec3d(
        			this.villager.getWanderCenter().x + MxRand.get().nextDouble() * 6.0D - 3.0D,
        			this.villager.getWanderCenter().y,
        			this.villager.getWanderCenter().z + MxRand.get().nextDouble() * 6.0D - 3.0D
        			);
        }
        else{
            if(this.villager.world.isDaytime() && !this.villager.world.isRaining()){
            	//if now is day time and not raining, the villager will randomly walking near home
            	vec3 = this.villager.getHome().extend(7,2,7).getRandomPosInsideBoundary();
            }
            else{
            	//if is raining or at night, the villager will stay at home
            	vec3 = this.villager.getHome().getRandomPosInsideBoundary();
            }
        }
        
        
        if (vec3 == null)
        {
            return false;
        }
        else
        {
            this.xPosition = vec3.x;
            this.yPosition = vec3.y;
            this.zPosition = vec3.z;
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
    	if(isCurrentlyBusy()){
        	return false;
        }
    
        return !this.villager.getNavigator().noPath();
    }
    
    @Override
	public void resetTask() {
		this.villager.getNavigator().clearPath();
	}


    @Override
    public void startExecuting()
    {
    	this.villager.getNavigator().tryMoveToXYZ(this.xPosition , this.yPosition, this.zPosition, this.speed);
    }
    
    private boolean isCurrentlyBusy() {
        if(	this.villager.get(EntityMxVillager.IS_FOLLOWING)
        	|| this.villager.get(EntityMxVillager.IS_INTERACTING)
        	|| this.villager.get(EntityMxVillager.IS_WAITING) )
        {
        	return true;
        }else {
        	return false;
        }   
    }
}
