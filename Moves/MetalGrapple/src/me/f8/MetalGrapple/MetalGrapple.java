package me.f8.MetalGrapple;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.MetalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import net.minecraft.server.v1_15_R1.AdvancementProgress.a;


public class MetalGrapple extends MetalAbility implements AddonAbility {
	private void displayParticle(Location location, int amount, double x, double y, double z, int r, int g, int b) {
		ParticleEffect.REDSTONE.display(location, amount, x, y, z, 0.005, new Particle.DustOptions(Color.fromRGB(r, g, b), 1));
	}

	public Location getRightHandPos(){
		return GeneralMethods.getRightSide(player.getLocation(), .6).add(0, 1.2, 0);
	}

	public Location getLeftHandPos(){
		return GeneralMethods.getLeftSide(player.getLocation(), .6).add(0, 1.2, 0);
	}
	
	
	private Listener MSL;
	private long cooldown;
	private int charge;
	private int range;
	private int speed ;
	private long duration;
	private long timeBetweenShots;
	private long lastShotTime;
	private int lastProjectileId;
	private long hitbox;

	private HashMap<Integer, Location> locations;
	private HashMap<Integer, Vector> directions;
	private HashMap<Integer, Location> startLocations;
	private HashMap<Integer, Location> deadProjectiles;
	
	public MetalGrapple(Player player) {
		super(player);
		
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		if (!bPlayer.canBend(this)) {
			return;
		}
		
		if (hasAbility(player, MetalGrapple.class)) {
		
			MetalGrapple ms = getAbility(player, MetalGrapple.class);
			
			if (ms.getCharge() == 0 || System.currentTimeMillis() < ms.getLastShotTime() + ms.getTimeBetweenShots()) {
				return;
			}
			
			if (ms.getCharge() == 1) {
				bPlayer.addCooldown(ms);
			}
			int projectileId = ms.getLastProjectileId() + 1;
			
			Location loc = null;
			if (projectileId % 2 == 1) {
				loc = getRightHandPos();
			}
			else {
				loc = getLeftHandPos();
			}
			ms.getParticleLocations().put(projectileId, loc);
			ms.getDirections().put(projectileId, player.getEyeLocation().getDirection());
			ms.getStartLocations().put(projectileId, loc.clone());
			ms.setLastShotTime(System.currentTimeMillis());
			ms.setCharge(ms.getCharge() - 1);
			ms.setLastProjectileId(projectileId);
			
		} else {
			
			setField();
			start();
		}	
	}
	
	public void setField() {
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Earth.MetalGrapple.Cooldown");
		this.charge = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Earth.MetalGrapple.Charge");
		this.range = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Earth.MetalGrapple.Range");					
		this.speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Earth.MetalGrapple.Speed");
		this.hitbox = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Earth.MetalGrapple.Hitbox");
								
		duration = 3000;
		this.timeBetweenShots = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Earth.MetalGrapple.ShotInterval");		
		lastShotTime = getStartTime();
				
		deadProjectiles = new HashMap<Integer, Location>();
		locations = new HashMap<Integer, Location>();
		directions = new HashMap<Integer, Vector>();
		startLocations = new HashMap<Integer, Location>();		
		lastProjectileId = 1;		
		
		
		Location loc = getRightHandPos();
		
		
		locations.put(lastProjectileId, loc);
		directions.put(lastProjectileId, player.getLocation().getDirection());
		startLocations.put(lastProjectileId, loc.clone());
			
		charge--;
		
	}

	@Override
	public void progress() {

		
		if (charge == 0 && locations.isEmpty()) {
			remove();
			return;
		}		
		
		if (System.currentTimeMillis() > getStartTime() + duration) {
			bPlayer.addCooldown(this);
			remove();
		}
		
		
		
	for (Integer i : locations.keySet()) {
			
			displayParticle(locations.get(i),5,0.2,0,0.2,161,157,148);
			player.getWorld().playSound(locations.get(i), Sound.ENTITY_BLAZE_SHOOT, 1, 1);
						
						
			locations.get(i).add(directions.get(i).clone().multiply(speed));
			
			if (locations.get(i).distance(startLocations.get(i)) > range
					|| GeneralMethods.isSolid(locations.get(i).getBlock())) {
				Vector a = locations.get(i).subtract(player.getLocation()).toVector();
				player.setVelocity(a);
				deadProjectiles.put(i, locations.get(i));
			}
		}
				
		for(Integer i : deadProjectiles.keySet()) {
			locations.remove(i);
			directions.remove(i);
			startLocations.remove(i);
		}
		
		deadProjectiles.clear();
		
	}
	

	
	
	public int getCharge() {
		return this.charge;
	}
	
	
	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	public int getLastProjectileId() {
		return this.lastProjectileId;
	}
	
	public void setLastProjectileId(int id) {
		this.lastProjectileId = id;
	}
	
	
	public long getLastShotTime() {
		return this.lastShotTime;
	}
	
	
	public void setLastShotTime(long time) {
		this.lastShotTime = time;
	}
	
	
	public long getTimeBetweenShots() {
		return this.timeBetweenShots;
	}
	
	
	public HashMap<Integer, Location> getParticleLocations() {
		return this.locations;
	}
	
	
	public HashMap<Integer, Vector> getDirections() {
		return this.directions;
	}
	
	
	public HashMap<Integer, Location> getStartLocations() {
		return this.startLocations;
	}
	
	@Override
	public long getCooldown() {
		return this.cooldown;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "MetalGrapple";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public String getAuthor() {
		return "Zephyriox";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getDescription() {
		return "Grapple towards a target rapidly."; 
	}
	
	@Override
	public void load() {
		
		MSL = new AbilityListener();
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(MSL, ProjectKorra.plugin);
		ProjectKorra.log.info("Succesfully enabled " + getName() + " by " + getAuthor());
		FileConfiguration config = ProjectKorra.plugin.getConfig();
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.Cooldown", Long.valueOf(1000));
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.Range", Integer.valueOf(20));
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.Speed", Integer.valueOf(1));
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.Charge", Integer.valueOf(4));
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.Hitbox", Long.valueOf(1));
		config.addDefault("ExtraAbilities.Earth.MetalGrapple.ShotInterval", Long.valueOf(500));
		config.options().copyDefaults(true);
		ProjectKorra.plugin.saveConfig();
		ProjectKorra.plugin.getLogger().info(String.format("%s %s, developed by %s, has been loaded.", getName(), getVersion(), getAuthor()));
	}

	@Override
	public void stop() {
		ProjectKorra.log.info("Successfully disabled " + getName() + " by " + getAuthor());
		
		HandlerList.unregisterAll(MSL);
		super.remove();
	}	
	
}