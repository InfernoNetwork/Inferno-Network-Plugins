package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;


public class Tokens implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("tokens")){
			if(!(p.hasPermission("inferno.tokens"))){
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the permission to execute this command.");
				
				return true;
				
			}
			
			if(args.length == 0){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Ban add <Player> [Reason]");
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("add")){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Ban add <Player> [Reason]");
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("add") && args.length == 1){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Ban add <Player> [Reason]");
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("add") && args.length == 2){
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
                if (target == null) {
                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[0] + "!");
                    return true;
                    
                }else{
                	
				target.kickPlayer("§c§lYou Have Been Banned For Permanent! \n§7Reason Specifed:§9 " + args[1] + "\n§2Unfairly banned? Appeal at §awww.infernonetwork.org");
				p.sendMessage(ChatUtils.prefix() + "You have banned player " + target.getName() + " for " + args[1]);
				
                }      
			}
		}
		
		return false;
		
	}

}
