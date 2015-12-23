package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;

public class TP implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("tp")){
			
			if(!(p.hasPermission("inferno.tp"))){
				
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the permission to execute this command.");
				
				return true;
				
			}
			
			if(args.length == 0){

				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /tp <Player>");
				
				return true;
				
			}else{
				
				if(args.length == 1){
					
					Player target = Bukkit.getServer().getPlayer(args[0]);
					
	                if (target == null) {
	                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[0]);
	                    return true;
	                    
	                }
					
					target.teleport(p);
					
					p.sendMessage(ChatUtils.prefix() + "You have teleported to player " + target.getName() + ".");
					target.sendMessage(ChatUtils.prefix() + "Player " + p.getName() + " has teleported to you.");
					
				}
			}
		}
		
		return false;
	}

}
