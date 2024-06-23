package mxbville.client.renderer;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderFactoryVillager implements IRenderFactory<EntityMxVillager>{
	@Override
	public Render<? super EntityMxVillager> createRenderFor(RenderManager manager) {
		return new RenderVillager(manager);
	}

}
