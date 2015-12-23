package inferno.network.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import inferno.network.commands.Ban;

public class OnLogin implements Listener{
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		
        String reason = Ban.getBanned(e.getPlayer());
        
        if (reason != null) {
                e.disallow(Result.KICK_BANNED, "§c§lYou Have Been Banned For Permanent! \n§7Reason Specifed:§9 " + reason + "\n§2Unfairly banned? Appeal at §awww.infernonetwork.org");
        }
		
	}

}
