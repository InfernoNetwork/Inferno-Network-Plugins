package inferno.network.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener{
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		e.setQuitMessage("");
		
	}

}
