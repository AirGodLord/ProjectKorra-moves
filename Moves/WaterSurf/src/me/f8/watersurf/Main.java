package me.f8.watersurf;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.TempBlock;


public class Main extends WaterAbility implements AddonAbility{
	
	private int speed;
	private long cooldown;
	private Location location;
	private Vector direction;
	private Location up;
	private Vector height;
	private Vector vel;
	private Vector dir;
	
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
		
		
		
		this.speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.WaterSurf.Speed");
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Water.WaterSurf.Cooldown");
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
		return "WaterSurf";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return true;
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
		
		this.location = player.getLocation();
		
		
		this.direction = player.getLocation().getDirection();
	
	
		dir = direction.setY(0);
		if (GeneralMethods.isSolid(location.getBlock())) {
			up = location.add(0,1,0);
			height = (up.subtract(location)).toVector();
			vel = (dir.multiply(0.1)) ;
			player.setVelocity(player.getVelocity().add(vel).add(height));
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
		config.addDefault("ExtraAbilities.Water.WaterSurf.Cooldown", Integer.valueOf(1000));
		config.addDefault("ExtraAbilities.Water.WaterSurf.Speed", Double.valueOf(1));
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


	