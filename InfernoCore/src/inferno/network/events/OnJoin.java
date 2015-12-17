package inferno.network.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import inferno.network.motd.MOTD;

public class OnJoin implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		e.setJoinMessage(null);
		
		MOTD.MOTD(p);
	}

}
