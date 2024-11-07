package mxbville.client.gui.villager;

import java.io.IOException;
import java.util.ArrayList;

import mxbville.client.gui.GuiHelper;
import mxbville.common.calc.math.MxRand;
import mxbville.common.entity.villager.EntityMxVillager;
import mxbville.common.gui.villager.ContainerVillagerMain;
import mxbville.common.network.ModNetwork;
import mxbville.common.network.messages.villager.MessageGuiSetFollowing;
import mxbville.common.network.messages.villager.MessageGuiSetInteracting;
import mxbville.common.village.profession.Profession;
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
	private static final ResourceLocation ChatTopicGuiTexture = new ResourceLocation(MxRef.MOD_ID + ":textures/gui/villager/chat_topics.png");

	/**
	 *  Width of the menu picture in  <code>VillagerMainGuiTexture</code>
	 */
    protected int xSize = 256;
	/**
	 *  Height of the menu picture in  <code>VillagerMainGuiTexture</code>
	 */
    protected int ySize = 100;
    
	/**
	 *  Y-Startposition of the menu picture on the screen
	 */
    protected int guiYOffset = 85;
    
    /**
     * Y-Startposition of the chat button
     */
    protected int chatButtonOffsetY = 18;
    /**
     * X-Startposition of the chat button
     */
    protected int chatButtonOffsetX = 179;
    
    /**
     * Actual chatButton height spacing
     */
    protected int chatButtonHight = 15;
    
    /**
     * Y-Startposition of the villager name and profession text
     */
    protected int villagerNameOffsetY = 3;

    /**
     * X-Startposition of the villager name and profession text
     */
    protected int villagerNameOffsetX = 18;
    
    /**
     * X-Startposition of the villager chat text
     */
    protected int textOffsetX = 12;
    
    protected int topicPanelHeight = 165;
    
    protected int topicPanelWidth = 90;
    
    //private DialogueManager dialogues;
    
    TextButton buttonChat;
    TextButton buttonTrade;
    TextButton buttonAction;
    TextButton buttonProfession;
    TextButton buttonBackAction;
    
    TextButton buttonFollow;
    TextButton buttonWait;
    TextButton buttonSetHome;
    TextButton buttonGoHome;
    
    TextButton buttonUpgrade;
 	TextButton buttonOutfit;
    
    QuestButton buttonQuest;
   
    private String chatContent;
    private String chatContentDisplay;
    private ArrayList<String> chatStringList = new ArrayList<String>();
    
    private int chatDisplayInterval; // ms
    private int chatDisplayDurationTotal = 1000; // ms
    
    private boolean isFollowingStatusLast;
    private boolean hasHomeStatusLast;
    private boolean isWaitingStatusLast;
    private boolean chatTopicsVisible; 
  
    /**
     * Keeps track of the menu page, that is currently displayed.
     * 0 = Main Menu.
     * 1 = Chat Menu.
     * 2 = Action Menu.
     * >2 = Error, revert to 0.
     */
    private int subMenuIndex;
    
    private EntityPlayer player;
    private EntityMxVillager villager;
    
    /**
     * Both needed for the calculation of chat display speed
     */
    private long lastNanotime;
    private long chatTimer;
    
	public GuiVillagerMain(EntityPlayer player, EntityMxVillager villager) {
		super(new ContainerVillagerMain());
		this.player = player;
        this.villager = villager;
        
        this.isFollowingStatusLast = this.villager.get(EntityMxVillager.IS_FOLLOWING);
        this.hasHomeStatusLast = this.villager.get(EntityMxVillager.HAS_HOME);
        this.isWaitingStatusLast = this.villager.get(EntityMxVillager.IS_WAITING);
        this.chatTopicsVisible = false;
        
        this.lastNanotime = System.nanoTime();
        this.subMenuIndex = 0;
        
        //dialogues = new DialogueManager(this.player, this.villager); 
        prepareStringList();
        refreshChatContent();
        
        setInteracting(true);
	}
	
    /**
     * TODO: Check the current profession of the villager and chose the possible Strings accordingly.
     * Preloads all possible chat answers of the current villager
     */
    private void prepareStringList() {
        String professionString = villager.get(EntityMxVillager.PROFESSIONID);
        String personality      = villager.get(EntityMxVillager.PERSONALITY);
        String currentChatState = villager.getCurrentChatState();
        
        if (currentChatState.contains("home")) {
            
        }else {
            for(int i =0;i<4;i++){
                chatStringList.add(I18n.format(MxRef.MOD_ID 
                        + ":gui.villager."
                        + professionString 
                        + "." 
                        + personality
                        + "."
                        + i, player.getName()));
            }
        }
     
        /*

    	String home = villager.hasHome()?"hashome":"nohome";
    	for(int i =0;i<2;i++){
    		chatStringList.add(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat."+ home + i));
    	}
    	*/
    }
	
	@Override
	public void initGui() {
		super.initGui();
		   
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize + this.guiYOffset) / 2;  
        
        String strChat = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.chat");
        String strTrade = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.trade");
        String strAction = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.action");
        String strProfession = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.profession");
        String strBack = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.close");
        
        String strGoHome = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.gohome");
        
        String strUpgrade = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.upgrade");
        String strOutfit = I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.outfit");
        
        // Index 0: First Menu Screen
        this.buttonList.add(buttonChat = new TextButton(0, x + chatButtonOffsetX, y + chatButtonOffsetY + 0 * chatButtonHight, strChat)); 
        this.buttonList.add(buttonTrade = new TextButton(1, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, strTrade));   
        this.buttonList.add(buttonAction = new TextButton(2, x + chatButtonOffsetX, y + chatButtonOffsetY + 2 * chatButtonHight, strAction));
        this.buttonList.add(buttonProfession = new TextButton(3, x + chatButtonOffsetX, y + chatButtonOffsetY + 3 * chatButtonHight, strProfession));
        this.buttonList.add(buttonBackAction = new TextButton(4, x + chatButtonOffsetX, y + chatButtonOffsetY + 4 * chatButtonHight, strBack));
        
        // Index 1: Action Menu Screen 
        this.buttonList.add(buttonFollow = new TextButton(5, x + chatButtonOffsetX, y + chatButtonOffsetY + 0 * chatButtonHight, ""));
        this.buttonList.add(buttonWait = new TextButton(6, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, ""));
        this.buttonList.add(buttonSetHome = new TextButton(7, x + chatButtonOffsetX, y + chatButtonOffsetY + 2 * chatButtonHight, ""));
        this.buttonList.add(buttonGoHome = new TextButton(8, x + chatButtonOffsetX, y + chatButtonOffsetY + 3 * chatButtonHight, strGoHome));
        // Move Back Action button here (pos +4)  or on Position +3
        
        // Index 2: Profession Menu Screen 
        this.buttonList.add(buttonUpgrade = new TextButton(9, x + chatButtonOffsetX, y + chatButtonOffsetY + 0 * chatButtonHight, strUpgrade));
        this.buttonList.add(buttonOutfit = new TextButton(10, x + chatButtonOffsetX, y + chatButtonOffsetY + 1 * chatButtonHight, strOutfit));
	    // Move Back Action button here (pos +2)
	
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
    	buttonTrade.enabled 		= false;
    	buttonAction.enabled 		= false;
    	buttonProfession.enabled 	= false;
    	buttonBackAction.enabled 	= false;
    	
    	buttonFollow.enabled		= false;
    	buttonWait.enabled 			= false;
    	buttonSetHome.enabled 		= false;
    	buttonGoHome.enabled 		= false;
    	
    	buttonUpgrade.enabled 		= false;
    	buttonOutfit.enabled 		= false;
    }
	
    private void refreshButtons(){
    	
    	boolean hasHome = this.villager.get(EntityMxVillager.HAS_HOME);
    	
    	String toggled_bt_text = this.villager.get(EntityMxVillager.IS_FOLLOWING)?"stop":"start";
    	buttonFollow.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.follow." + toggled_bt_text));
    	
    	toggled_bt_text = hasHome?"moveout":"movein";
    	buttonSetHome.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.home." + toggled_bt_text));
    	
    	toggled_bt_text = this.villager.get(EntityMxVillager.IS_WAITING)?"stop":"start";
    	buttonWait.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu.wait." + toggled_bt_text));
    	
    	toggled_bt_text = this.subMenuIndex==0?"close":"back"; 
    	buttonBackAction.setText(I18n.format(MxRef.MOD_ID + ":gui.villagermain.menu." + toggled_bt_text));
    	
    	disableButtons();
    	switch (this.subMenuIndex) {
    	case 0:
    		/* Main Menu */
    		buttonChat.enabled			= true;
    		buttonAction.enabled 		= true;
    		buttonBackAction.enabled 	= true;
    		if(hasHome) {
    			buttonTrade.enabled			= true;
    			buttonProfession.enabled	= true;
    		}
    		break;
    	case 1:
    		/* Action Menu */
    		buttonFollow.enabled		= true;
        	buttonWait.enabled 			= true;
        	buttonSetHome.enabled 		= true;
        	if(hasHome) {
        		buttonGoHome.enabled 	= true;
        	}
        	buttonBackAction.enabled 	= true;
    		break;
    	case 2:
    		/* Profession Menu */
    		Profession[] upgradeOptions = this.villager.getPersonalUpgradeOptions();
    		
    		buttonUpgrade.enabled	= hasHome && (upgradeOptions != null && upgradeOptions.length > 0);
        	buttonOutfit.enabled	= true;
        	buttonBackAction.enabled 	= true;
    		break;
    	default:
    		break;
    	}
    	repositionButtons(hasHome);
    	//TODO: quests
    	buttonQuest.enabled = false;
    	//buttonQuest.enabled = this.villager.hasHome() &&  (this.villager.getCurrentQuest() != null);
    }
    
    private void repositionButtons(boolean homeSet) {
    	int y_pos = (this.height - this.ySize + this.guiYOffset) / 2;
    	
    	int pos0 = y_pos + chatButtonOffsetY + 0 * chatButtonHight;
    	int pos1 = y_pos + chatButtonOffsetY + 1 * chatButtonHight;
    	int pos2 = y_pos + chatButtonOffsetY + 2 * chatButtonHight;
    	int pos3 = y_pos + chatButtonOffsetY + 3 * chatButtonHight;
    	int pos4 = y_pos + chatButtonOffsetY + 4 * chatButtonHight;
    	
    	if (homeSet) {
    		switch (this.subMenuIndex) {
	        	case 0: 
	        		buttonChat.y		= pos0;
	        		buttonTrade.y 		= pos1;
	        		buttonAction.y 		= pos2;
	        		buttonProfession.y	= pos3;
	        		buttonBackAction.y 	= pos4;
	        		break;
	        	case 1: 
	        		buttonFollow.y 		= pos0;
	        		buttonWait.y 		= pos1;
	        		buttonSetHome.y 	= pos2;
	        		buttonGoHome.y 		= pos3;
	        		buttonBackAction.y 	= pos4;
	        		break;
	        	case 2: 
	        		buttonUpgrade.y 	= pos0;
	        		buttonOutfit.y 		= pos1;
	        		buttonBackAction.y 	= pos2;
	        		break;
	    		default: break;
    		}
    	} else {
    		switch (this.subMenuIndex) {
        	case 0: 
        		buttonChat.y		= pos0;
        	    buttonAction.y 		= pos1;
        		buttonBackAction.y 	= pos2;
        		break;
        	case 1: 
        		buttonFollow.y 		= pos0;
        		buttonWait.y 		= pos1;
        		buttonSetHome.y 	= pos2;
        		buttonBackAction.y 	= pos3;
        		break;
        	case 2: 
        		buttonUpgrade.y 	= pos0;
        		buttonOutfit.y 		= pos1;
        		buttonBackAction.y 	= pos2;
        		break;
    		default: break;
		}
    	}
    	
    }
    
    private void calculateChatSpeed(){
    	int l = this.chatContent.length();
    	this.chatDisplayInterval = this.chatDisplayDurationTotal / l;
    }
    
    /**
     * Assigns a randomly selected String from chatStringList as 
     * the current message displayed in the villager chat
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
        
        if (chatTopicsVisible) {
        	this.mc.getTextureManager().bindTexture(ChatTopicGuiTexture);
        	 int xTopics = (this.width + this.xSize) / 2 - 9;
        	 int yTopics = (this.height - this.topicPanelHeight) / 2 + 9;
        	 this.drawTexturedModalRect(xTopics, yTopics, 0, 0, this.topicPanelWidth, this.topicPanelHeight);
        }
		
        GuiHelper.drawNameAndProfession(this.mc.fontRenderer, villager, x + villagerNameOffsetX , y + villagerNameOffsetY);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.disableLighting();
		
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize + this.guiYOffset) / 2;
        
        this.animateChatString(x, y);
        this.drawCurrentButtonTexts(mouseX, mouseY);
	}
	
	/**
	 * Displays a String in the villager's chat box, 
	 * revealing it letter by letter at a speed calculated based on its length.
	 */
	private void animateChatString(int x, int y) {
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
        this.fontRenderer.drawSplitString(this.chatContentDisplay,x + textOffsetX, y + 20, this.xSize - textOffsetX * 2, 0xF9ECD3);
	}
	
	private void drawCurrentButtonTexts(int mouseX, int mouseY) {
	    if (this.subMenuIndex == 1) {
            if(!this.buttonGoHome.enabled){
                this.drawButtonHoverText(this.buttonSetHome, mouseX, mouseY, 
                    I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.hint.title"), 
                    I18n.format(MxRef.MOD_ID + ":gui.villagermain.button.hint.desc"));
            }
        }
        
        if (this.subMenuIndex == 2) {
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

		if (button == buttonBackAction)  { this.pressedBackAction(); }
		else if (button == buttonAction) { this.pressedAction(); }
		else if (button == buttonChat)	{ this.pressedChat(); }
		else if (button == buttonFollow){ this.pressedFollow(); }
		//TODO: expand button handling
		super.actionPerformed(button);
	}
	
	private void pressedBackAction() {
		if (subMenuIndex == 0) {
			setInteracting(false);
			this.mc.player.closeScreen();
		}else {
			subMenuIndex--;
			refreshButtons();
		}
	}
	
	private void pressedChat() {
		chatTopicsVisible = !chatTopicsVisible;
		//TODO: change the chat message
		//TODO: display chat Topics as textbuttons
	}
	private void pressedAction() {
		subMenuIndex = 1;
		chatTopicsVisible = false;
		refreshButtons();
	}
	private void pressedTopic() {
		
	}
	
	private void pressedFollow() {
		// toggle follow status
		boolean enable = !this.villager.get(EntityMxVillager.IS_FOLLOWING);
		// deactivate waiting
		// send package to server
		ModNetwork.getInstance().sendToServer(new MessageGuiSetFollowing(this.villager.getEntityId(), this.villager.dimension, enable));
	}
	private void pressedWait() {
		
	}
	private void pressedTrade() {
		
	}
	private void pressedSetHome() {
		
	}
	private void pressedGoHome() {
		
	}
	private void pressedUpgrade() {
		
	}
	private void pressedQuest() {
		
	}
	private void pressedProfession() {
		
	}
    
    @Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		// Escape Key or the bound inventory key
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
                int x = 190; // Where are the button pixels on the background png
                int y = 115; // Where are the button pixels on the background png

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
                int x = 182; // Where are the button pixels on the background png
                int y = 127; // Where are the button pixels on the background png

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
