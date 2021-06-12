package me.zephyriox.waterwhip;

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
		
		this.speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.WaterWhip.Speed");
		this.range = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.WaterWhip.Range");
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Water.WaterWhip.Cooldown");
		this.damage = ProjectKorra.plugin.getConfig().getDouble("ExtraAbilities.Water.WaterWhip.Damage");
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
		return "WaterWhip";
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
		
		
		spawnWater();
		
		if (GeneralMethods.isSolid(location.getBlock())) {
			remove();
			return;
		}
		
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1)) {
			
			if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
				DamageHandler.damageEntity(entity, damage, this);
				remove();
				return;
			}
		}
	}
		
	public Block getSourceBlock(Player player, int range) {
		Vector direction = player.getEyeLocation().getDirection().normalize();

		for (int i = 0; i <= range; i++) {
			Block b = player.getEyeLocation().add(direction.clone().multiply((double) i)).getBlock();

			if ( b.getType() == Material.WATER || b.getType() == Material.ICE || b.getType() == Material.PACKED_ICE
					//|| b.getType() == Material.SNOW
					|| b.getType() == Material.SNOW_BLOCK)
				return b;
		}

		return null;
	}
	public boolean isBendable(Block b) {
		if ( b.getType() == Material.WATER || b.getType() == Material.ICE || b.getType() == Material.PACKED_ICE
				//|| b.getType() == Material.SNOW
				|| b.getType() == Material.SNOW_BLOCK)
			return true;

		return false;
	}
	
	public void spawnWater() {
		TempBlock tempBlock = new TempBlock(location.getBlock(), Material.WATER);
	    tempBlock.setRevertTime(100L);
		
	}
	
	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "Zephyriox";
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
		config.addDefault("ExtraAbilities.Water.WaterWhip.Range", Integer.valueOf(20));
		config.addDefault("ExtraAbilities.Water.WaterWhip.Cooldown", Integer.valueOf(1000));
		config.addDefault("ExtraAbilities.Water.WaterWhip.Damage", Double.valueOf(2.0D));
		config.addDefault("ExtraAbilities.Water.WaterWhip.Speed", Double.valueOf(1));
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


	