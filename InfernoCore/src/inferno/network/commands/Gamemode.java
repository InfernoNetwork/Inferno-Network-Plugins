package inferno.network.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.utils.ChatUtils;

public class Gamemode implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("gamemode")){
			
			if(!(p.hasPermission("inferno.gamemode"))){
				
				p.sendMessage(ChatUtils.permissions() + "You do not have the permission to execute this command.");
				
				return true;
				
			}
			
			if(args.length == 0){
				p.sendMessage(ChatUtils.utils() + "Invalid args. Usage : §c/gamemode <0/1/2>");
				
				return true;
				
			}else{
			
			if(args[0].equalsIgnoreCase("0")){
				
				p.sendMessage(ChatUtils.utils() + "You have set your gamemode to §cSurvival");
				p.setGameMode(GameMode.SURVIVAL);
				
			}else{
			
			if(args[0].equalsIgnoreCase("1")){
				
				p.sendMessage(ChatUtils.utils() + "You have set your gamemode to §cCreative");
				p.setGameMode(GameMode.CREATIVE);
				
			}else{
			
			if(args[0].equalsIgnoreCase("2")){
				
				p.sendMessage(ChatUtils.utils() + "You have set your gamemode to §cAdventure");
				p.setGameMode(GameMode.ADVENTURE);
				
			}
			}
			}
			}
		}
		
		return false;
		
	}

}
