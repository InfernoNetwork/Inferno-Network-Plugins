package inferno.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;

public class Kick implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("kick")){
			if(!(p.hasPermission("inferno.kick"))){
				
				p.sendMessage(ChatUtils.permissions() + "You do not have the permission to execute this command.");
				
				return true;
				
			}
			if(args.length == 0){
				p.sendMessage(ChatUtils.punishments() + "Invalid args. Usage : §c/Kick <Player> [Reason]");
				
				return true;
				
			}else{
			
			if(args.length == 1){
				p.sendMessage(ChatUtils.punishments() + "Invalid args. Usage : §c/Kick <Player> [Reason]");
				
				return true;
				
			}else{
				
			if(args.length == 2){
				
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
                if (target == null) {
                    sender.sendMessage(ChatUtils.punishments() + "Could not find player §c" + args[0] + "§e!");
                    return true;
                    
                }else{
				
				target.kickPlayer("§e You have been §cKicked §eby §c" + p.getName() + "\n §eReason:§c " + args[1]);
				p.sendMessage(ChatUtils.punishments() + "You have kicked player §c" + target.getName() + " §efor§c " + args[1]);
					
                		}
					}
				}
			}
		}
		
		return false;
	}

}
