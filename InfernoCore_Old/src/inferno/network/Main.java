package inferno.network;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import inferno.network.commands.Ban;
import inferno.network.commands.Fly;
import inferno.network.commands.Gamemode;
import inferno.network.commands.Kick;
import inferno.network.commands.TP;
import inferno.network.commands.Tokens;
import inferno.network.commands.Warn;
import inferno.network.events.OnJoin;
import inferno.network.events.OnLogin;
import inferno.network.events.OnQuit;
import inferno.network.sql.MySQL;


public class Main extends JavaPlugin{
	
	public static Main plugin;
	
	private MySQL mysql;
	
	public void onEnable(){
		
        mysql = new MySQL("localhost:3306", "root", "", "network_player_data");
		
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
		getServer().getPluginManager().registerEvents(new OnQuit(), this);
		getServer().getPluginManager().registerEvents(new OnLogin(), this);
		
		getCommand("ban").setExecutor(new Ban());
		getCommand("kick").setExecutor(new Kick());
		getCommand("warn").setExecutor(new Warn());
		getCommand("fly").setExecutor(new Fly());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("tp").setExecutor(new TP());
		getCommand("tokens").setExecutor(new Tokens());
		
		
	}
	
	public void onDisable(){
		
			try {
				
				MySQL.connection.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
	}


}
