package mxbville.common.entity.villager;

import java.util.ArrayList;
import java.util.List;

import mxbville.MxBville;
import mxbville.common.calc.HouseDetector;
import mxbville.common.calc.math.IntBoundary;
import mxbville.common.calc.math.IntVec3;
import mxbville.common.calc.math.MxRand;
import mxbville.common.entity.ai.VillagerAILookAtInteractPlayer;
import mxbville.common.entity.ai.VillagerAIWander;
import mxbville.common.functions.PersonalityGenerator;
import mxbville.common.gui.GUIIDList;
import mxbville.common.items.ModItems;
import mxbville.common.village.data.DataVillage;
import mxbville.common.village.profession.Profession;
import mxbville.common.village.profession.Profession.TradingRecipeList;
import mxbville.common.village.trading.ITrading;
import mxbville.util.MxRef;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMxVillager extends EntityCreature implements ITrading {
	
	// Personality
	public static final DataParameter<String> VILLAGER_NAME = EntityDataManager.<String>createKey(EntityMxVillager.class, DataSerializers.STRING);
	public static final DataParameter<String> PROFESSIONID = EntityDataManager.<String>createKey(EntityMxVillager.class, DataSerializers.STRING);
	public static final DataParameter<String> AFFINITYID = EntityDataManager.<String>createKey(EntityMxVillager.class, DataSerializers.STRING);
	
	public static final DataParameter<String> PERSONALITY = EntityDataManager.<String>createKey(EntityMxVillager.class, DataSerializers.STRING);
	// 'True = male' and 'false = female'
	public static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BOOLEAN);
	// A number between 0 and 9
	public static final DataParameter<Integer> FACEVARIANT = EntityDataManager.<Integer>createKey(EntityMxVillager.class, DataSerializers.VARINT);
	public static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BLOCK_POS);

    public static final DataParameter<Boolean> IS_INTERACTING = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_FOLLOWING = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> IS_WAITING = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> HAS_HOME = EntityDataManager.createKey(EntityMxVillager.class, DataSerializers.BOOLEAN);
    // Current Quest Status
    public static final DataParameter<Integer> QUEST = EntityDataManager.<Integer>createKey(EntityMxVillager.class, DataSerializers.VARINT);
	
	//the player this villager is currently interacting with
	private EntityPlayer interactionTarget;
	private EntityPlayer followTarget;
	private Profession  profession;
	
	
	private IntBoundary home;
	
	//the center of wandering when no home has been set to this villager
	private Vec3d wanderCenter;	
	
	//the upgrading history. Holds profession name strings
	private List<String> upgradingHistory = new ArrayList<>();
	
	
	public EntityMxVillager(World worldIn) {
		this(worldIn, MxRand.get().nextBoolean());
				
		upgradingHistory.add("caveman");
	}
	
	public EntityMxVillager(World worldIn, boolean isMale) {
		this(worldIn, isMale?PersonalityGenerator.getRandomMaleName():PersonalityGenerator.getRandomFemaleName(),PersonalityGenerator.getRandomAffinity(), isMale);
	}
	
	public EntityMxVillager(World worldIn, String name, String affinityProfession, boolean isMale) {
		super(worldIn);
		
		set(VILLAGER_NAME,name);
		set(PERSONALITY, PersonalityGenerator.getRandomPersonality());
		set(AFFINITYID, affinityProfession);
		set(GENDER, isMale);
		set(FACEVARIANT, MxRand.get().nextInt(5));
		
		this.setSize(0.6F, 1.8F);
		upgradingHistory.add("caveman");
		if(!this.world.isRemote){
			this.setProfession("villager");
		}
		
		if(!this.hasCustomName()){
			this.setCustomNameTag(name);
		}
		
		this.initEntityAI();
	}
	
    public <T> T get(DataParameter<T> key) {
        return this.dataManager.get(key);
    }

    public <T> void set(DataParameter<T> key, T value) {
        this.dataManager.set(key, value);
    }
	
	@Override
	public EntityJumpHelper getJumpHelper() {
		return super.getJumpHelper();
	}

	@Override
	protected float getJumpUpwardsMotion() {
		return super.getJumpUpwardsMotion();
	}
	
	
	@Override
	protected void initEntityAI(){
		((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
     //   if (this.getProfession().getRegID() == 8) {
     //   	this.tasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
     //   	this.tasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
     //   }else {
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntitySkeleton.class, 8.0F, 0.6D, 0.6D));
     //  }
        this.tasks.addTask(1, new VillagerAILookAtInteractPlayer(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        //this.tasks.addTask(5, new VillagerAIFollowing(this,0.6F));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.3D));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new VillagerAIWander(this, 0.4D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));

	}

	@Override
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();

		this.getDataManager().register(VILLAGER_NAME, "");
		this.getDataManager().register(PROFESSIONID, "");
		this.getDataManager().register(AFFINITYID, "");
		this.getDataManager().register(PERSONALITY, "");
		this.getDataManager().register(GENDER, false);
		this.getDataManager().register(FACEVARIANT, Integer.valueOf(0));
		this.getDataManager().register(HOME_POS,BlockPos.ORIGIN);
		this.getDataManager().register(IS_FOLLOWING, false);
		this.getDataManager().register(IS_INTERACTING, false);
		this.getDataManager().register(IS_WAITING, false);
		this.getDataManager().register(HAS_HOME, false);
		//quest
		this.getDataManager().register(QUEST, Integer.valueOf(-1));
		
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(!player.world.isRemote){
			if (!this.isBusy(player)) {
				//interact with the villager
				// Interact with the Villager
				ItemStack playerHoldItemStack = player.inventory.getCurrentItem();
				if (!playerHoldItemStack.isEmpty() && player.getHeldItem(hand).getCount() > 0 ) 
				{
					// interact with player items
					if (playerHoldItemStack.getItem() == ModItems.RESET_SCROLL && this.downgrade()) {
						this.consumeItemFromStack(player,playerHoldItemStack);
					}else {
						player.openGui(MxBville.instance, GUIIDList.VILLAGER_MAIN, player.world, player.dimension, this.getEntityId(), 0);
					}	
				}else {
					//interact empty handed
					player.openGui(MxBville.instance, GUIIDList.VILLAGER_MAIN, player.world, player.dimension, this.getEntityId(), 0);
				}
				
			}else {
				player.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.isbusy"));
			}
		}
		return true;
	}
	
	protected void consumeItemFromStack(EntityPlayer player, ItemStack stack){
		if (!player.capabilities.isCreativeMode){
            stack.shrink(1);
        }
	}
	
	private boolean isBusy(EntityPlayer player) {
		return ((this.get(IS_INTERACTING) && this.interactionTarget.isEntityAlive() && this.interactionTarget != player) 
				|| (this.get(IS_FOLLOWING) && this.followTarget.isEntityAlive() && this.followTarget != player));
	}
	
	@Override
	public TradingRecipeList getTradingRecipeList() {
		return this.profession.getTradingRecipeList();
	}

	@Override
	public void onTrade() {
	}
	
	public List<String> getUpgradeHistory() {
		if (this.upgradingHistory == null || this.upgradingHistory.size() < 1) {
			return null;
		}else {
			return this.upgradingHistory;
		}
	}
	
	public void setUpgradeHistory(List<String> upgradeHistory) {
		if (upgradeHistory != null && upgradeHistory.size() > 0)
		{
			this.upgradingHistory.clear();
			for(int i=0;i<upgradeHistory.size();i++)
			{
				this.upgradingHistory.add(upgradeHistory.get(i));
			}
		}	
	}
	
	/**
	 * Toggles the interaction status of a villager.
	 * If given a EntityPlayer instance, the interaction is ON.
	 * If given NULL the interaction is OFF. 
	 * 
	 * @param playerRef Null or an EntityPlayer. 
	 */
	public void setInteracting(EntityPlayer playerRef) {
		if (!this.world.isRemote)
		{
			this.interactionTarget = playerRef;
			set(IS_INTERACTING, (this.interactionTarget != null));
		}
	}
	
	public EntityPlayer getInteractingPlayer() {
		return this.interactionTarget;
	}
	
	
	/**
	 * Toggles the following status of a villager.
	 * If given a EntityPlayer instance, the villager is following.
	 * If given NULL, the villager is not following. 
	 * 
	 * @param playerRef Null or an EntityPlayer. 
	 */
	public void setFollowing(EntityPlayer playerRef) {
		if (!this.world.isRemote) 
		{
			this.followTarget = playerRef;
			set(IS_FOLLOWING, (this.followTarget != null));
			this.wanderCenter = null;
		}
	}
	
	public EntityPlayer getFollowingPlayer() {
		return this.followTarget;
	}
	
	public Vec3d getWanderCenter(){
		if(this.wanderCenter == null){
			this.wanderCenter = new Vec3d(this.posX, this.posY, this.posZ);
		}
		return this.wanderCenter;
	}
	
	public void setCurrentPosAsHome(EntityPlayer playerRef) {
		// server side only
		if(this.world.isRemote)
			return;
		
		//TODO: Housedetector
		//scan home boundary
		IntBoundary potentialHomeBound = HouseDetector.getClosedField(this.world, new IntVec3(this.posX,this.posY,this.posZ));
		if(potentialHomeBound == null){
			//TODO: Respond with "OpenSpace"
		}else if (HouseDetector.hasBed(this.world, potentialHomeBound) == false)
		{
			//TODO: respond with "no bed"
		}else 
		{
			String oldOwner = DataVillage.get(this.world).addHome(this.getName(),potentialHomeBound);
			if (oldOwner != null)
			{
				//TODO: respond with "Home existed"
			}else {
				//remove old home
				if(this.home != null){
					DataVillage.get(this.world).removeHome(this.getName(),home);
				}
				//TODO: respond
				this.setFollowing(null);
				this.setHome(potentialHomeBound);
			}
		}
	}
	
	public void setHome(IntBoundary home) {
		this.home = home;
		set(HAS_HOME, true);
		BlockPos homePos = new BlockPos(home.getRandomPosInsideBoundary());
		set(HOME_POS, homePos);
	}
	
	public void moveOutHome(EntityPlayer player){
		if(this.home != null){
			DataVillage.get(this.world).removeHome(this.getName(),home);
			this.home = null;
			set(HAS_HOME, false);
			set(HOME_POS, BlockPos.ORIGIN);
			// Send Message message
			if (player != null) {
				player.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.home.moveout",this.getName()));
			}
			
		}
	}
	
	public IntBoundary getHome(){
		return this.home;
	}
	
	public Profession getProfession(){
		if(this.world.isRemote && (this.profession == null || get(PROFESSIONID) != this.profession.getRegID())){
			this.profession = Profession.registry.get(get(PROFESSIONID));
			this.refreshProfession();
		}
		return this.profession;
	}
	
	public void setProfession(String professionName){
		set(PROFESSIONID, professionName);
		this.refreshProfession();
	}
	
	public Profession[] getPersonalUpgradeOptions(){
		Profession 	currentProfession 	= this.getProfession();
		String 		affinity			= get(AFFINITYID);
		ArrayList<Profession> list = new ArrayList<Profession>();
		list = currentProfession.getUpgradeToNextOptions();
		
		if (currentProfession.getAffinityUpgradeOptions() != null && currentProfession.getAffinityUpgradeOptions().length > 0 ) {
			for (int i = 0; i < currentProfession.getAffinityUpgradeOptions().length; i++)
			{
				if (affinity == currentProfession.getAffinityUpgradeOptions()[i]) {
					Profession additionalUpgradeOption = Profession.registry.get(currentProfession.getAffinityUpgradeOptions()[i]);
					list.add(additionalUpgradeOption);
				}
			}
		}
		if(list.size() > 0)
		{
			return list.toArray(new Profession[list.size()]);
		}else {
			return null;
		}
	}
	
	public void upgrade(String profesisonName){
		if(!this.world.isRemote){
			String oldProfessionName = get(PROFESSIONID);
			this.upgradingHistory.add(oldProfessionName);
			this.setProfession(profesisonName);
			// Notify player 
			this.getServer().getPlayerList().sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.upgrade",this.getName(),oldProfessionName,profesisonName));
			//TODO
			//this.removeCurrentQuest();
		}
	}
	
	public boolean downgrade() {
		if(!this.world.isRemote && this.upgradingHistory.size() > 0) {
			String oldProfessionName = get(PROFESSIONID);
			String lastProfession = this.upgradingHistory.remove(this.upgradingHistory.size() - 1);
			this.setProfession(lastProfession);
			this.getServer().getPlayerList().sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.downgrade",this.getName(),oldProfessionName,lastProfession));
			//TODO
			//this.removeCurrentQuest();
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean dismiss(EntityPlayer player){
		if(!this.world.isRemote){
			this.moveOutHome(player);
			this.world.removeEntity(this);
			//player.sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.dismiss", player.getName(), this.getName(),new TextComponentTranslation(this.getProfession().getUnloalizedDisplayName())));
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getName(){
		return this.getCustomNameTag();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		// update profession
		if(this.world.isRemote && (this.profession == null || get(PROFESSIONID) != this.profession.getRegID())){
			this.setProfession(get(PROFESSIONID));
		}
		//TODO: Quests
		/*
		//update quest
		if(!this.world.isRemote){
			this.updateQuest();
		}
		*/
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		if(!this.world.isRemote){
			this.moveOutHome(null);
			this.world.removeEntity(this);
			this.getServer().getPlayerList().sendMessage(new TextComponentTranslation(MxRef.MOD_ID + ":message.villager.died" + MxRand.get().nextInt(5),this.getName()));
		}
	}
	
	@Override
	protected void dropEquipment(boolean p_82160_1_, int p_82160_2_){
		//don't drop any equipment
	}
	

	
	public void refreshProfession(){
		String professionName = get(PROFESSIONID);
		this.profession = Profession.registry.get(professionName);
		if(!this.world.isRemote){
			//clear both hands
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
			//get the item on either left hand or right hand
			this.setItemStackToSlot(MxRand.get().nextBoolean()?EntityEquipmentSlot.MAINHAND:EntityEquipmentSlot.OFFHAND, this.profession.getRandomHoldItem());
		}
	}
	
	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return false;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean getAlwaysRenderNameTagForRender()
    {
        return true;
    }
    
	@Override
	protected boolean canDespawn() {
		return false;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("villagername", get(VILLAGER_NAME));
		nbt.setString("professionid", get(PROFESSIONID));
		nbt.setString("affinityid", get(AFFINITYID));
		nbt.setString("personality", get(PERSONALITY));
		nbt.setBoolean("gender", get(GENDER));
		nbt.setInteger("facevariant", get(FACEVARIANT));
		nbt.setBoolean("iswaiting", get(IS_WAITING));
		if(this.home != null) {
			nbt.setIntArray("homebounds", new int[] {
				this.home.minx,
				this.home.miny,
				this.home.minz,
				this.home.maxx,
				this.home.maxy,
				this.home.maxz
			});
			nbt.setBoolean("hasHome", get(HAS_HOME));
			
			nbt.setInteger("homex", get(HOME_POS).getX());
			nbt.setInteger("homey", get(HOME_POS).getY());
			nbt.setInteger("homez", get(HOME_POS).getZ());
		}
		// convert String List into NBT format
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < upgradingHistory.size(); i++)
		{
			String historyElement = upgradingHistory.get(i);
			if (historyElement != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("history" + i, historyElement);
				tagList.appendTag(tag);
			}
		}
		nbt.setTag("upgradinghistory", tagList);
		// Quest related nbts
		//TODO
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		set(VILLAGER_NAME, nbt.getString("villagername"));
		set(PROFESSIONID, nbt.getString("professionid"));
		set(AFFINITYID, nbt.getString("affinityid"));
		set(PERSONALITY, nbt.getString("personality"));
		set(GENDER, nbt.getBoolean("gender"));
		set(FACEVARIANT, nbt.getInteger("facevariant"));
		set(IS_WAITING, nbt.getBoolean("iswaiting"));
		
		int[] homeBounds = nbt.getIntArray("homebounds");
		if(homeBounds == null || homeBounds.length == 0)
		{
			this.home = null;
			set(HAS_HOME, false);
		}else {
			this.setHome(new IntBoundary(homeBounds[0],homeBounds[1],homeBounds[2],homeBounds[3],homeBounds[4],homeBounds[5]));
			set(HOME_POS, new BlockPos(nbt.getInteger("homex"),nbt.getInteger("homey"),nbt.getInteger("homez")));
		}
		NBTTagList tagList = nbt.getTagList("upgradinghistory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			String historyElement = tag.getString("history" + i);
			this.upgradingHistory.add(i, historyElement);
			
		}
	}
	
	//----------------------------------
		//upgrading preview
		@SideOnly(Side.CLIENT)
		public Profession previewProfession;



}
