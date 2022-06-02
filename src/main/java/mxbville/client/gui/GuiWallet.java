package mxbville.client.gui;

import mxbville.common.gui.GUIIDList;
import mxbville.util.MxRef;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiWallet extends GuiContainer {
    public static final int WIDTH = 175;
    public static final int HEIGHT = 160;
    
    private static final ResourceLocation backgroundSmall = new ResourceLocation(MxRef.MOD_ID, "textures/gui/wallet/wallet_small_gui.png");
    private static final ResourceLocation backgroundLarge = new ResourceLocation(MxRef.MOD_ID, "textures/gui/wallet/wallet_large_gui.png");

    private int guiID;
    
	public GuiWallet(Container inventorySlotsIn, int guiID) {
		super(inventorySlotsIn);
		this.guiID = guiID;
	}

	// Called to draw the gui
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	if (this.guiID == GUIIDList.WALLET_SMALL) {
    		mc.getTextureManager().bindTexture(backgroundSmall);
    	} else {
    		mc.getTextureManager().bindTexture(backgroundLarge);
    	}
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
    }
    
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
