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

public class ItemHorseCorn extends ItemMxBHorseFood {

	public ItemHorseCorn() {
		super("corn_horse");
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
        //Each heart = 2.0D
        //Each horse can have 60.0D (30 hearts)
        //Each Corn gives + 2.0D. Can be made configurable  	
        double currentMaxHealth = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
        if (currentMaxHealth < 60.0D) {
        	//success
            entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(currentMaxHealth + 2.0D);
            entity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return true;
        } else {
        	//failure
        	entity.playSound(SoundEvents.ENTITY_HORSE_ANGRY, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return false;
        }
    }
}
