package inferno.network;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import inferno.network.commands.Ban;
import inferno.network.commands.CosmeticTokens;
import inferno.network.commands.Kick;
import inferno.network.commands.Tokens;
import inferno.network.commands.Unban;
import inferno.network.commands.Warn;
import inferno.network.events.OnChat;
import inferno.network.events.OnJoin;
import inferno.network.events.OnLogin;
import inferno.network.events.OnQuit;
import inferno.network.sql.MySQL;

public class Main extends JavaPlugin{
	
	private MySQL mysql;
	
	public static Main plugin;
	
	public void onEnable(){
		
		mysql = new MySQL("localhost:3306", "root", "", "network_player_data");
		
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
		getServer().getPluginManager().registerEvents(new OnQuit(), this);
		getServer().getPluginManager().registerEvents(new OnLogin(), this);
		getServer().getPluginManager().registerEvents(new OnChat(), this);
		
		getCommand("tokens").setExecutor(new Tokens());
		getCommand("cosmetictokens").setExecutor(new CosmeticTokens());
		getCommand("ban").setExecutor(new Ban());
		getCommand("unban").setExecutor(new Unban());
		getCommand("kick").setExecutor(new Kick());
		getCommand("warn").setExecutor(new Warn());
		
	}
	
	public void onDisable(){
		
		try {
			
			MySQL.connection.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
}