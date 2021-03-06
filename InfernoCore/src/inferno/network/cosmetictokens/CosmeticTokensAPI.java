package inferno.network.cosmetictokens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import inferno.network.Main;
import inferno.network.utils.ChatUtils;

public class CosmeticTokensAPI {

	public static void addCosmeticTokens(Player p, int i) {

		int tokens = getCosmeticTokensCount(p) + i;
		setCosmeticTokens(p, tokens);
		p.sendMessage(ChatUtils.prefix() + "�a+ " + i + " CosmeticTokens");
		p.sendMessage(ChatUtils.prefix() + "You now have " + getCosmeticTokensCount(p) + " CosmeticTokens.");

	}

	public static void removeCosmeticTokens(Player p, int i) {

		int tokens = getCosmeticTokensCount(p) - i;
		setCosmeticTokens(p, tokens);
		p.sendMessage(ChatUtils.prefix() + "�c- " + i + " CosmeticTokens");
		p.sendMessage(ChatUtils.prefix() + "You now have " + getCosmeticTokensCount(p) + " CosmeticTokens.");

	}

	public static void checkPlayer(Player p, UUID uuid) {

		try {

			Connection c = Main.plugin.getSQL().openConnection();
			String exe = "SELECT cosmetictokens FROM player_cosmetictokens WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs = c.createStatement().executeQuery(exe);
			if (rs.next()) {
				return;

			} else

				Main.plugin.getSQL().openConnection().createStatement()
						.executeUpdate("INSERT INTO player_cosmetictokens (uuid,cosmetictokens) VALUES ('"
								+ p.getUniqueId() + "', 0)");

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public static void setCosmeticTokens(Player p, int i) {

		try {

			PreparedStatement statement = Main.plugin.getSQL().openConnection()
					.prepareStatement("UPDATE player_cosmetictokens SET cosmetictokens = " + i + " WHERE uuid = '"
							+ p.getUniqueId() + "'");
			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public static int getCosmeticTokensCount(Player p) {

		try {

			String exe = "SELECT cosmetictokens FROM player_cosmetictokens WHERE uuid = '" + p.getUniqueId() + "'";
			ResultSet rs = Main.plugin.getSQL().openConnection().createStatement().executeQuery(exe);

			if (rs.next()) {

				int i = rs.getInt("cosmetictokens");
				return i;

			}

			return 0;

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return 0;

	}
	

}
