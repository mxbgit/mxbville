package mxbville.common.items.food;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHorseAppleSlice extends ItemMxBHorseFood {

	public ItemHorseAppleSlice() {
		super("apple_slice");
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
    	//One slice gives 0.1D
        //Max could be 2.0D but, after 1.0D the horse starts to losing health
        //1.0D = 7 blocks
        //The only value that will be configurable is the amount that the carrot modifies the attribute
    	double currentJump = entity.getAttributeMap().getAttributeInstanceByName("horse.jumpStrength").getAttributeValue();
        if (currentJump != 0 && currentJump < 1.4D) {
        	//success
            entity.getAttributeMap().getAttributeInstanceByName("horse.jumpStrength").setBaseValue(currentJump + 0.1D);
            entity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return true;
        } else {
        	//failure
        	entity.playSound(SoundEvents.ENTITY_HORSE_ANGRY, SoundType.ANVIL.getVolume() * 0.6F, SoundType.ANVIL.getPitch());
            return false;
        }
    }
}