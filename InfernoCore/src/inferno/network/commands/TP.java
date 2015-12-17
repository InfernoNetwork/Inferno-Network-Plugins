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
				
				p.sendMessage(ChatUtils.permissions() + "You do not have the permission to execute this command.");
				
				return true;
				
			}
			
			if(args.length == 0){

				p.sendMessage(ChatUtils.punishments() + "Invalid args. Usage : §c/tp <Player>");
				
				return true;
				
			}else{
				
				if(args.length == 1){
					
					Player target = Bukkit.getServer().getPlayer(args[0]);
					
					target.teleport(p);
					
					p.sendMessage(ChatUtils.utils() + "You have teleported to player §c" + target.getName() + "§e.");
					target.sendMessage(ChatUtils.utils() + "Player §c" + p.getName() + " §ehas teleported to you.");
				}
				
				if(args.length == 1){
					
					Player target = Bukkit.getServer().getPlayer(args[0]);
					
					p.teleport(target);
					
					p.sendMessage(ChatUtils.utils() + "You have teleported to player §c" + target.getName() + "§e.");
					target.sendMessage(ChatUtils.utils() + "Player §c" + p.getName() + " §ehas teleported to you.");
				}
				
			}
			
		}
		
		return false;
	}

}
