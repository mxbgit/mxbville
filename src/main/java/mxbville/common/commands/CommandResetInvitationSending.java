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

public class CommandResetInvitationSending extends CommandBase  {

	private final List<String> aliases = Lists.newArrayList(MxRef.MOD_ID, "mx_resetinvites", "mx_resetinvitationsending");
	
	@Override
	public String getName() {
		
		return "mx_resetinvitationsending";
	}
	
	@Override
	public String getUsage(ICommandSender sender) {

		return "mx_resetinvitationsending";
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
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender; 
			ExtendedPlayerProperties.get(player).init(player, null);
		}
	}
}
