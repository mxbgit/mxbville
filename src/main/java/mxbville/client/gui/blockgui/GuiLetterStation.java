package mxbville.client.gui.blockgui;

import mxbville.common.blocks.inventories.ContainerLetterStation;
import mxbville.util.MxRef;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.collection.script.Update;

public class GuiLetterStation extends GuiContainer {
	public static final int WIDTH = 175;
	public static final int HEIGHT = 166;
	private static final ResourceLocation background = new ResourceLocation(MxRef.MOD_ID, "textures/gui/letterstation_gui.png");
	private ContainerLetterStation ContainerLetterStationReference ;
	
	
	public GuiLetterStation(InventoryPlayer parPlayerInventory, World parWorld, BlockPos parBlockpos) 
	{
		super(new ContainerLetterStation(parPlayerInventory, parWorld, parBlockpos));
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

	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
        
        // update the letter-ui when an item is inserted
        // in three consecutive steps
        if (firstCraftingCondition())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 3, 0, 166, 80, 21);
        
        if (secondCraftingCondition())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 24, 0, 187, 80, 21);
        
        if (thirdCraftingCondition())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 45, 0, 208, 80, 21);
	}
	
	private boolean firstCraftingCondition() {
		return this.inventorySlots.getSlot(0).getHasStack();
	}
	
	private boolean secondCraftingCondition() {
		return (firstCraftingCondition()  && this.inventorySlots.getSlot(1).getHasStack());
	}
	
	private boolean thirdCraftingCondition() {
		return (secondCraftingCondition() && this.inventorySlots.getSlot(2).getHasStack());
	}
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    } 
}
