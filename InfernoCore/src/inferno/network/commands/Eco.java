package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.tokens.TokensAPI;
import inferno.network.utils.ChatUtils;

public class Eco implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player target = Bukkit.getServer().getPlayer(args[3]);
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("eco")){
			
			if(args.length == 0){
				
				p.sendMessage(ChatUtils.prefix() + "eco help");
				
			}else{
				
				if(args[1].equalsIgnoreCase("tokens")){
					
					p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Eco tokens <give/remove> <Player> <Amount>");
					p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Eco cosz <give/remove> <Player> <Amount>");
					
				}else{
					
					if(args[2].equalsIgnoreCase("give")){		
						
		                if (target == null) {
		                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[3] + "!");
		                    return true;
		                    
		                }else{
		                	
		        			try{
		        				
		        				int i = Integer.parseInt(args[4]);
		        				
		        				TokensAPI.addTokens(target, i);
		        				
		        			}catch (NumberFormatException exc) {
		                	
		                }
						
					}
					
		           if(args[2].equalsIgnoreCase("remove")){
		        	   
						
		                if (target == null) {
		                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[3] + "!");
		                    return true;
		                    
		                }else{
		                	
		        			try{
		        				
		        				int i = Integer.parseInt(args[4]);
		        				
		        				TokensAPI.removeTokens(target, i);
		        				
		        			}catch (NumberFormatException exc) {
		                	
		        				}
		           			}
						}
					}
				}
			}
		}
		
		return false;
		
	}

}
