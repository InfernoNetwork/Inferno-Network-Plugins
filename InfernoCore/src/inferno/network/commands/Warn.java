package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;

public class Warn implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("warn")){
			if(!(p.hasPermission("inferno.warn"))){
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the permission to execute this command.");
				
				return true;
				
			}
			if(args.length == 0){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Warn <Player> [Reason]");
				
				return true;
				
			}else{
			
			if(args.length == 1){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Warn <Player> [Reason]");
				
				return true;
				
			}else{
				
			if(args.length == 2){
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
                if (target == null) {
                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[0]);
                    return true;
                    
                }else{
				
				p.sendMessage(ChatUtils.prefix() + "You have been Warned by " + p.getName() + " for " + args[1]);
				p.sendMessage(ChatUtils.prefix() + "You have Warned player " + target.getName() + " for " + args[1]);
					
                		}
					}
				}
			}
		}
		
		return false;
	}

}
