package mxbville.common.items.documents;

import java.util.List;

import javax.annotation.Nullable;

import mxbville.common.items.ItemBase;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInvitation extends ItemBase {

	public ItemInvitation(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":invitation.item.info");
		tooltip.add(info);
	}

}