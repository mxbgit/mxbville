package mxbville.common.items.documents;

import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.client.resources.I18n;
import mxbville.common.config.MxBvilleConfig;
import mxbville.common.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import scala.reflect.internal.Trees.CaseDef;

public class ItemInvitation extends ItemBase
{
	public 	String letterInfoString;
	private double successRate;
	
	public ItemInvitation(String name) {
		super(name);
		this.letterInfoString = this.getRegistryName() + ".item.info";
		this.successRate = MxBvilleConfig.invitationSuccess;
		this.setMaxStackSize(1);
	}
	
	
	public double getSuccessRate() {
		
		if (successRate < 0.0) { return 0.0; }
		else if (successRate > 0.8){return 0.8; }
		else {return successRate;}
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(letterInfoString);
		tooltip.add(info);
	}
}
