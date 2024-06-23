package mxbville.common.village.data;

import java.util.ArrayList;

import mxbville.MxBville;
import mxbville.common.calc.math.IntBoundary;
import mxbville.util.MxRef;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class DataVillage extends WorldSavedData {
	
	private static final String key = MxRef.MOD_ID + ".datavillage";
	private World world;
	
	private ArrayList<HomeBoundary> homeList = new ArrayList<HomeBoundary>();
	
	public static DataVillage get(World world){
		//only for server
		if(world.isRemote)
			return null;
		
		DataVillage data = (DataVillage)world.getPerWorldStorage().getOrLoadData(DataVillage.class, key);
		if(data == null){
			//first time creating...
			data = new DataVillage(key);
			world.getPerWorldStorage().setData(key, data);
		}
		data.world = world;
		
		return data;
	}
	
	public DataVillage(String name) {
		super(name);
	}

	public boolean removeHome(String owner,IntBoundary bound){
		if(bound == null)
			return false;
		
		for(int i =0;i<homeList.size();i++){
			HomeBoundary hb = homeList.get(i);
			if(hb.boundary.equalTo(bound) && hb.owner.equals(owner)){
				homeList.remove(i);
				this.markDirty();
				return true;
			}
		}
		return true;
	}
	
	
	/**
	 * return != null means the new boundary has contacted with a old boundary
	 */
	public String addHome(String owner, IntBoundary bound){	
				
		if(!MxBville.MXCONFIG.oneVillagerPerRoom)
			return null;
		
		for(int i =0;i<homeList.size();i++){
			if(homeList.get(i).boundary.contact(bound)){
				return homeList.get(i).owner;
			}
		}
		homeList.add(new HomeBoundary(owner,bound));
		this.markDirty();
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		//home list
		NBTTagList taglist = nbt.getTagList("homes", NBT.TAG_COMPOUND);
		int count = taglist.tagCount();
		this.homeList.clear();
		for(int i =0;i<count;i++){
			this.homeList.add(new HomeBoundary(taglist.getCompoundTagAt(i)));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		//home list
		NBTTagList taglist = new NBTTagList();
		for(HomeBoundary homeBoundary : this.homeList){
			NBTTagCompound compound = new NBTTagCompound();
			homeBoundary.writeToNBT(compound);
			taglist.appendTag(compound);
		}
		nbt.setTag("homes", taglist);

		return nbt;
	}

	static class HomeBoundary{
		public String owner;
		public IntBoundary boundary;
		public HomeBoundary(String owner, IntBoundary boundary){
			this.owner = owner;
			this.boundary = boundary;
		}
		
		public HomeBoundary(NBTTagCompound nbt){
			this.readFromNBT(nbt);
		}
		
		public void writeToNBT(NBTTagCompound nbt){
			nbt.setString("owner", this.owner);
			nbt.setIntArray("bound", this.boundary.toArray());
		}
		
		public void readFromNBT(NBTTagCompound nbt){
			int[] arr = nbt.getIntArray("bound");
			this.boundary = new IntBoundary(arr);
			this.owner = nbt.getString("owner");
		}
	}
}
