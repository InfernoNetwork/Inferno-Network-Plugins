package inferno.network.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import inferno.network.sql.MySQL;
import inferno.network.utils.ChatUtils;

public class Ban implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("ban")){
			if(!(p.hasPermission("inferno.ban"))){
				p.sendMessage(ChatUtils.prefix() + "§4You do not have the permission to execute this command.");
				
				return true;
				
			}
			if(args.length == 0){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Ban <Player> [Reason]");
				
				return true;
			}
			
			if(args.length == 1){
				p.sendMessage(ChatUtils.prefix() + "Invalid args. Usage : /Ban <Player> [Reason]");
				
				return true;
			}
			
			if(args.length == 2){
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
                if (target == null) {
                    sender.sendMessage(ChatUtils.prefix() + "Could not find player " + args[0] + "!");
                    return true;
                    
                }else{
                	
				target.kickPlayer("§c§lYou Have Been Banned For Permanent! \n§7Reason Specifed:§9 " + args[1] + "\n§2Unfairly banned? Appeal at §awww.infernonetwork.org");
				p.sendMessage(ChatUtils.prefix() + "You have banned player " + target.getName() + " for " + args[1]);
				banPlayer(p, args[1]);
				
                }      
			}
		}
		
		return false;
	}

	public void banPlayer(Player p, String reason) {
		
		   try {
			   
               PreparedStatement statement = MySQL.connection.prepareStatement("insert into bans (uuid, reason)\nvalues ('" + p.getUniqueId() + "', '" + reason + "');");
               statement.executeUpdate();
               statement.close();
               
       } catch (SQLException e) {
    	   
               e.printStackTrace();
       }
	}
	
	public static String getBanned(Player p){
		
		try{
			
			PreparedStatement statement = MySQL.connection.prepareStatement("select reason from bans where uuid='" + p.getUniqueId() + "'");
			ResultSet result = statement.executeQuery();
			
			if(result.next()){
				return result.getString("reason");
				
			}else{
				
				return null;
				
			}
			
		}catch (SQLException e) {
            e.printStackTrace();
            return "[[Could not Connect]]";
		}
	}

}