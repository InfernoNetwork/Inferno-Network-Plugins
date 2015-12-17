package inferno.network;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import inferno.network.commands.Ban;
import inferno.network.commands.Fly;
import inferno.network.commands.Gamemode;
import inferno.network.commands.Kick;
import inferno.network.commands.TP;
import inferno.network.commands.Warn;
import inferno.network.events.OnJoin;
import inferno.network.events.OnQuit;


public class Main extends JavaPlugin{
	
	public void onEnable(){
		
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
		getServer().getPluginManager().registerEvents(new OnQuit(), this);
		
		getCommand("kick").setExecutor(new Kick());
		getCommand("warn").setExecutor(new Warn());
		getCommand("ban").setExecutor(new Ban());
		getCommand("fly").setExecutor(new Fly());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("tp").setExecutor(new TP());
		
		saveDefaultConfig();
	}
	
	public void onDisable(){
		
		saveConfig();
		
	}

}
