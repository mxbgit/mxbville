package mxbville.client.gui;

import mxbville.util.MxRef;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiWalletSmall extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 160;
    // Location of the gui
    private static final ResourceLocation background = new ResourceLocation(MxRef.MOD_ID, "textures/gui/wallet/wallet_small_gui.png");

	public GuiWalletSmall(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}

	// Called to draw the gui
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }
    
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
