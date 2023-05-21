package mxbville.client.gui.itemgui;

import java.io.IOException;

import mxbville.client.gui.GuiTextButton;
import mxbville.common.gui.common.ContainerEmpty;
import mxbville.common.items.ModItems;
import mxbville.common.items.documents.ItemReplyLetter;
import mxbville.common.network.ModNetwork;
import mxbville.common.network.message.villager.MessageSpawnNewVillagerThroughMail;
import mxbville.util.MxRef;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiMail extends GuiContainer{

	private static final ResourceLocation mailGuiTexture = new ResourceLocation(MxRef.MOD_ID,"textures/gui/mail/mail.png");
	private static final ResourceLocation lostLetterGuiTexture = new ResourceLocation(MxRef.MOD_ID,"textures/gui/mail/lost_letter_gui.png");
	private boolean isLostLetter;
	protected int xSize = 143;
	protected int ySize = 150; 
	
	private EntityPlayer player;
	
	private GuiTextButton buttonApprove;
	
	
	public GuiMail(EntityPlayer player) {
		super(new ContainerEmpty());
		this.player = player;
	}

	public void initGui() {
		super.initGui();
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.buttonList.add(this.buttonApprove = new GuiTextButton(this.mc, 0, 0,y + 130, I18n.format(MxRef.MOD_ID + ":gui.mail.button.approve")));
        this.buttonApprove.x = this.width / 2 - this.buttonApprove.width / 2;
        this.buttonApprove.setColors(0xFF006400, 0xFF32CD32);
        this.buttonApprove.setShadow(false);
        
        ItemStack hold = this.player.getHeldItemMainhand();
        if(hold.getItem() == ModItems.LETTER_REPLY){
        	int mailType = ItemReplyLetter.getMailType(hold);
        	this.buttonApprove.visible = (mailType == ItemReplyLetter.MailType_NewVillagerFemale || mailType == ItemReplyLetter.MailType_NewVillagerMale);
        } else if (this.isLostLetter) {
        	this.buttonApprove.visible = true;
        }
	}
	
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if (this.isLostLetter) {
			this.mc.getTextureManager().bindTexture(lostLetterGuiTexture);
		}else {
			this.mc.getTextureManager().bindTexture(mailGuiTexture);
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
        ItemStack hold = this.player.getHeldItemMainhand();
        if(hold.getItem() == ModItems.LETTER_REPLY){
        	String content = I18n.format(ItemReplyLetter.getMailContent(hold));
        	String sender = I18n.format(MxRef.MOD_ID + ":gui.mail.from", ItemReplyLetter.getMailSender(hold));
        	String hi = I18n.format(MxRef.MOD_ID + ":gui.mail.hi", this.player.getName(),false);
        	this.fontRenderer.drawString(hi, x + 20, y + 12, 0);
        	this.fontRenderer.drawSplitString(content, x + 20, y + 28, this.xSize - 40, 0);
        	this.fontRenderer.drawString(sender, x + this.xSize - 20 - this.fontRenderer.getStringWidth(sender), y + 36 + 74, 0, false);
        }
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if(button == buttonApprove){
			ModNetwork.getInstance().sendToServer(new MessageSpawnNewVillagerThroughMail());
			this.mc.player.closeScreen();
		}
	}
}
