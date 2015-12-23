package inferno.network.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;

public class Fly implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("fly")){
			
			if(!(p.hasPermission("inferno.kick"))){
				
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the permission to execute this command.");
				
				return true;
				
			}
			if(args.length == 0){
				if(p.getAllowFlight()){
					
					p.setAllowFlight(false);
					p.sendMessage(ChatUtils.prefix() + "You have disabled Fly.");
					
				}else{
				
					p.setAllowFlight(true);
					p.sendMessage(ChatUtils.prefix() + "You have enabled Fly.");
					
				}
			}
		}
		
		return false;
		
	}

}
