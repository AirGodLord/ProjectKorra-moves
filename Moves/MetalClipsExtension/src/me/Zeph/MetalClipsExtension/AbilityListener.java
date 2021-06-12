package me.Zeph.MetalClipsExtension;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;


public class AbilityListener implements Listener {

	
	@EventHandler
	public void onShift(PlayerToggleSneakEvent event) {

		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		
		if (event.isCancelled() || bPlayer == null) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("MetalClips")) {
			new Main(player);

		}
	
	}
	

	@EventHandler
	public void onClick(PlayerAnimationEvent event) {

		
		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		
		if (event.isCancelled() || bPlayer == null) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("MetalClips")) {
			CoreAbility.getAbility(player, Main.class).clickFunction();

		}
}
}
