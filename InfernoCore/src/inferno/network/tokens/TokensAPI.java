package inferno.network.tokens;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import inferno.network.Main;
import inferno.network.utils.ChatUtils;

public class TokensAPI {

	public static void addTokens(Player p, int i) {

		int tokens = getTokensCount(p) + i;
		setTokens(p, tokens);
		p.sendMessage(ChatUtils.prefix() + "§a+ " + i + " Tokens");
		p.sendMessage(ChatUtils.prefix() + "You now have " + getTokensCount(p) + " Tokens.");

	}

	public static void removeTokens(Player p, int i) {

		int tokens = getTokensCount(p) - i;
		setTokens(p, tokens);
		p.sendMessage(ChatUtils.prefix() + "§c- " + i + " Tokens");
		p.sendMessage(ChatUtils.prefix() + "You now have " + getTokensCount(p) + " Tokens.");

	}

	public static void checkPlayer(Player p, UUID uuid) {

		try {

			String exe = "SELECT tokens FROM player_tokens WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs = Main.plugin.getSQL().openConnection().createStatement().executeQuery(exe);
			if (rs.next()) {

				return;

			} else

				Main.plugin.getSQL().openConnection().createStatement()
						.executeUpdate("INSERT INTO player_tokens (uuid,tokens) VALUES ('" + p.getUniqueId() + "', 0)");

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public static void setTokens(Player p, int i) {

		try {

			PreparedStatement statement =  Main.plugin.getSQL().openConnection().prepareStatement(
					"UPDATE player_tokens SET tokens = " + i + " WHERE uuid = '" + p.getUniqueId() + "'");
			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public static int getTokensCount(Player p) {

		try {

			String exe = "SELECT tokens FROM player_tokens WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs =  Main.plugin.getSQL().openConnection().createStatement().executeQuery(exe);

			if (rs.next()) {

				int i = rs.getInt("tokens");
				return i;

			}

			return 0;

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return 0;

	}

}
