package mxbville.common.items.coins;

import java.util.List;

import javax.annotation.Nullable;

import mxbville.MxBville;
import mxbville.common.items.ItemBase;
import mxbville.common.items.inventories.WalletInventoryProvider;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemWallet extends ItemBase {
	
	private int inventoryRows;
	private int inventoryColumns;
	
	public ItemWallet(String name, int rows, int columns) {
		super(name);
		setMaxStackSize(1);
		this.setCreativeTab(MxBville.ITEMTAB);
		this.inventoryColumns = columns;
		this.inventoryRows 	  = rows;
	}

	public int getInventoryRows() {
		return this.inventoryRows;
	}
	
	public int getInventoryCols() {
		return this.inventoryColumns;
	}
	
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    	return new WalletInventoryProvider(inventoryRows, inventoryColumns);
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":wallet.item.info");
		tooltip.add(info);
	}

		
}
