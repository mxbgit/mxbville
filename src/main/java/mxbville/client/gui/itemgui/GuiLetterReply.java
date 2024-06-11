package mxbville.client.gui.itemgui;

import java.io.IOException;

import mxbville.client.gui.GuiTextButton;
import mxbville.common.gui.common.ContainerEmpty;
import mxbville.common.items.ModItems;
import mxbville.common.items.documents.ItemReplyMail;
import mxbville.util.MxRef;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiLetterReply extends GuiContainer{

	private static final ResourceLocation	mainLetterBackground = new ResourceLocation(MxRef.MOD_ID,"textures/gui/letter/letter_reply.png");
	private static final ResourceLocation 	ambushLetterBackground = new ResourceLocation(MxRef.MOD_ID,"textures/gui/letter/letter_ambush.png");
	private boolean 						isAmbushLetter;
	private EntityPlayer					playerRef;
	protected int 							xSize = 143;
	protected int 							ySize = 150; 
	
	private GuiTextButton 					buttonApprove;
	
	public GuiLetterReply(EntityPlayer player, Boolean isAmbushLetter) {
		super(new ContainerEmpty());
		this.playerRef = player;
		this.isAmbushLetter = isAmbushLetter;
	}

	@Override
	public void initGui() {
		super.initGui();
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.buttonList.add(this.buttonApprove = new GuiTextButton(this.mc, 0, 0,y + 130, I18n.format(MxRef.MOD_ID + ":gui.letter_reply.button.approve")));
        this.buttonApprove.x = this.width / 2 - this.buttonApprove.width / 2;
        this.buttonApprove.setColors(0xFF006400, 0xFF32CD32);
        this.buttonApprove.setShadow(false);
        this.buttonApprove.visible = true;
	}
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.isAmbushLetter) {
			this.mc.getTextureManager().bindTexture(ambushLetterBackground);
		}else {
			this.mc.getTextureManager().bindTexture(mainLetterBackground);
		}
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		GlStateManager.disableLighting();
        ItemStack holdItemStack = this.playerRef.getHeldItemMainhand();
        if(holdItemStack.getItem() == ModItems.LETTER_REPLY){
   	
        	String mailReplyGreeting	= I18n.format(MxRef.MOD_ID + ":gui.letter_reply.generic_greeting", this.playerRef.getName(),false);
        	String mailReplyText	 	= I18n.format(MxRef.MOD_ID + ":gui.letter_reply." + ItemReplyMail.getMailTextTranslationKey(holdItemStack));
        	String sender 				= I18n.format(MxRef.MOD_ID + ":gui.letter_reply.sender", ItemReplyMail.getMailSenderName(holdItemStack));
        	
        	this.fontRenderer.drawString(mailReplyGreeting, x + 20, y + 12, 0);
        	this.fontRenderer.drawSplitString(mailReplyText, x + 20, y + 28, this.xSize - 40, 0);
        	this.fontRenderer.drawString(sender, x + this.xSize - 20 - this.fontRenderer.getStringWidth(sender), y + 36 + 74, 0, false);
        }
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button == buttonApprove) {
			// magic
			this.mc.player.closeScreen();
		}
	}

}
