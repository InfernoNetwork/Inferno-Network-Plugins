package inferno.network.events;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import inferno.network.Main;
import inferno.network.utils.ChatUtils;

public class OnChat implements Listener {

	volatile LinkedList<String> chat;

	public OnChat() {
		chat = new LinkedList<String>();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();

		if (p.hasPermission("inferno.chat")) {
			return;
		}

		if ((chat.contains(p.getName()))) {
			
			p.sendMessage(ChatUtils.prefix() + "You can only chat every 3 secounds.");
			e.setCancelled(true);
			
		} else {
			
			chat.add(p.getName());
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				public void run() {
					
					chat.remove(p.getName());
					
				}

			}, 30L);

		}

	}

}
