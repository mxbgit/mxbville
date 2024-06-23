package mxbville.client.renderer;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;

public class LayerVillagerHeldItem extends LayerHeldItem{

	public LayerVillagerHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
		super(livingEntityRendererIn);
	}

	@Override
	public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_,
			float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
		
		//items should not be displayed in preview/upgradingGui
		if(((EntityMxVillager)entitylivingbaseIn).previewProfession == null){ 
			
			super.doRenderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_,scale);
		}
	}
}
