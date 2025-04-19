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
	public static final int HEIGHT = 166;
	private static final ResourceLocation background = new ResourceLocation(MxRef.MOD_ID, "textures/gui/letterstation_gui.png");
	private boolean markedSlot_0 = false;
	private boolean markedSlot_1 = false;
	private boolean markedSlot_2 = false;
	
	public GuiLetterStation(InventoryPlayer parPlayerInventory, World parWorld, BlockPos parBlockpos) 
	{
		super(new ContainerLetterStation(parPlayerInventory, parWorld, parBlockpos));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
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
        this.markSlotIfFilled();
        // update the letter-ui when an item is inserted
        // in three consecutive steps
        if (oneSlotFilled())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 3, 0, 166, 80, 21);
        
        if (twoSlotsFilled())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 24, 0, 187, 80, 21);
        
        if (threeSlotsFilled())
        	drawTexturedModalRect(guiLeft + 54, guiTop + 45, 0, 208, 80, 21);
	}

	private void markSlotIfFilled() {
		this.markedSlot_0 = (this.inventorySlots.getSlot(0).getHasStack())?true:false;
		this.markedSlot_1 = (this.inventorySlots.getSlot(1).getHasStack())?true:false;
		this.markedSlot_2 = (this.inventorySlots.getSlot(2).getHasStack())?true:false;
	}
	
	private boolean oneSlotFilled() {
		return (markedSlot_0 || markedSlot_1 || markedSlot_2 ); 
	}
	
	private boolean twoSlotsFilled() {
		return ((markedSlot_2 && (markedSlot_0 || markedSlot_1)) || (markedSlot_0 && markedSlot_1)); 
	}
	
	private boolean threeSlotsFilled() {
		return (markedSlot_0 && markedSlot_1 && markedSlot_2 );
	}
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    } 
}
