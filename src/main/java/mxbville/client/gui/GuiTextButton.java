package mxbville.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiTextButton extends GuiButton{
	
	private Minecraft mc;
	
	private int colorNormal = 0xFFFFFF;
	private int colorHover = 0xFFFF55;
	private boolean shadow = true;

	public GuiTextButton(Minecraft mc,int id, int x, int y, String text) {
		super(id, x, y, mc.fontRenderer.getStringWidth(text) , mc.fontRenderer.FONT_HEIGHT ,text);
		this.mc = mc;
	}
	
	public void setText(String text){
		this.displayString = text;
		this.width = this.mc.fontRenderer.getStringWidth(text);
	}
	
	public void setShadow(boolean shadow){
		this.shadow = shadow;
	}
	
	public void setColors(int normal, int hover){
		this.colorHover = hover;
		this.colorNormal = normal;
	}
	
	public boolean isMouseOn(){
		return this.hovered;
		
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		 if (this.visible) {
			 FontRenderer fontrenderer = mc.fontRenderer;
			 this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			 int k = this.getHoverState(this.hovered);
	         this.mouseDragged(mc, mouseX, mouseY);
	         int l = this.colorNormal;
	         
	         if (packedFGColour != 0) {
	                l = packedFGColour;
	         } else if (!this.enabled) {
	                l = 0xAAAAAA;
	         } else if (this.hovered) {
	                l = this.colorHover;
	         }
	         fontrenderer.drawString(this.displayString, this.x, this.y, l, this.shadow);
		 }
	}
}
