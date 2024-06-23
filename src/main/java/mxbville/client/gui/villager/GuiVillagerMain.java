package mxbville.client.gui.villager;

import java.io.IOException;
import java.util.ArrayList;

import mxbville.client.gui.GuiHelper;
import mxbville.common.calc.math.MxRand;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.common.gui.villager.ContainerVillagerMain;
import mxbville.common.network.ModNetwork;
import mxbville.common.network.messages.villager.MessageGuiSetInteracting;
import mxbville.util.MxRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiVillagerMain extends GuiContainer {
	
	private static final ResourceLocation VillagerMainGuiTexture = new ResourceLocation(MxRef.MOD_ID + ":textures/gui/villager/mainmenu.png");
	//protected int xSize = 176;
	//protected int ySize = 182;
    protected int xSize = 256;
    protected int ySize = 97;
    protected int guiYOffset = 85;
    
    protected int chatButtonOffsetY = 18;
    protected int chatButtonOffsetX = 188;
    protected int chatButtonHight = 15;
    protected int villagerNameOffsetY = 3;

    protected int offsetX = 12;

    TextButton buttonUpgrade;
    TextButton buttonTrade;
    TextButton buttonFollow;
    TextButton buttonWait;
    TextButton buttonHome;
 
    TextButton buttonChatAbout;
    TextButton buttonChatRumors;
    
    TextButton buttonChat;
    TextButton buttonAction;
    TextButton buttonBackChat;
    TextButton buttonBackAction;
    
    QuestButton buttonQuest;
    
    private String chatContent;
    private String chatContentDisplay;
    private ArrayList<String> chatStringList = new ArrayList<String>();
    
    private int chatDisplayInterval; // ms
    private int chatDisplayDurationTotal = 1000; // ms
    
    private boolean isFollowingStatusLast;
    private boolean hasHomeStatusLast;
    private boolean isWaitingStatusLast;
    
    // Keeps track of where we are in the menu right now
    // 0 = Main Menu
    // 1 = Chat Menu
    // 2 = Action Menu
    // >2 = Error, revert to 0
    private int subMenuIndex;
    
    private EntityPlayer player;
    private EntityMxVillager villager;
    
    private long lastNanotime;
    private long chatTimer;
    
	public GuiVillagerMain(EntityPlayer player, EntityMxVillager villager) {
		super(new ContainerVillagerMain());
		this.player = player;
        this.villager = villager;
        
        this.isFollowingStatusLast = this.villager.get(EntityMxVillager.IS_FOLLOWING);
        this.hasHomeStatusLast = this.villager.get(EntityMxVillager.HAS_HOME);
        this.isWaitingStatusLast = this.villager.get(EntityMxVillager.IS_WAITING);
        
        this.lastNanotime = System.nanoTime();
        
        this.subMenuIndex = 0;
        
        prepareStringList();
        refreshChatContent();
        
        setInteracting(true);
	}
	
    /**
     * Preloads all possible chat answers of the current villager
     */
    private void prepareStringList() {
    	    		
    	for(int i =0;i<3;i++){
    		chatStringList.add(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat.common" + i, player.getName()));
    	}
    	String home = villager.hasHome()?"hashome":"nohome";
    	for(int i =0;i<2;i++){
    		chatStringList.add(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat."+ home + i));
    	}
    	
    }
	
	@Override
	public void initGui() {
		super.initGui();
		   
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize + this.guiYOffset) / 2;  
        
        String strUpgrade = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.upgrade");
        String strTrade = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.trade");
        String strChat = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat");
        String strAction = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.action");
        String strBack = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.back");
        String strChatAbout = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat.about");
        String strChatRumors = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat.rumors");
        
        // 0. Button Position
        this.buttonList.add(buttonTrade = new TextButton(6, x + chatButtonOffsetX, y + chatButtonOffsetY + 0 * chatButtonHight, strTrade));   
        
        // 1st Button Position
        this.buttonList.add(buttonChat = new TextButton(0, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, strChat));     
        this.buttonList.add(buttonChatAbout = new TextButton(3, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, strChatAbout));     
        this.buttonList.add(buttonUpgrade = new TextButton(7, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, strUpgrade));
        
        // 2nd Button Position 
        this.buttonList.add(buttonAction = new TextButton(1, x + chatButtonOffsetX, y + chatButtonOffsetY + 2 * chatButtonHight, strAction));     
        this.buttonList.add(buttonChatRumors = new TextButton(4, x + chatButtonOffsetX, y + chatButtonOffsetY + 2 * chatButtonHight, strChatRumors));     
        this.buttonList.add(buttonHome = new TextButton(8, x + chatButtonOffsetX, y + chatButtonOffsetY + 2 * chatButtonHight, ""));
        
        // 3rd Button Position and following
        this.buttonList.add(buttonWait = new TextButton(2, x + chatButtonOffsetX, y + chatButtonOffsetY + 3 * chatButtonHight, ""));
        this.buttonList.add(buttonBackChat = new TextButton(5, x + chatButtonOffsetX, y + chatButtonOffsetY + 3 * chatButtonHight, strBack));
        this.buttonList.add(buttonFollow = new TextButton(9, x + chatButtonOffsetX, y + chatButtonOffsetY + 3 * chatButtonHight, ""));
  
        this.buttonList.add(buttonBackAction = new TextButton(10, x + chatButtonOffsetX, y + chatButtonOffsetY + 4 * chatButtonHight, strBack));
        
        this.buttonList.add(buttonQuest = new QuestButton(100,x + 190,y + 2));
        
        this.refreshButtons(); 
	}

	public void setInteracting(boolean toggleValue) {
		ModNetwork.getInstance().sendToServer(new MessageGuiSetInteracting(this.villager.getEntityId(), this.villager.dimension, toggleValue));
	}
	
	public boolean doesGuiPauseGame() {
        return false;
    }	
	
    private void disableButtons() {
    	buttonChat.enabled 			= false;
    	buttonAction.enabled 		= false;
    	buttonChatAbout.enabled 	= false;
    	buttonChatRumors.enabled 	= false;
    	buttonBackChat.enabled 		= false;
    	buttonTrade.enabled 		= false;
    	buttonUpgrade.enabled 		= false;
    	buttonHome.enabled 			= false;
    	buttonFollow.enabled		= false;
    	buttonWait.enabled 			= false;
    	buttonBackAction.enabled 	= false;
    }
	
    private void refreshButtons(){
    	String toggled_bt_text = this.villager.get(EntityMxVillager.IS_FOLLOWING)?"stop":"start";
    	buttonFollow.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.follow." + toggled_bt_text));
    	
    	toggled_bt_text = this.villager.get(EntityMxVillager.HAS_HOME)?"moveout":"movein";
    	buttonHome.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.home." + toggled_bt_text));
    	
    	toggled_bt_text = this.villager.get(EntityMxVillager.IS_WAITING)?"stop":"start";
    	buttonWait.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.wait." + toggled_bt_text));
    	
    	disableButtons();
    	switch (this.subMenuIndex) {
    	case 0:
    		/* Main Menu */
    		buttonChat.enabled		= true;
    		buttonAction.enabled 	= true;
        	buttonWait.enabled 		= true;
    		break;
    	case 1:
    		/* Chat Menu */
    		buttonChatAbout.enabled 	= true;
    		buttonChatRumors.enabled 	= true;
    		buttonBackChat.enabled 		= true;
    		break;
    	default:
    		break;
    	}
    	//TODO: quests
    	//buttonQuest.enabled = this.villager.hasHome() &&  (this.villager.getCurrentQuest() != null)
    }
    
    private void repositionButtons(boolean homeSet) {
    	int y_pos = (this.height - this.ySize + this.guiYOffset) / 2;
    	
    	int pos0 = y_pos + chatButtonOffsetY + 0 * chatButtonHight;
    	int pos1 = y_pos + chatButtonOffsetY + 1 * chatButtonHight;
    	int pos2 = y_pos + chatButtonOffsetY + 2 * chatButtonHight;
    	int pos3 = y_pos + chatButtonOffsetY + 3 * chatButtonHight;
    	int pos4 = y_pos + chatButtonOffsetY + 4 * chatButtonHight;
    	
    	if (homeSet) {
    		buttonTrade.y 		= pos0;
    		buttonUpgrade.y 	= pos1;
      		buttonHome.y 		= pos2;
	  		buttonFollow.y 		= pos3;
	  		buttonBackAction.y 	= pos4;
    	} else {
      		buttonHome.y 		= pos1;
	  		buttonFollow.y 		= pos2;
	  		buttonBackAction.y 	= pos3;
    	}
    	
    }
    
    private void calculateChatSpeed(){
    	int l = this.chatContent.length();
    	this.chatDisplayInterval = this.chatDisplayDurationTotal / l;
    }
    
    /**
     * Displays a random String form the chatStringList
     * in the villager chat
     */
    private void refreshChatContent(){
    	this.chatContent = chatStringList.get(MxRand.get().nextInt(chatStringList.size()));
    	this.chatContentDisplay = "";
    	
    	this.calculateChatSpeed();
    } 
    
    
    private void setChatContent(String type){
    	this.chatContent = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat." + type);
    	this.chatContentDisplay = "";
    	
    	this.calculateChatSpeed();
    }
    
    private void updateChatContent(){
    	boolean isFollowingCurrentState = this.villager.get(EntityMxVillager.IS_FOLLOWING);
    	boolean hasHomeCurrentState 	= this.villager.get(EntityMxVillager.HAS_HOME);
    	boolean isWaitingCurrentState 	= this.villager.get(EntityMxVillager.IS_WAITING);
    	
    	if(!this.isFollowingStatusLast && isFollowingCurrentState) setChatContent("followstart");
    	else if(this.isFollowingStatusLast && !isFollowingCurrentState) setChatContent("followstop");
    	
    	if(!this.isWaitingStatusLast && isWaitingCurrentState) setChatContent("waitstart");
    	else if(this.isWaitingStatusLast && !isWaitingCurrentState) setChatContent("waitstop");
    	  	
    	if(!this.hasHomeStatusLast && hasHomeCurrentState) setChatContent("movein");
    	else if(this.hasHomeStatusLast && !hasHomeCurrentState) setChatContent("moveout");
    }
    
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(VillagerMainGuiTexture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize + this.guiYOffset) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		
        //GuiHelper.drawNameAndProfession(this.mc.fontRenderer, villager, this.width / 2, y + villagerNameOffsetY);
        GuiHelper.drawNameAndProfession(this.mc.fontRenderer, villager, (this.width - this.xSize) / 2 + 43, y + villagerNameOffsetY);
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize + this.guiYOffset) / 2;
        
        long currentNanotime = System.nanoTime();
        //chat text animation
        if(this.chatContent.length() > this.chatContentDisplay.length()){
        	chatTimer += (currentNanotime - this.lastNanotime) / 1000000;
        	if(chatTimer >= this.chatDisplayInterval){
        		chatTimer -= this.chatDisplayInterval;
        		this.chatContentDisplay = this.chatContent.substring(0, this.chatContentDisplay.length() + 1);
        	}
        }
        
        this.lastNanotime = currentNanotime;
        this.fontRenderer.drawSplitString(this.chatContentDisplay,x + offsetX, y + 20, this.xSize - offsetX * 2, 0xF9ECD3);
        
        if (this.subMenuIndex == 2) {
            if(!this.buttonTrade.enabled){
        		this.drawButtonHoverText(this.buttonHome, mouseX, mouseY, 
        			I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.hint.title"), 
        			I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.hint.desc"));
            }
            
            if(!this.buttonUpgrade.enabled){
            	if(this.villager.hasHome()) {
            		this.drawButtonHoverText(this.buttonUpgrade, mouseX, mouseY, 
            				I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.maxupgrade.title"), 
            				I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.maxupgrade.desc"));
            	}
            }
        }

        if(this.buttonQuest.enabled){
    		this.drawButtonHoverText(this.buttonQuest, mouseX, mouseY, 
    				I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.newquest.title"), 
    				I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.newquest.desc"));
        }
	}
	
	private void drawButtonHoverText(GuiButton button, int mouseX, int mouseY, String title, String desc){	
		if(GuiHelper.isPointInRegion(button.x, button.y, button.width, button.height, mouseX, mouseY)){
			ArrayList<String> list = new ArrayList<String>();
			list.add(title);
			list.add(desc);
			this.drawHoveringText(list, mouseX, mouseY, this.fontRenderer);
		}
	}
    
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		//TODO: button handling
		super.actionPerformed(button);
	}
    
    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()){
			setInteracting(false);
		}
    }
    
    @Override
    public void updateScreen() {
    	super.updateScreen();
		this.updateChatContent();
		this.refreshButtons(); 
		
	    this.isFollowingStatusLast = this.villager.get(EntityMxVillager.IS_FOLLOWING);
	    this.hasHomeStatusLast = this.villager.get(EntityMxVillager.HAS_HOME);
	    this.isWaitingStatusLast = this.villager.get(EntityMxVillager.IS_WAITING);
    }
	
    @SideOnly(Side.CLIENT)
    static class QuestButton extends GuiButton {
		
        public QuestButton(int buttonID, int x, int y)
        {
            super(buttonID, x, y, 58, 10, "");
        }

  
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible && this.enabled)
            {
                mc.getTextureManager().bindTexture(VillagerMainGuiTexture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int x = 190;
                int y = 110;

                if (flag)
                {
                    y -= 12;
                }
                
                this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    static class TextButton extends GuiButton {
		
    	private int colorNormal = 0xFFFFFF;
    	private int colorHover = 0xFFFF55;
    	
        public TextButton(int buttonID, int x, int y, String str)
        {
            super(buttonID, x, y, 68, 13, str);
        }
        
    	public void setText(String text){
    		this.displayString = text;
    	}
  
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.enabled)
            {
            	FontRenderer fontrenderer = mc.fontRenderer;
                mc.getTextureManager().bindTexture(VillagerMainGuiTexture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int x = 182;
                int y = 122;

                if (flag)
                {
                    y += 16;
                }
                
                this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
                
                int j = this.colorNormal;

                if (packedFGColour != 0)
                {
                    j = packedFGColour;
                }
                else
                if (!this.enabled)
                {
                    j = 0xAAAAAA;
                }
                //else if (this.hovered)
                else if (flag)
                {
                    j = this.colorHover;
                }

                this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
            }
        }
    }
}
