package me.Zeph.MetalClipsExtension;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.MetalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Main extends MetalAbility implements AddonAbility{
	
	private int range;
	private long cooldown;	
	private Location location;
	private Location origin;
	private Vector direction;
	private Entity minecart;
	private Location infront;
	private Vector towards;
	private Boolean hasclicked = false;
	private double landthrowforce;
	private double railthrowforce;
	
	public Main(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
		
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		
		setFields();
		
		for (Entity e : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), range)){
			if (e instanceof Minecart) {
				minecart = e;
			}
		}
		
		if (minecart instanceof Minecart) {
			setFields();
			start();
		}
		else {
			return;
		}
	}
		
	
	private void setFields() {
		
		this.origin = player.getLocation().clone().add(0, 1, 0);
		this.landthrowforce = ProjectKorra.plugin.getConfig().getDouble("Zeph.Metal.MetalClipsExtension.LandThrowForce");
		this.railthrowforce = ProjectKorra.plugin.getConfig().getDouble("Zeph.Metal.MetalClipsExtension.RailThrowForce");
		this.range = ProjectKorra.plugin.getConfig().getInt("Zeph.Metal.MetalClipsExtension.Range");
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("Zeph.Metal.MetalClipsExtension.Cooldown");
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
		return "MetalClips";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		
		location = player.getLocation().add(0,1,0);
		direction = location.getDirection().normalize();
		infront = (location.add(direction.multiply(3)));
		towards = GeneralMethods.getDirection(minecart.getLocation(), infront).normalize();
		
		if (player.isDead() || !player.isOnline() || !player.isSneaking()) {
			remove();
			return;
		}
		
		if (!hasclicked && player.isSneaking()) {
			minecart.setVelocity(towards);
		}
		
		else if (hasclicked) {
			Block block = minecart.getLocation().getBlock().getRelative(BlockFace.DOWN);
			if (block.getType() == Material.RAIL || block.getType() == Material.POWERED_RAIL || block.getType() == Material.ACTIVATOR_RAIL || block.getType() == Material.DETECTOR_RAIL) {
				minecart.setVelocity(player.getLocation().getDirection().normalize().multiply(railthrowforce));
			}
			else {
				minecart.setVelocity(player.getLocation().subtract(0,landthrowforce,0).getDirection().normalize().multiply(landthrowforce));
			}
			remove();
			return;
		}
		
		}
		
	public void clickFunction() {
		hasclicked = true;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "__Zephyrus";
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
		config.addDefault("Zeph.Metal.MetalClipsExtension.Range", Integer.valueOf(5));
		config.addDefault("Zeph.Metal.MetalClipsExtension.LandThrowForce", Double.valueOf(3));
		config.addDefault("Zeph.Metal.MetalClipsExtension.RailThrowForce", Double.valueOf(10));
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


	