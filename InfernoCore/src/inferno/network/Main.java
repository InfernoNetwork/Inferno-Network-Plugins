package inferno.network;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import com.huskehhh.mysql.MySQL;

import inferno.network.commands.Ban;
import inferno.network.commands.CosmeticTokens;
import inferno.network.commands.FriendCommand;
import inferno.network.commands.Kick;
import inferno.network.commands.Tokens;
import inferno.network.commands.Unban;
import inferno.network.commands.Warn;
import inferno.network.events.OnChat;
import inferno.network.events.OnJoin;
import inferno.network.events.OnLogin;
import inferno.network.events.OnQuit;
import inferno.network.utils.FriendAPI;

public class Main extends JavaPlugin {

	private MySQL mysql;

	public static Main plugin;

	public void onEnable() {
		plugin = this;
		initSQL();

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
		getCommand("friend").setExecutor(new FriendCommand());

		FriendAPI.init();
		initJoshDbs();

	}

	public MySQL getSQL() {
		return mysql;
	}

	public void onDisable() {
		try {
			mysql.closeConnection();
		} catch (SQLException e) {
			// whatevs
		}

	}

	private void initSQL() {

		String server = getDefaultConfig("mysql.server", "localhost");
		String port = getDefaultConfig("mysql.port", "3306");
		String user = getDefaultConfig("mysql.user", "root");
		String password = getDefaultConfig("mysql.password", "");
		String database = getDefaultConfig("mysql.database", "database");
		mysql = new MySQL(server, port, database, user, password);
	}

	private String getDefaultConfig(String key, String def) {
		Object o = this.getConfig().get(key);
		if (o == null || !(o instanceof String)) {
			this.getConfig().set(key, def);
			this.saveConfig();
			return def;
		}
		return o.toString();
	}

	private void initJoshDbs() {

		try {
			Connection c = Main.plugin.getSQL().openConnection();
			c.createStatement().execute(
					"CREATE TABLE IF NOT EXISTS `player_cosmetictokens` (`uuid` VARCHAR(36) NOT NULL, `cosmetictokens` INT NOT NULL)");
			c.createStatement().execute(
					"CREATE TABLE IF NOT EXISTS `player_tokens` (`uuid` VARCHAR(36) NOT NULL, `tokens` INT NOT NULL)");
			
			c.createStatement().execute("CREATE TABLE IF NOT EXISTS `player_bans` (`uuid` VARCHAR(36) NOT NULL, `reason` VARCHAR(255))");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}