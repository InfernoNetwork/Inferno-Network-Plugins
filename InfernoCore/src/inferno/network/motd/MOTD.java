package inferno.network.motd;

import org.bukkit.entity.Player;

public class MOTD {
	
	public static void MOTD(Player p){
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("§c§l>> §eWelcome to the §cInferno Network.");
		p.sendMessage("§c§l>> §eThere are §a[Amount of player] §eplayers online.");
		p.sendMessage("§c§l>> §eVist us online at §c https://infernonetwork.org§e.");
		p.sendMessage("§c§l>> §7You are currently connected to §a[server name]§7." );
		p.sendMessage("§c§l>> §7Use §c/server §7to navigate to our games.");
	}

}
