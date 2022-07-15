package mxbville.common.items.food;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHorseSugar extends ItemMxBHorseFood {

	public ItemHorseSugar() {
		super("sugar_lumps");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String info = I18n.format(this.getRegistryName() + ".item.info");
		tooltip.add(info);
	}
	
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        return super.itemInteractionForEntity(stack, player, entity, hand);
    }
    

    @Override
    public boolean changeAttributes(EntityLivingBase entity, EntityPlayer playerEntity) {
    	//Add 0.003D to the speed?
        //The game generates a random number to the speed starting at 0.45D
        //Limit the speed at 14.5D
        double currentSpeed = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
        double newSpeed = currentSpeed + + 0.003D;
        if ((newSpeed * 43) < 14.5D) {
        	//success
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(newSpeed);
            entity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return true;
        } else {
        	//failure
        	entity.playSound(SoundEvents.ENTITY_HORSE_ANGRY, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return false;
        }
    }
}