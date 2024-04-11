package mxbville.common.commands;

import java.util.List;

import com.google.common.collect.Lists;

import mxbville.common.player.ExtendedPlayerProperties;
import mxbville.util.MxRef;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandSetTreasureHuntLevel extends CommandBase {

	private final List<String> aliases = Lists.newArrayList(MxRef.MOD_ID, "mx_setcoindrop", "mx_treasurehuntlevel", "mx_settreasurehuntlevel");
	
	@Override
	public String getName() {
		
		return "mx_settreasurehuntlevel";
	}

	@Override
	public String getUsage(ICommandSender sender) {

		return "mx_settreasurehuntlevel <level>";
	}
	
	@Override
	public List<String> getAliases() {
		
		return aliases;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
		if (args.length < 1) return;
		
		int treasurehuntLevel = 0;
		String argument = args[0];
		
		try {
			treasurehuntLevel = Integer.parseInt(argument);
			
		}catch (NumberFormatException e) {
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid Treasurehunt Level Number! Try 0 to 3"));
			return;
		}
		
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender; 
			
			ExtendedPlayerProperties.get(player).upgradeTreasureHuntLevelTo(treasurehuntLevel);
			sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Your Treasurehuntlevel was upgraded"));

		}
		
	}

}
