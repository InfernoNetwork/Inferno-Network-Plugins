package inferno.network.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.Main;
import inferno.network.utils.ChatUtils;

public class Unban implements CommandExecutor{
	
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("unban")){
			
			if(!(p.hasPermission("inferno.unban"))){
				
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the correct permission to execute that command.");
				return true;
				
			}
			
			if(args.length == 0 || args.length < 1){
				
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage: /Unban <Player>");
				return true;
				
			}else if(args.length == 1){
				OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
				
                if (target == null) {
                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[0] + "!");
                    return true;
                    
                }
                
                p.sendMessage(ChatUtils.prefix() + "You have unbanned player" + target.getName());
                unbanPlayer(target);
                
			}
			
		}
		
		return false;
		
		}
		
		public void unbanPlayer(OfflinePlayer target){
			
			try {
				   
				PreparedStatement statement =  Main.plugin.getSQL().openConnection().prepareStatement("DELETE FROM player_bans WHERE uuid='" + target.getUniqueId() + "'");
				statement.executeUpdate();
				statement.close();
	               
	       } catch (SQLException e) {
	    	   
	               e.printStackTrace();
	               
	        }
		}
	}

