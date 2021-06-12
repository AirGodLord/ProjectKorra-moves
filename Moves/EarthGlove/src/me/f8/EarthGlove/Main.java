package me.f8.EarthGlove;


	import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
	import org.bukkit.entity.Entity;
	import org.bukkit.entity.LivingEntity;
	import org.bukkit.entity.Player;
	import org.bukkit.util.Vector;

	import com.projectkorra.projectkorra.GeneralMethods;
	import com.projectkorra.projectkorra.ProjectKorra;
	import com.projectkorra.projectkorra.ability.AddonAbility;
	import com.projectkorra.projectkorra.ability.EarthAbility;
	import com.projectkorra.projectkorra.util.DamageHandler;
	import com.projectkorra.projectkorra.util.ParticleEffect;



	public class Main extends EarthAbility implements AddonAbility{
		
		private void displayParticle(Location location, int amount, double x, double y, double z, int r, int g, int b) {
			ParticleEffect.REDSTONE.display(location, amount, x, y, z, 0.005, new Particle.DustOptions(Color.fromRGB(r, g, b), 1));
		}
		
		private int speed;
		private int range;
		private long cooldown;
		private double damage;	
		private Location location;
		private Location origin;
		private Vector direction;
		
		public Main(Player player) {
			super(player);
			// TODO Auto-generated constructor stub
			
			if (bPlayer.isOnCooldown(this)) {
				return;
			}
			
			
			setFields();
			
			
			start();
			
			
			bPlayer.addCooldown(this);
		}
			
		private void setFields() {
			
			this.origin = player.getLocation().clone().add(0, 1, 0);
			
			
			this.location = origin.clone();
			
			
			this.direction = player.getLocation().getDirection();
			
			this.speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Earth.EarthGlove.Speed");
			this.range = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Earth.EarthGlove.Range");
			this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Earth.EarthGlove.Cooldown");
			this.damage = ProjectKorra.plugin.getConfig().getDouble("ExtraAbilities.Earth.EarthGlove.Damage");
		}		


		@Override
		public long getCooldown() {
			// TODO Auto-generated method stub
			return cooldown;
		}

		@Override
		public Location getLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "EarthGlove";
		}

		@Override
		public boolean isHarmlessAbility() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSneakAbility() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void progress() {
			// TODO Auto-generated method stub
			
			if (player.isDead() || !player.isOnline()) {
				remove();
				return;
			}
			
			
			if (origin.distance(location) > range) {
				remove();
				return;
			}
			
			
			location.add(direction.multiply(speed));
			
			displayParticle(location,10,0.1,0.1,0.1,84,57,45);
			
			
			
			if (GeneralMethods.isSolid(location.getBlock())) {
				remove();
				return;
			}
			
			
			for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1)) {
				
				if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
					direction = GeneralMethods.getDirection(this.origin, this.location).normalize();
					
					DamageHandler.damageEntity(entity, damage, this);
					if (entity.isOnGround()) {
						entity.setVelocity(direction.multiply(-1));
						player.getWorld().playSound(location, Sound.BLOCK_PISTON_EXTEND, 1, 1);
					}
					else {
						entity.setVelocity(direction);
					}
					remove();
					return;
				}
			}
		}
			

		@Override
		public String getAuthor() {
			// TODO Auto-generated method stub
			return "F8";
		}

		@Override
		public String getVersion() {
			// TODO Auto-generated method stub
			return "1.0";
		}

		@Override
		public void load() {
			
			ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
			FileConfiguration config = ProjectKorra.plugin.getConfig();
			config.addDefault("ExtraAbilities.Earth.EarthGlove.Range", Integer.valueOf(20));
			config.addDefault("ExtraAbilities.Earth.EarthGlove.Cooldown", Integer.valueOf(1000));
			config.addDefault("ExtraAbilities.Earth.EarthGlove.Damage", Double.valueOf(2.0D));
			config.addDefault("ExtraAbilities.Earth.EarthGlove.Speed", Double.valueOf(1));
			config.options().copyDefaults(true);
			ProjectKorra.plugin.saveConfig();
			ProjectKorra.plugin.getLogger().info(String.format("%s %s, developed by %s, has been loaded.", getName(), getVersion(), getAuthor()));
			
			
			ProjectKorra.log.info("Successfully enabled " + getName() + " by " + getAuthor());
		}
			// TODO Auto-generated method stub
			
		

		@Override
		public void stop() {
			
			ProjectKorra.log.info("Successfully disabled " + getName() + " by " + getAuthor());
			
			
			super.remove();
		}
			// TODO Auto-generated method stub
			
		}


		

