package mxbville.common.entity.ai;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.entity.ai.EntityAIBase;

public class VillagerAIFollowing extends EntityAIBase{

	 /** The child that is following its parent. */
	private EntityMxVillager villager;
	private float moveSpeed;
	private int delayCounter;
   
	private static final double maxDisSq = 144.0F;
	private static final double minDisSq = 9.0F;
		
	public VillagerAIFollowing(EntityMxVillager villager,float speed)
	{
		this.villager = villager;
		this.moveSpeed = speed;
	}
	
	@Override
	public boolean shouldExecute() {
		if (!this.villager.get(EntityMxVillager.IS_FOLLOWING)) 
		{ 
			return false; 
		}
		
		if (!this.villager.getFollowingPlayer().isEntityAlive()) 
		{
			this.villager.setFollowing(null);
			return false;
		}		
		
		if (!this.inFollowingDistance())
		{
			double d = this.villager.getDistanceSq(this.villager.getFollowingPlayer());
			if (d >= maxDisSq) {
				this.villager.setFollowing(null);
			}
			return false;
		}
		
		return true;
	}
	
	private boolean inFollowingDistance() {
		double d = this.villager.getDistanceSq(this.villager.getFollowingPlayer());
		return d >= minDisSq && d <= maxDisSq;
	}

	public boolean continueExecution(){
		if(!this.villager.isAirBorne)
		{
			return false;
		}else 
		{
			return this.inFollowingDistance();
		}
	}
	
	@Override
	public void startExecuting() {
		this.delayCounter = 0;
	}
	
	@Override
	public void updateTask() {

		if(--this.delayCounter <= 0)
		{
			this.delayCounter = 10;
			this.villager.getNavigator().tryMoveToEntityLiving(this.villager.getFollowingPlayer(), this.moveSpeed);
		}
	}

}
