package me.f8.flamevault;



	import org.bukkit.Material;
	import org.bukkit.block.BlockFace;
	import org.bukkit.entity.Player;
	import org.bukkit.event.EventHandler;
	import org.bukkit.event.Listener;
	import org.bukkit.event.player.PlayerAnimationEvent;
	import org.bukkit.event.player.PlayerToggleSneakEvent;

	import com.projectkorra.projectkorra.BendingPlayer;

	import me.f8.flamevault.Main.HighJumpType;


	public class AbilityListener implements Listener {

		@EventHandler
		public void onSneak(PlayerToggleSneakEvent event) {

			boolean isOnBlock = false;
			Player player = event.getPlayer();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			Material block = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
			
			if (block.isSolid()) {
				isOnBlock = true;
			}
			
			if (bPlayer.getBoundAbilityName().equalsIgnoreCase("FlameVault")) {
				if (isOnBlock) {
					new Main(player, HighJumpType.EVADE);
				} else {
					new Main(player, HighJumpType.DOUBLEJUMP);
				}
			}
		}
		@EventHandler
		public void onSwing(PlayerAnimationEvent event) {
			Player player = event.getPlayer();
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

			if (bPlayer.getBoundAbilityName().equalsIgnoreCase("FlameVault")) {
				if (player.isSprinting()) {
					new Main(player, HighJumpType.LUNGE);
				} else {
					new Main(player, HighJumpType.JUMP);
				}
			}
		}
	}