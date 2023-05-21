package mxbville.client.gui.blockgui;

import mxbville.common.blocks.inventories.ContainerLetterStation;
import mxbville.util.MxRef;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiLetterStation extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 160;
    private static final ResourceLocation background = new ResourceLocation(MxRef.MOD_ID, "textures/gui/letterstation_gui.png");

	// Craft button
    // Letter part one (triggered on first item)
    // Letter part two (triggered on second part)
    // letter part gift (optional)
    
	public GuiLetterStation(InventoryPlayer parPlayerInventory, World parWorld, BlockPos parBlockpos) 
	{
		super(new ContainerLetterStation(parPlayerInventory, parWorld, parBlockpos));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString("Hello World", 28, 6, 4210752);
	}
	
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
