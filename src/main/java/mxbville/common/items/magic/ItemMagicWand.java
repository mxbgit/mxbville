package mxbville.common.items.magic;

import java.util.List;

import javax.annotation.Nullable;

import mxbville.common.items.ItemBase;
import mxbville.util.MxRef;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class ItemMagicWand extends ItemBase{

	public ItemMagicWand(String name) {
		super(name);
		setMaxStackSize(1);
	}

	/**
	 * Shoots a projectile in a straight line. The first block or entity hit is than passed
	 * to the method performMagicOnTarget.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			//TODO: Shoot a projectile in a a straight line
			// programm the projectile collision
		}
	
		return super.onItemRightClick(world, player, hand);
	}
	
	/**
	 * Accepts an Entity as an argument and performs Magic.
	 */
	public void performMagicOnTarget() {
		
	}
	
	/**
	 * Accepts a Block as an argument and performs Magic.
	 */
	public void performMagicOnTarget(Block targetBlock) {
		
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(MxRef.MOD_ID + ":magicwand." + "name" + ".item.info");
		tooltip.add(info);
	}
}
