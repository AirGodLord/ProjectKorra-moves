package me.f8.watersurf;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;


public class AbilityListener implements Listener {

	
	@EventHandler
	public void onSwing(PlayerAnimationEvent event) {

		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		
		if (event.isCancelled() || bPlayer == null) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("WaterSurf")) {
			new Main(player);

		}
		
	}
	
	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {

		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		
		if (event.isCancelled() || bPlayer == null) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("WaterSurf")) {
			bPlayer.isOnCooldown("WaterSurf");
			return;

		}
	}
}
