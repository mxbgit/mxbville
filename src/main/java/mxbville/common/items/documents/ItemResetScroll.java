package mxbville.common.items.documents;

import java.util.List;

import javax.annotation.Nullable;
import mxbville.common.items.ItemBase;
import mxbville.util.MxRef;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemResetScroll extends ItemBase {
	
	public ItemResetScroll(String name) {
		super(name);
		this.setMaxStackSize(16);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
	
		return super.onItemRightClick(worldIn, playerIn, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":reset_scroll.item.info");
		tooltip.add(info);
	}
}
