package inferno.network.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import inferno.network.Main;
import inferno.network.utils.ChatUtils;

public class OnChat implements Listener{
	
	@EventHandler
	@SuppressWarnings("deprecation")
	public void onChat(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		
		ArrayList<String> chat = new ArrayList<String>();
		
		if(!(p.hasPermission("inferno.chat"))){
			
			if(!(chat.contains(p.getName()))){
				
				chat.add(p.getName());
				
				Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.plugin, new Runnable(){

					public void run() {
						
						chat.remove(p.getName());
						e.setCancelled(true);
						
					}
					
				}, 30L);
					
			}else p.sendMessage(ChatUtils.prefix() + "You can only chat every 3 secounds.");
			
		}
			
	}

}
