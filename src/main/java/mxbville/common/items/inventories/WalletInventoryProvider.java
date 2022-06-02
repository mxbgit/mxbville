package mxbville.common.items.inventories;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class WalletInventoryProvider implements ICapabilitySerializable {

	private final ItemStackHandler inventory;

	public WalletInventoryProvider(int inventoryRows, int inventoryColumns) {
		inventory = new ItemStackHandler(inventoryRows * inventoryColumns);
	}
	
	@Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        //noinspection unchecked
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null, nbt);
    }

}
