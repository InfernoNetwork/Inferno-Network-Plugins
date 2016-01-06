package inferno.network.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.darkblade12.particleeffect.ParticleEffect;

import inferno.network.Main;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class OnSneak implements Listener {
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		final Player player = event.getPlayer();
		new BukkitRunnable() {
			double t = Math.PI / 4;
			Location loc = player.getLocation();

			public void run() {
				t = t + 0.1 * Math.PI;
				for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 4) {
					double x = t * cos(theta);
					double y = 2 * Math.exp(-0.1 * t) * sin(t) + 1.5;
					double z = t * sin(theta);
					loc.add(x, y, z);
					ParticleEffect.FIREWORKS_SPARK.display(loc.getDirection(), 1.0f, loc, 10d);
					loc.subtract(x, y, z);

					theta = theta + Math.PI / 64;

					x = t * cos(theta);
					y = 2 * Math.exp(-0.1 * t) * sin(t) + 1.5;
					z = t * sin(theta);
					loc.add(x, y, z);
					ParticleEffect.SPELL_WITCH.display((float)x,(float)y,(float)z, 1.0f,10,loc,200);
					loc.subtract(x, y, z);
				}
				if (t > 20) {
					this.cancel();
				}
			}

		}.runTaskTimer(Main.plugin, 0, 1);
	}
}
