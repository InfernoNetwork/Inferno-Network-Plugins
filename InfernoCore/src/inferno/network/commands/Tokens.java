package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.tokens.TokensAPI;
import inferno.network.utils.ChatUtils;

public class Tokens implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("tokens")){
			
			if(!(p.hasPermission("inferno.tokens"))){
				
				p.sendMessage(ChatUtils.prefix() + "�4You do not have the correct permission to execute that command.");
				return true;
				
			}
			
			if(args.length == 0 || args.length < 3){
				
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage: /Tokens <Add/Remove> <Player> <Amount>");
				return true;
				
			}else if(args[0].equalsIgnoreCase("add") && args.length == 3){
				
				try{
					
					Player target = Bukkit.getServer().getPlayer(args[1]);
					
	                if (target == null) {
	                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[1] + "!");
	                    return true;
	                    
	                }
	                
					int i = Integer.parseInt(args[2]);
					TokensAPI.addTokens(target, i);		
					
					return true;
					
				}catch(NumberFormatException exc) {

					return false;
					
				}
				
			}else{
				
				if(args[0].equalsIgnoreCase("remove") && args.length == 3){
					
					try{
						
						Player target = Bukkit.getServer().getPlayer(args[1]);
						
		                if (target == null) {
		                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[1] + "!");
		                    return true;
		                    
		                }
		                
						int i = Integer.parseInt(args[2]);
						TokensAPI.removeTokens(target, i);		
						
						return true;
						
					}catch(NumberFormatException exc) {

						return false;
						
						}
					}			
				}
			}
		
		return false;
	}

}
