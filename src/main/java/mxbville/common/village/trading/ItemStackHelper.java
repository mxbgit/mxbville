package mxbville.common.village.trading;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemStackHelper {

	public static boolean match(ItemStack[] items, ItemStack[] inputs, int size){
		return match(items, inputs, size, false);
	}
	
	public static boolean consume(ItemStack[] items, ItemStack[] inputs, int size){
		return match(items, inputs, size, true);
	}
	
	private static boolean match(ItemStack[] items,ItemStack[] inputs, int size, boolean consume){
		
		int[] tempStackSizes = null;
		if(consume){
			tempStackSizes = new int[items.length];
		}
		
		ArrayList<ItemStack> waitForMatching = new ArrayList<ItemStack>();
		if(inputs != null){
			for(int i = 0;i<inputs.length;i++){
				if(!inputs[i].isEmpty()){
					waitForMatching.add(inputs[i]);
				}
			}
		}
		boolean found;
		ItemStack item,target;
		for(int i =0;i<size;i++){
			//find item in inputs
			found = false;
			item = items[i];
			if(item.isEmpty())
				continue;
			for(int j = 0;j < waitForMatching.size();j++){
				target = waitForMatching.get(j);
				// Old obfuscated methods for getCount were func_190916_E()
				if(item.isItemEqual(target) && item.getCount() >= target.getCount()){
					found = true;
					if(consume){
						tempStackSizes[i] = item.getCount() - target.getCount();
					}
					waitForMatching.remove(j);
					break;
				}
			}
			if(!found){
				return false;
			}
		}
		
		if(waitForMatching.size() > 0)
			return false;

		// Consume Item Logic 
		// in a boolean function !!!
		if(consume){
			for(int i =0;i<items.length;i++){
				if(items != null){
					if(tempStackSizes[i] == 0){
						items[i] = ItemStack.EMPTY;
					}
					else{
						// Old obfuscated method was func_190920_e(int)
						items[i].setCount(tempStackSizes[i]);
					}
				}
			}
		}
		
		return true;
		
	}
}
