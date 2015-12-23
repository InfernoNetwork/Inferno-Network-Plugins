package inferno.network.tokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class TokensAPI {
	
	public static Connection connection;

	public static void addTokens(Player p, int i) {
		
		int coins = getTokensCount(p) + i;
		setTokens(p, coins);
		p.sendMessage("+ " + i);
		
	}
	
	public static void removeTokens(Player p, int i) {
		
		int coins = getTokensCount(p) - i;
		setTokens(p, coins);
		p.sendMessage("- " + i);
		
	}
	
	public static int getTokensCount(Player p){
		
		try{
			
			String exec = "SELECT tokens FROM " + "tokens" + " WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs = connection.createStatement().executeQuery(exec);
			
			if (rs.next()) {
				
				int i = rs.getInt("tokens");
				return i;
			}
				return 0;
			
		}catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return 0;
		
	}
	
	public static void setTokens(Player p, int i){
		try {
			
            PreparedStatement statement = connection.prepareStatement("UPDATE " + "tokens" + " SET tokens = " + i + " WHERE uuid = '" + p.getUniqueId() + "'");
            statement.executeUpdate();
            statement.close();
			
		}catch (SQLException e) {
			
			e.printStackTrace();
			
			
		}
		
	}
	
	public static void checkPlayer(Player p, UUID uuid) {
		try {
			
			String exec = "SELECT tokens FROM " + "tokens" + " WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs = connection.createStatement().executeQuery(exec);
			if (rs.next()) {
				return;
				
			} else
				connection.createStatement().executeUpdate(
						"INSERT INTO " + "tokens" + " (uuid,tokens) VALUES ('" + p.getUniqueId() + "', 0)");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
}
