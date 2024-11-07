package mxbville.client.gui;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHelper {

	public static void drawNameAndProfession(FontRenderer fontRendererIn, EntityMxVillager villager, int x, int y){

		String villagerName = villager.getName();
		String professionName = I18n.format(villager.getProfession().getUnloalizedDisplayName());
		
		int villagerNameWidth = fontRendererIn.getStringWidth(villagerName);
		int spaceBetweenNames = 6;
		
		fontRendererIn.drawString(villagerName + ",", x, y, 0000000);
		fontRendererIn.drawString(professionName, x + villagerNameWidth + spaceBetweenNames, y, 6316128);
	}
		
	public static void drawCenteredStringNoshadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
	}
	
	public static boolean isPointInRegion(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x - 1 && mouseX < x + w + 1 && mouseY >= y - 1 && mouseY < y + h + 1;
	}
}
