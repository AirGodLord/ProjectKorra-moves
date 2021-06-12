package me.f8.frostfall;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
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
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;


public class Main extends WaterAbility implements AddonAbility{
	
	private int range;
	private long cooldown;
	private Location location;
	
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
		
		
		this.location = player.getLocation();
		this.range = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.FrostFall.Range");
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Water.FrostFall.Cooldown");
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
		return "FrostFall";
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
		
		Block block = location.getBlock();
		TempBlock tempBlock = new TempBlock(block, Material.SNOW, Material.SNOW.createBlockData());
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
		config.addDefault("ExtraAbilities.Water.FrostFall.Range", Integer.valueOf(3));
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


	