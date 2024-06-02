package mxbville.common.items.documents;

import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.client.resources.I18n;
import mxbville.common.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInvitation extends ItemBase
{
	protected InvitationType type;
	
	public ItemInvitation(String name, InvitationType type) {
		super(name + "_" + type.name().toLowerCase());
		this.type = type;
		this.setMaxStackSize(1);
	}
	
	
	public InvitationType getType() {
		return this.type;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(this.getRegistryName() + ".item.info");
		tooltip.add(info);
	}
}
