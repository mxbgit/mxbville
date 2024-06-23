package mxbville.client.gui;

import mxbville.common.entity.villager.EntityMxVillager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHelper {

	public static void drawNameAndProfession(FontRenderer fontRendererIn, EntityMxVillager villager, int x, int y){
		// Quick Sphax GUI Fix:
		y -= 2;
		
		drawCenteredStringNoshadow(fontRendererIn, villager.getName(), x, y, 9999999); // Old Color: 6316128
        drawCenteredStringNoshadow(fontRendererIn, I18n.format(villager.getProfession().getUnloalizedDisplayName()), x, y + 10, 0000000); // Old Color: 8421504
	}
	
	public static void drawCenteredStringNoshadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
	}
	
	public static boolean isPointInRegion(int x, int y, int w, int h, int mouseX, int mouseY) {
        return mouseX >= x - 1 && mouseX < x + w + 1 && mouseY >= y - 1 && mouseY < y + h + 1;
	}
}
