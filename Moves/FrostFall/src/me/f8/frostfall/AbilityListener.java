package me.f8.frostfall;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerAnimationEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;

public class AbilityListener implements Listener {

	
	@EventHandler
	public void onFall(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == DamageCause.FALL) {
				BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

				
				if (event.isCancelled() || bPlayer == null) {
					return;

				
				} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
					return;

				
				} else if (bPlayer.hasElement(Element.WATER)) {
						new Main(player);
							event.setCancelled(true);
						}
	

	}
}
	}
}
